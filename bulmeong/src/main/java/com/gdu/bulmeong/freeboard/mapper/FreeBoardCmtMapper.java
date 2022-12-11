package com.gdu.bulmeong.freeboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.freeboard.domain.FreeBoardCmtDTO;

@Mapper
public interface FreeBoardCmtMapper {
	
	public int selectCommentCount(int freeNo);
	public List<FreeBoardCmtDTO> selectCommentList(Map<String, Object> map);

}
