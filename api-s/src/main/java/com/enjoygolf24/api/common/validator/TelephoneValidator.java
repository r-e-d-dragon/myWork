
package com.enjoygolf24.api.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.Telephone;

/**
 * <p>
 * クラス名：<br>
 * バリデータ<br>
 * <br>
 * 機能説明：<br>
 * 電話番号バリデータ<br>
 * <br>
 * 電話番号
 * </p>
 */
public class TelephoneValidator implements ConstraintValidator<Telephone, String> {

	/** チェックパターン */
	private static final String PATTERN = "^0\\d{1,4}-\\d{1,4}-\\d{4}$";

	/**
	 * チェック
	 *
	 * @param value   入力値
	 * @param context コンテナ
	 * @return チェック結果
	 */
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		return value.matches(PATTERN);
	}

}
