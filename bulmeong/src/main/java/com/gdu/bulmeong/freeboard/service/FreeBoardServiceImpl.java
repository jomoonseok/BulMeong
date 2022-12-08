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
import com.gdu.bulmeong.freeboard.mapper.FreeBoardMapper;
import com.gdu.bulmeong.util.PageUtil;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {
	
	@Autowired
	private FreeBoardMapper freeBoardMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Override
	public void getFreeList(Model model) {
		Map<String, Object> modelMap = model.asMap();
		HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		int totalRecord = freeBoardMapper.selectFreeListCount();
		// 오류나면 여기 //
		int recordPerPage = 5;
		// 오류나면 여기 //
		
		
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("freeBoardList", freeBoardMapper.selectFreeListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/freeboard/list"));

		
	}
	
	@Override
	public int increseFreeBoardHit(int freeNo) {
		return freeBoardMapper.updateHit(freeNo);
	}
	
	@Override
	public void addFreeBoard(HttpServletRequest request, HttpServletResponse response) {
		
		// HttpSession session = request.getSession();
		// UserDTO loginUser = (UserDTO)session.getAttribute("loginUser"); 
		
		// String nickname = loginUser.getNickname();
		String nickname = "nickname!";
		String freeTitle = request.getParameter("title");
		String freeContent = request.getParameter("content");
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
				out.println("else { location.href='" + request.getContextPath() + "/freeboard/list'}");
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
	
	

}
