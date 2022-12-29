package com.gdu.bulmeong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.bulmeong.users.interceptor.KeepLoginInterceptor;
import com.gdu.bulmeong.users.interceptor.SleepUserCheckingInterceptor;
import com.gdu.bulmeong.users.interceptor.TestInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private KeepLoginInterceptor keepLoginInterceptor;
	
	@Autowired
	private SleepUserCheckingInterceptor sleepUserCheckingInterceptor;
	
	@Autowired
	private TestInterceptor testInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/load/bulmeongImage/**")
			.addResourceLocations("file:///C:/bulmeongImage/noticeImage/");
		registry.addResourceHandler("/load/image/**")
			.addResourceLocations("//");
		registry.addResourceHandler("/load/tent/**")
			.addResourceLocations("file:///C:/bulmeongImage/tent/");
		registry.addResourceHandler("/load/profileImagePreview/**")
		.addResourceLocations("file:///C:/bulmeongImage/profileImagePreview/");
		registry.addResourceHandler("/load/profileImage/**")
		.addResourceLocations("file:///C:/bulmeongImage/profileImage/");

	}
	/*
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(testInterceptor)
		.addPathPatterns("/**");
	}
	*/
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(keepLoginInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/users/login/form")
		.excludePathPatterns("/users/login");
		
		registry.addInterceptor(sleepUserCheckingInterceptor)
		.addPathPatterns("/users/login");
		
	}
	
	
	
}
