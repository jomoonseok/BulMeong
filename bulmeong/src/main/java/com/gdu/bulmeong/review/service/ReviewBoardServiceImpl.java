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
		// int totalRecord = freeBoardMapper.selectFindFreeboardsCount(map);
		int totalRecord = 5;
		

		int recordPerPage = 10;
		pageUtil.setSearchPageUtil(page, totalRecord, recordPerPage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		

		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getSearchPaging(request.getContextPath() + "/reviewBoard/list"));
		
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
		String nickname = loginUser.getNickname();
		String reviewTitle = request.getParameter("reviewTitle");
		int campNo = Integer.parseInt(request.getParameter("campNo"));
		int reviewStar = Integer.parseInt(request.getParameter("reviewStar"));
		String reviewContent = request.getParameter("reviewContent");
		String reviewIp = request.getRemoteAddr();
		
		ReviewBoardDTO reviewBoard = ReviewBoardDTO.builder()
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
				out.println("if(confirm('게시글 등록이 완료되었습니다. 확인하러 가시겠습니까?')) { location.href='" + request.getContextPath() + "/reviewboard/detail?reviewNo=" + reviewBoard.getReviewNo() + "'}");
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
