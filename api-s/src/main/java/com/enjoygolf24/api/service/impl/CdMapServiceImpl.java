
package com.enjoygolf24.api.service.impl;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.common.code.ShopTypeCd;
import com.enjoygolf24.api.common.database.bean.CodeMaster;
import com.enjoygolf24.api.common.database.bean.TblAsp;
import com.enjoygolf24.api.common.database.jpa.repository.AspRepository;
import com.enjoygolf24.api.common.database.jpa.repository.CodeMasterRepository;
import com.enjoygolf24.api.common.utility.LoginUtility;
import com.enjoygolf24.api.service.CdMapService;

@Service
public class CdMapServiceImpl implements CdMapService {

	/** コード管理リポジトリ */
	@Autowired
	private CodeMasterRepository codeMasterRepository;

	@Autowired
	private AspRepository aspRepository;

	/**
	 * CDマップ作成
	 *
	 * @param codeType コード種類
	 * @return CDマップ
	 */
	@Override
	public LinkedHashMap<String, String> createMap(String codeType) {
		LinkedHashMap<String, String> map = createMap(codeType, c -> true);
		return map;
	}

	/**
	 * CDマップ作成
	 *
	 * @param codeType コード種類
	 * @return CDマップ
	 */
	@Override
	public LinkedHashMap<String, String> createMapReverse(String codeType) {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		codeMasterRepository.findByCodeTypeOrderByCdDesc(codeType).stream()
				.forEach(e -> map.put(e.getCd(), e.getName()));
		return map;
	}

	/**
	 * CDマップ作成 includeCds のみから作成
	 *
	 * @param codeType   コード種類
	 * @param includeCds 指定コード
	 * @return CDマップ
	 */
	@Override
	public LinkedHashMap<String, String> createMapOnlyIncludes(String codeType, String... includeCds) {
		List<String> includeList = Arrays.asList(includeCds);
		Predicate<CodeMaster> includeFilter = c -> includeList.contains(c.getCd());
		LinkedHashMap<String, String> map = createMap(codeType, includeFilter);
		return map;
	}

	/**
	 * CDマップ作成 includeCds のみから作成
	 *
	 * @param codeType   コード種類
	 * @param includeCds 指定コード
	 * @return CDマップ
	 */
	@Override
	public LinkedHashMap<String, String> createMapOnlyIncludesReverse(String codeType, String... includeCds) {
		List<String> includeList = Arrays.asList(includeCds);
		Predicate<CodeMaster> includeFilter = c -> includeList.contains(c.getCd());
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		codeMasterRepository.findByCodeTypeOrderByCdDesc(codeType).stream().filter(includeFilter)
				.forEach(e -> map.put(e.getCd(), e.getName()));
		return map;
	}

	/**
	 * CDマップ作成 excludeCds を除外
	 *
	 * @param codeType   コード種類
	 * @param excludeCds 指定コード
	 * @return CDマップ
	 */
	@Override
	public LinkedHashMap<String, String> createMapWithoutExcludes(String codeType, String... excludeCds) {
		List<String> excludeList = Arrays.asList(excludeCds);
		Predicate<CodeMaster> excludeFilter = c -> !excludeList.contains(c.getCd());
		LinkedHashMap<String, String> map = createMap(codeType, excludeFilter);
		return map;
	}

	/**
	 * CDマップ作成
	 *
	 * @param codeType  コード種類
	 * @param mapFilter フィルタ
	 * @return CDマップ
	 */
	private LinkedHashMap<String, String> createMap(String codeType, Predicate<CodeMaster> mapFilter) {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		codeMasterRepository.findByCodeTypeOrderByCd(codeType).stream().filter(mapFilter)
				.forEach(e -> map.put(e.getCd(), e.getName()));
		return map;
	}

	/**
	 * 未選択リスト作成
	 *
	 * @param sourceMap CDマップ
	 * @return 未選択リスト
	 */
	@Override
	public LinkedHashMap<String, String> putEmptyFirst(Map<String, String> sourceMap) {
		LinkedHashMap<String, String> distMap = new LinkedHashMap<>();
		distMap.put("", null);
		sourceMap.entrySet().forEach(e -> distMap.put(e.getKey(), e.getValue()));
		return distMap;
	}

	/**
	 * コート名称を取得する
	 *
	 * @param codeType コード種類
	 * @param cd       コード
	 * @return コード名称
	 */
	public String getName(String codeType, String cd) {
		CodeMaster codeMaster = codeMasterRepository.findByCodeTypeAndCd(codeType, cd);
		if (codeMaster != null) {
			return codeMaster.getName();
		}
		return "";
	}

	@Override
	public LinkedHashMap<String, String> createAspInfoMap() {

		TblAsp aspInfo = LoginUtility.getLoginCompanyInfo();
		LinkedHashMap<String, String> distMap = new LinkedHashMap<String, String>();

		if (ShopTypeCd.HONBU.contentEquals(aspInfo.getShopTypeCd())) {
			aspRepository.findAll().stream().forEach(e -> distMap.put(e.getAspCode(), e.getAspName()));

		} else {
			distMap.put(aspInfo.getAspCode(), aspInfo.getAspName());
		}
		return distMap;
	}

}
