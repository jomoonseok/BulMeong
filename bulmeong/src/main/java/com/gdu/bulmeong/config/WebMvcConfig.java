package com.gdu.bulmeong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.bulmeong.users.interceptor.KeepLoginInterceptor;
import com.gdu.bulmeong.users.interceptor.SleepUserCheckingInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private KeepLoginInterceptor keepLoginInterceptor;
	
	@Autowired
	private SleepUserCheckingInterceptor sleepUserCheckingInterceptor ;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/load/image/**")
			.addResourceLocations("file:///C:/noticeImage/");
	}
	/*
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(keepLoginInterceptor)
		.addPathPatterns("/")
		.addPathPatterns("/users/UsersController/*")
		.excludePathPatterns("/login");
		
		registry.addInterceptor(sleepUserCheckingInterceptor)
		.addPathPatterns("/")
		.addPathPatterns("/users/UsersController/login");
	}
	*/
	
	
}
