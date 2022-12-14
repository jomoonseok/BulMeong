package com.gdu.bulmeong.faq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.faq.mapper.FaqMapper;

@Service
public class FaqServiceImpl implements FaqService {
	
	@Autowired 
	private FaqMapper faqMapper;
	
	
	
}
