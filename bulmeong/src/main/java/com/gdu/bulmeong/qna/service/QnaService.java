package com.gdu.bulmeong.qna.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface QnaService {
	public void findQnaList(HttpServletRequest request, Model model);
}
