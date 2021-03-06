
package com.enjoygolf24.api.service.bean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.enjoygolf24.api.common.code.MailSectionCd;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.bean.TblMailMaster;
import com.enjoygolf24.api.common.database.bean.TblReservation;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.bean.TblUserPre;
import com.enjoygolf24.api.common.utility.DateUtility;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EmailSendServiceBean {

	public static final String AUTH_URL = "${AUTH_URL}";

	public static final String MEMBER_NAME = "${MEMBER_NAME}";

	public static final String MEMBER_CODE = "${MEMBER_CODE}";

	public static final String API_NAME = "${API_NAME}";

	public static final String PHONE_NUMBER = "${PHONE_NUMBER}";

	public static final String EMAIL_ADDRESS = "${EMAIL_ADDRESS}";

	public static final String MEMO = "${MEMO}";

	public static final String RESERVATION_NUMBER = "${RESERVATION_NUMBER}";
	public static final String RESERVATION_DATE = "${RESERVATION_DATE}";
	public static final String RESERVATION_TIME = "${RESERVATION_TIME}";
	public static final String BAT_NUMBER = "${BAT_NUMBER}";

	@Setter(AccessLevel.NONE)
	private String mailSectionCd;

	@Setter(AccessLevel.NONE)
	private String mailSectionCdSub = null;

	@Setter(AccessLevel.NONE)
	private String targetName;

	@Setter(AccessLevel.NONE)
	private String targetEmailAddress;

	@Setter(AccessLevel.NONE)
	private String senderName;

	@Setter(AccessLevel.NONE)
	private String senderEmailAddress;

	@Setter(AccessLevel.NONE)
	private String subject;

	@Setter(AccessLevel.NONE)
	private String body;

	@Setter(AccessLevel.NONE)
	private String bodySub = null;

	private String mailTypeCd;

	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private Map<String, String> replaces;

	public EmailSendServiceBean(String mailSectionCd) {
		this.mailSectionCd = mailSectionCd;
		replaces = new HashMap<>();
	}

	public EmailSendServiceBean(String mailSectionCd, TblUser member, String authUrl) {
		this(mailSectionCd);
		targetName = member.getLastName() + member.getFirstName() + "様";
		this.targetEmailAddress = member.getEmail();

		putReplace(AUTH_URL, authUrl);
		putReplace(MEMBER_NAME, member.getLastName() + member.getFirstName());
		putReplace(MEMBER_CODE, member.getMemberCode());

	}

	public EmailSendServiceBean(String mailSectionCd, TblUserPre member, TblAsp asp, String memo) {
		this(mailSectionCd);
		if (MailSectionCd.PRE_MEMBER_REGESTERED.equals(mailSectionCd)) {
			targetName = member.getLastName() + member.getFirstName() + "様";
			this.targetEmailAddress = member.getEmail();
		} else {
			targetName = asp.getAspName() + " " + asp.getManagerName();
			this.targetEmailAddress = asp.getAspEmail();
		}

		putReplace(MEMBER_NAME, member.getLastName() + member.getFirstName());
		putReplace(API_NAME, asp.getAspName());
		putReplace(EMAIL_ADDRESS, member.getEmail());
		putReplace(PHONE_NUMBER, member.getPhone());
		putReplace(MEMO, memo);

	}

	/**
	 * 予約登録・取消
	 * 
	 * @param mailSectionCd
	 * @param reservation
	 * @param member
	 * @param asp
	 */
	public EmailSendServiceBean(String mailSectionCd, TblReservation reservation, TblUser member, TblAsp asp) {
		this(mailSectionCd);
		this.targetEmailAddress = member.getEmail();

		putReplace(MEMBER_NAME, member.getLastName() + " " + member.getFirstName());
		putReplace(API_NAME, asp.getAspName());
		putReplace(RESERVATION_NUMBER, reservation.getReservationNumber());
		putReplace(RESERVATION_DATE, reservation.getRegisterDate());
		putReplace(RESERVATION_TIME, reservation.getReservationTime());
		putReplace(BAT_NUMBER, reservation.getBatNumber());
	}

	public void setMailMaster(TblMailMaster mailMaster) {
		senderName = mailMaster.getSenderName();
		senderEmailAddress = mailMaster.getSenderMailAddress();
		subject = mailMaster.getSubject();
		body = getReplacedBody(mailMaster.getBody());
	}

	public void setMailMasterSub(TblMailMaster mailMaster) {
		bodySub = getReplacedBody(mailMaster.getBody());
	}

	public String[] getTargetEmailAddressArray() {
		if (targetEmailAddress.contains("|")) {
			return targetEmailAddress.split("\\|");
		} else {
			return new String[] { targetEmailAddress };
		}
	}

	private void putReplace(String key, String value) {
		replaces.put(key, value);
	}

	private void putReplace(String key, Date value) {
		replaces.put(key, DateUtility.toSlashDateString(value));
	}

	private void putReplace(String key, Timestamp value) {
		replaces.put(key, DateUtility.toSlashTimestampString(value, DateUtility.WITHOUT_SECOND));
	}

	private void putReplace(String key, Integer value, String... append) {
		replaces.put(key, String.format("%,d", value) + (append.length > 0 ? append[0] : ""));
	}

	private String getReplacedBody(String body) {
		String replaced = body;
		for (Entry<String, String> e : replaces.entrySet()) {
			replaced = replaced.replace(e.getKey(), e.getValue());
		}

		return replaced;
	}

}
