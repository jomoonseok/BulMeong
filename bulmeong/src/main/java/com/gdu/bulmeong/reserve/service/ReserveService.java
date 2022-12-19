package com.gdu.bulmeong.reserve.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface ReserveService {

	public Map<String, Object> getCampByCampNo(int campNo);
	public Map<String, Object> getTentByCampNo(HttpServletRequest request);
	public void getInfoReserve(HttpServletRequest request, Model model);
	public void addInfoReserve(HttpServletRequest request, HttpServletResponse response);
}
