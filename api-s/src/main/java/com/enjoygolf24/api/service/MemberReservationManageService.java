package com.enjoygolf24.api.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.TblReservation;
import com.enjoygolf24.api.common.database.bean.TblReservationLimitMaster;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.service.bean.MemberReservationServiceBean;

@Service
public interface MemberReservationManageService {

	public List<MemberReservationManage> getMemberReservationList(String reservationNumber, String memberCode,
			String aspCode, String reservationDate, String status, boolean valide, int pageNo, int pageSize);

	public List<MemberReservationManage> getReservationList(String aspCode, String reservationDate, String dateKind);

	@Transactional
	public String MemberReservationRegister(MemberReservationServiceBean serviceBean)
			throws IllegalAccessException, InvocationTargetException;

	@Transactional
	public String MemberReservationUpdate(MemberReservationServiceBean serviceBean);

	@Transactional
	public String MemberReservationCancle(MemberReservationServiceBean serviceBean);

	public TblReservation getReservation(String reservationId);

	public List<MemberReservationManage> getMemberPointManageList(String memberCode, String categoryCode,
			String reservationDate);

	public TblReservationLimitMaster getMemberReservationLimit(String memberGrade, String gradeCode);

	public List<MemberReservationManage> getMemberReservationAllList(String reservationNumber, String memberCode,
			String aspCode, String batNumber, String reservationDate, String reservationTime, String status,
			boolean valide);
}
