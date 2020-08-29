package com.enjoygolf24.api.common.database.mybatis.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.enjoygolf24.api.common.database.bean.TblUser;

@Mapper
public interface UserLoginMapper {

	public TblUser getLoginInfo(@Param("email") String email);
}
