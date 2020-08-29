
package com.enjoygolf24.api.common.utility;

import org.springframework.stereotype.Component;

@Component
public class CodeGeneratorUtility {

	public String getAspCode(Long cnt) {

		return String.format("A%010d", cnt + 1);
	}

	public String getMemberCode(Long cnt) {
		return String.format("M%010d", cnt + 1);
	}

	public String getPreMemberCode(Long cnt) {
		return String.format("P%010d", cnt + 1);
	}

}
