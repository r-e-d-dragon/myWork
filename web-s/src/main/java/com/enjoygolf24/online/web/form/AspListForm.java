package com.enjoygolf24.online.web.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.Kanji;
import com.enjoygolf24.api.common.validator.annotation.PageSize;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;
import com.enjoygolf24.api.common.validator.annotation.Zenkaku;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.common.validator.groups.Search0;
import com.enjoygolf24.api.common.validator.groups.Update0;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class AspListForm implements Serializable {

	/*** シリアル番号 */
	private static final long serialVersionUID = -937204629996741357L;

	@Zenkaku(groups = Search0.class, max = 200)
	@SjisSafe(groups = Search0.class)
	String aspName;

	@Hankaku(groups = Search0.class, max = 20)
	String aspCode;

	@Hankaku(groups = Search0.class, max = 20)
	String managerCode;

	@Zenkaku(groups = Insert0.class, max = 200)
	@Kanji(groups = Insert0.class, max = 200)
	@SjisSafe(groups = Insert0.class)
	String managerName;

	@Zenkaku(groups = Search0.class, max = 200)
	@Kanji(groups = Search0.class, max = 20)
	@SjisSafe(groups = Search0.class)
	String address;

	@NotEmpty(groups = Update0.class)
	@Hankaku(groups = Update0.class, max = 20)
	String modAspCode;

	@PageSize
	int pageSize = DefaultPageSizeUtility.DEFAULT_PAGE_SIZE;

	/** 現在ページ */
	int pageNo = DefaultPageSizeUtility.PAGE_FIRST;

	List<TblAsp> aspList;

}
