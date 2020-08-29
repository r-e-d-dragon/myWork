package com.enjoygolf24.api.common.database.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the tbl_asp database table.
 * 
 */
@Entity
@Table(name="tbl_asp")
@NamedQuery(name="TblAsp.findAll", query="SELECT t FROM TblAsp t")
public class TblAsp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="asp_code")
	private String aspCode;

	private String address1;

	private String address2;

	@Column(name="asp_email")
	private String aspEmail;

	@Column(name="asp_name")
	private String aspName;

	@Column(name="availability_cd")
	private String availabilityCd;

	@Temporal(TemporalType.DATE)
	@Column(name="close_end_time")
	private Date closeEndTime;

	@Column(name="create_date")
	private Timestamp createDate;

	@Column(name="create_user")
	private String createUser;

	@Column(name="fax_number")
	private String faxNumber;

	@Column(name="holiday_type_cd")
	private String holidayTypeCd;

	@Column(name="image_path")
	private String imagePath;

	private String line1;

	@Column(name="line1_station_info")
	private String line1StationInfo;

	@Column(name="line1_station_name")
	private String line1StationName;

	private String line2;

	@Column(name="line2_statiion_name")
	private String line2StatiionName;

	@Column(name="line2_station_info")
	private String line2StationInfo;

	@Column(name="manager_name")
	private String managerName;

	@Temporal(TemporalType.DATE)
	@Column(name="open_start_time")
	private Date openStartTime;

	@Column(name="open_type_cd")
	private String openTypeCd;

	@Column(name="parking_provide_yn")
	private String parkingProvideYn;

	@Column(name="phone_number")
	private String phoneNumber;

	private String remarks;

	@Column(name="shop_type_cd")
	private String shopTypeCd;

	@Column(name="update_date")
	private Timestamp updateDate;

	@Column(name="update_seq")
	private Integer updateSeq;

	@Column(name="update_user")
	private String updateUser;

	private String zip1;

	private String zip2;

	public TblAsp() {
	}

	public String getAspCode() {
		return this.aspCode;
	}

	public void setAspCode(String aspCode) {
		this.aspCode = aspCode;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAspEmail() {
		return this.aspEmail;
	}

	public void setAspEmail(String aspEmail) {
		this.aspEmail = aspEmail;
	}

	public String getAspName() {
		return this.aspName;
	}

	public void setAspName(String aspName) {
		this.aspName = aspName;
	}

	public String getAvailabilityCd() {
		return this.availabilityCd;
	}

	public void setAvailabilityCd(String availabilityCd) {
		this.availabilityCd = availabilityCd;
	}

	public Date getCloseEndTime() {
		return this.closeEndTime;
	}

	public void setCloseEndTime(Date closeEndTime) {
		this.closeEndTime = closeEndTime;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getHolidayTypeCd() {
		return this.holidayTypeCd;
	}

	public void setHolidayTypeCd(String holidayTypeCd) {
		this.holidayTypeCd = holidayTypeCd;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLine1() {
		return this.line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine1StationInfo() {
		return this.line1StationInfo;
	}

	public void setLine1StationInfo(String line1StationInfo) {
		this.line1StationInfo = line1StationInfo;
	}

	public String getLine1StationName() {
		return this.line1StationName;
	}

	public void setLine1StationName(String line1StationName) {
		this.line1StationName = line1StationName;
	}

	public String getLine2() {
		return this.line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine2StatiionName() {
		return this.line2StatiionName;
	}

	public void setLine2StatiionName(String line2StatiionName) {
		this.line2StatiionName = line2StatiionName;
	}

	public String getLine2StationInfo() {
		return this.line2StationInfo;
	}

	public void setLine2StationInfo(String line2StationInfo) {
		this.line2StationInfo = line2StationInfo;
	}

	public String getManagerName() {
		return this.managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Date getOpenStartTime() {
		return this.openStartTime;
	}

	public void setOpenStartTime(Date openStartTime) {
		this.openStartTime = openStartTime;
	}

	public String getOpenTypeCd() {
		return this.openTypeCd;
	}

	public void setOpenTypeCd(String openTypeCd) {
		this.openTypeCd = openTypeCd;
	}

	public String getParkingProvideYn() {
		return this.parkingProvideYn;
	}

	public void setParkingProvideYn(String parkingProvideYn) {
		this.parkingProvideYn = parkingProvideYn;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getShopTypeCd() {
		return this.shopTypeCd;
	}

	public void setShopTypeCd(String shopTypeCd) {
		this.shopTypeCd = shopTypeCd;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getUpdateSeq() {
		return this.updateSeq;
	}

	public void setUpdateSeq(Integer updateSeq) {
		this.updateSeq = updateSeq;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getZip1() {
		return this.zip1;
	}

	public void setZip1(String zip1) {
		this.zip1 = zip1;
	}

	public String getZip2() {
		return this.zip2;
	}

	public void setZip2(String zip2) {
		this.zip2 = zip2;
	}

}