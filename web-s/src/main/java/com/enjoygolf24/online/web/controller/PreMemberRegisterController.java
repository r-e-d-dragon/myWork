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

import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberRegisterService;
import com.enjoygolf24.online.web.form.PreMemberInfoManageForm;
import com.enjoygolf24.online.web.form.PreMemberRegisterForm;

@Controller
@RequestMapping("/admin/member/registerPreUser")
public class PreMemberRegisterController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberRegisterService memberRegisterService;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	private static final Logger logger = LoggerFactory.getLogger(PreMemberRegisterController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(@ModelAttribute("preMemberRegisterForm") PreMemberRegisterForm form, Model model) {
		logger.info("Start preMemberRegister Controller");

		initForm(form, model);

		model.addAttribute("preMemberRegisterForm", form);

		logger.info("End preMemberRegister Controller");
		return "/admin/member/registerPreUser/index";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) PreMemberRegisterForm form, BindingResult result,
			Model model) {
		logger.info("Start preMemberRegister Controller");

		if (result.hasErrors()) {
			model.addAttribute("preMemberRegisterForm", form);
			return index(form, model);
		}

		if (!memberRegisterService.isUniqueEmailForPreMember(form.getEmail())) {
			model.addAttribute("preMemberRegisterForm", form);
			result.rejectValue("email", "error.user", "{0} : 同じEメールアドレスの会員が既に存在しています。");
			return index(form, model);
		}

		String memberCode = memberRegisterService.PreMemberRegister(form.createPreMemberRegisterServiceBean());

		PreMemberInfoManageForm modifyForm = new PreMemberInfoManageForm();
		initDetailForm(modifyForm, model, memberCode);
		result.rejectValue("hasChanged", "error.user", "登録完了しました。");

		model.addAttribute("preMemberInfoManageForm", modifyForm);

		logger.info("End preMemberRegister Controller");
		return "/admin/member/managePreUser/details";
	}

	private void initForm(PreMemberRegisterForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		model.addAttribute("aspName", memberRegisterService.getAspName(aspCode));
	}

	private void initDetailForm(PreMemberInfoManageForm form, Model model, String memberCode) {
		form.init(memberInfoManageService, memberCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
	}


}
