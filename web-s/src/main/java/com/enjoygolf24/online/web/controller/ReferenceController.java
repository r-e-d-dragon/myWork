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
@RequestMapping("/public/reference")
public class ReferenceController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;


	private static final Logger logger = LoggerFactory.getLogger(ReferenceController.class);

	@RequestMapping(value = "/introduce", method = RequestMethod.GET)
	public String introduce(HttpServletRequest request, Model model) {
		logger.info("Start introduce Controller");

		logger.info("End introduce Controller");
		return "/front/reference/introduce";
	}

	@RequestMapping(value = "/safetyMeasures", method = RequestMethod.GET)
	public String safetyMeasures(HttpServletRequest request, Model model) {
		logger.info("Start safetyMeasures Controller");

		logger.info("End safetyMeasures Controller");
		return "/front/reference/safetyMeasures";
	}

	@RequestMapping(value = "/price", method = RequestMethod.GET)
	public String price(HttpServletRequest request, Model model) {
		logger.info("Start price Controller");

		logger.info("End price Controller");
		return "/front/reference/price";
	}

	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public String terms(HttpServletRequest request, Model model) {
		logger.info("Start terms Controller");

		logger.info("End terms Controller");
		return "/front/reference/terms";
	}

	@RequestMapping(value = "/membership", method = RequestMethod.GET)
	public String membership(HttpServletRequest request, Model model) {
		logger.info("Start membership Controller");

		logger.info("End membership Controller");
		return "/front/reference/membership";
	}

	@RequestMapping(value = "/companyInfo", method = RequestMethod.GET)
	public String companyInfo(HttpServletRequest request, Model model) {
		logger.info("Start companyInfo Controller");

		logger.info("End companyInfo Controller");
		return "/front/reference/companyInfo";
	}


}
