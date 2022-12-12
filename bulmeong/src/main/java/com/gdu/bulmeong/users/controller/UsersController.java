package com.gdu.bulmeong.users.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@ResponseBody
	@GetMapping(value="/users/checkReduceId", produces="application/json")
	public Map<String, Object> checkReduceId(String id){
		return usersService.isReduceId(id);
	}
	
	@ResponseBody
	@GetMapping(value="/users/checkReduceNickname", produces="application/json")
	public Map<String, Object> checkReduceNickname(String nickname){
		return usersService.isReduceNickname(nickname);
	}
	
	@ResponseBody
	@GetMapping(value="/users/checkReduceEmail", produces="application/json")
	public Map<String, Object> checkReduceEmail(String Email){
		return usersService.isReduceEmail(Email);
	}
	
	@ResponseBody
	@GetMapping(value="/users/sendAuthCode", produces="application/json")
	public Map<String, Object> sendAuthCode(String Email){
		return usersService.sendAuthCode(Email);
	}
	
	@GetMapping("/users/login/form")
	public String loginForm() {
		return "users/loginForm";
	}
	
	
	
}
