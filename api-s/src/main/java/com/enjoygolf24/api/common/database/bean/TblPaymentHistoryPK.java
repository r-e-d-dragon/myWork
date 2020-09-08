package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_payment_history database table.
 * 
 */
@Embeddable
public class TblPaymentHistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="member_code")
	private String memberCode;

	@Column(name="valid_month")
	private String validMonth;

	public TblPaymentHistoryPK() {
	}
	public String getMemberCode() {
		return this.memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getValidMonth() {
		return this.validMonth;
	}
	public void setValidMonth(String validMonth) {
		this.validMonth = validMonth;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TblPaymentHistoryPK)) {
			return false;
		}
		TblPaymentHistoryPK castOther = (TblPaymentHistoryPK)other;
		return 
			this.memberCode.equals(castOther.memberCode)
			&& this.validMonth.equals(castOther.validMonth);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.memberCode.hashCode();
		hash = hash * prime + this.validMonth.hashCode();
		
		return hash;
	}
}