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
 * The persistent class for the tbl_reservation database table.
 * 
 */
@Entity
@Table(name = "tbl_reservation")
@NamedQuery(name = "TblReservation.findAll", query = "SELECT t FROM TblReservation t")
public class TblReservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "reservation_id")
	private String reservationId;

	@Column(name = "asp_code")
	private String aspCode;

	@Column(name = "bat_number")
	private String batNumber;

	@Column(name = "consumed_point")
	private Integer consumedPoint;

	@Column(name = "member_code")
	private String memberCode;

	@Column(name = "penalty_point")
	private Integer penaltyPoint;

	@Column(name = "point_category_code")
	private String pointCategoryCode;

	@Column(name = "point_grade")
	private String pointGrade;

	@Column(name = "register_date")
	private Timestamp registerDate;

	@Column(name = "register_user")
	private String registerUser;

	@Temporal(TemporalType.DATE)
	@Column(name = "reservation_date")
	private Date reservationDate;

	@Column(name = "reservation_number")
	private String reservationNumber;

	@Column(name = "reservation_time")
	private String reservationTime;

	private String status;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	public TblReservation() {
	}

	public String getReservationId() {
		return this.reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
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

	public Integer getConsumedPoint() {
		return this.consumedPoint;
	}

	public void setConsumedPoint(Integer consumedPoint) {
		this.consumedPoint = consumedPoint;
	}

	public String getMemberCode() {
		return this.memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public Timestamp getRegisterDate() {
		return this.registerDate;
	}

	public Integer getPenaltyPoint() {
		return this.penaltyPoint;
	}

	public void setPenaltyPoint(Integer penaltyPoint) {
		this.penaltyPoint = penaltyPoint;
	}

	public String getPointCategoryCode() {
		return this.pointCategoryCode;
	}

	public void setPointCategoryCode(String pointCategoryCode) {
		this.pointCategoryCode = pointCategoryCode;
	}

	public String getPointGrade() {
		return this.pointGrade;
	}

	public void setPointGrade(String pointGrade) {
		this.pointGrade = pointGrade;
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

	public Date getReservationDate() {
		return this.reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getReservationNumber() {
		return this.reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public String getReservationTime() {
		return this.reservationTime;
	}

	public void setReservationTime(String reservationTime) {
		this.reservationTime = reservationTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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