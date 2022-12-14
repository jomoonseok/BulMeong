package com.gdu.bulmeong.cart.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.cart.domain.CartDTO;

@Mapper
public interface CartMapper {
	public void addCart(CartDTO cart);
}
