package com.enjoygolf24.online.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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
import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.code.ReservationStatusCd;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.bean.TblReservation;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.jpa.repository.CodeMasterRepository;
import com.enjoygolf24.api.common.database.jpa.repository.ViewReservationPointTimeTableRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.database.mybatis.bean.PointManage;
import com.enjoygolf24.api.common.database.mybatis.bean.ReservationPointTimeTableInfo;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
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
@RequestMapping("/admin/booking")
public class MemberReservationManageController {

	private static final Logger logger = LoggerFactory.getLogger(MemberReservationManageController.class);

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
	
	@RequestMapping(value = "/top")
	public String memberReservationList(
			@ModelAttribute("memberReservationManageListForm") MemberReservationManageListForm form,
			@ModelAttribute("memberReservationRegisterForm") MemberReservationRegisterForm registerForm,
			@ModelAttribute("memberInfoManageListForm") MemberInfoManageListForm memberForm, BindingResult result,  Model model) {
		logger.info("Start memberReservationList Controller");

		initListForm(form, model);

		// 予約日 - 初期表示：当日
		if (StringUtil.isEmpty(form.getReservationDate())) {
			form.setReservationDate(DateUtility.getCurrentDateTime(DateUtility.DATE_FORMAT));
		}

		// 予約範囲指定 今月一日から翌月末日まで
		LocalDate now = LocalDate.now();
		LocalDate minDate = now.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate maxDate = now.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		model.addAttribute("minDate", minDate.format(dateTimeFormatter));
		model.addAttribute("maxDate", maxDate.format(dateTimeFormatter));

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

		logger.info("End memberReservationList Controller");
		return "/admin/booking/top/index";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerReservation(
			@ModelAttribute("memberReservationRegisterForm") MemberReservationRegisterForm form,
			Model model) {
		logger.info("Start registerReservation Controller");

		initRegisterForm(form, model);

		if (StringUtil.isEmpty(form.getReservationId())) {
			// 仮登録
			form.setStatus(ReservationStatusCd.STATUS_TEMP);
			// 仮予約番号
			String reservationNumber = form.getReservationDate().replace("/", "") + "-00-0000";
			form.setReservationNumber(reservationNumber);

			// 仮登録ユーザ
			form.setMemberCode("");
			form.setPointCategoryCode("");
			form.setGradeTypeCd(form.getGradeTypeCd());

			try {
				String reservationId = memberReservationManageService
						.MemberReservationRegister(form.createMemberReservationServiceBean());
				form.setReservationId(reservationId);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// error
				return registerReservation(form, model);
			}

			// 有効フラグ：false
			form.setValid(false);
		}

		model.addAttribute("monthlyPoint", PointCategoryCd.MONTHLY_POINT);
		model.addAttribute("eventPoint", PointCategoryCd.EVENT_POINT);

		model.addAttribute("memberReservationRegisterForm", form);

		logger.info("End registerReservation Controller");
		return "/admin/booking/register/index";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) MemberReservationRegisterForm form,
			BindingResult result, Model model) {
		logger.info("Start finish Controller");

		if (result.hasErrors()) {
			model.addAttribute("memberReservationRegisterForm", form);
			return registerReservation(form, model);
		}

		List<MemberReservationManage> checkRev = memberReservationManageService.getMemberReservationAllList(null, null,
				form.getAspCode(), form.getBatNumber(), form.getReservationDate(), form.getReservationTime(),
				ReservationStatusCd.STATUS_FIXED, false);
		if (!checkRev.isEmpty()) {
			model.addAttribute("memberReservationRegisterForm", form);
			result.rejectValue("memberCode", "error.memberCode", "{0} : 時間超過のため、既に予約されています。");
			return registerReservation(form, model);
		}
		
		if (!form.isValid()) {
			model.addAttribute("memberReservationRegisterForm", form);
			result.rejectValue("validMonthlyPoint", "error.validMonthlyPoint", "{0} : ポイントが足りないため、予約出来ません。　管理者に問い合わせして下さい。");
			return registerReservation(form, model);
		}

		int consumedPoint = Integer.valueOf(form.getConsumedPoint());
		if (PointCategoryCd.MONTHLY_POINT.equals(form.getPointCategoryCode())) {
			if (consumedPoint > form.getValidMonthlyPoint()) {
				model.addAttribute("memberReservationRegisterForm", form);
				result.rejectValue("validMonthlyPoint", "error.validMonthlyPoint", "{0} : 月ポイントが足りないため、予約出来ません。");
				return registerReservation(form, model);
			}
			// 月ポイント情報取得
			List<PointManage> validMonPointList = memberReservationManageService.getMemberPointManageList(
					form.getMemberCode(), PointCategoryCd.MONTHLY_POINT, form.getReservationDate());
			if (consumedPoint > validMonPointList.stream().mapToInt(x -> x.getPointAmount()).sum()) {
				model.addAttribute("memberReservationRegisterForm", form);
				result.rejectValue("validMonthlyPoint", "error.validMonthlyPoint", "{0} : 月ポイントが足りないため、予約出来ません。");
				return registerReservation(form, model);
			}
		}
		if (PointCategoryCd.EVENT_POINT.equals(form.getPointCategoryCode())) {
			if (consumedPoint > form.getValidEventPoint()) {
				model.addAttribute("memberReservationRegisterForm", form);
				result.rejectValue("pointCategoryCode", "error.pointCategoryCode", "{0} : イベントポイントが足りないため、予約出来ません。");
				return registerReservation(form, model);
			}
			// 月ポイント情報取得
			List<PointManage> validEvtPointList = memberReservationManageService.getMemberPointManageList(
					form.getMemberCode(), PointCategoryCd.EVENT_POINT, form.getReservationDate());
			if (consumedPoint > validEvtPointList.stream().mapToInt(x -> x.getPointAmount()).sum()) {
				model.addAttribute("memberReservationRegisterForm", form);
				result.rejectValue("pointCategoryCode", "error.pointCategoryCode", "{0} : イベントポイントが足りないため、予約出来ません。");
				return registerReservation(form, model);
			}
		}

		TblUser member = memberInfoManageService.selectMember(form.getMemberCode());
		if (member == null) {
			return registerReservation(form, model);
		}

		initRegisterForm(form, model);

		// 登録完了
		form.setStatus(ReservationStatusCd.STATUS_FIXED);

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

		// ポイント情報設定
		setMemberPointInfoForm(form);

		model.addAttribute("memberCode", form.getMemberCode());
		model.addAttribute("batNumber", form.getBatNumber());
		model.addAttribute("reservationDate", form.getReservationDate());
		model.addAttribute("reservationTime", form.getReservationTime());
		model.addAttribute("consumedPoint", form.getConsumedPoint());
		model.addAttribute("monthlyPoint", form.getMonthlyPoint());
		model.addAttribute("eventPoint", form.getEventPoint());
		model.addAttribute("memberName", member.getLastName() + " " + member.getFirstName());

		logger.info("End finish Controller");
		return "/admin/booking/register/finish";
	}

	@RequestMapping(value = "/cancle", method = RequestMethod.POST)
	public String cancleReservation(@ModelAttribute("memberReservationRegisterForm") MemberReservationRegisterForm form,
			Model model) {
		logger.info("Start cancleReservation Controller");

		TblAsp asp = aspService.getAsp(LoginUtility.getLoginUser().getAspCode());
		model.addAttribute("aspName", asp.getAspName());

		// 予約ID
		form.setReservationId(form.getSelectedReservationId());
		form.setAspCode(LoginUtility.getLoginUser().getAspCode());
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());

		// 予約情報
		TblReservation reservation = memberReservationManageService.getReservation(form.getSelectedReservationId());
		// 会員情報
		TblUser member = memberInfoManageService.selectMember(reservation.getMemberCode());

		form.setMemberCode(reservation.getMemberCode());

		model.addAttribute("memberCode", reservation.getMemberCode());
		model.addAttribute("batNumber", reservation.getBatNumber());
		model.addAttribute("reservationNumber", reservation.getReservationNumber());
		model.addAttribute("reservationDate", DateUtility.toSlashDateString(reservation.getReservationDate()));
		model.addAttribute("reservationTime", reservation.getReservationTime());
		model.addAttribute("consumedPoint", reservation.getConsumedPoint());
		model.addAttribute("memberName", member.getLastName() + " " + member.getFirstName());

		logger.info("End cancleReservation Controller");
		return "/admin/booking/cancle/index";
	}

	@RequestMapping(value = "/cancleFinish", method = RequestMethod.POST)
	public String cancleFinish(@ModelAttribute @Validated(Search0.class) MemberReservationRegisterForm form,
			BindingResult result, Model model) {
		logger.info("Start cancleFinish Controller");

		initRegisterForm(form, model);

		TblUser member = memberInfoManageService.selectMember(form.getMemberCode());
		if (member == null) {
			model.addAttribute("memberReservationRegisterForm", form);
			result.rejectValue("memberCode", "error.memberCode", "{0} : 会員情報が存在しません。　管理者に問い合わせして下さい。");
			return cancleReservation(form, model);
		}

		// 予約取消
		form.setStatus(ReservationStatusCd.STATUS_CANCLE);
		String reservationId = memberReservationManageService
				.MemberReservationCancle(form.createMemberReservationServiceBean());

		form.setReservationId(reservationId);
		// ポイント情報設定
		setMemberPointInfoForm(form);

		model.addAttribute("memberReservationManageListForm", form);

		TblReservation reservation = memberReservationManageService.getReservation(form.getReservationId());

		model.addAttribute("memberCode", reservation.getMemberCode());
		model.addAttribute("batNumber", reservation.getBatNumber());
		model.addAttribute("reservationDate", DateUtility.toSlashDateString(reservation.getReservationDate()));
		model.addAttribute("reservationTime", reservation.getReservationTime());
		model.addAttribute("consumedPoint", reservation.getConsumedPoint());

		// ポイント結果表示
		model.addAttribute("monthlyPoint", form.getMonthlyPoint());
		model.addAttribute("eventPoint", form.getEventPoint());
		model.addAttribute("memberName", member.getLastName() + " " + member.getFirstName());
		model.addAttribute("penaltyPoint", form.getPenaltyPoint());

		logger.info("End cancleFinish Controller");
		return "/admin/booking/cancle/finish";
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
		// 予約確定
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

		if (!StringUtil.isEmpty(form.getSelectedMemberCode())) {
			form.setMemberCode(form.getSelectedMemberCode());
		}

		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);
		form.setStatus(ReservationStatusCd.STATUS_FIXED);

		serchCommonLogic(form, model);

		logger.info("End searchReservationList Controller");
		return "/admin/booking/search/index";
	}

	@RequestMapping(value = "/search/listprev", method = RequestMethod.POST)
	public String searchReservationListPrev(
			@ModelAttribute @Validated(Search0.class) MemberReservationManageListForm form, BindingResult result,
			Model model) {
		logger.info("Start searchReservationListPrev Controller");

		form.setPageNo(form.getPageNo() - 1);

		serchCommonLogic(form, model);

		logger.info("End searchReservationListPrev Controller");

		return "/admin/booking/search/index";
	}

	@RequestMapping(value = "/search/listnext", method = RequestMethod.POST)
	public String searchReservationListNext(
			@ModelAttribute @Validated(Search0.class) MemberReservationManageListForm form, BindingResult result,
			Model model) {
		logger.info("Start searchReservationListNext Controller");

		form.setPageNo(form.getPageNo() + 1);

		serchCommonLogic(form, model);

		logger.info("End searchReservationListNext Controller");

		return "/admin/booking/search/index";
	}

	/**
	 * 登録フォーム初期処理
	 * 
	 * @param form
	 * @return
	 */
	private void initRegisterForm(MemberReservationRegisterForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		TblAsp asp = aspService.getAsp(aspCode);

		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		model.addAttribute("aspName", asp.getAspName());

		form.setValidEventPoint(0);
		form.setValidMonthlyPoint(0);
		form.setEventPoint("0");
		form.setMonthlyPoint("0");
		form.setPenaltyPoint("0");

		form.setLimitReservationCount(0);
		form.setLimitEventReservationCount(0);
		form.setLimitMonthlyReservationCount(0);
		form.setLimitReservationPoint(0);
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

		String name = form.getLastName() + form.getFirstName();
		String kananame = form.getLastNameKana() + form.getFirstNameKana();

		List<MemberReservationManage> memberReservationList = memberReservationManageService.getMemberReservationList(
				form.getReservationNumber(), form.getMemberCode(), LoginUtility.getLoginUser().getAspCode(),
				form.getReservationDate(), form.getStatus(), name, kananame, form.getPhone(), form.getEmail(),
				!form.isValid(), form.getPageNo(), form.getPageSize());

		PageInfo<MemberReservationManage> pageInfo = new PageInfo<MemberReservationManage>(memberReservationList);
		model.addAttribute("pageInfo", pageInfo);
		form.setReservationList(memberReservationList);

		model.addAttribute("monthlyPt", PointCategoryCd.MONTHLY_POINT);
		model.addAttribute("eventPt", PointCategoryCd.EVENT_POINT);
		model.addAttribute("adminPt", PointCategoryCd.ADMIN_POINT);

		model.addAttribute("modelMemberReservationList", memberReservationList);
	}

	/**
	 * 月、イベントポイント情報取得
	 * 
	 * @param form
	 */
	private void setMemberPointInfoForm(MemberReservationRegisterForm form) {
		// 月ポイント情報取得
		List<PointManage> monthlyPointList = memberReservationManageService
				.getMemberPointManageList(form.getMemberCode(), PointCategoryCd.MONTHLY_POINT, null);
		// 月ポイント合計
		int totalMonthlyPoint = monthlyPointList.stream().mapToInt(x -> x.getPointAmount()).sum();

		// イベントポイント情報取得
		List<PointManage> eventPointList = memberReservationManageService
				.getMemberPointManageList(form.getMemberCode(), PointCategoryCd.EVENT_POINT, null);
		// イベントポイント合計
		int totalEventPoint = eventPointList.stream().mapToInt(x -> x.getPointAmount()).sum();

		form.setEventPoint(String.valueOf(totalEventPoint));
		form.setMonthlyPoint(String.valueOf(totalMonthlyPoint));
	}

}
