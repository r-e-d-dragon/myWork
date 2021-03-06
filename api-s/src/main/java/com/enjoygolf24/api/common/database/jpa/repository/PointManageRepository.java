package com.enjoygolf24.api.common.database.jpa.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblPointManage;
import com.enjoygolf24.api.common.database.bean.TblPointManagePK;

@Repository
public interface PointManageRepository extends JpaRepository<TblPointManage, String> {

	TblPointManage findById(TblPointManagePK id);

	TblPointManage findByIdMemberCodeAndPointTypeAndStartDateAndEndDate(String memberCode, String pointType,
			Timestamp startDate, Timestamp endDate);

	TblPointManage findByIdMemberCodeAndCategoryCode(String memberCode, String categoryCode);

}
