package com.enjoygolf24.online.web.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.modelmapper.ModelMapper;

import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.PageSize;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;
import com.enjoygolf24.api.common.validator.annotation.Zenkaku;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.service.bean.MemberReservationServiceBean;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class MacroReservationManageListForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@Hankaku(groups = Search0.class, max = 20)
	String reservationNumber;
	String reservationDate;
	String reservationTime;

	@Hankaku(groups = Search0.class, max = 20)
	String memberCode;

	@Hankaku(groups = Search0.class, max = 20)
	String aspCode;

	@Zenkaku(groups = Search0.class, max = 200)
	@SjisSafe(groups = Search0.class)
	String aspName;

	private String batNumber;
	private String memberName;
	private String loginUserCd;

	@NotBlank(groups = Insert0.class)
	private String macroName;

	@NotBlank(groups = Insert0.class)
	private String fromReservationDate;
	@NotBlank(groups = Insert0.class)
	private String toReservationDate;
	@NotBlank(groups = Insert0.class)
	private String fromReservationTime;
	@NotBlank(groups = Insert0.class)
	private String toReservationTime;

	private String pointCategoryCode;
	private String pointGrade;
	private String timeSlotName;

	private String status;

	private String selectedReservationId;
	private String reservationId;

	@NotEmpty(groups = Insert0.class)
	private List<String> chkBatNumbers;

	@PageSize
	int pageSize = DefaultPageSizeUtility.DEFAULT_PAGE_SIZE;

	/** 現在ページ */
	int pageNo = DefaultPageSizeUtility.PAGE_FIRST;

	TblUser member;
	MemberReservationManage reservationManage;
	List<MemberReservationManage> reservationList = new ArrayList<MemberReservationManage>();

	public MemberReservationServiceBean createMemberReservationServiceBean() {
		ModelMapper modelMapper = new ModelMapper();
		MemberReservationServiceBean serviceBean = modelMapper.map(this, MemberReservationServiceBean.class);
		return serviceBean;
	}
}
