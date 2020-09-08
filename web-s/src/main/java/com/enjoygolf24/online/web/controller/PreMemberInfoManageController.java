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
import com.enjoygolf24.api.common.code.MemberTypeCd;
import com.enjoygolf24.api.common.code.MemberUseFlagCd;
import com.enjoygolf24.api.common.code.PreMemberUseFlagCd;
import com.enjoygolf24.api.common.database.bean.TblUserPre;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.CdMapService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.online.web.form.PreMemberConvertForm;
import com.enjoygolf24.online.web.form.PreMemberInfoManageForm;
import com.enjoygolf24.online.web.form.PreMemberInfoManageListForm;
import com.github.pagehelper.PageInfo;


@Controller
@RequestMapping("/admin/member/managePreUser")
public class PreMemberInfoManageController {

	private static final Logger logger = LoggerFactory.getLogger(PreMemberInfoManageController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	private CdMapService cdMapService;

	@Autowired
	AspService aspService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String memberInfoManageList(@ModelAttribute("preMemberInfoManageListForm") PreMemberInfoManageListForm form,
			Model model) {
		logger.info("Start memberList Controller");

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblUserPre> memberList = memberInfoManageService.getPreMemberListAll(form.getAspCode(), form.getPageNo(),
				form.getPageSize());
		PageInfo<TblUserPre> pageInfo = new PageInfo<TblUserPre>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);

		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");
		
		return "/admin/member/managePreUser/index";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(@ModelAttribute @Validated(Search0.class) PreMemberInfoManageListForm form, BindingResult result,
			Model model) {
		logger.info("Start memberList Controller");

		if (result.hasErrors()) {
			return memberInfoManageList(form, model);
		}

		initListForm(form, model);

		List<TblUserPre> memberList = memberInfoManageService.getPreMemberList(form.getMemberCode(),
				form.getName(),
				form.getPhone(), form.getEmail(), form.getAspCode(), form.getUseFlagCd(), form.getPageNo(),
				form.getPageSize());

		PageInfo<TblUserPre> pageInfo = new PageInfo<TblUserPre>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");

		return "/admin/member/managePreUser/index";
	}

	@RequestMapping(value = "/list/prev", method = RequestMethod.POST)
	public String listPrev(@ModelAttribute @Validated(Search0.class) PreMemberInfoManageListForm form,
			BindingResult result, Model model) {
		logger.info("Start memberList Controller");

		if (result.hasErrors()) {
			return memberInfoManageList(form, model);
		}

		initListForm(form, model);

		form.setPageNo(form.getPageNo() - 1);

		List<TblUserPre> memberList = memberInfoManageService.getPreMemberList(form.getMemberCode(), form.getName(),
				form.getPhone(), form.getEmail(), form.getAspCode(), form.getUseFlagCd(), form.getPageNo(),
				form.getPageSize());

		PageInfo<TblUserPre> pageInfo = new PageInfo<TblUserPre>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");

		return "/admin/member/managePreUser/index";
	}

	@RequestMapping(value = "/list/next", method = RequestMethod.POST)
	public String listNext(@ModelAttribute @Validated(Search0.class) PreMemberInfoManageListForm form,
			BindingResult result, Model model) {
		logger.info("Start memberList Controller");

		if (result.hasErrors()) {
			return memberInfoManageList(form, model);
		}

		initListForm(form, model);

		form.setPageNo(form.getPageNo() + 1);

		List<TblUserPre> memberList = memberInfoManageService.getPreMemberList(form.getMemberCode(), form.getName(),
				form.getPhone(), form.getEmail(), form.getAspCode(), form.getUseFlagCd(), form.getPageNo(),
				form.getPageSize());

		PageInfo<TblUserPre> pageInfo = new PageInfo<TblUserPre>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");

		return "/admin/member/managePreUser/index";
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public String details(@ModelAttribute PreMemberInfoManageListForm form, Model model) {

		PreMemberInfoManageForm modifyForm = new PreMemberInfoManageForm();
		initDetailForm(modifyForm, model, form.getSelectedMemberCode());

		model.addAttribute("preMemberInfoManageForm", modifyForm);
		return "/admin/member/managePreUser/details";
	}

	@RequestMapping(value = "/detailsIndex", method = RequestMethod.POST)
	public String detailsIndex(@ModelAttribute PreMemberInfoManageForm form, Model model) {

		initDetailForm(form, model, form.getPreMemberCode());

		model.addAttribute("preMemberInfoManageForm", form);
		return "/admin/member/managePreUser/details";
	}

	@RequestMapping(value = "/details/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) PreMemberInfoManageForm form, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("preMemberInfoManageForm", form);
			return detailsIndex(form, model);
		}

		if (!memberInfoManageService.isUniqueEmailForPreMember(form.getPreMemberCode(), form.getEmail())) {
			model.addAttribute("preMemberInfoManageForm", form);
			result.rejectValue("email", "error.user",
					"{0} : 同じEメールアドレスの会員が既に存在しています。");
			return detailsIndex(form, model);
		}

		memberInfoManageService.preMemberModify(form.createPreMemberModifyServiceBean());

		result.rejectValue("hasChanged", "error.user", "更新完了しました。");
		model.addAttribute("preMemberInfoManageForm", form);

		return "/admin/member/managePreUser/details";
	}

	@RequestMapping(value = "/convert", method = RequestMethod.POST)
	public String convert(@ModelAttribute PreMemberInfoManageListForm form, Model model) {

		PreMemberConvertForm convertForm = new PreMemberConvertForm();
		initConvertForm(convertForm, model, form.getSelectedMemberCode());

		model.addAttribute("preMemberConvertForm", convertForm);
		return "/admin/member/managePreUser/convert";
	}

	@RequestMapping(value = "/convertIndex", method = RequestMethod.POST)
	public String convertIndex(@ModelAttribute PreMemberConvertForm form, Model model) {

		initConvertForm(form, model, form.getPreMemberCode());

		model.addAttribute("preMemberConvertForm", form);
		return "/admin/member/managePreUser/convert";
	}

	@RequestMapping(value = "/convert/finish", method = RequestMethod.POST)
	public String convertFinish(@ModelAttribute @Validated(All.class) PreMemberConvertForm form, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			setModelMapper(model);
			model.addAttribute("preMemberConvertForm", form);
			return convertIndex(form, model);
		}

		if (!memberInfoManageService.isUniqueEmail(form.getPreMemberCode(), form.getEmail())) {
			model.addAttribute("preMemberConvertForm", form);
			result.rejectValue("email", "error.user", "{0} : 同じEメールアドレスの会員が既に存在しています。");
			return convertIndex(form, model);
		}

		memberInfoManageService.convert(form.createPreMemberConvertServiceBean());

		result.rejectValue("hasChanged", "error.user", "更新完了しました。");
		setModelMapper(model);
		model.addAttribute("preMemberConvertForm", form);

		return "/admin/member/managePreUser/convert";
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String confirm(@ModelAttribute PreMemberInfoManageListForm form, BindingResult result, Model model) {

		logger.info("Start confirm Controller");

		memberInfoManageService.confirm(form.getSelectedMemberCode(), LoginUtility.getLoginUser().getMemberCode());
		result.rejectValue("hasChanged", "error.user", "更新完了しました。");

		logger.info("End confirm Controller");
		return memberInfoManageList(form, model);
	}


	private void initListForm(PreMemberInfoManageListForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		if (!aspService.getAspByName("本社").getAspCode().equals(aspCode)) {
			form.setAspCode(aspCode);
		}
		model.addAttribute("useFlagCdMap", cdMapService.createMapOnlyIncludes(CodeTypeCd.PRE_MEMBER_USE_FLAG_CD,
				PreMemberUseFlagCd.PRE_MEMBER_USE_FLAG_PRE, PreMemberUseFlagCd.PRE_MEMBER_USE_FLAG_NORMAL));
	}

	private void initDetailForm(PreMemberInfoManageForm form, Model model, String memberCode) {
		form.init(memberInfoManageService, memberCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
	}

	private void initConvertForm(PreMemberConvertForm form, Model model, String memberCode) {
		form.init(memberInfoManageService, memberCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		setModelMapper(model);
	}

	private void setModelMapper(Model model) {
		model.addAttribute("memberTypeCdMap",
				cdMapService.createMapOnlyIncludes(CodeTypeCd.MEMBER_TYPE_CD, MemberTypeCd.MEMBER));
		model.addAttribute("genderCdMap", cdMapService.createMap(CodeTypeCd.GENDER_CD));
		model.addAttribute("memberGradeCdMap", cdMapService.createMap(CodeTypeCd.MEMBER_GRADE_CD));
		model.addAttribute("memberUseFlagCdMap",
				cdMapService.createMapOnlyIncludes(CodeTypeCd.MEMBER_USE_FLAG_CD,
						MemberUseFlagCd.MEMBER_USE_FLAG_NORMAL, MemberUseFlagCd.MEMBER_USE_FLAG_LOCKED,
						MemberUseFlagCd.MEMBER_USE_FLAG_WITHDRAWAL));
		model.addAttribute("additionalLessonCdMap", cdMapService.createMap(CodeTypeCd.ADDITIONAL_LESSON_CD));
		model.addAttribute("memberGradeTimeCdMap", cdMapService.createMap(CodeTypeCd.MEMBER_GRADE_TIME_CD));
		model.addAttribute("jobCdMap", cdMapService.createMap(CodeTypeCd.JOB_CD));
	}
}
