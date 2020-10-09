
package com.enjoygolf24.api.common.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.ReservationDate;

public class ReservationDateValidator implements ConstraintValidator<ReservationDate, String> {

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

		value = value.replace('-', '/');

		Calendar maxDate = Calendar.getInstance();
		maxDate.add(Calendar.MONTH, +1);
		maxDate.set(Calendar.DAY_OF_MONTH, maxDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		setTimeZero(maxDate);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		Calendar today = Calendar.getInstance();
		setTimeZero(today);

		Calendar c = Calendar.getInstance();
		c.setTime(convertedDate);
		setTimeZero(c);

		return (c.compareTo(maxDate) <= 0 && c.compareTo(today) >= 0);
	}

	public Calendar setTimeZero(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

}
