package com.gdu.bulmeong.cart.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
	public Map<String, Object> getJjimCheck(HttpServletRequest request);
	public Map<String, Object> getJjimCount(int campNo);
	public Map<String, Object> mark(HttpServletRequest request);
}
