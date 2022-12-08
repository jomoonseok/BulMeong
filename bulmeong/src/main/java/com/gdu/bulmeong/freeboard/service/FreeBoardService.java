package com.gdu.bulmeong.freeboard.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface FreeBoardService {

	public void getFreeList(Model model);
	public int increseFreeBoardHit(int freeNo);
	public void addFreeBoard(HttpServletRequest request, HttpServletResponse response);
}
