package com.gdu.bulmeong.freeboard.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gdu.bulmeong.freeboard.domain.FreeBoardDTO;

public interface FreeBoardService {

	// 1. CRUD 기능
	
	// 전체 목록
	public void getFreeList(HttpServletRequest request, Model model);
	
	// 검색 목록
	public void getSearchFreeList(HttpServletRequest request, Model model);

	
	public int increseFreeBoardHit(int freeNo);
	public void addFreeBoard(HttpServletRequest request, HttpServletResponse response);
	public FreeBoardDTO getFreeBoardByNo(int freeNo);
	public void modifyFreeBoard(HttpServletRequest request, HttpServletResponse response);
	public void removeFreeBoard(HttpServletRequest request, HttpServletResponse response);
	
	
	
}
