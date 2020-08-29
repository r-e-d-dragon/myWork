package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblReservation;

@Repository
public interface ReservationRepository extends JpaRepository<TblReservation, String> {

	/**
	 * 予約検索
	 * 
	 * @param memberCode 会員コード
	 * @return 予約情報リスト
	 */
	List<TblReservation> findByMemberCode(String memberCode);

	/**
	 * 予約検索
	 * 
	 * @param aspCode 店舗コード
	 * @return 予約情報リスト
	 */
	List<TblReservation> findByAspCode(String aspCode);

	/**
	 * 予約検索
	 * 
	 * @param reservationDate 予約日
	 * @return 予約情報リスト
	 */
	List<TblReservation> findByReservationDate(Date reservationDate);
}
