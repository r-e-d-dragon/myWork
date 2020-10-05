package com.enjoygolf24.api.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.MstReservationLimit;
import com.enjoygolf24.api.common.database.bean.TblReservation;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.database.mybatis.bean.PointManage;
import com.enjoygolf24.api.common.database.mybatis.bean.ReservationPointTimeTableInfo;
import com.enjoygolf24.api.service.bean.MemberReservationServiceBean;

@Service
public interface MemberReservationManageService {

	public List<MemberReservationManage> getMemberReservationList(String reservationNumber, String memberCode,
			String aspCode, String reservationDate, String status, boolean valid, int pageNo, int pageSize);

	public List<MemberReservationManage> getMemberReservationList(String reservationNumber, String memberCode,
			String aspCode, String reservationDate, String status, String name, String kananame, String phone,
			String email, boolean valid, int pageNo, int pageSize);

	public List<MemberReservationManage> getReservationList(String aspCode, String reservationDate, String dateKind);

	@Transactional
	public String MemberReservationRegister(MemberReservationServiceBean serviceBean)
			throws IllegalAccessException, InvocationTargetException;

	@Transactional
	public String MemberReservationUpdate(MemberReservationServiceBean serviceBean);

	@Transactional
	public String MemberReservationCancle(MemberReservationServiceBean serviceBean);

	public TblReservation getReservation(String reservationId);

	public List<PointManage> getMemberPointManageList(String memberCode, String categoryCode, String reservationDate);

	public MstReservationLimit getMemberReservationLimit(String memberTypeCode, Date reservationDate);

	public List<MemberReservationManage> getMemberReservationAllList(String reservationNumber, String memberCode,
			String aspCode, String batNumber, String reservationDate, String reservationTime, String status,
			boolean valid);

	public List<ReservationPointTimeTableInfo> getViewReservationPonitTimeTableInfo(Date dateTime,
			Date validateStartTerm);

	public MemberReservationManage getMemberReservationInfo(String memberCode, String reservationDate);

}
