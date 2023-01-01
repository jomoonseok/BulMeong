package com.gdu.bulmeong.review.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gdu.bulmeong.review.domain.ReviewBoardDTO;

public interface ReviewBoardService {

	public void getReviewList(Model model);
	
	public int increseReviewBoardHit(int reviewNo);
	public void addReviewBoard(HttpServletRequest request, HttpServletResponse response);
	public Map<String, Object> getCampcampNofacltNm();
	
	public ReviewBoardDTO getReviewBoardByNo(int reviewNo);
	public void modifyReviewBoard(HttpServletRequest request, HttpServletResponse response);
	public void removeReviewBoard(HttpServletRequest request, HttpServletResponse response);
}
