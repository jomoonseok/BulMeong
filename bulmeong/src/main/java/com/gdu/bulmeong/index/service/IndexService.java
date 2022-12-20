package com.gdu.bulmeong.index.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface IndexService {

	public Map<String, Object> getCampListIndex();
	public Map<String, Object> getLocation(HttpServletRequest request);
	public Map<String, Object> getWeather(HttpServletRequest request);
	
	
}
