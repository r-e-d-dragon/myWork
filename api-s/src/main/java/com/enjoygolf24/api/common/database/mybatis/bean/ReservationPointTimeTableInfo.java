package com.enjoygolf24.api.common.database.mybatis.bean;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.enjoygolf24.api.common.database.bean.TblUser;

import lombok.Data;

@Data
public class ReservationPointTimeTableInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dateTime;

	private String dateTypeCd;

	private Time endTime;

	private String gradeCode;

	private String gradeName;

	private String gradeTypeCd;

	private String holydayTypeCd;

	private Long pointAmount;

	private String reservationPointCode;

	private String reservationPointName;

	private Time startTime;

	private Long timeTableCode;

	private String timeTableName;

	private String timeSlotName;

	private Date validateStartTerm;

	private double weekTypeCd;

	TblUser tblUser;
	List<MemberReservationManage> reservationList;
}