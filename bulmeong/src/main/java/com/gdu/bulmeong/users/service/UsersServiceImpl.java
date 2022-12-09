package com.gdu.bulmeong.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.users.mapper.UsersMapper;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired 
	private UsersMapper usersMapper;
	 
	
}
