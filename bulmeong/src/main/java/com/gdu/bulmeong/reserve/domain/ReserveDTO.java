package com.gdu.bulmeong.reserve.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReserveDTO {
	
	private int reserveNo;
	private int campNo;
	private int tentNo;
	private String id;
	private int reserveSum;
	private String reserveDate;
	private int reserveState;      // 예약 대기 0, 결제 완료 1, 취소 2
	private String reserveCancel;
	
	
	
}
