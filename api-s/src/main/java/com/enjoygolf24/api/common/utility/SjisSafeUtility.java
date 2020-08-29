
package com.enjoygolf24.api.common.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * クラス名：<br>
 * SJIS判定。<br>
 * <br>
 * 機能説明：<br>
 * SJIS判定を行う<br>
 * <br>
 * </p>
 */
public class SjisSafeUtility {

	/** 代替文字 */
	public static final Map<String, String> ALTERNATES;
	static {
		ALTERNATES = new HashMap<>();
		// ALTERNATES.put("－", "ー");
	}

	/**
	 * 代替判定
	 *
	 * @param source 対象文字列
	 * @return 判定結果
	 */
	public static boolean hasAlternate(String source) {
		return ALTERNATES.containsKey(source);
	}

	/**
	 * 代替置換
	 *
	 * @param source 対象文字列
	 * @return 置換結果
	 */
	public static String getAlternate(String source) {
		String alternate = source;
		for (Entry<String, String> e : ALTERNATES.entrySet()) {
			alternate = alternate.replaceAll(e.getKey(), e.getValue());
		}
		return alternate;
	}

}
