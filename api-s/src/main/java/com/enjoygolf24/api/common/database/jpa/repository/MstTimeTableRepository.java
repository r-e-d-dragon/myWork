
package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.MstTimeTable;

@Repository
public interface MstTimeTableRepository extends JpaRepository<MstTimeTable, Long> {

	List<MstTimeTable> findByAspCodeAndDayTypeCdOrderByTimeTableCode(String aspCode, String DayTypeCd);

}
