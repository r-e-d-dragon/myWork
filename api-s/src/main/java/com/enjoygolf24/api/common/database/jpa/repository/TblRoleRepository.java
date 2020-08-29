package com.enjoygolf24.api.common.database.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblRole;

@Repository
public interface TblRoleRepository extends JpaRepository<TblRole, String> {

	TblRole findByShopTypeCdAndAuthTypeCd(String shopTypeCd, String authTypeCd);
}
