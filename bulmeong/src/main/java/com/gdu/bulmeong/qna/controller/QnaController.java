package com.gdu.bulmeong.qna.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.bulmeong.qna.service.QnaService;


@Controller
public class QnaController {

	@Autowired 
	private QnaService qnaService;
	 
	
//	@GetMapping("/")
//	public String index() {
//		return "index";
//	}
	
	@GetMapping("/qna/list")
	public String qnaList(HttpServletRequest request, Model model) {
		qnaService.findQnaList(request, model);
		return "qna/list";
	}
	
	@GetMapping("/popup")
	public String popup() {
		return "qna/popup";
	}
	

	
	
}
