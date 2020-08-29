package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the tbl_reservation_limit_master database table.
 * 
 */
@Embeddable
public class TblReservationLimitMasterPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "member_grade")
	private String memberGrade;

	@Column(name = "grade_code")
	private String gradeCode;

	public TblReservationLimitMasterPK() {
	}

	public String getMemberGrade() {
		return this.memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

	public String getGradeCode() {
		return this.gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TblReservationLimitMasterPK)) {
			return false;
		}
		TblReservationLimitMasterPK castOther = (TblReservationLimitMasterPK) other;
		return this.memberGrade.equals(castOther.memberGrade) && this.gradeCode.equals(castOther.gradeCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.memberGrade.hashCode();
		hash = hash * prime + this.gradeCode.hashCode();

		return hash;
	}
}