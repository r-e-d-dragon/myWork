package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.enjoygolf24.api.common.database.bean.TblReservation;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
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
	private String reservationDate;

	@NotBlank(groups = Insert0.class)
	private String reservationTime;

	private String status;

	@NotBlank(groups = Insert0.class)
	private String loginUserCd;

	@NotBlank(groups = Insert0.class)
	private String consumedPoint;
	@NotBlank(groups = Insert0.class)
	private String pointCategoryCode;
	private String pointGrade;

	private String memberName;
	private String penaltyPoint;
	private String eventPoint;
	private String monthlyPoint;
	private String dateKind;
	private String selectedReservationId;
	private String selectedMemberCode;

	private int totalMonthlyPoint;
	private int totalEventPoint;
	private int validMonthlyPoint;
	private int validEventPoint;

	private int limitReservationCount;
	private int limitEventReservationCount;
	private int limitMonthlyReservationCount;
	private int limitReservationPoint;

	TblReservation reservation;

	private boolean valid;

	public MemberReservationServiceBean createMemberReservationServiceBean() {
		ModelMapper modelMapper = new ModelMapper();

		MemberReservationServiceBean serviceBean = modelMapper.map(this, MemberReservationServiceBean.class);

		return serviceBean;
	}

}
