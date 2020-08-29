
package com.enjoygolf24.api.common.error;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class CustomBindResult {

	/** 例外リスト */
	private List<CustomFieldError> errorList;

	/** 表示順 */
	private int makeOrderNo = 10000;

	/**
	 * エラーがあるかチェックを行う。
	 * 
	 * @return boolean true/false
	 */
	public boolean hasAnyErrors() {
		if (errorList != null && !errorList.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * エラーリストを取得する。
	 * 
	 * @return エラーリスト
	 */
	public List<CustomFieldError> detailedErrors() {
		return getAllErrors();
	}

	/**
	 * 例外を追加する。
	 * 
	 * @param orderNo   ソート順
	 * @param fieldName フィールド名
	 * @param message   例外メッセージ
	 * @param arguments 引数
	 * @param codes     例外コード
	 */
	public void addError(int orderNo, String key, String fieldName, String message, Object[] arguments,
			String[] codes) {
		if (errorList == null) {
			errorList = new LinkedList<CustomFieldError>();
		}

		if (orderNo == -1) {
			orderNo = makeOrderNo++;
		}
		CustomFieldError error = new CustomFieldError();
		error.setOrder(orderNo);
		error.setKey(key);
		if (StringUtils.isEmpty(fieldName)) {
			fieldName = key;
		}
		error.setName(fieldName);
		String newMessage = MessageFormat.format(message, fieldName);
		error.setMessage(newMessage);
		error.setArguments(arguments);
		error.setCodes(codes);

		errorList.add(error);
	}

	/**
	 * 例外リストを取得する。
	 * 
	 * @return 例外リスト
	 */
	public List<CustomFieldError> getAllErrors() {

		if (errorList == null) {
			errorList = new LinkedList<CustomFieldError>();
		} else {
			Collections.sort(errorList, new Comparator<CustomFieldError>() {

				@Override
				public int compare(CustomFieldError error1, CustomFieldError error2) {
					return error1.getOrder() > error2.getOrder() ? 1 : (error1.getOrder() < error2.getOrder()) ? -1 : 0;
				}
			});
		}
		return errorList;
	}

}
