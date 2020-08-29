package com.enjoygolf24.online.web.json;

import java.util.List;

import com.enjoygolf24.api.common.database.bean.ZipMaster;

import lombok.Data;

@Data
public class JsonAddressResponse extends JsonCommonResponse {

	public List<ZipMaster> zipList;

	public List<ZipMaster> getData() {
		return zipList;
	}

	public void setData(List<ZipMaster> pList) {

		if (pList != null && !pList.isEmpty()) {
			hasData = true;
			hasOnlyOne = pList.size() == 1;
			super.setValidated(true);
		}
		this.zipList = pList;

	}

	public boolean hasData = false;

	public boolean hasOnlyOne = false;
}
