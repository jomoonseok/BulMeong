package com.gdu.bulmeong.freeboard.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.bulmeong.freeboard.domain.FreeBoardCmtDTO;
import com.gdu.bulmeong.freeboard.service.FreeBoardCmtService;



@Controller
public class FreeBoardCmtController {

		
	@Autowired
	private FreeBoardCmtService freeBoardCmtService;
	
	@ResponseBody
	@GetMapping(value="/freecomment/getCount", produces="application/json")
	public Map<String, Object> getCount(@RequestParam("freeNo") int freeNo){
		return freeBoardCmtService.getCmtCount(freeNo);
	}
	
	@ResponseBody
	@GetMapping(value="/freecomment/list", produces="application/json")
	public Map<String, Object> list(HttpServletRequest request){
		return freeBoardCmtService.getCmtList(request);
	}
	
	@ResponseBody
	@PostMapping(value="/freecomment/add", produces="application/json")
	public Map<String, Object> add(FreeBoardCmtDTO freeComment){
		return freeBoardCmtService.addCmt(freeComment);
	}
	
	
	
		
		
	
		
	
	
	
	
	
	
	
	
}
