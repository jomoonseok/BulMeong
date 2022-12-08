package com.gdu.bulmeong.freeboard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.bulmeong.freeboard.service.FreeBoardService;

@Controller
public class FreeBoardController {

	@Autowired
	private FreeBoardService freeBoardService;
	
	@GetMapping("/freeboard/list")
	public String list(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);
		freeBoardService.getFreeList(model);
		return "freeboard/list";
	}
	
	@GetMapping("/freeboard/increse/hit")
	public String increseHit(@RequestParam(value="freeNo", required=false, defaultValue="0") int freeNo) {
		int result = freeBoardService.increseFreeBoardHit(freeNo);
		if(result > 0) {
			return "redirect:/freeboard/detail?freeNo=" + freeNo;
		} else {
			return "redirect:/freeboard/list";
		}
	}
	
	@GetMapping("/freeboard/write")
	public String write() {
		return "freeboard/write";
	}
	
	@PostMapping("/freeboard/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		freeBoardService.addFreeBoard(request, response);
	}
	 
	
	
	
	
	
}
