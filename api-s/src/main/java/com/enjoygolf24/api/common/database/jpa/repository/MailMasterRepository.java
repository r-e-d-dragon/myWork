
package com.enjoygolf24.api.common.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblMailMaster;

@Repository
public interface MailMasterRepository extends JpaRepository<TblMailMaster, Long> {

	public TblMailMaster findByMailSectionCd(String mailSectionCd);

}
