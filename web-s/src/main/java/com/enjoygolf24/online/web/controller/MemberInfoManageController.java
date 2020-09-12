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
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.CdMapService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.online.web.form.MemberInfoManageForm;
import com.enjoygolf24.online.web.form.MemberInfoManageListForm;
import com.github.pagehelper.PageInfo;


@Controller
@RequestMapping("/admin/member/manageUser")
public class MemberInfoManageController {

	private static final Logger logger = LoggerFactory.getLogger(MemberInfoManageController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	private CdMapService cdMapService;

	@Autowired
	AspService aspService;

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String index(@ModelAttribute("memberInfoManageListForm") MemberInfoManageListForm form,
			HttpServletRequest request,
	        Model model) {
		logger.info("Start memberList Controller");

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblUser> memberList = memberInfoManageService.getMemberListAll(form.getAspCode(), form.getPageNo(),
				form.getPageSize());
		form.setMemberList(memberList);

		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");
		return "/admin/member/manageUser/index";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String memberInfoManageList(@ModelAttribute("memberInfoManageListForm") MemberInfoManageListForm form,
			Model model) {
		logger.info("Start memberList Controller");

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblUser> memberList = memberInfoManageService.getMemberListAll(form.getAspCode(), form.getPageNo(),
				form.getPageSize());
		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);

		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");
		
		return "/admin/member/manageUser/index";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(@ModelAttribute @Validated(Search0.class) MemberInfoManageListForm form, BindingResult result,
			Model model) {
		logger.info("Start memberList Controller");

		if (result.hasErrors()) {
			return memberInfoManageList(form, model);
		}

		initListForm(form, model);

		List<TblUser> memberList = memberInfoManageService.getMemberList(form.getMemberCode(), form.getName(),
				form.getPhone(), form.getEmail(), form.getAspCode(), form.getPageNo(), form.getPageSize());

		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");

		return "/admin/member/manageUser/index";
	}

	@RequestMapping(value = "/list/prev", method = RequestMethod.POST)
	public String listPrev(@ModelAttribute @Validated(Search0.class) MemberInfoManageListForm form,
			BindingResult result, Model model) {
		logger.info("Start memberList Controller");

		if (result.hasErrors()) {
			return memberInfoManageList(form, model);
		}

		initListForm(form, model);

		form.setPageNo(form.getPageNo() - 1);

		List<TblUser> memberList = memberInfoManageService.getMemberList(form.getMemberCode(), form.getName(),
				form.getPhone(), form.getEmail(), form.getAspCode(), form.getPageNo(), form.getPageSize());

		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");

		return "/admin/member/manageUser/index";
	}

	@RequestMapping(value = "/list/next", method = RequestMethod.POST)
	public String listNext(@ModelAttribute @Validated(Search0.class) MemberInfoManageListForm form,
			BindingResult result, Model model) {
		logger.info("Start memberList Controller");

		if (result.hasErrors()) {
			return memberInfoManageList(form, model);
		}

		initListForm(form, model);

		form.setPageNo(form.getPageNo() + 1);

		List<TblUser> memberList = memberInfoManageService.getMemberList(form.getMemberCode(), form.getName(),
				form.getPhone(), form.getEmail(), form.getAspCode(), form.getPageNo(), form.getPageSize());

		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");

		return "/admin/member/manageUser/index";
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public String details(@ModelAttribute MemberInfoManageListForm form, Model model) {

		MemberInfoManageForm modifyForm = new MemberInfoManageForm();
		initDetailForm(modifyForm, model, form.getSelectedMemberCode());

		model.addAttribute("memberInfoManageForm", modifyForm);
		return "/admin/member/manageUser/details";
	}

	@RequestMapping(value = "/detailsIndex", method = RequestMethod.POST)
	public String detailsIndex(@ModelAttribute MemberInfoManageForm form, Model model) {

		initDetailForm(form, model, form.getMemberCode());

		model.addAttribute("memberInfoManageForm", form);
		return "/admin/member/manageUser/details";
	}

	@RequestMapping(value = "/details/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) MemberInfoManageForm form, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			setModelMapper(model);
			model.addAttribute("memberInfoManageForm", form);
			return detailsIndex(form, model);
		}

		if (!memberInfoManageService.isUniqueEmail(form.getMemberCode(), form.getEmail())) {
			model.addAttribute("memberInfoManageForm", form);
			result.rejectValue("email", "error.user",
					"{0} : 同じEメールアドレスの会員が既に存在しています。");
			return detailsIndex(form, model);
		}

		memberInfoManageService.memberModify(form.createMemberModifyServiceBean());

		result.rejectValue("hasChanged", "error.user", "更新完了しました。");
		setModelMapper(model);
		model.addAttribute("memberInfoManageForm", form);

		return "/admin/member/manageUser/details";
	}


	private void initListForm(MemberInfoManageListForm form, Model model) {
		String aspCode = LoginUtility.getLoginUser().getAspCode();
		if (!aspService.getAspByName("本社").getAspCode().equals(aspCode)) {
			form.setAspCode(aspCode);
		}
	}

	private void initDetailForm(MemberInfoManageForm form, Model model, String memberCode) {
		form.init(memberInfoManageService, memberCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		setModelMapper(model);
	}

	private void setModelMapper(Model model) {
		model.addAttribute("memberTypeCdMap", cdMapService.createMapOnlyIncludes(CodeTypeCd.MEMBER_TYPE_CD,
				MemberTypeCd.INSTRUCTOR, MemberTypeCd.MEMBER));
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
