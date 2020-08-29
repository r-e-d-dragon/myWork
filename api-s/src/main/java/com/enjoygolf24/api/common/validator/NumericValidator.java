
package com.enjoygolf24.api.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.Numeric;

/**
 * <p>
 * クラス名：<br>
 * 数字バリデータ<br>
 * <br>
 * 機能説明：<br>
 * 数字バリデータ<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
public class NumericValidator implements ConstraintValidator<Numeric, String> {

	/** 最小数値 */
	private int min;

	/** 最大数値 */
	private int max;

	@Override
	public void initialize(Numeric parameters) {
		min = parameters.min();
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
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		if (!value.matches("^(|0|[1-9]\\d*)$")) {
			return false;
		}

		long intValue = Long.valueOf(value);
		return (min <= intValue && intValue <= max);
	}

	/**
	 * パラメータチェック
	 */
	private void validateParameters() {
		if (min < 0) {
			throw new IllegalArgumentException("min は 0 以上で定義");
		}
		if (max < 0) {
			throw new IllegalArgumentException("max は 0 以上で定義");
		}
	}

}
