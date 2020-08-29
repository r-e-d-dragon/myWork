
package com.enjoygolf24.api.common.validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.Kanji;

/**
 * <p>
 * クラス名：<br>
 * 全角バリデータ<br>
 * <br>
 * 機能説明：<br>
 * 全角バリデータ<br>
 * 名称と実態が乖離しているが、入力可否は以下のとおり<br>
 * 〇全角<br>
 * ・漢字：OK<br>
 * ・ひらがな：OK<br>
 * ・カタカナ：OK<br>
 * ・アルファベット：OK<br>
 * ・数字：OK<br>
 * ・記号：OK<br>
 * 〇半角 ・カタカナ：NG<br>
 * ・アルファベット：OK<br>
 * ・数字：OK<br>
 * ・記号：NG<br>
 * <br>
 * </p>
 */
public class KanjiValidator implements ConstraintValidator<Kanji, String> {

	/** 半角カタカナ */
	private static final String HANKAKU_KATAKANA = "[ｦ-ﾟ]";

	/** 半角記号：「( ) -」を除く */
	private static final String HANKAKU_SIGN = "[｡｢｣､･!-'*-,.-/:-@\\[-`\\{-\\~]";

	/** 使用不可 */
	private static final LinkedHashMap<String, Pattern> EXCLUDES;
	static {
		EXCLUDES = new LinkedHashMap<>();
		EXCLUDES.put("半角カナ", Pattern.compile(HANKAKU_KATAKANA));
		EXCLUDES.put("半角記号 ( ) - を除く", Pattern.compile(HANKAKU_SIGN));
	}

	/** 最大文字数 */
	private int max;

	@Override
	public void initialize(Kanji parameters) {
		max = parameters.max();
		validateParameters();
	}

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

		if ((value.length() > max)) {
			return false;
		}

		List<String> unusables = EXCLUDES.entrySet().stream().filter(e -> e.getValue().matcher(value).find())
		        .map(e -> e.getKey()).collect(Collectors.toList());
		return (unusables.size() == 0);
	}

	/**
	 * パラメータチェック
	 */
	private void validateParameters() {
		if (max < 0) {
			throw new IllegalArgumentException("max は 0 以上で定義");
		}
	}

}
