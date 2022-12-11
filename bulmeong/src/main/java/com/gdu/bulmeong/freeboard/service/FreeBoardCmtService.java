package com.gdu.bulmeong.freeboard.service;

import java.util.Map;

import org.springframework.ui.Model;

public interface FreeBoardCmtService {
	
	public Map<String, Object> getCommentCount(int freeNo);
	public void getFreeCmtList(Model model);


	
}
