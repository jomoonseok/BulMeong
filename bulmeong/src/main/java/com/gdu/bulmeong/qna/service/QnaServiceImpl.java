package com.gdu.bulmeong.qna.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.bulmeong.qna.domain.QnaDTO;
import com.gdu.bulmeong.qna.mapper.QnaMapper;
import com.gdu.bulmeong.util.PageUtil;



@Service
public class QnaServiceImpl implements QnaService {
	
	@Autowired 
	private QnaMapper qnaMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Override
	public void findQnaList(HttpServletRequest request, Model model) {
		
		// 파라미터 page
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		// 전체 개시글 개수
		int totalRecord = qnaMapper.selectQnaCount();
		
		// recordPerPage
		int recordPerPage = 10;
		
		// 페이징 계산
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		// DB에서 목록 가져오기
		List<QnaDTO> qnaList = qnaMapper.selectQnaList(map);
		
		// 뷰로 보낼 모델(데이터)
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/qna/list"));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		
	}
	
}
