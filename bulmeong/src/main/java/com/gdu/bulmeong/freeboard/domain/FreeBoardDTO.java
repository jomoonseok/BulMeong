package com.gdu.bulmeong.freeboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreeBoardDTO {
	private int freeNo;
	private String id, nickname, freeTitle, freeContent, freeCreateDate, freeModifiyDate, freeIp, profileImage;
	private int freeHit;


}
