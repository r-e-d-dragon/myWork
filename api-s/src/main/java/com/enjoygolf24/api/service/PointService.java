package com.enjoygolf24.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.TblPointManage;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.service.bean.PointManageServiceBean;

@Service
public interface PointService {

	public List<TblUser> getMemberListAll(String aspCode, int pageNo, int pageSize);

	public List<TblUser> getMemberList(String memberCode, String name, String phone, String aspCode, String email,
			int pageNo, int pageSize);

	public TblUser selectMember(String memberCode);

	public String getMemberGradeName(String memberGradeCode);

	@Transactional
	public void insertPoint(PointManageServiceBean serviceBean);

	public List<TblPointManage> getHistoryListAll(String aspCode, int pageNo, int pageSize);

	public List<TblPointManage> getHistoryList(String memberCode, String name, String registerUserCode,
			String registerUserName, String registeredMonth, String startMonth, String aspCode, int pageNo,
			int pageSize);

	public TblPointManage getHistory(String id, String memberCode);

}
