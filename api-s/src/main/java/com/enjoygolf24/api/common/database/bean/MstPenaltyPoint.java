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
 * The persistent class for the mst_penalty_point database table.
 * 
 */
@Entity
@Table(name = "mst_penalty_point")
@NamedQuery(name = "MstPenaltyPoint.findAll", query = "SELECT m FROM MstPenaltyPoint m")
public class MstPenaltyPoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mst_penalty_point_id_seq")
	@SequenceGenerator(name = "mst_penalty_point_id_seq", sequenceName = "mst_penalty_point_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "asp_code")
	private String aspCode;

	@Column(name = "carete_user")
	private String careteUser;

	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "grade_code")
	private String gradeCode;

	@Column(name = "limit_time")
	private Integer limitTime;

	@Column(name = "penalty_point_amount")
	private Long penaltyPointAmount;

	@Column(name = "penalty_point_name")
	private String penaltyPointName;

	@Column(name = "reservation_point_code")
	private String reservationPointCode;

	@Column(name = "upd_seq")
	private Long updSeq;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	@Temporal(TemporalType.DATE)
	@Column(name = "validate_start_term")
	private Date validateStartTerm;

	public MstPenaltyPoint() {
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

	public String getGradeCode() {
		return this.gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public Integer getLimitTime() {
		return this.limitTime;
	}

	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}

	public Long getPenaltyPointAmount() {
		return this.penaltyPointAmount;
	}

	public void setPenaltyPointAmount(Long penaltyPointAmount) {
		this.penaltyPointAmount = penaltyPointAmount;
	}

	public String getPenaltyPointName() {
		return this.penaltyPointName;
	}

	public void setPenaltyPointName(String penaltyPointName) {
		this.penaltyPointName = penaltyPointName;
	}

	public String getReservationPointCode() {
		return this.reservationPointCode;
	}

	public void setReservationPointCode(String reservationPointCode) {
		this.reservationPointCode = reservationPointCode;
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