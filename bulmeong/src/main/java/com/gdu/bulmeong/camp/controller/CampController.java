package com.gdu.bulmeong.camp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.bulmeong.camp.service.CampService;


@Controller
public class CampController {

	@Autowired
	private CampService campService;
	
	@GetMapping("/getCamp")  // 관리자만 이용할 수 있는 api 데이터 등록(나중에 작업 필요) --> 임시로 이용하려면 주소창에 직접 입력해서 들어오기
	public void getCamp(HttpServletResponse response) {
		campService.parseCampInfo();
		campService.addCampInfoToDb(response);
	}
	
	@GetMapping("/camp")
	public String camp() {
		return "camp/camp_list";
	}
	
	@ResponseBody
	@GetMapping(value="/camp/list", produces="application/json" )                 // 나중에 찜 또는 리뷰게시글 수로 정렬해서 리스트 띄우기
	public Map<String, Object> printCampList(HttpServletRequest request) {
		return campService.getCampList(request);
	}
	
	@ResponseBody
	@GetMapping(value="/camp/list/option", produces="application/json")
	public Map<String, Object> printCampListOption(HttpServletRequest request) {
		return campService.getCampListOption(request);
	}
	
	@GetMapping("/detail")
	public String detail(HttpServletRequest request, Model model) {
		campService.getDetailList(request, model);
		return "camp/detail";
	}
	
}
