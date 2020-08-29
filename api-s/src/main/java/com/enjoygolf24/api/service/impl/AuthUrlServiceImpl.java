
package com.enjoygolf24.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.enjoygolf24.api.service.AuthUrlService;

@Service
public class AuthUrlServiceImpl implements AuthUrlService {

	@Override
	public String generateAuthKey(String memberCode) {
		List<String> key = new ArrayList<>();
		key.add((new Timestamp(System.currentTimeMillis())).toString() + memberCode);
		key.add(memberCode + (new Timestamp(System.currentTimeMillis())).toString());
		key.add((new Timestamp(System.currentTimeMillis())).toString()
				+ (new Timestamp(System.currentTimeMillis())).toString());
		return String.join("", key.stream().map(e -> DigestUtils.sha256Hex(e)).collect(Collectors.toList()));
	}

}
