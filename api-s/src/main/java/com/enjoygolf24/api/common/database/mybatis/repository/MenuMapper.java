package com.enjoygolf24.api.common.database.mybatis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.enjoygolf24.api.common.database.mybatis.bean.TblMenuInfo;

@Mapper
public interface MenuMapper {

	List<TblMenuInfo> getMainMenuListByUser(@Param("userCode") String userCode);

	List<TblMenuInfo> getMainMenuListByUserAndMenu(@Param("userCode") String userCode, @Param("menuId") String menuId);

	List<TblMenuInfo> getMainMenuListByUserAndMenuAndUrl(@Param("userCode") String userCode,
			@Param("menuId") String menuId, @Param("url") String url);

	List<TblMenuInfo> getMenuPath(@Param("userCode") String userCode, @Param("url") String url);
}
