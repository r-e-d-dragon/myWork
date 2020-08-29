package com.enjoygolf24.api.common.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblUserRole;

@Repository
public interface TblUserRoleRepository extends JpaRepository<TblUserRole, Long> {

	TblUserRole findByUserCode(String userCode);
}
