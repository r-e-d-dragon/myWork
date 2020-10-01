package com.enjoygolf24.online.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.code.ReservationStatusCd;
import com.enjoygolf24.api.common.constants.ReservationContants;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.jpa.repository.CodeMasterRepository;
import com.enjoygolf24.api.common.database.jpa.repository.ViewReservationPointTimeTableRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.database.mybatis.bean.PointManage;
import com.enjoygolf24.api.common.database.mybatis.bean.ReservationPointTimeTableInfo;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberReservationManageService;
import com.enjoygolf24.online.web.form.MemberReservationRegisterForm;
import com.enjoygolf24.online.web.form.ReservationInfoListForm;
import com.enjoygolf24.online.web.form.ReservationMakingListForm;
import com.github.pagehelper.util.StringUtil;

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

	@Autowired
	AspService aspService;

	@Autowired
	CodeMasterRepository codeMasterRepository;

	@Autowired
	ViewReservationPointTimeTableRepository viewReservationPointTimeTableRepository;


	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String introduce(@ModelAttribute("reservationInfoForm") ReservationInfoListForm form,
			HttpServletRequest request,
			Model model) {
		logger.info("Start reservation Controller");

		setMemberPointModel(model);

		logger.info("End reservation Controller");
		return "/front/reservation/info";
	}

	@RequestMapping(value = "/make")
	public String makeReservation(
			@ModelAttribute("reservationMakingForm") ReservationMakingListForm form,
			@ModelAttribute("memberReservationRegisterForm") MemberReservationRegisterForm registerForm,
			HttpServletRequest request, Model model) {
		logger.info("Start reservation Controller");

		initListForm(form, model);

		List<ReservationPointTimeTableInfo> reservationPointTimeTable = setTimeTable(form, model);

		initRegisterForm(form, registerForm, model);

		model.addAttribute("reservationPointTimeTable", reservationPointTimeTable);
		model.addAttribute("reservationMakingForm", form);
		model.addAttribute("memberReservationRegisterForm", registerForm);

		logger.info("End reservation Controller");
		return "/front/reservation/make";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) MemberReservationRegisterForm form, BindingResult result,
			HttpServletRequest request, Model model) {
		logger.info("Start finish Controller");

		ReservationMakingListForm listForm = new ReservationMakingListForm();
		initListForm(listForm, model);
		listForm.setReservationDate(form.getReservationDate());
		listForm.setReservationTime(form.getSearchTime());

		if (result.hasErrors()) {
			model.addAttribute("memberReservationRegisterForm", form);
			return makeReservation(listForm, form, request, model);
		}

		List<MemberReservationManage> checkRev = memberReservationManageService.getMemberReservationAllList(null, null,
				form.getAspCode(), form.getBatNumber(), form.getReservationDate(), form.getReservationTime(),
				ReservationStatusCd.STATUS_FIXED, false);
		if (!checkRev.isEmpty()) {
			model.addAttribute("memberReservationRegisterForm", form);
			result.rejectValue("memberCode", "error.memberCode", "{0} : 時間超過のため、既に予約されています。");
			return makeReservation(listForm, form, request, model);
		}

		if (!form.isValid()) {
			model.addAttribute("memberReservationRegisterForm", form);
			result.rejectValue("validMonthlyPoint", "error.validMonthlyPoint",
					"{0} : ポイントが足りないため、予約出来ません。　管理者に問い合わせして下さい。");
			return makeReservation(listForm, form, request, model);
		}

		int consumedPoint = Integer.valueOf(form.getConsumedPoint());
		if (PointCategoryCd.MONTHLY_POINT.equals(form.getPointCategoryCode())) {
			if (consumedPoint > form.getValidMonthlyPoint()) {
				model.addAttribute("memberReservationRegisterForm", form);
				result.rejectValue("validMonthlyPoint", "error.validMonthlyPoint", "{0} : 月ポイントが足りないため、予約出来ません。");
				return makeReservation(listForm, form, request, model);
			}
			// 月ポイント情報取得
			List<PointManage> validMonPointList = memberReservationManageService.getMemberPointManageList(
					form.getMemberCode(), PointCategoryCd.MONTHLY_POINT, form.getReservationDate());
			if (consumedPoint > validMonPointList.stream().mapToInt(x -> x.getPointAmount()).sum()) {
				model.addAttribute("memberReservationRegisterForm", form);
				result.rejectValue("validMonthlyPoint", "error.validMonthlyPoint", "{0} : 月ポイントが足りないため、予約出来ません。");
				return makeReservation(listForm, form, request, model);
			}
		}
		if (PointCategoryCd.EVENT_POINT.equals(form.getPointCategoryCode())) {
			if (consumedPoint > form.getValidEventPoint()) {
				model.addAttribute("memberReservationRegisterForm", form);
				result.rejectValue("pointCategoryCode", "error.pointCategoryCode", "{0} : イベントポイントが足りないため、予約出来ません。");
				return makeReservation(listForm, form, request, model);
			}
			// 月ポイント情報取得
			List<PointManage> validEvtPointList = memberReservationManageService.getMemberPointManageList(
					form.getMemberCode(), PointCategoryCd.EVENT_POINT, form.getReservationDate());
			if (consumedPoint > validEvtPointList.stream().mapToInt(x -> x.getPointAmount()).sum()) {
				model.addAttribute("memberReservationRegisterForm", form);
				result.rejectValue("pointCategoryCode", "error.pointCategoryCode", "{0} : イベントポイントが足りないため、予約出来ません。");
				return makeReservation(listForm, form, request, model);
			}
		}

		TblUser member = memberInfoManageService.selectMember(form.getMemberCode());
		if (member == null) {
			return makeReservation(listForm, form, request, model);
		}

		// 登録完了
		form.setStatus(ReservationStatusCd.STATUS_FIXED);

		String reservationId = "";
		try {
			reservationId = memberReservationManageService
					.MemberReservationRegister(form.createMemberReservationServiceBean());
		} catch (IllegalAccessException | InvocationTargetException e) {
			return makeReservation(listForm, form, request, model);
		}
		form.setReservationId(reservationId);


		// 予約番号
		String reservationNumber = DateUtility.getCurrentDateTime("yyyyMMdd-HH");
		if (StringUtil.isEmpty(member.getPhone())) {
			reservationNumber += "-" + DateUtility.getCurrentDateTime("MISS");
		} else {
			reservationNumber += "-" + member.getPhone().substring(member.getPhone().length() - 4);
		}
		form.setReservationNumber(reservationNumber);

		// 予約情報更新
		memberReservationManageService.MemberReservationUpdate(form.createMemberReservationServiceBean());

		List<ReservationPointTimeTableInfo> reservationPointTimeTable = setTimeTable(listForm, model);

		model.addAttribute("reservationPointTimeTable", reservationPointTimeTable);
		model.addAttribute("reservationMakingForm", listForm);
		form.setHasChanged(true);

		model.addAttribute("memberReservationRegisterForm", form);
		setMemberPointModel(model);
		

		logger.info("End finish Controller");
		return "/front/reservation/make";
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancle(@ModelAttribute @Validated(Search0.class) MemberReservationRegisterForm form,
			BindingResult result, Model model) {
		logger.info("Start cancelFinish Controller");

		// initRegisterForm(form, model);
		ReservationMakingListForm listForm = new ReservationMakingListForm();
		initListForm(listForm, model);
		listForm.setReservationDate(form.getReservationDate());
		listForm.setReservationTime(form.getSearchTime());

		TblUser member = memberInfoManageService.selectMember(form.getMemberCode());
		if (member == null) {
			model.addAttribute("memberReservationRegisterForm", form);
			result.rejectValue("memberCode", "error.memberCode", "{0} : 会員情報が存在しません。　管理者に問い合わせして下さい。");
			return makeReservation(listForm, form, request, model);
		}

		// 予約取消
		form.setStatus(ReservationStatusCd.STATUS_CANCLE);
		String reservationId = memberReservationManageService
				.MemberReservationCancle(form.createMemberReservationServiceBean());
		result.rejectValue("hasChanged", "error.hasChanged", "予約取消が完了できました。");


		makeReservation(listForm, form, request, model);

		logger.info("End cancelFinish Controller");
		return "/front/reservation/make";
	}


	private void initListForm(ReservationMakingListForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		TblAsp asp = aspService.getAsp(aspCode);

		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());

		model.addAttribute("aspName", asp.getAspName());
	}

	private void initRegisterForm(ReservationMakingListForm listForm, MemberReservationRegisterForm form, Model model) {
		form.setValid(false);
		form.setHasChanged(false);

		String aspCode = LoginUtility.getLoginUser().getAspCode();

		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		form.setMemberCode(form.getLoginUserCd());
		form.setReservationDate(listForm.getReservationDate());
		form.setSearchTime(listForm.getReservationTime());

		MemberReservationManage reservation = memberReservationManageService
				.getMemberReservationInfo(LoginUtility.getLoginUser().getMemberCode(), form.getReservationDate());

		if (reservation != null) {
			form.setValid(true);
			listForm.setReservationCnt(reservation.getReservationList().size());
		}

		model.addAttribute("currentMonthlyPoint", reservation.getCurrentMonthlyPoint());
		model.addAttribute("nextMonthlyPoint", reservation.getNextMonthlyPoint());
		model.addAttribute("currentMonth",
				reservation.getCurrentMonth().substring(reservation.getCurrentMonth().length() - 2));
		model.addAttribute("nextMonth", reservation.getNextMonth().substring(reservation.getNextMonth().length() - 2));
		model.addAttribute("validEventPoint", reservation.getValidEventPoint());

		model.addAttribute("reservationList", reservation.getReservationList());

		form.setValidMonthlyPoint(reservation.getValidMonthlyPoint());
		form.setMonthlyPoint(Integer.toString(reservation.getCurrentMonthlyPoint()));
		form.setValidEventPoint(reservation.getValidEventPoint());
		form.setEventPoint(Integer.toString(reservation.getValidEventPoint()));
		form.setPenaltyPoint(reservation.getPenaltyPoint() != null ? reservation.getPenaltyPoint() : "0");

		String reservationId = "";

		if (StringUtil.isEmpty(reservationId)) {
			// 仮登録
			form.setStatus(ReservationStatusCd.STATUS_TEMP);
			// 仮予約番号
			String reservationNumber = form.getReservationDate().replace("/", "") + "-00-0000";
			form.setReservationNumber(reservationNumber);

			form.setPointCategoryCode("");
			form.setGradeTypeCd(form.getGradeTypeCd());

		}
		form.setReservationId(reservationId);
		form.setLimitReservationCount(reservation.getLimitReservationCount());
		form.setLimitMonthlyReservationCount(reservation.getLimitMonthlyReservationCount());
		form.setLimitEventReservationCount(reservation.getLimitEventReservationCount());
		form.setLimitReservationPoint(reservation.getLimitReservationPoint());

		model.addAttribute("reservationTotalMaxCount", ReservationContants.RESERVATION_TOTAL_MAX_COUNT);

	}

	private void setMemberPointModel(Model model) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
		String stringDate = dateFormat.format(new Date());
		MemberReservationManage reservation = memberReservationManageService
				.getMemberReservationInfo(LoginUtility.getLoginUser().getMemberCode(), stringDate);

		model.addAttribute("currentMonthlyPoint", reservation.getCurrentMonthlyPoint());
		model.addAttribute("nextMonthlyPoint", reservation.getNextMonthlyPoint());
		model.addAttribute("currentMonth",
				reservation.getCurrentMonth().substring(reservation.getCurrentMonth().length() - 2));
		model.addAttribute("nextMonth", reservation.getNextMonth().substring(reservation.getNextMonth().length() - 2));
		model.addAttribute("validEventPoint", reservation.getValidEventPoint());

		model.addAttribute("reservationList", reservation.getReservationList());


	}

	private List<ReservationPointTimeTableInfo> setTimeTable(ReservationMakingListForm form, Model model) {
		// 予約日 - 初期表示：当日
		if (StringUtil.isEmpty(form.getReservationDate())) {
			form.setReservationDate(DateUtility.getCurrentDateTime(DateUtility.DATE_FORMAT));
		}

		// 予約範囲指定 今月一日から翌月末日まで
		LocalDate now = LocalDate.now();

		if (!StringUtils.isEmptyOrWhitespace(form.getReservationDate())) {
			form.setReservationDate(form.getReservationDate().replace('-', '/'));
		}

		// タイム表取得
		List<ReservationPointTimeTableInfo> reservationPointTimeTable = memberReservationManageService
				.getViewReservationPonitTimeTableInfo(DateUtility.getDate(form.getReservationDate()),
						DateUtility.getDate(form.getReservationDate()));

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
						.filter(p -> p.getBatNumber().equals(batNo) && p.getTimeSlotName().equals(timeSlotName))
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

		// if has reservationTime for a condition
		if (!StringUtils.isEmptyOrWhitespace(form.getReservationTime())) {
			List<ReservationPointTimeTableInfo> result = reservationPointTimeTable.stream()
					.filter(item -> form.getReservationTime().equals(item.getTimeSlotName()))
					.collect(Collectors.toList());
			reservationPointTimeTable = result;
		}

		return reservationPointTimeTable;
	}



}
