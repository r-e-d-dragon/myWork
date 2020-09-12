package com.enjoygolf24.online.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
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

import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.online.web.form.SendMailForm;


@Controller
@RequestMapping("/admin/booking/sendMail")
public class SendMailController {

	private static final Logger logger = LoggerFactory.getLogger(SendMailController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	AspService aspService;


	@RequestMapping(value = "")
	public String sendMailInfo(
			@ModelAttribute("sendMailForm") SendMailForm form, Model model) {
		logger.info("Start fileUploadInfo Controller");

		initForm(form, model);

		logger.info("End sendMailInfo Controller");
		return "/admin/booking/sendMail/index";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String sendMailFinish(@ModelAttribute @Validated(All.class) SendMailForm form, BindingResult result,
			Model model) {
		logger.info("Start sendMailFinish Controller");

		if (result.hasErrors()) {
			model.addAttribute("sendMailForm", form);
			return sendMailInfo(form, model);
		}

		// TODO 受信メールアドレス
		String toMail = "admin@mail.com";

		try {
			Email email = new SimpleEmail();
			// メールサーバ
			email.setHostName("pop3.lolipop.jp");
			// SSL
			email.setSmtpPort(465);
			// TODO user-id, password
			email.setAuthenticator(new DefaultAuthenticator("username", "password"));
			email.setSSLOnConnect(true);
			email.setFrom(form.getFromMail());
			email.setSubject(form.getTitle());
			email.setMsg(form.getComment());
			email.addTo(toMail);
			email.send();
		} catch (Exception e) {
			// 異常 - メール送信失敗
			model.addAttribute("sendMailForm", form);
			result.rejectValue("email", "error.memberName", "{0} : メール送信に失敗しました。");
			return sendMailInfo(form, model);
		}

		logger.info("End sendMailFinish Controller");
		return "/admin/booking/sendMail/finish";
	}

	/**
	 * メール送信フォーム初期処理
	 * 
	 * @param form
	 * @return
	 */
	private void initForm(SendMailForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		TblAsp asp = aspService.getAsp(aspCode);

		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());

		model.addAttribute("aspName", asp.getAspName());
	}

}
