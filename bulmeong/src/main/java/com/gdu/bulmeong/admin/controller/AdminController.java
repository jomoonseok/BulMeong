package com.gdu.bulmeong.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.bulmeong.admin.service.AdminService;


@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/admin")
	public String admin_main() {
		return "/admin/main";
	}
	
	@GetMapping("/admin/userManage")
	public String admin_userManage() {
		return "/admin/userManage";
	}
	
	@ResponseBody
	@PostMapping(value="/admin/getAllUser", produces="application/json")
	public Map<String, Object> getAllUser() {
		return adminService.getAllUser();
	}
	
	
	
}
