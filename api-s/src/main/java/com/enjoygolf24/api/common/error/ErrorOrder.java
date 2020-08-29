
package com.enjoygolf24.api.common.error;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.supercsv.cellprocessor.ift.CellProcessor;

import com.github.mygreen.supercsv.builder.ProcessorBuilder;

/**
 * 例外表示順を表現するためのアノテーションです。
 * <p>フィールドに付与します。</p>
 *
 * <h3 class="description">基本的な使い方</h3>
 * <ul>
 *   <li>属性{@link #order()}でカラムの番号を指定します。
 *     <br>値は1から始まります。
 *   </li>
 *   <li>ヘッダー行が存在する場合、属性{@link #label()}で見出しの値を指定します。
 *     <br>省略した場合、フィールド名が適用されます。
 *   </li>
 * </ul>
 *
 * <pre class="highlight"><code class="java">
 * {@literal @CsvBean}
 * public class SampleForm {
 *
 *     {@literal @ErrorDisplayOrder(order=1)}
 *     private int no;
 *
 *     // getter/setterは省略
 * }
 * </code></pre>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ErrorOrder {

    /**
     * 列番号を指定します。
     * <p>他のカラムの値との重複は許可しません。</p>
     * @return 番号は1から始まります。
     */
    int order() default 0;

    /**
     * 独自の{@link ProcessorBuilder}を指定して{@link CellProcessor} を組み立てたい場合に指定します。
     * <p>サポートしていないクラスタイプに対応するときなどに指定します。</p>
     * @return {@link ProcessorBuilder}を実装したクラスを指定します。
     */
    @SuppressWarnings("rawtypes")
    Class<? extends ProcessorBuilder>[] builder() default {};
}
