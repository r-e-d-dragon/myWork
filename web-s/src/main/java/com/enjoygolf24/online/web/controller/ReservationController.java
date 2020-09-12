package com.enjoygolf24.online.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.database.mybatis.bean.PointManage;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberReservationManageService;
import com.enjoygolf24.online.web.form.ReservationInfoListForm;
import com.enjoygolf24.online.web.form.ReservationMakingListForm;

@Controller
@RequestMapping("/user/reservation")
public class ReservationController {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	MemberReservationManageService memberReservationManageService;


	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String introduce(@ModelAttribute("reservationInfoForm") ReservationInfoListForm form,
			HttpServletRequest request,
			Model model) {
		logger.info("Start reservation Controller");

		initPointInfoModel(model);

		logger.info("End reservation Controller");
		return "/front/reservation/info";
	}

	@RequestMapping(value = "/make", method = RequestMethod.GET)
	public String safetyMeasures(@ModelAttribute("reservationMakingForm") ReservationMakingListForm form,
			HttpServletRequest request, Model model) {
		logger.info("Start reservation Controller");

		initPointInfoModel(model);

		logger.info("End reservation Controller");
		return "/front/reservation/make";
	}

	private void initPointInfoModel(Model model) {
		MemberReservationManage reservation = new MemberReservationManage();

		// 会員情報取得
		TblUser member = memberInfoManageService.selectMember(LoginUtility.getLoginUser().getMemberCode());
		reservation.setTblUser(member);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
		String stringDate = dateFormat.format(new Date());

		// 月ポイント情報取得
		List<PointManage> totMonPointList = memberReservationManageService
				.getMemberPointManageList(LoginUtility.getLoginUser().getMemberCode(), PointCategoryCd.MONTLY_POINT, null);
		List<PointManage> validMonPointList = memberReservationManageService
				.getMemberPointManageList(LoginUtility.getLoginUser().getMemberCode(), PointCategoryCd.MONTLY_POINT, stringDate);

		// 月ポイント情報取得
		List<PointManage> totEvtPointList = memberReservationManageService
				.getMemberPointManageList(LoginUtility.getLoginUser().getMemberCode(), PointCategoryCd.EVENT_POINT, null);
		List<PointManage> validEvtPointList = memberReservationManageService
				.getMemberPointManageList(LoginUtility.getLoginUser().getMemberCode(), PointCategoryCd.EVENT_POINT, stringDate);

		reservation.setTotalMonthlyPoint(totMonPointList.stream().mapToInt(x -> x.getPointAmount()).sum());
		reservation.setTotalEventPoint(totEvtPointList.stream().mapToInt(x -> x.getPointAmount()).sum());
		reservation.setValidMonthlyPoint(validMonPointList.stream().mapToInt(x -> x.getPointAmount()).sum());
		reservation.setValidEventPoint(validEvtPointList.stream().mapToInt(x -> x.getPointAmount()).sum());

		model.addAttribute("totalMonthlyPoint", reservation.getTotalMonthlyPoint());
		model.addAttribute("totalEventPoint", reservation.getTotalEventPoint());
		model.addAttribute("validMonthlyPoint", reservation.getValidMonthlyPoint());
		model.addAttribute("validEventPoint", reservation.getValidEventPoint());

	}

}
