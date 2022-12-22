package com.gdu.bulmeong.admin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.admin.mapper.AdminMapper;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired 
	private AdminMapper adminMapper;
	
	public Map<String, Object> getAllUser() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("users", adminMapper.selectAllUser());
		
		return map;
	}
	

}
