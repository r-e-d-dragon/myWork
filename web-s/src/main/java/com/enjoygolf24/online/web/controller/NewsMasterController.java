package com.enjoygolf24.online.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.online.web.form.NewsMasterListForm;

@Controller
@RequestMapping("/admin/system/news")
public class NewsMasterController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	private static final Logger logger = LoggerFactory.getLogger(NewsMasterController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(@ModelAttribute("newsMasterListForm") NewsMasterListForm form, HttpServletRequest request,
			Model model) {
		logger.info("Start newsMaster Controller");

		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		// TODO: service , mybatis

		logger.info("End newsMaster Controller");
		return "/front/news/index";
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String details(HttpServletRequest request, Model model) {
		logger.info("Start newsMaster Controller");

		logger.info("End newsMaster Controller");
		return "/front/news/details";
	}


}
