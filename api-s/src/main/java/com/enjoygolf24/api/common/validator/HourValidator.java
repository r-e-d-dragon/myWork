
package com.enjoygolf24.api.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.enjoygolf24.api.common.constants.TimeConstants;
import com.enjoygolf24.api.common.validator.annotation.Hour;

/**
 * <p>
 * クラス名：<br>
 * 時バリデータ<br>
 * <br>
 * 機能説明：<br>
 * 時バリデータ<br>
 * <br>
 * </p>
 *
 * @since 2019/4
 *
 * @version 1.00 2次開発
 */
public class HourValidator implements ConstraintValidator<Hour, Integer> {

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

		boolean result = (TimeConstants.HOUR_MIN <= value.intValue() && value.intValue() <= TimeConstants.HOUR_MAX);
		return result;
	}

}
