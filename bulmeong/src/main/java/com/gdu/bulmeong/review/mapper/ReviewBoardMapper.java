package com.gdu.bulmeong.review.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.review.domain.ReviewBoardDTO;

@Mapper
public interface ReviewBoardMapper {
	
	public int reviewListCount();
	public List<ReviewBoardDTO> reviewList(Map<String, Object> map);
	public int updateHit(int reviewNo);

	
}

