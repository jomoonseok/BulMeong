package com.gdu.bulmeong.qna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.ex.service.BulMeongService;
import com.gdu.bulmeong.qna.service.QNAService;

@Controller
public class QNAController {

	@Autowired 
	private QNAService qnaService;
	 
	
//	@GetMapping("/")
//	public String index() {
//		return "index";
//	}
	
	
	
}
