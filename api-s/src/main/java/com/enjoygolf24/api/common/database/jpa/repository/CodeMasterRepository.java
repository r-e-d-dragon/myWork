
package com.enjoygolf24.api.common.database.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoygolf24.api.common.database.bean.CodeMaster;

@Repository
public interface CodeMasterRepository extends JpaRepository<CodeMaster, Long> {

	/**
	 * 検索
	 *
	 * @param codeType コード種類
	 * @return コード管理テーブルリスト
	 */
	public List<CodeMaster> findByCodeTypeOrderByCd(String codeType);

	/**
	 * 検索
	 *
	 * @param codeType コード種類
	 * @return コード管理テーブルリスト
	 */
	public List<CodeMaster> findByCodeTypeOrderByCdDesc(String codeType);

	/**
	 * 検索
	 *
	 * @param codeType コード種類
	 * @param cd       コード
	 * @return コード管理テーブル
	 */
	public CodeMaster findByCodeTypeAndCd(String codeType, String cd);

}
