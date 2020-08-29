package com.enjoygolf24.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.ZipMaster;

@Service
public interface ZipMasterService {

	public List<ZipMaster> searchAddressByZipCode(String headZip, String tailZip);
}
