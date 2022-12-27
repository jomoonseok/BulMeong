package com.gdu.bulmeong.camp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gdu.bulmeong.camp.domain.CampDTO;

public interface CampService {
	
	public String getCampInfo();               // 고캠핑 api 받아오기 (json)
	public List<CampDTO> parseCampInfo();      // 받아온 api 파싱해서 List에 CampDTO 담기
	public void addCampInfoToDb(HttpServletResponse response);             // 파싱이 완료된 CampDTO를 DB에 삽입하기
	
	public Map<String, Object> getCampList(HttpServletRequest request);  // DB에서 CampDTO List로 받아오기
	public Map<String, Object> getCampListOption(HttpServletRequest request); 
	
	public void getDetailList(HttpServletRequest request, Model model);
	public void deleteAllCamp(HttpServletResponse response);
	
}
