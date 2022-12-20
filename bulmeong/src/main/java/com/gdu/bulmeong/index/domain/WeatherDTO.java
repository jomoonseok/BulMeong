package com.gdu.bulmeong.index.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class WeatherDTO {

	private String fcstDate;
	private String fcstTime;
	private String category;
	private String fcstValue;
	private int gubun;  // 1 - 사용
	
}
