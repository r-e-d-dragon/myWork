package com.enjoygolf24.online.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.enjoygolf24.api.common.database.bean.TblPointManage;
import com.enjoygolf24.api.common.database.bean.TblPointSettings;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.CdMapService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberReservationManageService;
import com.enjoygolf24.api.service.PointService;
import com.enjoygolf24.online.web.form.PointHistoryListForm;
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
	AspService aspService;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	MemberReservationManageService memberReservationManageService;

	@Autowired
	private CdMapService cdMapService;

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String index(@ModelAttribute("pointManageListForm") PointManageListForm form,
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

		String memberCode = form.getSelectedMemberCode();

		PointManageForm detailForm = new PointManageForm();
		initDetailForm(detailForm, model, memberCode);

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

		int pointVal = Integer.parseInt(form.getPointVariation());
		if (pointVal < form.getPointVariationMin() || pointVal > form.getPointVariationMax()) {
			result.rejectValue("pointVariation", "error.user",
					"ポイント値は" + form.getPointVariationMin() + "から" + form.getPointVariationMin() + "まで入力して下さい。");
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

	@RequestMapping(value = "/history", method = RequestMethod.POST)
	public String list(@ModelAttribute PointManageForm form,
			Model model) {

		PointHistoryListForm historyForm = new PointHistoryListForm();

		initListForm(historyForm, model, form.getMemberCode());

		List<TblPointManage> pointHistoryList = pointService.getHistoryList(historyForm.getMemberCode(),
				historyForm.getName(), historyForm.getRegisterUserCode(), historyForm.getRegisterUserName(),
				historyForm.getRegisteredMonth(), historyForm.getStartMonth(), historyForm.getAspCode(),
				historyForm.getPageNo(), historyForm.getPageSize());

		PageInfo<TblPointManage> pageInfo = new PageInfo<TblPointManage>(pointHistoryList);
		model.addAttribute("pageInfo", pageInfo);
		historyForm.setPointHistoryList(pointHistoryList);
		model.addAttribute("modelPointHistoryList", pointHistoryList);
		model.addAttribute("pointHistoryListForm", historyForm);

		return "/admin/member/pointHistory/index";
	}



	private void initListForm(PointManageListForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		if (!aspService.getAspByName("本社").getAspCode().equals(aspCode)) {
			form.setAspCode(aspCode);
		}
	}

	private void initDetailForm(PointManageForm form, Model model, String memberCode) {
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		TblPointSettings pointSettings = pointService.getPointSettings(LoginUtility.getLoginUser().getAuthTypeCd());
		form.init(pointService, memberCode, pointSettings);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
		String stringDate = dateFormat.format(new Date());

		MemberReservationManage reservation = memberReservationManageService.getMemberReservationInfo(memberCode,
				stringDate);

		form.setEventPointBalance(reservation.getValidEventPoint());
		form.setMonthlyPointBalance(reservation.getCurrentMonthlyPoint());
		form.setMonthlyPointBalanceNextMonth(reservation.getNextMonthlyPoint());

		model.addAttribute("currentMonth",
				reservation.getCurrentMonth().substring(reservation.getCurrentMonth().length() - 2));
		model.addAttribute("nextMonth", reservation.getNextMonth().substring(reservation.getNextMonth().length() - 2));


		setModelMapper(model);
	}

	private void setModelMapper(Model model) {
		model.addAttribute("pointAppliedMonthCdMap", cdMapService.createMap(CodeTypeCd.POINT_APPLIED_MONTH));
		model.addAttribute("pointCategoryCdMap", cdMapService.createMap(CodeTypeCd.POINT_CATEGORY_CD));

	}

	private void initListForm(PointHistoryListForm form, Model model, String memberCode) {
		form.setMemberCode(memberCode);
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		if (!aspService.getAspByName("本社").getAspCode().equals(aspCode)) {
			form.setAspCode(aspCode);
		}
	}

}
