package com.gdu.bulmeong.freeboard.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.bulmeong.freeboard.domain.FreeBoardDTO;
import com.gdu.bulmeong.freeboard.mapper.FreeBoardCmtMapper;
import com.gdu.bulmeong.freeboard.mapper.FreeBoardLikeMapper;
import com.gdu.bulmeong.freeboard.mapper.FreeBoardMapper;
import com.gdu.bulmeong.users.domain.UsersDTO;
import com.gdu.bulmeong.util.PageUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FreeBoardServiceImpl implements FreeBoardService {
	
	private FreeBoardMapper freeBoardMapper;
	private FreeBoardCmtMapper freeBoardCmtMapper;
	private FreeBoardLikeMapper freeBoardLikeMapper;
	private PageUtil pageUtil;
	
	@Override
	public void getFreeList(Model model) {
		Map<String, Object> modelMap = model.asMap();
		HttpServletRequest request = (HttpServletRequest) modelMap.get("request");

		// 1-1. [검색기능] 파라미터 받아오기
		String dateColumn = request.getParameter("dateColumn");
		String column = request.getParameter("column");
		String query = request.getParameter("query");
		
		// 1-2. [검색기능] 파라미터 받아서 map에 넣기
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dateColumn", dateColumn);
		map.put("column", column);
		map.put("query", query);
		
		// 1-3. [검색기능] model에 검색기능 값 넣어주기
		model.addAttribute("dateColumn", dateColumn);
		model.addAttribute("column", column);
		model.addAttribute("query", query);
		
		 
		// 2-1. [페이징] '0'일 경우 1페이지로 가기
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int totalRecord = freeBoardMapper.selectFindFreeboardsCount(map);
		
		// 2-2. [페이징] 표시할 페이지 수
		int recordPerPage = 10;
		pageUtil.setSearchPageUtil(page, totalRecord, recordPerPage);
		map.put("begin", pageUtil.getBegin());
		map.put("recordPerPage", recordPerPage);
		
		// 2-3. [페이징] model에 값 넣어주기
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getSearchPaging("/freeboard/list"));
		
		
		// 3-1. [게시글 리스트] freeBoardDTO 받아오기 (Mapper에서)
		List<FreeBoardDTO> freeBoard = freeBoardMapper.selectFreeListByMap(map);
		
		// 3-2. [게시글 리스트] model에 freeBoard 값 넣어주기
		model.addAttribute("freeBoardList", freeBoard);
		
		// 4-1. [게시글 댓글수, 좋아요] 
		List<Integer> freeNo = new ArrayList<Integer>();
		List<Integer> cmtCount = new ArrayList<Integer>();
		for(int i = 0; i < freeBoard.size(); i++) {
			freeNo.add(freeBoard.get(i).getFreeNo());
			cmtCount.add(freeBoardCmtMapper.selectCmtCount(freeNo.get(i)));
		}
		List<Integer> freelike = new ArrayList<Integer>();
		for(int i = 0; i < freeBoard.size(); i++) {
			freeNo.add(freeBoard.get(i).getFreeNo());
			freelike.add(freeBoardLikeMapper.selectLikeCount(freeNo.get(i)));
		}

		// 4-2. [게시글 댓글수, 좋아요] model에 넣어주기 
		model.addAttribute("freeCmt", cmtCount);
		model.addAttribute("freelike", freelike);

		
		
		
		
	}
	
	
	@Override
	public int increseFreeBoardHit(int freeNo) {
		return freeBoardMapper.updateHit(freeNo);
	}
	
	@Override
	public void addFreeBoard(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser"); 
		String id = loginUser.getId();
		String nickname = loginUser.getNickname();
		String freeTitle = request.getParameter("freeTitle");
		String freeContent = request.getParameter("freeContent");
		String freeIp = request.getRemoteAddr();
		String profileImage = loginUser.getProfileImage();
		
		FreeBoardDTO freeBoard = FreeBoardDTO.builder()
				.id(id)
				.nickname(nickname)
				.freeTitle(freeTitle)
				.freeContent(freeContent)
				.freeIp(freeIp)
				.profileImage(profileImage)
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
		
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser");
		
		// 1. session에 loginUser가 없을때 권한 막기
		if (loginUser == null) {
			
			try {	
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");			
				out.println("alert('세션이 만료되었습니다.');");
				out.println("location.href='/freeboard/list';");
				out.println("</script>");			
				out.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		String id = loginUser.getId(); 		  // 로그인한사람
		
		String writer = request.getParameter("id"); // 작성자
		
		
		// 2. session에 loginUser가 작성자랑 같을 때 권한 주기
		if (id.equals(writer)) {
			
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
				
		// 3. session에 loginUser가 작성자랑 다를 때 권한 막기 ( 1.이랑 3.은 다른것임)
		} else {
		
			try {
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");			
				out.println("alert('수정 권한이 없습니다.');");
				out.println("location.href='/freeboard/list';");
				out.println("</script>");			
				out.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	
	@Override
	public void removeFreeBoard(HttpServletRequest request, HttpServletResponse response) {
		
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));		
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser");
		

		
		// 1. session에 loginUser가 없을때 권한 막기
		if (loginUser == null) {
			
			try {	
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");			
				out.println("alert('세션이 만료되었습니다.');");
				out.println("location.href='/freeboard/list';");
				out.println("</script>");			
				out.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String id = loginUser.getId(); 		  // 로그인한사람
		String writer = request.getParameter("id"); // 작성자
		
		// 2. session에 loginUser가 작성자랑 같을 때 권한 주기
		if (id.equals(writer)) {
			
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
		// 3. session에 loginUser가 작성자랑 다를 때 권한 막기
		} else {
			
			try {	
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");			
				out.println("alert('삭제 권한이 없습니다.');");
				out.println("history.back();");
				out.println("</script>");			
				out.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	

	
	

}
