package com.gdu.bulmeong.reserve.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.bulmeong.reserve.domain.TentDTO;
import com.gdu.bulmeong.reserve.mapper.ReserveMapper;

@Service
public class ReserveServiceImpl implements ReserveService {
	
	@Autowired 
	private ReserveMapper reserveMapper;
	 
	@Override
	public Map<String, Object> getTentByCampNo(int campNo) {
		Map<String, Object> result = new HashMap<>();
		result.put("tentList", reserveMapper.selectAllTentByCampNo(campNo));
		result.put("camp", reserveMapper.selectAllCampByCampNo(campNo));
		return result;
	}
	
	@Override
	public void getInfoReserve(HttpServletRequest request, Model model) {
		
		int campNo = Integer.parseInt(request.getParameter("campNo"));
		int tentNo = Integer.parseInt(request.getParameter("tentNo"));
		String RESERVE_BEGIN_DATE = request.getParameter("RESERVE_BEGIN_DATE");
		String RESERVE_END_DATE = request.getParameter("RESERVE_END_DATE");
		String RESERVE_BEGIN_END_DATE = RESERVE_BEGIN_DATE + " ~ " + RESERVE_END_DATE;
		String RESERVE_DATE = request.getParameter("RESERVE_DATE");
		int RESERVE_COUNT = Integer.parseInt(request.getParameter("RESERVE_COUNT"));
		
		TentDTO tent = reserveMapper.selectTentByTentNo(tentNo);
		int sum = RESERVE_COUNT * tent.getTentSum();
		int tentCategory = tent.getTentCategory();
		
		String strCategory = "";
		switch(tentCategory) {
		case 0: strCategory = "글램핑"; break;
		case 1: strCategory = "카라반"; break;
		case 2: strCategory = "일반야영장"; break;
		case 3: strCategory = "자동차야영장";
		}
		
		model.addAttribute("camp", reserveMapper.selectCampByCampNo(campNo));
		model.addAttribute("tent", tent);
		model.addAttribute("reserveBeginEndDate", RESERVE_BEGIN_END_DATE);
		model.addAttribute("reserveDate", RESERVE_DATE);
		model.addAttribute("reserveCount", RESERVE_COUNT);
		model.addAttribute("strCategory", strCategory);
		model.addAttribute("sum", sum);
		
		
		
	}
	
}
