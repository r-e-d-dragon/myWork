package com.enjoygolf24.api.common.database.mybatis.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.enjoygolf24.api.common.database.bean.TblPointHistory;

@Mapper
public interface PointMapper {

	public int getMonthlyPointBalance(@Param("memberCode") String memberCode, @Param("month") int month);

	public int getCarriablePointBalance(@Param("memberCode") String memberCode, @Param("currentDate") Date currentDate);

	public List<TblPointHistory> getHistory(@Param("memberCode") String memberCode,
			@Param("categoryCode") String categoryCode);
}
