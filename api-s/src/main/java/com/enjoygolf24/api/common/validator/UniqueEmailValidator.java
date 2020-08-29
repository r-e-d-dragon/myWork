
package com.enjoygolf24.api.common.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.constants.MemberContants;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.common.validator.annotation.UniqueEmail;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private MemberRepository memberRepository;

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		List<TblUser> member = memberRepository.findByEmailAndUseFlagNot(value,
				MemberContants.MEMBER_USE_FLAG_WITHDRAWE);
		if (member.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
