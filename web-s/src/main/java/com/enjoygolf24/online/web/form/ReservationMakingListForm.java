package com.enjoygolf24.online.web.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.PageSize;
import com.enjoygolf24.api.common.validator.annotation.ReservationDate;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.common.validator.groups.Search0;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class ReservationMakingListForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@Hankaku(groups = Search0.class, max = 20)
	String AspCode;

	String reservationNumber;

	private String reservationTimeForDisplay;

	@ReservationDate(groups = Insert0.class)
	private String reservationDate;
	private String memberName;
	private String loginUserCd;
	private String reservationTime;
	private String consumedPoint;
	private String batNumber;
	private String batNumberCd;

	private String penaltyPoint;

	private String eventPoint;
	private String monthlyPoint;

	private String pointCategoryCode;
	private String gradeTypeCd;
	private String timeSlotName;

	private String status;
	private String emptyFlag;
	private String dateKind;
	private String selectedReservationId;
	private String reservationId;

	private int limitReservationCount;
	private int limitEventReservationCount;
	private int limitMonthlyReservationCount;

	private String action;

	private List<String> chkBatNumbers;

	@PageSize
	int pageSize = DefaultPageSizeUtility.DEFAULT_PAGE_SIZE;

	/** 現在ページ */
	int pageNo = DefaultPageSizeUtility.PAGE_FIRST;

	TblUser member;

	MemberReservationManage reservationManage;
	List<MemberReservationManage> reservationList = new ArrayList<MemberReservationManage>();
}
