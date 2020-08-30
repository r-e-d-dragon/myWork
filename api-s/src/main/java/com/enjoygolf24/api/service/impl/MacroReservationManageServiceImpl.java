package com.enjoygolf24.api.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.code.DateTypeCd;
import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.code.ReservationStatusCd;
import com.enjoygolf24.api.common.database.bean.TblPointConsumeMaster;
import com.enjoygolf24.api.common.database.bean.TblReservation;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.common.database.jpa.repository.ReservationRepository;
import com.enjoygolf24.api.common.database.jpa.repository.TblPointConsumeMasterRepository;
import com.enjoygolf24.api.common.database.mybatis.repository.ReservationMapper;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.service.MacroReservationManageService;
import com.enjoygolf24.api.service.bean.MemberReservationServiceBean;

@Service
public class MacroReservationManageServiceImpl implements MacroReservationManageService {

	@Autowired
	HttpSession session;

	@Autowired
	ReservationMapper reservationMapper;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	TblPointConsumeMasterRepository pointConsumeMasterRepository;

	@Override
	@Transactional
	public List<String> MacroReservationRegister(MemberReservationServiceBean serviceBean) {

		List<String> ids = new ArrayList<String>();

		// TODO
		List<TblPointConsumeMaster> tblPointConsumeMaster = pointConsumeMasterRepository
				.findByIdDateKindOrderByIdTimeSlot(DateTypeCd.WEEKDAY);

		// TODO
		int fromReservationTime = Integer.valueOf(serviceBean.getFromReservationTime().substring(0, 2));
		int toReservationTime = Integer.valueOf(serviceBean.getToReservationTime().substring(0, 2));

		LocalDateTime start = DateUtility.getDate(serviceBean.getFromReservationDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime().plusHours(fromReservationTime);
		LocalDateTime end = DateUtility.getDate(serviceBean.getToReservationDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDateTime().plusHours(toReservationTime);

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");

		for (LocalDateTime current = start; !current.isAfter(end); current = current.plusHours(1)) {
			serviceBean.setReservationDate(current.format(format));
			serviceBean.setReservationTime(tblPointConsumeMaster.get(current.getHour()).getTimeSlotName());
			// 打席数分
			for (String batNumber : serviceBean.getChkBatNumbers()) {
				// 打席番号
				serviceBean.setBatNumber(batNumber);
				// 予約情報登録
				TblReservation reservation = insertMacroReservation(serviceBean);
				ids.add(reservation.getReservationId());
			}
		}

		return ids;
	}

	/**
	 * 予約情報登録
	 * 
	 * @param serviceBean
	 * @param reservationHistory
	 * @return TblReservation
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Transactional
	private TblReservation insertMacroReservation(MemberReservationServiceBean serviceBean) {

		TblReservation reservation = new TblReservation();

		reservation.setReservationId(UUID.randomUUID().toString());
		reservation.setReservationNumber(serviceBean.getReservationNumber());
		reservation.setStatus(ReservationStatusCd.STATUS_FIXED);
		reservation.setMemberCode(serviceBean.getLoginUserCd());
		reservation.setAspCode(serviceBean.getAspCode());
		reservation.setBatNumber(serviceBean.getBatNumber());
		reservation.setReservationDate(DateUtility.getDate(serviceBean.getReservationDate()));
		reservation.setReservationTime(serviceBean.getReservationTime());
		reservation.setConsumedPoint(0);
		reservation.setPointCategoryCode(PointCategoryCd.ADMIN_POINT);
		reservation.setPointGrade("");
		reservation.setPenaltyPoint(0);

		reservation.setRegisterUser(serviceBean.getLoginUserCd());
		reservation.setRegisterDate(new Timestamp(System.currentTimeMillis()));
		reservation.setUpdateUser(serviceBean.getLoginUserCd());
		reservation.setUpdateDate(new Timestamp(System.currentTimeMillis()));

		reservationRepository.save(reservation);

		return reservation;
	}

}