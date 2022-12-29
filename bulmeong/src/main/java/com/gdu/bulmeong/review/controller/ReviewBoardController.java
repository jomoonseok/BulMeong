package com.gdu.bulmeong.review.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.bulmeong.review.service.ReviewBoardService;

@Controller
public class ReviewBoardController {

	@Autowired
	private ReviewBoardService reviewBoardService;
	
	@GetMapping("/reviewboard/list")
	public String requiredLogin_list(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);
		reviewBoardService.getReviewList(model);
		return "reviewboard/list";
	}
	
	@GetMapping("/reviewboard/increse/hit")
	public String increseHit(@RequestParam(value="reviewNo", required=false, defaultValue="0") int reviewNo) {
		int result = reviewBoardService.increseReviewBoardHit(reviewNo);
		if(result > 0) {
			return "redirect:/reviewboard/detail?reviewNo=" + reviewNo;
		} else {
			return "redirect:/reviewboard/list";
		}
	}
	
	@GetMapping("/reviewboard/write")
	public String write() {
		return "reviewboard/write";
	}
	
	@PostMapping("/reviewboard/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		reviewBoardService.addReviewBoard(request, response);
	}
	
	@ResponseBody
	@PostMapping(value="/reviewboard/getFacltNm", produces="application/json")
	public Map<String, Object> getFacltNm() {
		return reviewBoardService.getCampcampNofacltNm();
	}

	
	
	

	
}
