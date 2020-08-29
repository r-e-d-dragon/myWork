
package com.enjoygolf24.api.common.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.Password;

/**
 * <p>
 * クラス名：<br>
 * パスワードバリデータ<br>
 * <br>
 * 機能説明：<br>
 * パスワードバリデータ<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

	/** 最小サイズ */
	private static final int MIN_LENGTH = 8;

	/** 最大サイズ */
	private static final int MAX_LENGTH = 32;

	/** アルファベット小文字 */
	private static final String SMALL_ALPHABET = "a-z";

	/** アルファベット大文字 */
	private static final String LARGE_ALPHABET = "A-Z";

	/** 数字 */
	private static final String NUMBER = "0-9";

	/** 記号 */
	private static final String SYMBOL = "`˜!@#$%^&*_+\\-=\\\\\\|:;\"'<>,.?/\\(\\){}\\[\\]";

	/** パスワードパターン */
	private static final List<String> PATTERNS = Arrays.asList(
// @formatter:off
	        "^.*[" + SMALL_ALPHABET + "]+.*$", "^.*[" + LARGE_ALPHABET + "]+.*$", "^.*[" + NUMBER + "]+.*$",
	        "^[" + SMALL_ALPHABET + LARGE_ALPHABET + NUMBER + SYMBOL + "]+$" // SYMBOL は任意
// @formatter:on
	);

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

		if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
			return false;
		}
		return PATTERNS.stream().allMatch(e -> value.matches(e));
	}

}
