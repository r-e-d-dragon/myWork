
package com.enjoygolf24.api.common.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.enjoygolf24.api.common.validator.HourValidator;

/**
 * <p>
 * クラス名：<br>
 * 時バリデータアノテーション<br>
 * <br>
 * 機能説明：<br>
 * 時バリデータのアノテーション<br>
 * <br>
 * </p>
 */
@Documented
@Constraint(validatedBy = { HourValidator.class })
@Target({ FIELD })
@Retention(RUNTIME)
public @interface Hour {

	/**
	 * @return エラーメッセージ
	 */
	String message() default "{application.combination.validation.hour}";

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
		Hour[] value();
	}

}
