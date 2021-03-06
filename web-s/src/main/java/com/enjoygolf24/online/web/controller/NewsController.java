package com.enjoygolf24.online.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/public/news")
public class NewsController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		logger.info("Start news Controller");

		logger.info("End news Controller");
		return "/front/news/index";
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String details(HttpServletRequest request, Model model) {
		logger.info("Start news Controller");

		logger.info("End news Controller");
		return "/front/news/details";
	}


}
