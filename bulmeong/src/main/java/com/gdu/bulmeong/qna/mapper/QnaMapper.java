package com.gdu.bulmeong.qna.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.qna.domain.QnaDTO;

@Mapper
public interface QnaMapper {
	public int selectQnaCount();
	public List<QnaDTO> selectQnaList(Map<String, Object> map);
}
