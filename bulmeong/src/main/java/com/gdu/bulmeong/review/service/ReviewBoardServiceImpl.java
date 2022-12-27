package com.gdu.bulmeong.review.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.bulmeong.freeboard.domain.FreeBoardDTO;
import com.gdu.bulmeong.review.domain.ReviewBoardDTO;
import com.gdu.bulmeong.review.mapper.ReviewBoardMapper;
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
	
	
	

}
