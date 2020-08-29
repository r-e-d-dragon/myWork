package com.enjoygolf24.api.common.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.validator.annotation.PageSize;

/**
 * <p>
 * クラス名：<br>
 * ページサイズバリデータ<br>
 * <br>
 * 機能説明：<br>
 * ページサイズバリデータ<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
public class PageSizeValidator implements ConstraintValidator<PageSize, Object> {

	/**
	 * チェック
	 *
	 * @param value   入力値
	 * @param context コンテナ
	 * @return チェック結果
	 */
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		if (!(value instanceof Integer)) {
			return false;
		}
		final Integer intValue = new Integer(value.toString());
		boolean result = Arrays.stream(DefaultPageSizeUtility.PAGE_LIST).anyMatch(e -> e.compareTo(intValue) == 0);
		return result;
	}

}
