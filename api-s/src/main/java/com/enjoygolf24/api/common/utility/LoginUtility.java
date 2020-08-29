
package com.enjoygolf24.api.common.utility;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.enjoygolf24.api.common.bean.LoginUser;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.bean.TblUser;

/**
 * <p>
 * クラス名：<br>
 * ログイン情報を取得する。<br>
 * <br>
 * 機能説明：<br>
 * ログイン情報取得する<br>
 * <br>
 * </p>
 *
 */
public class LoginUtility {

	/**
	 * セッションからログイン情報を取得する。
	 * 
	 * @return ログインユーザ
	 */
	public static TblUser getLoginUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication == null || authentication instanceof AnonymousAuthenticationToken)) {
			LoginUser loginUser = (LoginUser) authentication.getPrincipal();
			return loginUser.getUser();
		}

		return null;
	}

	/**
	 * セッションからログイン者の会社情報を取得する。
	 * 
	 * @return ログインユーザ
	 */
	public static TblAsp getLoginCompanyInfo() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication == null || authentication instanceof AnonymousAuthenticationToken)) {
			LoginUser loginUser = (LoginUser) authentication.getPrincipal();
			return loginUser.getCompany();
		}

		return null;
	}
}
