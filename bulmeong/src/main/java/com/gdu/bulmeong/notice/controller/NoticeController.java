package com.gdu.bulmeong.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.ex.service.BulMeongService;

@Controller
public class NoticeController {

	@Autowired 
	private BulMeongService bulMeongService;
	 
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	
	
}
