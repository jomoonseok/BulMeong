package com.gdu.bulmeong.freeboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.freeboard.domain.FreeBoardCmtDTO;
import com.gdu.bulmeong.freeboard.domain.FreeSeqDTO;

@Mapper
public interface FreeBoardCmtMapper {
	
	// 1. 댓글
	public int selectCmtCount(int freeNo);
	
	public int updateGroupNo(FreeBoardCmtDTO freeCmt);
	public FreeBoardCmtDTO selectFreeCmtdByNo(int freeCmtNo);
	
	
	public List<FreeBoardCmtDTO> selectCmtList(Map<String, Object> map);
	public int insertCmt(FreeBoardCmtDTO freeCmt);
	public int deleteCmt(int freeCmtNo);
	public int updateCmt(FreeBoardCmtDTO freeCmt);
	
	// 2. 대댓글
	public int updatePreviousReply(FreeBoardCmtDTO freeCmt);
	public int insertCmtReply(FreeBoardCmtDTO freeCmtReply);


}
