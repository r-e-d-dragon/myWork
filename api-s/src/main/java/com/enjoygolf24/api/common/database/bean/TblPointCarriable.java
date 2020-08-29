package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the code_master database table.
 * 
 */
@Entity
@Table(name = "tbl_point_carriable")
@NamedQuery(name = "TblPointCarriable.findAll", query = "SELECT c FROM TblPointCarriable c")
public class TblPointCarriable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_point_carriable_id_seq")
	@SequenceGenerator(name = "tbl_point_carriable_id_seq", sequenceName = "tbl_point_carriable_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "member_code")
	private String memberCode;

	@Column(name = "point_type_cd")
	private String pointTypeCd;

	@Column(name = "point_variation")
	private Integer pointVariation;

	@Column(name = "reservation_number")
	private String reservationNumber;

	@Temporal(TemporalType.DATE)
	@Column(name = "term_start_date")
	private Date termStartDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "term_end_date")
	private Date termEndDate;

	@Column(name = "register_user")
	private String registerUser;

	@Column(name = "register_date")
	private Timestamp registerDate;

	@Column(name = "update_user")
	private String updateUser;

	@Column(name = "update_date")
	private Timestamp updateDate;

	private String memo;

	public TblPointCarriable() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberCode() {
		return this.memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getPointTypeCd() {
		return this.pointTypeCd;
	}

	public void setPointTypeCd(String pointTypeCd) {
		this.pointTypeCd = pointTypeCd;
	}

	public Integer getPointVariation() {
		return this.pointVariation;
	}

	public void setPointVariation(Integer pointVariation) {
		this.pointVariation = pointVariation;
	}

	public String getReservationNumber() {
		return this.reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public Date getTermStartDate() {
		return this.termStartDate;
	}

	public void setTermStartDate(Date termStartDate) {
		this.termStartDate = termStartDate;
	}

	public Date getTermEndDate() {
		return this.termEndDate;
	}

	public void setTermEndDate(Date termEndDate) {
		this.termEndDate = termEndDate;
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

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}