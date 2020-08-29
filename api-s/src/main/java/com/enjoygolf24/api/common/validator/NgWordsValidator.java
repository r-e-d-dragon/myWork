
package com.enjoygolf24.api.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.NgWords;

/**
 * <p>
 * クラス名：<br>
 * NGワードバリデータ<br>
 * <br>
 * 機能説明：<br>
 * NGワードバリデータ<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
public class NgWordsValidator implements ConstraintValidator<NgWords, String> {

	/** NGワードリポジトリ */
	// @Autowired
	// private NgWordRepository ngWordRepository;

	/** message.properties */
	@Autowired
	private MessageSource msg;

	/**
	 * チェック
	 *
	 * @param value   入力値
	 * @param context バリデータコンテクスト
	 * @return チェック結果
	 */
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		// List<NgWord> ngWords = ngWordRepository.findAll();
//		List<String> boo = ngWords.stream().filter(e -> value.contains(e.getWord())).map(e -> "「" + e.getWord() + "」")
//		        .collect(Collectors.toList());
//		if (boo.size() > 0) {
//			String fieldName = getFieldName(context); // disableDefaultConstraintViolation 以前に取得する必要有
//			context.disableDefaultConstraintViolation();
//			context.buildConstraintViolationWithTemplate(
//			        String.format("%s：こ%sの言葉は使用できません%s", fieldName, boo.size() == 1 ? "" : "れら", String.join("、", boo)))
//			        .addConstraintViolation();
//			return false;
//		}

		return true;
	}

	/**
	 * フィールド名取得
	 *
	 * @param context バリデータコンテクスト
	 * @return フィールド名
	 */
	private String getFieldName(ConstraintValidatorContext context) {
		String fieldName = ((ConstraintValidatorContextImpl) context).getConstraintViolationCreationContexts().get(0)
		        .getPath().toString();
		String fieldNameLocale = msg.getMessage(fieldName, null, null);
		return fieldNameLocale;
	}

}
