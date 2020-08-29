
package com.enjoygolf24.api.common.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblAuthKeyManage;

@Repository
public interface AuthKeyManageRepository extends JpaRepository<TblAuthKeyManage, Long> {

	public TblAuthKeyManage findByAuthKeyAndAuthStatusCd(String authKey, String authStatusCd);

}
