package com.gdu.bulmeong.index.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IndexFreeBoardDTO {
	private int freeNo;
	private String nickname, freeTitle;
	private int freeHit;
	private int likeCount;

}
