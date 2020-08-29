
package com.enjoygolf24.api.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.Hankaku;

/**
 * <p>
 * クラス名：<br>
 * 半角バリデータ<br>
 * <br>
 * 機能説明：<br>
 * 半角バリデータ<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
public class HankakuValidator implements ConstraintValidator<Hankaku, String> {

	/** 最大文字数 */
	private int max;

	/**
	 * 全角チェック
	 *
	 * @param value チェック文字列
	 * @return チェック結果
	 */
	public static boolean isHankaku(String value) {
		return value.matches("^[ -~]+$");
	}

	@Override
	public void initialize(Hankaku parameters) {
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

		if (!isHankaku(value)) {
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
