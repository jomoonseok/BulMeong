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
	public String requiredLogin_admin_main(HttpServletRequest request, Model model) {
		adminService.adminCheck(request, model);
		return "/admin/main";
	}

	
	// 유저
	@GetMapping("/admin/userManage")
	public String requiredLogin_admin_userManage() {
		return "/admin/userManage";
	}
	
	@ResponseBody
	@PostMapping(value="/admin/getAllUser", produces="application/json")
	public Map<String, Object> getAllUser() {
		return adminService.getAllUser();
	}
	
	
	
	// 캠프
	@GetMapping("/admin/campManage")
	public String requiredLogin_admin_campManage() {
		return "/admin/campManage";
	}
	
	@ResponseBody
	@PostMapping(value="/admin/getAllCamp", produces="application/json")
	public Map<String, Object> getAllCamp(HttpServletRequest request) {
		return adminService.getAllCamp(request);
	}
	
	// 텐트
	@GetMapping("/admin/tentManage")
	public String requiredLogin_admin_tentManage() {
		return "/admin/tentManage";
	}
	
	@ResponseBody
	@PostMapping(value="/admin/getAllTent", produces="application/json")
	public Map<String, Object> getAllTent(HttpServletRequest request) {
		return adminService.getAllTent(request);
	}
	
	@GetMapping("/admin/addTent")
	public String requiredLogin_addTent() {
		return "/admin/addTent";
	}
	
	@ResponseBody
	@PostMapping(value="/admin/getFacltNm", produces="application/json")
	public Map<String, Object> getFacltNm() {
		return adminService.getCampcampNofacltNm();
	}
	
	@PostMapping("/admin/add")
	public void requiredLogin_uploadTent(MultipartHttpServletRequest request, HttpServletResponse response) {
		adminService.uploadTent(request, response);
	}
	
	@GetMapping("/admin/modify")
	public String requiredLogin_modifyTent(HttpServletRequest request, Model model){
		adminService.modifyTent(request, model); 
		return "/admin/modifyTent";
	}
	
	@PostMapping("/admin/change")
	public void requiredLogin_changeTent(MultipartHttpServletRequest request, HttpServletResponse response) {
		adminService.changeTent(request, response);
	}
	
	@ResponseBody
	@PostMapping("/admin/remove")
	public Map<String, Object> removeTent(HttpServletRequest request) {
		return adminService.removeTent(request);
	}
	
	@ResponseBody
	@PostMapping("/admin/changeImage")
	public Map<String, Object> changeIamge(MultipartHttpServletRequest request) {
		return adminService.changeImage(request);
	}
	
	
}
