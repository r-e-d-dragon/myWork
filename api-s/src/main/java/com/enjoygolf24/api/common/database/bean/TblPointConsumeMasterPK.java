package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the tbl_point_consume_master database table.
 * 
 */
@Embeddable
public class TblPointConsumeMasterPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "date_kind")
	private String dateKind;

	@Column(name = "time_slot")
	private String timeSlot;

	public TblPointConsumeMasterPK() {
	}

	public String getDateKind() {
		return this.dateKind;
	}

	public void setDateKind(String dateKind) {
		this.dateKind = dateKind;
	}

	public String getTimeSlot() {
		return this.timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TblPointConsumeMasterPK)) {
			return false;
		}
		TblPointConsumeMasterPK castOther = (TblPointConsumeMasterPK) other;
		return this.dateKind.equals(castOther.dateKind) && this.timeSlot.equals(castOther.timeSlot);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dateKind.hashCode();
		hash = hash * prime + this.timeSlot.hashCode();

		return hash;
	}
}