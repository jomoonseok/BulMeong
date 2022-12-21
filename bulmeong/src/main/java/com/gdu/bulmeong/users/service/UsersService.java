package com.gdu.bulmeong.users.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.bulmeong.users.domain.SleepUsersDTO;
import com.gdu.bulmeong.users.domain.UsersDTO;

public interface UsersService {
	public Map<String, Object> isReduceId(String id);
	public Map<String, Object> isReduceNickname(String nickname);
	public Map<String, Object> isReduceEmail(String email);
	public Map<String, Object> sendAuthCode(String email);
	public void join(HttpServletRequest request, HttpServletResponse response);
	public void login(HttpServletRequest request, HttpServletResponse response);
	public void keepLogin(HttpServletRequest request, HttpServletResponse response);
	public void retire(HttpServletRequest request, HttpServletResponse response);
	public void logout(HttpServletRequest request, HttpServletResponse response);
	public UsersDTO getUserBySessionId(Map<String, Object> map);
	public Map<String, Object> confirmPassword(HttpServletRequest request);
	public void modifyPassword(HttpServletRequest request, HttpServletResponse response);
	public void modifyUser(HttpServletRequest request, HttpServletResponse response);
	public void sleepUserHandle();
	public SleepUsersDTO getSleepUserById(String id);
	public void restoreUser(HttpServletRequest request, HttpServletResponse response);
	
	public Map<String, Object> saveImage(MultipartHttpServletRequest multipartRequest);
}
