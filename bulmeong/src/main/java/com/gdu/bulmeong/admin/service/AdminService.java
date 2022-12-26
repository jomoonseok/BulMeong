package com.gdu.bulmeong.admin.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

public interface AdminService {

	public Map<String, Object> getAllUser();
	public Map<String, Object> getAllCamp(HttpServletRequest request);
	public Map<String, Object> getAllTent(HttpServletRequest request);
	public Map<String, Object> getCampcampNofacltNm();
	public void uploadTent(MultipartHttpServletRequest request, HttpServletResponse response);
	public void modifyTent(HttpServletRequest request, Model model);
}
