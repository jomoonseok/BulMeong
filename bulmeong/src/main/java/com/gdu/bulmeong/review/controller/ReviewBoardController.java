package com.gdu.bulmeong.review.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.review.service.ReviewBoardService;

@Controller
public class ReviewBoardController {

	@Autowired
	private ReviewBoardService reviewService;
	
	@GetMapping("/reviewboard/list")
	public String requiredLogin_list(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);
		reviewService.getReviewList(model);
		return "reviewboard/list";
	}

	

	
}
