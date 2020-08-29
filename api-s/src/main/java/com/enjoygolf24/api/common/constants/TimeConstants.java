
package com.enjoygolf24.api.common.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class TimeConstants {

	/** 時最小値 */
	public static final int HOUR_MIN = 0;

	/** 時最大値 */
	public static final int HOUR_MAX = 23;

	/** 分最小値 */
	public static final int MINUTE_MIN = 0;

	/** 分最大値 */
	public static final int MINUTE_MAX = 59;

	/** 時Map */
	public static final Map<String, Integer> HOURS_MAP = createNumberMap(HOUR_MIN, HOUR_MAX);

	/** 分Map */
	public static final Map<String, Integer> MINUTES_MAP = createNumberMap(MINUTE_MIN, MINUTE_MAX);

	/** 時MapFirstNull */
	public static final Map<String, Integer> HOURS_MAP_FIRST_NULL = createNumberMapFirstNull(HOUR_MIN, HOUR_MAX);

	/** 分MapFirstNull */
	public static final Map<String, Integer> MINUTES_MAP_FIRST_NULL = createNumberMapFirstNull(MINUTE_MIN, MINUTE_MAX);

	/**
	 * 数値マップ作成
	 *
	 * @param start 開始値
	 * @param end   終了値
	 * @return 数値マップ
	 */
	private static Map<String, Integer> createNumberMap(int start, int end) {
		Map<String, Integer> numberMap = new LinkedHashMap<>();
		for (int i = start; i <= end; i++) {
			Integer ic = Integer.valueOf(i);
			numberMap.put(ic.toString(), ic);
		}
		return numberMap;
	}

	/**
	 * 数値マップ作成
	 *
	 * @param start 開始値
	 * @param end   終了値
	 * @return 数値マップ
	 */
	private static Map<String, Integer> createNumberMapFirstNull(int start, int end) {
		Map<String, Integer> numberMap = new LinkedHashMap<>();
		for (int i = start - 1; i <= end; i++) {
			if (i < 0) {
				numberMap.put(null, null);
			} else {
				Integer ic = Integer.valueOf(i);
				numberMap.put(ic.toString(), ic);
			}

		}
		return numberMap;
	}

}
