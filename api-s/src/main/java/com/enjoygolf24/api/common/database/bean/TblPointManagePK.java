package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_point_manage database table.
 * 
 */
@Embeddable
public class TblPointManagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long id;

	@Column(name="member_code")
	private String memberCode;

	public TblPointManagePK() {
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMemberCode() {
		return this.memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TblPointManagePK)) {
			return false;
		}
		TblPointManagePK castOther = (TblPointManagePK)other;
		return 
			this.id.equals(castOther.id)
			&& this.memberCode.equals(castOther.memberCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.memberCode.hashCode();
		
		return hash;
	}
}