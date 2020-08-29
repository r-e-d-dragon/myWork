package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
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
 * The persistent class for the tbl_auth_key_manage database table.
 * 
 */
@Entity
@Table(name = "tbl_auth_key_manage")
@NamedQuery(name = "TblAuthKeyManage.findAll", query = "SELECT t FROM TblAuthKeyManage t")
public class TblAuthKeyManage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_auth_key_manage_id_seq")
	@SequenceGenerator(name = "tbl_auth_key_manage_id_seq", sequenceName = "tbl_auth_key_manage_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "auth_key")
	private String authKey;

	@Column(name = "auth_status_cd")
	private String authStatusCd;

	@Column(name = "member_code")
	private String memberCode;

	@Column(name = "processing_cd")
	private String processingCd;

	@Column(name = "register_date")
	private Timestamp registerDate;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_seq")
	private Integer updateSeq;

	public TblAuthKeyManage() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthKey() {
		return this.authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getAuthStatusCd() {
		return this.authStatusCd;
	}

	public void setAuthStatusCd(String authStatusCd) {
		this.authStatusCd = authStatusCd;
	}

	public String getMemberCode() {
		return this.memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getProcessingCd() {
		return this.processingCd;
	}

	public void setProcessingCd(String processingCd) {
		this.processingCd = processingCd;
	}

	public Timestamp getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getUpdateSeq() {
		return this.updateSeq;
	}

	public void setUpdateSeq(Integer updateSeq) {
		this.updateSeq = updateSeq;
	}

}