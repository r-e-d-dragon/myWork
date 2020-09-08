package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the tbl_payment_info database table.
 * 
 */
@Entity
@Table(name = "tbl_payment_info")
@NamedQuery(name = "TblPaymentInfo.findAll", query = "SELECT t FROM TblPaymentInfo t")
public class TblPaymentInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_payment_info_id_seq")
	@SequenceGenerator(name = "tbl_payment_info_id_seq", sequenceName = "tbl_payment_info_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "asp_code")
	private String aspCode;

	@Column(name = "bank_code")
	private String bankCode;

	@Column(name = "branch_code")
	private String branchCode;

	@Column(name = "carete_user")
	private String careteUser;

	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "member_code")
	private String memberCode;

	@Column(name = "name_kana")
	private String nameKana;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	public TblPaymentInfo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAspCode() {
		return this.aspCode;
	}

	public void setAspCode(String aspCode) {
		this.aspCode = aspCode;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBranchCode() {
		return this.branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCareteUser() {
		return this.careteUser;
	}

	public void setCareteUser(String careteUser) {
		this.careteUser = careteUser;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getMemberCode() {
		return this.memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getNameKana() {
		return this.nameKana;
	}

	public void setNameKana(String nameKana) {
		this.nameKana = nameKana;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}