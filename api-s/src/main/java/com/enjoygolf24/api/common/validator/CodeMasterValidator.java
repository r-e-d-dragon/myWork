
package com.enjoygolf24.api.common.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.CodeMaster;
import com.enjoygolf24.api.service.CdMapService;

public class CodeMasterValidator implements ConstraintValidator<CodeMaster, String> {

	/** CDマップサービス */
	@Autowired
	private CdMapService cdMapService;

	/** コード */
	private String code;

	private String[] except;

	@Override
	public void initialize(CodeMaster parameters) {
		code = parameters.code();
		except = parameters.except();
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

		if (except.length != 0) {
			List<String> valuetList = Arrays.asList(value);
			List<String> exceptList = Arrays.asList(except);
			boolean contain = false;

			for (String element : valuetList) {
				if (exceptList.contains(element)) {
					contain = true;
				}
				if (contain) {
					return false;
				}
			}
		}

		boolean result = cdMapService.createMap(code).containsKey(value);
		return result;
	}

}
