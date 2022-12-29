package com.gdu.bulmeong.reserve.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class TentDTO {
	
	private int tentNo;
	private int campNo;
	private int tentCategory;
	private String tentName;
	private int tentMaxCount;
	private String tentReserveDay;
	private int tentSum;
	private int tentState;
	private String tentImage;
	private int jjim;
	
}
