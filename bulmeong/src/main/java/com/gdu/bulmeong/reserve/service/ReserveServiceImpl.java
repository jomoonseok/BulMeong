package com.gdu.bulmeong.reserve.service;

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

import com.gdu.bulmeong.reserve.domain.TentDTO;
import com.gdu.bulmeong.reserve.mapper.ReserveMapper;
import com.gdu.bulmeong.users.domain.UsersDTO;

@Service
public class ReserveServiceImpl implements ReserveService {
	
	@Autowired 
	private ReserveMapper reserveMapper;
	
	@Override
	public Map<String, Object> getCampByCampNo(int campNo) {
		Map<String, Object> result = new HashMap<>();
		result.put("camp", reserveMapper.selectAllCampByCampNo(campNo));
		return result;
	}
	
	@Override
	public Map<String, Object> getTentByCampNo(HttpServletRequest request) {
		int campNo = Integer.parseInt(request.getParameter("campNo"));
		String reserveBeginDate = request.getParameter("reserveBeginDate");
		String reserveEndDate = request.getParameter("reserveEndDate");
		
		Map<String, Object> map = new HashMap<>();
		map.put("campNo", campNo);
		map.put("reserveBeginDate", reserveBeginDate);
		map.put("reserveEndDate", reserveEndDate);
		
		Map<String, Object> result = new HashMap<>();
		result.put("tentList", reserveMapper.selectAllTentByCampNo(campNo));
		result.put("camp", reserveMapper.selectAllCampByCampNo(campNo));
		result.put("reservedTent", reserveMapper.selectReservedTent(map));
		
		return result;
	}
	
	@Override
	public void getInfoReserve(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser");
		
		int campNo = Integer.parseInt(request.getParameter("campNo"));
		int tentNo = Integer.parseInt(request.getParameter("tentNo"));
		String reserveBeginDate = request.getParameter("reserveBeginDate");
		String reserveEndDate = request.getParameter("reserveEndDate");
		String reserveBeginEndDate = reserveBeginDate + " ~ " + reserveEndDate;
		String reserveDate = request.getParameter("reserveDate");
		int reserveCount = Integer.parseInt(request.getParameter("reserveCount"));
		int reserveDate2 = Integer.parseInt(request.getParameter("reserveDate2"));
		TentDTO tent = reserveMapper.selectTentByTentNo(tentNo);
		int sum = tent.getTentSum();
		int tentCategory = tent.getTentCategory();
		
		String strCategory = "";
		switch(tentCategory) {
		case 0: strCategory = "글램핑"; break;
		case 1: strCategory = "카라반"; break;
		case 2: strCategory = "일반야영장"; break;
		case 3: strCategory = "자동차야영장";
		}
		
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("camp", reserveMapper.selectCampByCampNo(campNo));
		model.addAttribute("tent", tent);
		model.addAttribute("reserveBeginDate", reserveBeginDate);
		model.addAttribute("reserveEndDate", reserveEndDate);
		model.addAttribute("reserveBeginEndDate", reserveBeginEndDate);
		model.addAttribute("reserveDate", reserveDate);
		model.addAttribute("reserveCount", reserveCount);
		model.addAttribute("strCategory", strCategory);
		model.addAttribute("reserveDate2", reserveDate2);
	}
	
	@Override
	public void addInfoReserve(HttpServletRequest request, HttpServletResponse response) {
		int campNo = Integer.parseInt(request.getParameter("campNo"));
		int tentNo = Integer.parseInt(request.getParameter("tentNo"));
		String id = request.getParameter("id");
		String reserveName = request.getParameter("reserveName");
		/* String mobile = request.getParameter("mobile"); */
		int reserveCount = Integer.parseInt(request.getParameter("reserveCount"));
		String reserveBeginDate = request.getParameter("reserveBeginDate");
		String reserveEndDate = request.getParameter("reserveEndDate");
		String reserveSum = request.getParameter("reserveSum");
		
		Map<String, Object> map = new HashMap<>();
		map.put("campNo", campNo);
		map.put("tentNo", tentNo);
		map.put("id", id);
		map.put("reserveName", reserveName);
		map.put("reserveCount", reserveCount);
		map.put("reserveBeginDate", reserveBeginDate);
		map.put("reserveEndDate", reserveEndDate);
		map.put("reserveSum", reserveSum);
		
		int result = reserveMapper.insertReserve(map);
		
		PrintWriter out = null;
		try {
			if(result > 0) {
				out = response.getWriter();
				response.setContentType("text/html; charset=UTF-8");
				out.println("<script>");
				out.println("alert('삽입 성공')");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('삽입 실패')");
				out.println("</script>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
