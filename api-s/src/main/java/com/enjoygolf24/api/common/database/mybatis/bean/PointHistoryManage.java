
package com.enjoygolf24.api.common.database.mybatis.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PointHistoryManage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pointCode;

	private String consumedPoint;

	private String reservationId;

	private String registerUserCode;

	private String registerUserName;

	private String registerDate;

	private String memo;

	private String startDate;

	private String registerDateFormatted;

	private String pointTypeName;

	private String memberCode;

}
