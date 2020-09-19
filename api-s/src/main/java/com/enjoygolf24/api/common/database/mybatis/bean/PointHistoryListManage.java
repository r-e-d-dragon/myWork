
package com.enjoygolf24.api.common.database.mybatis.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PointHistoryListManage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String memberCode;

	private String lastName;

	private String firstName;

	private String categoryCode;

	private String pointType;

	private String pointAmount;

	private String consumedPoint;

	private String reservationNumber;

	private String registerUser;

	private String registerUserName;

	private String registerDate;

	private String startDateFormatted;

	private String registerDateFormatted;

	private String pointTypeName;

	private String memo;
}
