package com.gdu.bulmeong.camp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.bulmeong.camp.domain.CampDTO;
import com.gdu.bulmeong.camp.mapper.CampMapper;
import com.gdu.bulmeong.util.PageUtil;

@Service
public class CampServiceImpl implements CampService {
	
	@Autowired
	private CampMapper campMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Override
	public String getCampInfo() {
		
		// 한국관광공사_고캠핑 정보 조회서비스_GW
//		String serviceKey = "tuKJQnWosgochz4Xdrz/EnE0fod1HOfyo0ZK7gAZMn7bUHdBQNpbTN6zksDAGe2KOWYJLpKMYuvDJWjBI5aAvQ==";
		String serviceKey = "0+KGdm6YrHSXA95CGIey5Sk0Sahzg8aiIbzdSRfUSCSABUGREXTJKtUr3IO+48dPKEQEZudz2n78Q/tx5pHIBQ==";
		
		
		
		/*
			numOfRows(한페이지결과수) : 10
			pageNo(페이지번호) : 1
			MobileOS(OS구분) : IOS(아이폰), AND(안드로이드), WIN(윈도우폰), ETC(기타)
			MobileApp(서비스명(어플명)) : bulMeong
			type(타입) : json
		 */
		
		StringBuilder urlBuilder = new StringBuilder();
		try {
			urlBuilder.append("http://apis.data.go.kr/B551011/GoCamping/basedList");
			urlBuilder.append("?numOfRows=30");
			urlBuilder.append("&MobileOS=ETC");
			urlBuilder.append("&MobileApp=bulMeong");
			urlBuilder.append("&serviceKey=").append(URLEncoder.encode(serviceKey, "UTF-8"));
			urlBuilder.append("&_type=json");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String apiURL = urlBuilder.toString();
		
		URL url = null;
		HttpURLConnection con = null;
		try {
			url = new URL(apiURL);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		con.disconnect();
		
		return sb.toString();
	}
	
	@Override
	public List<CampDTO> parseCampInfo() {
		
		String campInfo = getCampInfo();
		List<CampDTO> campList = new ArrayList<>();

		JSONObject obj = new JSONObject(campInfo);
		JSONObject obj2 = new JSONObject(obj.get("response").toString());
		JSONObject obj3 = new JSONObject(obj2.get("body").toString());
		JSONObject obj4 = new JSONObject(obj3.get("items").toString());
		JSONArray arr = new JSONArray(obj4.getJSONArray("item"));
		
		for(int i = 0; i < arr.length(); i++) {
			JSONObject obj5 = arr.getJSONObject(i);  // JSON 객체 obj5에 파싱이 완료된 각 캠핑장의 정보 입력
			
			String facltNm = obj5.getString("facltNm");
			String intro = obj5.getString("intro");
			String lineIntro = obj5.getString("lineIntro");
			String featureNm = obj5.getString("featureNm");
			String sbrsCl = obj5.getString("sbrsCl");
			String firstImageUrl = obj5.getString("firstImageUrl");
			String themaEnvrnCl = obj5.getString("themaEnvrnCl");
			String eqpmnLendCl = obj5.getString("eqpmnLendCl");
			String tourEraCl = obj5.getString("tourEraCl");
			String induty = obj5.getString("induty");
			String allar = obj5.getString("allar");
			String operPdCl = obj5.getString("operPdCl");
			String hvofBgnde = obj5.getString("hvofBgnde");
			String hvofEnddle = obj5.getString("hvofEnddle");
			String prmisnDe = obj5.getString("prmisnDe");
			String operDeCl = obj5.getString("operDeCl");
			String toiletCo = obj5.getString("toiletCo");
			String extshrCo = obj5.getString("extshrCo");
			String brazierCl = obj5.getString("brazierCl");
			String glampInnerFclty = obj5.getString("glampInnerFclty");
			String caravInnerFclty = obj5.getString("caravInnerFclty");
			String lctCl = obj5.getString("lctCl");
			String addr1 = obj5.getString("addr1");
			String doNm = obj5.getString("doNm");
			String sigunguNm = obj5.getString("sigunguNm");
			String addr2 = obj5.getString("addr2");
			String zipcode = obj5.getString("zipcode");
			String mapX = obj5.getString("mapX");
			String mapY = obj5.getString("mapY");
			String homepage = obj5.getString("homepage");
			String tel = obj5.getString("tel");
			String createdtime = obj5.getString("createdtime");
			String modifiedtime = obj5.getString("modifiedtime");
			
			
			CampDTO camp = CampDTO.builder()
					.facltNm(facltNm).intro(intro).lineIntro(lineIntro).featureNm(featureNm).sbrsCl(sbrsCl).firstImageUrl(firstImageUrl).themaEnvrnCl(themaEnvrnCl).eqpmnLendCl(eqpmnLendCl).tourEraCl(tourEraCl).induty(induty)
					.allar(allar).operPdCl(operPdCl).hvofBgnde(hvofBgnde).hvofEnddle(hvofEnddle).prmisnDe(prmisnDe).operDeCl(operDeCl).toiletCo(toiletCo).extshrCo(extshrCo).brazierCl(brazierCl).glampInnerFclty(glampInnerFclty).caravInnerFclty(caravInnerFclty)
					.lctCl(lctCl).addr1(addr1).doNm(doNm).sigunguNm(sigunguNm).addr2(addr2).zipcode(zipcode).mapX(mapX).mapY(mapY).homepage(homepage).tel(tel).createdtime(createdtime).modifiedtime(modifiedtime).build();
					
			campList.add(camp);
		}
		
		return campList;
	}
	
	@Override
	public void addCampInfoToDb(HttpServletResponse response) {
		
//		campMapper.deleteCampApi();  // 캠프 데이터 전부 삭제 (초기화용)
		
		List<CampDTO> campList = parseCampInfo();
		CampDTO camp = new CampDTO();
		int result = 0;
		
		for(int i = 0; i < campList.size(); i++) {
			camp = campList.get(i);
			result += campMapper.insertCampApi(camp); 
		}
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result == campList.size()) {
				out.println("<script>");
				out.println("alert('실행결과 : 성공')");
				out.println("location.href='/'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('실행결과 : 오류')");
				out.println("history.back()");
				out.println("</script>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Map<String, Object> getCampList(HttpServletRequest request) {
		
		int page = Integer.parseInt(request.getParameter("page"));
		int campCount = campMapper.selectCampCount();
		
		pageUtil.setPageUtil(page, campCount, 5);
		
		Map<String, Object> map = new HashMap<>();
		map.put("begin", pageUtil.getBegin() - 1);
//		map.put("end", pageUtil.getEnd());
		map.put("end", pageUtil.getRecordPerPage());
		
		List<CampDTO> camp = campMapper.selectAllCamp(map);
		
		Map<String, Object> result = new HashMap<>();
		result.put("campList", camp);
		result.put("pageUtil", pageUtil);
		result.put("campCount", campCount);
		
		return result;
	}
	
	@Override
	public void deleteAllCamp(HttpServletResponse response) {
		int result = 0;
		result = campMapper.deleteCampApi();
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 1) {
				out.println("<script>");
				out.println("alert('실행결과 : 성공')");
				out.println("location.href='/'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('실행결과 : 오류')");
				out.println("history.back()");
				out.println("</script>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Map<String, Object> getCampListOption(HttpServletRequest request) {
		
		String query = request.getParameter("query"); 
		String doNm = request.getParameter("doNm"); 
		String induty = request.getParameter("induty");
		String themaEnvrnCl = request.getParameter("themaEnvrnCl");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("query", query);
		map.put("doNm", doNm);
		map.put("induty", induty);
		map.put("themaEnvrnCl", themaEnvrnCl);

		
		Map<String, Object> result = new HashMap<>();
		result.put("campList", campMapper.selectCampByOption(map));
		
		return result;
	}
	
	@Override
	public void getDetailList(HttpServletRequest request, Model model) {
		int campNo = Integer.parseInt(request.getParameter("campNo"));
		model.addAttribute("camp", campMapper.selectCampByNo(campNo));
		
	}
	
	@Override
	public Map<String, Object> getCampListByJjim(int campNo) {
		Map<String, Object> result = new HashMap<>();
		result.put("jjim", campMapper.selectAllCampByJjim(campNo));
		return result;
	}
}
