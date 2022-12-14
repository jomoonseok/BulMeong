package com.gdu.bulmeong.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.cart.domain.CartDTO;
import com.gdu.bulmeong.cart.mapper.CartMapper;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired 
	private CartMapper cartMapper;
	 
	@Override
	public void addCart(CartDTO cart) {
		cartMapper.addCart(cart);
	}
	

}
