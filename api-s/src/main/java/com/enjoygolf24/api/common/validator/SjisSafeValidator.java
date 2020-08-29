
package com.enjoygolf24.api.common.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.constants.MicroSoft;
import com.enjoygolf24.api.common.utility.SjisSafeUtility;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;

/**
 * <p>
 * クラス名：<br>
 * SJISバリデータ<br>
 * <br>
 * 機能説明：<br>
 * SJISバリデータ<br>
 * <br>
 * </p>
 *
 */
public class SjisSafeValidator implements ConstraintValidator<SjisSafe, String> {

	// SJIS 変換可能だが入力不可とする
	private static final List<Character> EXCLUDES;
	static {
		EXCLUDES = new ArrayList<>();
		EXCLUDES.add('―');
		EXCLUDES.add('∥');
		EXCLUDES.add('￤');
	}

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

		value.toCharArray();
		Arrays.asList(value.toCharArray());

		List<String> boo = toCharacterStream(value).filter(e -> !isSafe(e)).map(e -> e.toString())
		        .collect(Collectors.toList());
		if (boo.size() > 0) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
			        String.format("こ%sの文字は使用できません[%s]", boo.size() == 1 ? "" : "れら", String.join(" ", boo)))
			        .addConstraintViolation();
			return false;
		}

		return true;
	}

	/**
	 * CharStream変換
	 *
	 * @param source 変換対象
	 * @return CharStream
	 */
	private Stream<Character> toCharacterStream(String source) {
		Stream.Builder<Character> builder = Stream.builder();
		source.chars().forEach(c -> builder.add((char) c));
		return builder.build();
	}

	/**
	 * 文字コード変換可否
	 *
	 * @param source 判定文字
	 * @return 判定結果
	 */
	private boolean isSafe(Character source) {
		boolean isMatchedExcludes = EXCLUDES.stream().anyMatch(e -> e.equals(source));
		if (isMatchedExcludes) {
			return false;
		}

		String orgStr = source.toString();
		byte[] orgByte = orgStr.getBytes(MicroSoft.DEFAULT_CHARSET);
		String revStr = new String(orgByte, MicroSoft.DEFAULT_CHARSET);
		if (orgStr.equals(revStr)) {
			return true;
		} else {
			return SjisSafeUtility.hasAlternate(orgStr);
		}
	}

}
