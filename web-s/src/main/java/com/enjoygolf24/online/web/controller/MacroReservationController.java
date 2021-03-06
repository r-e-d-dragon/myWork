package com.enjoygolf24.online.web.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
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
import com.enjoygolf24.api.common.code.MacroDateTypeCd;
import com.enjoygolf24.api.common.code.ReservationStatusCd;
import com.enjoygolf24.api.common.database.bean.CodeMaster;
import com.enjoygolf24.api.common.database.bean.MstTimeTable;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.jpa.repository.CodeMasterRepository;
import com.enjoygolf24.api.common.database.jpa.repository.MstTimeTableRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.database.mybatis.bean.ReservationPointTimeTableInfo;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.utility.StringUtil;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.MacroReservationManageService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.online.web.form.MacroReservationManageListForm;
import com.enjoygolf24.online.web.form.MemberReservationManageListForm;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/admin/booking/macro")
public class MacroReservationController {

	private static final Logger logger = LoggerFactory.getLogger(MacroReservationController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	MacroReservationManageService macroReservationManageService;

	@Autowired
	AspService aspService;

	@Autowired
	CodeMasterRepository codeMasterRepository;

	@Autowired
	MstTimeTableRepository mstTimeTableRepository;
	
	@RequestMapping(value = "")
	public String macrosReservationInfo(
			@ModelAttribute("macroReservationManageListForm") MacroReservationManageListForm form, Model model) {
		logger.info("Start macrosReservationInfo Controller");

		initListForm(form, model);

		// タイムテーブル
		List<MstTimeTable> timeTable = macroReservationManageService.getMstTimeTable(form.getAspCode());
		List<ReservationPointTimeTableInfo> timeTableList = new ArrayList<ReservationPointTimeTableInfo>();
		timeTable.forEach(p -> {
			ReservationPointTimeTableInfo table = new ReservationPointTimeTableInfo();
			try {
				BeanUtils.copyProperties(table, p);
			} catch (Exception e) {
				logger.error("予約時間表取得に失敗しました。", e);
			}
			String timeSlotName = DateUtility.toTimeString(p.getStartTime()) + "~"
					+ DateUtility.toTimeString(p.getEndTime());
			table.setTimeSlotName(timeSlotName);
			timeTableList.add(table);
		});
		model.addAttribute("timeTableList", timeTableList);

		// TODO 打席番号 － コードマスタ
		List<String> batNumbers = codeMasterRepository.findByCodeTypeOrderByCd("990").stream().map(p -> p.getName())
				.collect(Collectors.toList());
		model.addAttribute("batNumbers", batNumbers);

		model.addAttribute("termDate", MacroDateTypeCd.TERM_DATE);
		model.addAttribute("repeatDate", MacroDateTypeCd.REPEAT_DATE);

		if (StringUtil.isEmpty(form.getMacroDateType())) {
			form.setMacroDateType(MacroDateTypeCd.TERM_DATE);
		}

		serchCommonLogic(form, model);

		logger.info("End macrosReservationInfo Controller");
		return "/admin/booking/macro/index";
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String confirmMacroReservationInfo(
			@ModelAttribute("macroReservationManageListForm") @Validated(All.class) MacroReservationManageListForm form,
			BindingResult result,
			Model model) {
		logger.info("Start confirmMacroReservationInfo Controller");

		if (result.hasErrors()) {
			model.addAttribute("macroReservationManageListForm", form);
			return macrosReservationInfo(form, model);
		}

		// 現在時刻
		LocalDateTime currentDateTime = DateUtility.getCurrentTimestamp().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDateTime();

		if (MacroDateTypeCd.TERM_DATE.equals(form.getMacroDateType())) {
			// 期間指定予約
			int fromReservationTime = Integer.valueOf(form.getFromReservationTime().substring(0, 2));
			int toReservationTime = Integer.valueOf(form.getToReservationTime().substring(0, 2));

			LocalDateTime fromReservationDateTime = DateUtility.getDate(form.getFromReservationDate()).toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDateTime().plusHours(fromReservationTime);
			LocalDateTime toReservationDateTime = DateUtility.getDate(form.getToReservationDate()).toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDateTime().plusHours(toReservationTime);
			if (fromReservationDateTime.compareTo(toReservationDateTime) > 0) {
				model.addAttribute("macroReservationManageListForm", form);
				result.rejectValue("toReservationDate", "error.toReservationDate", "{0} : 開始日時より未来日時を選択して下さい。");
				return macrosReservationInfo(form, model);
			}
			if (currentDateTime.compareTo(fromReservationDateTime) > 0) {
				model.addAttribute("macroReservationManageListForm", form);
				result.rejectValue("fromReservationTime", "error.fromReservationTime",
						"{0} : 開始日時の予約時刻は現在時刻より未来時刻を選択して下さい。");
				return macrosReservationInfo(form, model);
			}

		} else if (MacroDateTypeCd.REPEAT_DATE.equals(form.getMacroDateType())) {
			// 繰り返し予約
			int repeatFromReservationTime = Integer.valueOf(form.getRepeatFromReservationTime().substring(0, 2));
			int repeatToReservationTime = Integer.valueOf(form.getRepeatToReservationTime().substring(0, 2));

			LocalDateTime repeatFromReservationDateTime = DateUtility.getDate(form.getRepeatFromReservationDate())
					.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusHours(repeatFromReservationTime);
			LocalDateTime repeatToReservationDateTime = DateUtility.getDate(form.getRepeatToReservationDate())
					.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusHours(repeatToReservationTime);
			if (repeatFromReservationDateTime.compareTo(repeatToReservationDateTime) > 0) {
				model.addAttribute("macroReservationManageListForm", form);
				result.rejectValue("repeatToReservationDate", "error.repeatToReservationDate",
						"{0} : 開始日時より未来日時を選択して下さい。");
				return macrosReservationInfo(form, model);
			}
			if (currentDateTime.compareTo(repeatFromReservationDateTime) > 0) {
				model.addAttribute("macroReservationManageListForm", form);
				result.rejectValue("repeatFromReservationTime", "error.repeatFromReservationTime",
						"{0} : 開始日時の予約時刻は現在時刻より未来時刻を選択して下さい。");
				return macrosReservationInfo(form, model);
			}
		}

		initListForm(form, model);

		// TODO 予約情報確認
		// TODO 一括予約期間にすでに予約情報があるか確認する。

		// TODO 打席番号 － コードマスタ
		List<String> batNumbers = codeMasterRepository.findByCodeTypeOrderByCd("990").stream().map(p -> p.getName())
				.collect(Collectors.toList());
		model.addAttribute("batNumbers", batNumbers);

		model.addAttribute("termDate", MacroDateTypeCd.TERM_DATE);
		model.addAttribute("repeatDate", MacroDateTypeCd.REPEAT_DATE);

		model.addAttribute("macroReservationManageListForm", form);

		logger.info("End confirmMacroReservationInfo Controller");
		return "/admin/booking/macro/confirm";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String finishMacroReservationInfo(
			@ModelAttribute("macroReservationManageListForm") @Validated(All.class) MacroReservationManageListForm form,
			@ModelAttribute("memberReservationManageListForm") MemberReservationManageListForm searchForm,
			BindingResult result,
			Model model) {
		logger.info("Start finishMacroReservationInfo Controller");

		if (result.hasErrors()) {
			model.addAttribute("macroReservationManageListForm", form);
			return confirmMacroReservationInfo(form, result, model);
		}

		initListForm(form, model);

		// 予約番号
		String reservationNumber = DateUtility.getCurrentDateTime("yyyyMMdd-HHmmssSSS");
		form.setReservationNumber(reservationNumber);
		form.setStatus(ReservationStatusCd.STATUS_FIXED);
		// 選択打席番号
		form.setBatNumber(String.join(",", form.getChkBatNumbers()));

		// 一括予約登録
		macroReservationManageService.MacroReservationRegister(form.createMemberReservationServiceBean());

		searchForm.setReservationNumber(reservationNumber);

		model.addAttribute("action", "ins");
		model.addAttribute("reservationNumber", searchForm.getReservationNumber());
		model.addAttribute("macroName", form.getMacroName());

		model.addAttribute("macroDateTypeName", codeMasterRepository
				.findByCodeTypeAndCd(CodeTypeCd.MACRO_DATE_TYPE_CD, form.getMacroDateType()).getName());

		if (MacroDateTypeCd.TERM_DATE.equals(form.getMacroDateType())) {
			// 期間指定
			model.addAttribute("fromReservationDate", form.getFromReservationDate());
			model.addAttribute("fromReservationTime", form.getFromReservationTime());
			model.addAttribute("toReservationDate", form.getToReservationDate());
			model.addAttribute("toReservationTime", form.getToReservationTime());
		} else if (MacroDateTypeCd.REPEAT_DATE.equals(form.getMacroDateType())) {
			// 繰り返し
			model.addAttribute("fromReservationDate", form.getRepeatFromReservationDate());
			model.addAttribute("fromReservationTime", form.getRepeatFromReservationTime());
			model.addAttribute("toReservationDate", form.getRepeatToReservationDate());
			model.addAttribute("toReservationTime", form.getRepeatToReservationTime());
		}
		model.addAttribute("chkBatNumbers", form.getChkBatNumbers());

		model.addAttribute("macroReservationManageListForm", form);

		logger.info("End finishMacroReservationInfo Controller");
		return "/admin/booking/macro/finish";
	}

	@RequestMapping(value = "/cancle", method = RequestMethod.POST)
	public String cancleMacroReservationInfo(
			@ModelAttribute MacroReservationManageListForm form, Model model) {
		logger.info("Start cancleMacroReservationInfo Controller");

		initListForm(form, model);

		// 一括予約登録
		macroReservationManageService.MacroReservationCancle(form.createMemberReservationServiceBean());

		model.addAttribute("macroReservationManageListForm", form);

		logger.info("End cancleMacroReservationInfo Controller");
		return "/admin/booking/macro/cancleFinish";
	}

	/**
	 * リストフォーム初期処理
	 * 
	 * @param form
	 * @return
	 */
	private void initListForm(MacroReservationManageListForm form, Model model) {
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
	private void serchCommonLogic(MacroReservationManageListForm form, Model model) {

		initListForm(form, model);

		List<MemberReservationManage> macroReservationList = macroReservationManageService
				.getMacroReservationList(LoginUtility.getLoginUser().getAspCode(), form.getPageNo(),
						form.getPageSize());

		PageInfo<MemberReservationManage> pageInfo = new PageInfo<MemberReservationManage>(macroReservationList);
		model.addAttribute("pageInfo", pageInfo);
		
		Map<String, String> macroDateTypeMap = codeMasterRepository
				.findByCodeTypeOrderByCdDesc(CodeTypeCd.MACRO_DATE_TYPE_CD).stream()
				.collect(Collectors.toMap(CodeMaster::getCd, d -> d.getName()));

		macroReservationList.forEach(p -> {
			p.setMacroDateTypeName(macroDateTypeMap.get(p.getMacroDateType()));
		});
		form.setReservationList(macroReservationList);

		model.addAttribute("macroReservationList", macroReservationList);
	}

}
