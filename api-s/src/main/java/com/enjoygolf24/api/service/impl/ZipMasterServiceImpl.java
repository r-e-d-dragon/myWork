package com.enjoygolf24.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.ZipMaster;
import com.enjoygolf24.api.common.database.jpa.repository.ZipMasterRepository;
import com.enjoygolf24.api.service.ZipMasterService;

@Service
public class ZipMasterServiceImpl implements ZipMasterService {

	@Autowired
	ZipMasterRepository zipMasterRepository;

	@Override
	public List<ZipMaster> searchAddressByZipCode(String headZip, String tailZip) {
		String zipCode = headZip.concat(tailZip);
		return zipMasterRepository.findByZipCode(zipCode);
	}

}
