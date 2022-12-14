package com.gdu.bulmeong.users.aop;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@EnableAspectJAutoProxy
@Aspect
@Component
public class RequiredLoginAspect {

	@Pointcut("execution(* com.gdu.bulmeong.*.controller.*Controller.requiredLogin_*(..))")
	public void requiredLogin() { }
	
	@Before("requiredLogin()")  // 포인트컷 실행 전에 requiredLogin() 메소드 수행
	public void requiredLoginHandler(JoinPoint joinPoint) throws Throwable {
		
		// 로그인이 되어 있는지 확인하기 위해서 session이 필요하므로 request가 필요하다.
		// 응답을 만들기 위해서 response도 필요하다.
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		
		// 세션
		HttpSession session = request.getSession();
		
		// 로그인 여부 확인
		if(session.getAttribute("loginUser") == null) {		

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("if(confirm('로그인이 필요한 기능입니다. 로그인 하시겠습니까?')){");
			out.println("location.href='/users/login/form';");
			out.println("} else {");
			out.println("history.back();");
			out.println("}");			
			out.println("</script>");
			out.close();	
		}	

		
	}
// 
//	@Pointcut("execution(* com.gdu.bulmeong.freeboard.controller.requiredLogin_*(..))")
//			
//	// @Pointcut("execution(* com.gdu.bulmeong.freeboard.controller..*.*(..))") // 메소드명 아닌 모든 메소드
//	public void sessionCheck() { }
//
//	@Before("sessionCheck()")  // 포인트컷 실행 전에 requiredLogin() 메소드 수행
//	public void sessionCheckHandler(JoinPoint joinPoint) throws Throwable {
//
//		// 로그인이 되어 있는지 확인하기 위해서 session이 필요하므로 request가 필요하다.
//		// 응답을 만들기 위해서 response도 필요하다.
//		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = servletRequestAttributes.getRequest();
//		HttpServletResponse response = servletRequestAttributes.getResponse();
//
//		// 세션
//		HttpSession session = request.getSession();
//		System.out.println("aop : " + session.getAttribute("loginUser"));
//
//		if(session.getAttribute("loginUser") == null) {
//			throw new Exception("세션 만료");
//		}
//
//
//	}

	
}