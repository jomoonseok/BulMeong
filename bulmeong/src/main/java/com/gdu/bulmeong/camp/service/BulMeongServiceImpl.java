package com.gdu.bulmeong.camp.service;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

@Service
public class BulMeongServiceImpl implements BulMeongService {
	
//	@Autowired 
//	private BulMeongMapper bulMeongMapper;
	 
	@Override
	public String getCampInfo(String basedList) {
		
		// 한국관광공사_고캠핑 정보 조회서비스_GW
		
		// 인증키(Decoding)
		String serviceKey = "0+KGdm6YrHSXA95CGIey5Sk0Sahzg8aiIbzdSRfUSCSABUGREXTJKtUr3IO+48dPKEQEZudz2n78Q/tx5pHIBQ==";
		
		/*
			numOfRows(한페이지결과수) : 10
			pageNo(페이지번호) : 1
			MobileOS(OS구분) : IOS(아이폰), AND(안드로이드), WIN(윈도우폰), ETC(기타)
			MobileApp(서비스명(어플명)) : BulMeong
			type(타입) : json
		 */
		
		// ApiURL + 파라미터
		StringBuilder urlBuilder = new StringBuilder();
		try {
			urlBuilder.append("https://apis.data.go.kr/B551011/GoCamping/basedList ");
			urlBuilder.append("?serviceKey=").append(URLEncoder.encode(serviceKey, "UTF-8"));
			urlBuilder.append("&numOfRows=10");
			urlBuilder.append("&pageNo=1");
			urlBuilder.append("&MobileOS=ETC");
			urlBuilder.append("&MobileApp=BulMeong");
			urlBuilder.append("&_type=json");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String apiURL = urlBuilder.toString();
		
		// API 요청
		URL url = null;
		HttpURLConnection con = null;
		try {
			url = new URL(apiURL); // MalformedURLException
			con = (HttpURLConnection) url.openConnection();  // IOException
			con.setRequestMethod("GET"); // 대문자만 인식함.
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// API 응답
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// con 닫기
		con.disconnect();
		
		// 반환 (API로부터 가져온 모든 텍스트 정보 -> JSON 형식으로 되어 있는 텍스트)
		return sb.toString();
		
	}
	
}
