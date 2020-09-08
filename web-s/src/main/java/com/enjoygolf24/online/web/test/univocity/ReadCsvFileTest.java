package com.enjoygolf24.online.web.test.univocity;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.enjoygolf24.api.common.database.bean.TblPaymentHistory;
import com.enjoygolf24.api.common.database.bean.TblPaymentHistoryPK;
import com.enjoygolf24.api.common.database.jpa.repository.PaymentHistoryRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.PaymentInfo;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.online.web.test.openCsv.data.CandyACsvFormat;
import com.univocity.parsers.common.ArgumentUtils;
import com.univocity.parsers.fixed.FixedWidthParser;

public class ReadCsvFileTest {

	@Autowired
	static PaymentHistoryRepository paymentHistoryRepository;

	public static void main(String[] args) {
		System.out.println("Read Start !!!");
		// TODO Auto-generated method stub
		sampleRead();

		System.out.println("Read End !!!");
	}

	// レコードを1件ずつ読み込む場合
	public static void sampleRead() {

		boolean endFlag = false;
		// TODO
		List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();

		int transferredCount = 0;
		long transferredAmount = 0;

		CandyACsvFormat format = new CandyACsvFormat();
		// CSVファイル読み取り
		FixedWidthParser parser0 = format.getParser();
		// TODO
		List<String[]> allRows = parser0.parseAll(ArgumentUtils
				.newReader(new File("/var/enjoygolf24/test-20200907031258621.csv"),
				Charset.forName("SHIFT-JIS")));
		
		for (String[] row : allRows) {
			if (CandyACsvFormat.DATA_TYPE_HEADER.equals(row[0])) {
				System.out.println("> " + Arrays.toString(row));
			} else if (CandyACsvFormat.DATA_TYPE_DATA.equals(row[0])) {
				if (com.enjoygolf24.api.common.bean.csv.CandyACsvFormat.TRANSFER_RESULT_CODE_SUCCESS.equals(row[12])) {
					System.out.println("> " + Arrays.toString(row));
					PaymentInfo payment = new PaymentInfo();
					payment.setBankCode(row[1]);
					payment.setBranchCode(row[2]);
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
			return;
		}
		if (paymentInfoList.size() != transferredCount) {
			// 異常 - 振替済の読み取り合計件数が一致しない。
			System.out.println("CSVファイル読み取りへ失敗しました。(振替済の読み取り件数が一致しない。)");
			return;
		}
		if (transferredAmount != paymentInfoList.stream().mapToLong(p -> p.getAmount()).sum()) {
			// 異常 - 振替済の読み取り合計金額が一致しない。
			System.out.println("CSVファイル読み取りへ失敗しました。(振替済の読み取り合計金額が一致しない。)");
			return;
		}
		System.out.println("transferredCount(" + paymentInfoList.size() + ")");
		System.out.println("transferredCount("
				+ paymentInfoList.stream().mapToLong(p -> p.getAmount()).sum() + ")");

		System.out.println("transferredCount(" + transferredCount + ")");
		System.out.println("transferredAmount(" + transferredAmount + ")");
		
		int i = 0;
		for (PaymentInfo payment: paymentInfoList) {
			TblPaymentHistoryPK pk = new TblPaymentHistoryPK();
			pk.setMemberCode("MEM01" + i);
			pk.setValidMonth("202010");

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

//	/**
//	 * Creates a reader for a resource in the relative path
//	 *
//	 * @param relativePath relative path of the resource to be read
//	 *
//	 * @return a reader of the resource
//	 */
//	public static Reader getReader(String relativePath) {
//		try {
//
//			return new InputStreamReader(new File("/Users/jbax/dev/data/worldcitiespop.txt"), "ISO-8859-1");
//		} catch (UnsupportedEncodingException e) {
//			throw new IllegalStateException("Unable to read input", e);
//		}
//	}
}
