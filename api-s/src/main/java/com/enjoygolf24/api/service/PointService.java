package com.enjoygolf24.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.TblPointMonthly;
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

	public int getCarriablePointBalance(String memberCode);

	public int getMonthlyPointBalance(String memberCode);

	public List<TblPointMonthly> getHistoryMonthly(String memberCode, int pageNo, int pageSize);

}