package com.gdu.bulmeong.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.admin.service.AdminService;


@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/admin")
	public String admin_main() {
		return "/admin/admin_main";
	}
	
	
}
