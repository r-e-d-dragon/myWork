package com.enjoygolf24.online.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.jpa.repository.CodeMasterRepository;
import com.enjoygolf24.api.common.database.jpa.repository.TblPointConsumeMasterRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberReservationManageService;
import com.enjoygolf24.online.web.form.MemberReservationManageListForm;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;

@Controller
@RequestMapping("/admin/booking/bulk")
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
	TblPointConsumeMasterRepository tblPointConsumeMasterRepository;
	
	@RequestMapping(value = "")
	public String memberReservationList(
			@ModelAttribute("memberReservationManageListForm") MemberReservationManageListForm form, Model model) {
		logger.info("Start memberReservationList Controller");

		initListForm(form, model);

		// 予約日 - 初期表示：当日
		if (StringUtil.isEmpty(form.getReservationDate())) {
			form.setReservationDate(DateUtility.getCurrentDateTime(DateUtility.DATE_FORMAT));
		}

		// TODO 打席番号 － コードマスタ
		List<String> batNumbers = codeMasterRepository.findByCodeTypeOrderByCd("990").stream().map(p -> p.getName())
				.collect(Collectors.toList());
		model.addAttribute("batNumbers", batNumbers);


		logger.info("End memberReservationList Controller");
		return "/admin/booking/bulk/index";
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
				form.getReservationDate(), form.getStatus(), false, form.getPageNo(), form.getPageSize());

		PageInfo<MemberReservationManage> pageInfo = new PageInfo<MemberReservationManage>(memberReservationList);
		model.addAttribute("pageInfo", pageInfo);
		form.setReservationList(memberReservationList);

		model.addAttribute("modelMemberReservationList", memberReservationList);
	}
}