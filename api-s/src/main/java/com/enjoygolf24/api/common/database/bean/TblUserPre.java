package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the tbl_user database table.
 * 
 */
@Entity
@Table(name = "tbl_user_pre")
@NamedQuery(name = "TblUserPre.findAll", query = "SELECT t FROM TblUserPre t")
public class TblUserPre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "pre_member_code")
	private String preMemberCode;

	@Column(name = "asp_code")
	private String aspCode;

	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "first_name_kana")
	private String firstNameKana;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "last_name_kana")
	private String lastNameKana;

	private String memo;

	private String password;

	private String phone;

	@Column(name = "register_date")
	private Timestamp registerDate;

	@Column(name = "register_user")
	private String registerUser;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	@Column(name = "use_flag")
	private String useFlag;

	public TblUserPre() {
	}

	public String getPreMemberCode() {
		return this.preMemberCode;
	}

	public void setPreMemberCode(String preMemberCode) {
		this.preMemberCode = preMemberCode;
	}

	public String getAspCode() {
		return this.aspCode;
	}

	public void setAspCode(String aspCode) {
		this.aspCode = aspCode;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstNameKana() {
		return this.firstNameKana;
	}

	public void setFirstNameKana(String firstNameKana) {
		this.firstNameKana = firstNameKana;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastNameKana() {
		return this.lastNameKana;
	}

	public void setLastNameKana(String lastNameKana) {
		this.lastNameKana = lastNameKana;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public String getRegisterUser() {
		return this.registerUser;
	}

	public void setRegisterUser(String registerUser) {
		this.registerUser = registerUser;
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

	public String getUseFlag() {
		return this.useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	public String getUserName() {
		return this.firstName + " " + this.lastName;
	}

}