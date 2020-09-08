package com.enjoygolf24.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.bean.TblUserPre;
import com.enjoygolf24.api.service.bean.ManagerModifyServiceBean;
import com.enjoygolf24.api.service.bean.MemberModifyServiceBean;
import com.enjoygolf24.api.service.bean.PreMemberConvertServiceBean;
import com.enjoygolf24.api.service.bean.PreMemberModifyServiceBean;

@Service
public interface MemberInfoManageService {

	public boolean isUniqueEmail(String memberCode, String email);

	public boolean isUniqueEmailForPreMember(String memberCode, String email);

	public List<TblUser> getMemberListAll(String aspCode, int pageNo, int pageSize);

	public List<TblUser> getMemberList(String memberCode, String name, String phone, String email, String aspCode,
			int pageNo, int pageSize);

	public List<TblUser> getManagerListAll(int pageNo, int pageSize);

	public List<TblUser> getManagerList(String aspCode, String aspName, String memberCode, String name, String phone,
			String email, int pageNo, int pageSize);

	public List<TblUserPre> getPreMemberListAll(String aspCode, int pageNo, int pageSize);

	public List<TblUserPre> getPreMemberList(String memberCode, String name, String phone, String email, String aspCode,
			String useFlagCd, int pageNo, int pageSize);

	public String getAspName(String LoginUserAspCode);

	public TblUser selectMember(String memberCode);

	public TblUserPre selectPreMember(String preMemberCode);

	@Transactional
	public void memberModify(MemberModifyServiceBean serviceBean);

	@Transactional
	public void preMemberModify(PreMemberModifyServiceBean serviceBean);

	@Transactional
	public void managerModify(ManagerModifyServiceBean serviceBean);

	@Transactional
	public void convert(PreMemberConvertServiceBean serviceBean);

	@Transactional
	public void confirm(String preMemberCode, String loginUserCode);

	@Transactional
	public void sendAuthKeyMail(String memberCode, String memberTypeCode);
}
