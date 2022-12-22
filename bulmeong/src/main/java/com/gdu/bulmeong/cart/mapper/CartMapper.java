package com.gdu.bulmeong.cart.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
	public int selectUserCartCount(Map<String, Object> map);
	public int selectCampCartCount(int campNo); 
	public int insertCart(Map<String, Object> map);
	public int deleteCart(Map<String, Object> map);
}
