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
		String nickname = request.getParameter("nickname");
		Map<String, Object> map = new HashMap<>();
		map.put("freeNo", freeNo);
		map.put("nickname", nickname);
		Map<String, Object> result = new HashMap<>();
		result.put("likeCount", freeLikeMapper.selectUserLikeCount(map));
		return result;
		
	}
	
	@Override
	public Map<String, Object> getLikeCount(int freeNo) {
		Map<String, Object> result = new HashMap<>();
		result.put("count", freeLikeMapper.selectLikeCount(freeNo));
		return result;
	}
	
	@Override
	public Map<String, Object> mark(HttpServletRequest request) {
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		String nickname = request.getParameter("nickname");
		Map<String, Object> map = new HashMap<>();
		map.put("freeNo", freeNo);
		map.put("nickname", nickname);
		Map<String, Object> result = new HashMap<>();
		if (freeLikeMapper.selectUserLikeCount(map) == 0) {  // 해당 게시물의 "좋아요"를 처음 누른 상태
			result.put("isSuccess",freeLikeMapper.insertLike(map) == 1);  // 신규 삽입			
		} else {
			result.put("isSuccess", freeLikeMapper.deleteLike(map) == 1);  // 기존 정보 삭제		
		}
		return result;
	}
	
	

}
