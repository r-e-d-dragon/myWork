package com.enjoygolf24.online.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "/")
    public String menu(HttpServletRequest request, Model model) {
		logger.info("Start Index Controller");
		
		logger.info("End Index Controller");
        return "/front/index";
    }

	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		logger.info("Start login Controller");

		logger.info("End login Controller");
		return "/front/login";
	}
}
