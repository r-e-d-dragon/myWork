
package com.enjoygolf24.api.common.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.enjoygolf24.api.common.validator.ListNumberValidator;

/**
 * <p>
 * クラス名：<br>
 * リスト<数値>の範囲値チェック<br>
 * <br>
 * 機能説明：<br>
 * リスト<数値>の範囲値チェックバリデータのアノテーション<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
@Documented
@Constraint(validatedBy = { ListNumberValidator.class })
@Target({ FIELD })
@Retention(RUNTIME)
public @interface NumberList {

	/**
	 * @return エラーメッセージ
	 */
	String message() default "{application.combination.validation.numberList}";

	/**
	 * @return グループ
	 */
	Class<?>[] groups() default {};

	/**
	 * @return ペイロード
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * @return 最大数値
	 */
	int max() default Integer.MAX_VALUE;

	/**
	 * @return 最小数値
	 */
	int min() default Integer.MIN_VALUE;

	/**
	 * リスト
	 */
	@Target({ FIELD })
	@Retention(RUNTIME)
	@Documented
	public @interface List {

		/**
		 * @return バリデータ
		 */
		NumberList[] value();
	}

}
