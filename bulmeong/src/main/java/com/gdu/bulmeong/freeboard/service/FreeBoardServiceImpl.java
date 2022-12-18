package com.gdu.bulmeong.freeboard.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.bulmeong.freeboard.domain.FreeBoardDTO;
import com.gdu.bulmeong.freeboard.mapper.FreeBoardCmtMapper;
import com.gdu.bulmeong.freeboard.mapper.FreeBoardMapper;
import com.gdu.bulmeong.users.domain.UsersDTO;
import com.gdu.bulmeong.util.PageUtil;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {
	
	@Autowired
	private FreeBoardMapper freeBoardMapper;
	
	@Autowired
	private FreeBoardCmtMapper freeBoardCmtMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	
	@Override
	public void getFreeList(Model model) {
		Map<String, Object> modelMap = model.asMap();
		HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
		// 원하는 파라미터 가져오는 방법이 이거자나
		String dateColumn = request.getParameter("dateColumn");
		String column = request.getParameter("column");
		String query = request.getParameter("query");
		
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dateColumn", dateColumn);
		map.put("column", column);
		map.put("query", query);
		 
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		int totalRecord = freeBoardMapper.selectFindFreeboardsCount(map);

		
		/**************************************************************************************/
		/***********************************수정필요합니다*************************************/
		/**************************************************************************************/
		int recordPerPage = 5;
		/**************************************************************************************/
		/***********************************수정필요합니다*************************************/
		/**************************************************************************************/
		
		
		pageUtil.setSearchPageUtil(page, totalRecord, recordPerPage);
		
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("freeBoardList", freeBoardMapper.selectFreeListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getSearchPaging(request.getContextPath() + "/freeboard/list"));

		model.addAttribute("dateColumn", dateColumn);
		model.addAttribute("column", column);
		model.addAttribute("query", query);
	}
	
	@Override
	public int increseFreeBoardHit(int freeNo) {
		return freeBoardMapper.updateHit(freeNo);
	}
	
	@Override
	public void addFreeBoard(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser"); 
		
		// String nickname = loginUser.getNickname();
		
		/**************************************************************************************/
		/***********************************수정필요합니다*************************************/
		/**************************************************************************************/
		String nickname = "관리자";
		/**************************************************************************************/
		/***********************************수정필요합니다*************************************/
		/**************************************************************************************/
		

		String freeTitle = request.getParameter("freeTitle");
		String freeContent = request.getParameter("freeContent");
		String freeIp = request.getRemoteAddr();
		
		FreeBoardDTO freeBoard = FreeBoardDTO.builder()
				.nickname(nickname)
				.freeTitle(freeTitle)
				.freeContent(freeContent)
				.freeIp(freeIp)
				.build();
		
		
		
		int result = freeBoardMapper.insertFreeBoard(freeBoard);
			
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {
				out.println("if(confirm('게시글 등록이 완료되었습니다. 확인하러 가시겠습니까?')) { location.href='" + request.getContextPath() + "/freeboard/detail?freeNo=" + freeBoard.getFreeNo() + "'}");
				out.println("else { location.href='/freeboard/list'}");
			} else {
				out.println("alert('게시글 등록에 실패하였습니다.');");
				out.println("history.back();");
			}
			out.println("</script>");
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	@Override
	public FreeBoardDTO getFreeBoardByNo(int freeNo) {
		return freeBoardMapper.selectFreeBoardByNo(freeNo);
	}
	
	@Override
	public void modifyFreeBoard(HttpServletRequest request, HttpServletResponse response) {

		String freeTitle = request.getParameter("freeTitle");
		String freeContent = request.getParameter("freeContent");
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		
		FreeBoardDTO freeBoard = FreeBoardDTO.builder()
				.freeTitle(freeTitle)
				.freeContent(freeContent)
				.freeNo(freeNo)
				.build();
		
		int result = freeBoardMapper.updateFreeBoard(freeBoard);
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {			
				out.println("alert('게시글 수정에 성공하였습니다.');");
				out.println("location.href='/freeboard/detail?freeNo=" + freeBoard.getFreeNo() + "';");
			} else {
				out.println("alert('게시글 수정에 실패하였습니다.');");
				out.println("history.back();");
			}
			out.println("</script>");			
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void removeFreeBoard(HttpServletRequest request, HttpServletResponse response) {
		
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));		
		int result = freeBoardMapper.deleteFreeBoard(freeNo);
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {
				out.println("alert('게시글이 삭제되었습니다.');");
				out.println("location.href='" + request.getContextPath() + "/freeboard/list';");
			} else {
				out.println("alert('게시글이 삭제되지 않았습니다.");
				out.println("history.back();");
			}
			out.println("</script>");
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	
	

}
