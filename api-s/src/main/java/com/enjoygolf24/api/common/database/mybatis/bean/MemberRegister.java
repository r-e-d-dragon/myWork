
package com.enjoygolf24.api.common.database.mybatis.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberRegister implements Serializable {

	private static final long serialVersionUID = 1L;

	private String memberCode;

	private String processingCd;

	private String authStatusCd;

	private String authKey;

}
