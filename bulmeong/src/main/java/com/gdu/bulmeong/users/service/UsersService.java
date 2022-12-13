package com.gdu.bulmeong.users.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.bulmeong.users.domain.UsersDTO;

public interface UsersService {
	public Map<String, Object> isReduceId(String id);
	public Map<String, Object> isReduceNickname(String nickname);
	public Map<String, Object> isReduceEmail(String email);
	public Map<String, Object> sendAuthCode(String email);
	public void join(HttpServletRequest request, HttpServletResponse response);
	public void login(HttpServletRequest request, HttpServletResponse response);
	//public void keepLogin(HttpServletRequest request, HttpServletResponse response);
	//public UsersDTO getUserBySessionId(Map<String, Object> map);
}
