
package com.enjoygolf24.api.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.OnOff;

public class OnOffValidator implements ConstraintValidator<OnOff, String> {

	private static final String PATTERN = "^[0-1]{1}$";

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		return value.matches(PATTERN);
	}

}
