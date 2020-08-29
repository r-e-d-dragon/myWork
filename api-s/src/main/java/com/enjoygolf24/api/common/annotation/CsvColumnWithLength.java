
package com.enjoygolf24.api.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * クラス名：<br>
 * CSVデータ用アノテーション<br>
 * <br>
 * 機能説明：<br>
 * CSVデータを定義する<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CsvColumnWithLength {

	/**
	 * 順番
	 * 
	 * @return CSVの出力順を1から指定
	 */
	int number() default 0;

	/**
	 * サイズ（文字数）
	 * 
	 * @return 全半角関係なく文字数を指定
	 */
	int length() default 0;

}
