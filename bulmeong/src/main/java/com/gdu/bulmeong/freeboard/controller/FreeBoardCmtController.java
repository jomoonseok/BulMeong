package com.gdu.bulmeong.freeboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.freeboard.service.FreeBoardCmtService;

@Controller
public class FreeBoardCmtController {

	@Autowired
	private FreeBoardCmtService freeBoardCmtService;
	
	@GetMapping(value="/freecomment/list")
	public String list(HttpServletRequest request, Model model){
		model.addAttribute("request", request);
		freeBoardCmtService.getFreeCmtList(model);
		return "freecomment/list";
	}

	
	
	
	
}
