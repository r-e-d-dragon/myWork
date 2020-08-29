
package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblPointCarriable;

@Repository
public interface TblPointCarriableRepository extends JpaRepository<TblPointCarriable, Long> {

	public List<TblPointCarriable> findByMemberCode(String memberCode);

}
