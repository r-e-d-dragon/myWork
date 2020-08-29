package com.enjoygolf24.api.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public interface InitPasswordService {

	public boolean aukeyCheck(String authKey);

	public boolean aukeyCheckAdmin(String authKey);

	public boolean memberCodeCheck(String authKey, String memberCode);

	@Transactional
	public void initPassword(String authKey, String memberCode, String password);
}
