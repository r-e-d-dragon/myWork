package com.enjoygolf24.api.common.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblReservationLimitMaster;

@Repository
public interface TblReservationLimitMasterRepository extends JpaRepository<TblReservationLimitMaster, String> {

	TblReservationLimitMaster findByIdMemberGradeAndIdGradeCode(String memberGrade, String gradeCode);

}
