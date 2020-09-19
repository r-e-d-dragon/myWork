package com.enjoygolf24.online.web.form;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.PointAppliedMonthCd;
import com.enjoygolf24.api.common.constants.PointContants;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.validator.annotation.CodeMaster;
import com.enjoygolf24.api.common.validator.annotation.Numeric;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.service.PointService;
import com.enjoygolf24.api.service.bean.PointManageServiceBean;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class PointManageForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	private PointService pointService;

	private TblUser selectedMember;

	private String memberCode;

	private String email;

	private String memberName;

	private String birthday;

	private String registerDate;

	private String hasChanged;
	
	private int monthlyPointBalance;
	
	private int eventPointBalance;

	private int monthlyPointBalanceNextMonth;

	private int eventPointBalanceNextMonth;

	private int pointVariationDefault;

	private int pointVariationMin;

	private int pointVariationMax;

	private int pointVariationSize;

	private String memo;

	@NotBlank(groups = Insert0.class)
	private String loginUserCd;

	@NotBlank(groups = Insert0.class)
	@Numeric(groups = Insert0.class, min = PointContants.MIN, max = PointContants.MAX)
	private String pointVariation;

	@NotBlank(groups = Insert0.class)
	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.POINT_APPLIED_MONTH)
	private String pointAppliedMonthCd;


	public void init(PointService pointService, String memberCode) {
		this.pointService = pointService;
		this.selectedMember = pointService.selectMember(memberCode);
		this.memberCode = memberCode;

		email = selectedMember.getEmail();
		memberName = selectedMember.getUserName();

		DateFormat dateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT);
		if (selectedMember.getBirthday() != null) {
			birthday = dateFormat.format(selectedMember.getBirthday());
		}

		registerDate = dateFormat.format(selectedMember.getRegisterDate());

		pointVariation = null;
		pointAppliedMonthCd = PointAppliedMonthCd.THIS_MONTH;

		pointVariationDefault = PointContants.DEFAULT;

		pointVariationMin = PointContants.MIN;

		pointVariationMax = PointContants.MAX;

		pointVariationSize = PointContants.SIZE;

		memo = null;

	}

	public PointManageServiceBean createPointManageServiceBean() {

		PointManageServiceBean serviceBean = new PointManageServiceBean();

		serviceBean.setLoginUserCd(loginUserCd);
		serviceBean.setMemberCode(memberCode);

		serviceBean.setPointVariation(pointVariation);
		serviceBean.setPointAppliedMonthCd(pointAppliedMonthCd);
		serviceBean.setMemo(memo);

		return serviceBean;
	}
}
