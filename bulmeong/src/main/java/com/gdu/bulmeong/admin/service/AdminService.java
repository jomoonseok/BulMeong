package com.gdu.bulmeong.admin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface AdminService {
	
	public void adminCheck(HttpServletRequest request, Model model);
	public Map<String, Object> getAllUser(HttpServletRequest request);
	public Map<String, Object> removeUser(List<Integer> usersNo);
	public Map<String, Object> getAllCamp(HttpServletRequest request);
	public Map<String, Object> getAllTent(HttpServletRequest request);
	public Map<String, Object> getAllReserve(HttpServletRequest request);
	public Map<String, Object> getCampcampNofacltNm();
	public void uploadTent(MultipartHttpServletRequest request, HttpServletResponse response);
	public void modifyTent(HttpServletRequest request, Model model);
	public void changeTent(MultipartHttpServletRequest request, HttpServletResponse response);
	public Map<String, Object> removeTent(HttpServletRequest request);
	public Map<String, Object> changeImage(MultipartHttpServletRequest request);
}
