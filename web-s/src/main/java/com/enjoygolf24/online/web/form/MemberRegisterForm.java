package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;
import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.MemberTypeCd;
import com.enjoygolf24.api.common.code.MemberUseFlagCd;
import com.enjoygolf24.api.common.validator.annotation.BankAccountNumber;
import com.enjoygolf24.api.common.validator.annotation.BankBranchCode;
import com.enjoygolf24.api.common.validator.annotation.BankCode;
import com.enjoygolf24.api.common.validator.annotation.CodeMaster;
import com.enjoygolf24.api.common.validator.annotation.Email;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.Kana;
import com.enjoygolf24.api.common.validator.annotation.Kanji;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;
import com.enjoygolf24.api.common.validator.annotation.Telephone;
import com.enjoygolf24.api.common.validator.annotation.UniqueEmail;
import com.enjoygolf24.api.common.validator.annotation.Zenkaku;
import com.enjoygolf24.api.common.validator.annotation.Zip1;
import com.enjoygolf24.api.common.validator.annotation.Zip2;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.service.bean.MemberRegisterServiceBean;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class MemberRegisterForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@Kanji(groups = Insert0.class, max = 200)
	@SjisSafe(groups = Insert0.class)
	private String address1;

	@Kanji(groups = Insert0.class, max = 200)
	@SjisSafe(groups = Insert0.class)
	private String address2;

	@Hankaku(groups = Insert0.class, max = 20)
	private String aspCode;

	@Zenkaku(groups = Insert0.class, max = 100)
	@SjisSafe(groups = Insert0.class)
	private String bankKana;

	@Size(groups = Insert0.class, max = 4)
	@BankCode(groups = Insert0.class)
	private String bankCode;

	@Size(groups = Insert0.class, max = 3)
	@BankBranchCode(groups = Insert0.class)
	private String bankBranchCode;

	@Size(groups = Insert0.class, max = 7)
	@BankAccountNumber(groups = Insert0.class)
	private String bankAccountNumber;

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
	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.MEMBER_TYPE_CD, except = { MemberTypeCd.MANAGER })
	private String memberTypeCd;

	@NotBlank(groups = Insert0.class)
	@Telephone(groups = Insert0.class)
	private String phone;

	@Size(groups = Insert0.class, max = 3)
	@Zip1(groups = Insert0.class)
	private String zip1;

	@Size(groups = Insert0.class, max = 4)
	@Zip2(groups = Insert0.class)
	private String zip2;

	@Size(groups = Insert0.class, max = 1200)
	private String memo;

	@NotBlank(groups = Insert0.class)
	private String loginUserCd;

	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.GENDER_CD)
	private String gender;

	private String birthday;

	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.JOB_CD)
	private String jobCode;

	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.ADDITIONAL_LESSON_CD)
	private String additionalLessonCd;

	@NotBlank(groups = Insert0.class)
	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.MEMBER_USE_FLAG_CD, except = {
			MemberUseFlagCd.MEMBER_USE_FLAG_PRE })
	private String useFlag;

	private String hasChanged;

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.needSameEmailAndEmailConfirm}")
	public boolean isSameEmailAndEmailConfirm() {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(emailConfirm)) {
			return true;
		}
		return email.equals(emailConfirm);
	}

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.needNotBlankWhenNormalMember}")
	public boolean isFilledAdditionalLessonCd() {

		if (isNormalMember()) {
			return !StringUtils.isEmptyOrWhitespace(additionalLessonCd);
		} else {
			return true;
		}
	}

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.needNotBlankZipCodeAndAddress}")
	public boolean isFilledZipCdAndAddress() {

		if (StringUtils.isEmptyOrWhitespace(zip1) && StringUtils.isEmptyOrWhitespace(zip2) && StringUtils.isEmptyOrWhitespace(address1)) {
			return true;
		} else if (!StringUtils.isEmptyOrWhitespace(zip1) && !StringUtils.isEmptyOrWhitespace(zip2)
				&& !StringUtils.isEmptyOrWhitespace(address1)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isNormalMember() {
		if (MemberTypeCd.MEMBER.equals(memberTypeCd) && MemberUseFlagCd.MEMBER_USE_FLAG_NORMAL.equals(useFlag)) {
			return true;
		}
		return false;
	}


	public MemberRegisterServiceBean createMemberRegisterServiceBean() {
		ModelMapper modelMapper = new ModelMapper();
		MemberRegisterServiceBean serviceBean = modelMapper.map(this, MemberRegisterServiceBean.class);

		return serviceBean;
	}

}
