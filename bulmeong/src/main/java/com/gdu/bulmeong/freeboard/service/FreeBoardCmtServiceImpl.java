package com.gdu.bulmeong.freeboard.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gdu.bulmeong.freeboard.mapper.FreeBoardCmtMapper;
import com.gdu.bulmeong.util.PageUtil;

@Service
public class FreeBoardCmtServiceImpl implements FreeBoardCmtService {
	
	@Autowired
	private FreeBoardCmtMapper freeBoardCmtMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Override
	public Map<String, Object> getCommentCount(int freeNo) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commentCount", freeBoardCmtMapper.selectCommentCount(freeNo));
		
		return result;
		
	}
	
	@Override
	public void getFreeCmtList(Model model) {
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		int commentCount = freeBoardCmtMapper.selectCommentCount(freeNo);
		
		/**************************************************************************************/
		/***********************************수정필요합니다*************************************/
		/**************************************************************************************/
		int recordPerPage = 5;
		/**************************************************************************************/
		/***********************************수정필요합니다*************************************/
		/**************************************************************************************/
				
				
		pageUtil.setPageUtil(page, commentCount, recordPerPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("freeNo", freeNo);
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commentList", freeBoardCmtMapper.selectCommentList(map));
		result.put("pageUtil", pageUtil);
		
		model.addAttribute("freeCmtList", freeBoardCmtMapper.selectCommentList(map));

		
	}

}
