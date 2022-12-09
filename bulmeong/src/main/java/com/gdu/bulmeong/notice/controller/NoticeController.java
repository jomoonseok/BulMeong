package com.gdu.bulmeong.notice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.ex.service.BulMeongService;
import com.gdu.bulmeong.notice.service.NoticeService;

@Controller
public class NoticeController {

	@Autowired 
	private NoticeService noticeService;
	 
	
//	@GetMapping("/")
//	public String index() {
//		return "index";
//	}
	
	@GetMapping("/notice/list")
	public String list(HttpServletRequest request, Model model) {
		noticeService.getNoticeList(request, model);
		return "notice/list";
	}
	
}
