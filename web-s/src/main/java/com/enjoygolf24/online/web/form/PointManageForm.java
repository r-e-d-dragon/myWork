package com.enjoygolf24.online.web.form;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.PointExpirationTermCd;
import com.enjoygolf24.api.common.code.PointTypeCd;
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

	private String memberGradeCode;

	private String memberGradeName;

	private String hasChanged;
	
	private int monthlyPointBalance;
	
	private int carriablePointBalance;

	@NotBlank(groups = Insert0.class)
	private String loginUserCd;

	@NotBlank(groups = Insert0.class)
	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.POINT_CATEGORY_TERM_CD)
	private String pointExpirationTermCd;

	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.POINT_TYPE_CD, except = { PointTypeCd.USE,
			PointTypeCd.OLDBIE })
	private String monthlyPointTypeCd;

	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.POINT_TYPE_CD, except = { PointTypeCd.USE,
			PointTypeCd.MONTLY_POINT, PointTypeCd.BIRHDAY, PointTypeCd.INTRODUCTION })
	private String carriablePointTypeCd;

	@Numeric(groups = Insert0.class)
	private String pointVariation;

	@NotBlank(groups = Insert0.class)
	private String termStartDate;

	@Size(groups = Insert0.class, max = 1200)
	private String memo;

	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.POINT_CATEGORY_TERM_CD)
	private String pointCategoryCd;

	@PageSize
	int pageSize = DefaultPageSizeUtility.DEFAULT_PAGE_SIZE;

	/** 現在ページ */
	int pageNo = DefaultPageSizeUtility.PAGE_FIRST;

	List<TblPointHistory> pointHistoryList;

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.needNotBlankWhenCustomPoint}")
	public boolean isFilledCustomRequired() {
		String pointTypeCd = (StringUtils.isEmptyOrWhitespace(carriablePointTypeCd)) ? monthlyPointTypeCd
				: carriablePointTypeCd;

		if (PointTypeCd.CUSTOM.equals(pointTypeCd)) {
			return !(StringUtils.isEmptyOrWhitespace(pointVariation)
					&& StringUtils.isEmptyOrWhitespace(memo));
		} else {
			return true;
		}
	}

	@AssertTrue(groups = Insert0.class, message = "{application.combination.validation.needNotBlankPointTypeCd}")
	public boolean isFilledPointTypeCd() {
		if (PointExpirationTermCd.MONTLY.equals(pointExpirationTermCd)) {
			return !(StringUtils.isEmptyOrWhitespace(monthlyPointTypeCd));
		} else {
			return !(StringUtils.isEmptyOrWhitespace(carriablePointTypeCd));
		}
	}

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

		memberGradeCode = selectedMember.getMemberGradeCode();
		memberGradeName = pointService.getMemberGradeName(memberGradeCode);

		pointExpirationTermCd = null;
		monthlyPointTypeCd = null;
		carriablePointTypeCd = null;
		pointVariation = null;
		termStartDate = null;
		memo = null;
	}

	public PointManageServiceBean createPointManageServiceBean() {

		PointManageServiceBean serviceBean = new PointManageServiceBean();

		serviceBean.setLoginUserCd(loginUserCd);
		serviceBean.setMemberCode(memberCode);
		serviceBean.setMemo(memo);
		serviceBean.setPointExpirationTermCd(pointExpirationTermCd);
		String pointTypeCd = (StringUtils.isEmptyOrWhitespace(carriablePointTypeCd)) ? monthlyPointTypeCd
				: carriablePointTypeCd;
		serviceBean.setPointTypeCd(pointTypeCd);
		serviceBean.setPointVariation(pointVariation);
		serviceBean.setTermStartDate(termStartDate);

		return serviceBean;
	}
}
