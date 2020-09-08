
package com.enjoygolf24.api.service;

import org.springframework.stereotype.Service;

@Service
public interface CsvFileReadService {

	public boolean csvFileRead(String fileType, String filename);

}
