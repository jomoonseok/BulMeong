package com.gdu.bulmeong.freeboard.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gdu.bulmeong.freeboard.domain.FreeBoardCmtDTO;
import com.gdu.bulmeong.freeboard.mapper.FreeBoardCmtMapper;
import com.gdu.bulmeong.users.domain.UsersDTO;
import com.gdu.bulmeong.util.PageUtil;
import com.gdu.bulmeong.util.SecurityUtil;

@Service
public class FreeBoardCmtServiceImpl implements FreeBoardCmtService {
	
	
	@Autowired
	private FreeBoardCmtMapper freeBoardCmtMapper;
	

	@Override
	public Map<String, Object> getCmtCount(int freeNo) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commentCount", freeBoardCmtMapper.selectCmtCount(freeNo));

		return result;
	}
	
	@Override
	public Map<String, Object> getCmtList(Model model) {
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("freeNo", freeNo);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commentList", freeBoardCmtMapper.selectCmtList(map));

		return result;	
		
	}
	
//	@Override
//	public List<FreeBoardCmtDTO> getCmtLists(int freeNo) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("freeNo", freeNo);
//		return freeBoardCmtMapper.selectCmtList(map);
//	}
	
	@Transactional
	@Override
	public Map<String, Object> addCmt(FreeBoardCmtDTO freeCmt) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser"); 		
		String freeCmtIp = request.getRemoteAddr();
		String id = loginUser.getId();
		String nickname = loginUser.getNickname();
		String profileImage = loginUser.getProfileImage();

		
		// ?????? freeGroupNo ??????????????? ????????????
		int freeGroupNo = Integer.parseInt(request.getParameter("freeGroupNo"));
		
		// ?????? freeCmt??? GroupNo ????????????
		FreeBoardCmtDTO freeCmtDTO = new FreeBoardCmtDTO();
		freeCmtDTO.setFreeGroupNo(freeGroupNo);
		
		// ?????? freeCmt??? update ?????????? (????????? 0??? ???????????? > + 1 ??? ??????. )
		freeBoardCmtMapper.updateGroupNo(freeCmtDTO);				

		// ?????? ??? ????????? ip, nickname, groupNo??? ????????????.
		freeCmt.setFreeCmtIp(freeCmtIp);
		freeCmt.setId(id);
		freeCmt.setNickname(nickname);
		freeCmt.setFreeGroupNo(freeGroupNo); // 0?????? ????????????.
		freeCmt.setProfileImage(profileImage);
		
	
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
	
	

	
	
	@Transactional
	@Override
	public Map<String, Object> addReply(FreeBoardCmtDTO freeCmtReply) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser"); 
		String id = loginUser.getId();
		String nickname = loginUser.getNickname();
		String profileImage = loginUser.getProfileImage();
		
		// 1. ?????? ?????? groupOrder?????? !
		// ????????? DEPTH, GROUP_NO, GROUP_ORDER
		int freeCmtDepth = Integer.parseInt(request.getParameter("freeCmtDepth"));
		int freeGroupNo = Integer.parseInt(request.getParameter("freeGroupNo"));
		int freeGroupOrder = Integer.parseInt(request.getParameter("freeGroupOrder"));
		
		
		FreeBoardCmtDTO freeCmt = new FreeBoardCmtDTO();
		freeCmt.setFreeCmtDepth(freeCmtDepth);
		freeCmt.setFreeGroupNo(freeGroupNo);
		freeCmt.setFreeGroupOrder(freeGroupOrder);
		
		freeBoardCmtMapper.updatePreviousReply(freeCmt);

		// 2. ?????? ??????!
		//UsersDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		String freeCmtIp = request.getRemoteAddr();
		freeCmtReply.setId(id);
		freeCmtReply.setFreeCmtIp(freeCmtIp);
		freeCmtReply.setNickname(nickname);
		freeCmtReply.setFreeCmtDepth(freeCmtDepth + 1);
		freeCmtReply.setFreeGroupNo(freeGroupNo);
		freeCmtReply.setFreeGroupOrder(freeGroupOrder + 1);
		freeCmtReply.setProfileImage(profileImage);
				
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAddReply", freeBoardCmtMapper.insertCmtReply(freeCmtReply) == 1);

		
		return result;
	}
	
	
}
