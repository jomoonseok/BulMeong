package com.gdu.bulmeong.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.bulmeong.pay.service.PayService;


@Controller
public class PayController {

	 
	@GetMapping("/camp/pay")
	public String iamport() {
		return "camp/pay";
	}
	
		
	
		


	
}
