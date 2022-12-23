package com.gdu.bulmeong.admin.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {

	public Map<String, Object> getAllUser();
	public Map<String, Object> getAllCamp(HttpServletRequest request);
	public Map<String, Object> getAllTent(HttpServletRequest request);
	
}
