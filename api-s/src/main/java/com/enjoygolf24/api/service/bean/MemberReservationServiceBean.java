package com.enjoygolf24.api.service.bean;

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
}
