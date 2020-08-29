package com.enjoygolf24.api.common.database.mybatis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.enjoygolf24.api.common.database.bean.TblAsp;

@Mapper
public interface AspMapper {

	public List<TblAsp> getAspList(@Param("aspName") String aspCode, @Param("aspCode") String aspName,
	        @Param("aspAddress") String aspAddress);
}
