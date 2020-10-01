package com.enjoygolf24.api.service.bean;

import java.util.Date;

import lombok.Data;

@Data
public class MemberModifyServiceBean {

	String address1;

	String address2;

	String memberCode;

	String aspCode;

	String email;

	String firstName;

	String firstNameKana;

	String lastName;

	String lastNameKana;

	String memberTypeCd;

	String phone;

	String zip1;

	String zip2;

	String memo;

	String loginUserCd;

	String initPasswordCd;

	String gender;

	Date birthday;

	String jobCode;

	String useFlag;

	String additionalLessonCd;
}
