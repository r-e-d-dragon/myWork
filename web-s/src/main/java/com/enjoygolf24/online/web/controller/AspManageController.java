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
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.common.validator.groups.All;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.common.validator.groups.Update0;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.CdMapService;
import com.enjoygolf24.online.web.form.AspListForm;
import com.enjoygolf24.online.web.form.ShopModifyForm;
import com.enjoygolf24.online.web.form.ShopRegisterForm;
import com.github.pagehelper.PageInfo;

//@SessionAttributes(names = "aspListForm")
@Controller
@RequestMapping("/admin/asp")
public class AspManageController {

	private static final Logger logger = LoggerFactory.getLogger(AspManageController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	AspService aspService;

	@Autowired
	private CdMapService cdMapService;


	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String aspListIndex(@ModelAttribute("aspListForm") AspListForm form, HttpServletRequest request,
	        Model model) {
		logger.info("Start aspListIndex Controller");

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblAsp> aspList = aspService.getAspListAll(form.getPageNo(), form.getPageSize());
		form.setAspList(aspList);

		// TODO test
		model.addAttribute("modelAspList", aspList);

		logger.info("End aspListIndex Controller");
		return "/admin/asp/aspIndex";
	}

	@RequestMapping(value = "/manageShop")
	public String aspList(@ModelAttribute("aspListForm") AspListForm form, Model model) {
		logger.info("Start aspList Controller");

		TblUser user = LoginUtility.getLoginUser();

		initListForm(form, model);
		form.setPageNo(DefaultPageSizeUtility.PAGE_FIRST);

		List<TblAsp> aspList = aspService.getAspListAll(form.getPageNo(), form.getPageSize());
		PageInfo<TblAsp> pageInfo = new PageInfo<TblAsp>(aspList);
		model.addAttribute("pageInfo", pageInfo);
		form.setAspList(aspList);

		// TODO test
		model.addAttribute("modelAspList", aspList);

		logger.info("End aspList Controller");
		
		return "/admin/asp/aspIndex";
	}

	@RequestMapping(value = "/createShop", method = RequestMethod.GET)
	public String createShop(@ModelAttribute("shopRegisterForm") ShopRegisterForm form, Model model) {
		logger.info("Start createShop Controller");
		initShopForm(form, model);
		logger.info("End createShop Controller");
		return "/admin/asp/shop/create";
	}

	@RequestMapping(value = "/createShop/finish", method = RequestMethod.POST)
	public String createShopRegister(@ModelAttribute @Validated(All.class) ShopRegisterForm form, BindingResult result,
			Model model) {

		logger.info("Start createShopRegister Controller");

		if (result.hasErrors()) {
			return createShop(form, model);
		}

		TblAsp aspBean = form.converToBean();

		aspBean = aspService.register(aspBean);

		logger.info("End createShopRegister Controller");
		return "/admin/asp/shop/createFinish";
	}

	@RequestMapping(value = "/manageShop/search", method = RequestMethod.POST)
	public String list(@ModelAttribute @Validated(Search0.class) AspListForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return aspList(form, model);
		}

		initListForm(form, model);

		List<TblAsp> aspList = aspService.getAspList(form.getAspName(), form.getAspCode(), form.getAddress(),
		        form.getPageNo(),
		        form.getPageSize());

		PageInfo<TblAsp> pageInfo = new PageInfo<TblAsp>(aspList);
		model.addAttribute("pageInfo", pageInfo);
		form.setAspList(aspList);
		// TODO test
		model.addAttribute("modelAspList", aspList);

		logger.info("End aspList Controller");

		return "/admin/asp/aspIndex";
	}

	@RequestMapping(value = "/manageShop/details", method = RequestMethod.POST)
	public String modify(@ModelAttribute @Validated(Update0.class) AspListForm form, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return aspList(form, model);
		}
		ShopModifyForm modifyForm = new ShopModifyForm();
		
		initShopModifyForm(modifyForm, model);
		TblAsp tblAsp = aspService.getAsp(form.getModAspCode());

		modifyForm.converToForm(tblAsp);
		model.addAttribute("shopModifyForm", modifyForm);

		return "/admin/asp/shop/modify";
	}

	@RequestMapping(value = "/manageShop/details/finish", method = RequestMethod.POST)
	public String modifyFinish(@ModelAttribute @Validated(Update0.class) ShopModifyForm form, BindingResult result,
			Model model) {

		if (result.hasErrors()) {

			initShopModifyForm(form, model);
			return "/admin/asp/shop/modify";
		}
		aspService.modify(form.converToBean());

		return "/admin/asp/shop/modifyFinish";
	}

	/**
	 * フォーム初期処理
	 * 
	 * @param form
	 * @return
	 */
	private void initListForm(AspListForm form, Model model) {
		model.addAttribute("parkingProvideYnMap", cdMapService.createMap(CodeTypeCd.YESNO_TYPE_CD));
	}

	/**
	 * フォーム初期処理
	 * 
	 * @param form
	 * @return
	 */
	private void initShopForm(ShopRegisterForm form, Model model) {

		model.addAttribute("parkingProvideYnMap", cdMapService.createMap(CodeTypeCd.YESNO_TYPE_CD));
	}

	/**
	 * フォーム初期処理
	 * 
	 * @param form
	 * @return
	 */
	private void initShopModifyForm(ShopModifyForm form, Model model) {

		model.addAttribute("parkingProvideYnMap", cdMapService.createMap(CodeTypeCd.YESNO_TYPE_CD));
	}
}
