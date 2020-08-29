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
 * The persistent class for the tbl_point_history database table.
 * 
 */
@Entity
@Table(name = "tbl_point_history")
@NamedQuery(name = "TblPointHistory.findAll", query = "SELECT t FROM TblPointHistory t")
public class TblPointHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_point_history_point_history_id_seq")
	@SequenceGenerator(name = "tbl_point_history_point_history_id_seq", sequenceName = "tbl_point_history_point_history_id_seq", allocationSize = 1)
	private Long pointHistoryId;

	@Column(name = "consumed_point")
	private Integer consumedPoint;

	@Temporal(TemporalType.DATE)
	@Column(name = "expire_date")
	private Date expireDate;

	@Column(name = "member_code")
	private String memberCode;

	@Column(name = "point_code")
	private String pointCode;

	@Column(name = "register_date")
	private Timestamp registerDate;

	@Column(name = "register_user")
	private String registerUser;

	@Column(name = "reservation_id")
	private String reservationId;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	public TblPointHistory() {
	}

	public Long getPointHistoryId() {
		return this.pointHistoryId;
	}

	public void setPointHistoryId(Long pointHistoryId) {
		this.pointHistoryId = pointHistoryId;
	}

	public Integer getConsumedPoint() {
		return this.consumedPoint;
	}

	public void setConsumedPoint(Integer consumedPoint) {
		this.consumedPoint = consumedPoint;
	}

	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getMemberCode() {
		return this.memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getPointCode() {
		return this.pointCode;
	}

	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
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

	public String getReservationId() {
		return this.reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
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