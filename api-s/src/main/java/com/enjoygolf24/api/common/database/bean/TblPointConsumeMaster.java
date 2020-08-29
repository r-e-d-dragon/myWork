package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the tbl_point_consume_master database table.
 * 
 */
@Entity
@Table(name = "tbl_point_consume_master")
@NamedQuery(name = "TblPointConsumeMaster.findAll", query = "SELECT t FROM TblPointConsumeMaster t")
public class TblPointConsumeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TblPointConsumeMasterPK id;

	@Column(name = "consume_point")
	private Integer consumePoint;

	@Column(name = "time_slot_name")
	private String timeSlotName;

	public TblPointConsumeMaster() {
	}

	public TblPointConsumeMasterPK getId() {
		return this.id;
	}

	public void setId(TblPointConsumeMasterPK id) {
		this.id = id;
	}

	public Integer getConsumePoint() {
		return this.consumePoint;
	}

	public void setConsumePoint(Integer consumePoint) {
		this.consumePoint = consumePoint;
	}

	public String getTimeSlotName() {
		return this.timeSlotName;
	}

	public void setTimeSlotName(String timeSlotName) {
		this.timeSlotName = timeSlotName;
	}

}