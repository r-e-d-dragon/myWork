
package com.enjoygolf24.api.common.database.mybatis.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PointManage implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long pointManageId;

	private String aspCode;

	private String aspName;

	private String memberCode;

	private String memberGradeCd;

	private String memberGradeName;

	private String lastName;

	private String firstName;

	private String lastNameKana;

	private String firstNameKana;

	private String email;

	private String phone;

	private String categoryCode;
	private String pointType;
	private Integer consumedPoint;
	private Integer pointAmount;
	private String startDate;
	private String endDate;
}
