package com.gdu.bulmeong.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.bulmeong.ex.service.BulMeongService;

@Controller
public class BulMeongController {

	@Autowired 
	private BulMeongService bulMeongService;
	 
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	
	
	
	
}
