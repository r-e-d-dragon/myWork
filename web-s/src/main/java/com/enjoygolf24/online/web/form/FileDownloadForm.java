package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.enjoygolf24.api.common.validator.groups.Insert0;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class FileDownloadForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@NotBlank(groups = Insert0.class)
	String aspCode;
	String memberCode;
	String filenpath;
	String filename;
	String targetMonth;

	String loginUserCd;
	String downloadDate;
	String downloadUserCd;
}
