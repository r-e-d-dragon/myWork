package com.enjoygolf24.online.web.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.Kana;
import com.enjoygolf24.api.common.validator.annotation.Kanji;
import com.enjoygolf24.api.common.validator.annotation.PageSize;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;
import com.enjoygolf24.api.common.validator.annotation.Telephone;
import com.enjoygolf24.api.common.validator.annotation.Zenkaku;
import com.enjoygolf24.api.common.validator.groups.Search0;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class MemberReservationManageListForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@Hankaku(groups = Search0.class, max = 20)
	String reservationNumber;
	@Hankaku(groups = Search0.class, max = 20)
	String reservationDate;
	@Hankaku(groups = Search0.class, max = 20)
	String memberCode;

	@Hankaku(groups = Search0.class, max = 20)
	String aspCode;

	@Zenkaku(groups = Search0.class, max = 200)
	@SjisSafe(groups = Search0.class)
	String aspName;

	@Kanji(groups = Search0.class, max = 80)
	@SjisSafe(groups = Search0.class)
	private String firstName;

	@Kana(groups = Search0.class, max = 80)
	@SjisSafe(groups = Search0.class)
	private String firstNameKana;

	@Kanji(groups = Search0.class, max = 80)
	@SjisSafe(groups = Search0.class)
	private String lastName;

	@Kana(groups = Search0.class, max = 80)
	@SjisSafe(groups = Search0.class)
	private String lastNameKana;

	@Telephone(groups = Search0.class)
	private String phone;

	@Size(groups = Search0.class, max = 80)
	private String email;

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
