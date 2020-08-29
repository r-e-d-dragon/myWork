
package com.enjoygolf24.api.common.utility;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class DefaultPageSizeUtility {

	/** 最初ページ */
	public static final int PAGE_FIRST = 1;

	/** デフォルトページサイズ */
	public static final int DEFAULT_PAGE_SIZE = 20;

	/** モバイルデフォルトページサイズ */
	public static final int MOBILE_PURCHASE_HISTORY_DEFAULT_PAGE_SIZE = 10;

	/** モバイルデフォルトページサイズ */
	public static final int MOBILE_DEFAULT_PAGE_SIZE = 100;

	/** モバイルバリューコードページサイズ */
	public static final int MOBILE_VALUE_CODE_DEFAULT_PAGE_SIZE = 100;

	/** イメージリストデフォルトページサイズ */
	public static final int CLIPART_DEFAULT_PAGE_SIZE = 30;

	/** ページリスト */
	public static final Integer[] PAGE_LIST = { Integer.valueOf(DEFAULT_PAGE_SIZE), Integer.valueOf(50),
	        Integer.valueOf(100) };

	/**
	 * ページサイズオプション表示
	 * 
	 * @return ページサイズオプション
	 */
	public static LinkedHashMap<String, Integer> createPageSizeItems() {
		LinkedHashMap<String, Integer> pageSizeItems = new LinkedHashMap<>();
		Arrays.stream(PAGE_LIST).forEach(e -> pageSizeItems.put(e.toString(), e));
		return pageSizeItems;
	}

}
