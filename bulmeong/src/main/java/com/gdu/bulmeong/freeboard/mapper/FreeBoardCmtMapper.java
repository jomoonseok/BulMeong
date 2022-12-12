package com.gdu.bulmeong.freeboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.freeboard.domain.FreeBoardCmtDTO;

@Mapper
public interface FreeBoardCmtMapper {
	
	public int selectCmtCount(int freeNo);
	public List<FreeBoardCmtDTO> selectCmtList(Map<String, Object> map);
	public int insertCmt(FreeBoardCmtDTO freeCmt);


}
