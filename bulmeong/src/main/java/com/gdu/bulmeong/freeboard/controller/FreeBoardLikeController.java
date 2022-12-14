package com.gdu.bulmeong.freeboard.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.bulmeong.freeboard.service.FreeBoardLikeService;

@Controller
public class FreeBoardLikeController {

	@Autowired
	private FreeBoardLikeService freeLikeService;
	
	@ResponseBody
	@GetMapping(value="/like/getLikeCheck", produces="application/json")
	public Map<String, Object> getLikeCheck(HttpServletRequest request) {
		return freeLikeService.getLikeCheck(request);
	}
	
	@ResponseBody
	@GetMapping(value="/like/getLikeCount", produces="application/json")
	public Map<String, Object> getLikeCount(int freeNo) {
		return freeLikeService.getLikeCount(freeNo);
	}
	
	@ResponseBody
	@GetMapping(value="/like/mark", produces="application/json")
	public Map<String, Object> mark(HttpServletRequest request) {
		return freeLikeService.mark(request);
	}

	

	
}
