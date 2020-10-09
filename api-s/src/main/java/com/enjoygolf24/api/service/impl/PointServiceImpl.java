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
import org.thymeleaf.util.StringUtils;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.PointAppliedMonthCd;
import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.code.PointTypeCd;
import com.enjoygolf24.api.common.database.bean.TblPointManage;
import com.enjoygolf24.api.common.database.bean.TblPointManagePK;
import com.enjoygolf24.api.common.database.bean.TblPointSettings;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.jpa.repository.CodeMasterRepository;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PointManageRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PointSettingsRepository;
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
	private PointSettingsRepository pointSettingsRepository;

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

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);

		if (PointAppliedMonthCd.NEXT_MONTH.equals(serviceBean.getPointAppliedMonthCd())) {
			c.add(Calendar.MONTH, 1);
		}

		startDate = c.getTime();

		TblPointManagePK pk = new TblPointManagePK();
		pk.setId(pointManageRepository.count());
		pk.setMemberCode(serviceBean.getMemberCode());

		TblPointManage newPointManage = new TblPointManage();
		newPointManage.setId(pk);
		newPointManage.setStartDate(DateUtility.toTimestampDayOfFirst(startDate));

		newPointManage.setCategoryCode(serviceBean.getPointCategoryCd());

		newPointManage.setPointAmount(pointVariation);

		if (PointCategoryCd.EVENT_POINT.equals(serviceBean.getPointCategoryCd())) {
			newPointManage.setPointType(PointTypeCd.CUSTOM);
			newPointManage.setEndDate(DateUtility.toTimestampDayOfLast(DateUtility.getDate("9999/12/31")));
		} else {
			newPointManage.setPointType(PointTypeCd.MONTLY_POINT);
			Date endDate;
			Calendar endC = c;
			endC.add(Calendar.MONTH, 1);
			endC.set(Calendar.DAY_OF_MONTH, 0);
			endDate = endC.getTime();

			newPointManage.setEndDate(DateUtility.toTimestampDayOfLast(endDate));
		}
		newPointManage.setConsumedPoint(0);
		newPointManage.setRegisterDate(current);
		newPointManage.setRegisterUser(serviceBean.getLoginUserCd());
		newPointManage.setUpdateDate(current);
		newPointManage.setUpdateUser(serviceBean.getLoginUserCd());
		newPointManage.setMemo(serviceBean.getMemo());

		pointManageRepository.save(newPointManage);

		String txt = pointVariation + "pt" + " to " + serviceBean.getMemberCode() + " by "
				+ serviceBean.getLoginUserCd() + " at " + current;
		logger.info(txt);

	}

	@Override
	public List<TblPointManage> getHistoryListAll(String aspCode, int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		return pointMapper.getHistoryList(null, null, null, null, null, null, aspCode);
	}

	@Override
	public List<TblPointManage> getHistoryList(String memberCode, String name, String registerUserCode,
			String registerUserName, String registeredMonth, String startMonth, String aspCode, int pageNo,
			int pageSize) {
		PageHelper.startPage(pageNo, pageSize);

		return pointMapper.getHistoryList(memberCode, name, registerUserCode, registerUserName,
				monthformat(registeredMonth), monthformat(startMonth), aspCode);
	}

	private String monthformat(String month) {
		if (!StringUtils.isEmpty(month)) {
			if (Integer.parseInt(month.substring(5)) < 10) {
				month = month.substring(0, 5) + month.substring(6);
			}
		}
		return month;
	}

	@Override
	public TblPointManage getHistory(String id, String memberCode) {

		TblPointManagePK pk = new TblPointManagePK();
		pk.setId(Long.parseLong(id, 10));
		pk.setMemberCode(memberCode);

		TblPointManage pointHistory = pointManageRepository.findById(pk);
		return pointHistory;
	}

	@Override
	public TblPointSettings getPointSettings(String authTypeCd) {

		TblPointSettings pointSettings = pointSettingsRepository.findByAuthTypeCd(authTypeCd);
		return pointSettings;
	}

}
