
package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.ViewTimeGradeCalendar;

@Repository
public interface ViewTimeGradeCalendarRepository extends JpaRepository<ViewTimeGradeCalendar, String> {

	List<ViewTimeGradeCalendar> findByDateTimeOrderByTimeTableCode(Date dateTime);

}
