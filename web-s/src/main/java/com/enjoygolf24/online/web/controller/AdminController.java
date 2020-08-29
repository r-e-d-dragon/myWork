package com.enjoygolf24.online.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.mybatis.bean.TblMenuInfo;
import com.enjoygolf24.api.common.database.mybatis.repository.MenuMapper;
import com.enjoygolf24.api.common.utility.LoginUtility;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	MenuMapper menuMapper;

	@RequestMapping(value = "/admin")
	public String admin(HttpServletRequest request, Model model) {
		logger.info("Start login Controller");

		TblUser user = LoginUtility.getLoginUser();
		List<TblMenuInfo> menuInfoList = menuMapper.getMainMenuListByUser(user.getMemberCode());

		Map<String, List<TblMenuInfo>> menuMap = new LinkedMap<String, List<TblMenuInfo>>();
		List<TblMenuInfo> topMenuList = new ArrayList<TblMenuInfo>();

		for (TblMenuInfo menu : menuInfoList) {

			if (menu.getLevel() < 1) {
				continue;
			}

			if (menu.getLevel() == 1) {
				if (!menuMap.containsKey(menu.getMemuId())) {
					menuMap.put(menu.getMemuId(), new ArrayList<TblMenuInfo>());
					topMenuList.add(menu);
				}
			} else {
				if (menuMap.containsKey(menu.getParentId())) {
					menuMap.get(menu.getParentId()).add(menu);
				}
			}
		}

		model.addAttribute("userMenuMap", menuMap);
		model.addAttribute("topMenuList", topMenuList);

		logger.info("End login Controller");
		return "/admin/index";
	}

	@RequestMapping(value = "/admin/main")
	public String main(HttpServletRequest request, Model model) {
		logger.info("Start login Controller");


		logger.info("End login Controller");
		return admin(request, model);
	}

}
