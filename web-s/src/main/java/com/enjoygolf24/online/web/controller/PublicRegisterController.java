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

import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberRegisterService;
import com.enjoygolf24.online.web.form.PublicRegisterForm;

@Controller
@RequestMapping("/public/register")
public class PublicRegisterController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberRegisterService memberRegisterService;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	AspService aspService;

	private static final Logger logger = LoggerFactory.getLogger(PreMemberRegisterController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(@ModelAttribute("publicRegisterForm") PublicRegisterForm form, Model model) {
		logger.info("Start publicRegister Controller");

		initForm(form, model);

		model.addAttribute("preMemberRegisterForm", form);

		logger.info("End publicRegister Controller");
		return "/front/register/index";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) PublicRegisterForm form, BindingResult result,
			Model model) {
		logger.info("Start publicRegister Controller");

		if (result.hasErrors()) {
			model.addAttribute("publicRegisterForm", form);
			return index(form, model);
		}

		if (!memberRegisterService.isUniqueEmailForPreMember(form.getEmail())) {
			model.addAttribute("publicRegisterForm", form);
			result.rejectValue("email", "error.user", "メールアドレス : 同じEメールアドレスの会員が既に存在しています。");
			return index(form, model);
		}

		String memberCode = memberRegisterService.PreMemberRegister(form.createPreMemberRegisterServiceBean());
		form.setHasChanged(true);


		logger.info("End publicRegister Controller");
		return index(form, model);
	}

	private void initForm(PublicRegisterForm form, Model model) {
		model.addAttribute("aspMap", aspService.createAspMap());

	}






}
