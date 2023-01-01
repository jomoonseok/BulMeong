package com.gdu.bulmeong.review.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.bulmeong.review.domain.ReviewBoardDTO;
import com.gdu.bulmeong.review.mapper.ReviewBoardMapper;
import com.gdu.bulmeong.users.domain.UsersDTO;
import com.gdu.bulmeong.util.PageUtil;

@Service
public class ReviewBoardServiceImpl implements ReviewBoardService {
	
	@Autowired
	private ReviewBoardMapper reviewBoardMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	
	@Override
	public void getReviewList(Model model) {
		Map<String, Object> modelMap = model.asMap();
		HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int totalRecord = 5;
		

		int recordPerPage = 10;
		pageUtil.setSearchPageUtil(page, totalRecord, recordPerPage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("recordPerPage", recordPerPage);
		

		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getSearchPaging("/reviewBoard/list"));
		
		List<ReviewBoardDTO> reivewBoard = reviewBoardMapper.reviewList(map);
		

		model.addAttribute("reviewBoardList", reivewBoard);
		
	}
	
	
	@Override
	public int increseReviewBoardHit(int reviewNo) {
		return reviewBoardMapper.updateHit(reviewNo);
	}
	
	@Override
	public void addReviewBoard(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser"); 
		String id = loginUser.getId();
		String nickname = loginUser.getNickname();
		String reviewTitle = request.getParameter("reviewTitle");
		int campNo = Integer.parseInt(request.getParameter("campNo"));
		int reviewStar = Integer.parseInt(request.getParameter("reviewStar"));
		String reviewContent = request.getParameter("reviewContent");
		String reviewIp = request.getRemoteAddr();
		
		ReviewBoardDTO reviewBoard = ReviewBoardDTO.builder()
				.id(id)
				.nickname(nickname)
				.reviewTitle(reviewTitle)
				.campNo(campNo)
				.reviewStar(reviewStar)
				.reviewContent(reviewContent)
				.reviewIp(reviewIp)
				.build();

		int result = reviewBoardMapper.insertReviewBoard(reviewBoard);
			
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {
				out.println("if(confirm('게시글 등록이 완료되었습니다. 확인하러 가시겠습니까?')) { location.href='/reviewboard/detail?reviewNo=" + reviewBoard.getReviewNo() + "'}");
				out.println("else { location.href='/reviewBoard/list'}");
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
	public Map<String, Object> getCampcampNofacltNm() {
		List<Map<String, Object>> camp = reviewBoardMapper.selectCampcampNofacltNm();
		Map<String, Object> result = new HashMap<>();
		result.put("camp", camp);
		return result;
	}
	
	@Override
	public ReviewBoardDTO getReviewBoardByNo(int reviewNo) {
		return reviewBoardMapper.selectReviewBoardByNo(reviewNo);
	}
	
	@Override
	public void modifyReviewBoard(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser");
		
		// 1. session에 loginUser가 없을때 권한 막기
		if (loginUser == null) {
			
			try {	
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");			
				out.println("alert('세션이 만료되었습니다.');");
				out.println("location.href='/reviewboard/list';");
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
			
				String reviewTitle = request.getParameter("reviewTitle");
				int campNo = Integer.parseInt(request.getParameter("campNo"));
				int reviewStar = Integer.parseInt(request.getParameter("reviewStar"));
				String reviewContent = request.getParameter("reviewContent");
				int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
				
				ReviewBoardDTO reviewBoard = ReviewBoardDTO.builder()
						.reviewTitle(reviewTitle)
						.campNo(campNo)
						.reviewStar(reviewStar)
						.reviewContent(reviewContent)
						.reviewNo(reviewNo)
						.build();
				
				int result = reviewBoardMapper.updateReviewBoard(reviewBoard);
				
				try {
					
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					
					out.println("<script>");
					if(result > 0) {			
						out.println("alert('게시글 수정에 성공하였습니다.');");
						out.println("location.href='/reviewboard/detail?reviewNo=" + reviewBoard.getReviewNo() + "';");
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
				out.println("location.href='/reviewboard/list';");
				out.println("</script>");			
				out.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	@Override
	public void removeReviewBoard(HttpServletRequest request, HttpServletResponse response) {
		
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));		
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser");
		

		
		// 1. session에 loginUser가 없을때 권한 막기
		if (loginUser == null) {
			
			try {	
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");			
				out.println("alert('세션이 만료되었습니다.');");
				out.println("location.href='/reviewboard/list';");
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
			
			int result = reviewBoardMapper.deleteReviewBoard(reviewNo);
			
			try {
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				if(result > 0) {
					out.println("alert('게시글이 삭제되었습니다.');");
					out.println("location.href='/reviewboard/list';");
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
	
	
	
	
	/*
	 * @Override public void addReview(HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * HttpSession session = request.getSession(); UsersDTO loginUser =
	 * (UsersDTO)session.getAttribute("loginUser"); // 프로필사진 String nickname =
	 * loginUser.getNickname(); // 작성일 // 별점 // int reviewStar =
	 * Integer.parseInt(request.getParameter("reviewStar")); String reviewContent =
	 * request.getParameter("reviewContent");
	 * 
	 * ReviewBoardDTO reviewBoard = ReviewBoardDTO.builder() .nickname(nickname)
	 * .reviewContent(reviewContent) .build();
	 * 
	 * int result = reviewBoardMapper.insertReview(reviewBoard);
	 * 
	 * try {
	 * 
	 * response.setContentType("text/html; charset=UTF-8"); PrintWriter out =
	 * response.getWriter();
	 * 
	 * out.println("<script>"); if(result > 0) {
	 * out.println("alert('리뷰가 등록되었습니다.')");
	 * out.println("location.href='/camp/reviewList'}"); } else {
	 * out.println("alert('게시글 등록에 실패하였습니다.');"); out.println("history.back();"); }
	 * out.println("</script>"); out.close();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * 
	 * }
	 */

}
