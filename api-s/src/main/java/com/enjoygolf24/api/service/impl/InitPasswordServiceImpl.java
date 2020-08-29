package com.enjoygolf24.api.service.impl;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.code.AuthStatusCd;
import com.enjoygolf24.api.common.code.MemberTypeCd;
import com.enjoygolf24.api.common.database.bean.TblAuthKeyManage;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.jpa.repository.AuthKeyManageRepository;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.service.InitPasswordService;

@Service
public class InitPasswordServiceImpl implements InitPasswordService {

	@Autowired
	private AuthKeyManageRepository authKeyManageRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public boolean aukeyCheck(String authKey) {

		TblAuthKeyManage auth = authKeyManageRepository.findByAuthKeyAndAuthStatusCd(authKey, AuthStatusCd.WAITING);
		if (auth == null) {
			return false;
		}
		if (AuthStatusCd.WAITING.equals(auth.getAuthStatusCd())) {
			TblUser member = memberRepository.findByMemberCode(auth.getMemberCode());
			if (!MemberTypeCd.MANAGER.equals(member.getMemberTypeCd())) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean aukeyCheckAdmin(String authKey) {

		TblAuthKeyManage auth = authKeyManageRepository.findByAuthKeyAndAuthStatusCd(authKey, AuthStatusCd.WAITING);
		if (auth == null) {
			return false;
		}
		if (AuthStatusCd.WAITING.equals(auth.getAuthStatusCd())) {
			TblUser member = memberRepository.findByMemberCode(auth.getMemberCode());
			if (MemberTypeCd.MANAGER.equals(member.getMemberTypeCd())) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean memberCodeCheck(String authKey, String memberCode) {
		TblAuthKeyManage auth = authKeyManageRepository.findByAuthKeyAndAuthStatusCd(authKey, AuthStatusCd.WAITING);

		if (auth != null & memberCode.equals(auth.getMemberCode())) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public void initPassword(String authKey, String memberCode, String password) {
		TblUser member = memberRepository.findByMemberCode(memberCode);
		Timestamp current = new Timestamp(System.currentTimeMillis());

		member.setPassword(new BCryptPasswordEncoder().encode(password));
		member.setUpdateDate(current);
		member.setUpdateUser(memberCode);

		TblAuthKeyManage auth = authKeyManageRepository.findByAuthKeyAndAuthStatusCd(authKey, AuthStatusCd.WAITING);
		auth.setAuthStatusCd(AuthStatusCd.DONE);
		auth.setUpdateDate(current);
		auth.setUpdateSeq(auth.getUpdateSeq() + 1);

	}

}
