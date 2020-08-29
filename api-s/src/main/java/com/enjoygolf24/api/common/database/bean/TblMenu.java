package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbl_menu database table.
 * 
 */
@Entity
@Table(name="tbl_menu")
@NamedQuery(name="TblMenu.findAll", query="SELECT t FROM TblMenu t")
public class TblMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="memu_id")
	private String memuId;

	@Column(name="active_cd")
	private String activeCd;

	@Column(name="availability_cd")
	private String availabilityCd;

	private String caption;

	private String description;

	@Column(name="link_type")
	private String linkType;

	@Column(name="order_no")
	private Long orderNo;

	@Column(name="parent_id")
	private String parentId;

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

	private String url;

	@Column(name="user_type_cd")
	private String userTypeCd;

	public TblMenu() {
	}

	public String getMemuId() {
		return this.memuId;
	}

	public void setMemuId(String memuId) {
		this.memuId = memuId;
	}

	public String getActiveCd() {
		return this.activeCd;
	}

	public void setActiveCd(String activeCd) {
		this.activeCd = activeCd;
	}

	public String getAvailabilityCd() {
		return this.availabilityCd;
	}

	public void setAvailabilityCd(String availabilityCd) {
		this.availabilityCd = availabilityCd;
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinkType() {
		return this.linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public Long getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserTypeCd() {
		return this.userTypeCd;
	}

	public void setUserTypeCd(String userTypeCd) {
		this.userTypeCd = userTypeCd;
	}

}