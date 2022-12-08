package com.gdu.bulmeong.notice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NoticeDTO {
	private int noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private int noticeHit;
	private String noticeCreateDate;
	private String noticeModifyDate;
	private String id;
}
