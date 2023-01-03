package com.gdu.bulmeong.qna.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.bulmeong.qna.domain.QnaDTO;
import com.gdu.bulmeong.qna.mapper.QnaMapper;
import com.gdu.bulmeong.util.PageUtil;
import com.gdu.bulmeong.util.SecurityUtil;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class QnaServiceImpl implements QnaService {
	
	private QnaMapper qnaMapper;
	private PageUtil pageUtil;
	public SecurityUtil securityUtil;
	
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
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		// DB에서 목록 가져오기
		List<QnaDTO> qnaList = qnaMapper.selectQnaList(map);
		
		// 뷰로 보낼 모델(데이터)
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("paging", pageUtil.getPaging("/qna/list"));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		
	}
	
	// 질문추가
	@Transactional
	@Override
	public int addQuestion(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String qnaTitle = request.getParameter("qnaTitle");
		String qnaContent = request.getParameter("qnaContent");
		String qnaIp = request.getRemoteAddr();
		int qnaState = Integer.parseInt(request.getParameter("qnaState"));
		
		QnaDTO qna = new QnaDTO();
		qna.setId(id);
		qna.setQnaTitle(qnaTitle);
		qna.setQnaContent(qnaContent);
		qna.setQnaIp(qnaIp);
		qna.setQnaState(qnaState);
		
		int result = 0;
		int insertResult = qnaMapper.insertQuestion(qna);

		if(insertResult == 1) {
			result = qnaMapper.updateGroupNo(qna);
		}
		
		try {
			if(result > 0) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("window.close();");
				out.println("alert('작성되었습니다.');");
				out.println("opener.parent.location.reload();");
				out.println("</script>");
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	
	// 답변추가
	@Transactional
	@Override
	public void addAnswer(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String writeAnswer = securityUtil.preventXSS(request.getParameter("writeAnswer"));
		String qnaIp = request.getRemoteAddr();
		String qnaTitle = "답변입니다.";
		
		int depth = Integer.parseInt(request.getParameter("depth"));
		int qnaGroupNo = Integer.parseInt(request.getParameter("qnaGroupNo"));
		
		QnaDTO answer = new QnaDTO();
		answer.setId(id);
		answer.setQnaContent(writeAnswer);
		answer.setQnaIp(qnaIp);
		answer.setDepth(depth);
		answer.setQnaGroupNo(qnaGroupNo);
		answer.setQnaTitle(qnaTitle);
		
		int result = qnaMapper.insertAnswer(answer);
		
		try {
			if(result > 0) {
				depth = 1;
				QnaDTO qna = new QnaDTO();
				qna.setDepth(depth);
				qna.setQnaGroupNo(qnaGroupNo);
				
				qnaMapper.updatePreviousAnswer(qna);
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('작성되었습니다.');");
				out.println("location.href='/qna/list';");
				out.println("</script>");
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	// 삭제
	@Override
	public int removeQna(int qnaNO) {
		return qnaMapper.deleteQna(qnaNO);
	}
	
}
