package com.enjoygolf24.api.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.code.DateTypeCd;
import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.code.ReservationStatusCd;
import com.enjoygolf24.api.common.database.bean.TblMacroReservationManage;
import com.enjoygolf24.api.common.database.bean.TblPointConsumeMaster;
import com.enjoygolf24.api.common.database.bean.TblPointHistory;
import com.enjoygolf24.api.common.database.bean.TblPointManage;
import com.enjoygolf24.api.common.database.bean.TblReservation;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PointHistoryRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PointManageRepository;
import com.enjoygolf24.api.common.database.jpa.repository.ReservationRepository;
import com.enjoygolf24.api.common.database.jpa.repository.TblMacroReservationManageRepository;
import com.enjoygolf24.api.common.database.jpa.repository.TblPointConsumeMasterRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.database.mybatis.repository.ReservationMapper;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.service.MacroReservationManageService;
import com.enjoygolf24.api.service.bean.MemberReservationServiceBean;
import com.github.pagehelper.PageHelper;

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
	TblMacroReservationManageRepository macroReservationManageRepository;

	@Autowired
	TblPointConsumeMasterRepository pointConsumeMasterRepository;

	@Autowired
	PointHistoryRepository pointHistoryRepository;

	@Autowired
	PointManageRepository pointManageRepository;

	@Override
	@Transactional
	public TblMacroReservationManage MacroReservationRegister(MemberReservationServiceBean serviceBean) {

		// TODO
		List<TblPointConsumeMaster> tblPointConsumeMaster = pointConsumeMasterRepository
				.findByIdDateKindOrderByIdTimeSlot(DateTypeCd.WEEKDAY);

		TblMacroReservationManage manager = insertMacroReservationManage(serviceBean);

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

				// 予約情報取得
				TblReservation reservation = reservationRepository
						.findByAspCodeAndReservationDateAndReservationTimeAndBatNumberAndStatus(
								serviceBean.getAspCode(), DateUtility.getDate(serviceBean.getReservationDate()),
								serviceBean.getReservationTime(), batNumber, ReservationStatusCd.STATUS_FIXED);

				if (reservation != null) {
					// 予約情報更新ー取消
					updateReservationCancle(serviceBean, reservation);
					// 取消、ポイント戻し処理
					updateMemberCanclePointManage(serviceBean, reservation.getReservationId());
				}

				// 予約情報登録
				insertMacroReservation(serviceBean, batNumber);
			}
		}

		return manager;
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
	private TblMacroReservationManage insertMacroReservationManage(MemberReservationServiceBean serviceBean) {

		TblMacroReservationManage macroReservationManage = new TblMacroReservationManage();

		macroReservationManage.setReservationNumber(serviceBean.getReservationNumber());
		macroReservationManage.setMacroName(serviceBean.getMacroName());

		macroReservationManage.setStatus(serviceBean.getStatus());
		macroReservationManage.setAspCode(serviceBean.getAspCode());
		macroReservationManage.setBatNumber(serviceBean.getBatNumber());
		macroReservationManage.setFromReservationDate(DateUtility.getDate(serviceBean.getFromReservationDate()));
		macroReservationManage.setFromReservationTime(serviceBean.getFromReservationTime());
		macroReservationManage.setToReservationDate(DateUtility.getDate(serviceBean.getToReservationDate()));
		macroReservationManage.setToReservationTime(serviceBean.getToReservationTime());

		macroReservationManage.setRegisterUser(serviceBean.getLoginUserCd());
		macroReservationManage.setRegisterDate(new Timestamp(System.currentTimeMillis()));
		macroReservationManage.setUpdateUser(serviceBean.getLoginUserCd());
		macroReservationManage.setUpdateDate(new Timestamp(System.currentTimeMillis()));

		macroReservationManageRepository.save(macroReservationManage);

		return macroReservationManage;
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
	private TblReservation insertMacroReservation(MemberReservationServiceBean serviceBean, String batNumber) {

		TblReservation reservation = new TblReservation();

		reservation.setReservationId(UUID.randomUUID().toString());
		reservation.setReservationNumber(serviceBean.getReservationNumber());
		reservation.setStatus(serviceBean.getStatus());
		reservation.setMemberCode(serviceBean.getLoginUserCd());
		reservation.setAspCode(serviceBean.getAspCode());
		reservation.setBatNumber(batNumber);
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

	/**
	 * 予約情報取消
	 * 
	 * @param serviceBean
	 * @param reservationId
	 * @return
	 */
	@Transactional
	private void updateReservationCancle(MemberReservationServiceBean serviceBean, TblReservation reservation) {

		reservation.setStatus(ReservationStatusCd.STATUS_CANCLE);

		reservation.setUpdateUser(serviceBean.getLoginUserCd());
		reservation.setUpdateDate(new Timestamp(System.currentTimeMillis()));

		// TODO
		if (!PointCategoryCd.ADMIN_POINT.contentEquals(reservation.getPointCategoryCode())) {
			// ユーザ予約
			// TODO 予約取消メール送信
		}

		reservationRepository.save(reservation);
	}

	/**
	 * 予約取消 ポイント管理
	 * 
	 * @param serviceBean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Transactional
	private void updateMemberCanclePointManage(MemberReservationServiceBean serviceBean, String reservationId) {

		// 予約履歴更新
		List<TblPointHistory> list = pointHistoryRepository.findByReservationId(reservationId);
		for (TblPointHistory history : list) {
			history.setConsumedPoint(-history.getConsumedPoint());
			history.setUpdateUser(serviceBean.getLoginUserCd());
			history.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			pointHistoryRepository.save(history);

			// ポイント管理更新
			TblPointManage tblPointManage = pointManageRepository.findByIdMemberCodeAndPointTypeAndStartDateAndEndDate(
					serviceBean.getMemberCode(), history.getPointCode(), history.getStartDate(),
					history.getExpireDate());

			if (tblPointManage != null) {
				tblPointManage.setConsumedPoint(tblPointManage.getConsumedPoint() + history.getConsumedPoint());
				tblPointManage.setPointAmount(tblPointManage.getPointAmount() - history.getConsumedPoint());

				tblPointManage.setUpdateUser(serviceBean.getLoginUserCd());
				tblPointManage.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				pointManageRepository.save(tblPointManage);
			}
		}
	}

	@Override
	public List<MemberReservationManage> getMacroReservationList(String aspCode, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return reservationMapper.getMacroReservationList(aspCode);
	}
}