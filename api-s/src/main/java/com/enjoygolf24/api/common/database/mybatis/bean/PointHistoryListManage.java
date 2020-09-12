
package com.enjoygolf24.api.common.database.mybatis.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PointHistoryListManage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pointHistoryId;

	private String memberCode;

	private String lastName;

	private String firstName;

	private String pointCode;

	private String consumedPoint;

	private String reservationId;

	private String registerUser;

	private String registerDate;

	private String startDate;

	private String registerDateFormatted;

	private String pointTypeName;

}
