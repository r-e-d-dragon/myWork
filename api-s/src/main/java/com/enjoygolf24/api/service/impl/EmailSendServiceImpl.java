package com.enjoygolf24.api.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.TblMailMaster;
import com.enjoygolf24.api.common.database.jpa.repository.MailMasterRepository;
import com.enjoygolf24.api.service.EmailSendService;
import com.enjoygolf24.api.service.bean.EmailSendServiceBean;

@Service
@ConfigurationProperties(prefix = "mail")
public class EmailSendServiceImpl implements EmailSendService {

	/** ユーザID */
	private String userid;
	/** パスワード */
	private String passwd;
	/** メールサーバ */
	private String hostname;
	/** SMTPポート */
	private int smtpPort;

	@Autowired
	private MailMasterRepository mailMasterRepository;

	@Override
	public String getBody(EmailSendServiceBean serviceBean) {
		setMailMaster(serviceBean);
		return serviceBean.getBody();
	}

	@Override
	public void send(EmailSendServiceBean serviceBean) {
		setMailMaster(serviceBean);
		// メール送信
		sendMail(serviceBean);
	}

	private void setMailMaster(EmailSendServiceBean serviceBean) {
		TblMailMaster mailMaster = mailMasterRepository.findByMailSectionCd(serviceBean.getMailSectionCd());
		serviceBean.setMailMaster(mailMaster);
		if (serviceBean.getMailSectionCdSub() != null) {
			mailMaster = mailMasterRepository.findByMailSectionCd(serviceBean.getMailSectionCdSub());
			serviceBean.setMailMasterSub(mailMaster);
		}
	}

	/**
	 * メール送信
	 * 
	 * @param serviceBean
	 * @return
	 */
	public void sendMail(EmailSendServiceBean serviceBean) {

		// 送信者メールアドレス
		String fromMailAddress = serviceBean.getSenderEmailAddress();

		try {
			Email email = new SimpleEmail();
			// メールサーバ
			email.setHostName(hostname);
			// Smtp-Port
			email.setSmtpPort(smtpPort);
			// user-id, password
			email.setAuthenticator(new DefaultAuthenticator(userid, passwd));
			// TODO debug
			// email.setDebug(true);
			// (SSL)
			email.setSSLOnConnect(true);

			email.setFrom(fromMailAddress);
			email.setSubject(serviceBean.getSubject());
			email.setMsg(serviceBean.getBody());
			email.addTo(serviceBean.getTargetEmailAddress());

			// 送信先メールアドレス（Reply-To）を設定する
			Collection<InternetAddress> replyTo = new ArrayList<InternetAddress>();
			replyTo.add(new InternetAddress(fromMailAddress));
			email.setReplyTo(replyTo);

			// TODO
			// TODO
			// TODO
			// TODO
			// TODO
			// TODO
			// TODO
			// email.send();
		} catch (Exception e) {
			// 異常 - メール送信失敗
			throw new RuntimeException("メール送信失敗", e);
		}
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
