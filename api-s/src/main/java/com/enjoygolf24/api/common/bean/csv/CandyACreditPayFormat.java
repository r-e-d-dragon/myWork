package com.enjoygolf24.api.common.bean.csv;

import com.univocity.parsers.fixed.FieldAlignment;
import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;

/**
 * 売上データ（CANDY-A仕様）クレジット決済用
 * 
 * レコード長:256, 改行コード:CRLF
 */
public class CandyACreditPayFormat {

	public static final String DATA_TYPE_HEADER = "HS";
	public static final String DATA_TYPE_DATA = "DS";
	public static final String DATA_TYPE_TRAILER = "TS";
	public static final String DATA_TYPE_END = "E";

	/** データコード：売上(0) */
	public static final String DATA_CODE_SALES = "0";
	/** データコード：取消(1) */
	public static final String DATA_CODE_CANCLE = "1";
	/** データコード：返品(2) */
	public static final String DATA_CODE_RETURNS = "2";

	/** 承認区分：未承認(0) */
	public static final String APPROVAL_TYPE_UNAPPROVED = "0";
	/** 承認区分：承認済(1) */
	public static final String APPROVAL_TYPE_APPROVED = "1";

	/**
	 * 売上要求
	 * 
	 * @return
	 */
	public FixedWidthParser getRequestParser() {
		FixedWidthParser parser = new FixedWidthParser(getRequestParserSettings());
		return parser;
	}

	/**
	 * 売上結果
	 * 
	 * @return
	 */
	public FixedWidthParser getResultParser() {
		FixedWidthParser parser = new FixedWidthParser(getResultParserSettings());
		return parser;
	}

	/**
	 * 売上入出力共通ヘッダー(S001)
	 * 
	 * @return
	 */
	public FixedWidthFields getHeaderRecordFields() {
		FixedWidthFields headerRecordFields = new FixedWidthFields();
		headerRecordFields.addField("データ種別", 2).setFieldLength(0, 2); // HS：固定
		headerRecordFields.addField("端末識別ID", 8).setFieldLength(1, 8);
		headerRecordFields.addField("端末識別枝番", 2).setFieldLength(2, 2);
		headerRecordFields.addField("FILLER", 2).setFieldLength(3, 2);
		headerRecordFields.addField("取引日付", 6).setFieldLength(4, 6); // YYMMDD
		headerRecordFields.addField("認識コード", 20).setFieldLength(5, 20);
		headerRecordFields.addField("処理タイプ ", 2).setFieldLength(6, 2);
		headerRecordFields.addField("スペース", 212).setFieldLength(7, 212);
		return headerRecordFields;
	};

	/**
	 * 売上要求ファイル形式(S002)
	 * 
	 * 売上対象データの入力ファイル形式
	 * 
	 * @return
	 */
	public FixedWidthFields getRequestDataRecordFields() {
		FixedWidthFields dataRecordFields = new FixedWidthFields();
		dataRecordFields.addField("データ種別", 2).setFieldLength(0, 2); // DS：固定値
		dataRecordFields.addField("端末識別ID", 8).setFieldLength(1, 8);
		dataRecordFields.addField("端末識別枝番", 2).setFieldLength(2, 2);
		dataRecordFields.addField("予備", 2).setFieldLength(3, 2);
		dataRecordFields.addField("取引日付", 6).setFieldLength(4, 6); // YYMMDD
		dataRecordFields.addField("データ区分", 1).setFieldLength(5, 1); // ０：売上、１：取消、２：返品
		dataRecordFields.addField("伝票番号", 10).setFieldLength(6, 10);
		dataRecordFields.addField("元伝票番号", 10).setFieldLength(7, 10);
		dataRecordFields.addField("会員コード", 20).setFieldLength(8, 20); // 数値を指定した場合、右詰左ゼロ埋で設定します。
		dataRecordFields.addField("商品コード", 15).setFieldLength(9, 15); // 数値を指定した場合、右詰左ゼロ埋で設定します。
		dataRecordFields.addField("商品名", 15, FieldAlignment.LEFT, '　').setFieldLength(10, 15); // 全角
		dataRecordFields.addField("数量", 7).setFieldLength(11, 7);
		dataRecordFields.addField("単価", 7).setFieldLength(12, 7);
		dataRecordFields.addField("金額", 8).setFieldLength(13, 8);
		dataRecordFields.addField("処理フラグ", 1).setFieldLength(14, 1);
		dataRecordFields.addField("承認区分", 1).setFieldLength(15, 1);// ０：未承認、１：承認済
		dataRecordFields.addField("データ番号", 5).setFieldLength(16, 5);
		dataRecordFields.addField("元伝票番号", 5).setFieldLength(17, 5);
		dataRecordFields.addField("クレジット会社コード", 4).setFieldLength(18, 4);
		dataRecordFields.addField("クレジットカード番号", 16).setFieldLength(19, 16);
		dataRecordFields.addField("有効期限", 4).setFieldLength(20, 4);
		dataRecordFields.addField("商品コード（クレジット）", 7).setFieldLength(21, 7);
		dataRecordFields.addField("支払区分", 2).setFieldLength(22, 2);
		dataRecordFields.addField("支払開始月", 2).setFieldLength(23, 2);
		dataRecordFields.addField("分割回数", 2).setFieldLength(24, 2);
		dataRecordFields.addField("ボーナス金額", 8).setFieldLength(25, 8);
		dataRecordFields.addField("ボーナス回数", 2).setFieldLength(26, 2);
		dataRecordFields.addField("ボーナス月１", 2).setFieldLength(27, 2);
		dataRecordFields.addField("ボーナス月２", 2).setFieldLength(28, 2);
		dataRecordFields.addField("ボーナス金額２", 8).setFieldLength(29, 8);
		dataRecordFields.addField("エラーコード", 3).setFieldLength(30, 3);
		dataRecordFields.addField("有人判定承認番号", 7).setFieldLength(31, 7);
		dataRecordFields.addField("承認番号", 7).setFieldLength(32, 7);
		dataRecordFields.addField("承認処理日付", 6).setFieldLength(33, 6);
		dataRecordFields.addField("承認処理時間", 6).setFieldLength(34, 6);
		dataRecordFields.addField("端末処理通番", 5).setFieldLength(35, 5);
		dataRecordFields.addField("売上集計日", 6).setFieldLength(36, 6);
		dataRecordFields.addField("FILLER", 15).setFieldLength(37, 15);
		return dataRecordFields;
	};

	/**
	 * 売上結果ファイル形式(S003)
	 * 
	 * @return
	 */
	public FixedWidthFields getResultDataRecordFields() {
		FixedWidthFields dataRecordFields = new FixedWidthFields();
		dataRecordFields.addField("データ種別", 2).setFieldLength(0, 2); // DS：固定値
		dataRecordFields.addField("端末識別ID", 8).setFieldLength(1, 8);
		dataRecordFields.addField("端末識別枝番", 2).setFieldLength(2, 2);
		dataRecordFields.addField("予備", 2).setFieldLength(3, 2);
		dataRecordFields.addField("取引日付", 6).setFieldLength(4, 6); // YYMMDD
		dataRecordFields.addField("データ区分", 1).setFieldLength(5, 1); // ０：売上、１：取消、２：返品
		dataRecordFields.addField("伝票番号", 10).setFieldLength(6, 10);
		dataRecordFields.addField("元伝票番号", 10).setFieldLength(7, 10);
		dataRecordFields.addField("会員コード", 20).setFieldLength(8, 20);
		dataRecordFields.addField("商品コード", 15).setFieldLength(9, 15);
		dataRecordFields.addField("商品名", 15, FieldAlignment.LEFT, '　').setFieldLength(10, 15); // 全角
		dataRecordFields.addField("数量", 7).setFieldLength(11, 7);
		dataRecordFields.addField("単価", 7).setFieldLength(12, 7);
		dataRecordFields.addField("金額", 8).setFieldLength(13, 8);
		dataRecordFields.addField("処理フラグ", 1).setFieldLength(14, 1);
		dataRecordFields.addField("承認区分", 1).setFieldLength(15, 1);// ０：未承認、１：承認済
		dataRecordFields.addField("データ番号", 5).setFieldLength(16, 5);
		dataRecordFields.addField("元伝票番号", 5).setFieldLength(17, 5);
		dataRecordFields.addField("クレジット会社コード", 4).setFieldLength(18, 4);
		dataRecordFields.addField("クレジットカード番号", 16).setFieldLength(19, 16);
		dataRecordFields.addField("有効期限", 4).setFieldLength(20, 4);
		dataRecordFields.addField("商品コード（クレジット）", 7).setFieldLength(21, 7);
		dataRecordFields.addField("支払区分", 2).setFieldLength(22, 2);
		dataRecordFields.addField("支払開始月", 2).setFieldLength(23, 2);
		dataRecordFields.addField("分割回数", 2).setFieldLength(24, 2);
		dataRecordFields.addField("ボーナス金額", 8).setFieldLength(25, 8);
		dataRecordFields.addField("ボーナス回数", 2).setFieldLength(26, 2);
		dataRecordFields.addField("ボーナス月１", 2).setFieldLength(27, 2);
		dataRecordFields.addField("ボーナス月２", 2).setFieldLength(28, 2);
		dataRecordFields.addField("ボーナス金額２", 8).setFieldLength(29, 8);
		dataRecordFields.addField("エラーコード", 3).setFieldLength(30, 3);
		dataRecordFields.addField("有人判定承認番号", 7).setFieldLength(31, 7);
		dataRecordFields.addField("承認番号", 7).setFieldLength(32, 7);
		dataRecordFields.addField("承認処理日付", 6).setFieldLength(33, 6);
		dataRecordFields.addField("承認処理時間", 6).setFieldLength(34, 6);
		dataRecordFields.addField("端末処理通番", 5).setFieldLength(35, 5);
		dataRecordFields.addField("売上集計日", 6).setFieldLength(36, 6);
		dataRecordFields.addField("", 15).setFieldLength(37, 15);
		return dataRecordFields;
	};

	/**
	 * 売上入出力共通トレーラー(S004)
	 * 
	 * @return
	 */
	public FixedWidthFields getTrailerRecordFields() {
		FixedWidthFields trailerRecordFields = new FixedWidthFields();
		trailerRecordFields.addField("データ種別", 2).setFieldLength(0, 2); // TS：固定
		trailerRecordFields.addField("端末識別ID", 8).setFieldLength(1, 8);
		trailerRecordFields.addField("端末識別枝番", 2).setFieldLength(2, 2);
		trailerRecordFields.addField("FILLER", 2).setFieldLength(3, 2);
		trailerRecordFields.addField("取引日付", 6).setFieldLength(4, 6); // YYMMDD
		trailerRecordFields.addField("認識コード", 20).setFieldLength(5, 20);
		trailerRecordFields.addField("売上件数", 7).setFieldLength(6, 7);
		trailerRecordFields.addField("取消・返品件数", 7).setFieldLength(7, 7);
		trailerRecordFields.addField("合計件数", 7).setFieldLength(8, 7);
		trailerRecordFields.addField("売上金額", 15).setFieldLength(9, 15);
		trailerRecordFields.addField("取消・返品金額", 15).setFieldLength(10, 15);
		trailerRecordFields.addField("合計金額", 15).setFieldLength(11, 15);
		trailerRecordFields.addField("スペース", 148).setFieldLength(12, 148);
		return trailerRecordFields;
	};

	/**
	 * エンドレコード(E001)
	 * 
	 * 各ファイル共通のエンドレコード（レコード長:256)
	 * 
	 * @return
	 */
	public FixedWidthFields getEndRecordFields() {
		FixedWidthFields endRecordFields = new FixedWidthFields();
		endRecordFields.addField("データ種別", 1).setFieldLength(0, 1); // E：固定値
		endRecordFields.addField("FILLER", 253).setFieldLength(1, 253);// スペース：固定値
		// endRecordFields.addField("CRLF ", 2).setFieldLength(2, 2); // CRLF
		return endRecordFields;
	};

	/**
	 * 売上結果データ CSVパーサ設定
	 * 
	 * @return
	 */
	private FixedWidthParserSettings getResultParserSettings() {
		FixedWidthParserSettings parserSettings = new FixedWidthParserSettings();
		parserSettings.getFormat().setLineSeparator("\r\n");
		parserSettings.addFormatForLookahead(DATA_TYPE_HEADER, getHeaderRecordFields());
		parserSettings.addFormatForLookahead(DATA_TYPE_DATA, getResultDataRecordFields());
		parserSettings.addFormatForLookahead(DATA_TYPE_TRAILER, getTrailerRecordFields());
		parserSettings.addFormatForLookahead(DATA_TYPE_END, getEndRecordFields());
		return parserSettings;
	}

	/**
	 * 売上要求データ CSVパーサ設定
	 * 
	 * @return
	 */
	private FixedWidthParserSettings getRequestParserSettings() {
		FixedWidthParserSettings parserSettings = new FixedWidthParserSettings();
		parserSettings.getFormat().setLineSeparator("\r\n");
		parserSettings.addFormatForLookahead(DATA_TYPE_HEADER, getHeaderRecordFields());
		parserSettings.addFormatForLookahead(DATA_TYPE_DATA, getRequestDataRecordFields());
		parserSettings.addFormatForLookahead(DATA_TYPE_TRAILER, getTrailerRecordFields());
		parserSettings.addFormatForLookahead(DATA_TYPE_END, getEndRecordFields());
		return parserSettings;
	}
}
