package com.gdu.bulmeong.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.review.service.ReviewBoardService;

@Controller
public class ReviewBoardController {

	@Autowired
	private ReviewBoardService reviewService;
	
	@GetMapping("/reviewboard/list")
	public String list() {
		return "reviewboard/list";
	}

	

	
}
