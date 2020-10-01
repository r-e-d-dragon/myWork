
package com.enjoygolf24.api.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CdMapService {

	/**
	 * CDマップ作成
	 *
	 * @param codeType コード種類
	 * @return CDマップ
	 */
	public LinkedHashMap<String, String> createMap(String codeType);

	/**
	 * CDマップ作成
	 *
	 * @param codeType コード種類
	 * @return CDマップ
	 */
	public LinkedHashMap<String, String> createMapReverse(String codeType);

	/**
	 * CDマップ作成 includeCds のみから作成
	 *
	 * @param codeType   コード種類
	 * @param includeCds 指定コード
	 * @return CDマップ
	 */
	public LinkedHashMap<String, String> createMapOnlyIncludes(String codeType, String... includeCds);

	/**
	 * CDマップ作成 includeCds のみから作成
	 *
	 * @param codeType   コード種類
	 * @param includeCds 指定コード
	 * @return CDマップ
	 */
	public LinkedHashMap<String, String> createMapOnlyIncludesReverse(String codeType, String... includeCds);

	/**
	 * CDマップ作成 excludeCds を除外
	 *
	 * @param codeType   コード種類
	 * @param excludeCds 指定コード
	 * @return CDマップ
	 */
	public LinkedHashMap<String, String> createMapWithoutExcludes(String codeType, String... excludeCds);

	/**
	 * 未選択リスト作成
	 *
	 * @param sourceMap CDマップ
	 * @return 未選択リスト
	 */
	public LinkedHashMap<String, String> putEmptyFirst(Map<String, String> sourceMap);

	/**
	 * コート名称を取得する
	 *
	 * @param codeType コード種類
	 * @param cd       コード
	 * @return コード名称
	 */
	public String getName(String codeType, String cd);

	/**
	 * 
	 * @param aspCode
	 * @return
	 */
	public LinkedHashMap<String, String> createAspInfoMap();

}
