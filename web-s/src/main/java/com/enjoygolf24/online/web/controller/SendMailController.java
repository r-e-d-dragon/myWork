package com.enjoygolf24.online.web.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties(prefix = "mail")
public class SendMailController {

	private static final Logger logger = LoggerFactory.getLogger(SendMailController.class);

	/** ユーザID */
	private String userid;
	/** パスワード */
	private String passwd;
	/** メールサーバ */
	private String hostname;
	/** SMTPポート */
	private int smtpPort;

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	AspService aspService;

	@RequestMapping(value = "")
	public String sendMailInfo(
			@ModelAttribute("sendMailForm") SendMailForm form, Model model) {
		logger.info("Start sendMailInfo Controller");

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

		// 送信者メールアドレス
		String fromMailAddress = "info@golfenjoy24.com";

		try {
			Email email = new SimpleEmail();
			// メールサーバ
			email.setHostName(hostname);
			// Smtp-Port
			email.setSmtpPort(smtpPort);
			// user-id, password
			email.setAuthenticator(new DefaultAuthenticator(userid, passwd));
			email.setDebug(true);
			// (SSL)
			email.setSSLOnConnect(true);

			email.setFrom(fromMailAddress);
			email.setSubject(form.getTitle());
			email.setMsg(form.getComment());
			email.addTo(form.getToMail());

			// 送信先メールアドレス（Reply-To）を設定する
			Collection<InternetAddress> replyTo = new ArrayList<InternetAddress>();
			replyTo.add(new InternetAddress(fromMailAddress));
			email.setReplyTo(replyTo);

			email.send();
		} catch (Exception e) {
			// 異常 - メール送信失敗
			model.addAttribute("sendMailForm", form);
			result.rejectValue("memberName", "error.memberName", "{0} : メール送信に失敗しました。");
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}
}
