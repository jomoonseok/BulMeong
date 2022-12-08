package com.gdu.bulmeong.faq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.faq.mapper.FAQMapper;

@Service
public class FAQServiceImpl implements FAQService {
	
	@Autowired 
	private FAQMapper faqMapper;
	 
	
}
