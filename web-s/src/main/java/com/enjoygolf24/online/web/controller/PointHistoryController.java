package com.enjoygolf24.online.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enjoygolf24.api.common.database.bean.TblPointHistory;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.CdMapService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberReservationManageService;
import com.enjoygolf24.api.service.PointService;
import com.enjoygolf24.online.web.form.PointHistoryListForm;
import com.github.pagehelper.PageInfo;


@Controller
@RequestMapping("/admin/member/pointHistory")
public class PointHistoryController {

	private static final Logger logger = LoggerFactory.getLogger(PointHistoryController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	PointService pointService;

	@Autowired
	AspService aspService;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	MemberReservationManageService memberReservationManageService;

	@Autowired
	private CdMapService cdMapService;

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String index(@ModelAttribute("pointHistoryListForm") PointHistoryListForm form,
			HttpServletRequest request,
	        Model model) {
		logger.info("Start pointHistory Controller");

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);
		
		List<TblPointHistory> pointHistoryList = pointService.getHistoryListAll(form.getAspCode(), form.getPageNo(),
				form.getPageSize());
		
		PageInfo<TblPointHistory> pageInfo = new PageInfo<TblPointHistory>(pointHistoryList);
		model.addAttribute("pageInfo", pageInfo);

		form.setPointHistoryList(pointHistoryList);

		model.addAttribute("modelPointHistoryListt", pointHistoryList);

		logger.info("End pointHistory Controller");
		return "/admin/member/pointHistory/index";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String memberHistoryList(@ModelAttribute("pointHistoryListForm") PointHistoryListForm form,
			Model model) {
		logger.info("Start pointHistory Controller");

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblPointHistory> pointHistoryList = pointService.getHistoryListAll(form.getAspCode(), form.getPageNo(),
				form.getPageSize());

		PageInfo<TblPointHistory> pageInfo = new PageInfo<TblPointHistory>(pointHistoryList);
		model.addAttribute("pageInfo", pageInfo);
		form.setPointHistoryList(pointHistoryList);

		model.addAttribute("modelPointHistoryListt", pointHistoryList);

		logger.info("End pointHistory Controller");
		
		return "/admin/member/pointHistory/index";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(@ModelAttribute @Validated(Search0.class) PointHistoryListForm form, BindingResult result,
			Model model) {
		logger.info("Start pointHistory Controller");

		if (result.hasErrors()) {
			return memberHistoryList(form, model);
		}

		initListForm(form, model);

		List<TblPointHistory> pointHistoryList = pointService.getHistoryList(form.getMemberCode(),
				form.getName(), form.getPhone(), form.getEmail(), form.getAspCode(), form.getPageNo(),
				form.getPageSize());

		PageInfo<TblPointHistory> pageInfo = new PageInfo<TblPointHistory>(pointHistoryList);
		model.addAttribute("pageInfo", pageInfo);
		form.setPointHistoryList(pointHistoryList);
		model.addAttribute("modelPointHistoryListt", pointHistoryList);

		logger.info("End pointHistory Controller");

		return "/admin/member/pointHistory/index";
	}

	@RequestMapping(value = "/list/prev", method = RequestMethod.POST)
	public String listPrev(@ModelAttribute @Validated(Search0.class) PointHistoryListForm form,
			BindingResult result, Model model) {
		logger.info("Start pointHistory Controller");

		if (result.hasErrors()) {
			return memberHistoryList(form, model);
		}

		initListForm(form, model);

		form.setPageNo(form.getPageNo() - 1);

		List<TblPointHistory> pointHistoryList = pointService.getHistoryList(form.getMemberCode(), form.getName(),
				form.getPhone(), form.getEmail(), form.getAspCode(), form.getPageNo(), form.getPageSize());

		PageInfo<TblPointHistory> pageInfo = new PageInfo<TblPointHistory>(pointHistoryList);
		model.addAttribute("pageInfo", pageInfo);
		form.setPointHistoryList(pointHistoryList);
		model.addAttribute("modelPointHistoryListt", pointHistoryList);

		logger.info("End pointHistory Controller");

		return "/admin/member/pointHistory/index";
	}

	@RequestMapping(value = "/list/next", method = RequestMethod.POST)
	public String listNext(@ModelAttribute @Validated(Search0.class) PointHistoryListForm form,
			BindingResult result, Model model) {
		logger.info("Start pointHistory Controller");

		if (result.hasErrors()) {
			return memberHistoryList(form, model);
		}

		initListForm(form, model);

		form.setPageNo(form.getPageNo() + 1);

		List<TblPointHistory> pointHistoryList = pointService.getHistoryList(form.getMemberCode(), form.getName(),
				form.getPhone(), form.getEmail(), form.getAspCode(), form.getPageNo(), form.getPageSize());

		PageInfo<TblPointHistory> pageInfo = new PageInfo<TblPointHistory>(pointHistoryList);
		model.addAttribute("pageInfo", pageInfo);
		form.setPointHistoryList(pointHistoryList);
		model.addAttribute("modelPointHistoryListt", pointHistoryList);

		logger.info("End pointHistory Controller");

		return "/admin/member/pointHistory/index";
	}



	private void initListForm(PointHistoryListForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		if (!aspService.getAspByName("本社").getAspCode().equals(aspCode)) {
			form.setAspCode(aspCode);
		}
	}


}
