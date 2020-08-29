
package com.enjoygolf24.api.common.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.enjoygolf24.api.common.validator.CodeMasterValidator;

@Documented
@Constraint(validatedBy = { CodeMasterValidator.class })
@Target({ FIELD })
@Retention(RUNTIME)
public @interface CodeMaster {

	/**
	 * @return エラーメッセージ
	 */
	String message() default "{application.combination.validation.cd}";

	/**
	 * @return グループ
	 */
	Class<?>[] groups() default {};

	/**
	 * @return ペイロード
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * @return 設定コード
	 *
	 */
	String code() default "";

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
		CodeMaster[] value();
	}

	String[] except() default {};

}
