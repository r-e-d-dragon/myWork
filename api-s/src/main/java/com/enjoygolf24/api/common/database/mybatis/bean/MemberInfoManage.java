
package com.enjoygolf24.api.common.database.mybatis.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberInfoManage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String aspCode;

	private String aspName;

	private String memberCode;

	private String memberTypeCd;

	private String memberTypeName;

	private String lastName;

	private String firstName;

	private String lastNameKana;

	private String firstNameKana;

	private String email;

	private String phone;
}
