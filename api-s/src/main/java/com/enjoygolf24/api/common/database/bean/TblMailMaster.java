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
 * The persistent class for the tbl_mail_master database table.
 * 
 */
@Entity
@Table(name = "tbl_mail_master")
@NamedQuery(name = "TblMailMaster.findAll", query = "SELECT t FROM TblMailMaster t")
public class TblMailMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_mail_master_id_seq")
	@SequenceGenerator(name = "tbl_mail_master_id_seq", sequenceName = "tbl_mail_master_id_seq", allocationSize = 1)
	private Long id;

	private String body;

	@Column(name = "mail_section_cd")
	private String mailSectionCd;

	@Column(name = "register_datetime")
	private Timestamp registerDatetime;

	@Column(name = "sender_mail_address")
	private String senderMailAddress;

	@Column(name = "sender_name")
	private String senderName;

	private String subject;

	@Column(name = "update_datetime")
	private Timestamp updateDatetime;

	@Column(name = "update_seq")
	private Integer updateSeq;

	public TblMailMaster() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getMailSectionCd() {
		return this.mailSectionCd;
	}

	public void setMailSectionCd(String mailSectionCd) {
		this.mailSectionCd = mailSectionCd;
	}

	public Timestamp getRegisterDatetime() {
		return this.registerDatetime;
	}

	public void setRegisterDatetime(Timestamp registerDatetime) {
		this.registerDatetime = registerDatetime;
	}

	public String getSenderMailAddress() {
		return this.senderMailAddress;
	}

	public void setSenderMailAddress(String senderMailAddress) {
		this.senderMailAddress = senderMailAddress;
	}

	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Timestamp getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public Integer getUpdateSeq() {
		return this.updateSeq;
	}

	public void setUpdateSeq(Integer updateSeq) {
		this.updateSeq = updateSeq;
	}

}