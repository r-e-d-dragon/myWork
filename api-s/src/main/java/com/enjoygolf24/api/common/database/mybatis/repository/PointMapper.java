package com.enjoygolf24.api.common.database.mybatis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.enjoygolf24.api.common.database.bean.TblPointManage;

@Mapper
public interface PointMapper {

	public List<TblPointManage> getHistoryList(@Param("memberCode") String memberCode, @Param("name") String name,
			@Param("registerUserCode") String registerUserCode, @Param("registerUserName") String registerUserName,
			@Param("registeredMonth") String registeredMonth, @Param("startMonth") String startMonth,
			@Param("aspCode") String aspCode);
}
