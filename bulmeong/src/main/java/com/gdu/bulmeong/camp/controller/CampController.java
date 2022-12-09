package com.gdu.bulmeong.camp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.camp.service.CampService;

@Controller
public class CampController {

	@Autowired 
	private CampService campService;
	 
	@GetMapping("/camp")
	public String index() {
		campService.getCampInfo();
		return "camp/list";
	}
	
	
	
	
	
}
