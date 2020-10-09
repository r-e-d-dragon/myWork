package com.enjoygolf24.online.web.json;

import java.util.List;

import com.enjoygolf24.api.common.database.bean.TblAsp;

import lombok.Data;

@Data
public class JsonAspResponse extends JsonCommonResponse {

	public List<TblAsp> aspList;

	public List<TblAsp> getData() {
		return aspList;
	}

	public void setData(List<TblAsp> pList) {

		if (pList != null && !pList.isEmpty()) {
			hasData = true;
			super.setValidated(true);
		}
		this.aspList = pList;

	}

	public boolean hasData = false;
}
