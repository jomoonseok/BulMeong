package com.gdu.bulmeong.camp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.gdu.bulmeong.camp.domain.CampDTO;

@Service
public class CampServiceImpl implements CampService {
	
//	@Autowired 
//	private BulMeongMapper bulMeongMapper;
	 
	@Override
	public Map<String, Object> getCampInfo() {
		
		// 한국관광공사_고캠핑 정보 조회서비스_GW
		
		// 인증키(Decoding)
		String serviceKey = "tuKJQnWosgochz4Xdrz/EnE0fod1HOfyo0ZK7gAZMn7bUHdBQNpbTN6zksDAGe2KOWYJLpKMYuvDJWjBI5aAvQ==";
		
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
			urlBuilder.append("?MobileOS=ETC");
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
		
		Map<String, Object> map = new HashMap<>();
		map.put("campInfo", sb.toString());
		
		JSONObject obj = new JSONObject(sb.toString());
		JSONObject obj2 = new JSONObject(obj.get("response").toString());
		JSONObject obj3 = new JSONObject(obj2.get("body").toString());
		JSONObject obj4 = new JSONObject(obj3.get("items").toString());
		JSONArray arr = new JSONArray(obj4.getJSONArray("item"));
		
		for(int i = 0; i < arr.length(); i++) {
			System.out.println(arr[i]);
		}
		System.out.println(arr);
		
//		JSONArray arr = new JSONArray(obj.get("items"));
//		
//		System.out.println(arr);
		
		return map;
	}
	
}
