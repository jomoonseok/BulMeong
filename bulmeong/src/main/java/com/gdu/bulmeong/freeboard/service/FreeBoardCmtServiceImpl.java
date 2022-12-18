package com.gdu.bulmeong.freeboard.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gdu.bulmeong.freeboard.domain.FreeBoardCmtDTO;
import com.gdu.bulmeong.freeboard.mapper.FreeBoardCmtMapper;
import com.gdu.bulmeong.util.PageUtil;

@Service
public class FreeBoardCmtServiceImpl implements FreeBoardCmtService {
	
	
	@Autowired
	private FreeBoardCmtMapper freeBoardCmtMapper;
	
	@Autowired
	private PageUtil pageUtil;
	

	@Override
	public Map<String, Object> getCmtCount(int freeNo) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commentCount", freeBoardCmtMapper.selectCmtCount(freeNo));

		return result;
	}
	
	
	@Override
	public Map<String, Object> getCmtList(HttpServletRequest request) {
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		int commentCount = freeBoardCmtMapper.selectCmtCount(freeNo);
		
		/**************************************************************************************/
		/***********************************수정필요합니다*************************************/
		/**************************************************************************************/
		int recordPerPage = 100;
		/**************************************************************************************/
		/***********************************수정필요합니다*************************************/
		/**************************************************************************************/
		
		pageUtil.setSearchPageUtil(page, commentCount, recordPerPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("freeNo", freeNo);
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commentList", freeBoardCmtMapper.selectCmtList(map));
		result.put("pageUtil", pageUtil);
		return result;
	}
	
	@Override
	public Map<String, Object> addCmt(FreeBoardCmtDTO freeCmt) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String freeCmtIp = request.getRemoteAddr();

		/**************************************************************************************/
		/***********************************수정필요합니다*************************************/
		/**************************************************************************************/
		freeCmt.setNickname("관리자");

		/**************************************************************************************/
		/***********************************수정필요합니다*************************************/
		/**************************************************************************************/
		freeCmt.setFreeCmtIp(freeCmtIp);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAdd", freeBoardCmtMapper.insertCmt(freeCmt) == 1);
		
		return result;
	}
	
	
	@Override
	public Map<String, Object> removeCmt(int freeCmtNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isRemove", freeBoardCmtMapper.deleteCmt(freeCmtNo) == 1 );
		return result;
	}
	
	@Override
	public Map<String, Object> modifyCmt(FreeBoardCmtDTO freeCmt) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isModify", freeBoardCmtMapper.updateCmt(freeCmt) == 1);

		return result;
	}
	
	@Override
	public Map<String, Object> addReply(FreeBoardCmtDTO freeCmtReply) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		//HttpSession session = request.getSession();
		//UsersDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		String freeCmtIp = request.getRemoteAddr();
		
		// 원글의 DEPTH, GROUP_NO, GROUP_ORDER
		int freeCmtDepth = Integer.parseInt(request.getParameter("freeCmtDepth"));
		int freeGroupNo = Integer.parseInt(request.getParameter("freeGroupNo"));
		int freeGroupOrder = Integer.parseInt(request.getParameter("freeGroupOrder"));
		
		FreeBoardCmtDTO freeBoardCmt = new FreeBoardCmtDTO();
		freeBoardCmt.setFreeCmtDepth(freeCmtDepth);
		freeBoardCmt.setFreeGroupNo(freeGroupNo);
		freeBoardCmt.setFreeGroupOrder(freeGroupOrder);
		
		freeBoardCmtMapper.updatePreviousReply(freeBoardCmt);
		
		System.out.println("freeBoardCmt : " + freeBoardCmt);

		// freeCmtReply.setId(loginUser.getId());
		freeCmtReply.setFreeCmtIp(freeCmtIp);
		freeCmtReply.setNickname("관리자");
		freeCmtReply.setFreeCmtDepth(freeCmtDepth + 1);
		freeCmtReply.setFreeGroupNo(freeGroupNo);
		freeCmtReply.setFreeGroupOrder(freeGroupOrder + 1);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAddReply", freeBoardCmtMapper.insertCmtReply(freeCmtReply) == 1);

		
		return result;
	}
	
	
}
