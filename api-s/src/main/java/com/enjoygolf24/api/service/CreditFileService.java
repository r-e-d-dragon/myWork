
package com.enjoygolf24.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CreditFileService {

	public boolean creditFileRead(String fileType, String filename);

	public List<String> creditFileWrite();
}
