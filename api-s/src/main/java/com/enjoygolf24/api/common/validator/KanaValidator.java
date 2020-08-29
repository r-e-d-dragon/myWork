
package com.enjoygolf24.api.common.validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.Kana;

/**
 * <p>
 * クラス名：<br>
 * 全角バリデータ<br>
 * <br>
 * 機能説明：<br>
 * 全角バリデータ<br>
 * 名称と実態が乖離しているが、入力可否は以下のとおり<br>
 * 〇全角<br>
 * ・漢字：NG<br>
 * ・ひらがな：NG<br>
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
public class KanaValidator implements ConstraintValidator<Kana, String> {

	/** 全角漢字 */
	private static final String ZENAKU_KANJI = "[一-龥]";

	/** 全角ひらがな */
	private static final String ZENAKU_HIRAGANA = "[ぁ-ん]";

	/** 半角カタカナ */
	private static final String HANKAKU_KATAKANA = "[ｦ-ﾟ]";

	/** 半角記号：「( ) -」を除く */
	private static final String HANKAKU_SIGN = "[｡｢｣､･!-'*-,.-/:-@\\[-`\\{-\\~]";

	/** 使用不可 */
	private static final LinkedHashMap<String, Pattern> EXCLUDES;
	static {
		EXCLUDES = new LinkedHashMap<>();
		EXCLUDES.put("漢字", Pattern.compile(ZENAKU_KANJI));
		EXCLUDES.put("ひらがな", Pattern.compile(ZENAKU_HIRAGANA));
		EXCLUDES.put("半角カナ", Pattern.compile(HANKAKU_KATAKANA));
		EXCLUDES.put("半角記号 ( ) - を除く", Pattern.compile(HANKAKU_SIGN));
	}

	/** 最大文字数 */
	private int max;

	@Override
	public void initialize(Kana parameters) {
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
