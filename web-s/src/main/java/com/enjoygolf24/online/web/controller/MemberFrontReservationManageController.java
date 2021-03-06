package com.enjoygolf24.online.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.enjoygolf24.api.common.code.ReservationStatusCd;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.jpa.repository.CodeMasterRepository;
import com.enjoygolf24.api.common.database.jpa.repository.ViewReservationPointTimeTableRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.database.mybatis.bean.ReservationPointTimeTableInfo;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberReservationManageService;
import com.enjoygolf24.online.web.form.MemberInfoManageListForm;
import com.enjoygolf24.online.web.form.MemberReservationManageListForm;
import com.enjoygolf24.online.web.form.MemberReservationRegisterForm;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;

@Controller
@RequestMapping("/admin/booking/front")
public class MemberFrontReservationManageController {

	private static final Logger logger = LoggerFactory.getLogger(MemberFrontReservationManageController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	MemberReservationManageService memberReservationManageService;

	@Autowired
	AspService aspService;

	@Autowired
	CodeMasterRepository codeMasterRepository;


	@Autowired
	ViewReservationPointTimeTableRepository viewReservationPointTimeTableRepository;

	@RequestMapping(value = "")
	public String memberReservationList(
			@ModelAttribute("memberReservationManageListForm") MemberReservationManageListForm form,
			@ModelAttribute("memberReservationRegisterForm") MemberReservationRegisterForm registerForm,
			@ModelAttribute("memberInfoManageListForm") MemberInfoManageListForm memberForm, BindingResult result,
			Model model) {
		logger.info("Start memberReservationList Controller");

		// TODO - ログインユーザ
		memberForm.setSelectedMemberCode("admin2@user.com");

		// 会員コード
		if (!StringUtil.isEmpty(memberForm.getSelectedMemberCode())) {
			form.setMemberCode(memberForm.getSelectedMemberCode());
		}

		TblUser member = memberInfoManageService.selectMember(form.getMemberCode());

		initListForm(form, model);

		// TODO
		form.setMonthlyPoint("150");
		form.setEventPoint("50");

		// 予約日 - 初期表示：当日
		if (StringUtil.isEmpty(form.getReservationDate())) {
			form.setReservationDate(DateUtility.getCurrentDateTime(DateUtility.DATE_FORMAT));
		}

		// タイム表取得
		List<ReservationPointTimeTableInfo> reservationPointTimeTable = memberReservationManageService
				.getViewReservationPonitTimeTableInfo(DateUtility.getDate(form.getReservationDate()),
						DateUtility.getDate(form.getReservationDate()));
		model.addAttribute("reservationPointTimeTable", reservationPointTimeTable);
		// 休日、祝日判定
		form.setDateKind(reservationPointTimeTable.get(0).getHolydayTypeCd());
		model.addAttribute("dateType",
				codeMasterRepository.findByCodeTypeAndCd(CodeTypeCd.HOLIDAY_TYPE_CD, form.getDateKind()).getName());

		// 予約情報取得
		List<MemberReservationManage> memberReservationList = memberReservationManageService.getReservationList(
				LoginUtility.getLoginUser().getAspCode(), form.getReservationDate(), form.getDateKind());

		// TODO 打席番号 － コードマスタ
		List<String> batNumbers = codeMasterRepository.findByCodeTypeOrderByCd("990").stream().map(p -> p.getName())
				.collect(Collectors.toList());
		model.addAttribute("batNumbers", batNumbers);

		for (ReservationPointTimeTableInfo table : reservationPointTimeTable) {
			List<MemberReservationManage> reservationList = new ArrayList<MemberReservationManage>();

			String timeSlotName = DateUtility.toTimeString(table.getStartTime()) + "~"
					+ DateUtility.toTimeString(table.getEndTime());
			table.setTimeSlotName(timeSlotName);

			// 打席数分
			for (String batNo : batNumbers) {
				MemberReservationManage rev = new MemberReservationManage();
				rev.setBatNumber(batNo);
				Optional<MemberReservationManage> manage = memberReservationList.stream()
						.filter(p -> p.getBatNumber().equals(batNo)
								&& p.getTimeSlotName().equals(timeSlotName))
						.findFirst();
				if (manage.isPresent()) {
					rev.setBatNumberCd(manage.get().getBatNumberCd());
					rev.setConsumedPoint(manage.get().getConsumedPoint());
					rev.setMemberCode(manage.get().getMemberCode());
					rev.setEmptyFlag(manage.get().getEmptyFlag());
					rev.setExpireFlag(manage.get().getExpireFlag());
					rev.setReservationNumber(manage.get().getReservationNumber());
				}
				reservationList.add(rev);
			}
			table.setReservationList(reservationList);
		}

		model.addAttribute("monthlyPoint", form.getMonthlyPoint());
		model.addAttribute("eventPoint", form.getEventPoint());

		model.addAttribute("memberCode", form.getMemberCode());
		model.addAttribute("memberName", member.getLastName() + " " + member.getFirstName());

		logger.info("End memberReservationList Controller");
		return "/admin/booking/front/index";
	}

	/**
	 * 予約検索
	 * 
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String initMemberReservationSearchList(
			@ModelAttribute @Validated(Search0.class) MemberReservationManageListForm form, BindingResult result,
			Model model) {
		logger.info("Start initMemberReservationSearchList Controller");

		form.setReservationDate(DateUtility.getCurrentDateTime(DateUtility.DATE_FORMAT));
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);
		form.setStatus(ReservationStatusCd.STATUS_FIXED);
		
		serchCommonLogic(form, model);

		logger.info("End initMemberReservationSearchList Controller");
		return "/admin/booking/search/index";
	}

	/**
	 * 予約検索
	 * 
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchReservationList(
			@ModelAttribute @Validated(Search0.class) MemberReservationManageListForm form, BindingResult result,
			Model model) {
		logger.info("Start searchReservationList Controller");

		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);
		form.setStatus(ReservationStatusCd.STATUS_FIXED);

		serchCommonLogic(form, model);

		logger.info("End searchReservationList Controller");
		return "/admin/booking/search/index";
	}

	/**
	 * リストフォーム初期処理
	 * 
	 * @param form
	 * @return
	 */
	private void initListForm(MemberReservationManageListForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		TblAsp asp = aspService.getAsp(aspCode);

		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());

		model.addAttribute("aspName", asp.getAspName());
	}

	/**
	 * 検索共通ロジック
	 * 
	 * @param form
	 * @param model
	 * @return
	 */
	private void serchCommonLogic(MemberReservationManageListForm form, Model model) {

		initListForm(form, model);

		List<MemberReservationManage> memberReservationList = memberReservationManageService.getMemberReservationList(
				form.getReservationNumber(), form.getMemberCode(), LoginUtility.getLoginUser().getAspCode(),
				form.getReservationDate(), form.getStatus(), false, form.getPageNo(),
				form.getPageSize());

		PageInfo<MemberReservationManage> pageInfo = new PageInfo<MemberReservationManage>(memberReservationList);
		model.addAttribute("pageInfo", pageInfo);
		form.setReservationList(memberReservationList);

		model.addAttribute("modelMemberReservationList", memberReservationList);
	}
}
