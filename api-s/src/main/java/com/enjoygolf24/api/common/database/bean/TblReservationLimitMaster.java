package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the tbl_reservation_limit_master database table.
 * 
 */
@Entity
@Table(name = "tbl_reservation_limit_master")
@NamedQuery(name = "TblReservationLimitMaster.findAll", query = "SELECT t FROM TblReservationLimitMaster t")
public class TblReservationLimitMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TblReservationLimitMasterPK id;

	@Column(name = "event_limit")
	private Integer eventLimit;

	@Column(name = "monthly_limit")
	private Integer monthlyLimit;

	@Column(name = "register_date")
	private Timestamp registerDate;

	@Column(name = "register_user")
	private String registerUser;

	private String remarks;

	@Column(name = "reservation_limit")
	private Integer reservationLimit;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	public TblReservationLimitMaster() {
	}

	public TblReservationLimitMasterPK getId() {
		return this.id;
	}

	public void setId(TblReservationLimitMasterPK id) {
		this.id = id;
	}

	public Integer getEventLimit() {
		return this.eventLimit;
	}

	public void setEventLimit(Integer eventLimit) {
		this.eventLimit = eventLimit;
	}

	public Integer getMonthlyLimit() {
		return this.monthlyLimit;
	}

	public void setMonthlyLimit(Integer monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
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

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getReservationLimit() {
		return this.reservationLimit;
	}

	public void setReservationLimit(Integer reservationLimit) {
		this.reservationLimit = reservationLimit;
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