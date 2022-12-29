package com.gdu.bulmeong.review.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface ReviewBoardService {

	public void getReviewList(Model model);
	
	public int increseReviewBoardHit(int reviewNo);
	public void addReviewBoard(HttpServletRequest request, HttpServletResponse response);
	public Map<String, Object> getCampcampNofacltNm();
}
