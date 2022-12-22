package com.gdu.bulmeong.freeboard.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface FreeBoardLikeService {
	
	public Map<String, Object> getLikeCheck(HttpServletRequest request);
	public Map<String, Object> getLikeCount(int freeNo);
	public Map<String, Object> mark(HttpServletRequest request);
	


	
	
	
}
