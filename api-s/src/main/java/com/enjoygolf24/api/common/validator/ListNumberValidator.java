
package com.enjoygolf24.api.common.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.enjoygolf24.api.common.validator.annotation.NumberList;

/**
 * <p>
 * クラス名：<br>
 * 数値リストバリデータ<br>
 * <br>
 * 機能説明：<br>
 * 数値リストバリデータ<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
public class ListNumberValidator implements ConstraintValidator<NumberList, List<Integer>> {

	/** 最大文字数 */
	private int max;

	/** 最小文字数 */
	private int min;

	@Override
	public void initialize(NumberList parameters) {
		max = parameters.max();
		min = parameters.min();
		validateParameters();
	}

	/**
	 * チェック
	 *
	 * @param numbers リスト入力値
	 * @param context コンテナ
	 * @return チェック結果
	 */
	public boolean isValid(List<Integer> numbers, ConstraintValidatorContext context) {

		for (Integer number : numbers) {
			if (number == null) {
				return false;
			}

			if (number.intValue() < min || number.intValue() > max) {
				return false;
			}
		}

		return true;
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
