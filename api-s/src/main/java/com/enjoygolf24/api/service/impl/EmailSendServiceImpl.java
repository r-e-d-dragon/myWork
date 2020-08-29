package com.enjoygolf24.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.TblMailMaster;
import com.enjoygolf24.api.common.database.jpa.repository.MailMasterRepository;
import com.enjoygolf24.api.service.EmailSendService;
import com.enjoygolf24.api.service.bean.EmailSendServiceBean;

@Service
public class EmailSendServiceImpl implements EmailSendService {

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

	}

	private void setMailMaster(EmailSendServiceBean serviceBean) {
		TblMailMaster mailMaster = mailMasterRepository.findByMailSectionCd(serviceBean.getMailSectionCd());
		serviceBean.setMailMaster(mailMaster);
		if (serviceBean.getMailSectionCdSub() != null) {
			mailMaster = mailMasterRepository.findByMailSectionCd(serviceBean.getMailSectionCdSub());
			serviceBean.setMailMasterSub(mailMaster);
		}
	}

}
