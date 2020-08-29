
package com.enjoygolf24.api.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import com.amazonaws.services.identitymanagement.model.ServiceNotSupportedException;
import com.enjoygolf24.api.common.code.MemberTypeCd;
import com.enjoygolf24.api.common.constants.SessionKeyConstants;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.mybatis.bean.TblMenuInfo;
import com.enjoygolf24.api.common.database.mybatis.repository.MenuMapper;
import com.enjoygolf24.api.common.properties.AuthorytyProperty;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.utility.URICheckUtility;

/**
 * <p>
 * クラス名：<br>
 * フィルター<br>
 * <br>
 * 機能説明：<br>
 * 各処理の前に必要な処理を行う<br>
 * <br>
 * </p>
 */

@Component
@Order(1)
public class MenuAccessSecurityFilter implements Filter {

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(MenuAccessSecurityFilter.class);

	/** Session */
	@Autowired
	HttpSession session;

	@Autowired
	MenuMapper menuMapper;

	@Autowired
	AuthorytyProperty authorytyProperty;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (!URICheckUtility.isNotChecked(request)) {
			HttpServletRequest httpRequest = ((HttpServletRequest) request);
			TblUser user = LoginUtility.getLoginUser();
			String uri = httpRequest.getRequestURI();

			session.removeAttribute(SessionKeyConstants.USER_MENU);
			if (user != null) {
				if (!valiidateUri(user, (HttpServletRequest) request)) {
					logger.error(
							"該当するURIにはアクセス出来ません。[uri:" + uri + ",MemberCode:" + user.getMemberCode() + ",memberTypeCd:"
									+ user.getMemberTypeCd() + ",customerType:" + user.getAuthTypeCd() + "]");
					throw new ServiceNotSupportedException("該当するURIにはアクセス出来ません。[uri:" + uri + "]");
				} else {
					String path = new UrlPathHelper().getPathWithinApplication((HttpServletRequest) request);
					List<TblMenuInfo> sessionMenuList = menuMapper.getMenuPath(user.getMemberCode(), path);
					if (sessionMenuList.isEmpty()) {
						sessionMenuList = menuMapper.getMenuPath(user.getMemberCode(), getDbUrl(path));
					}
					session.setAttribute(SessionKeyConstants.USER_MENU, sessionMenuList);

				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

	/**
	 * アクセス可能URIか判定する。
	 * 
	 * @param customer    ログイン情報
	 * @param uri         URI
	 * @param contextPath contextPath
	 * @return boolean true(権限あり)/false(権限なし)
	 */
	private boolean valiidateUri(TblUser user, HttpServletRequest request) {

		List<String> authorityList = new ArrayList<String>();
		authorityList.addAll(authorytyProperty.publicMenus());

		String path = new UrlPathHelper().getPathWithinApplication(request);

		if (MemberTypeCd.MEMBER.contentEquals(user.getMemberTypeCd())) {
			authorityList.addAll(authorytyProperty.loginUserMenus());
		} else if (MemberTypeCd.INSTRUCTOR.contentEquals(user.getMemberTypeCd())) {
			authorityList.addAll(authorytyProperty.instructorMenus());
		} else if (MemberTypeCd.MANAGER.contentEquals(user.getMemberTypeCd())) {
			authorityList.addAll(authorytyProperty.adminPublicMenus());
			List<TblMenuInfo> menuList = menuMapper.getMainMenuListByUserAndMenuAndUrl(user.getMemberCode(), null,
					getDbUrl(path));

			if (!menuList.isEmpty()) {
				authorityList.add(getAccessUrl(path));
			}
		} else {
			return false;
		}

		if (!match(authorityList, path)) {
			return false;
		}

		return true;
	}

	/**
	 * アクセス可能URIを判定する。
	 * 
	 * @param authPatternList URIパータンリスト
	 * @param uri             URI
	 * @param contextPath     contextPath
	 * @return boolean true(アクセス可)/false(アクセス不可)
	 */
	private boolean match(List<String> authPatternList, String path) {
		AntPathMatcher pathMathcer = new AntPathMatcher();
		for (String pattern : authPatternList) {
			if (pathMathcer.match(pattern, path)) {
				return true;
			}
		}
		return false;
	}

	private String getDbUrl(String path) {

		int index = StringUtils.ordinalIndexOf(path, "/", 4);
		if (index > 0) {
			path = path.substring(0, index);
		}
		return path;
	}

	private String getAccessUrl(String path) {
		path = getDbUrl(path);

		return path + "/**";
	}
}
