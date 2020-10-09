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

import com.enjoygolf24.api.common.bean.csv.CandyACreditPayFormat;
import com.enjoygolf24.api.common.constants.CsvFileReadContants;
import com.enjoygolf24.api.common.database.bean.TblPaymentHistory;
import com.enjoygolf24.api.common.database.bean.TblPaymentHistoryPK;
import com.enjoygolf24.api.common.database.jpa.repository.PaymentHistoryRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.PaymentInfo;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.service.CreditFileService;
import com.univocity.parsers.common.ArgumentUtils;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthWriter;
import com.univocity.parsers.fixed.FixedWidthWriterSettings;

@Service
public class CreditFileServiceImpl implements CreditFileService {

	@Autowired
	PaymentHistoryRepository paymentHistoryRepository;

	private final String FILETYPE_CANDY = CsvFileReadContants.FILETYPE_CANDY;

	@Override
	@Transactional
	public boolean creditFileRead(String fileType, String filename) {

		boolean rtnFlag = false;

		if (FILETYPE_CANDY.equals(fileType)) {
			rtnFlag = candyA(filename);
		}
		return rtnFlag;
	}

	@Override
	public List<String> creditFileWrite() {
		List<String> data = new ArrayList<String>();
		// データファイル
		CandyACreditPayFormat format = new CandyACreditPayFormat();

		FixedWidthWriterSettings headerSettings = new FixedWidthWriterSettings(format.getHeaderRecordFields());
		// Let's create the writer
		FixedWidthWriter writerHeader = new FixedWidthWriter(headerSettings);
		// And a few records
		String headers = writerHeader.writeRowToString(new String[] { CandyACreditPayFormat.DATA_TYPE_HEADER,
				"12345671", "11", "", "201007", "X1234567890123456789", "01", "" });
		data.add(headers);

		FixedWidthWriterSettings dataSettings = new FixedWidthWriterSettings(format.getRequestDataRecordFields());
		// Let's create the writer
		FixedWidthWriter writerData = new FixedWidthWriter(dataSettings);
		// And a few records
		String line1 = writerData
				.writeRowToString(new String[] { CandyACreditPayFormat.DATA_TYPE_DATA, "12345671", "11", "", "201007",
						"0", "0123456789", "1112223333", "00000000000123456701", "0000000000012345", "エンジョイゴルフ２４", "1",
						"9800", "10000", "", "1", "12345", "12345", "3046", "1111222233334444", "2512", "1234567", "10",
						"", "", "", "", "", "", "", "", "", "1234567", "201007", "134125", "33333", "201020", "" });
		data.add(line1);
		String line2 = writerData.writeRowToString(
				new String[] { CandyACreditPayFormat.DATA_TYPE_DATA, "12345678", "11", "", "201007", "0", "0123456789",
						"1112223333", "00000000000123456702", "0000000000012345", "エンジョイゴルフ２４（全角）", "1", "9800",
						"10000", "", "1", "12345", "12345", "3046", "1111222233334444", "2512", "1234567", "10", "", "",
						"", "", "", "", "", "", "", "1234567", "201007", "134125", "33333", "201020", "" });
		data.add(line2);
		String line3 = writerData
				.writeRowToString(new String[] { CandyACreditPayFormat.DATA_TYPE_DATA, "12345678", "11", "", "201007",
						"1", "0123456789", "1112223333", "00000000000123456703", "0000000000012345", "エンジョイゴルフ２４", "1",
						"9800", "10000", "", "1", "12345", "12345", "3046", "1111222233334444", "2512", "1234567", "10",
						"", "", "", "", "", "", "", "", "", "1234567", "201007", "134125", "33333", "201020", "" });
		data.add(line3);
		String line4 = writerData
				.writeRowToString(new String[] { CandyACreditPayFormat.DATA_TYPE_DATA, "12345678", "11", "", "201007",
						"2", "0123456789", "1112223333", "00000000000123456703", "0000000000012345", "エンジョイゴルフ２４", "1",
						"9800", "10000", "", "1", "12345", "12345", "3046", "1111222233334444", "2512", "1234567", "10",
						"", "", "", "", "", "", "", "", "", "1234567", "201007", "134125", "33333", "201020", "" });
		data.add(line4);

		FixedWidthWriterSettings trailerSettings = new FixedWidthWriterSettings(format.getTrailerRecordFields());
		// Let's create the writer
		FixedWidthWriter writerTrailer = new FixedWidthWriter(trailerSettings);
		String trailerLine = writerTrailer.writeRowToString(new String[] { CandyACreditPayFormat.DATA_TYPE_TRAILER,
				"12345678", "11", "", "201007", "X1234567890123456789", "2", "2", "4", "20000", "20000", "40000", "" });
		data.add(trailerLine);

		FixedWidthWriterSettings endSettings = new FixedWidthWriterSettings(format.getEndRecordFields());
		// Let's create the writer
		FixedWidthWriter writerEnd = new FixedWidthWriter(endSettings);
		String endLine = writerEnd.writeRowToString(new String[] { CandyACreditPayFormat.DATA_TYPE_END, "" });
		data.add(endLine);

		return data;
	}

	private boolean candyA(String filename) {
		// TODO
		List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();

		int transferredCount = 0;
		long transferredAmount = 0;

		boolean endFlag = false;
		try (Reader reader = ArgumentUtils.newReader(new File(filename), Charset.forName("SHIFT-JIS"));) {
			// CSVファイル読み取り
			CandyACreditPayFormat format = new CandyACreditPayFormat();
			FixedWidthParser parser = format.getResultParser();
			List<String[]> allRows = parser.parseAll(reader);

			for (String[] row : allRows) {
				if (CandyACreditPayFormat.DATA_TYPE_HEADER.equals(row[0])) {
					System.out.println("> " + Arrays.toString(row));
				} else if (CandyACreditPayFormat.DATA_TYPE_DATA.equals(row[0])) {
					if (CandyACreditPayFormat.DATA_CODE_SALES.equals(row[5])
							&& CandyACreditPayFormat.APPROVAL_TYPE_APPROVED.equals(row[15])) {
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
				} else if (CandyACreditPayFormat.DATA_TYPE_TRAILER.equals(row[0])) {
					//
					transferredCount += Integer.valueOf(row[3]);
					transferredAmount += Long.valueOf(row[4]);
				} else if (CandyACreditPayFormat.DATA_TYPE_END.equals(row[0])) {
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
		pk.setValidMonth("202010");

		TblPaymentHistory history = new TblPaymentHistory();
		history.setId(pk);
		history.setBankCode(payment.getBankCode());
		history.setBranchCode(payment.getBranchCode());
		history.setNameKana(payment.getNameKana());
		history.setBillDate(DateUtility.getDate("2020/10/10"));
		history.setAccountNumber(payment.getAccountNumber());
		paymentHistoryRepository.save(history);
	}
}