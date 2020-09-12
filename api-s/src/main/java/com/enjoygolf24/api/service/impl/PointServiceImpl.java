package com.enjoygolf24.api.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.PointAppliedMonthCd;
import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.database.bean.TblPointHistory;
import com.enjoygolf24.api.common.database.bean.TblPointManage;
import com.enjoygolf24.api.common.database.bean.TblPointManagePK;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.jpa.repository.CodeMasterRepository;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PointHistoryRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PointManageRepository;
import com.enjoygolf24.api.common.database.mybatis.repository.MemberMapper;
import com.enjoygolf24.api.common.database.mybatis.repository.PointMapper;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.service.PointService;
import com.enjoygolf24.api.service.bean.PointManageServiceBean;
import com.github.pagehelper.PageHelper;

@Service
public class PointServiceImpl implements PointService {

	private static final Logger logger = LoggerFactory.getLogger(MemberRegisterServiceImpl.class);

	@Autowired
	HttpSession session;

	@Autowired
	MemberMapper memberMapper;

	@Autowired
	PointMapper pointMapper;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CodeMasterRepository codeMasterRepository;

	@Autowired
	private PointHistoryRepository pointHistoryRepository;

	@Autowired
	private PointManageRepository pointManageRepository;

	@Override
	public List<TblUser> getMemberListAll(String aspCode, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		return memberMapper.getMemberListForPointManage(null, null, null, null, aspCode);
	}

	@Override
	public List<TblUser> getMemberList(String memberCode, String name, String phone, String email, String aspCode,
			int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return memberMapper.getMemberListForPointManage(memberCode, name, phone, email, aspCode);
	}

	@Override
	public TblUser selectMember(String memberCode) {
		TblUser member = memberRepository.findByMemberCode(memberCode);
		return member;
	}

	@Override
	public String getMemberGradeName(String memberGradeCode) {
		String memberGradeName = codeMasterRepository.findByCodeTypeAndCd(CodeTypeCd.MEMBER_GRADE_CD, memberGradeCode)
				.getName();
		return memberGradeName;
	}

	@Override
	@Transactional
	public void insertPoint(PointManageServiceBean serviceBean) {
		Timestamp current = new Timestamp(System.currentTimeMillis());
		Date startDate;

		Integer pointVariation = Integer.parseInt(serviceBean.getPointVariation());

		TblPointHistory pointHistory = new TblPointHistory();

		pointHistory.setConsumedPoint(pointVariation);
		pointHistory.setMemberCode(serviceBean.getMemberCode());

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);

		if (PointAppliedMonthCd.NEXT_MONTH.equals(serviceBean.getPointAppliedMonthCd())) {
			c.add(Calendar.MONTH, 1);
		}

		startDate = c.getTime();

		pointHistory.setStartDate(startDate);

		pointHistory.setExpireDate(DateUtility.getDate("9999/12/31"));

		// TODO:PointCode?
		pointHistory.setPointCode("00");
		pointHistory.setRegisterDate(current);
		pointHistory.setRegisterUser(serviceBean.getLoginUserCd());
		pointHistory.setUpdateDate(current);
		pointHistory.setUpdateUser(serviceBean.getLoginUserCd());

		pointHistoryRepository.save(pointHistory);

		// set pointManage
		// TblPointManage tblPointManage = pointManageRepository
		// .findByIdMemberCodeAndCategoryCode(serviceBean.getMemberCode(),
		// PointCategoryCd.EVENT_POINT);
		/*
		 * if (tblPointManage != null) {
		 * tblPointManage.setConsumedPoint(pointVariation);
		 * tblPointManage.setPointAmount(tblPointManage.getPointAmount() +
		 * pointVariation); tblPointManage.setUpdateDate(current);
		 * tblPointManage.setUpdateUser(serviceBean.getLoginUserCd());
		 * 
		 * pointManageRepository.save(tblPointManage);
		 * 
		 * } else {
		 */
		TblPointManagePK pk = new TblPointManagePK();
		pk.setId(pointManageRepository.count());
		pk.setMemberCode(serviceBean.getMemberCode());

		TblPointManage newPointManage = new TblPointManage();
		newPointManage.setId(pk);
		newPointManage.setStartDate(DateUtility.toTimestampDayOfFirst(startDate));

		newPointManage.setEndDate(DateUtility.toTimestampDayOfLast(DateUtility.getDate("9999/12/31")));
		newPointManage.setCategoryCode(PointCategoryCd.EVENT_POINT);

		newPointManage.setPointAmount(pointVariation);
		newPointManage.setPointType("00");
		newPointManage.setRegisterDate(current);
		newPointManage.setRegisterUser(serviceBean.getLoginUserCd());
		newPointManage.setUpdateDate(current);
		newPointManage.setUpdateUser(serviceBean.getLoginUserCd());

		pointManageRepository.save(newPointManage);

		// }

		String txt = pointVariation + "pt" + " to " + serviceBean.getMemberCode() + " by "
				+ serviceBean.getLoginUserCd() + " at " + current;
		logger.info(txt);

	}

	@Override
	public int getCarriablePointBalance(String memberCode) {
		return pointMapper.getCarriablePointBalance(memberCode, new Date());
	}

	@Override
	public int getMonthlyPointBalance(String memberCode) {
		return pointMapper.getMonthlyPointBalance(memberCode, Calendar.getInstance().get(Calendar.MONTH) + 1);
	}

	@Override
	public List<TblPointHistory> getHistory(String memberCode, String categoryCode, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return pointMapper.getHistory(memberCode, categoryCode);
	}

	@Override
	public List<TblPointHistory> getHistoryListAll(String aspCode, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		return pointMapper.getHistoryList(null, null, null, null, aspCode);
	}

	@Override
	public List<TblPointHistory> getHistoryList(String memberCode, String name, String phone, String email,
			String aspCode, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return pointMapper.getHistoryList(memberCode, name, phone, email, aspCode);
	}

}
