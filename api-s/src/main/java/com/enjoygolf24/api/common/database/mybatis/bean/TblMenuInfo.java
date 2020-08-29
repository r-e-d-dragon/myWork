package com.enjoygolf24.api.common.database.mybatis.bean;

import com.enjoygolf24.api.common.database.bean.TblMenu;

import lombok.Data;

@Data
public class TblMenuInfo extends TblMenu {

	/*** シリアルID */
	private static final long serialVersionUID = -5677331318027537757L;

	/** レーベル */
	private Long level;

	/** 全体パース */
	private String[] path;

	/** 親IDパース */
	private String[] parentPath;

	/** 全体パース文字列 */
	private String[] pathString;

}
