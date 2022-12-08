package com.gdu.bulmeong.freeboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.freeboard.domain.FreeBoardDTO;

@Mapper
public interface FreeBoardMapper {
	public int selectFreeListCount();
	public List<FreeBoardDTO> selectFreeListByMap(Map<String, Object> map);
	public int updateHit(int freeNo);
	public int insertFreeBoard(FreeBoardDTO freeBoard);
}
