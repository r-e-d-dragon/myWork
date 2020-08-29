package com.enjoygolf24.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.TblAsp;

@Service
public interface AspService {

	public List<TblAsp> getAspListAll(int pageNo, int pageSize);

	public List<TblAsp> getAspList(String aspName, String aspCode, String address, int pageNo, int pageSize);

	public TblAsp register(TblAsp aspBean);

	public TblAsp getAsp(String aspCode);

	public TblAsp getAspByName(String aspName);

	public TblAsp modify(TblAsp aspBean);
}
