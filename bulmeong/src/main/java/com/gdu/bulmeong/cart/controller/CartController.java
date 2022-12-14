package com.gdu.bulmeong.cart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.bulmeong.cart.domain.CartDTO;
import com.gdu.bulmeong.cart.service.CartService;
import com.gdu.bulmeong.users.domain.UsersDTO;


@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	@GetMapping("/cart/jjim")
	public String cart() {
		return "cart/jjim";
	}
	
	/*
	@GetMapping("/cart")
	public String cart() {
		return "cart/addCart";
	}
	
	// 카트 담기
	@ResponseBody
	@RequestMapping(value = "/cart/addCart", method = RequestMethod.POST)
	public int addCart(CartDTO cart, HttpSession session) {
	   int result = 0;
	   
	   UsersDTO user = (UsersDTO)session.getAttribute("user");
	   
	   if(user != null) {
		   cart.setId(user.getId());
		   cartService.addCart(cart);
		   result = 1;
	   }
	   
	   return result;
	   
	}
	*/

	
	
		
	
		


	
}
