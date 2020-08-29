
package com.enjoygolf24.api.common.utility;

import java.math.BigDecimal;

public class CalculateUtility {

    /**
     * 割り算を行う。
     * 
     * @param value1 計算元金額
     * @param value2 率（Integer）
     * @return 計算結果（BigDecimal）
     */
    public static BigDecimal devideByPercent(int value1, BigDecimal value2) {

        value2 = value2.divide(new BigDecimal(100));
        BigDecimal result = new BigDecimal(value1).divide(value2);
        result = result.setScale(0, BigDecimal.ROUND_HALF_UP);

        return result;
    }

    /**
     * 割り算を行う。
     * 
     * @param value1 計算元金額
     * @param value2 率（Integer）
     * @return 計算結果（BigDecimal）
     */
    public static BigDecimal devideByPercent(BigDecimal value1, BigDecimal value2) {

        value2 = value2.divide(new BigDecimal(100));
        BigDecimal result = value1.divide(value2);
        result = result.setScale(0, BigDecimal.ROUND_HALF_UP);

        return result;
    }

    /**
     * 掛け算を行う。
     * 
     * @param value1 計算元金額
     * @param value2 率（Integer）
     * @return 計算結果（BigDecimal）
     */
    public static BigDecimal multipulByPercent(Long value1, BigDecimal value2) {

        value2 = value2.divide(new BigDecimal(100));
        BigDecimal result = new BigDecimal(value1).multiply(value2);
        result = result.setScale(0, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * 掛け算を行う。
     * 
     * @param value1 計算元金額
     * @param value2 率（Integer）
     * @return 計算結果（BigDecimal）
     */
    public static BigDecimal multipulByPercent(int value1, BigDecimal value2) {

        value2 = value2.divide(new BigDecimal(100));
        BigDecimal result = new BigDecimal(value1).multiply(value2);
        result = result.setScale(0, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * 掛け算を行う。
     * 
     * @param value1 計算元金額
     * @param value2 率（Integer）
     * @return 計算結果（BigDecimal）
     */
    public static BigDecimal multipulByPercent(BigDecimal value1, BigDecimal value2) {

        value2 = value2.divide(new BigDecimal(100));
        BigDecimal result = value1.multiply(value2);
        result = result.setScale(0, BigDecimal.ROUND_HALF_UP);

        return result;
    }

    /**
     * 素数店以下を四捨五入してINTEGERに変換する。
     * 
     * @param value1 BIGDECIMAL
     * @return Long
     */
    public static int convertToInt(BigDecimal value1) {

        value1 = value1.setScale(0, BigDecimal.ROUND_HALF_UP);

        return value1.intValue();
    }

    /**
     * 素数店以下を四捨五入してINTEGERに変換する。
     * 
     * @param value1 BIGDECIMAL
     * @return Long
     */
    public static long convertToLong(BigDecimal value1) {

        value1 = value1.setScale(0, BigDecimal.ROUND_HALF_UP);

        return value1.longValue();
    }

    /**
     * 文字列に変換する。
     * 
     * @param value1 BIGDECIMAL
     * @return String
     */
    public static String convertToString(BigDecimal value1) {
        return value1.toPlainString();
    }
}
