package com.enjoygolf24.api.common.database.mybatis.bean;

import java.util.Date;

import com.enjoygolf24.api.common.database.bean.TblPaymentInfo;

import lombok.Data;

@Data
public class PaymentInfo extends TblPaymentInfo {

	private static final long serialVersionUID = 1L;

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