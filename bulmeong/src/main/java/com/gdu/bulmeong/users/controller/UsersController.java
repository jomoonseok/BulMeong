package com.gdu.bulmeong.users.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.bulmeong.users.service.UsersService;

@Controller
public class UsersController {

	@Autowired 
	private UsersService usersService;
	 
	
	@GetMapping("/users/agree")
	public String agree() {
		return "users/agree";
	}
	
	@GetMapping("/users/join/write")
	public String joinWrite(@RequestParam(required=false) String location
            			  , @RequestParam(required = false) String promotion
            			  , Model model) {
		model.addAttribute("location", location);
		model.addAttribute("promotion", promotion);
		return "users/joinWrite";
	}
	
	@GetMapping("/users/login/form")
	public String loginForm() {
		return "users/loginForm";
	}
	
	
	
}
