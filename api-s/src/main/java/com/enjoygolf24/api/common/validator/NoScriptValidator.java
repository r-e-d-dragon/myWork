
package com.enjoygolf24.api.common.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.NoScript;


public class NoScriptValidator implements ConstraintValidator<NoScript, String> {

    /** スクリプトタグ */
    private static final Pattern SCRIPT_TAG = Pattern.compile("^.*< */? *script.*$", Pattern.CASE_INSENSITIVE);

    /**
     * チェック
     *
     * @param value   入力値
     * @param context バリデータコンテクスト
     * @return チェック結果
     */
    @Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmptyOrWhitespace(value)) {
            return true;
        }

        boolean result = !SCRIPT_TAG.matcher(value).find();
        return result;
    }

}
