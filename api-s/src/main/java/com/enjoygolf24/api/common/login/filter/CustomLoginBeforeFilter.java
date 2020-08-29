
package com.enjoygolf24.api.common.login.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;


public class CustomLoginBeforeFilter extends GenericFilterBean {

    /** ロガー*/
    private static final Logger logger = LoggerFactory.getLogger(CustomLoginBeforeFilter.class);


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpSession session = httpRequest.getSession();

		String uri = httpRequest.getRequestURI();
		String[] uriArray = uri.split("/");
		List<String> uriList = Arrays.asList(uriArray);


        chain.doFilter(request, response);
    }

}
