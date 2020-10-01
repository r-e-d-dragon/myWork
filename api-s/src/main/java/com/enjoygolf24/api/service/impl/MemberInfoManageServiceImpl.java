package com.enjoygolf24.api.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.enjoygolf24.api.common.code.AuthStatusCd;
import com.enjoygolf24.api.common.code.MailSectionCd;
import com.enjoygolf24.api.common.code.MemberGradeCd;
import com.enjoygolf24.api.common.code.MemberGradeTimeCd;
import com.enjoygolf24.api.common.code.MemberTypeCd;
import com.enjoygolf24.api.common.code.OnOffCd;
import com.enjoygolf24.api.common.code.PreMemberUseFlagCd;
import com.enjoygolf24.api.common.code.ProcessingCd;
import com.enjoygolf24.api.common.constants.MemberContants;
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
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberRegisterService;
import com.enjoygolf24.api.service.bean.EmailSendServiceBean;
import com.enjoygolf24.api.service.bean.ManagerModifyServiceBean;
import com.enjoygolf24.api.service.bean.MemberModifyServiceBean;
import com.enjoygolf24.api.service.bean.PreMemberConvertServiceBean;
import com.enjoygolf24.api.service.bean.PreMemberModifyServiceBean;
import com.github.pagehelper.PageHelper;

@Service
public class MemberInfoManageServiceImpl implements MemberInfoManageService {

	private static final Logger logger = LoggerFactory.getLogger(MemberRegisterServiceImpl.class);

	private static final String ASP_AUTH_URL = "%s/public/open/initPassword/admin?encryptedkey=%s";

	private static final String MEMBER_AUTH_URL = "%s/public/open/initPassword?encryptedkey=%s";

	@Autowired
	HttpSession session;

	@Autowired
	MemberMapper memberMapper;

	@Autowired
	private AspRepository aspRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PreMemberRepository preMemberRepository;

	@Autowired
	private AuthKeyManageRepository authKeyManageRepository;

	@Autowired
	private EmailSendService emailSendService;

	@Autowired
	private AuthUrlService authUrlService;

	@Autowired
	private MemberRegisterService memberRegisterService;

	@Autowired
	private TblUserRoleRepository userRoleRepository;

	@Autowired
	private TblRoleRepository tblRoleRepository;

	@Autowired
	CodeGeneratorUtility codeGenerator;

	@Override
	public boolean isUniqueEmail(String memberCode, String email) {
		List<TblUser> memberList = memberRepository.findByEmailAndUseFlagNot(email,
				MemberContants.MEMBER_USE_FLAG_WITHDRAWE);
		if (memberList.size() == 0) {
			return true;
		} else {
			for (TblUser member : memberList) {
				if (member.getMemberCode().equals(memberCode)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<TblUser> getMemberListAll(String aspCode, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		return memberMapper.getMemberList(null, null, null, null, aspCode);
	}

	@Override
	public List<TblUser> getMemberList(String memberCode, String name, String phone, String email, String aspCode,
			int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return memberMapper.getMemberList(memberCode, name, phone, email, aspCode);
	}

	@Override
	public List<TblUser> getManagerListAll(int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		return memberMapper.getManagerList(null, null, null, null, null, null);
	}

	@Override
	public List<TblUser> getManagerList(String aspCode, String aspName, String memberCode, String name, String phone,
			String email, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return memberMapper.getManagerList(aspCode, aspName, memberCode, name, phone, email);
	}

	@Override
	public List<TblUserPre> getPreMemberListAll(String aspCode, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		return memberMapper.getPreMemberList(null, null, null, null, aspCode, null);
	}

	@Override
	public List<TblUserPre> getPreMemberList(String memberCode, String name, String phone, String email, String aspCode,
			String useFlagCd, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return memberMapper.getPreMemberList(memberCode, name, phone, email, aspCode, useFlagCd);
	}

	@Override
	public String getAspName(String LoginUserAspCode) {
		String aspName = aspRepository.findByAspCode(LoginUserAspCode).getAspName();
		return aspName;
	}

	@Override
	public TblUser selectMember(String memberCode) {
		TblUser member = memberRepository.findByMemberCode(memberCode);
		return member;
	}

	@Override
	@Transactional
	public void memberModify(MemberModifyServiceBean serviceBean) {

		TblUser member = memberRepository.findByMemberCode(serviceBean.getMemberCode());
		Timestamp current = new Timestamp(System.currentTimeMillis());

		member.setEmail(serviceBean.getEmail());
		member.setFirstName(serviceBean.getFirstName());
		member.setFirstNameKana(serviceBean.getFirstNameKana());
		member.setLastName(serviceBean.getLastName());
		member.setLastNameKana(serviceBean.getLastNameKana());
		member.setMemberTypeCd(serviceBean.getMemberTypeCd());
		member.setMemo(serviceBean.getMemo());
		member.setPhone(serviceBean.getPhone());
		member.setUpdateDate(current);
		member.setUpdateUser(serviceBean.getLoginUserCd());
		member.setZip1(serviceBean.getZip1());
		member.setZip2(serviceBean.getZip2());
		member.setGender(serviceBean.getGender());
		member.setBirthday(serviceBean.getBirthday());
		member.setUseFlag(serviceBean.getUseFlag());
		member.setAdditionalLessonCd(serviceBean.getAdditionalLessonCd());

		if (OnOffCd.ON.equals(serviceBean.getInitPasswordCd())) {
			member.setPassword(null);
			sendAuthKeyMail(member.getMemberCode(), member.getMemberTypeCd());
		}

		member.setUpdateDate(current);
		member.setUpdateUser(serviceBean.getLoginUserCd());
		member.setAddress1(serviceBean.getAddress1());
		member.setAddress2(serviceBean.getAddress2());
		member.setJobCd(serviceBean.getJobCode());
		memberRepository.save(member);
	}

	@Override
	@Transactional
	public void preMemberModify(PreMemberModifyServiceBean serviceBean) {

		TblUserPre member = preMemberRepository.findByPreMemberCode(serviceBean.getPreMemberCode());
		Timestamp current = new Timestamp(System.currentTimeMillis());

		member.setEmail(serviceBean.getEmail());
		member.setFirstName(serviceBean.getFirstName());
		member.setFirstNameKana(serviceBean.getFirstNameKana());
		member.setLastName(serviceBean.getLastName());
		member.setLastNameKana(serviceBean.getLastNameKana());
		member.setMemo(serviceBean.getMemo());
		member.setPhone(serviceBean.getPhone());
		member.setUpdateDate(current);
		member.setUpdateUser(serviceBean.getLoginUserCd());

		member.setUpdateDate(current);
		member.setUpdateUser(serviceBean.getLoginUserCd());

		preMemberRepository.save(member);
	}

	@Transactional
	private void updateManagerRole(TblUser member) {

		if (!MemberTypeCd.MANAGER.contentEquals(member.getMemberTypeCd())) {
			return;
		}

		TblAsp aspInfo = aspRepository.findByAspCode(member.getAspCode());

		TblRole role = tblRoleRepository.findByShopTypeCdAndAuthTypeCd(aspInfo.getShopTypeCd(), member.getAuthTypeCd());

		TblUser loginUser = LoginUtility.getLoginUser();

		TblUserRole userRole = userRoleRepository.findByUserCode(member.getMemberCode());
		if (!role.getRoleId().contentEquals(userRole.getRoleId())) {
			userRole.setRoleId(role.getRoleId());
			userRole.setUpdateUser(loginUser.getMemberCode());
			userRole.setUpdateDate(DateUtility.getCurrentTimestamp());
		}
		userRoleRepository.save(userRole);
	}

	@Override
	@Transactional
	public void convert(PreMemberConvertServiceBean serviceBean) {
		TblUserPre preMember = preMemberRepository.findByPreMemberCode(serviceBean.getPreMemberCode());
		preMember.setUseFlag(OnOffCd.OFF);
		preMemberRepository.save(preMember);

		TblUser member = insertMember(serviceBean);
		memberRegisterService.sendAuthKeyMail(member.getMemberCode(), member.getMemberTypeCd());
	}

	@Transactional
	private TblUser insertMember(PreMemberConvertServiceBean serviceBean) {

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
		member.setMemberGradeCode(MemberGradeCd.FULL_MEMBER);
		member.setGender(serviceBean.getGender());
		member.setBirthday(serviceBean.getBirthday());

		if (!StringUtils.isEmpty(serviceBean.getZip1())) {
			member.setZip1(serviceBean.getZip1());
		}

		if (!StringUtils.isEmpty(serviceBean.getZip2())) {
			member.setZip2(serviceBean.getZip2());
		}

		member.setAdditionalLessonCd(serviceBean.getAdditionalLessonCd());
		member.setJobCd(serviceBean.getJobCode());
		member.setMemberGradeTimeCode(MemberGradeTimeCd.USUAL);

		memberRepository.save(member);

		return member;
	}

	@Override
	@Transactional
	public void managerModify(ManagerModifyServiceBean serviceBean) {

		TblUser member = memberRepository.findByMemberCode(serviceBean.getMemberCode());
		Timestamp current = new Timestamp(System.currentTimeMillis());

		member.setAuthTypeCd(serviceBean.getAuthTypeCd());
		member.setAddress1(serviceBean.getAddress1());
		member.setAddress2(serviceBean.getAddress2());
		member.setEmail(serviceBean.getEmail());
		member.setFirstName(serviceBean.getFirstName());
		member.setFirstNameKana(serviceBean.getFirstNameKana());
		member.setLastName(serviceBean.getLastName());
		member.setLastNameKana(serviceBean.getLastNameKana());
		member.setPhone(serviceBean.getPhone());
		member.setUpdateDate(current);
		member.setUpdateUser(serviceBean.getLoginUserCd());
		member.setZip1(serviceBean.getZip1());
		member.setZip2(serviceBean.getZip2());
		member.setPhone(serviceBean.getPhone());
		member.setUpdateDate(current);
		member.setUpdateUser(serviceBean.getLoginUserCd());

		if (OnOffCd.ON.equals(serviceBean.getInitPasswordCd())) {
			member.setPassword(null);
			sendAuthKeyMail(member.getMemberCode(), member.getMemberTypeCd());
		}

		updateManagerRole(member);

		memberRepository.save(member);
	}

	@Override
	@Transactional
	public void confirm(String preMemberCode, String loginUserCode) {
		TblUserPre member = selectPreMember(preMemberCode);
		Timestamp current = new Timestamp(System.currentTimeMillis());

		member.setUseFlag(PreMemberUseFlagCd.PRE_MEMBER_USE_FLAG_NORMAL);
		member.setUpdateDate(current);
		member.setUpdateUser(loginUserCode);

		preMemberRepository.save(member);

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
		EmailSendServiceBean mailSendServiceBean = new EmailSendServiceBean(MailSectionCd.MEMBER_MODIFY_INITPASSWORD,
				member, authUrl);
		return mailSendServiceBean;
	}

	private String generateAuthUrl(TblAuthKeyManage AuthKeyManage, String memberTypeCode) {

		if (MemberTypeCd.MANAGER.equals(memberTypeCode)) {
			return String.format(ASP_AUTH_URL, "http://enjoygolf24.com", AuthKeyManage.getAuthKey());
		} else {
			return String.format(MEMBER_AUTH_URL, "http://enjoygolf24.com", AuthKeyManage.getAuthKey());
		}

	}

	@Override
	public TblUserPre selectPreMember(String preMemberCode) {
		TblUserPre member = preMemberRepository.findByPreMemberCode(preMemberCode);
		return member;
	}

	@Override
	public boolean isUniqueEmailForPreMember(String memberCode, String email) {
		if (isUniqueEmail(memberCode, email) == true) {
			List<TblUserPre> preMemberList = preMemberRepository.findByEmailAndUseFlagNot(email, OnOffCd.OFF);
			if (preMemberList.size() == 0) {
				return true;
			} else {
				for (TblUserPre member : preMemberList) {
					if (member.getPreMemberCode().equals(memberCode)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
