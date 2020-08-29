
package com.enjoygolf24.api.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.Zip2;

public class Zip2Validator implements ConstraintValidator<Zip2, String> {

	private static final String PATTERN = "^[0-9]{4}$";

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		return value.matches(PATTERN);
	}

}
