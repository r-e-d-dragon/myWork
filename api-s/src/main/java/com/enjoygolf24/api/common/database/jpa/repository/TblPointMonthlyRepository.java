
package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblPointMonthly;

@Repository
public interface TblPointMonthlyRepository extends JpaRepository<TblPointMonthly, Long> {

	public List<TblPointMonthly> findByMemberCode(String memberCode);

}
