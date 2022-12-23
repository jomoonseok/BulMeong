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
import com.gdu.bulmeong.util.SecurityUtil;



@Service
public class QnaServiceImpl implements QnaService {
	
	@Autowired 
	private QnaMapper qnaMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	// 리스트
	@Override
	public void findQnaList(HttpServletRequest request, Model model) {

		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));

		int totalRecord = qnaMapper.selectQnaCount();

		int recordPerPage = 10;

		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		System.out.println(map);
		
		// DB에서 목록 가져오기
		List<QnaDTO> qnaList = qnaMapper.selectQnaList(map);
		
		// 뷰로 보낼 모델(데이터)
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/qna/list"));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		
	}
	/*
	// 질문추가
	@Override
	public int addQuestion(HttpServletRequest request) {
		//String writer = SecurityUtil.sha256();
		return 0;
	}
	
	
	// 답변추가
	@Override
	public int addAnswer(HttpServletRequest requestu) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	// 삭제
	@Override
	public int removeNotice(int qnaNO) {
		// TODO Auto-generated method stub
		return 0;
	}
	*/
}
