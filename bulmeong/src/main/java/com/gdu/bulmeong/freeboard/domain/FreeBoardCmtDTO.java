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
	private int freeCmtNo, freeNo;
	private String nickname, freeCmtContent, freeCmtCreateDate, freeCmtModifyDate, freeCmtIp;
	private int freeCmtState, freeCmtDepth, freeGroupNo, freeGroupOrder;
}
