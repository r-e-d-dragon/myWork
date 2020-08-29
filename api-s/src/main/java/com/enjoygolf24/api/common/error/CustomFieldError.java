
package com.enjoygolf24.api.common.error;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class CustomFieldError {

	/** 表示順 */
	private int order;

	/** arguments */
	private Object[] arguments;

	/** code */
	private String[] codes;

	/** Form Field名 */
	private String key;

	/** Form Field名(日本語) */
	private String name;

	/** エラーメッセージ */
	private String message;

	/** エラーメッセージ */
	private String detailMessage;

	/**
	 * codeを取得する。
	 * 
	 * @return code
	 */
	public String getCode() {
		return (this.codes != null && this.codes.length > 0 ? this.codes[this.codes.length - 1] : null);
	}

	/**
	 * 詳細メッセージを取得する。
	 * 
	 * @return
	 */
	public String getDetailMessage() {
		detailMessage = message;

		if (!StringUtils.isEmpty(name)) {
			// detailMessage = new StringBuilder().append(name).append(" :
			// ").append(message).toString();
		}

		return detailMessage;
	}
}
