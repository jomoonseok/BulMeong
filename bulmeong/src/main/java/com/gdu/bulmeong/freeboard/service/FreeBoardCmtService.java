package com.gdu.bulmeong.freeboard.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gdu.bulmeong.freeboard.domain.FreeBoardCmtDTO;

public interface FreeBoardCmtService {
	
	
	public Map<String, Object> getCmtCount(int freeNo);
	public Map<String, Object> getCmtList(HttpServletRequest request);
	public Map<String, Object> addCmt(FreeBoardCmtDTO freeCmt);
	

	
}
