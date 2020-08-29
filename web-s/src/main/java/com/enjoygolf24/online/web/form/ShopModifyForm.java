package com.enjoygolf24.online.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.enjoygolf24.api.common.code.AvailabilityCd;
import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.OpenTypeCd;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.validator.annotation.CodeMaster;
import com.enjoygolf24.api.common.validator.annotation.Email;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.Kanji;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;
import com.enjoygolf24.api.common.validator.annotation.Telephone;
import com.enjoygolf24.api.common.validator.annotation.UniqueEmail;
import com.enjoygolf24.api.common.validator.annotation.Zenkaku;
import com.enjoygolf24.api.common.validator.annotation.Zip1;
import com.enjoygolf24.api.common.validator.annotation.Zip2;
import com.enjoygolf24.api.common.validator.groups.Insert1;
import com.enjoygolf24.api.common.validator.groups.Update0;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class ShopModifyForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@Hankaku(groups = Update0.class, max = 20)
	private String aspCode;

	@NotBlank(groups = Update0.class)
	@Zenkaku(groups = Update0.class, max = 200)
	@Kanji(groups = Update0.class, max = 200)
	@SjisSafe(groups = Update0.class)
	private String aspName;

	@NotBlank(groups = Update0.class)
	@Zenkaku(groups = Update0.class, max = 200)
	@Kanji(groups = Update0.class, max = 200)
	@SjisSafe(groups = Update0.class)
	private String managerName;

	@NotBlank(groups = Update0.class)
	@Size(groups = Update0.class, max = 3)
	@Zip1(groups = Update0.class)
	private String zip1;

	@NotBlank(groups = Update0.class)
	@Size(groups = Update0.class, max = 4)
	@Zip2(groups = Update0.class)
	private String zip2;

	@NotBlank(groups = Update0.class)
	@Kanji(groups = Update0.class, max = 200)
	@SjisSafe(groups = Update0.class)
	private String address1;

	@Kanji(groups = Update0.class, max = 200)
	@SjisSafe(groups = Update0.class)
	private String address2;

	@NotBlank(groups = Update0.class)
	@Telephone(groups = Update0.class)
	private String phone;

	@Telephone(groups = Update0.class)
	private String fax;

	@NotBlank(groups = Update0.class)
	@Zenkaku(groups = Update0.class, max = 100)
	@SjisSafe(groups = Update0.class)
	private String line1;

	@NotBlank(groups = Update0.class)
	@Zenkaku(groups = Update0.class, max = 100)
	@SjisSafe(groups = Update0.class)
	private String line1StationInfo;

	@NotBlank(groups = Update0.class)
	@Zenkaku(groups = Update0.class, max = 100)
	@SjisSafe(groups = Update0.class)
	private String line1StationName;

	@Zenkaku(groups = Update0.class, max = 100)
	@SjisSafe(groups = Update0.class)
	private String line2;

	@Zenkaku(groups = Update0.class, max = 100)
	@SjisSafe(groups = Update0.class)
	private String line2StatiionName;

	@Zenkaku(groups = Update0.class, max = 100)
	@SjisSafe(groups = Update0.class)
	private String line2StationInfo;

	@NotBlank(groups = Update0.class)
	@Size(groups = Update0.class, max = 80)
	@Email(groups = Update0.class)
	@UniqueEmail(groups = Insert1.class)
	private String email;

	@NotBlank(groups = Update0.class)
	@Size(groups = Update0.class, max = 2)
	@CodeMaster(groups = Update0.class, code = CodeTypeCd.YESNO_TYPE_CD)
	private String parkingProvideYn;

	private Integer updateSeq;

	public TblAsp converToBean() {
		TblAsp bean = new TblAsp();
		bean.setAspCode(this.aspCode);
		bean.setAddress1(this.address1);
		bean.setAddress2(this.address2);
		bean.setAspEmail(this.email);
		bean.setAspName(this.aspName);
		bean.setAvailabilityCd(AvailabilityCd.AVAILABLE);
		bean.setFaxNumber(this.fax);

		bean.setHolidayTypeCd("01");

		// bean.setImagePath(imagePath);
		bean.setLine1(this.line1);
		bean.setLine1StationInfo(this.line1StationInfo);
		bean.setLine1StationName(this.line1StationName);
		bean.setLine2(this.line2);
		bean.setLine2StatiionName(this.line2StatiionName);
		bean.setLine2StationInfo(this.line2StationInfo);
		bean.setManagerName(this.managerName);

		// bean.setOpenStartTime(openStartTime);
		bean.setOpenTypeCd(OpenTypeCd.NOT_OPEN);
		bean.setParkingProvideYn(this.parkingProvideYn);
		bean.setPhoneNumber(this.phone);
		// bean.setRemarks(remarks);
		bean.setZip1(this.zip1);
		bean.setZip2(this.zip2);
		bean.setUpdateSeq(this.updateSeq);
		return bean;
	}

	public void converToForm(TblAsp tblAsp) {
		this.aspCode = tblAsp.getAspCode();
		this.setAddress1(tblAsp.getAddress1());
		this.setAddress2(tblAsp.getAddress2());
		this.setEmail(tblAsp.getAspEmail());
		this.setAspName(tblAsp.getAspName());
		// this.setAvailabilityCd(AvailabilityCd.AVAILABLE);
		this.setFax(tblAsp.getFaxNumber());

		// this.setHolidayTypeCd("01");

		// bean.setImagePath(imagePath);
		this.setLine1(tblAsp.getLine1());
		this.setLine1StationInfo(tblAsp.getLine1StationInfo());
		this.setLine1StationName(tblAsp.getLine1StationName());
		this.setLine2(tblAsp.getLine2());
		this.setLine2StatiionName(tblAsp.getLine2StatiionName());
		this.setLine2StationInfo(tblAsp.getLine2StationInfo());
		this.setManagerName(tblAsp.getManagerName());

		// bean.setOpenStartTime(openStartTime);
		// this.setOpenTypeCd(OpenTypeCd.NOT_OPEN);
		this.setParkingProvideYn(tblAsp.getParkingProvideYn());
		this.setPhone(tblAsp.getPhoneNumber());
		// bean.setRemarks(remarks);
		this.setZip1(tblAsp.getZip1());
		this.setZip2(tblAsp.getZip2());
		this.updateSeq = tblAsp.getUpdateSeq();
	}

}
