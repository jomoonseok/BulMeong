package com.gdu.bulmeong.qna.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.bulmeong.qna.domain.QnaDTO;
import com.gdu.bulmeong.qna.mapper.QnaMapper;
import com.gdu.bulmeong.util.PageUtil;
import com.gdu.bulmeong.util.SecurityUtil;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class QnaServiceImpl implements QnaService {
	
	@Autowired 
	private QnaMapper qnaMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Autowired
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
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
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
		
		QnaDTO qna = new QnaDTO();
		qna.setId(id);
		qna.setQnaTitle(qnaTitle);
		qna.setQnaContent(qnaContent);
		qna.setQnaIp(qnaIp);
		
		
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
	public int addAnswer(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String qnaTitle = securityUtil.preventXSS(request.getParameter("qnaTitle"));
		String qnaContent = securityUtil.preventXSS(request.getParameter("qnaContent"));
		String qnaIp = request.getRemoteAddr();
		
		int depth = Integer.parseInt(request.getParameter("depth"));
		int qnaGroupNo = Integer.parseInt(request.getParameter("qnaGroupNo"));
		
		QnaDTO qna = new QnaDTO();
		qna.setDepth(depth);
		qna.setQnaGroupNo(qnaGroupNo);
		
		qnaMapper.updatePreviousAnswer(qna);
		
		QnaDTO answer = new QnaDTO();
		answer.setId(id);
		answer.setQnaTitle(qnaTitle);
		answer.setQnaContent(qnaContent);
		answer.setQnaIp(qnaIp);
		answer.setDepth(depth + 1);
		answer.setQnaGroupNo(qnaGroupNo);
		
		return qnaMapper.insertAnswer(answer);
	}
	
	
	// 삭제
	@Override
	public int removeQna(int qnaNO) {
		return qnaMapper.deleteQna(qnaNO);
	}
	
}
