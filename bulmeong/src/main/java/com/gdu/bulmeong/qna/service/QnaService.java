package com.gdu.bulmeong.qna.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface QnaService {
	public void findQnaList(HttpServletRequest request, Model model);
	public int addQuestion(HttpServletRequest request, HttpServletResponse response);
	public void addAnswer(HttpServletRequest request, HttpServletResponse response);
	public int removeQna(int qnaNO);
}
