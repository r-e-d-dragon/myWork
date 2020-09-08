package com.enjoygolf24.online.web.test.openCsv.data;

import java.time.LocalDate;

import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import com.github.mygreen.supercsv.annotation.conversion.CsvFixedSize;
import com.github.mygreen.supercsv.annotation.conversion.CsvFullChar;
import com.github.mygreen.supercsv.annotation.format.CsvDateTimeFormat;

import lombok.Data;

@Data
@CsvBean
public class SampleCsv {
	// 右詰めする
	@CsvColumn(number = 1)
	@CsvFixedSize(size = 10, rightAlign = true)
	private int id;

	// パディング文字を全角空白にする。
	// 全角を入力する前提としたカラムと想定し、さらに @CsvFullChar で半角を全角に変換します。
	@CsvColumn(number = 2, label = "氏名")
	@CsvFixedSize(size = 20, padChar = '　')
	@CsvFullChar
	private String name;

	// パディング文字をアンダースコア（_）にする。
	@CsvColumn(number = 3, label = "生年月日")
	@CsvFixedSize(size = 10, padChar = '_')
	@CsvDateTimeFormat(pattern = "uuuu-MM-dd")
	private LocalDate birthday;

	// 文字サイズを超えている場合は、切り出す。
	@CsvColumn(number = 4, label = "備考")
	@CsvFixedSize(size = 20, chopped = true)
	private String comment;
}
