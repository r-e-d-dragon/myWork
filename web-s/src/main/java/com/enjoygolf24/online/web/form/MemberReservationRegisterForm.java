package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.database.bean.TblReservation;
import com.enjoygolf24.api.common.validator.annotation.CodeMaster;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.Numeric;
import com.enjoygolf24.api.common.validator.annotation.ReservationDate;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.service.bean.MemberReservationServiceBean;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class MemberReservationRegisterForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@NotBlank(groups = Search0.class)
	private String reservationId;
	private String reservationNumber;

	@Hankaku(groups = Insert0.class)
	@NotBlank(groups = Insert0.class)
	private String aspCode;

	@NotBlank(groups = Insert0.class)
	private String batNumber;
	private String batNumberCd;

	@NotBlank(groups = Insert0.class)
	private String memberCode;

	@NotBlank(groups = Insert0.class)
	@ReservationDate(groups = Insert0.class)
	private String reservationDate;

	@NotBlank(groups = Insert0.class)
	private String reservationTime;

	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.RESERVATION_STATUS_TYPE_CD)
	private String status;

	@NotBlank(groups = Insert0.class)
	private String loginUserCd;

	@NotBlank(groups = Insert0.class)
	private String consumedPoint;

	@NotBlank(groups = Insert0.class)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.POINT_CATEGORY_CD)
	private String pointCategoryCode;

	@Numeric
	private int reservationCnt;

	@Numeric
	private int reservationPoint;

	private String gradeTypeCd;

	private String memberName;
	private String penaltyPoint;
	private String eventPoint;
	private String monthlyPoint;

	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.HOLIDAY_TYPE_CD)
	private String dateKind;
	private String selectedReservationId;
	private String selectedMemberCode;

	@Numeric
	private int totalMonthlyPoint;

	@Numeric
	private int totalEventPoint;

	@Numeric
	private int validMonthlyPoint;

	@Numeric
	private int validEventPoint;

	@Numeric
	private int limitReservationCount;

	@Numeric
	private int limitEventReservationCount;

	@Numeric
	private int limitMonthlyReservationCount;

	@Numeric
	private int limitReservationPoint;

	TblReservation reservation;

	private boolean valid;

	private boolean hasChanged;
	private String searchTime;

	private String trigger;

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.reservationPointLimitOver}")
	public boolean isLessThanPointLimit() {
		return (reservationPoint + Integer.parseInt(consumedPoint) <= limitReservationPoint);
	}

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.reservationCntLimitOver}")
	public boolean isLessThanCntLimit() {
		return (reservationCnt + 1 <= limitReservationCount);
	}


	public MemberReservationServiceBean createMemberReservationServiceBean() {
		ModelMapper modelMapper = new ModelMapper();

		MemberReservationServiceBean serviceBean = modelMapper.map(this, MemberReservationServiceBean.class);

		return serviceBean;
	}

}
