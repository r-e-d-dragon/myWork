package com.enjoygolf24.online.web.test;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enjoygolf24.online.web.controller.AdminController;

/**
 * Gmail smtp使用 STARTTLS (Port 587) -> email.setStartTLSEnabled(true);
 */
public class GmailSendTest {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	public static void main(String[] args) {

		Email email = new SimpleEmail();

		String fromUserMail = "from@gmail.com";
		String toUserMail = "to@user.com";

		String authuser = "xxx@gmail.com";
		// 2段階認証をオンしてから、Googleが発行する、12桁のアプリケーションパスワード
		String authpwd = "aaaabbbbccccdddd";

		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
		email.setDebug(true);
		// TODO - FakeSMTP
		// email.setHostName("smtp.gmail.com");
		email.setHostName("localhost");

		try {
			email.getMailSession().getProperties().put("mail.smtps.auth", "true");
			email.getMailSession().getProperties().put("mail.debug", "true");
			email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
			email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
			email.getMailSession().getProperties().put("mail.smtps.port", "587");
			email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
			email.getMailSession().getProperties().put("mail.smtps.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
			email.setFrom(fromUserMail, "SenderName");
			email.setSubject("TestMail");
			email.setMsg("This is a test mail?");
			email.addTo(toUserMail, "ToName");
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Mail送信成功！！！");
	}

}
