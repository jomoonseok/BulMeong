package com.gdu.bulmeong.qna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.ex.mapper.BulMeongMapper;

@Service
public class QNAServiceImpl implements QNAService {
	
	@Autowired 
	private BulMeongMapper bulMeongMapper;
	 
	
}
