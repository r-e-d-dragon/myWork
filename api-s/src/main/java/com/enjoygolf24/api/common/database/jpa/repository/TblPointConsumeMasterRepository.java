package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblPointConsumeMaster;

@Repository
public interface TblPointConsumeMasterRepository extends JpaRepository<TblPointConsumeMaster, String> {

	List<TblPointConsumeMaster> findByIdDateKindOrderByIdTimeSlot(String dateKind);

}
