package com.enjoygolf24.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.MstTimeTable;
import com.enjoygolf24.api.common.database.bean.TblMacroReservationManage;
import com.enjoygolf24.api.common.database.mybatis.bean.MemberReservationManage;
import com.enjoygolf24.api.service.bean.MemberReservationServiceBean;

@Service
public interface MacroReservationManageService {

	@Transactional
	public TblMacroReservationManage MacroReservationRegister(MemberReservationServiceBean serviceBean);

	public List<MemberReservationManage> getMacroReservationList(String aspCode, int pageNo, int pageSize);

	public List<MstTimeTable> getMstTimeTable(String aspCode);

}
