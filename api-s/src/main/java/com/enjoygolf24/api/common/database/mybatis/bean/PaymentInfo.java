package com.enjoygolf24.api.common.database.mybatis.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class PaymentInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String accountNumber;
	private String aspCode;
	private String bankCode;
	private String branchCode;
	private String careteUser;
	private Timestamp createDate;
	private String memberCode;
	private String nameKana;
	private Timestamp updateDate;
	private String updateUser;

	private Long amount;
	private String transferredResultCode;
	private int totalCount = 0;
	private long totalAmount = 0;
	private int transferredCount = 0;
	private long transferredAmount = 0;
	private int transferImpossibleCount = 0;
	private long transferImpossibleAmount = 0;
	private Date billDate;
	private Date validMonth;

}