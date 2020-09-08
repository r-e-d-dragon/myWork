package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the tbl_point_manage database table.
 * 
 */
@Entity
@Table(name = "tbl_point_manage")
@NamedQuery(name = "TblPointManage.findAll", query = "SELECT t FROM TblPointManage t")
public class TblPointManage implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TblPointManagePK id;

	@Column(name = "category_code")
	private String categoryCode;

	@Column(name = "consumed_point")
	private Integer consumedPoint;

	@Column(name = "end_date")
	private Timestamp endDate;

	@Column(name = "point_amount")
	private Integer pointAmount;

	@Column(name = "point_type")
	private String pointType;

	@Column(name = "register_date")
	private Timestamp registerDate;

	@Column(name = "register_user")
	private String registerUser;

	@Column(name = "reservation_number")
	private String reservationNumber;

	@Column(name = "start_date")
	private Timestamp startDate;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	public TblPointManage() {
	}

	public TblPointManagePK getId() {
		return this.id;
	}

	public void setId(TblPointManagePK id) {
		this.id = id;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryCode() {
		return this.categoryCode;
	}

	public Integer getConsumedPoint() {
		return this.consumedPoint;
	}

	public void setConsumedPoint(Integer consumedPoint) {
		this.consumedPoint = consumedPoint;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Integer getPointAmount() {
		return this.pointAmount;
	}

	public void setPointAmount(Integer pointAmount) {
		this.pointAmount = pointAmount;
	}

	public String getPointType() {
		return this.pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
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

	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
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