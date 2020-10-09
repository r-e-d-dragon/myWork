package com.enjoygolf24.online.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.utility.CsvFileUtility;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.CreditFileService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.online.web.form.FileDownloadForm;


@Controller
@RequestMapping("/admin/credit/fileDownload")
public class CreditCsvFileDownloadController {

	private static final Logger logger = LoggerFactory.getLogger(CreditCsvFileDownloadController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	AspService aspService;

	@Autowired
	CreditFileService creditFileService;

	@RequestMapping(value = "")
	public String fileDownloadInfo(
			@ModelAttribute FileDownloadForm form, Model model) {
		logger.info("Start fileDownloadInfo Controller");

		initForm(form, model);

		// 翌月
		String nextMonth = DateUtility.toDateString(DateUtility.SLASH_DATE_FORMAT_YEAR_MONTH,
				DateUtility.addMonth(DateUtility.toDate(DateUtility.getCurrentTimestamp()), 1));

		form.setTargetMonth(nextMonth);

		logger.info("End fileDownloadInfo Controller");
		return "/admin/credit/fileDownload/index";
	}

    /**
     * csvをダウンロードする。
     */
	@RequestMapping(value = "/csv", method = RequestMethod.POST)
	public void fileDownloadFinish(HttpServletResponse response, @ModelAttribute FileDownloadForm form) {

		logger.info("Start fileDownloadFinish Controller");

		String targetMonth = form.getTargetMonth();
		// TODO 日本語ファイル名は文字化け、 CsvFileUtility.putCsvDataToResponseの改修必要
		String filename = targetMonth.replace("/", "") + "_"
				+ DateUtility.getCurrentDateTime(DateUtility.PLAIN_DATE_FORMAT_FULL) + ".csv";

		creditFileService.creditFileWrite();
		
		// TODO 決済対象会員情報を取得し、依頼データ作成必要
		List<String> data = creditFileService.creditFileWrite();

		Pair<String, List<String>> csvData = new MutablePair<>(filename, data);
		CsvFileUtility.putCsvDataToResponse(response, csvData);

		logger.info("End fileDownloadFinish Controller");
		return;
	}

	/**
	 * リストフォーム初期処理
	 * 
	 * @param form
	 * @return
	 */
	private void initForm(FileDownloadForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		TblAsp asp = aspService.getAsp(aspCode);

		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());

		model.addAttribute("aspName", asp.getAspName());
	}
}
