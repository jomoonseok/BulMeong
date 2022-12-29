package com.gdu.bulmeong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.bulmeong.users.interceptor.KeepLoginInterceptor;
import com.gdu.bulmeong.users.interceptor.SleepUserCheckingInterceptor;
import com.gdu.bulmeong.users.interceptor.TestInterceptor;
import com.gdu.bulmeong.util.MyFileUtil;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private KeepLoginInterceptor keepLoginInterceptor;
	
	@Autowired
	private SleepUserCheckingInterceptor sleepUserCheckingInterceptor;
	
	@Autowired
	private TestInterceptor testInterceptor;
	
	@Autowired
	private MyFileUtil myFileUtil;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

//		registry.addResourceHandler("/load/bulmeongImage/**")
//			.addResourceLocations("file:///C:/bulmeongImage/noticeImage/");
		registry.addResourceHandler("/load/noticeImage/**")
			.addResourceLocations("file:" + myFileUtil.getSummernotePath() + "/");
		registry.addResourceHandler("/load/tent/**")
			.addResourceLocations("file:///C:/bulmeongImage/tent/");
		registry.addResourceHandler("/load/profileImagePreview/**")
		.addResourceLocations("file:" + myFileUtil.getPreviewPath() + "/");
		registry.addResourceHandler("/load/profileImage/**")
		.addResourceLocations("file:" + myFileUtil.getProfilePath() + "/");

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
