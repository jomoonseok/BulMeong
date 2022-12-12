package com.gdu.bulmeong.users.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		result.put("isRetireUser", usersMapper.selectRetireUserById(id) != null);
		
		return result;
	}
	
	
	
	@Override
	public Map<String, Object> isReduceNickname(String nickname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", nickname);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isUser", usersMapper.selectUserByMap(map) != null);
		result.put("isSleepUser", usersMapper.selectSleepUserByMap(map) != null);
		
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
	
	
	
	@Override
	public void join(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String mobile = request.getParameter("mobile");
		String gender = request.getParameter("gender");
		String birthyear = request.getParameter("birthyear");
		String birthmonth = request.getParameter("birthmonth");
		String birthdate = request.getParameter("birthdate");
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
		String birthday = birthmonth + birthdate;
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
	
	 
	
}
