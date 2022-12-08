package com.gdu.bulmeong.ex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.ex.mapper.BulMeongMapper;

@Service
public class BulMeongServiceImpl implements BulMeongService {
	
	@Autowired 
	private BulMeongMapper bulMeongMapper;
	 
	
}
