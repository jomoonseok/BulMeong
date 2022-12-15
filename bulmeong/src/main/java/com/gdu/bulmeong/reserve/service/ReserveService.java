package com.gdu.bulmeong.reserve.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface ReserveService {

	public Map<String, Object> getTentByCampNo(int campNo);
	public void getInfoReserve(HttpServletRequest request, Model model);
}
