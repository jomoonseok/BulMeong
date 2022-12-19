package com.gdu.bulmeong.reserve.controller;

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
	@GetMapping("/reserve/campInfo")
	public Map<String, Object> campList(@RequestParam(value="campNo") int campNo) {
		return reserveService.getCampByCampNo(campNo);
	}
	
	@ResponseBody
	@GetMapping("/reserve/tentInfo")
	public Map<String, Object> tentList(HttpServletRequest request) {
		return reserveService.getTentByCampNo(request);
	}
	
	
	
	
	/*
	 * @ResponseBody
	 * 
	 * @GetMapping("/reserve/camp_info") public Map<String, Object>
	 * campInfo(@RequestParam(value="campNo") int campNo) { return
	 * reserveService.getCampByCampNo(campNo); }
	 */
	 	
	/*
	 * @GetMapping("/reserve/reservation") public String
	 * reservation(HttpServletRequest requst, Model model) {
	 * 
	 * return "reserve/reservation"; }
	 */
	 
	 
	 @GetMapping("/reserve/pay") 
	 public String reserve_pay(HttpServletRequest request, Model model) { 
		 reserveService.getInfoReserve(request, model); 
		 return "reserve/pay"; 
	 }
	 
	 @PostMapping("/reserve/add")
	 public String reserve_add(HttpServletRequest request, HttpServletResponse response) {
		 reserveService.addInfoReserve(request, response);
		 return "redirect:/";
		 
	 }
	 	
	 
	 

	 
	 
	 

	
	
}
