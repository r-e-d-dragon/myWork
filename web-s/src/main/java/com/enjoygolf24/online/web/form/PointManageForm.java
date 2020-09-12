package com.enjoygolf24.online.web.form;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.database.bean.TblPointHistory;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.validator.annotation.CodeMaster;
import com.enjoygolf24.api.common.validator.annotation.Numeric;
import com.enjoygolf24.api.common.validator.annotation.PageSize;
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
	
	private int carriablePointBalance;

	private int pointVariationDefault;

	private int pointVariationMin;

	private int pointVariationMax;

	private int pointVariationSize;

	@NotBlank(groups = Insert0.class)
	private String loginUserCd;

	@Numeric(groups = Insert0.class)
	private String pointVariation;

	@NotBlank(groups = Insert0.class)
	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.POINT_APPLIED_MONTH)
	private String pointAppliedMonthCd;

	@PageSize
	int pageSize = DefaultPageSizeUtility.DEFAULT_PAGE_SIZE;

	/** 現在ページ */
	int pageNo = DefaultPageSizeUtility.PAGE_FIRST;

	List<TblPointHistory> pointHistoryList;


	public void init(PointService pointService, String memberCode, int defaultVal, int min, int max, int size) {
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
		pointAppliedMonthCd = null;

		pointVariationDefault = defaultVal;

		pointVariationMin = min;

		pointVariationMax = max;

		pointVariationSize = size;

	}

	public PointManageServiceBean createPointManageServiceBean() {

		PointManageServiceBean serviceBean = new PointManageServiceBean();

		serviceBean.setLoginUserCd(loginUserCd);
		serviceBean.setMemberCode(memberCode);

		serviceBean.setPointVariation(pointVariation);
		serviceBean.setPointAppliedMonthCd(pointAppliedMonthCd);

		return serviceBean;
	}
}
