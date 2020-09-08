package com.enjoygolf24.api.service.impl;

import java.io.File;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.bean.csv.CandyACsvFormat;
import com.enjoygolf24.api.common.constants.CsvFileReadContants;
import com.enjoygolf24.api.common.database.bean.TblPaymentHistory;
import com.enjoygolf24.api.common.database.bean.TblPaymentHistoryPK;
import com.enjoygolf24.api.common.database.jpa.repository.PaymentHistoryRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.PaymentInfo;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.service.CsvFileReadService;
import com.univocity.parsers.common.ArgumentUtils;
import com.univocity.parsers.fixed.FixedWidthParser;

@Service
public class CsvFileReadServiceImpl implements CsvFileReadService {

	@Autowired
	PaymentHistoryRepository paymentHistoryRepository;

	private final String FILETYPE_CANDY = CsvFileReadContants.FILETYPE_CANDY;

	@Override
	@Transactional
	public boolean csvFileRead(String fileType, String filename) {

		boolean rtnFlag = false;

		if (FILETYPE_CANDY.equals(fileType)) {
			rtnFlag = candyA(filename);
		}
		return rtnFlag;
	}

	private boolean candyA(String filename) {
		// TODO
		List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();

		int transferredCount = 0;
		long transferredAmount = 0;

		boolean endFlag = false;
		try (Reader reader = ArgumentUtils.newReader(new File(filename), Charset.forName("SHIFT-JIS"));) {
			// CSVファイル読み取り
			CandyACsvFormat format = new CandyACsvFormat();
			FixedWidthParser parser = format.getParser();
			List<String[]> allRows = parser.parseAll(reader);

			for (String[] row : allRows) {
				if (CandyACsvFormat.DATA_TYPE_HEADER.equals(row[0])) {
					System.out.println("> " + Arrays.toString(row));
				} else if (CandyACsvFormat.DATA_TYPE_DATA.equals(row[0])) {
					if (CandyACsvFormat.TRANSFER_RESULT_CODE_SUCCESS.equals(row[12])) {
						System.out.println("> " + Arrays.toString(row));
						PaymentInfo payment = new PaymentInfo();
						payment.setBankCode(row[1]);
						payment.setBranchCode(row[3]);
						payment.setAccountNumber(row[7]);
						payment.setNameKana(row[8]);
						payment.setAmount(Long.valueOf(row[9]));
						payment.setTransferredResultCode(row[12]);
						paymentInfoList.add(payment);
					}
				} else if (CandyACsvFormat.DATA_TYPE_TRAILER.equals(row[0])) {
					//
					transferredCount += Integer.valueOf(row[3]);
					transferredAmount += Long.valueOf(row[4]);
				} else if (CandyACsvFormat.DATA_TYPE_END.equals(row[0])) {
					endFlag = true;
				}
			}

			if (!endFlag) {
				// 異常 - エンドレコードがない。
				System.out.println("CSVファイル読み取りへ失敗しました。(エンドレコードがない)");
				return false;
			}
			if (paymentInfoList.size() != transferredCount) {
				// 異常 - 振替済の読み取り合計件数が一致しない。
				System.out.println("CSVファイル読み取りへ失敗しました。(振替済の読み取り件数が一致しない。)");
				return false;
			}
			if (transferredAmount != paymentInfoList.stream().mapToLong(p -> p.getAmount()).sum()) {
				// 異常 - 振替済の読み取り合計金額が一致しない。
				System.out.println("CSVファイル読み取りへ失敗しました。(振替済の読み取り合計金額が一致しない。)");
				return false;
			}
			System.out.println("transferredCount(" + paymentInfoList.size() + ")");
			System.out
					.println("transferredCount(" + paymentInfoList.stream().mapToLong(p -> p.getAmount()).sum() + ")");

			System.out.println("transferredCount(" + transferredCount + ")");
			System.out.println("transferredAmount(" + transferredAmount + ")");
			for (PaymentInfo payment : paymentInfoList) {
				insertPaymentHistory(payment);
			}

			// TODO 読み取り成功
			System.out.println("CSVファイル読み取り成功(" + filename + ")");
		} catch (Exception e) {
			// TODO
			// 異常終了時の処理
			System.out.println("CSVファイル読み取りへ失敗しました。" + e.getMessage());
			return false;
		} catch (Throwable t) {
			// TODO
			// 異常終了時の処理
			System.out.println("CSVファイル読み取りへ失敗しました。" + t.getMessage());
			return false;
		}
		return true;
	}

	@Transactional
	private void insertPaymentHistory(PaymentInfo payment) {
		// TODO 臨時
		TblPaymentHistoryPK pk = new TblPaymentHistoryPK();
		pk.setMemberCode("MEM01-" + DateUtility.toDateString("ssSSS", DateUtility.getCurrentTimestamp()));
		pk.setValidMonth("201910");

		TblPaymentHistory history = new TblPaymentHistory();
		history.setId(pk);
		history.setBankCode(payment.getBankCode());
		history.setBranchCode(payment.getBranchCode());
		history.setNameKana(payment.getNameKana());
		history.setBillDate(DateUtility.getDate("2020/09/10"));
		history.setAccountNumber(payment.getAccountNumber());
		paymentHistoryRepository.save(history);
	}
}