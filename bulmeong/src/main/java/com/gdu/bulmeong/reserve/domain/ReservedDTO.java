package com.gdu.bulmeong.reserve.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservedDTO {
	private int tentNo;
	private int campNo;
	private int tentCategory;
	private String tentName;
	private int tentMaxCount;
	private String tentSum;
	private String tentImage;      
	private String id;
	private String reserveName;
	private String reserveCount;
	private String reserveBeginDate;
	private String reserveEndDate;
	private String reserveSum;
	private String reserveState;
	
	
	
}
