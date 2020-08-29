package com.enjoygolf24.api.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.bean.TblUser;
import com.enjoygolf24.api.common.database.jpa.repository.AspRepository;
import com.enjoygolf24.api.common.database.mybatis.repository.AspMapper;
import com.enjoygolf24.api.common.utility.CodeGeneratorUtility;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.service.AspService;
import com.github.pagehelper.PageHelper;

@Service
public class AspServiceImpl implements AspService {

	@Autowired
	HttpSession session;

	@Autowired
	AspMapper aspMapper;

	@Autowired
	CodeGeneratorUtility codeGenerator;

	@Autowired
	AspRepository aspRepository;

	@Override
	public List<TblAsp> getAspListAll(int pageNo, int pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		return aspMapper.getAspList(null, null, null);
	}

	@Override
	public List<TblAsp> getAspList(String aspName, String aspCode, String address, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return aspMapper.getAspList(aspName, aspCode, address);
	}

	@Override
	public TblAsp register(TblAsp aspBean) {

		TblUser user = LoginUtility.getLoginUser();

		aspBean.setAspCode(codeGenerator.getAspCode(aspRepository.count()));
		aspBean.setCreateUser(user.getMemberCode());
		aspBean.setCreateDate(new Timestamp(System.currentTimeMillis()));
		aspBean.setUpdateUser(user.getMemberCode());
		aspBean.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		aspBean.setUpdateSeq(1);
		aspBean.setShopTypeCd("02");
		aspRepository.save(aspBean);

		return aspBean;
	}

	@Override
	public TblAsp getAsp(String aspCode) {
		return aspRepository.findByAspCode(aspCode);
	}

	@Override
	public TblAsp getAspByName(String aspName) {
		return aspRepository.findByAspName(aspName);
	}

	@Override
	public TblAsp modify(TblAsp aspBean) {
		TblUser user = LoginUtility.getLoginUser();

		TblAsp origBean = getAsp(aspBean.getAspCode());
		aspBean.setCreateUser(origBean.getCreateUser());
		aspBean.setCreateDate(origBean.getCreateDate());

		aspBean.setUpdateUser(user.getMemberCode());
		aspBean.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		aspBean.setUpdateSeq(aspBean.getUpdateSeq() + 1);
		aspBean.setShopTypeCd(origBean.getShopTypeCd());

		aspRepository.save(aspBean);
		return aspBean;
	}
}
