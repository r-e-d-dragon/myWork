
package com.enjoygolf24.api.common.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.enjoygolf24.api.common.validator.KanjiValidator;

/**
 * <p>
 * クラス名：<br>
 * 全角バリデータアノテーション<br>
 * <br>
 * 機能説明：<br>
 * 全角バリデータのアノテーション<br>
 * <br>
 * </p>
 */
@Documented
@Constraint(validatedBy = { KanjiValidator.class })
@Target({ FIELD })
@Retention(RUNTIME)
public @interface Kanji {

	/**
	 * @return エラーメッセージ
	 */
	String message() default "{application.combination.validation.kanji}";

	/**
	 * @return グループ
	 */
	Class<?>[] groups() default {};

	/**
	 * @return ペイロード
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * @return 最大文字数
	 */
	int max() default Integer.MAX_VALUE;

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
		Kanji[] value();
	}

}
