package com.gdu.bulmeong.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.ex.mapper.BulMeongMapper;
import com.gdu.bulmeong.reserve.mapper.ReserveMapper;

@Service
public class ReserveServiceImpl implements ReserveService {
	
	@Autowired 
	private ReserveMapper reserveMapper;
	 
	
}
