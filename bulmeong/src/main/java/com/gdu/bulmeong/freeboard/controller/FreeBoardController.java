package com.gdu.bulmeong.freeboard.controller;

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

import com.gdu.bulmeong.freeboard.service.FreeBoardService;

@Controller
public class FreeBoardController {

	@Autowired
	private FreeBoardService freeBoardService;
	
	
	// 1. CRUD 기능
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
	
	@GetMapping("/freeboard/detail")
	public String detail(@RequestParam(value="freeNo", required=false, defaultValue="0") int freeNo, Model model) {
		model.addAttribute("free", freeBoardService.getFreeBoardByNo(freeNo));
		return "freeboard/detail";
	}
	
	@PostMapping("/freeboard/edit")
	public String edit(int freeNo, Model model) {
		model.addAttribute("free", freeBoardService.getFreeBoardByNo(freeNo));
		return "freeboard/edit";
	}
	
	@PostMapping("/freeboard/modify")
	public void modify(HttpServletRequest request, HttpServletResponse response) {
		freeBoardService.modifyFreeBoard(request, response);
	}
	
	
	@PostMapping("/freeboard/remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		freeBoardService.removeFreeBoard(request, response);
	}
	

	
}
