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
 * The persistent class for the mst_reservation_limit database table.
 * 
 */
@Entity
@Table(name = "mst_reservation_limit")
@NamedQuery(name = "MstReservationLimit.findAll", query = "SELECT m FROM MstReservationLimit m")
public class MstReservationLimit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mst_reservation_limit_seq")
	@SequenceGenerator(name = "mst_reservation_limit_seq", sequenceName = "mst_reservation_limit_seq", allocationSize = 1)
	private Long id;

	@Column(name = "carete_user")
	private String careteUser;

	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "event_limit")
	private Integer eventLimit;

	@Column(name = "max_reservation_point")
	private Integer maxReservationPoint;

	@Column(name = "member_type_cd")
	private String memberTypeCd;

	@Column(name = "monthly_limit")
	private Integer monthlyLimit;

	private String remarks;

	@Column(name = "reservation_limit")
	private Integer reservationLimit;

	@Column(name = "upd_seq")
	private Long updSeq;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	@Temporal(TemporalType.DATE)
	@Column(name = "validate_start_term")
	private Date validateStartTerm;

	public MstReservationLimit() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getEventLimit() {
		return this.eventLimit;
	}

	public void setEventLimit(Integer eventLimit) {
		this.eventLimit = eventLimit;
	}

	public Integer getMaxReservationPoint() {
		return this.maxReservationPoint;
	}

	public void setMaxReservationPoint(Integer maxReservationPoint) {
		this.maxReservationPoint = maxReservationPoint;
	}

	public String getMemberTypeCd() {
		return this.memberTypeCd;
	}

	public void setMemberTypeCd(String memberTypeCd) {
		this.memberTypeCd = memberTypeCd;
	}

	public Integer getMonthlyLimit() {
		return this.monthlyLimit;
	}

	public void setMonthlyLimit(Integer monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
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

	public Long getUpdSeq() {
		return this.updSeq;
	}

	public void setUpdSeq(Long updSeq) {
		this.updSeq = updSeq;
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

	public Date getValidateStartTerm() {
		return this.validateStartTerm;
	}

	public void setValidateStartTerm(Date validateStartTerm) {
		this.validateStartTerm = validateStartTerm;
	}

}