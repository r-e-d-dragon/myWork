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
 * The persistent class for the zip_master database table.
 * 
 */
@Entity
@Table(name = "zip_master")
@NamedQuery(name = "ZipMaster.findAll", query = "SELECT z FROM ZipMaster z")
public class ZipMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zip_master_id_seq")
	@SequenceGenerator(name = "zip_master_id_seq", sequenceName = "zip_master_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "city_kanji")
	private String cityKanji;

	@Column(name = "prefecture_cd")
	private String prefectureCd;

	@Column(name = "prefecture_kanji")
	private String prefectureKanji;

	@Column(name = "register_datetime")
	private Timestamp registerDatetime;

	@Column(name = "street_kanji")
	private String streetKanji;

	@Column(name = "update_datetime")
	private Timestamp updateDatetime;

	@Column(name = "update_seq")
	private Integer updateSeq;

	@Column(name = "zip_code")
	private String zipCode;

	public ZipMaster() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityKanji() {
		return this.cityKanji;
	}

	public void setCityKanji(String cityKanji) {
		this.cityKanji = cityKanji;
	}

	public String getPrefectureCd() {
		return this.prefectureCd;
	}

	public void setPrefectureCd(String prefectureCd) {
		this.prefectureCd = prefectureCd;
	}

	public String getPrefectureKanji() {
		return this.prefectureKanji;
	}

	public void setPrefectureKanji(String prefectureKanji) {
		this.prefectureKanji = prefectureKanji;
	}

	public Timestamp getRegisterDatetime() {
		return this.registerDatetime;
	}

	public void setRegisterDatetime(Timestamp registerDatetime) {
		this.registerDatetime = registerDatetime;
	}

	public String getStreetKanji() {
		return this.streetKanji;
	}

	public void setStreetKanji(String streetKanji) {
		this.streetKanji = streetKanji;
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

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}