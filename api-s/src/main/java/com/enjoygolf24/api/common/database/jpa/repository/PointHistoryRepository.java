package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblPointHistory;

@Repository
public interface PointHistoryRepository extends JpaRepository<TblPointHistory, String> {

	TblPointHistory findByMemberCode(String memberCode);

	List<TblPointHistory> findByReservationId(String reservationId);
}
