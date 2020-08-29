package com.enjoygolf24.online.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.code.ReservationStatusCd;
import com.enjoygolf24.api.common.database.bean.TblReservationLimitMaster;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.bean.ZipMaster;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.service.MemberInfoManageService;
import com.enjoygolf24.api.service.MemberReservationManageService;
import com.enjoygolf24.api.service.ZipMasterService;
import com.enjoygolf24.online.web.json.JsonAddressResponse;
import com.enjoygolf24.online.web.json.JsonUserResponse;

@Controller 
public class CommonAjaxController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonAjaxController.class);

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

		MemberReservationManage reservation = new MemberReservationManage();

		// 会員情報取得
		TblUser member = memberInfoManageService.selectMember(memberCode);
		reservation.setTblUser(member);

		// 月ポイント情報取得
		List<MemberReservationManage> totMonPointList = memberReservationManageService
				.getMemberPointManageList(memberCode, PointCategoryCd.MONTLY_POINT, null);
		List<MemberReservationManage> validMonPointList = memberReservationManageService
				.getMemberPointManageList(memberCode, PointCategoryCd.MONTLY_POINT, reservationDate);
		// 月ポイント情報取得
		List<MemberReservationManage> totEvtPointList = memberReservationManageService
				.getMemberPointManageList(memberCode, PointCategoryCd.EVENT_POINT, null);
		List<MemberReservationManage> validEvtPointList = memberReservationManageService
				.getMemberPointManageList(memberCode, PointCategoryCd.EVENT_POINT, reservationDate);

		reservation.setTotalMonthlyPoint(totMonPointList.stream().mapToInt(x -> x.getPointAmount()).sum());
		reservation.setTotalEventPoint(totEvtPointList.stream().mapToInt(x -> x.getPointAmount()).sum());
		reservation.setValidMonthlyPoint(validMonPointList.stream().mapToInt(x -> x.getPointAmount()).sum());
		reservation.setValidEventPoint(validEvtPointList.stream().mapToInt(x -> x.getPointAmount()).sum());

		List<MemberReservationManage> reservationList = memberReservationManageService.getMemberReservationAllList(null,
				memberCode, null, null, null, null, ReservationStatusCd.STATUS_FIXED, true);
		reservation.setReservationList(reservationList);

		reservation.setMonthlyReservationCount(
				reservationList.stream().filter(p -> p.getPointCategoryCode().equals(PointCategoryCd.MONTLY_POINT))
						.collect(Collectors.toList()).size());
		reservation.setEventReservationCount(
				reservationList.stream().filter(p -> p.getPointCategoryCode().equals(PointCategoryCd.EVENT_POINT))
						.collect(Collectors.toList()).size());

		TblReservationLimitMaster tblReservationLimitMaster = memberReservationManageService
				.getMemberReservationLimit(CodeTypeCd.MEMBER_GRADE_CD, member.getMemberGradeCode());
		if (tblReservationLimitMaster != null) {
			reservation.setLimitReservationCount(tblReservationLimitMaster.getReservationLimit());
			reservation.setLimitEventReservationCount(tblReservationLimitMaster.getEventLimit());
			reservation.setLimitMonthlyReservationCount(tblReservationLimitMaster.getMonthlyLimit());
		} else {
			reservation.setLimitReservationCount(0);
			reservation.setLimitEventReservationCount(0);
			reservation.setLimitMonthlyReservationCount(0);
		}

		response.setData(reservation);

		logger.info("End searchUser Controller");
		return response;
	}
}
