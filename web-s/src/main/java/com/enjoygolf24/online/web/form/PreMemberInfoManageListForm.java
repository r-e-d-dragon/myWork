package com.enjoygolf24.online.web.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

import com.enjoygolf24.api.common.code.CodeTypeCd;
import com.enjoygolf24.api.common.code.PreMemberUseFlagCd;
import com.enjoygolf24.api.common.database.bean.TblUserPre;
import com.enjoygolf24.api.common.utility.DefaultPageSizeUtility;
import com.enjoygolf24.api.common.validator.annotation.CodeMaster;
import com.enjoygolf24.api.common.validator.annotation.Hankaku;
import com.enjoygolf24.api.common.validator.annotation.PageSize;
import com.enjoygolf24.api.common.validator.annotation.SjisSafe;
import com.enjoygolf24.api.common.validator.annotation.Telephone;
import com.enjoygolf24.api.common.validator.groups.Insert0;
import com.enjoygolf24.api.common.validator.groups.Search0;

import lombok.Data;

@Data
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class PreMemberInfoManageListForm implements Serializable {

	private static final long serialVersionUID = -937204629996741357L;

	@Hankaku(groups = Search0.class, max = 20)
	String memberCode;

	@SjisSafe(groups = Search0.class)
	private String name;

	@Telephone(groups = Search0.class)
	private String phone;

	@Size(groups = Search0.class, max = 80)
	private String email;

	String selectedMemberCode;

	String aspCode;

	String hasChanged;

	@Size(groups = Insert0.class, max = 2)
	@CodeMaster(groups = Insert0.class, code = CodeTypeCd.PRE_MEMBER_USE_FLAG_CD, except = {
			PreMemberUseFlagCd.PRE_MEMBER_USE_FLAG_TRANSFERED })
	String useFlagCd;

	@PageSize
	int pageSize = DefaultPageSizeUtility.DEFAULT_PAGE_SIZE;

	/** 現在ページ */
	int pageNo = DefaultPageSizeUtility.PAGE_FIRST;

	List<TblUserPre> memberList;

}
