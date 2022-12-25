package com.gdu.bulmeong.faq.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.bulmeong.faq.service.FaqService;

@Controller
public class FaqController {

	@Autowired 
	private FaqService faqService;
	 
	@GetMapping("/faq/list")
	public String faqList(HttpServletRequest request, Model model) {
		faqService.loadFaqList(request, model);
		return "faq/list";
	}
	
	@GetMapping("/faq/write")
	public String adminOnly_writeHtml() {
		return "faq/write";
	}
	
	@PostMapping("/faq/add")
	public String addFaq(HttpServletRequest request) {
		faqService.addFaq(request);
		return "redirect:/faq/list";
	}
	
}
