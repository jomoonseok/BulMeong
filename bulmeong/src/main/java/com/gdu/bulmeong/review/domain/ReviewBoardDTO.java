package com.gdu.bulmeong.review.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReviewBoardDTO {
	private int reviewNo, campNo, reviewHit, reviewStar;
	private String nickname, reviewTitle, reviewContent, reviewCreateDate, reviewModifiyDate, reviewIp;
}

