package com.gdu.bulmeong.users.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.bulmeong.users.domain.RetireUsersDTO;
import com.gdu.bulmeong.users.domain.SleepUsersDTO;
import com.gdu.bulmeong.users.domain.UsersDTO;
import com.gdu.bulmeong.users.mapper.UsersMapper;
import com.gdu.bulmeong.util.JavaMailUtil;
import com.gdu.bulmeong.util.SecurityUtil;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired 
	private UsersMapper usersMapper;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@Autowired
	private JavaMailUtil javaMailUtil;
	
	@Override
	public Map<String, Object> isReduceId(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isUser", usersMapper.selectUserByMap(map) != null);
		result.put("isSleepUser", usersMapper.selectSleepUserByMap(map) != null);
		result.put("isRetireUser", usersMapper.selectRetireUserByMap(map) != null);
		
		return result;
	}
	
	
	
	@Override
	public Map<String, Object> isReduceNickname(String nickname) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", nickname);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isUser", usersMapper.selectUserByMap(map) != null);
		result.put("isSleepUser", usersMapper.selectSleepUserByMap(map) != null);
		result.put("isRetireUser", usersMapper.selectRetireUserByMap(map) != null);
		
		return result;
	}
	
	
	
	@Override
	public Map<String, Object> isReduceEmail(String email) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isUser", usersMapper.selectUserByMap(map) != null);
		result.put("isSleepUser", usersMapper.selectSleepUserByMap(map) != null);
		
		return result;
	}
	
	
	
	@Override
	public Map<String, Object> sendAuthCode(String email) {
		
		// 인증코드 만들기
		String authCode = securityUtil.getAuthCode(6);  // String authCode = securityUtil.generateRandomString(6);
		// System.out.println("발송된 인증코드 : " + authCode);
		
		// 메일 전송
		javaMailUtil.sendJavaMail(email, "[Application] 인증요청", "인증번호는 <strong>" + authCode + "</strong>입니다.");
		
		// join.jsp로 생성한 인증코드를 보내줘야 함
		// 그래야 사용자가 입력한 인증코드와 비교를 할 수 있음
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("authCode", authCode);
		System.out.println(authCode);
		
		return result;
	}
	
	
	@Transactional
	@Override
	public void join(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String mobile = request.getParameter("mobile");
		String gender = request.getParameter("gender");
		String birthyear = request.getParameter("birthyear");
		String birthMonth = request.getParameter("birthmonth");
		String birthDate = request.getParameter("birthdate");
		String postcode = request.getParameter("postcode");
		String roadAddress = request.getParameter("roadAddress");
		String jibunAddress = request.getParameter("jibunAddress");
		String detailAddress = request.getParameter("detailAddress");
		String extraAddress = request.getParameter("extraAddress");
		String email = request.getParameter("email");
		String location = request.getParameter("location");
		String promotion = request.getParameter("promotion");
		
		pw = securityUtil.sha256(pw);
		name = securityUtil.preventXSS(name);
		String birthday = birthMonth + birthDate;
		detailAddress = securityUtil.preventXSS(detailAddress);
		int agreeCode = 0;  // 필수 동의
		if(!location.isEmpty() && promotion.isEmpty()) {
			agreeCode = 1;  // 필수 + 위치
		} else if(location.isEmpty() && !promotion.isEmpty()) {
			agreeCode = 2;  // 필수 + 프로모션
		} else if(!location.isEmpty() && !promotion.isEmpty()) {
			agreeCode = 3;  // 필수 + 위치 + 프로모션
		}
		
		UsersDTO user = UsersDTO.builder()
					.id(id)
					.pw(pw)
					.name(name)
					.nickname(nickname)
					.mobile(mobile)
					.gender(gender)
					.birthYear(birthyear)
					.birthDay(birthday)
					.postCode(postcode)
					.roadAddress(roadAddress)
					.jibunAddress(jibunAddress)
					.detailAddress(detailAddress)
					.extraAddress(extraAddress)
					.email(email)
					.agreeCode(agreeCode)
					.build();
		
		int result = usersMapper.insertUser(user);
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 0) {
				
				// 조회 조건으로 사용할 Map
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", id);
				
				// 로그인 처리를 위해서 session에 로그인 된 사용자 정보를 올려둠
				request.getSession().setAttribute("loginUser", usersMapper.selectUserByMap(map));
				
				// 로그인 기록 남기기
				int updateResult = usersMapper.updateAccessLog(id);
				if(updateResult == 0) {
					usersMapper.insertAccessLog(id);
				}
				
				out.println("<script>");
				out.println("alert('회원 가입되었습니다.');");
				out.println("location.href='/';");
				out.println("</script>");
				
			} else {
				
				out.println("<script>");
				out.println("alert('회원 가입에 실패했습니다.');");
				out.println("history.go(-2);");
				out.println("</script>");
				
			}
			
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@Transactional
	@Override
	public void retire(HttpServletRequest request, HttpServletResponse response) {
		
		// 탈퇴할 회원의 userNo, id, joinDate는 session의 loginUser에 저장되어 있다.
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser");
		
		// 탈퇴할 회원 RetireUserDTO 생성
		RetireUsersDTO retireUser = RetireUsersDTO.builder()
				.id(loginUser.getId())
				.nickname(loginUser.getNickname())
				.joinDate(loginUser.getJoinDate())
				.build();
		
		// 탈퇴처리
		int deleteResult1 = usersMapper.deleteUserAccess(loginUser.getId());
		int deleteResult2 = usersMapper.deleteUser(loginUser.getId());
		int insertResult = usersMapper.insertRetireUser(retireUser);
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(deleteResult1 > 0 && deleteResult2 > 0 && insertResult > 0) {
				
				// session 초기화(로그인 사용자 loginUser 삭제를 위해서)
				session.invalidate();
				
				out.println("<script>");
				out.println("alert('회원 탈퇴되었습니다.');");
				out.println("location.href='/';");
				out.println("</script>");
				
			} else {
				
				out.println("<script>");
				out.println("alert('회원 탈퇴에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
				
			}
			
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void login(HttpServletRequest request, HttpServletResponse response) {
		// 파라미터
		String url = request.getParameter("url");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		id = securityUtil.preventXSS(id);
		
		// pw는 DB에 저장된 데이터와 동일한 형태로 가공
		pw = securityUtil.sha256(pw);
		
		// 조회 조건으로 사용할 Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pw", pw);
		
		// id, pw가 일치하는 회원을 DB에서 조회하기
		UsersDTO loginUser = usersMapper.selectUserByMap(map);
		
		// id, pw가 일치하는 회원이 있다 : session에 loginUser 저장하기 + 로그인 기록 남기기 
		if(loginUser != null) {
			
			// 로그인 유지 처리는 keepLogin 메소드가 따로 처리함
			keepLogin(request, response);
			
			// 로그인 처리를 위해서 session에 로그인 된 사용자 정보를 올려둠
			request.getSession().setAttribute("loginUser", loginUser);

			// 로그인 기록 남기기
			int updateResult = usersMapper.updateAccessLog(id);
			if(updateResult == 0) {
				usersMapper.insertAccessLog(id);
			}
			
			// 이동 (로그인페이지 이전 페이지로 되돌아가기)
			try {
				response.sendRedirect(url);
			} catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		// id, pw가 일치하는 회원이 없다 : 로그인 페이지로 돌려 보내기
		else {
			
			// 응답
			try {
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('일치하는 회원 정보가 없습니다.');");
				out.println("history.back();");
				//out.println("location.href='/users/login/form';");
				out.println("</script>");
				out.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	@Override
	public void keepLogin(HttpServletRequest request, HttpServletResponse response) {
		
		// 파라미터
		String id = request.getParameter("id");
		String keepLogin = request.getParameter("keepLogin");
		
		// 로그인 유지를 체크한 경우
		if(keepLogin != null) {
			
			// session_id
			String sessionId = request.getSession().getId();
			// session_id를 쿠키에 저장하기
			Cookie cookie = new Cookie("keepLogin", sessionId);
			cookie.setMaxAge(60 * 60 * 24 * 15);  // 15일
			cookie.setPath("/");
			response.addCookie(cookie);
			
			// session_id를 DB에 저장하기
			UsersDTO user = UsersDTO.builder()
					.id(id)
					.sessionId(sessionId)
					.sessionLimitDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15).toString())  // 현재타임스탬프 + 15일에 해당하는 타임스탬프
					.build();

			usersMapper.updateSessionInfo(user);
			
		}
		// 로그인 유지를 체크하지 않은 경우
		else {
			
			// keepLogin 쿠키 제거하기
			Cookie cookie = new Cookie("keepLogin", "");
			cookie.setMaxAge(0);  // 쿠키 유지 시간이 0이면 삭제를 의미함
			cookie.setPath("/");
			response.addCookie(cookie);
			
		}
		
	}
	
	
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		
		// 로그아웃 처리
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser") != null) {
			session.invalidate();
		}
		
		// 로그인 유지 풀기
		Cookie cookie = new Cookie("keepLogin", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
	}
	
	
	
	@Override
	public UsersDTO getUserBySessionId(Map<String, Object> map) {
		return usersMapper.selectUserByMap(map);
	}
	
	
	
	@Override
	public Map<String, Object> confirmPassword(HttpServletRequest request) {
		
		// 파라미터 pw + SHA-256 처리
		String pw = securityUtil.sha256(request.getParameter("pw"));
		
		// id
		HttpSession session = request.getSession();
		String id = ((UsersDTO)session.getAttribute("loginUser")).getId();
		
		// 조회 조건으로 사용할 Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pw", pw);
		
		// id, pw가 일치하는 회원 조회
		UsersDTO user = usersMapper.selectUserByMap(map);
		
		// 결과 반환
		Map<String, Object> result= new HashMap<String, Object>();
		result.put("isUser", user != null);
		return result;
	}
	
	
	
	@Override
	public void modifyPassword(HttpServletRequest request, HttpServletResponse response) {
		
		// 현재 로그인 된 사용자
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO)session.getAttribute("loginUser");

		// 파라미터
		String pw = securityUtil.sha256(request.getParameter("pw"));

		// 동일한 비밀번호로 변경 금지
		if(pw.equals(loginUser.getPw())) {
			
			// 응답
			try {
				
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('현재 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}

		// 사용자 번호
		String id = loginUser.getId();
		
		// DB로 보낼 UserDTO
		UsersDTO user = UsersDTO.builder()
				.id(id)
				.pw(pw)
				.build();
		
		// 비밀번호 수정
		int result = usersMapper.updateUserPassword(user);
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 0) {
				
				// session에 저장된 loginUser 업데이트
				loginUser.setPw(pw);
				
				out.println("<script>");
				out.println("alert('비밀번호가 수정되었습니다.');");
				out.println("location.href='/';");
				out.println("</script>");
				
			} else {
				
				out.println("<script>");
				out.println("alert('비밀번호가 수정되지 않았습니다.');");
				out.println("history.back();");
				out.println("</script>");
				
			}
			
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@Override
	public void modifyUser(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String mobile = request.getParameter("mobile");
		String birthYear = request.getParameter("birthyear");
		String birthMonth = request.getParameter("birthmonth");
		String birthDate = request.getParameter("birthdate");
		String postCode = request.getParameter("postCode");
		String roadAddress = request.getParameter("roadAddress");
		String jibunAddress = request.getParameter("jibunAddress");
		String detailAddress = request.getParameter("detailAddress");
		String extraAddress = securityUtil.preventXSS(request.getParameter("extraAddress"));
		String email = request.getParameter("email");
		String location = request.getParameter("location");
		String promotion = request.getParameter("promotion");
		
		int agreeCode = 0;
		if(location.equals("on") && promotion.equals("off")) {
			agreeCode = 1;  // 필수 + 위치
		} else if(location.equals("off") && promotion.equals("on")) {
			agreeCode = 2;  // 필수 + 프로모션
		} else if(location.equals("on") && promotion.equals("on")) {
			agreeCode = 3;  // 필수 + 위치 + 프로모션
		}
		
		String birthday = birthMonth + birthDate;
		UsersDTO user = UsersDTO.builder()
					.id(id)
					.name(name)
					.gender(gender)
					.mobile(mobile)
					.birthYear(birthYear)
					.birthDay(birthday)
					.postCode(postCode)
					.roadAddress(roadAddress)
					.jibunAddress(jibunAddress)
					.detailAddress(detailAddress)
					.extraAddress(extraAddress)
					.email(email)
					.agreeCode(agreeCode)
					.build();
		
		int result = usersMapper.updateUser(user);
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 0) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", id);
				
				request.getSession().setAttribute("loginUser", usersMapper.selectUserByMap(map));
				
				out.println("<script>");
				out.println("alert('회원 정보가 수정되었습니다.');");
				out.println("location.href='/';");
				out.println("</script>");
				
			} else {
				
				out.println("<script>");
				out.println("alert('회원 정보 수정에 실패했습니다.');");
				out.println("history.go(-1);");
				out.println("</script>");
				
			}
			
			out.close();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@Transactional
	@Override
	public void sleepUserHandle() {
		int insertCount = usersMapper.insertSleepUser();
		if(insertCount > 0) {
			usersMapper.deleteUserForSleep();
		}
		
	}
	
	
	
	@Override
	public SleepUsersDTO getSleepUserById(String id) {
		return usersMapper.selectSleepUserById(id);
	}
	
	
	
	@Transactional
	@Override
	public void restoreUser(HttpServletRequest request, HttpServletResponse response) {
		
		// 계정 복원을 원하는 사용자의 아이디
		HttpSession session = request.getSession();
		SleepUsersDTO sleepUser = (SleepUsersDTO)session.getAttribute("sleepUser");
		String id = sleepUser.getId();
		
		// 계정복구진행
		int insertCount = usersMapper.insertRestoreUser(id);
		int deleteCount = 0;
		if(insertCount > 0) {
			deleteCount = usersMapper.deleteSleepUser(id);
		}
		
		// 응답
		try {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(insertCount > 0 && deleteCount > 0) {
				
				// session에 저장된 sleepUser 제거
				session.removeAttribute("sleepUser");
				
				out.println("<script>");
				out.println("alert('휴면 계정이 복구되었습니다. 휴면 계정 활성화를 위해 곧바로 로그인을 해 주세요.');");
				// out.println("location.href='" + request.getContextPath() + "/user/login/form';");  // 로그인 후 referer에 의해 /user/restore로 되돌아오기 때문에 사용하지 말 것
				out.println("location.href='/';");
				out.println("</script>");
				
			} else {
				
				out.println("<script>");
				out.println("alert('휴면 계정이 복구되지 않았습니다.');");
				out.println("history.back();");
				out.println("</script>");
				
			}
			
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public String getNaverLoginApiURL(HttpServletRequest request) {
		String apiURL = null;
		
		try {
			
			String clientId = "ymBUOS66J0o3pUpDZezX";
			String redirectURI = URLEncoder.encode("http://localhost:9090/users/naver/login", "UTF-8");  // 네이버 로그인 Callback URL에 작성한 주소 입력 
			SecureRandom random = new SecureRandom();
			String state = new BigInteger(130, random).toString();
			
			apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
			apiURL += "&client_id=" + clientId;
			apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&state=" + state;
			System.out.println("apiURL : " + apiURL);
			HttpSession session = request.getSession();
			session.setAttribute("state", state);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return apiURL;
	}
	
	
	
	@Override
	public String getNaverLoginToken(HttpServletRequest request) {
		
		// access_token 받기
		String clientId = "ymBUOS66J0o3pUpDZezX";
		String clientSecret = "8toAWv3hNm";
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		
		System.out.println("code : " + code);
		System.out.println("state : " + state);
		
		String redirectURI = null;
		try {
			redirectURI = URLEncoder.encode("http://localhost:9090/users/naver/login", "UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		StringBuffer res = new StringBuffer();  // StringBuffer는 StringBuilder과 동일한 역할 수행
		try {
			
			String apiURL;
			apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
			apiURL += "client_id=" + clientId;
			apiURL += "&client_secret=" + clientSecret;
			//apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&code=" + code;
			apiURL += "&state=" + state;
			
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			con.disconnect();
			
			/*
				res.toString()
				
				{
					"access_token":"AAAANipjD0VEPFITQ50DR__AgNpF2hTecVHIe9v-_uoyK5eP1mfdYX57bM3VTF_x4cWgz0v2fQlZsOOjl9uS0j8CLI4",
					"refresh_token":"2P9T9LTrnjaBf8XwF87a2UNUL4isfvk3QyLF8U1MDmju5ViiSXNSxii80ii8kvZWDiiYSiptFFYsuwqWl6C8n59NwoAEU6MmipfIis2htYMnZUlutzvRexh0PIZzzqqK3HlGYttJ",
					"token_type":"bearer",
					"expires_in":"3600"
				}
			*/
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("res : " + res.toString());
		JSONObject obj = new JSONObject(res.toString());
		String access_token = obj.getString("access_token");
		return access_token;
	}
	
	
	
	@Override
	public UsersDTO getNaverLoginProfile(String access_token) {
		// access_token을 이용해서 profile 받기
		String header = "Bearer " + access_token;
		
		StringBuffer sb = new StringBuffer();
		
		try {
			
			String apiURL = "https://openapi.naver.com/v1/nid/me";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", header);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			br.close();
			con.disconnect();
			
			/*
				sb.toString()
				
				{
					"resultcode": "00",
					"message": "success",
					"response": {
						"id":"asdfghjklqwertyuiopzxcvbnmadfafrgbgfg",
						"gender":"M",
						"email":"hahaha@naver.com",
						"mobile":"010-1111-1111",
						"mobile_e164":"+821011111111",
						"name":"\ubbfc\uacbd\ud0dc",
						"birthday":"10-10",
						"birthyear":"1990"
					}
				}
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 받아온 profile을 UserDTO로 만들어서 반환
		UsersDTO user = null;
		try {
			
			JSONObject profile = new JSONObject(sb.toString()).getJSONObject("response");
			System.out.println("profile : " + profile);
			String id = profile.getString("id");
			String name = profile.getString("name");
			String nickname = profile.getString("nickname");
			String gender = profile.getString("gender");
			String email = profile.getString("email");
			String mobile = profile.getString("mobile").replaceAll("-", "");
			String birthyear = profile.getString("birthyear");
			String birthday = profile.getString("birthday").replace("-", "");
			String profileImage = "";
			System.out.println("imageIsNull : " + profile.isNull("profile_image"));
			if(profile.isNull("profile_image")){
				profileImage = "/images/userimage/basic_profileImage.png";
			} else{
				profileImage = profile.getString("profile_image");
			};
			System.out.println("if 처리후 image : " + profileImage);
			
			profileImage = profileImage.equals("https://ssl.pstatic.net/static/pwe/address/img_profile.png") ? "/images/userimage/basic_profileImage.png" : profileImage;
			
			System.out.println("기본 프로필인지 아닌지 : " + profileImage);
			
			user = UsersDTO.builder()
					.id(id)
					.nickname(nickname)
					.name(name)
					.gender(gender)
					.email(email)
					.mobile(mobile)
					.birthYear(birthyear)
					.birthDay(birthday)
					.profileImage(profileImage)
					.build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return user;
	}
	
	
	
	@Override
	public UsersDTO getNaverUserById(String id) {
		// 조회 조건으로 사용할 Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		
		return usersMapper.selectUserByMap(map);
	}
	
	
	
	@Override
	public void naverLogin(HttpServletRequest request, UsersDTO naverUser) {
		// 로그인 처리를 위해서 session에 로그인 된 사용자 정보를 올려둠
		request.getSession().setAttribute("loginUser", naverUser);
		
		// 로그인 기록 남기기
		String id = naverUser.getId();
		int updateResult = usersMapper.updateAccessLog(id);
		if(updateResult == 0) {
			usersMapper.insertAccessLog(id);
		}
	}
	
	
	
	@Override
	public void naverJoin(HttpServletRequest request, HttpServletResponse response) {
		// 파라미터
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String mobile = request.getParameter("mobile");
		String birthyear = request.getParameter("birthYear");
		String birthday = request.getParameter("birthDay");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String profileImage = request.getParameter("profileImage");
		String location = request.getParameter("location");
		String promotion = request.getParameter("promotion");
		
		// 일부 파라미터는 DB에 넣을 수 있도록 가공
		name = securityUtil.preventXSS(name);
		String pw = securityUtil.sha256(birthyear + birthday);  // 생년월일을 초기비번 8자리로 제공하기로 함
		
		int agreeCode = 0;  // 필수 동의
		if(location != null && promotion == null) {
			agreeCode = 1;  // 필수 + 위치
		} else if(location == null && promotion != null) {
			agreeCode = 2;  // 필수 + 프로모션
		} else if(location != null && promotion != null) {
			agreeCode = 3;  // 필수 + 위치 + 프로모션
		}
		
		// DB로 보낼 UserDTO 만들기
		UsersDTO user = UsersDTO.builder()
				.id(id)
				.pw(pw)
				.name(name)
				.gender(gender)
				.email(email)
				.mobile(mobile)
				.birthYear(birthyear)
				.birthDay(birthday)
				.nickname(nickname)
				.profileImage(profileImage)
				.agreeCode(agreeCode)
				.snsType("naver")  // 네이버로그인으로 가입하면 naver를 저장해 두기로 함
				.build();
				
		// 회원가입처리
		int result = usersMapper.insertNaverUser(user);
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 0) {
				
				// 조회 조건으로 사용할 Map
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", id);
				
				// 로그인 처리를 위해서 session에 로그인 된 사용자 정보를 올려둠
				request.getSession().setAttribute("loginUser", usersMapper.selectUserByMap(map));
				
				// 로그인 기록 남기기
				int updateResult = usersMapper.updateAccessLog(id);
				if(updateResult == 0) {
					usersMapper.insertAccessLog(id);
				}
				
				out.println("<script>");
				out.println("alert('회원 가입되었습니다.');");
				out.println("location.href='/';");
				out.println("</script>");
				
			} else {
				
				out.println("<script>");
				out.println("alert('회원 가입에 실패했습니다.');");
				out.println("history.go(-2);");
				out.println("</script>");
				
			}
			
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@Override
	public Map<String, Object> findUser(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("findUser", usersMapper.selectUserByMap(map));
		return result;
	}
	
	
	
	@Override
	public Map<String, Object> sendTemporaryPassword(UsersDTO user) {

		// 9자리 임시 비밀번호
		String temporaryPassword = securityUtil.generateRandomString(9);
		System.out.println("임시비번 : " + temporaryPassword);
		
		// 메일 내용
		String text = "";
		text += "비밀번호가 초기화되었습니다.<br>";
		text += "임시비밀번호 : <strong>" + temporaryPassword + "</strong><br><br>";
		text += "임시비밀번호로 로그인 후에 반드시 비밀번호를 변경해 주세요.";
		
		// 메일 전송
		javaMailUtil.sendJavaMail(user.getEmail(), "[Application] 임시비밀번호", text);
		
		// DB로 보낼 user
		user.setPw(securityUtil.sha256(temporaryPassword));  // user에 포함된 userNo와 pw를 사용
		
		// 임시 비밀번호로 DB 정보 수정하고 결과 반환
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccess", usersMapper.updateUserPassword(user));
		return result;
		
	}
	
	
	
	@Override
	public Map<String, Object> saveImage(MultipartHttpServletRequest multipartRequest) {
		MultipartFile multipartFile = multipartRequest.getFile("image");
		
		Map<String, Object> result = new HashMap<String, Object>();

		return result;
	}
	
	 
	
}
