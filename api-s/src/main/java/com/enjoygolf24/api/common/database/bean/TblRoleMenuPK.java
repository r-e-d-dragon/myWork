package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbl_role_menu database table.
 * 
 */
@Embeddable
public class TblRoleMenuPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="menu_id")
	private String menuId;

	@Column(name="role_id")
	private String roleId;

	public TblRoleMenuPK() {
	}
	public String getMenuId() {
		return this.menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getRoleId() {
		return this.roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TblRoleMenuPK)) {
			return false;
		}
		TblRoleMenuPK castOther = (TblRoleMenuPK)other;
		return 
			this.menuId.equals(castOther.menuId)
			&& this.roleId.equals(castOther.roleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.menuId.hashCode();
		hash = hash * prime + this.roleId.hashCode();
		
		return hash;
	}
}