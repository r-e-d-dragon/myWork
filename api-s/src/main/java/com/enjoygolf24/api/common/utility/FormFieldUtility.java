
package com.enjoygolf24.api.common.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enjoygolf24.api.common.error.ErrorOrder;

public class FormFieldUtility {

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(FormFieldUtility.class);

	/** フォーム表示順マップ */
	private static Map<String, Map<String, Integer>> formFieldOrderMap = new HashMap<String, Map<String, Integer>>();

	/**
	 * マップにフォーム名が存在するかチェックする。
	 * 
	 * @param formName フォーム名
	 * @return boolean true/false
	 */
	public static synchronized boolean exist(String formName) {
		return formFieldOrderMap.containsKey(formName);
	}

	/**
	 * フォーム名ごとに初期化する
	 * 
	 * @param formName フォーム名
	 * @param fields   Field配列
	 * @param methods  Method配列
	 */
	public static synchronized void init(String formName, Field[] fields, Method[] methods) {

		if (fields == null || exist(formName)) {
			return;
		}

		Map<String, Integer> orderMap = new HashMap<String, Integer>();
		int index = 1;
		for (Field field : fields) {
			ErrorOrder displayOrder = field.getAnnotation(ErrorOrder.class);
			if (displayOrder == null) {
				orderMap.put(field.getName(), index * 10);
			} else {
				orderMap.put(field.getName(), displayOrder.order() * 10);
			}
			index++;
		}

		int methodIndex = 1001;
		for (Method method : methods) {
			ErrorOrder displayOrder = method.getAnnotation(ErrorOrder.class);
			if (displayOrder != null) {
				orderMap.put(method.getName(), (displayOrder.order() * 10) + 1);
			} else {
				orderMap.put(method.getName(), methodIndex);
			}
			methodIndex++;
		}

		formFieldOrderMap.put(formName, orderMap);

	}

	/**
	 * フォーム名をもとに定義順番を取得する。
	 * 
	 * @param formName  フォーム名
	 * @param fieldName フィールド名
	 * @return フィールド定義順番
	 */
	public static int getOrder(String formName, String fieldName) {

		int index = -1;
		if (exist(formName)) {
			// isXXXX() Method対応
			if (formFieldOrderMap.get(formName).get(fieldName) == null) {
				logger.info(fieldName);
				fieldName = "is" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
			}

			if (formFieldOrderMap.get(formName).get(fieldName) == null) {
				logger.info(fieldName);
				return -1;
			}
			index = formFieldOrderMap.get(formName).get(fieldName);
		}
		return index;
	}

	/**
	 * マップにフィールド名が存在するかチェックする。
	 * 
	 * @param formName  フォーム名
	 * @param fieldName フィールド名
	 * @return boolean true/false
	 */
	public static boolean isExistField(String formName, String fieldName) {

		if (!formFieldOrderMap.containsKey(formName)) {
			return false;
		}

		if (!formFieldOrderMap.get(formName).containsKey(fieldName)) {
			return false;
		}

		return true;
	}
}
