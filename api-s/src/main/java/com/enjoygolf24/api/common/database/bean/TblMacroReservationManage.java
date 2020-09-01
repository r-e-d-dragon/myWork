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
 * The persistent class for the tbl_macro_reservation_manage database table.
 * 
 */
@Entity
@Table(name = "tbl_macro_reservation_manage")
@NamedQuery(name = "TblMacroReservationManage.findAll", query = "SELECT t FROM TblMacroReservationManage t")
public class TblMacroReservationManage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_macro_reservation_manage_id_seq")
	@SequenceGenerator(name = "tbl_macro_reservation_manage_id_seq", sequenceName = "tbl_macro_reservation_manage_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "asp_code")
	private String aspCode;

	@Column(name = "bat_number")
	private String batNumber;

	@Temporal(TemporalType.DATE)
	@Column(name = "from_reservation_date")
	private Date fromReservationDate;

	@Column(name = "from_reservation_time")
	private String fromReservationTime;

	@Column(name = "macro_name")
	private String macroName;

	@Column(name = "register_date")
	private Timestamp registerDate;

	@Column(name = "register_user")
	private String registerUser;

	@Column(name = "reservation_number")
	private String reservationNumber;

	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name = "to_reservation_date")
	private Date toReservationDate;

	@Column(name = "to_reservation_time")
	private String toReservationTime;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	public TblMacroReservationManage() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAspCode() {
		return this.aspCode;
	}

	public void setAspCode(String aspCode) {
		this.aspCode = aspCode;
	}

	public String getBatNumber() {
		return this.batNumber;
	}

	public void setBatNumber(String batNumber) {
		this.batNumber = batNumber;
	}

	public Date getFromReservationDate() {
		return this.fromReservationDate;
	}

	public void setFromReservationDate(Date fromReservationDate) {
		this.fromReservationDate = fromReservationDate;
	}

	public String getFromReservationTime() {
		return this.fromReservationTime;
	}

	public void setFromReservationTime(String fromReservationTime) {
		this.fromReservationTime = fromReservationTime;
	}

	public String getMacroName() {
		return this.macroName;
	}

	public void setMacroName(String macroName) {
		this.macroName = macroName;
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

	public String getReservationNumber() {
		return this.reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getToReservationDate() {
		return this.toReservationDate;
	}

	public void setToReservationDate(Date toReservationDate) {
		this.toReservationDate = toReservationDate;
	}

	public String getToReservationTime() {
		return this.toReservationTime;
	}

	public void setToReservationTime(String toReservationTime) {
		this.toReservationTime = toReservationTime;
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