package com.gdu.bulmeong.index.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.index.domain.WeatherDTO;
import com.gdu.bulmeong.index.mapper.IndexMapper;

@Service
public class IndexServiceImpl implements IndexService {
	
	@Autowired
	private IndexMapper indexMapper;
	
	@Override
	public Map<String, Object> getCampListIndex() {
		Map<String, Object> result = new HashMap<>();
		result.put("camp", indexMapper.selectAllCampIndex());
		return result;
	}
	
	@Override
	public Map<String, Object> getLocation(HttpServletRequest request) {
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuilder sb = null;
		
		String nx = "37.4730836";  // 기본값
		String ny = "126.8788276"; // 기본값
		
		String latitude = request.getParameter("latitude");  // 위도
		String longitude = request.getParameter("longitude");  // 경도
		
		if(latitude != null) {
			nx = latitude;
		}
		
		if(longitude != null) {
			ny = longitude;
		}
		
		String addrApiURL = "http://apis.vworld.kr/coord2jibun.do?x=" + ny + "&y=" + nx + "&output=json&epsg=epsg:4326&apiKey=BB41611F-4527-3F34-9020-F228FC6BE23A";
		try {
			url = new URL(addrApiURL);
			conn = (HttpURLConnection)url.openConnection();
	        
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
			
	        sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        
	        br.close();
	        conn.disconnect();
	        
		} catch (IOException e) {
			e.printStackTrace();
		} 
		

		Map<String, Object> result = new HashMap<>();
		result.put("location", sb.toString());
		
		return result;
	}
	
	
	@Override
	public Map<String, Object> getWeather(HttpServletRequest request) {
		
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuilder sb = null;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		
		// 시간
		LocalDate now = LocalDate.now();  // 오늘
        String formatedNow = now.format(formatter);  
        LocalDate tomorrow = LocalDate.now().plusDays(1);  // 내일
        String fmtTomorrow = tomorrow.format(formatter);
        LocalDate afterTomorrow = LocalDate.now().plusDays(2);  // 모레
        String fmtAfterTomorrow = afterTomorrow.format(formatter);
        
 		// 현재 시간에서 1시간 뒤 구하기
 		LocalDateTime nowTimePlus = LocalDateTime.now().plusHours(1);
 		String formatNow = nowTimePlus.format(DateTimeFormatter.ofPattern("HH00"));  // 현재부터 1시간 뒤
        
		String serviceKey = "tuKJQnWosgochz4Xdrz%2FEnE0fod1HOfyo0ZK7gAZMn7bUHdBQNpbTN6zksDAGe2KOWYJLpKMYuvDJWjBI5aAvQ%3D%3D";
		String pageNo = "1";
		String numOfRows = "1000";
		String dataType = "JSON";
		String base_date = formatedNow;
		String base_time = "0500";
		String latitude = request.getParameter("latitude");  // 위도
		String longitude = request.getParameter("longitude");  // 경도
		
		int intLatitude;
		int intlongitude;
		
		// 위도, 경도가 null일 때 넣어줄 기본값
		int nx = 37;  // 기본값
		int ny = 126; // 기본값
		
		if(latitude != null) {  // 위도 (nx)
			intLatitude = (int)Double.parseDouble(latitude);
		} else {
			intLatitude = nx;
		}
		
		if(longitude != null) {  // 경도 (ny)
			intlongitude = (int)Double.parseDouble(longitude);
		} else {
			intlongitude = ny;
		}
		
		
		String apiURL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst" 
						+ "?serviceKey=" + serviceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows + "&dataType=" + dataType + "&base_date=" + base_date + "&base_time=" + base_time + "&nx=" + intLatitude + "&ny=" + intlongitude;

		try {
			url = new URL(apiURL);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
	        
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
			
	        sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        
	        br.close();
	        conn.disconnect();
	        
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		JSONObject obj = new JSONObject(sb.toString());
		JSONObject obj2 = new JSONObject(obj.get("response").toString());
		JSONObject obj3 = new JSONObject(obj2.get("body").toString());
		JSONObject obj4 = new JSONObject(obj3.get("items").toString());  // {"item":[{"baseDate":"20221220","fcstTime":"0600","fcstValue":"-2","nx":37,"ny":126,"category":"TMP","baseTime":"0500","fcstDate":"20221220"}]}
		
		JSONArray arr = obj4.getJSONArray("item");
		JSONObject obj5 = null;
		
		// 3일의 날짜를 일별로 구분해서 전달하기 위한 작업
		// 사용할 객체
		WeatherDTO todayWeather = new WeatherDTO();          // 오늘 객체
		WeatherDTO tomorrowWeather = new WeatherDTO();       // 내일 객체
		WeatherDTO afterTomorrowWeather = new WeatherDTO();  // 모레 객체
		
		// 사용할 필드값
		String fcstDate;   // 예보일자
		String fcstTime;   // 예보시간
		String category;   // 항목
	    String fcstValue;  // 항목에 따른 값
	    int gubun;         // 사용여부를 가릴 필드
		
		// 객체를 담을 리스트
		List<WeatherDTO> todayList = new ArrayList<>();          
	    List<WeatherDTO> tomorrowList = new ArrayList<>();      
	    List<WeatherDTO> afterTomorrowList = new ArrayList<>();  
		
		for(int i = 0; i < arr.length(); i++) {
			obj5 = arr.getJSONObject(i);  // 키값으로 값을 불러오기 위해서 다시 JSONObject에 JSONArray의 값을 하나하나 담아옴
			
			// 각각의 object에서 weatherDTO 객체에 담을 필드의 값 뽑아 저장하기
			fcstDate = obj5.getString("fcstDate");
			fcstTime = obj5.getString("fcstTime");
			category = obj5.getString("category");
			fcstValue = obj5.getString("fcstValue");
			
			if(fcstTime.equals(formatNow)) {  // 한시간 뒤의 예보와 같은 데이터만 추출하기
				gubun = 0;
				switch (category) {
	            case "TMP":
	                category = "기온";		// °C
	                fcstValue = fcstValue + "°C";
	                gubun = 1;
	                break;
	            case "REH":
	                category = "습도";      // 90%
	                fcstValue = fcstValue + "%";
	                gubun = 1;
	                break;
	            case "PTY":
	            	category = "강수형태";  // 없음0 , 비1, 비 또는 눈2, 눈3, 소나기4
	            	switch(fcstValue) {
	            	case "0": fcstValue = "없음"; break;
	                case "1": fcstValue = "비"; break;
	                case "2": fcstValue = "비 또는 눈"; break;
	                case "3": fcstValue = "눈"; break;
	                case "4": fcstValue = "소나기"; break;
	                }
	            	gubun = 1;
	            	break;
	            case "POP":
	            	category = "강수확률";  // 60%
	            	fcstValue = fcstValue + "%";
	            	gubun = 1;
	            	break;
	            case "PCP":
	            	category = "강수량";
	            	gubun = 1;
	            	break;
	            case "SKY":
	                category = "하늘상태";  // 맑음1, 구름많음3, 흐림4
	                
	                switch(fcstValue) {
	                case "1": fcstValue = "맑음"; break;
	                case "3": fcstValue = "구름많음"; break;
	                case "4": fcstValue = "흐림"; break;
	                }
	                gubun = 1;
	                break;
				}
				
				if(gubun == 1 && fcstDate.equals(formatedNow)) {
					todayWeather = WeatherDTO.builder()
							.category(category).fcstValue(fcstValue).fcstDate(fcstDate).fcstTime(fcstTime).gubun(gubun).build();
					todayList.add(todayWeather);	
				} else if(gubun == 1 && fcstDate.equals(fmtTomorrow)) {
					tomorrowWeather = WeatherDTO.builder()
							.category(category).fcstValue(fcstValue).fcstDate(fcstDate).fcstTime(fcstTime).gubun(gubun).build();
					tomorrowList.add(tomorrowWeather);	
				} else if(gubun == 1 && fcstDate.equals(fmtAfterTomorrow)) {
					afterTomorrowWeather = WeatherDTO.builder()
							.category(category).fcstValue(fcstValue).fcstDate(fcstDate).fcstTime(fcstTime).gubun(gubun).build();
					afterTomorrowList.add(afterTomorrowWeather);
				}
				
			} // if
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("todayWeather", todayList);  // 오늘
		result.put("tomorrowWeather", tomorrowList);  // 내일
		result.put("afterTomorrowWeather", afterTomorrowList); // 모레  
		
		result.put("todayDate", formatedNow);  // 오늘 날짜
		result.put("tomorrowDate", fmtTomorrow);  // 내일 날짜
		result.put("afterTomorrowDate", fmtAfterTomorrow);  // 모레 날짜
		return result;
	}
	
	@Override
	public Map<String, Object> getCampListByJjim() {
		Map<String, Object> result = new HashMap<>();
		result.put("jjim", indexMapper.selectAllCampByJjim());
		return result;
	}
	
	
}
