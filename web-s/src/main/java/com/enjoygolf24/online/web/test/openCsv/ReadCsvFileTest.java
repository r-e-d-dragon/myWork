package com.enjoygolf24.online.web.test.openCsv;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.prefs.CsvPreference;

import com.enjoygolf24.online.web.test.openCsv.data.SampleCsv;
import com.github.mygreen.supercsv.io.LazyCsvAnnotationBeanReader;

public class ReadCsvFileTest {

	public static void main(String[] args) {
		System.out.println("Read Start !!!");
		// TODO Auto-generated method stub
		sampleRead();

		System.out.println("Read End !!!");
	}

	// レコードを1件ずつ読み込む場合
	public static void sampleRead() {

		try (LazyCsvAnnotationBeanReader<SampleCsv> csvReader = new LazyCsvAnnotationBeanReader<>(SampleCsv.class,
				Files.newBufferedReader(new File("/var/enjoygolf24/sample.csv").toPath(),
						Charset.forName("Windows-31j")),
				CsvPreference.STANDARD_PREFERENCE);
		) {
			// ヘッダー行を読み込み初期化します
			csvReader.init();

			List<SampleCsv> list = new ArrayList<>();
			SampleCsv record = null;
			while ((record = csvReader.read()) != null) {
				list.add(record);
			}
			csvReader.close();
			
			for (SampleCsv csv : list) {
				System.out.println(">>> " + csv.getId() + ", " + csv.getName() + ", " + csv.getBirthday() + ", "
						+ csv.getComment());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
