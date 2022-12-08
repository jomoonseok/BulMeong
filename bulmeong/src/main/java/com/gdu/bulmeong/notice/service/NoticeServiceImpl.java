package com.gdu.bulmeong.notice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.ex.mapper.BulMeongMapper;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired 
	private BulMeongMapper bulMeongMapper;
	 
	
}
