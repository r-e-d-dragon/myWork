
package com.enjoygolf24.api.common.validator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.internal.constraintvalidators.AbstractEmailValidator;
import org.hibernate.validator.internal.util.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enjoygolf24.api.common.validator.annotation.Email;

public class EmailValidator extends AbstractEmailValidator<Email> {

	/** ロガー */
	private static final Log LOG = org.hibernate.validator.internal.util.logging.LoggerFactory
			.make(MethodHandles.lookup());

	/** エラーロガー */
	private static final Logger errorLogger = LoggerFactory.getLogger("errorLogger");

	/** パターン */
	private java.util.regex.Pattern pattern;

	/** Eメールチェック */
	private static final String regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	@Override
	public void initialize(Email emailAnnotation) {
		super.initialize(emailAnnotation);

		Pattern.Flag[] flags = emailAnnotation.flags();
		int intFlag = 0;
		for (Pattern.Flag flag : flags) {
			intFlag = intFlag | flag.getValue();
		}

		// we only apply the regexp if there is one to apply
		if (!".*".equals(emailAnnotation.regexp()) || emailAnnotation.flags().length > 0) {
			try {
				pattern = java.util.regex.Pattern.compile(emailAnnotation.regexp(), intFlag);
			} catch (PatternSyntaxException e) {

				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();

				StringBuilder errorBuilder = new StringBuilder().append("Error Log :").append("Exception :")
						.append(exceptionAsString);

				errorLogger.error(errorBuilder.toString());

				throw LOG.getInvalidRegularExpressionException(e);
			}
		}
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null || value == "") {
			return true;
		}

		// boolean isValid = super.isValid(value, context);
		if (pattern == null) {
			pattern = java.util.regex.Pattern.compile(regexp);
		}

		Matcher m = pattern.matcher(value);
		return m.matches();
	}
}
