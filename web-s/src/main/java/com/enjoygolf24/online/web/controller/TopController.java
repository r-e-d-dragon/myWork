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
@RequestMapping("/public")
public class TopController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;


	private static final Logger logger = LoggerFactory.getLogger(TopController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		logger.info("Start top Controller");

		logger.info("End top Controller");
		return "/front/index";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String top(HttpServletRequest request, Model model) {
		logger.info("Start top Controller");

		logger.info("End top Controller");
		return "/front/index";
	}

}
