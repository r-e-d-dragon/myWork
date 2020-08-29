
package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.ZipMaster;

@Repository
public interface ZipMasterRepository extends JpaRepository<ZipMaster, Long> {

	public List<ZipMaster> findByZipCode(String zipCode);

}
