package com.gdu.bulmeong.pay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.pay.mapper.PayMapper;

@Service
public class PayServiceImpl implements PayService {
	
	@Autowired 
	private PayMapper payMapper;
	 
	@Override
	public void findTentByNo(int tentNo) {
		payMapper.selectTentByNo(tentNo);
		
	}
	
}
