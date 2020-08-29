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
 * The persistent class for the code_master database table.
 * 
 */
@Entity
@Table(name = "code_master")
@NamedQuery(name = "CodeMaster.findAll", query = "SELECT c FROM CodeMaster c")
public class CodeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "code_master_id_seq")
	@SequenceGenerator(name = "code_master_id_seq", sequenceName = "code_master_id_seq", allocationSize = 1)
	private Long id;

	private String cd;

	@Column(name = "code_type")
	private String codeType;

	private String name;

	@Column(name = "register_date")
	private Timestamp registerDate;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "update_seq")
	private Integer updateSeq;

	public CodeMaster() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCd() {
		return this.cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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