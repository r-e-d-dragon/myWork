package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.validator.annotation.PageSize;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class ReservationInfoListForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	String reservationId;

	@PageSize
	int pageSize = DefaultPageSizeUtility.DEFAULT_PAGE_SIZE;

	/** 現在ページ */
	int pageNo = DefaultPageSizeUtility.PAGE_FIRST;

}
