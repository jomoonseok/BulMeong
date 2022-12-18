package com.gdu.bulmeong.freeboard.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gdu.bulmeong.freeboard.domain.FreeBoardCmtDTO;

public interface FreeBoardCmtService {
	
	// 1. 댓글
	public Map<String, Object> getCmtCount(int freeNo);
	public Map<String, Object> getCmtList(HttpServletRequest request);
	public Map<String, Object> addCmt(FreeBoardCmtDTO freeCmt);
	public Map<String, Object> removeCmt(int freeCmtNo);
	public Map<String, Object> modifyCmt(FreeBoardCmtDTO freeCmt);
	
	// 2. 대댓글
	public Map<String, Object> addReply(FreeBoardCmtDTO freeCmtReply);

	
}
