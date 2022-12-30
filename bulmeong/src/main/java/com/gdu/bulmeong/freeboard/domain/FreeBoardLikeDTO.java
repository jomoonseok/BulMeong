package com.gdu.bulmeong.freeboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreeBoardLikeDTO {
	private int freeNo;
	private String id;
}
