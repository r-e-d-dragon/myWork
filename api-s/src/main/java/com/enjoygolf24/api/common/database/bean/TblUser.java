package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the tbl_user database table.
 * 
 */
@Entity
@Table(name = "tbl_user")
@NamedQuery(name = "TblUser.findAll", query = "SELECT t FROM TblUser t")
public class TblUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "member_code")
	private String memberCode;

	@Column(name = "additional_lesson_cd")
	private String additionalLessonCd;

	private String address1;

	private String address2;

	@Column(name = "asp_code")
	private String aspCode;

	@Column(name = "auth_type_cd")
	private String authTypeCd;

	@Column(name = "bank_kana")
	private String bankKana;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "first_name_kana")
	private String firstNameKana;

	private String gender;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "last_name_kana")
	private String lastNameKana;

	@Column(name = "member_grade_code")
	private String memberGradeCode;

	@Column(name = "member_grade_time_code")
	private String memberGradeTimeCode;

	@Column(name = "member_type_cd")
	private String memberTypeCd;

	@Column(name = "job_code")
	private String jobCode;

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

	private String zip1;

	private String zip2;

	public TblUser() {
	}

	public String getMemberCode() {
		return this.memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getAdditionalLessonCd() {
		return this.additionalLessonCd;
	}

	public void setAdditionalLessonCd(String additionalLessonCd) {
		this.additionalLessonCd = additionalLessonCd;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAspCode() {
		return this.aspCode;
	}

	public void setAspCode(String aspCode) {
		this.aspCode = aspCode;
	}

	public String getAuthTypeCd() {
		return this.authTypeCd;
	}

	public void setAuthTypeCd(String authTypeCd) {
		this.authTypeCd = authTypeCd;
	}

	public String getBankKana() {
		return this.bankKana;
	}

	public void setBankKana(String bankKana) {
		this.bankKana = bankKana;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getMemberGradeCode() {
		return this.memberGradeCode;
	}

	public void setMemberGradeCode(String memberGradeCode) {
		this.memberGradeCode = memberGradeCode;
	}

	public String getMemberGradeTimeCode() {
		return this.memberGradeCode;
	}

	public void setMemberGradeTimeCode(String memberGradeTimeCode) {
		this.memberGradeTimeCode = memberGradeTimeCode;
	}

	public String getMemberTypeCd() {
		return this.memberTypeCd;
	}

	public void setMemberTypeCd(String memberTypeCd) {
		this.memberTypeCd = memberTypeCd;
	}

	public String getJobCode() {
		return this.jobCode;
	}

	public void setJobCd(String jobCode) {
		this.jobCode = jobCode;
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

	public String getZip1() {
		return this.zip1;
	}

	public void setZip1(String zip1) {
		this.zip1 = zip1;
	}

	public String getZip2() {
		return this.zip2;
	}

	public void setZip2(String zip2) {
		this.zip2 = zip2;
	}

	public String getUserName() {
		return this.firstName + " " + this.lastName;
	}

}