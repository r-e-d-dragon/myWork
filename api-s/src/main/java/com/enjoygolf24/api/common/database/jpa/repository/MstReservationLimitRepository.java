
package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.MstReservationLimit;

@Repository
public interface MstReservationLimitRepository extends JpaRepository<MstReservationLimit, Long> {

	MstReservationLimit findByMemberTypeCdAndValidateStartTermLessThanEqual(String memberTypeCode,
			Date reservationDate);

}
