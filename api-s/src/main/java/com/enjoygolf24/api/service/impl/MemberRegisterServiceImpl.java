package com.enjoygolf24.api.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.code.AuthStatusCd;
import com.enjoygolf24.api.common.code.MailSectionCd;
import com.enjoygolf24.api.common.code.MemberTypeCd;
import com.enjoygolf24.api.common.code.MemberUseFlagCd;
import com.enjoygolf24.api.common.code.OnOffCd;
import com.enjoygolf24.api.common.code.ProcessingCd;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.bean.TblAuthKeyManage;
import com.enjoygolf24.api.common.database.bean.TblRole;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.bean.TblUserPre;
import com.enjoygolf24.api.common.database.bean.TblUserRole;
import com.enjoygolf24.api.common.database.jpa.repository.AspRepository;
import com.enjoygolf24.api.common.database.jpa.repository.AuthKeyManageRepository;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PreMemberRepository;
import com.enjoygolf24.api.common.database.jpa.repository.TblRoleRepository;
import com.enjoygolf24.api.common.database.jpa.repository.TblUserRoleRepository;
import com.enjoygolf24.api.common.database.mybatis.repository.MemberMapper;
import com.enjoygolf24.api.common.utility.CodeGeneratorUtility;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.service.AuthUrlService;
import com.enjoygolf24.api.service.EmailSendService;
import com.enjoygolf24.api.service.MemberRegisterService;
import com.enjoygolf24.api.service.bean.EmailSendServiceBean;
import com.enjoygolf24.api.service.bean.ManagerRegisterServiceBean;
import com.enjoygolf24.api.service.bean.MemberRegisterServiceBean;
import com.enjoygolf24.api.service.bean.PreMemberRegisterServiceBean;

@Service
public class MemberRegisterServiceImpl implements MemberRegisterService {

	private static final Logger logger = LoggerFactory.getLogger(MemberRegisterServiceImpl.class);

	private static final String ASP_AUTH_URL = "%s/public/open/initPassword/admin?encryptedkey=%s";

	private static final String MEMBER_AUTH_URL = "%s/public/open/initPassword?encryptedkey=%s";

	@Autowired
	private AspRepository aspRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PreMemberRepository preMemberRepository;

	@Autowired
	private AuthKeyManageRepository authKeyManageRepository;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private EmailSendService emailSendService;

	@Autowired
	private AuthUrlService authUrlService;

	@Autowired
	private TblUserRoleRepository userRoleRepository;

	@Autowired
	private TblRoleRepository tblRoleRepository;

	@Autowired
	CodeGeneratorUtility codeGenerator;

	@Override
	public String getAspName(String LoginUserAspCode) {
		String aspName = aspRepository.findByAspCode(LoginUserAspCode).getAspName();
		return aspName;
	}

	@Override
	@Transactional
	public String MemberRegister(MemberRegisterServiceBean serviceBean) {

		TblUser member = insertMember(serviceBean);

		sendAuthKeyMail(member.getMemberCode(), member.getMemberTypeCd());

		return member.getMemberCode();
	}

	@Transactional
	private TblUser insertMember(MemberRegisterServiceBean serviceBean) {

		TblUser member = new TblUser();

		member.setMemberCode(codeGenerator.getMemberCode(memberRepository.count()));

		Timestamp current = new Timestamp(System.currentTimeMillis());

		if (!StringUtils.isEmpty(serviceBean.getAddress1())) {
			member.setAddress1(serviceBean.getAddress1());
		}

		member.setAspCode(serviceBean.getAspCode());
		member.setAuthTypeCd(null);

		if (!StringUtils.isEmpty(serviceBean.getBankKana())) {
			member.setBankKana(serviceBean.getBankKana());
		}

		member.setEmail(serviceBean.getEmail());
		member.setFirstName(serviceBean.getFirstName());
		member.setFirstNameKana(serviceBean.getFirstNameKana());
		member.setLastName(serviceBean.getLastName());
		member.setLastNameKana(serviceBean.getLastNameKana());

		member.setMemberTypeCd(serviceBean.getMemberTypeCd());

		if (!StringUtils.isEmpty(serviceBean.getMemo())) {
			member.setMemo(serviceBean.getMemo());
		}

		member.setPassword(null);
		member.setPhone(serviceBean.getPhone());
		member.setRegisterDate(current);
		member.setRegisterUser(serviceBean.getLoginUserCd());
		member.setUpdateDate(current);
		member.setUpdateUser(serviceBean.getLoginUserCd());
		member.setUseFlag(serviceBean.getUseFlag());
		member.setMemberGradeCode(serviceBean.getMemberGradeCode());
		member.setGender(serviceBean.getGender());
		if (serviceBean.getBirthday() != null) {
			member.setBirthday(DateUtility.getDate(serviceBean.getBirthday()));
		}

		if (!StringUtils.isEmpty(serviceBean.getZip1())) {
			member.setZip1(serviceBean.getZip1());
		}

		if (!StringUtils.isEmpty(serviceBean.getZip2())) {
			member.setZip2(serviceBean.getZip2());
		}

		member.setMemberGradeCode(serviceBean.getMemberGradeCode());
		member.setAdditionalLessonCd(serviceBean.getAdditionalLessonCd());
		member.setJobCd(serviceBean.getJobCode());
		member.setMemberGradeTimeCode(serviceBean.getMemberGradeTimeCode());

		memberRepository.save(member);

		return member;
	}

	@Override
	@Transactional
	public String ManagerRegister(ManagerRegisterServiceBean serviceBean) {

		TblUser member = insertManager(serviceBean);

		insertManagerRole(member);

		sendAuthKeyMail(member.getMemberCode(), member.getMemberTypeCd());

		return member.getMemberCode();
	}

	@Transactional
	private void insertManagerRole(TblUser member) {

		TblAsp aspInfo = aspRepository.findByAspCode(member.getAspCode());

		TblUserRole userRole = new TblUserRole();
		TblRole role = tblRoleRepository.findByShopTypeCdAndAuthTypeCd(aspInfo.getShopTypeCd(), member.getAuthTypeCd());

		TblUser loginUser = LoginUtility.getLoginUser();

		userRole.setUserCode(member.getMemberCode());
		userRole.setRoleId(role.getRoleId());
		userRole.setRegisterUser(loginUser.getMemberCode());
		userRole.setRegisterDate(DateUtility.getCurrentTimestamp());
		userRole.setUpdateUser(loginUser.getMemberCode());
		userRole.setUpdateDate(DateUtility.getCurrentTimestamp());

		userRoleRepository.save(userRole);
	}

	@Transactional
	private TblUser insertManager(ManagerRegisterServiceBean serviceBean) {

		TblUser member = new TblUser();

		String memberCode = "M000A" + Long.toString(memberRepository.count() + 1);
		Timestamp current = new Timestamp(System.currentTimeMillis());

		if (!StringUtils.isEmpty(serviceBean.getAddress1())) {
			member.setAddress1(serviceBean.getAddress1());
		}
		member.setAspCode(serviceBean.getAspCode());
		member.setAuthTypeCd(serviceBean.getAuthTypeCd());
		member.setBankKana(null);
		member.setEmail(serviceBean.getEmail());
		member.setFirstName(serviceBean.getFirstName());
		member.setFirstNameKana(serviceBean.getFirstNameKana());
		member.setLastName(serviceBean.getLastName());
		member.setLastNameKana(serviceBean.getLastNameKana());
		member.setMemberCode(memberCode);
		member.setMemberTypeCd(MemberTypeCd.MANAGER);
		member.setMemo(null);
		member.setPassword(null);
		member.setPhone(serviceBean.getPhone());
		member.setRegisterDate(current);
		member.setRegisterUser(serviceBean.getLoginUserCd());
		member.setUpdateDate(current);
		member.setUpdateUser(serviceBean.getLoginUserCd());
		member.setUseFlag(MemberUseFlagCd.MEMBER_USE_FLAG_NORMAL);

		if (!StringUtils.isEmpty(serviceBean.getZip1())) {
			member.setZip1(serviceBean.getZip1());
		}

		if (!StringUtils.isEmpty(serviceBean.getZip2())) {
			member.setZip2(serviceBean.getZip2());
		}

		memberRepository.save(member);
		return member;
	}

	@Override
	@Transactional
	public void sendAuthKeyMail(String memberCode, String memberTypeCode) {

		TblAuthKeyManage AuthKeyManage = insertWaitingTblAuthKeyManage(memberCode);

		EmailSendServiceBean emailSendServiceBean = createAuthKeyEmailSendServiceBean(memberCode, AuthKeyManage,
				memberTypeCode);

		// TODO: AWS email send
		emailSendService.send(emailSendServiceBean);

		String txt = emailSendServiceBean.getSenderName() + "\n" + emailSendServiceBean.getSenderEmailAddress() + "\n"
				+ emailSendServiceBean.getTargetName() + "\n" + emailSendServiceBean.getTargetEmailAddress() + "\n"
				+ emailSendServiceBean.getSubject() + "\n" + emailSendServiceBean.getBody();

		logger.info(txt);
	}

	@Transactional
	private TblAuthKeyManage insertWaitingTblAuthKeyManage(String memberCode) {

		memberMapper.setAuthStatusCd(memberCode, ProcessingCd.INIT_PASSWORD, AuthStatusCd.WAITING);

		TblAuthKeyManage AuthKeyManage = new TblAuthKeyManage();

		AuthKeyManage.setProcessingCd(ProcessingCd.INIT_PASSWORD);
		AuthKeyManage.setMemberCode(memberCode);
		String authKey = authUrlService.generateAuthKey(memberCode);

		AuthKeyManage.setAuthKey(authKey);

		AuthKeyManage.setAuthStatusCd(AuthStatusCd.WAITING);

		Timestamp current = new Timestamp(System.currentTimeMillis());
		AuthKeyManage.setRegisterDate(current);
		AuthKeyManage.setUpdateDate(current);
		AuthKeyManage.setUpdateSeq(Integer.valueOf(1));

		authKeyManageRepository.save(AuthKeyManage);

		return AuthKeyManage;
	}

	private EmailSendServiceBean createAuthKeyEmailSendServiceBean(String memberCode, TblAuthKeyManage AuthKeyManage,
			String memberTypeCode) {
		String authUrl = generateAuthUrl(AuthKeyManage, memberTypeCode);
		TblUser member = memberRepository.findByMemberCode(memberCode);
		EmailSendServiceBean mailSendServiceBean = new EmailSendServiceBean(MailSectionCd.MEMBER_REGISTER, member,
				authUrl);
		return mailSendServiceBean;
	}

	private String generateAuthUrl(TblAuthKeyManage AuthKeyManage, String memberTypeCode) {

		if (MemberTypeCd.MANAGER.equals(memberTypeCode)) {
			return String.format(ASP_AUTH_URL, "http://localhost:80", AuthKeyManage.getAuthKey());
		} else {
			return String.format(MEMBER_AUTH_URL, "http://localhost:80", AuthKeyManage.getAuthKey());
		}

	}

	@Override
	@Transactional
	public String PreMemberRegister(PreMemberRegisterServiceBean serviceBean) {
		TblUserPre member = insertPreMember(serviceBean);

		return member.getPreMemberCode();
	}

	@Transactional
	private TblUserPre insertPreMember(PreMemberRegisterServiceBean serviceBean) {

		TblUserPre member = new TblUserPre();

		member.setPreMemberCode(codeGenerator.getPreMemberCode(preMemberRepository.count()));

		Timestamp current = new Timestamp(System.currentTimeMillis());

		member.setAspCode(serviceBean.getAspCode());

		member.setEmail(serviceBean.getEmail());
		member.setFirstName(serviceBean.getFirstName());
		member.setFirstNameKana(serviceBean.getFirstNameKana());
		member.setLastName(serviceBean.getLastName());
		member.setLastNameKana(serviceBean.getLastNameKana());

		if (!StringUtils.isEmpty(serviceBean.getMemo())) {
			member.setMemo(serviceBean.getMemo());
		}

		// TODO: how to set password for preMember?
		member.setPassword(new BCryptPasswordEncoder().encode("Passw0rd"));

		if (!StringUtils.isEmpty(serviceBean.getPhone())) {
			member.setPhone(serviceBean.getPhone());
		}

		member.setRegisterDate(current);
		member.setRegisterUser(serviceBean.getLoginUserCd());
		member.setUpdateDate(current);
		member.setUpdateUser(serviceBean.getLoginUserCd());
		member.setUseFlag(OnOffCd.ON);

		preMemberRepository.save(member);

		return member;
	}

	@Override
	public boolean isUniqueEmail(String email) {
		List<TblUser> memberList = memberRepository.findByEmailAndUseFlagNot(email,
				MemberUseFlagCd.MEMBER_USE_FLAG_WITHDRAWAL);
		if (memberList.size() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isUniqueEmailForPreMember(String email) {
		if (isUniqueEmail(email) == true) {
			List<TblUserPre> preMemberList = preMemberRepository.findByEmailAndUseFlagNot(email, OnOffCd.OFF);
			if (preMemberList.size() == 0) {
				return true;
			}
		}
		return false;
	}

}
