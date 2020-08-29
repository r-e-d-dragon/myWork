
package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.TblUserPre;

@Repository
public interface PreMemberRepository extends JpaRepository<TblUserPre, String> {

	public TblUserPre findByPreMemberCode(String memberCode);

	public List<TblUserPre> findByEmailAndUseFlagNot(String email, String useFlag);

}
