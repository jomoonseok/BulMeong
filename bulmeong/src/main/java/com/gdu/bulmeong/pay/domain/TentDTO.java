package com.gdu.bulmeong.pay.domain;

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
	private String tentName;
	private int tentMaxCount;
	private int tentCount;
	private int tentPeriod;
	private int tentSum;
}
