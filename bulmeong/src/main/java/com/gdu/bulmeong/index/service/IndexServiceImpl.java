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
		
		LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);
		
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
		
		
		// 현재 시간대 구하기
		LocalDateTime nowTime = LocalDateTime.now();
		String formatNow = nowTime.format(DateTimeFormatter.ofPattern("HH00"));  // 현재 시간대(ex:1800)
		
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
		
		JSONArray arr = new JSONArray();
		arr = obj4.getJSONArray("item");
		
		WeatherDTO weather = new WeatherDTO();
		JSONObject obj6 = null;
		
		String category;
		String fcstDate;
		String fcstTime;
	    String fcstValue;
		
	    List<WeatherDTO> list = new ArrayList<>();
	    Map<String, Object> result = new HashMap<>();
	    
		for(int i = 0; i < arr.length(); i++) {
			obj6 = arr.getJSONObject(i);  // 키값으로 값을 불러오기 위해서 다시 JSONObject에 JSONArray의 값을 하나하나 담아옴
			category = obj6.getString("category");
			fcstValue = obj6.getString("fcstValue");
			fcstDate = obj6.getString("fcstDate");
			fcstTime = obj6.getString("fcstTime");
			
			switch (category) {
	            case "TMP":
	                category = "기온";		// 
	                break;
	            case "REH":
	                category = "습도";      // %
	                break;
	            case "PTY":
	            	category = "강수형태";  // 없음0 , 비1, 비/눈2, 눈3, 소나기4
	            	break;
	            case "SKY":
	                category = "하늘상태";  // 맑음1, 구름많음3, 흐림4
	                break;
			}
			
			weather = WeatherDTO.builder()
					.category(category).fcstValue(fcstValue).fcstValue(fcstValue).fcstDate(fcstDate).fcstTime(fcstTime).build();
			list.add(weather);
		}
		
		
		result.put("weather", list);
		result.put("formatNow", formatNow);
		
		return result;
	}
	
}
