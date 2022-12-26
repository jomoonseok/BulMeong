package com.gdu.bulmeong.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class AdminTentDTO {
	
	private int campNo;
	private String facltNm;
	private int tentNo;
	private String tentName;
	private String tentImage;
	private int tentCategory;
	private int tentMaxCount;
	private String tentReserveDay;
	private int tentSum;
	private int tentState;
	
}
