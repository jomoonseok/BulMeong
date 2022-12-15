package com.gdu.bulmeong.cart.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.bulmeong.cart.service.CartService;


@RestController  // @Controller + @ResponseBody
public class CartController {


	@Autowired
	private CartService cartService;
	
	@GetMapping(value="/cart/getJjimCheck", produces="application/json")
	public Map<String, Object> getJjimCheck(HttpServletRequest request) {
		return cartService.getJjimCheck(request);
	}
	
	@GetMapping(value="/cart/getJjimCount", produces="application/json")
	public Map<String, Object> getJjimCount(int campNo) {
		return cartService.getJjimCount(campNo);
	}
	
	@GetMapping(value="/cart/mark", produces="application/json")
	public Map<String, Object> mark(HttpServletRequest request) {
		return cartService.mark(request);
	}
	
	
		
	
		


	
}
