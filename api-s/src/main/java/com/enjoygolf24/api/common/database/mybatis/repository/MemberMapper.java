
package com.enjoygolf24.api.common.database.mybatis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.enjoygolf24.api.common.database.bean.TblAuthKeyManage;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.bean.TblUserPre;

@Mapper
public interface MemberMapper {

	public List<TblAuthKeyManage> setAuthStatusCd(@Param("memberCode") String email,
			@Param("processingCd") String processingCd, @Param("authStatusCd") String authStatusCd);

	public List<TblUser> getMemberList(@Param("memberCode") String memberCode, @Param("name") String name,
			@Param("phone") String phone, @Param("email") String email, @Param("aspCode") String aspCode);

	public List<TblUser> getManagerList(@Param("aspCode") String aspCode, @Param("aspName") String aspName,
			@Param("memberCode") String memberCode, @Param("name") String name, @Param("phone") String phone,
			@Param("email") String email);

	public List<TblUserPre> getPreMemberList(@Param("memberCode") String memberCode, @Param("name") String name,
			@Param("phone") String phone, @Param("email") String email, @Param("aspCode") String aspCode,
			@Param("useFlagCd") String useFlagCd);

	public List<TblUser> getMemberListForPointManage(@Param("memberCode") String memberCode, @Param("name") String name,
			@Param("phone") String phone, @Param("email") String email, @Param("aspCode") String aspCode);

}
