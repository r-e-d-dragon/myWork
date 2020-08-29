package com.enjoygolf24.api.service.bean;

import java.util.Date;

import lombok.Data;

@Data
public class PreMemberConvertServiceBean {

	String address1;

	String address2;

	String bankKana;

	String preMemberCode;

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

	String gender;

	Date birthday;

	String memberGradeCode;

	String memberGradeTimeCode;

	String jobCode;

	String useFlag;

	String additionalLessonCd;
}
