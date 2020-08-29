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

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.PointTypeCd;
import com.enjoygolf24.api.common.database.bean.TblPointMonthly;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.service.CdMapService;
import com.enjoygolf24.api.service.PointService;
import com.enjoygolf24.online.web.form.PointManageForm;
import com.enjoygolf24.online.web.form.PointManageListForm;
import com.github.pagehelper.PageInfo;


@Controller
@RequestMapping("/admin/member/point")
public class PointManageController {

	private static final Logger logger = LoggerFactory.getLogger(PointManageController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	PointService pointService;

	@Autowired
	private CdMapService cdMapService;

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String aspListIndex(@ModelAttribute("pointManageListForm") PointManageListForm form,
			HttpServletRequest request,
	        Model model) {
		logger.info("Start point Controller");

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblUser> memberList = pointService.getMemberListAll(form.getAspCode(), form.getPageNo(),
				form.getPageSize());
		form.setMemberList(memberList);

		model.addAttribute("modelMemberList", memberList);

		logger.info("End point Controller");
		return "/admin/member/point/index";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String memberInfoManageList(@ModelAttribute("pointManageListForm") PointManageListForm form,
			Model model) {
		logger.info("Start point Controller");

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblUser> memberList = pointService.getMemberListAll(form.getAspCode(), form.getPageNo(),
				form.getPageSize());
		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);

		model.addAttribute("modelMemberList", memberList);

		logger.info("End point Controller");
		
		return "/admin/member/point/index";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(@ModelAttribute @Validated(Search0.class) PointManageListForm form, BindingResult result,
			Model model) {
		logger.info("Start point Controller");

		if (result.hasErrors()) {
			return memberInfoManageList(form, model);
		}

		initListForm(form, model);

		List<TblUser> memberList = pointService.getMemberList(form.getMemberCode(),
				form.getName(), form.getPhone(), form.getEmail(), form.getAspCode(), form.getPageNo(),
				form.getPageSize());

		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End point Controller");

		return "/admin/member/point/index";
	}

	@RequestMapping(value = "/list/prev", method = RequestMethod.POST)
	public String listPrev(@ModelAttribute @Validated(Search0.class) PointManageListForm form,
			BindingResult result, Model model) {
		logger.info("Start point Controller");

		if (result.hasErrors()) {
			return memberInfoManageList(form, model);
		}

		initListForm(form, model);

		form.setPageNo(form.getPageNo() - 1);

		List<TblUser> memberList = pointService.getMemberList(form.getMemberCode(), form.getName(), form.getPhone(),
				form.getEmail(), form.getAspCode(), form.getPageNo(), form.getPageSize());

		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End point Controller");

		return "/admin/member/point/index";
	}

	@RequestMapping(value = "/list/next", method = RequestMethod.POST)
	public String listNext(@ModelAttribute @Validated(Search0.class) PointManageListForm form,
			BindingResult result, Model model) {
		logger.info("Start point Controller");

		if (result.hasErrors()) {
			return memberInfoManageList(form, model);
		}

		initListForm(form, model);

		form.setPageNo(form.getPageNo() + 1);

		List<TblUser> memberList = pointService.getMemberList(form.getMemberCode(), form.getName(), form.getPhone(),
				form.getEmail(), form.getAspCode(), form.getPageNo(), form.getPageSize());

		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End point Controller");

		return "/admin/member/point/index";
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public String details(@ModelAttribute PointManageListForm form, Model model) {

		PointManageForm detailForm = new PointManageForm();
		initDetailForm(detailForm, model, form.getSelectedMemberCode());

		model.addAttribute("pointManageForm", detailForm);
		return "/admin/member/point/details";
	}

	@RequestMapping(value = "/detailsIndex", method = RequestMethod.POST)
	public String detailsIndex(@ModelAttribute PointManageForm form, Model model) {

		initDetailForm(form, model, form.getMemberCode());

		model.addAttribute("pointManageForm", form);
		return "/admin/member/point/details";
	}

	@RequestMapping(value = "/details/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) PointManageForm form, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			setModelMapper(model);
			model.addAttribute("pointManageForm", form);
			return detailsIndex(form, model);
		}

		pointService.insertPoint(form.createPointManageServiceBean());

		initDetailForm(form, model, form.getMemberCode());
		result.rejectValue("hasChanged", "error.user", "ポイント登録完了しました。");
		setModelMapper(model);
		model.addAttribute("pointManageForm", form);

		return "/admin/member/point/details";
	}

	@RequestMapping(value = "/history_monthly", method = RequestMethod.POST)
	public String historyMonthly(@ModelAttribute PointManageForm form, Model model) {

		initDetailForm(form, model, form.getMemberCode());
		model.addAttribute("pointManageForm", form);

		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblPointMonthly> pointMonthlyList = pointService.getHistoryMonthly(form.getMemberCode(), form.getPageNo(),
				form.getPageSize());
		PageInfo<TblPointMonthly> pageInfo = new PageInfo<TblPointMonthly>(pointMonthlyList);
		model.addAttribute("pageInfo", pageInfo);
		form.setPointMonthlyList(pointMonthlyList);

		model.addAttribute("modelPointMonthlyList", pointMonthlyList);

		return "/admin/member/point/history_monthly";
	}

	@RequestMapping(value = "/history_monthly/prev", method = RequestMethod.POST)
	public String historyMonthlyPrev(@ModelAttribute PointManageForm form, Model model) {

		initDetailForm(form, model, form.getMemberCode());
		model.addAttribute("pointManageForm", form);

		form.setPageNo(form.getPageNo() - 1);

		List<TblPointMonthly> pointMonthlyList = pointService.getHistoryMonthly(form.getMemberCode(), form.getPageNo(),
				form.getPageSize());
		PageInfo<TblPointMonthly> pageInfo = new PageInfo<TblPointMonthly>(pointMonthlyList);
		model.addAttribute("pageInfo", pageInfo);
		form.setPointMonthlyList(pointMonthlyList);

		model.addAttribute("modelPointMonthlyList", pointMonthlyList);

		return "/admin/member/point/history_monthly";
	}

	@RequestMapping(value = "/history_monthly/next", method = RequestMethod.POST)
	public String historyMonthlyNext(@ModelAttribute PointManageForm form, Model model) {

		initDetailForm(form, model, form.getMemberCode());
		model.addAttribute("pointManageForm", form);

		form.setPageNo(form.getPageNo() + 1);

		List<TblPointMonthly> pointMonthlyList = pointService.getHistoryMonthly(form.getMemberCode(), form.getPageNo(),
				form.getPageSize());
		PageInfo<TblPointMonthly> pageInfo = new PageInfo<TblPointMonthly>(pointMonthlyList);
		model.addAttribute("pageInfo", pageInfo);
		form.setPointMonthlyList(pointMonthlyList);

		model.addAttribute("modelPointMonthlyList", pointMonthlyList);

		return "/admin/member/point/history_monthly";
	}


	private void initListForm(PointManageListForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		// TODO: set null if asp code were head office -> modify mapper
		form.setAspCode(aspCode);
	}

	private void initDetailForm(PointManageForm form, Model model, String memberCode) {
		form.init(pointService, memberCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		form.setCarriablePointBalance(pointService.getCarriablePointBalance(memberCode));
		form.setMonthlyPointBalance(pointService.getMonthlyPointBalance(memberCode));
		setModelMapper(model);
	}

	private void setModelMapper(Model model) {
		model.addAttribute("pointExpirationTermCdMap", cdMapService.createMap(CodeTypeCd.PONT_EXPIRATION_TERM_CD));
		model.addAttribute("monthlyPointTypeCdMap", cdMapService.createMapOnlyIncludes(CodeTypeCd.PONT_TYPE_CD,
				PointTypeCd.MONTLY_POINT, PointTypeCd.BIRHDAY, PointTypeCd.INTRODUCTION, PointTypeCd.CUSTOM));
		model.addAttribute("carriablePointTypeCdMap",
				cdMapService.createMapOnlyIncludes(CodeTypeCd.PONT_TYPE_CD, PointTypeCd.OLDBIE, PointTypeCd.CUSTOM));

	}

}
