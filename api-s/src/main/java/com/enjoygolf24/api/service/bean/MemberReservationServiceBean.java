package com.enjoygolf24.api.service.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MemberReservationServiceBean {

	private String reservationId;

	private String aspCode;

	private String batNumber;

	private Integer eventPoint;

	private String memberCode;

	private Integer monthlyPoint;

	private String registerUser;

	private String reservationDate;

	private String reservationNumber;

	private String reservationTime;

	private String status;

	private String loginUserCd;

	private Integer consumedPoint;
	private Integer penaltyPoint;

	private String pointCategoryCode;
	private String pointGrade;

	private String fromReservationDate;
	private String fromReservationTime;
	private String toReservationDate;
	private String toReservationTime;
	private List<String> chkBatNumbers = new ArrayList<String>();
}
