
package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblUser;

@Repository
public interface MemberRepository extends JpaRepository<TblUser, String> {

	public TblUser findByEmailAndUseFlag(String email, String useFlag);

	public List<TblUser> findByEmailAndUseFlagNot(String email, String useFlag);

	public TblUser findByMemberCode(String memberCode);

}
