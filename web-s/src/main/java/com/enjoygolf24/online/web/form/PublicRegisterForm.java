package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;
import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.validator.annotation.Email;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.Kana;
import com.enjoygolf24.api.common.validator.annotation.Kanji;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;
import com.enjoygolf24.api.common.validator.annotation.Telephone;
import com.enjoygolf24.api.common.validator.annotation.UniqueEmail;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.service.bean.PreMemberRegisterServiceBean;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class PublicRegisterForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@Hankaku(groups = Insert0.class, max = 20)
	private String aspCode;

	@NotBlank(groups = Insert0.class)
	@Size(groups = Insert0.class, max = 80)
	@Email(groups = Insert0.class)
	@UniqueEmail(groups = Insert0.class)
	private String email;

	@NotBlank(groups = Insert0.class)
	@Size(groups = Insert0.class, max = 80)
	@Email(groups = Insert0.class)
	private String emailConfirm;

	@NotBlank(groups = Insert0.class)
	@Kanji(groups = Insert0.class, max = 80)
	@SjisSafe(groups = Insert0.class)
	private String firstName;

	@NotBlank(groups = Insert0.class)
	@Kana(groups = Insert0.class, max = 80)
	@SjisSafe(groups = Insert0.class)
	private String firstNameKana;

	@NotBlank(groups = Insert0.class)
	@Kanji(groups = Insert0.class, max = 80)
	@SjisSafe(groups = Insert0.class)
	private String lastName;

	@NotBlank(groups = Insert0.class)
	@Kana(groups = Insert0.class, max = 80)
	@SjisSafe(groups = Insert0.class)
	private String lastNameKana;

	@NotBlank(groups = Insert0.class)
	@Telephone(groups = Insert0.class)
	private String phone;

	@Size(groups = Insert0.class, max = 1200)
	private String memo;

	private String hasChanged;

	private String loginUserCd;

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.needSameEmailAndEmailConfirm}")
	public boolean isSameEmailAndEmailConfirm() {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(emailConfirm)) {
			return true;
		}
		return email.equals(emailConfirm);
	}

	public PreMemberRegisterServiceBean createPreMemberRegisterServiceBean() {
		ModelMapper modelMapper = new ModelMapper();
		PreMemberRegisterServiceBean serviceBean = modelMapper.map(this, PreMemberRegisterServiceBean.class);

		return serviceBean;
	}

}
