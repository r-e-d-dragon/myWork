package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.database.bean.TblUserPre;
import com.enjoygolf24.api.common.validator.annotation.Email;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.Kana;
import com.enjoygolf24.api.common.validator.annotation.Kanji;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;
import com.enjoygolf24.api.common.validator.annotation.Telephone;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.bean.PreMemberModifyServiceBean;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class PreMemberInfoManageForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	private MemberInfoManageService memberInfoManageService;

	private TblUserPre selectedMember;

	@Hankaku(groups = Insert0.class, max = 20)
	private String preMemberCode;

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
	@Telephone(groups = Insert0.class)
	private String phone;

	@Size(groups = Insert0.class, max = 1200)
	private String memo;

	@NotBlank(groups = Insert0.class)
	private String loginUserCd;

	private String hasChanged;

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.needSameEmailAndEmailConfirm}")
	public boolean isSameEmailAndEmailConfirm() {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(emailConfirm)) {
			return true;
		}
		return email.equals(emailConfirm);
	}

	public void init(MemberInfoManageService memberInfoManageService, String preMemberCode) {
		this.memberInfoManageService = memberInfoManageService;
		this.selectedMember = memberInfoManageService.selectPreMember(preMemberCode);
		this.preMemberCode = preMemberCode;

		aspCode = selectedMember.getAspCode();
		aspName = memberInfoManageService.getAspName(aspCode);
		email = selectedMember.getEmail();
		emailConfirm = selectedMember.getEmail();
		firstName = selectedMember.getFirstName();
		firstNameKana = selectedMember.getFirstNameKana();
		lastName = selectedMember.getLastName();
		lastNameKana = selectedMember.getLastNameKana();
		phone = selectedMember.getPhone();
		memo = selectedMember.getMemo();
	}

	public PreMemberModifyServiceBean createPreMemberModifyServiceBean() {

		PreMemberModifyServiceBean serviceBean = new PreMemberModifyServiceBean();

		serviceBean.setPreMemberCode(preMemberCode);
		serviceBean.setAspCode(aspCode);

		serviceBean.setEmail(email);
		serviceBean.setFirstName(firstName);
		serviceBean.setFirstNameKana(firstNameKana);
		serviceBean.setLastName(lastName);
		serviceBean.setLastNameKana(lastNameKana);
		serviceBean.setLoginUserCd(loginUserCd);

		if (!StringUtils.isEmpty(memo)) {
			serviceBean.setMemo(memo);
		}

		if (!StringUtils.isEmpty(phone)) {
			serviceBean.setPhone(phone);
		}

		return serviceBean;
	}
}
