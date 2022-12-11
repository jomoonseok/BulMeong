package com.gdu.bulmeong.reserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.reserve.service.ReserveService;

@Controller
public class ReserveController {

	@Autowired 
	private ReserveService reserveService;
	 
	@GetMapping("/reserve")
	public String calendar() {
		return "/reserve/calendar";
	}
	
	
	
	
}
