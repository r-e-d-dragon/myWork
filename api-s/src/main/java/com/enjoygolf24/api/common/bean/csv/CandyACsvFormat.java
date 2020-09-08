package com.enjoygolf24.api.common.bean.csv;

import com.univocity.parsers.fixed.FieldAlignment;
import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;

public class CandyACsvFormat {

	public static final String DATA_TYPE_HEADER = "1";
	public static final String DATA_TYPE_DATA = "2";
	public static final String DATA_TYPE_TRAILER = "8";
	public static final String DATA_TYPE_END = "9";

	public static final String TRANSFER_RESULT_CODE_SUCCESS = "0";

	public FixedWidthParser getParser() {
		FixedWidthParser parser = new FixedWidthParser(getParserSettings());
		return parser;
	}

	/**
	 * ヘッダーレコード
	 * 
	 * @return
	 */
	private FixedWidthFields getHeaderRecordFields() {
		FixedWidthFields headerRecordFields = new FixedWidthFields();
		headerRecordFields.addField("データ区分", 1).setFieldLength(0, 1);
		headerRecordFields.addField("種別コード", 2).setFieldLength(1, 2);
		headerRecordFields.addField("コード区分", 1).setFieldLength(2, 1);
		headerRecordFields.addField("委託者コード ", 10).setFieldLength(3, 10);
		headerRecordFields.addField("委託者名", 40).setFieldLength(4, 40);
		headerRecordFields.addField("引落日", 4).setFieldLength(5, 4);
		headerRecordFields.addField("取引銀行番号", 4, FieldAlignment.RIGHT, '0').setFieldLength(6, 4);
		headerRecordFields.addField("取引銀行名", 15).setFieldLength(7, 15);
		headerRecordFields.addField("取引支店番号", 3, FieldAlignment.RIGHT, '0').setFieldLength(8, 3);
		headerRecordFields.addField("取引支店名", 15).setFieldLength(9, 15);
		headerRecordFields.addField("預金種目", 1).setFieldLength(10, 1);
		headerRecordFields.addField("口座番号", 7, FieldAlignment.RIGHT, '0').setFieldLength(11, 7);
		headerRecordFields.addField("FILLER", 17).setFieldLength(12, 17);
		return headerRecordFields;
	};

	/**
	 * データレコード
	 * 
	 * @return
	 */
	private FixedWidthFields getDataRecordFields() {
		FixedWidthFields dataRecordFields = new FixedWidthFields();
		dataRecordFields.addField("データ区分", 1).setFieldLength(0, 1);
		dataRecordFields.addField("引落銀行番号", 4).setFieldLength(1, 4);
		dataRecordFields.addField("引落銀行名", 15).setFieldLength(2, 15);
		dataRecordFields.addField("引落支店番号 ", 3).setFieldLength(3, 3);
		dataRecordFields.addField("引落支店名", 15).setFieldLength(4, 15);
		dataRecordFields.addField("FILLER1", 4).setFieldLength(5, 4);
		dataRecordFields.addField("預金種目", 1).setFieldLength(6, 1);
		dataRecordFields.addField("口座番号", 7, FieldAlignment.RIGHT, '0').setFieldLength(7, 7);
		dataRecordFields.addField("預金者名", 30).setFieldLength(8, 30);
		dataRecordFields.addField("引落金額", 10, FieldAlignment.RIGHT, '0').setFieldLength(9, 10);
		dataRecordFields.addField("新規コード", 1).setFieldLength(10, 1);
		dataRecordFields.addField("顧客番号", 20, FieldAlignment.RIGHT, '0').setFieldLength(11, 20);
		dataRecordFields.addField("振替結果コード", 1).setFieldLength(12, 1);
		dataRecordFields.addField("FILLER2", 8).setFieldLength(13, 8);
		return dataRecordFields;
	};

	/**
	 * トレーラーレコード
	 * 
	 * @return
	 */
	private FixedWidthFields getTrailerRecordFields() {
		FixedWidthFields trailerRecordFields = new FixedWidthFields();
		trailerRecordFields.addField("データ区分", 1).setFieldLength(0, 1);
		trailerRecordFields.addField("合計件数", 6).setFieldLength(1, 6);
		trailerRecordFields.addField("合計金額", 12).setFieldLength(2, 12);
		trailerRecordFields.addField("振替済件数", 6).setFieldLength(3, 6);
		trailerRecordFields.addField("振替済金額", 12).setFieldLength(4, 12);
		trailerRecordFields.addField("振替不能件数", 6).setFieldLength(5, 6);
		trailerRecordFields.addField("振替不能金額", 12).setFieldLength(6, 12);
		trailerRecordFields.addField("FILLER", 65).setFieldLength(7, 65);
		return trailerRecordFields;
	};

	/**
	 * エンドレコード
	 * 
	 * @return
	 */
	private FixedWidthFields getEndRecordFields() {
		FixedWidthFields endRecordFields = new FixedWidthFields();
		endRecordFields.addField("データ区分", 1).setFieldLength(0, 1);
		endRecordFields.addField("FILLER", 119).setFieldLength(1, 119);
		return endRecordFields;
	};

	/**
	 * CSVパーサ設定
	 * 
	 * @return
	 */
	private FixedWidthParserSettings getParserSettings() {
		FixedWidthParserSettings parserSettings = new FixedWidthParserSettings();
		parserSettings.getFormat().setLineSeparator("\r\n");
		parserSettings.addFormatForLookahead(DATA_TYPE_HEADER, getHeaderRecordFields());
		parserSettings.addFormatForLookahead(DATA_TYPE_DATA, getDataRecordFields());
		parserSettings.addFormatForLookahead(DATA_TYPE_TRAILER, getTrailerRecordFields());
		parserSettings.addFormatForLookahead(DATA_TYPE_END, getEndRecordFields());
		return parserSettings;
	}
}
