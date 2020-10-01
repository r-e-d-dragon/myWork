package com.enjoygolf24.online.web.form;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.MemberTypeCd;
import com.enjoygolf24.api.common.code.MemberUseFlagCd;
import com.enjoygolf24.api.common.code.OnOffCd;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.validator.annotation.CodeMaster;
import com.enjoygolf24.api.common.validator.annotation.Email;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.Kana;
import com.enjoygolf24.api.common.validator.annotation.Kanji;
import com.enjoygolf24.api.common.validator.annotation.OnOff;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;
import com.enjoygolf24.api.common.validator.annotation.Telephone;
import com.enjoygolf24.api.common.validator.annotation.Zip1;
import com.enjoygolf24.api.common.validator.annotation.Zip2;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.bean.MemberModifyServiceBean;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class MemberInfoManageForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	private MemberInfoManageService memberInfoManageService;

	private TblUser selectedMember;

	@Kanji(groups = Insert0.class, max = 200)
	@SjisSafe(groups = Insert0.class)
	private String address1;

	@Kanji(groups = Insert0.class, max = 200)
	@SjisSafe(groups = Insert0.class)
	private String address2;

	@Hankaku(groups = Insert0.class, max = 20)
	private String memberCode;

	@Hankaku(groups = Insert0.class, max = 20)
	private String aspCode;

	private String aspName;

	@NotBlank(groups = Insert0.class)
	@Size(groups = Insert0.class, max = 80)
	@Email(groups = Insert0.class)
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

	@NotBlank(groups = Insert0.class)
	@OnOff(groups = Insert0.class)
	private String initPasswordCd;

	@NotBlank(groups = Insert0.class)
	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.GENDER_CD)
	private String gender;

	private String birthday;

	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.ADDITIONAL_LESSON_CD)
	private String additionalLessonCd;

	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.JOB_CD)
	private String jobCode;

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

	public boolean isNormalMember() {
		if (MemberTypeCd.MEMBER.equals(memberTypeCd) && MemberUseFlagCd.MEMBER_USE_FLAG_NORMAL.equals(useFlag)) {
			return true;
		}
		return false;
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

	public void init(MemberInfoManageService memberInfoManageService, String memberCode) {
		this.memberInfoManageService = memberInfoManageService;
		this.selectedMember = memberInfoManageService.selectMember(memberCode);
		this.memberCode = memberCode;

		address1 = selectedMember.getAddress1();
		address2 = selectedMember.getAddress2();
		aspCode = selectedMember.getAspCode();
		aspName = memberInfoManageService.getAspName(aspCode);
		email = selectedMember.getEmail();
		emailConfirm = selectedMember.getEmail();
		firstName = selectedMember.getFirstName();
		firstNameKana = selectedMember.getFirstNameKana();
		lastName = selectedMember.getLastName();
		lastNameKana = selectedMember.getLastNameKana();
		memberTypeCd = selectedMember.getMemberTypeCd();
		phone = selectedMember.getPhone();
		zip1 = selectedMember.getZip1();
		zip2 = selectedMember.getZip2();
		memo = selectedMember.getMemo();
		gender = selectedMember.getGender();

		if (selectedMember.getBirthday() != null) {
			DateFormat dateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT);
			birthday = dateFormat.format(selectedMember.getBirthday());
		}

		useFlag = selectedMember.getUseFlag();
		additionalLessonCd = selectedMember.getAdditionalLessonCd();
		jobCode = selectedMember.getJobCode();

		initPasswordCd = OnOffCd.OFF;
	}

	public MemberModifyServiceBean createMemberModifyServiceBean() {

		MemberModifyServiceBean serviceBean = new MemberModifyServiceBean();

		if (!StringUtils.isEmpty(address1)) {
			serviceBean.setAddress1(address1);
		}

		if (!StringUtils.isEmpty(address2)) {
			serviceBean.setAddress2(address2);
		}

		serviceBean.setAspCode(aspCode);

		serviceBean.setEmail(email);
		serviceBean.setFirstName(firstName);
		serviceBean.setFirstNameKana(firstNameKana);
		serviceBean.setInitPasswordCd(initPasswordCd);
		serviceBean.setLastName(lastName);
		serviceBean.setLastNameKana(lastNameKana);
		serviceBean.setLoginUserCd(loginUserCd);
		serviceBean.setMemberCode(memberCode);
		serviceBean.setMemberTypeCd(memberTypeCd);

		if (!StringUtils.isEmpty(memo)) {
			serviceBean.setMemo(memo);
		}

		serviceBean.setPhone(phone);

		if (!StringUtils.isEmpty(zip1)) {
			serviceBean.setZip1(zip1);
		}

		if (!StringUtils.isEmpty(zip2)) {
			serviceBean.setZip2(zip2);
		}

		serviceBean.setGender(gender);
		serviceBean.setBirthday(DateUtility.getDate(birthday));
		serviceBean.setUseFlag(useFlag);
		serviceBean.setAdditionalLessonCd(additionalLessonCd);
		if (!StringUtils.isEmpty(jobCode)) {
			serviceBean.setJobCode(jobCode);
		}

		return serviceBean;
	}
}
