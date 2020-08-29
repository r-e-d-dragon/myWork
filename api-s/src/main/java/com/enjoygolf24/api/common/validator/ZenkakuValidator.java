
package com.enjoygolf24.api.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.Zenkaku;



public class ZenkakuValidator implements ConstraintValidator<Zenkaku, String> {

    /** 最大文字数 */
    private int max;

    /**
     * 全角チェック
     *
     * @param value チェック文字列
     * @return チェック結果
     */
    public static boolean isZenkaku(String value) {
        return value.matches("^[^ -~｡-ﾟ]+$");
    }

    @Override
    public void initialize(Zenkaku parameters) {
        max = parameters.max();
        validateParameters();
    }

    /**
     * チェック
     *
     * @param value   入力値
     * @param context コンテナ
     * @return チェック結果
     */
    @Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        if (!isZenkaku(value)) {
            return false;
        }

        return (value.length() <= max);
    }

    /**
     * パラメータチェック
     */
    private void validateParameters() {
        if (max < 0) {
            throw new IllegalArgumentException("max は 0 以上で定義");
        }
    }

}
