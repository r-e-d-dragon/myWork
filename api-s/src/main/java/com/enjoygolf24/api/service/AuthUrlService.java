
package com.enjoygolf24.api.service;

import org.springframework.stereotype.Service;

@Service
public interface AuthUrlService {

	public String generateAuthKey(String memberCode);

}
