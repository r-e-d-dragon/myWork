package com.enjoygolf24.online.web.security;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.enjoygolf24.api.common.code.MemberTypeCd;
import com.enjoygolf24.api.common.utility.LoginUtility;

@Component
public class CommonLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonLoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
		String message = "";
		logger.info(message);
		if (MemberTypeCd.MANAGER.equals(LoginUtility.getLoginUser().getMemberTypeCd())) {
			getRedirectStrategy().sendRedirect(request, response, "/admin/main");
		} else {
			getRedirectStrategy().sendRedirect(request, response, "/user/reservation/info");
		}

    }
}
