package com.enjoygolf24.api.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.database.bean.TblPointHistory;
import com.enjoygolf24.api.common.database.bean.TblPointManage;
import com.enjoygolf24.api.common.database.bean.TblPointManagePK;
import com.enjoygolf24.api.common.database.bean.TblReservation;
import com.enjoygolf24.api.common.database.bean.TblReservationLimitMaster;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PointHistoryRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PointManageRepository;
import com.enjoygolf24.api.common.database.jpa.repository.ReservationRepository;
import com.enjoygolf24.api.common.database.jpa.repository.TblReservationLimitMasterRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.database.mybatis.repository.ReservationMapper;
import com.enjoygolf24.api.common.utility.DateUtility;
import com.enjoygolf24.api.service.MemberReservationManageService;
import com.enjoygolf24.api.service.bean.MemberReservationServiceBean;
import com.github.pagehelper.PageHelper;

@Service
public class MemberReservationManageServiceImpl implements MemberReservationManageService {

	@Autowired
	HttpSession session;

	@Autowired
	ReservationMapper reservationMapper;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	PointHistoryRepository pointHistoryRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PointManageRepository pointManageRepository;

	@Autowired
	TblReservationLimitMasterRepository tblReservationLimitMasterRepository;

	@Override
	public List<MemberReservationManage> getMemberReservationList(String reservationNumber, String memberCode,
			String aspCode, String reservationDate, String status, boolean valide, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return reservationMapper.getMemberReservationList(reservationNumber, memberCode, aspCode, null, reservationDate,
				null, status, valide);
	}

	@Override
	public List<MemberReservationManage> getReservationList(String aspCode, String reservationDate, String dateKind) {
		return reservationMapper.getReservationList(aspCode, reservationDate, dateKind);
	}

	@Override
	@Transactional
	public String MemberReservationUpdate(MemberReservationServiceBean serviceBean) {

		// 予約情報更新
		TblReservation reservation = updateReservation(serviceBean);

		// 予約日
		String reservationDate = serviceBean.getReservationDate();
		// 月ポイント情報取得
		if (PointCategoryCd.MONTLY_POINT.equals(serviceBean.getPointCategoryCode())) {
			List<MemberReservationManage> pointList = getMemberPointManageList(serviceBean.getMemberCode(),
					PointCategoryCd.MONTLY_POINT, serviceBean.getReservationDate());
			// TODO 当月分は当月のみ使用、ポイントがあっても開始日か過ぎてないと使えない。
			// TODO 来月は、来月の使用ポイントから計算
			Optional<MemberReservationManage> mPoint = pointList.stream()
					.filter(p -> p.getStartDate().startsWith(reservationDate.substring(0, 7))).findFirst();
			if (mPoint.isPresent()) {
				System.out.println("getPointManageId > " + mPoint.get().getPointManageId());
				if (serviceBean.getConsumedPoint() <= mPoint.get().getPointAmount()) {
					// ポイント履歴登録
					insertPointHistory(serviceBean, mPoint.get(), reservation.getReservationId(),
							serviceBean.getConsumedPoint());
					// ポイント管理
					updateMemberPointManage(serviceBean, mPoint.get(), serviceBean.getConsumedPoint());
				}
			} else {
				// TODO 予約月のポイント情報がない
			}
		}

		// イベントポイント情報取得
		if (PointCategoryCd.EVENT_POINT.equals(serviceBean.getPointCategoryCode())) {
			List<MemberReservationManage> pointList = getMemberPointManageList(serviceBean.getMemberCode(),
					PointCategoryCd.EVENT_POINT, serviceBean.getReservationDate());

			int consumedPoint = serviceBean.getConsumedPoint();
			for (MemberReservationManage point : pointList) {
				System.out.println("ConsumedPoint::::: " + point.getPointAmount());
				if (point.getPointAmount() - consumedPoint >= 0) {
					// ポイント履歴登録
					insertPointHistory(serviceBean, point, reservation.getReservationId(), consumedPoint);
					// ポイント管理
					updateMemberPointManage(serviceBean, point, consumedPoint);
					break;
				} else {
					consumedPoint = consumedPoint - point.getPointAmount();
					// ポイント履歴登録
					insertPointHistory(serviceBean, point, reservation.getReservationId(), point.getPointAmount());
					// ポイント管理
					updateMemberPointManage(serviceBean, point, point.getPointAmount());
				}
			}
		}

		return reservation.getReservationId();
	}

	@Override
	@Transactional
	public String MemberReservationRegister(MemberReservationServiceBean serviceBean) {

		// 仮予約情報登録
		TblReservation reservation = insertReservation(serviceBean);

		return reservation.getReservationId();
	}

	@Override
	@Transactional
	public String MemberReservationCancle(MemberReservationServiceBean serviceBean) {

		// 予約情報更新ー取消
		updateReservationCancle(serviceBean);

		// 取消、ポイント戻し処理
		updateMemberCanclePointManage(serviceBean);

		return serviceBean.getReservationId();
	}

	/**
	 * 予約履歴登録
	 * 
	 * @param serviceBean
	 * @return
	 */
	@Transactional
	private TblPointHistory insertPointHistory(MemberReservationServiceBean serviceBean,
			MemberReservationManage pointManage, String reservationId, int consomedPoint) {

		TblPointHistory pointHistory = new TblPointHistory();

		pointHistory.setMemberCode(serviceBean.getMemberCode());
		pointHistory.setReservationId(reservationId);
		// ポイントコード
		pointHistory.setPointCode(pointManage.getPointType());
		// 消費ポイント
		pointHistory.setConsumedPoint(consomedPoint);

		pointHistory.setStartDate(DateUtility.getDate(pointManage.getStartDate()));
		pointHistory.setExpireDate(DateUtility.getDate(pointManage.getEndDate()));

		pointHistory.setRegisterUser(serviceBean.getLoginUserCd());
		pointHistory.setRegisterDate(new Timestamp(System.currentTimeMillis()));
		pointHistory.setUpdateUser(serviceBean.getLoginUserCd());
		pointHistory.setUpdateDate(new Timestamp(System.currentTimeMillis()));

		pointHistoryRepository.save(pointHistory);
		return pointHistory;
	}

	/**
	 * 予約登録 ポイント管理
	 * 
	 * @param serviceBean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Transactional
	private TblPointManage updateMemberPointManage(MemberReservationServiceBean serviceBean,
			MemberReservationManage pointManage, int consomedPoint) {

		TblPointManagePK pk = new TblPointManagePK();
		pk.setId(pointManage.getPointManageId());
		pk.setMemberCode(serviceBean.getMemberCode());

		TblPointManage tblPointManage = pointManageRepository.findById(pk);

		tblPointManage.setConsumedPoint(tblPointManage.getConsumedPoint() + consomedPoint);
		tblPointManage.setPointAmount(tblPointManage.getPointAmount() - consomedPoint);

		tblPointManage.setUpdateUser(serviceBean.getLoginUserCd());
		tblPointManage.setUpdateDate(new Timestamp(System.currentTimeMillis()));

		pointManageRepository.save(tblPointManage);

		return tblPointManage;
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
	private String updateMemberCanclePointManage(MemberReservationServiceBean serviceBean) {

		// 予約履歴更新
		List<TblPointHistory> list = pointHistoryRepository.findByReservationId(serviceBean.getReservationId());
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
		return serviceBean.getReservationId();
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
	private TblReservation insertReservation(MemberReservationServiceBean serviceBean) {

		TblReservation reservation = new TblReservation();

		reservation.setReservationId(UUID.randomUUID().toString());
		reservation.setReservationNumber(serviceBean.getReservationNumber());
		reservation.setStatus(serviceBean.getStatus());
		reservation.setMemberCode(serviceBean.getMemberCode());
		reservation.setAspCode(serviceBean.getAspCode());
		reservation.setBatNumber(serviceBean.getBatNumber());
		// 予約日
		reservation.setReservationDate(DateUtility.getDate(serviceBean.getReservationDate()));
		reservation.setReservationTime(serviceBean.getReservationTime());
		reservation.setConsumedPoint(serviceBean.getConsumedPoint());
		reservation.setPointCategoryCode(serviceBean.getPointCategoryCode());
		reservation.setPointGrade(serviceBean.getPointGrade());
		reservation.setPenaltyPoint(serviceBean.getPenaltyPoint());

		reservation.setRegisterUser(serviceBean.getLoginUserCd());
		reservation.setRegisterDate(new Timestamp(System.currentTimeMillis()));
		reservation.setUpdateUser(serviceBean.getLoginUserCd());
		reservation.setUpdateDate(new Timestamp(System.currentTimeMillis()));

		reservationRepository.save(reservation);

		return reservation;
	}

	/**
	 * 予約情報更新
	 * 
	 * @param serviceBean
	 * @param reservationId
	 * @return
	 */
	@Transactional
	private TblReservation updateReservation(MemberReservationServiceBean serviceBean) {

		TblReservation reservation = getReservation(serviceBean.getReservationId());

		reservation.setStatus(serviceBean.getStatus());
		reservation.setPointCategoryCode(serviceBean.getPointCategoryCode());
		reservation.setReservationNumber(serviceBean.getReservationNumber());
		reservation.setMemberCode(serviceBean.getMemberCode());
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
	private TblReservation updateReservationCancle(MemberReservationServiceBean serviceBean) {

		TblReservation reservation = getReservation(serviceBean.getReservationId());

		reservation.setStatus(serviceBean.getStatus());
		reservation.setUpdateUser(serviceBean.getLoginUserCd());
		reservation.setUpdateDate(new Timestamp(System.currentTimeMillis()));

		reservationRepository.save(reservation);

		return reservation;
	}

	@Override
	public TblReservation getReservation(String reservationId) {
		return reservationRepository.getOne(reservationId);
	}

	@Override
	public List<MemberReservationManage> getMemberPointManageList(String memberCode, String categoryCode,
			String reservationDate) {
		return reservationMapper.getMemberPointManageList(memberCode, categoryCode, reservationDate);
	}

	@Override
	public TblReservationLimitMaster getMemberReservationLimit(String memberGrade, String gradeCode) {
		return tblReservationLimitMasterRepository.findByIdMemberGradeAndIdGradeCode(memberGrade, gradeCode);
	}

	@Override
	public List<MemberReservationManage> getMemberReservationAllList(String reservationNumber, String memberCode,
			String aspCode, String batNumber, String reservationDate, String reservationTime, String status,
			boolean valide) {
		return reservationMapper.getMemberReservationList(reservationNumber, memberCode, aspCode, batNumber,
				reservationDate, reservationTime, status, valide);
	}
}