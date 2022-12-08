package com.gdu.bulmeong.ex2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.ex.mapper.BulMeongMapper;

@Service
public class BulMeong2ServiceImpl implements BulMeong2Service {
	
	@Autowired 
	private BulMeongMapper bulMeongMapper;
	 
	
}
