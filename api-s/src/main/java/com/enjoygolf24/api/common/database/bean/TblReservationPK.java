package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_reservation database table.
 * 
 */
@Embeddable
public class TblReservationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="reservation_number")
	private String reservationNumber;

	private String status;

	public TblReservationPK() {
	}
	public String getReservationNumber() {
		return this.reservationNumber;
	}
	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TblReservationPK)) {
			return false;
		}
		TblReservationPK castOther = (TblReservationPK)other;
		return 
			this.reservationNumber.equals(castOther.reservationNumber)
			&& this.status.equals(castOther.status);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.reservationNumber.hashCode();
		hash = hash * prime + this.status.hashCode();
		
		return hash;
	}
}