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
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.service.CdMapService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.online.web.form.ManagerInfoManageForm;
import com.enjoygolf24.online.web.form.ManagerInfoManageListForm;
import com.github.pagehelper.PageInfo;


@Controller
@RequestMapping("/admin/asp/manageUser")
public class ManagerInfoManageController {

	private static final Logger logger = LoggerFactory.getLogger(ManagerInfoManageController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	private CdMapService cdMapService;

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String aspListIndex(@ModelAttribute("managerInfoManageListForm") ManagerInfoManageListForm form,
			HttpServletRequest request,
	        Model model) {
		logger.info("Start memberList Controller");

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblUser> memberList = memberInfoManageService.getManagerListAll(form.getPageNo(), form.getPageSize());
		form.setMemberList(memberList);

		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");
		return "/admin/asp/manageUser/index";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String managerInfoManageList(@ModelAttribute("managerInfoManageListForm") ManagerInfoManageListForm form,
			Model model) {
		logger.info("Start memberList Controller");

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblUser> memberList = memberInfoManageService.getManagerListAll(form.getPageNo(), form.getPageSize());
		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);

		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");
		
		return "/admin/asp/manageUser/index";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(@ModelAttribute @Validated(Search0.class) ManagerInfoManageListForm form, BindingResult result,
			Model model) {
		logger.info("Start memberList Controller");

		if (result.hasErrors()) {
			return managerInfoManageList(form, model);
		}

		initListForm(form, model);

		List<TblUser> memberList = memberInfoManageService.getManagerList(form.getAspCode(), form.getAspName(),
				form.getMemberCode(),
				form.getName(),
				form.getPhone(), form.getEmail(), form.getPageNo(), form.getPageSize());

		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");

		return "/admin/asp/manageUser/index";
	}

	@RequestMapping(value = "/list/prev", method = RequestMethod.POST)
	public String listPrev(@ModelAttribute @Validated(Search0.class) ManagerInfoManageListForm form,
			BindingResult result, Model model) {
		logger.info("Start memberList Controller");

		if (result.hasErrors()) {
			return managerInfoManageList(form, model);
		}

		initListForm(form, model);

		form.setPageNo(form.getPageNo() - 1);

		List<TblUser> memberList = memberInfoManageService.getManagerList(form.getAspCode(), form.getAspName(),
				form.getMemberCode(), form.getName(), form.getPhone(), form.getEmail(), form.getPageNo(),
				form.getPageSize());

		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");

		return "/admin/asp/manageUser/index";
	}

	@RequestMapping(value = "/list/next", method = RequestMethod.POST)
	public String listNext(@ModelAttribute @Validated(Search0.class) ManagerInfoManageListForm form,
			BindingResult result, Model model) {
		logger.info("Start memberList Controller");

		if (result.hasErrors()) {
			return managerInfoManageList(form, model);
		}

		initListForm(form, model);

		form.setPageNo(form.getPageNo() + 1);

		List<TblUser> memberList = memberInfoManageService.getManagerList(form.getAspCode(), form.getAspName(),
				form.getMemberCode(), form.getName(), form.getPhone(), form.getEmail(), form.getPageNo(),
				form.getPageSize());

		PageInfo<TblUser> pageInfo = new PageInfo<TblUser>(memberList);
		model.addAttribute("pageInfo", pageInfo);
		form.setMemberList(memberList);
		model.addAttribute("modelMemberList", memberList);

		logger.info("End memberList Controller");

		return "/admin/asp/manageUser/index";
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public String details(@ModelAttribute ManagerInfoManageListForm form, Model model) {

		ManagerInfoManageForm modifyForm = new ManagerInfoManageForm();
		initDetailForm(modifyForm, model, form.getSelectedMemberCode());

		model.addAttribute("managerInfoManageForm", modifyForm);
		return "/admin/asp/manageUser/details";
	}

	@RequestMapping(value = "/detailsIndex", method = RequestMethod.POST)
	public String detailsIndex(@ModelAttribute ManagerInfoManageForm form, Model model) {

		initDetailForm(form, model, form.getMemberCode());

		model.addAttribute("managerInfoManageForm", form);
		return "/admin/asp/manageUser/details";
	}

	@RequestMapping(value = "/details/finish", method = RequestMethod.POST)
	public String finish(@ModelAttribute @Validated(All.class) ManagerInfoManageForm form, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("managerInfoManageForm", form);
			setModelMapper(model);
			return detailsIndex(form, model);
		}

		if (!memberInfoManageService.isUniqueEmail(form.getMemberCode(), form.getEmail())) {
			result.rejectValue("email", "error.user", "{0} : 同じEメールアドレスの会員が既に存在しています。");
			model.addAttribute("managerInfoManageForm", form);
			setModelMapper(model);
			return detailsIndex(form, model);
		}

		memberInfoManageService.managerModify(form.createManagerModifyServiceBean());
		result.rejectValue("hasChanged", "error.user", "更新完了しました。");
		model.addAttribute("managerInfoManageForm", form);
		setModelMapper(model);

		return "/admin/asp/manageUser/details";
	}


	private void initListForm(ManagerInfoManageListForm form, Model model) {

	}

	private void initDetailForm(ManagerInfoManageForm form, Model model, String memberCode) {
		form.init(memberInfoManageService, memberCode);
		form.setLoginUserCd(LoginUtility.getLoginUser().getMemberCode());
		setModelMapper(model);
	}

	private void setModelMapper(Model model) {
		model.addAttribute("authTypeCdMap", cdMapService.createMap(CodeTypeCd.AUTH_TYPE_CD));
	}
}
