package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

/**
 * The persistent class for the view_reservation_point_time_table database
 * table.
 * 
 */
@Entity
@Immutable
@Table(name = "view_reservation_point_time_table")
@NamedQuery(name = "ViewReservationPointTimeTable.findAll", query = "SELECT v FROM ViewReservationPointTimeTable v")
public class ViewReservationPointTimeTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name = "date_time")
	private Date dateTime;

	@Column(name = "date_type_cd")
	private String dateTypeCd;

	@Column(name = "end_time")
	private Time endTime;

	@Column(name = "grade_code")
	private String gradeCode;

	@Column(name = "grade_name")
	private String gradeName;

	@Column(name = "grade_type_cd")
	private String gradeTypeCd;

	@Column(name = "holyday_type_cd")
	private String holydayTypeCd;

	@Column(name = "point_amount")
	private Long pointAmount;

	@Column(name = "reservation_point_code")
	private String reservationPointCode;

	@Column(name = "reservation_point_name")
	private String reservationPointName;

	@Column(name = "start_time")
	private Time startTime;

	@Column(name = "time_table_code")
	private Long timeTableCode;

	@Column(name = "time_table_name")
	private String timeTableName;

	@Temporal(TemporalType.DATE)
	@Column(name = "validate_start_term")
	private Date validateStartTerm;

	@Column(name = "week_type_cd")
	private double weekTypeCd;

	public ViewReservationPointTimeTable() {
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getDateTypeCd() {
		return this.dateTypeCd;
	}

	public void setDateTypeCd(String dateTypeCd) {
		this.dateTypeCd = dateTypeCd;
	}

	public Time getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public String getGradeCode() {
		return this.gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getGradeTypeCd() {
		return this.gradeTypeCd;
	}

	public void setGradeTypeCd(String gradeTypeCd) {
		this.gradeTypeCd = gradeTypeCd;
	}

	public String getHolydayTypeCd() {
		return this.holydayTypeCd;
	}

	public void setHolydayTypeCd(String holydayTypeCd) {
		this.holydayTypeCd = holydayTypeCd;
	}

	public Long getPointAmount() {
		return this.pointAmount;
	}

	public void setPointAmount(Long pointAmount) {
		this.pointAmount = pointAmount;
	}

	public String getReservationPointCode() {
		return this.reservationPointCode;
	}

	public void setReservationPointCode(String reservationPointCode) {
		this.reservationPointCode = reservationPointCode;
	}

	public String getReservationPointName() {
		return this.reservationPointName;
	}

	public void setReservationPointName(String reservationPointName) {
		this.reservationPointName = reservationPointName;
	}

	public Time getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Long getTimeTableCode() {
		return this.timeTableCode;
	}

	public void setTimeTableCode(Long timeTableCode) {
		this.timeTableCode = timeTableCode;
	}

	public String getTimeTableName() {
		return this.timeTableName;
	}

	public void setTimeTableName(String timeTableName) {
		this.timeTableName = timeTableName;
	}

	public Date getValidateStartTerm() {
		return this.validateStartTerm;
	}

	public void setValidateStartTerm(Date validateStartTerm) {
		this.validateStartTerm = validateStartTerm;
	}

	public double getWeekTypeCd() {
		return this.weekTypeCd;
	}

	public void setWeekTypeCd(double weekTypeCd) {
		this.weekTypeCd = weekTypeCd;
	}

}