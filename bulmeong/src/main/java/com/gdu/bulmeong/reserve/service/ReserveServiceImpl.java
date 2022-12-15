package com.gdu.bulmeong.reserve.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.reserve.mapper.ReserveMapper;

@Service
public class ReserveServiceImpl implements ReserveService {
	
	@Autowired 
	private ReserveMapper reserveMapper;
	 
	@Override
	public Map<String, Object> getTentByCampNo(int campNo) {
		Map<String, Object> result = new HashMap<>();
		result.put("tentList", reserveMapper.selectTentByCampNo(campNo));
		result.put("camp", reserveMapper.selectCampByCampNo(campNo));
		return result;
	}
	
	@Override
	public void getInfoReserve(HttpServletRequest request) {
		
		int campNo = Integer.parseInt(request.getParameter("campNo"));
		int tentNo = Integer.parseInt(request.getParameter("tentNo"));
		String RESERVE_BEGIN_DATE = request.getParameter("RESERVE_BEGIN_DATE");
		String RESERVE_END_DATE = request.getParameter("RESERVE_END_DATE");
		String RESERVE_COUNT = request.getParameter("RESERVE_COUNT");
		
		
		
	}
	
}
