package com.enjoygolf24.api.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.service.bean.ManagerRegisterServiceBean;
import com.enjoygolf24.api.service.bean.MemberRegisterServiceBean;
import com.enjoygolf24.api.service.bean.PreMemberRegisterServiceBean;

@Service
public interface MemberRegisterService {

	@Transactional
	public String MemberRegister(MemberRegisterServiceBean memberRegisterServiceBean);

	@Transactional
	public String ManagerRegister(ManagerRegisterServiceBean managerRegisterServiceBean);

	public String getAspName(String LoginUserAspCode);

	@Transactional
	public void sendAuthKeyMail(String memberCode, String memberTypeCode);

	@Transactional
	public String PreMemberRegister(PreMemberRegisterServiceBean serviceBean);

	public boolean isUniqueEmail(String email);

	public boolean isUniqueEmailForPreMember(String email);

}
