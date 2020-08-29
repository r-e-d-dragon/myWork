package com.enjoygolf24.online.web.json;

import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;

import lombok.Data;

@Data
public class JsonUserResponse extends JsonCommonResponse {

	public MemberReservationManage reservation;

	public MemberReservationManage getData() {
		return reservation;
	}

	public void setData(MemberReservationManage reservation) {
		if (reservation != null) {
			hasData = true;
			hasReservation = !reservation.getReservationList().isEmpty();
			super.setValidated(true);
		}
		this.reservation = reservation;
	}

	public boolean hasData = false;
	public boolean hasReservation = false;
}
