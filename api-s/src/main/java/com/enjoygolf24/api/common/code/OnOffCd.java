package com.enjoygolf24.api.common.code;

public class OnOffCd {

	/** ON */
	public static final String ON = "1";

	/** OFF */
	public static final String OFF = "0";

	public static boolean isOn(String value) {
		return (ON.compareTo(value) == 0);
	}

}
