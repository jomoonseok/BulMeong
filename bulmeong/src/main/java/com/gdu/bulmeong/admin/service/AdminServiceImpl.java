package com.gdu.bulmeong.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.admin.mapper.AdminMapper;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired 
	private AdminMapper adminMapper;
	
	 
	

}
