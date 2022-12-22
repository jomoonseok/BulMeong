package com.gdu.bulmeong.freeboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.gdu.bulmeong.freeboard.domain.FreeBoardCmtDTO;

public interface FreeBoardCmtService {
	
	// 1. 댓글
	public Map<String, Object> getCmtCount(int freeNo);
	public Map<String, Object> getCmtList(Model model);
	// List<FreeBoardCmtDTO> getCmtLists(int freeNo);
	public Map<String, Object> addCmt(FreeBoardCmtDTO freeCmt);
	public Map<String, Object> removeCmt(int freeCmtNo);
	public Map<String, Object> modifyCmt(FreeBoardCmtDTO freeCmt);
	
	

	
	// 2. 대댓글
	public Map<String, Object> addReply(FreeBoardCmtDTO freeCmtReply);

	
}
