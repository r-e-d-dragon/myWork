
package com.enjoygolf24.online.web.json;

import java.util.Map;

import lombok.Data;

@Data
public class JsonCommonResponse {

    /** バリデート*/
	protected boolean validated;

    /** エラーメッセージ*/
	protected Map<String, String> errorMessages;

}
