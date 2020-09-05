
package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.ViewReservationPointTimeTable;

@Repository
public interface ViewReservationPointTimeTableRepository extends JpaRepository<ViewReservationPointTimeTable, String> {

	List<ViewReservationPointTimeTable> findByDateTimeAndValidateStartTermLessThanEqual(Date dateTime,
			Date validateStartTerm);

	List<ViewReservationPointTimeTable> findByDateTime(Date dateTime);
}
