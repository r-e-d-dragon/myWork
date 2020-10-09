
package com.enjoygolf24.api.common.database.mybatis.bean;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.enjoygolf24.api.common.database.bean.TblUser;

import lombok.Data;

@Data
public class MemberReservationManage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reservationId;
	private String reservationNumber;
	private String status;
	private String memberCode;
	private String aspCode;
	private String aspName;
	private String batNumber;
	private String reservationDate;
	private String reservationTime;
	private String consumedPoint;
	private String penaltyPoint;
	private String registerUser;
	private String registerDate;
	private String updateUser;
	private String updateDate;
	private String lastName;
	private String firstName;
	private String expireFlag;
	private String batNumberCd;
	private String timeSlot;
	private String timeSlotName;
	private String emptyFlag;
	private String dateKind;

	private Long pointManageId;
	private String pointCategoryCode;
	private String pointGrade;
	private String pointType;
	private int pointAmount;
	private String startDate;
	private String endDate;

	private int validMonthlyPoint;
	private int currentMonthlyPoint;
	private int nextMonthlyPoint;

	private int validEventPoint;

	private int monthlyReservationCount;
	private int eventReservationCount;

	private int limitReservationCount;
	private int limitEventReservationCount;
	private int limitMonthlyReservationCount;
	private int limitReservationPoint;

	private String fromReservationDate;
	private String fromReservationTime;
	private String toReservationDate;
	private String toReservationTime;
	private String macroName;
	private String macroDateType;
	private String macroDateTypeName;

	private Time startTime;
	private Time endTime;

	private String currentMonth;
	private String nextMonth;

	TblUser tblUser;
	List<MemberReservationManage> reservationList;
	List<PointManage> pointList = new ArrayList<PointManage>();
}
