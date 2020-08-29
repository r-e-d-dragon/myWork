
package com.enjoygolf24.api.common.utility;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * クラス名：<br>
 * URI文字列のチェックを行う。<br>
 * <br>
 * 機能説明：<br>
 * URI文字列のチェックを行う。<br>
 * <br>
 * </p>
 *
 */
public class URICheckUtility {

	private static final Logger logger = LoggerFactory.getLogger(URICheckUtility.class);

	/**
	 * ログを書かないかをチェックする
	 * 
	 * @param uri URI
	 * @return boolean true:ログを書く false:ログを書かない
	 */
	public static boolean isNotChecked(ServletRequest request) {

		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		String uri = httpRequest.getRequestURI();

		return uri.matches(".*\\.(css|ico|js|jpg|jpeg|bmp|png|gif|pdf|jquery.min.map)+$|.*/img/.*|.*/js/.*|.*/login");
	}

	public static boolean isNotCheckedActionUrl(ServletRequest request) {

		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		String uri = httpRequest.getRequestURI();

		return uri.matches(".*\\.(css|ico|js|jpg|jpeg|bmp|png|gif|pdf|jquery.min.map)+$|.*/img/.*|.*/js/.*");
	}

	/**
	 * 指定されたパスを判定する。
	 *
	 * @param uri URI
	 * @return boolean true:ログを書く false:ログを書かない
	 */
	public static boolean isContainsUri(ServletRequest request, String excludeUri) {

		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		String uri = httpRequest.getRequestURI();

		if (!StringUtils.isEmpty(excludeUri)) {
			if (uri.matches(".*" + excludeUri + "*|.*" + excludeUri + "/.*")) {
				logger.info("isNotCheckedManager matches info [" + uri + "]:[" + excludeUri + "] return true");
				return true;
			}
		}

		return false;
	}

	public static String getUri(ServletRequest request) {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		return httpRequest.getRequestURI();
	}
}
