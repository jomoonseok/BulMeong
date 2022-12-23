package com.gdu.bulmeong.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.admin.domain.AdminTentDTO;
import com.gdu.bulmeong.admin.mapper.AdminMapper;
import com.gdu.bulmeong.camp.domain.CampDTO;
import com.gdu.bulmeong.camp.mapper.CampMapper;
import com.gdu.bulmeong.util.PageUtil;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired 
	private AdminMapper adminMapper;
	
	@Autowired
	private CampMapper campMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	public Map<String, Object> getAllUser() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("users", adminMapper.selectAllUser());
		
		return map;
	}
	
	public Map<String, Object> getAllCamp(HttpServletRequest request) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int campCount = campMapper.selectCampCount();
		pageUtil.setPageUtil(page, campCount, 10);
		
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("end", pageUtil.getRecordPerPage());
		
		List<CampDTO> camp = adminMapper.selectAllCampAdmin(map);
		
		Map<String, Object> result = new HashMap<>();
		result.put("campList", camp);
		result.put("pageUtil", pageUtil);
		result.put("campCount", campCount);
		
		return result;
	}
	
	@Override
	public Map<String, Object> getAllTent(HttpServletRequest request) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int tentCount = adminMapper.selectAllTentCount();
		pageUtil.setPageUtil(page, tentCount, 10);
		
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("end", pageUtil.getRecordPerPage());
		
		List<AdminTentDTO> tent = adminMapper.selectAllTentAdmin(map);
		
		Map<String, Object> result = new HashMap<>();
		result.put("tentList", tent);
		result.put("pageUtil", pageUtil);
		result.put("tentCount", tentCount);
		return result;
	}
	

}
