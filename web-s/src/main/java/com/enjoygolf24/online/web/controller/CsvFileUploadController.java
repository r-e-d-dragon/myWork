package com.enjoygolf24.online.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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

import com.enjoygolf24.api.common.constants.CsvFileReadContants;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.CsvFileReadService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.online.web.form.FileUploadListForm;


@Controller
@RequestMapping("/admin/booking/fileUpload")
public class CsvFileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(CsvFileUploadController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	AspService aspService;

	@Autowired
	CsvFileReadService csvFileReadService;

	@RequestMapping(value = "")
	public String fileUploadInfo(
			@ModelAttribute("fileUploadListForm") FileUploadListForm form, Model model) {
		logger.info("Start fileUploadInfo Controller");

		initForm(form, model);

		logger.info("End fileUploadInfo Controller");
		return "/admin/booking/fileUpload/index";
	}

	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	public String fileUploadFinish(@ModelAttribute @Validated(All.class) FileUploadListForm form, BindingResult result,
			Model model) {
		logger.info("Start fileUploadFinish Controller");

		if (result.hasErrors()) {
			model.addAttribute("fileUploadListForm", form);
			return fileUploadInfo(form, model);
		}

		initForm(form, model);

		String fileType = "CANDY-A";

		// ファイルが空の場合は異常終了
		if (form.getMultipartFile().isEmpty()) {
			// 異常終了時の処理
			model.addAttribute("fileUploadListForm", form);
			result.rejectValue("filename", "error.filename", "{0} : ファイルアップロードが失敗しました。");
			return fileUploadInfo(form, model);
		} else {
			// ファイル種類から決まる値をセットする
			StringBuffer filePath = new StringBuffer(CsvFileReadContants.FILE_PATH).append(File.separator); // ファイルパス

			// アップロードファイルを格納するディレクトリを作成する
			File uploadDir = mkdirs(filePath);

			// 元ファイル名に日付をつける
			String filename = form.getFilename().substring(0, form.getFilename().lastIndexOf('.')) + "-"
					+ DateUtility.getCurrentDateTime("yyyyMMddHHmmssSSS")
					+ form.getFilename().substring(form.getFilename().lastIndexOf('.'));

			// アップロードファイルを置く
			File uploadFile = new File(uploadDir.getPath() + File.separator + filename);
			byte[] bytes;
			try (BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));) {
				bytes = form.getMultipartFile().getBytes();
				uploadFileStream.write(bytes);
				uploadFileStream.close();

				csvFileReadService.csvFileRead(fileType, uploadDir.getPath() + File.separator + filename);

				logger.info("You successfully uploaded !!! ");
			} catch (Exception e) {
				// 異常終了時の処理
				logger.error("ファイルアップロードが失敗しました。", e);
			} catch (Throwable t) {
				// 異常終了時の処理
				logger.error("ファイルアップロードが失敗しました。", t);
			}
		}
		model.addAttribute("originalFilename", form.getMultipartFile().getOriginalFilename());

		logger.info("End fileUploadFinish Controller");
		return "/admin/booking/fileUpload/finish";
	}

	/**
	 * リストフォーム初期処理
	 * 
	 * @param form
	 * @return
	 */
	private void initForm(FileUploadListForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		TblAsp asp = aspService.getAsp(aspCode);

		form.setAspCode(aspCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());

		model.addAttribute("aspName", asp.getAspName());
	}

	/**
	 * アップロードファイルを格納するディレクトリを作成する
	 *
	 * @param filePath
	 * @return
	 */
	private File mkdirs(StringBuffer filePath) {
		String yyyy = DateUtility.toDateString("yyyy", DateUtility.getCurrentTimestamp());
		File uploadDir = new File(filePath.toString(), yyyy);

		// フォルダ作成
		uploadDir.mkdirs();

		return uploadDir;
	}

}
