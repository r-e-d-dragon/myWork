
package com.enjoygolf24.api.common.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.MultiEmail;

/**
 * <p>
 * クラス名：<br>
 * 複数Eメールアドレスバリデータ<br>
 * <br>
 * 機能説明：<br>
 * 複数Eメールアドレスバリデータ<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
public class MultiEmailValidator implements ConstraintValidator<MultiEmail, String> {

	/** 複数Eメールアドレス入力パターン */
	private static final String PATTERN = "^[^| ]+(\\|[^| ]+)*$";

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

		if (!value.matches(PATTERN)) {
			return false;
		}

		// 全て Eメール 形式であること
		String[] emails = value.split("\\|");
		if (emails.length == 0) {
			return false;
		}
		EmailValidator emailValidator = new EmailValidator();
		if (!Arrays.asList(emails).stream().allMatch(e -> emailValidator.isValid(e, context))) {
			return false;
		}

		return true;
	}

}
