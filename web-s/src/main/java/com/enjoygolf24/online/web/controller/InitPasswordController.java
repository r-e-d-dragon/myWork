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
import org.springframework.web.bind.annotation.RequestParam;

import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.service.InitPasswordService;
import com.enjoygolf24.online.web.form.InitPasswordForm;

@Controller
@RequestMapping("/public/open/initPassword")
public class InitPasswordController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	@Autowired
	InitPasswordService initPasswordService;

	private static final Logger logger = LoggerFactory.getLogger(InitPasswordController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(@RequestParam("encryptedkey") String authKey,
			@ModelAttribute("initPasswordForm") InitPasswordForm form, Model model) {
		logger.info("Start init password Controller");

		if (!initPasswordService.aukeyCheck(authKey)) {
			return "/front/initPassword/authError";
		}

		initForm(form, model, authKey);

		model.addAttribute("memberRegisterForm", form);

		logger.info("End init password Controller");
		return "/front/initPassword/index";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) InitPasswordForm form, BindingResult result,
			Model model) {
		logger.info("Start init password Controller");

		if (result.hasErrors()) {
			return index(form.getAuthKey(), form, model);
		}
		
		if (!initPasswordService.memberCodeCheck(form.getAuthKey(), form.getMemberCode())) {
			result.rejectValue("memberCode", "error.user", "{0} : 入力値に問題があります。");
			return index(form.getAuthKey(), form, model);
		}

		initPasswordService.initPassword(form.getAuthKey(), form.getMemberCode(), form.getPassword());
	
		logger.info("End init password Controller");
		return "/front/initPassword/finish";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminIndex(@RequestParam("encryptedkey") String authKey,
			@ModelAttribute("initPasswordForm") InitPasswordForm form, Model model) {
		logger.info("Start init password Controller");

		if (!initPasswordService.aukeyCheckAdmin(authKey)) {
			return "/front/initPassword/authError";
		}

		initForm(form, model, authKey);

		model.addAttribute("memberRegisterForm", form);

		logger.info("End init password Controller");
		return "/admin/initPassword/index";
	}

	@RequestMapping(value = "/admin/finish", method = RequestMethod.POST)
	public String adminFinish(@ModelAttribute @Validated(All.class) InitPasswordForm form, BindingResult result,
			Model model) {
		logger.info("Start init password Controller");

		if (result.hasErrors()) {
			return adminIndex(form.getAuthKey(), form, model);
		}

		if (!initPasswordService.memberCodeCheck(form.getAuthKey(), form.getMemberCode())) {
			result.rejectValue("memberCode", "error.user", "{0} : 入力値に問題があります。");
			return adminIndex(form.getAuthKey(), form, model);
		}

		initPasswordService.initPassword(form.getAuthKey(), form.getMemberCode(), form.getPassword());

		logger.info("End init password Controller");
		return "/admin/initPassword/finish";
	}


	private void initForm(InitPasswordForm form, Model model, String authKey) {
		form.setAuthKey(authKey);

	}


}
