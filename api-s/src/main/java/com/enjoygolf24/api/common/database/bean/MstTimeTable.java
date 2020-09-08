package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the mst_time_table database table.
 * 
 */
@Entity
@Table(name = "mst_time_table")
@NamedQuery(name = "MstTimeTable.findAll", query = "SELECT m FROM MstTimeTable m")
public class MstTimeTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mst_time_table_id_seq")
	@SequenceGenerator(name = "mst_time_table_id_seq", sequenceName = "mst_time_table_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "asp_code")
	private String aspCode;

	@Column(name = "carete_date")
	private Timestamp careteDate;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "day_type_cd")
	private String dayTypeCd;

	@Column(name = "end_time")
	private Time endTime;

	@Column(name = "start_time")
	private Time startTime;

	@Column(name = "time_table_code")
	private Long timeTableCode;

	@Column(name = "time_table_name")
	private String timeTableName;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	public MstTimeTable() {
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

	public Timestamp getCareteDate() {
		return this.careteDate;
	}

	public void setCareteDate(Timestamp careteDate) {
		this.careteDate = careteDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDayTypeCd() {
		return this.dayTypeCd;
	}

	public void setDayTypeCd(String dayTypeCd) {
		this.dayTypeCd = dayTypeCd;
	}

	public Time getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
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