package com.gdu.bulmeong.faq.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface FaqService {
	public void loadFaqList(HttpServletRequest request, Model model);
	public int addFaq(HttpServletRequest request);
	public int removeFaq(int faqNo);
}
