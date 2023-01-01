package com.gdu.bulmeong.qna.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QnaDTO {
	private int qnaNo;
	private String id;
	private String qnaTitle;
	private String qnaContent;
	private String qnaRe;
	private String qnaCreateDate;
	private String qnaModifyDate;
	private int qnaState;
	private String qnaIp;
	private int qnaGroupNo;
	private int depth;
}
