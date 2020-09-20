package com.enjoygolf24.api.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.code.PointCategoryCd;
import com.enjoygolf24.api.common.code.ReservationStatusCd;
import com.enjoygolf24.api.common.database.bean.MstPenaltyPoint;
import com.enjoygolf24.api.common.database.bean.MstReservationLimit;
import com.enjoygolf24.api.common.database.bean.TblPointHistory;
import com.enjoygolf24.api.common.database.bean.TblPointManage;
import com.enjoygolf24.api.common.database.bean.TblPointManagePK;
import com.enjoygolf24.api.common.database.bean.TblReservation;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.jpa.repository.MemberRepository;
import com.enjoygolf24.api.common.database.jpa.repository.MstPenaltyPointRepository;
import com.enjoygolf24.api.common.database.jpa.repository.MstReservationLimitRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PointHistoryRepository;
import com.enjoygolf24.api.common.database.jpa.repository.PointManageRepository;
import com.enjoygolf24.api.common.database.jpa.repository.ReservationRepository;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.common.database.mybatis.bean.PointManage;
import com.enjoygolf24.api.common.database.mybatis.bean.ReservationPointTimeTableInfo;
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
	MstReservationLimitRepository mstReservationLimitRepository;

	@Autowired
	MstPenaltyPointRepository mstPenaltyPointRepository;

	@Override
	public List<ReservationPointTimeTableInfo> getViewReservationPonitTimeTableInfo(Date dateTime,
			Date validateStartTerm) {
		return reservationMapper.getViewReservationPoitTimeTableInfo(dateTime, validateStartTerm);
	}

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
		if (PointCategoryCd.MONTHLY_POINT.equals(serviceBean.getPointCategoryCode())) {
			List<PointManage> pointList = getMemberPointManageList(serviceBean.getMemberCode(),
					PointCategoryCd.MONTHLY_POINT, serviceBean.getReservationDate());
			// TODO 当月分は当月のみ使用、ポイントがあっても開始日か過ぎてないと使えない。
			// TODO 来月は、来月の使用ポイントから計算
			Optional<PointManage> mPoint = pointList.stream()
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
			List<PointManage> pointList = getMemberPointManageList(serviceBean.getMemberCode(),
					PointCategoryCd.EVENT_POINT, serviceBean.getReservationDate());

			int consumedPoint = serviceBean.getConsumedPoint();
			for (PointManage point : pointList) {
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
		TblReservation reservation = updateReservationCancle(serviceBean);

		if (!PointCategoryCd.ADMIN_POINT.equals(reservation.getPointCategoryCode())) {
			// 取消、ポイント戻し処理
			updateMemberCanclePointManage(serviceBean);
		}

		return serviceBean.getReservationId();
	}

	/**
	 * 予約履歴登録
	 * 
	 * @param serviceBean
	 * @return
	 */
	@Transactional
	private TblPointHistory insertPointHistory(MemberReservationServiceBean serviceBean, PointManage pointManage,
			String reservationId, int consomedPoint) {

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
	private TblPointManage updateMemberPointManage(MemberReservationServiceBean serviceBean, PointManage pointManage,
			int consomedPoint) {

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
		int penaltyPoint = serviceBean.getPenaltyPoint();

		for (TblPointHistory history : list) {
			if (penaltyPoint > 0) {
				history.setConsumedPoint(-history.getConsumedPoint() + penaltyPoint);
				penaltyPoint = -history.getConsumedPoint() + penaltyPoint;
			} else {
				history.setConsumedPoint(-history.getConsumedPoint());
			}
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
		reservation.setGradeTypeCd(serviceBean.getGradeTypeCd());
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

		// 管理者一括予約
		if (PointCategoryCd.ADMIN_POINT.equals(reservation.getPointCategoryCode())) {
			reservationRepository.save(reservation);
			return reservation;
		}

		// ペナルティー情報取得
		MstPenaltyPoint penalty = mstPenaltyPointRepository.findByGradeCodeAndValidateStartTermLessThanEqual(
				reservation.getGradeTypeCd(), DateUtility.getCurrentTimestamp());
		if (penalty != null) {
			int reservationTime = Integer.valueOf(reservation.getReservationTime().substring(0, 2));
			LocalDateTime reservationDateTime = reservation.getReservationDate().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDateTime().plusHours(reservationTime)
					.minusHours(penalty.getLimitTime());
			LocalDateTime limitDateTime = DateUtility.getCurrentTimestamp().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDateTime();
			// ペナルティー発生
			if (reservationDateTime.compareTo(limitDateTime) < 0) {
				reservation
						.setConsumedPoint(reservation.getConsumedPoint() - penalty.getPenaltyPointAmount().intValue());
				reservation.setPenaltyPoint(penalty.getPenaltyPointAmount().intValue());

				// ペナルティー設定
				serviceBean.setPenaltyPoint(penalty.getPenaltyPointAmount().intValue());
			}
		}

		reservationRepository.save(reservation);

		return reservation;
	}

	@Override
	public TblReservation getReservation(String reservationId) {
		return reservationRepository.getOne(reservationId);
	}

	@Override
	public List<PointManage> getMemberPointManageList(String memberCode, String categoryCode, String reservationDate) {
		return reservationMapper.getMemberPointManageList(memberCode, categoryCode, reservationDate);
	}

	@Override
	public MstReservationLimit getMemberReservationLimit(String memberTypeCode, Date reservationDate) {
		return mstReservationLimitRepository.findByMemberTypeCdAndValidateStartTermLessThanEqual(memberTypeCode,
				reservationDate);
	}

	@Override
	public List<MemberReservationManage> getMemberReservationAllList(String reservationNumber, String memberCode,
			String aspCode, String batNumber, String reservationDate, String reservationTime, String status,
			boolean valide) {
		return reservationMapper.getMemberReservationList(reservationNumber, memberCode, aspCode, batNumber,
				reservationDate, reservationTime, status, valide);
	}

	@Override
	public MemberReservationManage getMemberReservationInfo(String memberCode, String reservationDate) {

		MemberReservationManage reservation = new MemberReservationManage();
		// 会員情報取得
		TblUser member = memberRepository.findByMemberCode(memberCode);
		reservation.setTblUser(member);

		// 月ポイント情報取得
		List<PointManage> totMonPointList = getMemberPointManageList(memberCode, PointCategoryCd.MONTHLY_POINT, null);
		List<PointManage> validMonPointList = getMemberPointManageList(memberCode, PointCategoryCd.MONTHLY_POINT,
				reservationDate);
		// イベントポイント情報取得
		List<PointManage> totEvtPointList = getMemberPointManageList(memberCode, PointCategoryCd.EVENT_POINT, null);
		List<PointManage> validEvtPointList = getMemberPointManageList(memberCode, PointCategoryCd.EVENT_POINT,
				reservationDate);

		reservation.setTotalMonthlyPoint(totMonPointList.stream().mapToInt(x -> x.getPointAmount()).sum());
		reservation.setValidMonthlyPoint(validMonPointList.stream().mapToInt(x -> x.getPointAmount()).sum());

		reservation.setTotalEventPoint(totEvtPointList.stream().mapToInt(x -> x.getPointAmount()).sum());
		reservation.setValidEventPoint(validEvtPointList.stream().mapToInt(x -> x.getPointAmount()).sum());

		List<MemberReservationManage> reservationList = getMemberReservationAllList(null, memberCode, null, null, null,
				null, ReservationStatusCd.STATUS_FIXED, true);
		reservation.setReservationList(reservationList);

		reservation.setMonthlyReservationCount(
				reservationList.stream().filter(p -> p.getPointCategoryCode().equals(PointCategoryCd.MONTHLY_POINT))
						.collect(Collectors.toList()).size());
		reservation.setEventReservationCount(
				reservationList.stream().filter(p -> p.getPointCategoryCode().equals(PointCategoryCd.EVENT_POINT))
						.collect(Collectors.toList()).size());

		// 予約制限情報取得
		MstReservationLimit master = getMemberReservationLimit(member.getMemberTypeCd(),
				DateUtility.getDate(reservationDate));
		if (master != null) {
			reservation.setLimitReservationCount(master.getReservationLimit());
			reservation.setLimitEventReservationCount(master.getEventLimit());
			reservation.setLimitMonthlyReservationCount(master.getMonthlyLimit());
			reservation.setLimitReservationPoint(master.getMaxReservationPoint());
		} else {
			reservation.setLimitReservationCount(0);
			reservation.setLimitEventReservationCount(0);
			reservation.setLimitMonthlyReservationCount(0);
			reservation.setLimitReservationPoint(0);
		}

		return reservation;
	}
}