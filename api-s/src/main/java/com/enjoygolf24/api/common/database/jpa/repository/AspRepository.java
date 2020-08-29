
package com.enjoygolf24.api.common.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblAsp;

@Repository
public interface AspRepository extends JpaRepository<TblAsp, String> {

	public TblAsp findByAspCode(String aspCode);

	public TblAsp findByAspName(String aspName);
}
