package com.gdu.bulmeong.freeboard.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gdu.bulmeong.freeboard.domain.FreeBoardDTO;

public interface FreeBoardService {

	// 1. CRUD 기능
	public void getFreeList(Model model);
	public int increseFreeBoardHit(int freeNo);
	public void addFreeBoard(HttpServletRequest request, HttpServletResponse response);
	public FreeBoardDTO getFreeBoardByNo(int freeNo);
	public void modifyFreeBoard(HttpServletRequest request, HttpServletResponse response);
	public void removeFreeBoard(HttpServletRequest request, HttpServletResponse response);
	
	// 2. 검색 기능
	public void findFreeobard(HttpServletRequest request, Model model);
	
}
