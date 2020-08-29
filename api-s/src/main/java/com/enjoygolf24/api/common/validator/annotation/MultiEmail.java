
package com.enjoygolf24.api.common.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.enjoygolf24.api.common.validator.MultiEmailValidator;

/**
 * <p>
 * クラス名：<br>
 * 複数Eメールアドレスバリデータアノテーション<br>
 * <br>
 * 機能説明：<br>
 * 複数Eメールアドレスバリデータのアノテーション<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
@Documented
@Constraint(validatedBy = { MultiEmailValidator.class })
@Target({ FIELD })
@Retention(RUNTIME)
public @interface MultiEmail {

	/**
	 * @return エラーメッセージ
	 */
	String message() default "{application.combination.validation.multiEmail}";

	/**
	 * @return グループ
	 */
	Class<?>[] groups() default {};

	/**
	 * @return ペイロード
	 */
	Class<? extends Payload>[] payload() default {};

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
		MultiEmail[] value();
	}

}
