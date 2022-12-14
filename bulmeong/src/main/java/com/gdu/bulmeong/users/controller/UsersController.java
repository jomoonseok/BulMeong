package com.gdu.bulmeong.users.controller;

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
	public Map<String, Object> checkReduceEmail(String email){
		return usersService.isReduceEmail(email);
	}
	
	@ResponseBody
	@GetMapping(value="/users/sendAuthCode", produces="application/json")
	public Map<String, Object> sendAuthCode(String email){
		return usersService.sendAuthCode(email);
	}
	
	@PostMapping("/users/join")
	public void join(HttpServletRequest request, HttpServletResponse response) {
		usersService.join(request, response);
	}
	
	@PostMapping("/users/retire")
	public void retire(HttpServletRequest request, HttpServletResponse response) {
		usersService.retire(request, response);
	}
	
	@GetMapping("/users/login/form")
	public String loginForm(HttpServletRequest request, Model model) {
		
		// 요청 헤더 referer : 이전 페이지의 주소가 저장
		model.addAttribute("url", request.getHeader("referer"));  // 로그인 후 되돌아 갈 주소 url
		
		// 네이버 로그인
		//model.addAttribute("apiURL", usersService.getNaverLoginApiURL(request));
		
		return "users/loginForm";
	}
	
	@PostMapping("/users/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		usersService.login(request, response);
	}
	
	@GetMapping("/user/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		usersService.logout(request, response);
		return "redirect:/";
	}
	
	@GetMapping("/users/check/form")
	public String requiredLogin_checkForm() {
		return "users/check";
	}
	
	@ResponseBody
	@PostMapping(value="/users/check/pw", produces="application/json")
	public Map<String, Object> requiredLogin_checkPw(HttpServletRequest request) {
		return usersService.confirmPassword(request);
	}
	
	@GetMapping("/users/mypage")
	public String requiredLogin_mypage() {
		return "users/mypage";
	}
	
	
	
}
