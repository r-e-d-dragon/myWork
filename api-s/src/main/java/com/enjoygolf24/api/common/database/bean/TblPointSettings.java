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
@Table(name = "tbl_point_settings")
@NamedQuery(name = "TblPointSettings.findAll", query = "SELECT m FROM TblPointSettings m")
public class TblPointSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_point_settings_seq")
	@SequenceGenerator(name = "mst_reservation_limit_seq", sequenceName = "tbl_point_settings_seq", allocationSize = 1)
	private Long id;

	@Column(name = "default_val")
	private int defaultVal;

	@Column(name = "min_val")
	private int minVal;

	@Column(name = "max_val")
	private int maxVal;

	@Column(name = "val_size")
	private int valSize;

	@Column(name = "carete_user")
	private String careteUser;

	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "auth_type_cd")
	private String authTypeCd;

	private String remarks;

	@Column(name = "upd_seq")
	private Long updSeq;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_user")
	private String updateUser;

	@Temporal(TemporalType.DATE)
	@Column(name = "validate_start_term")
	private Date validateStartTerm;

	public TblPointSettings() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDefaultVal() {
		return this.defaultVal;
	}

	public void setDefaultVal(int defaultVal) {
		this.defaultVal = defaultVal;
	}

	public int getMinVal() {
		return this.minVal;
	}

	public void setMinVal(int minVal) {
		this.minVal = minVal;
	}

	public int getMaxVal() {
		return this.maxVal;
	}

	public void setMaxVal(int maxVal) {
		this.maxVal = maxVal;
	}

	public int getValSize() {
		return this.valSize;
	}

	public void setValSize(int valSize) {
		this.valSize = valSize;
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

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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