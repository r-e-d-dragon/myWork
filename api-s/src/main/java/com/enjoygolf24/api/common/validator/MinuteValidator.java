
package com.enjoygolf24.api.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.enjoygolf24.api.common.constants.TimeConstants;
import com.enjoygolf24.api.common.validator.annotation.Minute;

/**
 * <p>
 * クラス名：<br>
 * 分バリデータ<br>
 * <br>
 * 機能説明：<br>
 * 分バリデータ<br>
 * <br>
 * </p>
 *
 * @since 2019/4
 *
 * @version 1.00 2次開発
 */
public class MinuteValidator implements ConstraintValidator<Minute, Integer> {

	/**
	 * チェック
	 *
	 * @param value   入力値
	 * @param context バリデータコンテクスト
	 * @return チェック結果
	 */
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		boolean result = (TimeConstants.MINUTE_MIN <= value.intValue() && value.intValue() <= TimeConstants.MINUTE_MAX);
		return result;
	}

}
