package com.enjoygolf24.online.web.test.openCsv.data;

import com.univocity.parsers.annotations.FixedWidth;
import com.univocity.parsers.annotations.Parsed;

import lombok.Data;

@Data
public class ProfileByFieldName {
	@Parsed(index = 0, field = "dataType")
	@FixedWidth(from = 0, to = 1)
	private String dataType;
	@Parsed(index = 1, field = "totalCount")
	@FixedWidth(from = 2, to = 7)
	private String totalCount;
	@Parsed(index = 2, field = "totalAmount")
	@FixedWidth(from = 8, to = 19)
	private String totalAmount;
	@Parsed(index = 3, field = "transferredCount")
	@FixedWidth(6)
	private String transferredCount;
	@Parsed(index = 4, field = "transferredAmount")
	@FixedWidth(12)
	private String transferredAmount;
	@Parsed(index = 5, field = "transferImpossibleCount")
	@FixedWidth(6)
	private String transferImpossibleCount;
	@Parsed(index = 6, field = "transferImpossibleAmount")
	@FixedWidth(12)
	private String transferImpossibleAmount;
	@Parsed(index = 7, field = "filler")
	@FixedWidth(65)
	private String filler;

//	@Override
//	public String toString() {
//		return "ProfileByFieldPosition{" + "dataType=" + dataType + ", totalCount='" + totalCount + '\''
//				+ ", totalAmount=" + totalAmount + ", transferredCount='" + transferredCount + ", transferredAmount='"
//				+ transferredAmount + ", transferImpossibleCount='" + transferImpossibleCount
//				+ ", transferImpossibleAmount='" + transferImpossibleAmount + '\'' + ", followers=" + filler + '}';
//	}
}
