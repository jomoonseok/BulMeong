package com.gdu.bulmeong.users.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MyPageReservedDTO {
	private String facltNm;
	private String addr1;
	private int reserveNo;
	private int campNo;
	private int tentNo;
	private String reserveName;
	private String reserveCount;
	private String reserveBeginDate;
	private String EndDate;
	private String reserveSum;
	private String tentImage;      
	private int tentCategory;
	private String tentName;
	private int tentMaxCount;
	
}
