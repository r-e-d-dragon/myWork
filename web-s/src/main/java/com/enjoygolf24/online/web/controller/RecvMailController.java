package com.enjoygolf24.online.web.controller;

import javax.mail.internet.InternetAddress;
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
import com.enjoygolf24.online.web.form.RecvMailForm;

@Controller
@RequestMapping("/admin/booking/recvMail")
public class RecvMailController {

	private static final Logger logger = LoggerFactory.getLogger(RecvMailController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	AspService aspService;


	@RequestMapping(value = "")
	public String recvMailInfo(
			@ModelAttribute("recvMailForm") RecvMailForm form, Model model) {
		logger.info("Start recvMailInfo Controller");

		initForm(form, model);

		logger.info("End recvMailInfo Controller");
		return "/admin/booking/recvMail/index";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String recvMailFinish(@ModelAttribute @Validated(All.class) RecvMailForm form, BindingResult result,
			Model model) {
		logger.info("Start recvMailFinish Controller");

		if (result.hasErrors()) {
			model.addAttribute("sendMailForm", form);
			return recvMailInfo(form, model);
		}

		// TODO 受信メールアドレス
		String toMail = "user@user.jp";
		String passwd = "passwd";

		try {
			InternetAddress addr[] = { new InternetAddress(toMail) };

			Email email = new SimpleEmail();
			// TODO メールサーバ
			email.setHostName("pop3.lolipop.jp");

			// Smtp-Port
			email.setSmtpPort(465);
			// TODO user-id, password
			email.setAuthenticator(new DefaultAuthenticator(toMail, passwd));
			email.setDebug(true);
			// (SSL)
			email.setSSLOnConnect(true);

			email.setFrom(form.getFromMail());
			email.setSubject(form.getTitle());
			email.setMsg(form.getComment());
			email.addTo(toMail);


			email.send();

		} catch (Exception e) {
			// 異常 - メール送信失敗
			model.addAttribute("recvMailForm", form);
			result.rejectValue("memberName", "error.memberName", "{0} : メール受信に失敗しました。");
			return recvMailInfo(form, model);
		}

		logger.info("End recvMailFinish Controller");
		return "/admin/booking/recvMail/finish";
	}

	/**
	 * メール送信フォーム初期処理
	 * 
	 * @param form
	 * @return
	 */
	private void initForm(RecvMailForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		TblAsp asp = aspService.getAsp(aspCode);

		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());

		model.addAttribute("aspName", asp.getAspName());
	}
}
