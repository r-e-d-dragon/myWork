
package com.enjoygolf24.api.common.utility;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>
 * クラス名：<br>
 * 日付クラスユーティリティ<br>
 * <br>
 * 機能説明：<br>
 * 日付クラス用のツールを提供する。<br>
 * <br>
 * </p>
 */
public class DateUtility {

	/** 秒出力有 */
	public static final boolean WITH_SECOND = true;

	/** 秒出力無 */
	public static final boolean WITHOUT_SECOND = false;

	/** 日付フォーマット */
	public static final String DATE_FORMAT = "yyyy/MM/dd";

	/** 日付フォーマット */
	private static final String HYPHEN_DATE_PATTERN = "yyyy-MM-dd";

	/** 日付フォーマット */
	public static final String PLAIN_DATE_FORMAT = "yyyyMMdd";

	/** 日付フォーマット */
	public static final String TIME_FORMAT = "HH:mm";

	/** 日付フォーマット */
	public static final String SLASH_DATE_FORMAT_YEAR_MONTH = "yyyy/MM";

	/** 日付フォーマット */
	public static final String PLAIN_DATE_FORMAT_FULL = "yyyyMMddHHmmss";

	/** 日付フォーマット */
	public static final String PLAIN_DATE_FORMAT_FULL_WITH_MS = "yyyyMMddHHmmssSSS";

	/** 日時フォーマット */
	public static final String SLASH_TIMESTAMP_PATTERN_WITH_SECOND = "yyyy/MM/dd HH:mm:ss";

	/** 日時フォーマット */
	public static final String SLASH_TIMESTAMP_PATTERN_WITHOUT_SECOND = "yyyy/MM/dd HH:mm";

	/** 日時フォーマット */
	public static final String MAIL_SENDING_SCHEDULED_DATETIME_FORMAT = "yyyy/MM/dd HH:00";

	/** 日時フォーマッタ */
	private static final SimpleDateFormat sdf = new SimpleDateFormat(DateUtility.DATE_FORMAT);

	/** 一日ミリセカンド */
	private static final long ONE_DATE_MILLISECOND = 1000 * 60 * 60 * 24;

	/** 一時間ミリセカンド */
	private static final long ONE_HOUR_MILLISECOND = 1000 * 60 * 60;

	/** 一分ミリセカンド */
	private static final long ONE_MINUTE_MILLISECOND = 1000 * 60;

	/** ポイント終了日までの年数 */
	private static final int YEAR_POINT_END_SPAN = 3;

	/**
	 * YYYYMMDD日付変換
	 *
	 * @param date 日付
	 * @return YYYYMMDD日付
	 */
	public static String toPlainDateString(Date date) {
		return toDateString(PLAIN_DATE_FORMAT, date);
	}

	/**
	 * ハイフン日付変換
	 *
	 * @param date 日付
	 * @return ハイフン日付
	 */
	public static String toHyphenDateString(Date date) {
		return toDateString(HYPHEN_DATE_PATTERN, date);
	}

	/**
	 * ハイフン日付変換
	 *
	 * @param timestamp 日時
	 * @return ハイフン日付
	 */
	public static String toHyphenDateString(Timestamp timestamp) {
		return toDateString(HYPHEN_DATE_PATTERN, timestamp);
	}

	/**
	 * スラッシュ日時変換
	 *
	 * @param timestamp 日時
	 * @return スラッシュ日時
	 */
	public static String toSlashTimestampString(Timestamp timestamp) {
		return toSlashTimestampString(timestamp, WITH_SECOND);
	}

	/**
	 * スラッシュ日時変換
	 *
	 * @param timestamp  日時
	 * @param withSecond 秒出力有無
	 * @return スラッシュ日時
	 */
	public static String toSlashTimestampString(Timestamp timestamp, boolean withSecond) {
		return toDateString((withSecond) ? SLASH_TIMESTAMP_PATTERN_WITH_SECOND : SLASH_TIMESTAMP_PATTERN_WITHOUT_SECOND,
				timestamp);
	}

	/**
	 * スラッシュ日付変換
	 *
	 * @param date 日付
	 * @return スラッシュ日付
	 */
	public static String toSlashDateString(Date date) {
		return toDateString(DATE_FORMAT, date);
	}

	/**
	 * スラッシュ日付変換
	 *
	 * @param date 日付
	 * @return スラッシュ日付
	 */
	public static String toSlashDateString(Timestamp timestamp) {
		return toDateString(DATE_FORMAT, timestamp);
	}

	/**
	 * スラッシュ日付変換
	 *
	 * @param date 日付
	 * @return スラッシュ日付
	 */
	public static String toSlashDateString(String date) {
		return String.format("%s/%s/%s", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
	}

	/**
	 * フォーマット
	 *
	 * @param pattern パターン
	 * @param date    日付
	 * @return フォーマット日付
	 */
	public static String toDateString(String pattern, Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * フォーマット
	 *
	 * @param pattern   パターン
	 * @param timestamp 日時
	 * @return フォーマット日時
	 */
	public static String toDateString(String pattern, Timestamp timestamp) {
		return new SimpleDateFormat(pattern).format(timestamp);
	}

	/**
	 * フォーマット
	 *
	 * @param pattern   パターン
	 * @param timestamp 日時
	 * @return フォーマット日時
	 */
	public static String toTimeString(String pattern, Time time) {
		return new SimpleDateFormat(pattern).format(time);
	}

	/**
	 * フォーマット
	 *
	 * @param pattern   パターン
	 * @param timestamp 日時
	 * @return フォーマット日時
	 */
	public static String toTimeString(Time time) {
		return toTimeString(TIME_FORMAT, time);
	}

	/**
	 * 日付を取得する
	 * 
	 * @param date 日付テキスト（yyyy/MM/dd)
	 * @return 日付
	 */
	public static Date getDate(String date) {

		if (date == null || date.isEmpty()) {
			return null;
		}

		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日付を取得する
	 * 
	 * @param date 日付テキスト（yyyy/MM/dd)
	 * @return SQL日付
	 */
	public static java.sql.Date getSqlDate(String date) {

		if (date == null || date.isEmpty()) {
			return null;
		}

		try {

			return new java.sql.Date(sdf.parse(date).getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日付・日時変換（00:00:00）
	 * 
	 * @param source 日付
	 * @return 日時（00:00:00）
	 */
	public static Timestamp toTimestampDayOfFirst(Date source) {
		return Timestamp.valueOf(String.format("%s 0:0:0.000", toHyphenDateString(source)));
	}

	/**
	 * 日付・日時変換（23:59:59）
	 * 
	 * @param source 日付
	 * @return 日時（23:59:59）
	 */
	public static Timestamp toTimestampDayOfLast(Date source) {
		return Timestamp.valueOf(String.format("%s 23:59:59.999", toHyphenDateString(source)));
	}

	/**
	 * 日付・日時変換（23:59:59）
	 *
	 * @param source 日付
	 * @return 日時（23:59:59）
	 */
	public static Timestamp toTimestamp(Date source, int hour, int minute) {
		GregorianCalendar gcalendar = (GregorianCalendar) Calendar.getInstance();

		Calendar previewDateCalendar = Calendar.getInstance();
		previewDateCalendar.setTime(source);
		int year = previewDateCalendar.get(Calendar.YEAR);
		int month = previewDateCalendar.get(Calendar.MONTH);
		int date = previewDateCalendar.get(Calendar.DATE);
		gcalendar.set(year, month, date, hour, minute, 0);

		return new Timestamp(gcalendar.getTimeInMillis());
	}

	/**
	 * ミリ秒差分取得
	 *
	 * @param older 前日時
	 * @param newer 後日時
	 * @return ミリ秒差分
	 */
	public static long diffMillisecond(Timestamp older, Timestamp newer) {
		long diffMills = newer.getTime() - older.getTime();
		return diffMills;
	}

	/**
	 * 日差分取得
	 *
	 * @param older 前日時
	 * @param newer 後日時
	 * @return 日差分
	 */
	public static long diffDays(Timestamp older, Timestamp newer) {
		long diffMills = newer.getTime() - older.getTime();
		return diffMills / ONE_DATE_MILLISECOND;
	}

	/**
	 * 日差分取得
	 *
	 * @param older 前日時
	 * @param newer 後日時
	 * @return 日差分
	 */
	public static long diffDays(Date older, Date newer) {
		long diffMills = newer.getTime() - older.getTime();
		return diffMills / ONE_DATE_MILLISECOND;
	}

	/**
	 * 時差分取得
	 *
	 * @param older 前日時
	 * @param newer 後日時
	 * @return 時差分
	 */
	public static long diffHour(Timestamp older, Timestamp newer) {
		long diffMills = newer.getTime() - older.getTime();
		return diffMills / ONE_HOUR_MILLISECOND;
	}

	/**
	 * 分差分取得
	 *
	 * @param older 前日時
	 * @param newer 後日時
	 * @return 分差分
	 */
	public static long diffMinute(Timestamp older, Timestamp newer) {
		long diffMills = newer.getTime() - older.getTime();
		return diffMills / ONE_MINUTE_MILLISECOND;
	}

	/**
	 * 日時調整
	 *
	 * @param source      調整前日時
	 * @param adjustMills 調整値
	 * @return 調整後日時
	 */
	public static Timestamp adjust(Timestamp source, long adjustMills) {
		return new Timestamp(source.getTime() + adjustMills);
	}

	/**
	 * 日時→日付変換
	 *
	 * @param source 変換前日時
	 * @return 変換後日時
	 */
	public static Date toDate(Timestamp source) {
		return new Date(source.getTime());
	}

	/**
	 * 有効期限終了希望日算出<br/>
	 * 注意：算出ロジックはバッチと同期をとること
	 *
	 * @param preferredExpirationStartDate 有効期限開始希望日
	 * @return 有効期限終了希望日
	 */
	public static Date calcPreferredExpirationEndDate(Date preferredExpirationStartDate) {
		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.setTime(preferredExpirationStartDate);
		boolean leapFlag = false;
		if (calendar.isLeapYear(calendar.get(GregorianCalendar.YEAR))
				&& (calendar.get(GregorianCalendar.MONTH) == GregorianCalendar.FEBRUARY)
				&& (calendar.get(GregorianCalendar.DAY_OF_MONTH) == calendar
						.getActualMaximum(GregorianCalendar.DAY_OF_MONTH))) {
			// 発行日が閏年の閏日（2月29日）である場合、期限が2月28日になるよう調整する
			leapFlag = true;
		}
		calendar.add(Calendar.YEAR, YEAR_POINT_END_SPAN);
		if (!leapFlag) {
			// 2月29日発行以外の場合、-1日計算（2月29日の場合、3年後計算結果が2月28日になるため-1日演算を行わない）
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}
		Date preferredExpirationEndDate = (new Date(calendar.getTime().getTime()));
		return preferredExpirationEndDate;
	}

	/**
	 * 有効期限終了希望日算出<br/>
	 * 注意：算出ロジックはバッチと同期をとること
	 *
	 * @param preferredExpirationStartDateStr 有効期限開始希望日
	 * @return 有効期限終了希望日
	 */
	public static String calcPreferredExpirationEndDate(String preferredExpirationStartDateStr) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date preferredExpirationStartDate = null;
		try {
			preferredExpirationStartDate = sdf.parse(preferredExpirationStartDateStr);
		} catch (ParseException e) {
			// 原則、ここには入らない
			e.printStackTrace();
			return null;
		}

		Date preferredExpirationEndDate = DateUtility.calcPreferredExpirationEndDate(preferredExpirationStartDate);
		String preferredExpirationEndDateStr = sdf.format(preferredExpirationEndDate);

		return preferredExpirationEndDateStr;
	}

	/**
	 * 有効期限終了希望日算出<br/>
	 * 注意：算出ロジックはバッチと同期をとること
	 *
	 * @param preferredExpirationStartDateStr 有効期限開始希望日
	 * @return 有効期限終了希望日
	 */
	public static Date calcPreferredExpirationEndDateStrToDate(String preferredExpirationStartDateStr) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date preferredExpirationStartDate = null;
		try {
			preferredExpirationStartDate = sdf.parse(preferredExpirationStartDateStr);
		} catch (ParseException e) {
			// 原則、ここには入らない
			e.printStackTrace();
			return null;
		}

		Date preferredExpirationEndDate = DateUtility.calcPreferredExpirationEndDate(preferredExpirationStartDate);

		return preferredExpirationEndDate;
	}

	/**
	 *
	 * @param format
	 * @return
	 */
	public static String getCurrentDateTime(String format) {
		String currentDateTime = null;
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat(format);
			currentDateTime = sdf.format(System.currentTimeMillis());
		} catch (Exception e) {
			return null;
		}

		return currentDateTime;
	}

	/**
	 *
	 * @param format
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 日付文字列取得
	 *
	 * @param slashFormattedTimestmp 日付文字列（YYYY/MM/DD HH:Mi:SS.MIL）
	 * @return 日付文字列
	 */
	public static String exchageSlashFormattedTimestmpToPlainDateString(String slashFormattedTimestmp) {
		String extracted = String.format("%s%s%s", slashFormattedTimestmp.substring(0, 4),
				slashFormattedTimestmp.substring(5, 7), slashFormattedTimestmp.substring(8, 10));
		return extracted;
	}

	/**
	 * 時間文字列取得
	 *
	 * @param slashFormattedTimestmp 日付文字列（YYYY/MM/DD HH:Mi:SS.MIL）
	 * @return 時間文字列
	 */
	public static String exchageSlashFormattedTimestmpToPlainTimeString(String slashFormattedTimestmp) {
		String extracted = String.format("%s%s%s", slashFormattedTimestmp.substring(11, 13),
				slashFormattedTimestmp.substring(14, 16), slashFormattedTimestmp.substring(17, 19));
		return extracted;
	}

	/**
	 * 日付加算
	 *
	 * @param baseDate 基準日
	 * @param i        加算日数
	 * @return 計算後日付
	 */
	public static Date addDay(Date baseDate, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		cal.add(Calendar.DATE, i);
		return cal.getTime();
	}

	/**
	 * 日付加算
	 *
	 * @param baseDate 基準日
	 * @param i        加算日数
	 * @return 計算後日付
	 */
	public static Date addMonth(Date baseDate, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		cal.add(Calendar.MONTH, i);
		return cal.getTime();
	}

	/**
	 *
	 */

	/**
	 * メール配信日時(予約)フォーマット
	 *
	 * @param source 送信予定日時
	 * @return メール配信日時(予約)
	 */
	public static String formatMailSendingScheduledDatetime(Timestamp source) {
		String formatted = toDateString(MAIL_SENDING_SCHEDULED_DATETIME_FORMAT, source);
		return formatted;
	}

	/**
	 * 土・日曜日判定
	 * 
	 * @param source
	 * @return true:土・日
	 */
	public static boolean isWeekend(String slashFormattedTimestmp) {
		boolean rtn = false;
		try {
			String extracted = exchageSlashFormattedTimestmpToPlainDateString(slashFormattedTimestmp);

			int year = Integer.valueOf(extracted.substring(0, 4));
			int month = Integer.valueOf(extracted.substring(4, 6));
			int date = Integer.valueOf(extracted.substring(6, 8));

			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, date);
			if ((cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
					|| (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
				rtn = true;
			}
		} catch (Exception e) {
			return rtn;
		}
		return rtn;
	}

}
