package com.gdu.bulmeong.index.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.bulmeong.index.service.IndexService;


@Controller
public class IndexController {

	@Autowired
	private IndexService indexService;
	
	@ResponseBody
	@GetMapping(value="/index/listIndex", produces="application/json" )     
	public Map<String, Object> printCampListIndex() {
		return indexService.getCampListIndex();
	}
	
	@ResponseBody
	@GetMapping(value="/index/location", produces="application/json" )     
	public Map<String, Object> getLocation(HttpServletRequest request) {
		return indexService.getLocation(request);
	}
	
	@ResponseBody
	@GetMapping(value="/index/weather", produces="application/json" )     
	public Map<String, Object> printWeather(HttpServletRequest request) {
		return indexService.getWeather(request);
	}
	
	@ResponseBody
	@GetMapping(value="/index/jjimList", produces="application/json" )     
	public Map<String, Object> printCampJjim(HttpServletRequest request) {
		return indexService.getCampListByJjim();
	}
	
	@ResponseBody
	@GetMapping(value="/index/freeBrdList", produces="application/json")
	public Map<String, Object> printFreeBrdList() {
		return indexService.getFreeBrdList();
	}
	
	
}
