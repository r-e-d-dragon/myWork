
package com.enjoygolf24.api.common.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * クラス名：<br>
 * 文字列変換を行う。<br>
 * <br>
 * 機能説明：<br>
 * 文字列変換を行う<br>
 * <br>
 * </p>
 *
 */
public class StringConvertUtil {

	/**
	 * CRLFをLFに変換する。
	 * 
	 * @param value 変換元文字列
	 * @return 返還後文字列
	 */
	public static String convertToLineFeedOnly(String value) {

		Pattern pt = Pattern.compile("\\n|\\r\\n|\\r");
		Matcher match = pt.matcher(value);

		return match.replaceAll("\\\n");
	}
}
