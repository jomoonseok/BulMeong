package com.gdu.bulmeong.reserve.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.bulmeong.reserve.service.ReserveService;

@Controller
public class ReserveController {

	@Autowired 
	private ReserveService reserveService;
	 
	@GetMapping("/reserve")
	public String calendar() {
		return "/reserve/calendar";
	}
	
	@ResponseBody
	@GetMapping("/reserve/list")
	public Map<String, Object> tentList(@RequestParam(value="campNo") int campNo) {
		return reserveService.getTentByCampNo(campNo);
	}
	
	
	
	
}
