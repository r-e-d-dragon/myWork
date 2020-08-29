
package com.enjoygolf24.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.bean.LoginUser;
import com.enjoygolf24.api.common.code.MemberTypeCd;
import com.enjoygolf24.api.common.constants.MemberContants;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.bean.TblUserRole;
import com.enjoygolf24.api.common.database.jpa.repository.AspRepository;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.common.database.jpa.repository.TblUserRoleRepository;
import com.enjoygolf24.api.service.CommonLoginService;

/**
 * <p>
 * クラス名：<br>
 * 顧客のログインクサービス実装クラス<br>
 * <br>
 * 機能説明：<br>
 * ログイン時の各種処理を行う。<br>
 * <br>
 * </p>
 *
 * @since 2019/2
 *
 * @version 1.00 1次開発
 */
@Service
public class CommonLoginServiceImpl implements CommonLoginService {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	TblUserRoleRepository tblUserRoleRepository;

	@Autowired
	AspRepository aspRepository;

	/**
	 * ユーザー認証
	 *
	 * @param userId ユーザーID
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		String authString = "";
		TblUser login = null;
		TblAsp aspInfo = null;

		try {
			// String password = encoder.encode("Passw0rd");
			login = memberRepository.findByEmailAndUseFlag(userId, MemberContants.MEMBER_USE_FLAG_NORMAL);

			if (login == null) {
				throw new UsernameNotFoundException("User not found for login id: " + userId);
			}

			String memberType = login.getMemberTypeCd();

			if (MemberTypeCd.MANAGER.contentEquals(memberType)) {
				TblUserRole role = tblUserRoleRepository.findByUserCode(login.getMemberCode());

				if (role == null) {
					throw new UsernameNotFoundException("It can not be acquired User role");
				}
				authString += role.getRoleId();
			} else if (MemberTypeCd.INSTRUCTOR.contentEquals(memberType)) {

				authString += "INSTRUCTOR";

			} else if (MemberTypeCd.MEMBER.contentEquals(memberType)) {

				authString += "MEMBER";
			} else {
				throw new UsernameNotFoundException("It can not be acquired User Type");
			}

			aspInfo = aspRepository.findByAspCode(login.getAspCode());
			if (aspInfo == null) {
				throw new UsernameNotFoundException("It can not be acquired Asp Info");
			}

		} catch (Exception e) {
			throw new UsernameNotFoundException("It can not be acquired User");
		}

		return new LoginUser(login, aspInfo, authString);
	}
}
