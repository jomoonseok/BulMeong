package com.gdu.bulmeong.reserve.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.bulmeong.reserve.service.ReserveService;

@Controller
public class ReserveController {

	@Autowired 
	private ReserveService reserveService;
	 
	@GetMapping("/reserve")
	public String calendar() {
		return "reserve/calendar";
	}
	
	@ResponseBody
	@GetMapping("/reserve/list")
	public Map<String, Object> tentList(@RequestParam(value="campNo") int campNo) {
		return reserveService.getTentByCampNo(campNo);
	}
	
	/*
	 * @ResponseBody
	 * 
	 * @GetMapping("/reserve/camp_info") public Map<String, Object>
	 * campInfo(@RequestParam(value="campNo") int campNo) { return
	 * reserveService.getCampByCampNo(campNo); }
	 */
	
	
	 @GetMapping("/reserve/pay") 
	 public String reserve_pay(HttpServletRequest request, Model model) { 
		 reserveService.getInfoReserve(request, model); 
		 return "reserve/pay"; 
		 
	 }
	 
	 
	
	
	
	
}
