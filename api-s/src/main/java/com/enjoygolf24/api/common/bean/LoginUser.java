package com.enjoygolf24.api.common.bean;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.bean.TblUser;

@SessionScope
@Component
public class LoginUser extends User {

	private static final long serialVersionUID = 1L;

	private TblUser loginUser;
	private TblAsp aspInfo;

	public LoginUser(TblUser tblLogin, TblAsp tblAsp, String... role) {

		super(tblLogin.getMemberCode(), tblLogin.getPassword(), AuthorityUtils.createAuthorityList(role));

		this.loginUser = tblLogin;
		this.aspInfo = tblAsp;

	}

	public TblUser getUser() {

		return loginUser;
	}

	public TblAsp getCompany() {

		return aspInfo;
	}

	public void setUser(TblUser tblLogin) {
		this.loginUser = tblLogin;
	}

}
