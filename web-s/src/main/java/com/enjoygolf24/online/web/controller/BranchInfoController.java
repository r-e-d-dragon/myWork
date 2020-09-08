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
@RequestMapping("/public/branchInfo")
public class BranchInfoController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	private static final Logger logger = LoggerFactory.getLogger(ReferenceController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		logger.info("Start BranchInfo Controller");

		logger.info("End BranchInfo Controller");
		return "/front/branchInfo/index";
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String details(HttpServletRequest request, Model model) {
		logger.info("Start BranchInfo Controller");

		logger.info("End BranchInfo Controller");
		return "/front/branchInfo/details";
	}

}
