package com.gdu.bulmeong.cart.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.camp.mapper.CampMapper;
import com.gdu.bulmeong.cart.mapper.CartMapper;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired 
	private CartMapper cartMapper;
	
	@Autowired
	private CampMapper campMapper;
	 
	@Override
	public Map<String, Object> getJjimCheck(HttpServletRequest request) {
		int campNo = Integer.parseInt(request.getParameter("campNo"));
		String id = request.getParameter("id");
		Map<String, Object> map = new HashMap<>();
		map.put("campNo", campNo);
		map.put("id", id);
		Map<String, Object> result = new HashMap<>();
		result.put("count", cartMapper.selectUserCartCount(map));
		return result;
	}
	
	@Override
	public Map<String, Object> getJjimCount(int campNo) {
		Map<String, Object> result = new HashMap<>();
		result.put("count", cartMapper.selectCampCartCount(campNo));
		return result;
	}
	
	@Override
	public Map<String, Object> mark(HttpServletRequest request) {
		int campNo = Integer.parseInt(request.getParameter("campNo"));
		String id = request.getParameter("id");
		 
		Map<String, Object> map = new HashMap<>();
		map.put("campNo", campNo);
		map.put("id", id);
		Map<String, Object> result = new HashMap<>();
		System.out.println("map :" + map);
		if (cartMapper.selectUserCartCount(map) == 0) {  // 해당 게시물의 "좋아요"를 처음 누른 상태
			result.put("insertSuccess", cartMapper.insertCart(map) == 1);  // 신규 삽입			
		} else {
			result.put("deleteSuccess", cartMapper.deleteCart(map) == 1);  // 기존 정보 삭제		
		}
		result.put("cartCnt", cartMapper.selectCampCartCount(campNo));
		return result;
	}
	

}
