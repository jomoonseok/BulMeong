package com.gdu.bulmeong.freeboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.freeboard.domain.FreeBoardDTO;

@Mapper
public interface FreeBoardMapper {
	
	// 1. CRUD 기능
	public int selectFreeListCount();
	public List<FreeBoardDTO> selectFreeListByMap(Map<String, Object> map);
	public int updateHit(int freeNo);
	public int insertFreeBoard(FreeBoardDTO freeBoard);
	public FreeBoardDTO selectFreeBoardByNo(int freeNo);
	public int updateFreeBoard(FreeBoardDTO freeBoard);
	public int deleteFreeBoard(int freeNo);
	
	// 2. 검색 기능
	public int selectFindFreeboardsCount(Map<String, Object> map);
	public List<FreeBoardDTO> selectFindFreeboard(Map<String, Object> map);
}
