package com.gdu.bulmeong.reserve.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.reserve.mapper.ReserveMapper;

@Service
public class ReserveServiceImpl implements ReserveService {
	
	@Autowired 
	private ReserveMapper reserveMapper;
	 
	@Override
	public Map<String, Object> getTentByCampNo(int campNo) {
		System.out.println(campNo);
		Map<String, Object> result = new HashMap<>();
		result.put("tentList", reserveMapper.selectTentByCampNo(campNo));
		result.put("camp", reserveMapper.selectCampByCampNo(campNo));
		return result;
	}
	
	
	
}
