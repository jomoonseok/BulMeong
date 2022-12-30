package com.gdu.bulmeong.freeboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreeBoardCmtDTO {	
	private int freeCmtNo, freeNo, freeSeq;
	private String id, nickname, freeCmtContent, freeCmtCreateDate, freeCmtModifyDate, freeCmtIp, profileImage;
	private int freeCmtState, freeCmtDepth, freeGroupNo, freeGroupOrder, cmtCnt;
}
