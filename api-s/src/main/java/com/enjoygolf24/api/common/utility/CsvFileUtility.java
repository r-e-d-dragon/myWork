
package com.enjoygolf24.api.common.utility;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.MimeTypeUtils;
import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.annotation.CsvColumnWithLength;
import com.github.mygreen.supercsv.annotation.CsvColumn;

/**
 * <p>
 * クラス名：<br>
 * CSVファイルユーティリティ<br>
 * <br>
 * 機能説明：<br>
 * CSVファイルの出力を行う。<br>
 * <br>
 * </p>
 */
public class CsvFileUtility {

	public static final Charset DEFAULT_CHARSET = Charset.forName("MS932");

	/** クォーテーション */
	public static final String QUOTATION_DOUBLE = "\"";

	/**
	 * ファイル出力
	 *
	 * @param filePath  ファイルパス
	 * @param csvDatas  CSVデータリスト
	 * @param quotation クォーテーション
	 */
	public static final void write(String filePath, List<?> csvDatas, String quotation) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filePath), DEFAULT_CHARSET))) {
			for (Object csvData : csvDatas) {
				String line = getCsvString(csvData, quotation);
				writer.write(line);
				writer.write("\r\n");
			}
			writer.flush();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ファイル出力
	 *
	 * @param filePath  ファイルパス
	 * @param header    ヘッダ
	 * @param csvDatas  CSVデータリスト
	 * @param quotation クォーテーション
	 */
	public static final void write(String filePath, String header, List<?> csvDatas, String quotation) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filePath), DEFAULT_CHARSET))) {
			writer.write(header);
			writer.write("\r\n");

			for (Object csvData : csvDatas) {
				String line = getCsvString(csvData, quotation);
				writer.write(line);
				writer.write("\r\n");
			}
			writer.flush();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * CSVヘッダ取得
	 *
	 * @param csvData   CSVデータ
	 * @param quotation クォーテーション
	 * @return CSVヘッダ
	 */
	public static String getCsvHeader(Object csvData, String quotation) {
		try {
			Field[] fields = csvData.getClass().getDeclaredFields();

			Set<Field> set = new TreeSet<>((f1, f2) -> {
				CsvColumn order1 = f1.getAnnotation(CsvColumn.class);
				CsvColumn order2 = f2.getAnnotation(CsvColumn.class);
				return order1.number() - order2.number();
			});

			for (Field field : fields) {
				if (field.getAnnotation(CsvColumn.class) != null) {
					set.add(field);
				}
			}

			List<String> values = new ArrayList<>();
			for (Field field : set) {
				field.setAccessible(true);
				CsvColumn annotation = field.getAnnotation(CsvColumn.class);
				String stringValue = annotation.label();

				if (!StringUtils.isEmpty(quotation)) {
					stringValue = String.format("%s%s%s", quotation, stringValue, quotation);
				}

				values.add(stringValue);
			}

			return StringUtils.join(values, ",");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * CSVストリング取得（クォーテーションなし）
	 *
	 * @param csvData CSVデータ
	 * @return CSVストリング
	 */
	public static String getCsvString(Object csvData) {
		return getCsvString(csvData, "");
	}

	/**
	 * CSVストリング取得
	 *
	 * @param csvData   CSVデータ
	 * @param quotation クォーテーション
	 * @return CSVストリング
	 */
	public static String getCsvString(Object csvData, String quotation) {
		try {
			Field[] fields = csvData.getClass().getDeclaredFields();
			boolean withSize = Arrays.stream(fields).anyMatch(f -> f.getAnnotation(CsvColumnWithLength.class) != null);

			Set<Field> set = new TreeSet<>((f1, f2) -> {
				if (withSize) {
					CsvColumnWithLength order1 = f1.getAnnotation(CsvColumnWithLength.class);
					CsvColumnWithLength order2 = f2.getAnnotation(CsvColumnWithLength.class);
					return order1.number() - order2.number();
				} else {
					CsvColumn order1 = f1.getAnnotation(CsvColumn.class);
					CsvColumn order2 = f2.getAnnotation(CsvColumn.class);
					return order1.number() - order2.number();
				}
			});

			for (Field field : fields) {
				if (field.getAnnotation(CsvColumn.class) != null
						|| field.getAnnotation(CsvColumnWithLength.class) != null) {
					set.add(field);
				}
			}

			List<String> values = new ArrayList<>();
			for (Field field : set) {
				field.setAccessible(true);
				String stringValue = "";
				Object orgValue = field.get(csvData);
				if (orgValue != null) {
					Class<?> type = field.getType();
					if (type.equals(BigDecimal.class)) {
						stringValue = ((BigDecimal) orgValue).toString();
					} else if (type.equals(Boolean.class)) {
						stringValue = ((Boolean) orgValue).toString();
					} else if (type.equals(Date.class)) {
						stringValue = DateUtility.toSlashDateString((Date) orgValue);
					} else if (type.equals(Integer.class)) {
						stringValue = ((Integer) orgValue).toString();
					} else if (type.equals(Long.class)) {
						stringValue = ((Long) orgValue).toString();
					} else if (type.equals(Timestamp.class)) {
						stringValue = DateUtility.toSlashDateString((Date) orgValue);
					} else {
						stringValue = (String) orgValue;
					}
				}

				if (withSize) {
					CsvColumnWithLength annotation = field.getAnnotation(CsvColumnWithLength.class);
					if (stringValue.length() > annotation.length()) {
						stringValue = stringValue.substring(0, annotation.length());
					}
				}

				if (!StringUtils.isEmpty(quotation)) {
					stringValue = String.format("%s%s%s", quotation, stringValue, quotation);
				}

				values.add(stringValue);
			}

			return StringUtils.join(values, ",");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * CSVデータダウンロード
	 *
	 * @param response レスポンス
	 * @param csvData  CSVデータ
	 */
	public static void putCsvDataToResponse(HttpServletResponse response, Pair<String, List<String>> csvData) {
		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE);
		response.setCharacterEncoding(DEFAULT_CHARSET.toString());
		// "filename*=\"UTF-8''" + encodedFilename + "\""
		try {
			response.setHeader("Content-Disposition",
					String.format("attachment; filename=\"%s\"", URLEncoder.encode(csvData.getLeft(), "UTF-8")));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (PrintWriter pw = response.getWriter()) {
			csvData.getRight().stream().forEach(line -> {
				pw.print(line);
				pw.print("\r\n");
			});
			response.flushBuffer();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
