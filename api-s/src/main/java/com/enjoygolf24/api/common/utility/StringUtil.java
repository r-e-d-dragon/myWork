package com.enjoygolf24.api.common.utility;

public class StringUtil {

    public static boolean isEmpty(String value) {

        if (value == null || value.isEmpty()) {
            return true;
        }
        return false;
    }

}
