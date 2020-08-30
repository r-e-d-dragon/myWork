package com.enjoygolf24.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.service.bean.MemberReservationServiceBean;

@Service
public interface MacroReservationManageService {

	@Transactional
	public List<String> MacroReservationRegister(MemberReservationServiceBean serviceBean);
}
