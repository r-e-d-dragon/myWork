
package com.enjoygolf24.api.service;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.service.bean.EmailSendServiceBean;

@Service
public interface EmailSendService {

	public String getBody(EmailSendServiceBean serviceBean);

	public void send(EmailSendServiceBean serviceBean);

}
