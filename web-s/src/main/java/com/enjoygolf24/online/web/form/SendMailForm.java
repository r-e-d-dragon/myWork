package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.enjoygolf24.api.common.validator.annotation.Email;
import com.enjoygolf24.api.common.validator.groups.Insert0;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class SendMailForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@NotBlank(groups = Insert0.class)
	String aspCode;
	String memberCode;

	@NotBlank(groups = Insert0.class)
	@Email(groups = Insert0.class)
	String fromMail;
	@NotBlank(groups = Insert0.class)
	String memberName;
	@NotBlank(groups = Insert0.class)
	String title;
	@NotBlank(groups = Insert0.class)
	String comment;

	String loginUserCd;
	String updateDate;
	String updateUserCd;

}
