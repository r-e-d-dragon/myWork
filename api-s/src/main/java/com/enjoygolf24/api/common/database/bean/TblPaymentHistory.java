package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_payment_history database table.
 * 
 */
@Entity
@Table(name="tbl_payment_history")
@NamedQuery(name="TblPaymentHistory.findAll", query="SELECT t FROM TblPaymentHistory t")
public class TblPaymentHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TblPaymentHistoryPK id;

	@Column(name="account_number")
	private String accountNumber;

	@Column(name="bank_code")
	private String bankCode;

	@Temporal(TemporalType.DATE)
	@Column(name="bill_date")
	private Date billDate;

	@Column(name="branch_code")
	private String branchCode;

	@Column(name="name_kana")
	private String nameKana;

	public TblPaymentHistory() {
	}

	public TblPaymentHistoryPK getId() {
		return this.id;
	}

	public void setId(TblPaymentHistoryPK id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Date getBillDate() {
		return this.billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getBranchCode() {
		return this.branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getNameKana() {
		return this.nameKana;
	}

	public void setNameKana(String nameKana) {
		this.nameKana = nameKana;
	}

}