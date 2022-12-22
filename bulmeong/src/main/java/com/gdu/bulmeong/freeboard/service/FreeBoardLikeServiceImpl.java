package com.gdu.bulmeong.freeboard.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.freeboard.mapper.FreeBoardLikeMapper;

@Service
public class FreeBoardLikeServiceImpl implements FreeBoardLikeService {
	
	@Autowired
	private FreeBoardLikeMapper freeLikeMapper;
	
	
	@Override
	public Map<String, Object> getLikeCheck(HttpServletRequest request) {
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		int nickname = Integer.parseInt(request.getParameter("nickname"));
		Map<String, Object> map = new HashMap<>();
		map.put("freeNo", freeNo);
		map.put("freeNo", freeNo);
		Map<String, Object> result = new HashMap<>();
		result.put("likeCount", freeLikeMapper.selectUserLikeCount(map));
		return result;
		
	}
	
	@Override
	public Map<String, Object> getLikeCount(int freeNo) {
		
		return null;
	}
	
	@Override
	public Map<String, Object> mark(HttpServletRequest request) {
		
		return null;
	}
	
	

}
