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
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.service.CdMapService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberRegisterService;
import com.enjoygolf24.online.web.form.ManagerInfoManageForm;
import com.enjoygolf24.online.web.form.ManagerRegisterForm;

@Controller
@RequestMapping("/admin/asp/registerUser")
public class ManagerRegisterController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	@Autowired
	private CdMapService cdMapService;

	@Autowired
	MemberRegisterService memberRegisterService;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	private static final Logger logger = LoggerFactory.getLogger(ManagerRegisterController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(@ModelAttribute("managerRegisterForm") ManagerRegisterForm form, Model model) {
		logger.info("Start managerRegister Controller");

		initForm(form, model);

		model.addAttribute("managerRegisterForm", form);

		logger.info("End managerRegister Controller");
		return "/admin/asp/registerUser/index";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) ManagerRegisterForm form, BindingResult result,
			Model model) {
		logger.info("Start managerRegister Controller");

		if (result.hasErrors()) {
			model.addAttribute("managerRegisterForm", form);
			return index(form, model);
		}

		String memberCode = memberRegisterService.ManagerRegister(form.createManagerRegisterServiceBean());

		ManagerInfoManageForm modifyForm = new ManagerInfoManageForm();

		initDetailForm(modifyForm, model, memberCode);
		result.rejectValue("hasChanged", "error.user", "登録完了しました。");

		model.addAttribute("managerInfoManageForm", modifyForm);

		logger.info("End managerRegister Controller");

		return "/admin/asp/manageUser/details";

	}

	private void initForm(ManagerRegisterForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		model.addAttribute("aspName", memberRegisterService.getAspName(aspCode));
		model.addAttribute("authTypeCdMap", cdMapService.createMap(CodeTypeCd.AUTH_TYPE_CD));
		model.addAttribute("aspCodeCdMap", cdMapService.createAspInfoMap());
	}

	private void initDetailForm(ManagerInfoManageForm form, Model model, String memberCode) {
		form.init(memberInfoManageService, memberCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		model.addAttribute("authTypeCdMap", cdMapService.createMap(CodeTypeCd.AUTH_TYPE_CD));
	}



}
