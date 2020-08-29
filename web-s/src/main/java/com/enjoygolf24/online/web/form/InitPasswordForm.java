package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.Password;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.common.validator.groups.Search0;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class InitPasswordForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@NotBlank(groups = Insert0.class)
	String authKey;

	@NotBlank(groups = Insert0.class)
	@Hankaku(groups = Search0.class, max = 20)
	String memberCode;

	@NotBlank(groups = Insert0.class)
	@Password(groups = Insert0.class)
	String password;

	@NotBlank(groups = Insert0.class)
	@Password(groups = Insert0.class)
	String PasswordConfirm;

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.needSameNewPasswordAndNewPasswordConfirm}")
	public boolean isPasswordAndPasswordConfirm() {
		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(PasswordConfirm)) {
			return true;
		}

		return password.equals(PasswordConfirm);
	}


}
