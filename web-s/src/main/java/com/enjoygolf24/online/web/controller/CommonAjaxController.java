package com.enjoygolf24.online.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enjoygolf24.api.common.database.bean.ZipMaster;
import com.enjoygolf24.api.service.AspService;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberReservationManageService;
import com.enjoygolf24.api.service.ZipMasterService;
import com.enjoygolf24.online.web.json.JsonAddressResponse;
import com.enjoygolf24.online.web.json.JsonAspResponse;
import com.enjoygolf24.online.web.json.JsonUserResponse;

@Controller 
public class CommonAjaxController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonAjaxController.class);

	@Autowired
	AspService aspService;

	@Autowired
	ZipMasterService zipService;

	@Autowired
	MemberInfoManageService memberInfoManageService;

	@Autowired
	MemberReservationManageService memberReservationManageService;

	@RequestMapping(value = "/public/common/searchAddress")
	public @ResponseBody JsonAddressResponse searchAddress(@RequestParam("headZip") String headZip,
			@RequestParam("tailZip") String tailZip) {
		logger.info("Start searchAddress Controller");
		
		JsonAddressResponse response = new JsonAddressResponse();
		List<ZipMaster> dataList = zipService.searchAddressByZipCode(headZip, tailZip);
		response.setData(dataList);
		logger.info("End searchAddress Controller");
		return response;
    }

	@RequestMapping(value = "/public/common/searchUser")
	public @ResponseBody JsonUserResponse searchUser(@RequestParam("memberCode") String memberCode,
			@RequestParam("reservationDate") String reservationDate) {
		logger.info("Start searchUser Controller");

		JsonUserResponse response = new JsonUserResponse();

		response.setData(memberReservationManageService.getMemberReservationInfo(memberCode, reservationDate));

		logger.info("End searchUser Controller");
		return response;
	}

	@RequestMapping(value = "/public/common/searchAsp")
	public @ResponseBody JsonAspResponse searchAsp(@RequestParam(required = false, name = "aspName") String aspName,
			@RequestParam(required = false, name = "aspAddress") String aspAddress) {
		logger.info("Start searchAsp Controller");

		JsonAspResponse response = new JsonAspResponse();

		response.setData(aspService.getAspList(aspName, "", aspAddress, 1, 1000));

		logger.info("End searchAsp Controller");
		return response;
	}
}
