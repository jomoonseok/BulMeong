package com.gdu.bulmeong.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.gdu.bulmeong.admin.service.AdminService;


@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/admin")
	public String admin_main() {
		return "/admin/main";
	}

	
	// 유저
	@GetMapping("/admin/userManage")
	public String admin_userManage() {
		return "/admin/userManage";
	}
	
	@ResponseBody
	@PostMapping(value="/admin/getAllUser", produces="application/json")
	public Map<String, Object> getAllUser() {
		return adminService.getAllUser();
	}
	
	
	
	// 캠프
	@GetMapping("/admin/campManage")
	public String admin_campManage() {
		return "/admin/campManage";
	}
	
	@ResponseBody
	@PostMapping(value="/admin/getAllCamp", produces="application/json")
	public Map<String, Object> getAllCamp(HttpServletRequest request) {
		return adminService.getAllCamp(request);
	}
	
	// 텐트
	@GetMapping("/admin/tentManage")
	public String admin_tentManage() {
		return "/admin/tentManage";
	}
	
	@ResponseBody
	@PostMapping(value="/admin/getAllTent", produces="application/json")
	public Map<String, Object> getAllTent(HttpServletRequest request) {
		return adminService.getAllTent(request);
	}
	
	@GetMapping("/admin/addTent")
	public String addTent() {
		return "/admin/addTent";
	}
	
	@ResponseBody
	@PostMapping(value="/admin/getFacltNm", produces="application/json")
	public Map<String, Object> getFacltNm() {
		return adminService.getCampcampNofacltNm();
	}
	
	@PostMapping("/admin/add")
	public void uploadTent(MultipartHttpServletRequest request, HttpServletResponse response) {
		adminService.uploadTent(request, response);
	}
	
	@GetMapping("/admin/modify")
	public String modifyTent(HttpServletRequest request, Model model){
		adminService.modifyTent(request, model); 
		return "/admin/modifyTent";
	}
	
	
	
}
