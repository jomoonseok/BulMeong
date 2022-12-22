package com.gdu.bulmeong.faq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.faq.service.FaqService;

@Controller
public class FaqController {

	@Autowired 
	private FaqService faqService;
	 
	@GetMapping("/faq/list")
	public String faqList() {
		return "faq/list";
	}
	
	
	
	
}
