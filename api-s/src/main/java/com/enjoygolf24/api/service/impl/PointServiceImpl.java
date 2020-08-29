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
import com.enjoygolf24.api.common.code.MemberGradeCd;
import com.enjoygolf24.api.common.code.PointExpirationTermCd;
import com.enjoygolf24.api.common.code.PointTypeCd;
import com.enjoygolf24.api.common.constants.PointContants;
import com.enjoygolf24.api.common.database.bean.TblPointCarriable;
import com.enjoygolf24.api.common.database.bean.TblPointMonthly;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.jpa.repository.CodeMasterRepository;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.common.database.jpa.repository.TblPointCarriableRepository;
import com.enjoygolf24.api.common.database.jpa.repository.TblPointMonthlyRepository;
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
	private TblPointMonthlyRepository tblPointMonthlyRepository;

	@Autowired
	private TblPointCarriableRepository tblPointCarriableRepository;

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

		if (PointExpirationTermCd.MONTLY.equals(serviceBean.getPointExpirationTermCd())) {
			TblPointMonthly pointMonthly = new TblPointMonthly();

			pointMonthly.setMemberCode(serviceBean.getMemberCode());
			pointMonthly.setMemo(memo);
			pointMonthly.setPointTypeCd(pointTypeCd);
			pointMonthly.setPointVariation(pointVariation);
			pointMonthly.setRegisterDate(current);
			pointMonthly.setRegisterUser(serviceBean.getLoginUserCd());
			pointMonthly.setUpdateDate(current);
			pointMonthly.setUpdateUser(serviceBean.getLoginUserCd());

			tblPointMonthlyRepository.save(pointMonthly);

		} else {
			TblPointCarriable pointCarriable = new TblPointCarriable();
			pointCarriable.setMemberCode(serviceBean.getMemberCode());
			pointCarriable.setMemo(memo);
			pointCarriable.setPointTypeCd(pointTypeCd);
			pointCarriable.setPointVariation(pointVariation);
			pointCarriable.setRegisterDate(current);
			pointCarriable.setRegisterUser(serviceBean.getLoginUserCd());
			if (PointTypeCd.CUSTOM.equals(pointTypeCd)) {
				pointCarriable.setTermStartDate(DateUtility.getDate(serviceBean.getTermStartDate()));
				pointCarriable.setTermEndDate(DateUtility.getDate("9999/12/31"));
			} else {
				Timestamp termForOldbie = new Timestamp(System.currentTimeMillis());
				Calendar cal = Calendar.getInstance();
				cal.setTime(termForOldbie);
				cal.add(Calendar.MONTH, PointContants.TERM_FOR_OLDBIE);
				termForOldbie.setTime(cal.getTime().getTime());

				pointCarriable.setTermStartDate(current);
				// pointCarriable.setTermEndDate(termForOldbie);
				pointCarriable.setTermEndDate(DateUtility.getDate("9999/12/31"));
			}

			pointCarriable.setUpdateDate(current);
			pointCarriable.setUpdateUser(serviceBean.getLoginUserCd());

			tblPointCarriableRepository.save(pointCarriable);

		}
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
	public List<TblPointMonthly> getHistoryMonthly(String memberCode, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return pointMapper.getHistoryMonthly(memberCode);
	}

}
