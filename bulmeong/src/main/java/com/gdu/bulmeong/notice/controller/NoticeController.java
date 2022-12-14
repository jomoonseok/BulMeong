package com.gdu.bulmeong.notice.controller;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.bulmeong.notice.service.NoticeService;

@Controller
public class NoticeController {

	@Autowired 
	private NoticeService noticeService;
	 
	
//	@GetMapping("/")
//	public String index() {
//		return "index";
//	}
	
	/* 목록 */
	@GetMapping("/notice/list")
	public String list(HttpServletRequest request, Model model) {
		noticeService.getNoticeList(request, model);
		return "notice/list";
	}
	
	/* 작성 */
	@GetMapping("/notice/write")
	public String write() {
		return "notice/write";
	}
	
	/* 이미지삽입 */
	@ResponseBody
	@PostMapping(value="/notice/uploadImage", produces="application/json")
	public Map<String, Object> uploadImage(MultipartHttpServletRequest multipartRequest) {
		return noticeService.saveSummernoteImage(multipartRequest);
	}
	
	/* 추가 */
	@PostMapping("/notice/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		noticeService.saveNotice(request, response);
	}
	
	/* 조회수증가 */
	@GetMapping("/notice/increase/hit")
	public String increase(@RequestParam(value="noticeNo", required=false, defaultValue="0")int noticeNo) {
		int result = noticeService.increaseNoticeHit(noticeNo);
		if(result > 0) {
			return "redirect:/notice/detail?noticeNo=" + noticeNo;
		} else {
			return "redirect:/notice/list";
		}
	}
	
	/* 상세보기 */
	@GetMapping("/notice/detail")
	public String detail(@RequestParam(value="noticeNo", required=false, defaultValue="0") int noticeNo, Model model) {
		model.addAttribute("notice", noticeService.getNoticeByNo(noticeNo));
		return "notice/detail";
	}
	
	/* 편집 */
	@PostMapping("/notice/edit")
	public String edit(int noticeNo, Model model) {
		model.addAttribute("notice", noticeService.getNoticeByNo(noticeNo));
		return "notice/edit";
	}
	
	/* 수정 */
	@PostMapping("/notice/modify")
	public void modify(HttpServletRequest request, HttpServletResponse response) {
		noticeService.modifyNotice(request, response);
	}
	
	/* 삭제 */
	@PostMapping("/notice/remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		noticeService.removeNotice(request, response);
	}
	
}
