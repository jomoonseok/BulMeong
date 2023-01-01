package com.gdu.bulmeong.review.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.freeboard.domain.FreeBoardDTO;
import com.gdu.bulmeong.review.domain.ReviewBoardDTO;

@Mapper
public interface ReviewBoardMapper {
	
	public int reviewListCount();
	public List<ReviewBoardDTO> reviewList(Map<String, Object> map);
	public int updateHit(int reviewNo);
	public int insertReviewBoard(ReviewBoardDTO reviewBoard);
	public List<Map<String, Object>> selectCampcampNofacltNm();
	public ReviewBoardDTO selectReviewBoardByNo(int reviewNo);
	public int updateReviewBoard(ReviewBoardDTO reviewBoard);
	public int updateReviewBrdProfile(ReviewBoardDTO reviewBoard);
	public int deleteReviewBoard(int reviewNo);
}

