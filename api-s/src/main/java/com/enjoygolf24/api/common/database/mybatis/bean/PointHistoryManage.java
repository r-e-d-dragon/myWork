
package com.enjoygolf24.api.common.database.mybatis.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PointHistoryManage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pointTypeCd;

	private String pointVariation;

	private String reservationNumber;

	private String registerUser;

	private String registerDate;

	private String memo;

	private String termStartDate;

	private String registerDateFormatted;

	private String pointTypeName;

	public String getMemo() {
		if (memo != null) {
			return memo;
		} else {
			return pointTypeName;
		}

	}

}
