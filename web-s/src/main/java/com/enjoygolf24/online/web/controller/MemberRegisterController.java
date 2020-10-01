package com.enjoygolf24.online.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.MemberTypeCd;
import com.enjoygolf24.api.common.code.MemberUseFlagCd;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.service.CdMapService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberRegisterService;
import com.enjoygolf24.online.web.form.MemberInfoManageForm;
import com.enjoygolf24.online.web.form.MemberRegisterForm;

@Controller
@RequestMapping("/admin/member/registerUser")
public class MemberRegisterController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberRegisterService memberRegisterService;

	@Autowired
	private CdMapService cdMapService;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	private static final Logger logger = LoggerFactory.getLogger(MemberRegisterController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(@ModelAttribute("memberRegisterForm") MemberRegisterForm form, Model model) {
		logger.info("Start memberRegister Controller");

		initForm(form, model);

		model.addAttribute("memberRegisterForm", form);

		logger.info("End memberRegister Controller");
		return "/admin/member/registerUser/index";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) MemberRegisterForm form, BindingResult result,
			Model model) {
		logger.info("Start memberRegister Controller");

		if (result.hasErrors()) {
			model.addAttribute("memberRegisterForm", form);
			return index(form, model);
		}

		if (!memberRegisterService.isUniqueEmail(form.getEmail())) {
			model.addAttribute("memberRegisterForm", form);
			result.rejectValue("email", "error.user", "{0} : 同じEメールアドレスの会員が既に存在しています。");
			return index(form, model);
		}

		String memberCode = memberRegisterService.MemberRegister(form.createMemberRegisterServiceBean());

		MemberInfoManageForm modifyForm = new MemberInfoManageForm();
		initDetailForm(modifyForm, model, memberCode);
		result.rejectValue("hasChanged", "error.user", "登録完了しました。");

		model.addAttribute("memberInfoManageForm", modifyForm);

		logger.info("End memberRegister Controller");
		return "/admin/member/manageUser/details";
	}

	private void initForm(MemberRegisterForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		model.addAttribute("aspName", memberRegisterService.getAspName(aspCode));
		model.addAttribute("memberTypeCdMap", cdMapService.createMapOnlyIncludesReverse(CodeTypeCd.MEMBER_TYPE_CD,
				MemberTypeCd.INSTRUCTOR, MemberTypeCd.MEMBER));
		model.addAttribute("genderCdMap", cdMapService.createMap(CodeTypeCd.GENDER_CD));
		model.addAttribute("memberGradeCdMap", cdMapService.createMap(CodeTypeCd.MEMBER_GRADE_CD));
		model.addAttribute("memberUseFlagCdMap",
				cdMapService.createMapOnlyIncludes(CodeTypeCd.MEMBER_USE_FLAG_CD,
						MemberUseFlagCd.MEMBER_USE_FLAG_NORMAL, MemberUseFlagCd.MEMBER_USE_FLAG_LOCKED,
						MemberUseFlagCd.MEMBER_USE_FLAG_WITHDRAWAL));
		model.addAttribute("additionalLessonCdMap", cdMapService.createMap(CodeTypeCd.ADDITIONAL_LESSON_CD));
		model.addAttribute("memberGradeTimeCdMap", cdMapService.createMap(CodeTypeCd.MEMBER_GRADE_TIME_CD));
		model.addAttribute("jobCdMap", cdMapService.createMap(CodeTypeCd.JOB_CD));
	}

	private void initDetailForm(MemberInfoManageForm form, Model model, String memberCode) {
		form.init(memberInfoManageService, memberCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		model.addAttribute("memberTypeCdMap", cdMapService.createMapOnlyIncludesReverse(CodeTypeCd.MEMBER_TYPE_CD,
				MemberTypeCd.INSTRUCTOR, MemberTypeCd.MEMBER));
		model.addAttribute("genderCdMap", cdMapService.createMap(CodeTypeCd.GENDER_CD));
		model.addAttribute("memberGradeCdMap", cdMapService.createMap(CodeTypeCd.MEMBER_GRADE_CD));
		model.addAttribute("memberUseFlagCdMap",
				cdMapService.createMapOnlyIncludes(CodeTypeCd.MEMBER_USE_FLAG_CD,
						MemberUseFlagCd.MEMBER_USE_FLAG_NORMAL, MemberUseFlagCd.MEMBER_USE_FLAG_LOCKED,
						MemberUseFlagCd.MEMBER_USE_FLAG_WITHDRAWAL));
		model.addAttribute("additionalLessonCdMap", cdMapService.createMap(CodeTypeCd.ADDITIONAL_LESSON_CD));
		model.addAttribute("memberGradeTimeCdMap", cdMapService.createMap(CodeTypeCd.MEMBER_GRADE_TIME_CD));
		model.addAttribute("jobCdMap", cdMapService.createMap(CodeTypeCd.JOB_CD));
	}


}
