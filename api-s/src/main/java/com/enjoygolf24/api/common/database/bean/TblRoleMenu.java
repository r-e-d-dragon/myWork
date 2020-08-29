package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbl_role_menu database table.
 * 
 */
@Entity
@Table(name="tbl_role_menu")
@NamedQuery(name="TblRoleMenu.findAll", query="SELECT t FROM TblRoleMenu t")
public class TblRoleMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TblRoleMenuPK id;

	@Column(name="availability_cd")
	private String availabilityCd;

	@Column(name="register_date")
	private Timestamp registerDate;

	@Column(name="register_user")
	private String registerUser;

	@Column(name="upd_seq")
	private Long updSeq;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="update_user")
	private String updateUser;

	public TblRoleMenu() {
	}

	public TblRoleMenuPK getId() {
		return this.id;
	}

	public void setId(TblRoleMenuPK id) {
		this.id = id;
	}

	public String getAvailabilityCd() {
		return this.availabilityCd;
	}

	public void setAvailabilityCd(String availabilityCd) {
		this.availabilityCd = availabilityCd;
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

}