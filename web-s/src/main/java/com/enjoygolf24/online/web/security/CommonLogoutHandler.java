package com.enjoygolf24.online.web.security;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CommonLogoutHandler implements LogoutSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(CommonLogoutHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

		logger.info("");

		String URL = request.getContextPath() + "/public";

        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect(URL);
    }

}
