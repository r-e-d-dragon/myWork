package com.enjoygolf24.online.web.test.openCsv;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDate;

import org.supercsv.prefs.CsvPreference;

import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.online.web.test.openCsv.data.SampleCsv;
import com.github.mygreen.supercsv.io.LazyCsvAnnotationBeanWriter;

public class CreateCsvFileTest {

	public static void main(String[] args) {
		System.out.println("Start !!!");
		// TODO Auto-generated method stub
		sampleWrite();

		System.out.println("End !!!");
	}

	// レコードを1件ずつ書き出す場合
	public static void sampleWrite() {

		String sysdate = DateUtility.toDateString("yyyyMMddHHmmssSSS", DateUtility.getCurrentTimestamp());

		try (LazyCsvAnnotationBeanWriter<SampleCsv> csvWriter = new LazyCsvAnnotationBeanWriter<>(SampleCsv.class,
				Files.newBufferedWriter(new File("/var/enjoygolf24/sample_" + sysdate + ".csv").toPath(),
						Charset.forName("Windows-31j")),
				CsvPreference.STANDARD_PREFERENCE);) {

			// 初期化を行います
			csvWriter.init();

			// ヘッダー行の書き出し
			csvWriter.writeHeader();

			// レコードのデータの書き出し
			SampleCsv record1 = new SampleCsv();
			record1.setId(1);
			record1.setName("山田太郎");
			record1.setBirthday(LocalDate.of(2000, 10, 1));
			record1.setComment("あいうえお");
			csvWriter.write(record1);

			SampleCsv record2 = new SampleCsv();
			record2.setId(2);
			record2.setName("鈴木次郎");
			record2.setBirthday(LocalDate.of(2012, 1, 2));
			record2.setComment(null);
			csvWriter.write(record2);

			// csvWrier.flush();
			// csvWrier.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
