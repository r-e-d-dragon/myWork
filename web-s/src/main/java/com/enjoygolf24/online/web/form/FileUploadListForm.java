package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.enjoygolf24.api.common.validator.groups.Insert0;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class FileUploadListForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@NotBlank(groups = Insert0.class)
	String aspCode;
	String memberCode;
	String filenpath;

	@NotBlank(groups = Insert0.class)
	String filename;

	String loginUserCd;
	String updateDate;
	String updateUserCd;

	MultipartFile multipartFile;

//	public MemberReservationServiceBean createMemberReservationServiceBean() {
//		ModelMapper modelMapper = new ModelMapper();
//
//		MemberReservationServiceBean serviceBean = modelMapper.map(this, MemberReservationServiceBean.class);
//
//		return serviceBean;
//	}



}
