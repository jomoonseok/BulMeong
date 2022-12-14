package com.gdu.bulmeong.pay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PayDTO {
	private int payNo;
	private int reserveNo;
	private String payName;
	private String payDate;
}
