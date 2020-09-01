
package com.enjoygolf24.api.common.database.mybatis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;

@Mapper
public interface ReservationMapper {
	public List<MemberReservationManage> getMemberReservationList(@Param("reservationNumber") String reservationNumber,
			@Param("memberCode") String memberCode, @Param("aspCode") String aspCode,
			@Param("batNumber") String batNumber, @Param("reservationDate") String reservationDate,
			@Param("reservationTime") String reservationTime, @Param("status") String status,
			@Param("valide") boolean valide);

	public List<MemberReservationManage> getReservationList(@Param("aspCode") String aspCode,
			@Param("reservationDate") String reservationDate, @Param("dateKind") String dateKind);

	public List<MemberReservationManage> getMemberPointManageList(@Param("memberCode") String memberCode,
			@Param("categoryCode") String categoryCode, @Param("reservationDate") String reservationDate);

	public List<MemberReservationManage> getMacroReservationList(@Param("aspCode") String aspCode);
}
