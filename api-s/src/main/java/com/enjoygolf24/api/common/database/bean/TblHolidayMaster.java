package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_holiday_master database table.
 * 
 */
@Entity
@Table(name="tbl_holiday_master")
@NamedQuery(name="TblHolidayMaster.findAll", query="SELECT t FROM TblHolidayMaster t")
public class TblHolidayMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name="holiday_date")
	private Date holidayDate;

	@Column(name="holiday_name")
	private String holidayName;

	public TblHolidayMaster() {
	}

	public Date getHolidayDate() {
		return this.holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getHolidayName() {
		return this.holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

}