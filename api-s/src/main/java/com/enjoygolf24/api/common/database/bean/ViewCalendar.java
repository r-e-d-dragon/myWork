package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
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
 * The persistent class for the view_calendar database table.
 * 
 */
@Entity
@Immutable
@Table(name = "view_calendar")
@NamedQuery(name = "ViewCalendar.findAll", query = "SELECT v FROM ViewCalendar v")
public class ViewCalendar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name = "date_time")
	private Date dateTime;

	@Column(name = "date_type_cd")
	private String dateTypeCd;

	@Column(name = "holyday_type_cd")
	private String holydayTypeCd;

	@Column(name = "week_type_cd")
	private double weekTypeCd;

	public ViewCalendar() {
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

	public String getHolydayTypeCd() {
		return this.holydayTypeCd;
	}

	public void setHolydayTypeCd(String holydayTypeCd) {
		this.holydayTypeCd = holydayTypeCd;
	}

	public double getWeekTypeCd() {
		return this.weekTypeCd;
	}

	public void setWeekTypeCd(double weekTypeCd) {
		this.weekTypeCd = weekTypeCd;
	}

}