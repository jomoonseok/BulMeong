package com.gdu.bulmeong.users.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.bulmeong.users.domain.UsersDTO;
import com.gdu.bulmeong.users.service.UsersService;

@Controller
public class UsersController {

	@Autowired 
	private UsersService usersService;
	/*
	@ResponseBody
	@PostMapping(value="/users/test", produces="application/json")
	public Map<String, Object> test(@RequestBody Map<String, Object> map) {
		System.out.println(map);
		System.out.println(map.get("arr1"));
		System.out.println(map.get("arr1").getClass().getName());
		
		Map<String, Object> test = new HashMap<String, Object>();
		test.put("test", "test");
		System.out.println(test);
		
		return map;
	}
	*/
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
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
		
		// ?????? ?????? referer : ?????? ???????????? ????????? ??????
		// model.addAttribute("url", request.getHeader("referer"));  // ????????? ??? ????????? ??? ?????? url
		
		// ????????? ?????????
		model.addAttribute("apiURL", usersService.getNaverLoginApiURL(request));
		
		return "users/loginForm";
	}
	
	@PostMapping("/users/login")
	public void login(HttpServletRequest request, HttpServletResponse response, Model model) {
		usersService.login(request, response, model);
	}
	
	@GetMapping("/users/naver/login")
	public String naverLogin(HttpServletRequest request, Model model) {
		String access_token = usersService.getNaverLoginToken(request);
		UsersDTO profile = usersService.getNaverLoginProfile(access_token);  // ???????????????????????? ????????? ????????? ??????
		UsersDTO naverUser = usersService.getNaverUserById(profile.getId()); // ?????? ???????????????????????? ????????? ??????????????? DB??? ????????? ??????
		
		// ???????????????????????? ??????????????? ?????? : ???????????????????????? ??????
		if(naverUser == null) {
			model.addAttribute("profile", profile);
			return "users/naver_join";
		}
		// ???????????????????????? ?????? ????????? ?????? : ????????? ??????
		else {
			usersService.naverLogin(request, naverUser);
			return "redirect:/";
		}
		
	}
	
	@PostMapping("/users/naver/join")
	public void naverJoin(HttpServletRequest request, HttpServletResponse response) {
		usersService.naverJoin(request, response);
	}
	
	@GetMapping("/users/logout")
	public String requiredLogin_logout(HttpServletRequest request, HttpServletResponse response) {
		usersService.logout(request, response);
		return "redirect:/";
	}
	
	@PostMapping("/users/check/form")
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
	
	@GetMapping("/users/modify")
	public String requiredLogin_modifyInfo() {
		return "users/modifyInfo";
	}
	
	@PostMapping("/users/modify/pw")
	public void requiredLogin_modifyPw(HttpServletRequest request, HttpServletResponse response) {
		usersService.modifyPassword(request, response);
	}
	
	@PostMapping("/users/modify/info")
	public void requiredLogin_modify(HttpServletRequest request, HttpServletResponse response) {
		usersService.modifyUser(request, response);
	}
	
	@GetMapping("/users/jjim")
	public String requiredLogin_jjim(HttpServletRequest request, Model model) {
		usersService.getJjimList(request, model);
		return "users/jjim";
	}
	
	@GetMapping("/users/board/list")
	public String requiredLogin_boardList(HttpServletRequest request, Model model) {
		usersService.getFreeBoardList(request, model);
		return "users/boardList";
	}
	
	@GetMapping("/users/reserve")
	public String requiredLogin_reserve(HttpServletRequest request, Model model) {
		usersService.getReserveById(request, model);
		return "users/reserve";
	}
	
	@GetMapping("/users/sleep/display")
	public String sleepDisplay() {
		return "users/sleep";
	}
	
	@PostMapping("/users/restore")
	public void restore(HttpServletRequest request, HttpServletResponse response) {
		usersService.restoreUser(request, response);
	}
	
	@GetMapping("/users/findId/form")
	public String findId() {
		return "users/findId";
	}
	
	@ResponseBody
	@PostMapping(value="/users/findId", produces="application/json")  // ????????? ??????
	public Map<String, Object> findId(@RequestBody Map<String, Object> map) {
		return usersService.findUser(map);
	}
	
	@GetMapping("/users/findPw/form")
	public String findPw() {
		return "users/findPw";
	}
	
	@ResponseBody
	@PostMapping(value="/users/findPw", produces="application/json")  // ???????????? ??????
	public Map<String, Object> findPw(@RequestBody Map<String, Object> map) {
		return usersService.findUser(map);
	}
	
	@ResponseBody
	@PostMapping(value="/users/sendTemporaryPassword", produces="application/json")  // ???????????? ???????????? ??????
	public Map<String, Object> memberSendEmailTemporaryPassword(UsersDTO user) {
		return usersService.sendTemporaryPassword(user);
	}
	
	@GetMapping("/users/popUp")
	public String requiredLogin_pw() {
		return "users/pwPopUp";
	}
	
	@GetMapping("/users/profile")
	public String requiredLogin_modifyProfileImage() {
		return "users/profile";
	}
	
	@ResponseBody
	@PostMapping(value="/users/changeImage", produces="application/json")
	public Map<String, Object> changeImage(MultipartHttpServletRequest multipartRequest){
		return usersService.saveImage(multipartRequest);
	}
	
	@PostMapping("/users/modify/profile")
	public void requiredLogin_modifyProfile(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		usersService.modifyProfile(multipartRequest, response);
	}
	
}
