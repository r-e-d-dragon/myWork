
package com.enjoygolf24.api.common.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.enjoygolf24.api.common.validator.BankAccountNumberValidator;

@Documented
@Constraint(validatedBy = { BankAccountNumberValidator.class })
@Target({ FIELD })
@Retention(RUNTIME)
public @interface BankAccountNumber {

	/**
	 * @return エラーメッセージ
	 */
	String message() default "{application.combination.validation.bankAccountNumber}";

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
		BankAccountNumber[] value();
	}

}
