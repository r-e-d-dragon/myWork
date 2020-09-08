package com.enjoygolf24.api.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.enjoygolf24.api.common.code.MemberGradeCd;
import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.code.PointTypeCd;
import com.enjoygolf24.api.common.constants.PointContants;
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

		String memo = null;
		String pointTypeCd = serviceBean.getPointTypeCd();
		Integer pointVariation = null;
		Date endDateForMonthly = null;

		if (PointTypeCd.BIRHDAY.equals(pointTypeCd)) {
			pointVariation = PointContants.EVENT_POINT_FOR_BIRHDAY;
		} else if (PointTypeCd.INTRODUCTION.equals(pointTypeCd)) {
			pointVariation = PointContants.EVENT_POINT_FOR_INTRODUCTION;
		} else if (PointTypeCd.MONTLY_POINT.equals(pointTypeCd)) {
			TblUser member = memberRepository.findByMemberCode(serviceBean.getMemberCode());
			pointVariation = (MemberGradeCd.FULL_MEMBER.equals(member.getMemberGradeCode()))
					? PointContants.MONTLY_POINT_FOR_FULL_MENBER
					: PointContants.MONTLY_POINT_FOR_HALF_MENBER;
		} else if (PointTypeCd.OLDBIE.equals(pointTypeCd)) {
			pointVariation = PointContants.EVENT_POINT_FOR_OLDBIE;
		} else {
			pointVariation = Integer.parseInt(serviceBean.getPointVariation());
			memo = serviceBean.getMemo();
		}

		TblPointHistory pointHistory = new TblPointHistory();

		// pointHistory.setCategoryCode(serviceBean.getPointExpirationTermCd());
		pointHistory.setConsumedPoint(pointVariation);
		pointHistory.setMemberCode(serviceBean.getMemberCode());

		if (PointCategoryCd.MONTLY_POINT.equals(serviceBean.getPointExpirationTermCd())) {
			pointHistory.setStartDate(DateUtility.getDate(serviceBean.getTermStartDate()));
			LocalDate convertedDate = LocalDate.parse(serviceBean.getTermStartDate(),
					DateTimeFormatter.ofPattern("yyyy/M/d"));
			convertedDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));

			endDateForMonthly = java.sql.Date.valueOf(convertedDate);
			pointHistory.setExpireDate(endDateForMonthly);

		} else {
			pointHistory.setStartDate(DateUtility.getDate(serviceBean.getTermStartDate()));
			pointHistory.setExpireDate(DateUtility.getDate("9999/12/31"));
		}

		pointHistory.setPointCode(pointTypeCd);
		pointHistory.setRegisterDate(current);
		pointHistory.setRegisterUser(serviceBean.getLoginUserCd());
		pointHistory.setUpdateDate(current);
		pointHistory.setUpdateUser(serviceBean.getLoginUserCd());
		// pointHistory.setMemo(memo);

		pointHistoryRepository.save(pointHistory);

		// set pointManage
		TblPointManage tblPointManage = pointManageRepository
				.findByIdMemberCodeAndCategoryCode(serviceBean.getMemberCode(), serviceBean.getPointExpirationTermCd());
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
		newPointManage.setCategoryCode(serviceBean.getPointExpirationTermCd());
		newPointManage
				.setStartDate(DateUtility.toTimestampDayOfFirst(DateUtility.getDate(serviceBean.getTermStartDate())));

		if (PointCategoryCd.MONTLY_POINT.equals(serviceBean.getPointExpirationTermCd())) {

			newPointManage.setEndDate(DateUtility.toTimestampDayOfLast(endDateForMonthly));
		} else {
			newPointManage.setEndDate(DateUtility.toTimestampDayOfLast(DateUtility.getDate("9999/12/31")));
		}

		newPointManage.setPointAmount(pointVariation);
		newPointManage.setPointType(pointTypeCd);
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

}
