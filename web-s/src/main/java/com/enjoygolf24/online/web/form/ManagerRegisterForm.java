package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;
import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.validator.annotation.CodeMaster;
import com.enjoygolf24.api.common.validator.annotation.Email;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.Kana;
import com.enjoygolf24.api.common.validator.annotation.Kanji;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;
import com.enjoygolf24.api.common.validator.annotation.Telephone;
import com.enjoygolf24.api.common.validator.annotation.UniqueEmail;
import com.enjoygolf24.api.common.validator.annotation.Zip1;
import com.enjoygolf24.api.common.validator.annotation.Zip2;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.common.validator.groups.Insert1;
import com.enjoygolf24.api.service.bean.ManagerRegisterServiceBean;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class ManagerRegisterForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@Kanji(groups = Insert0.class, max = 200)
	@SjisSafe(groups = Insert0.class)
	private String address1;

	@Kanji(groups = Insert0.class, max = 200)
	@SjisSafe(groups = Insert0.class)
	private String address2;

	@Hankaku(groups = Insert0.class, max = 20)
	private String aspCode;

	@NotBlank(groups = Insert0.class)
	@Size(groups = Insert0.class, max = 80)
	@Email(groups = Insert0.class)
	@UniqueEmail(groups = Insert1.class)
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

	@Size(groups = Insert0.class, max = 3)
	@Zip1(groups = Insert0.class)
	private String zip1;

	@Size(groups = Insert0.class, max = 4)
	@Zip2(groups = Insert0.class)
	private String zip2;

	@NotBlank(groups = Insert0.class)
	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.AUTH_TYPE_CD)
	private String authTypeCd;

	@NotBlank(groups = Insert0.class)
	private String loginUserCd;

	private String hasChanged;

	@AssertTrue(groups = Insert1.class, message = "{application.combination.validation.needSameEmailAndEmailConfirm}")
	public boolean isSameEmailAndEmailConfirm() {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(emailConfirm)) {
			return true;
		}
		return email.equals(emailConfirm);
	}

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.needNotBlankZipCodeAndAddress}")
	public boolean isFilledZipCdAndAddress() {

		if (StringUtils.isEmptyOrWhitespace(zip1) && StringUtils.isEmptyOrWhitespace(zip2)
				&& StringUtils.isEmptyOrWhitespace(address1)) {
			return true;
		} else if (!StringUtils.isEmptyOrWhitespace(zip1) && !StringUtils.isEmptyOrWhitespace(zip2)
				&& !StringUtils.isEmptyOrWhitespace(address1)) {
			return true;
		} else {
			return false;
		}
	}


	public ManagerRegisterServiceBean createManagerRegisterServiceBean() {
		ModelMapper modelMapper = new ModelMapper();

		ManagerRegisterServiceBean serviceBean = modelMapper.map(this, ManagerRegisterServiceBean.class);

		return serviceBean;
	}

}
