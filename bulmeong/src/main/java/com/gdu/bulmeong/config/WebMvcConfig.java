package com.gdu.bulmeong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gdu.bulmeong.users.interceptor.KeepLoginInterceptor;
import com.gdu.bulmeong.users.interceptor.PreventLoginInterceptor;
import com.gdu.bulmeong.users.interceptor.SleepUserCheckingInterceptor;
import com.gdu.bulmeong.util.MyFileUtil;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private KeepLoginInterceptor keepLoginInterceptor;
	
	@Autowired
	private SleepUserCheckingInterceptor sleepUserCheckingInterceptor;
	
	@Autowired
	private MyFileUtil myFileUtil;
	
	@Autowired
	private PreventLoginInterceptor preventLoginInterceptor;
	
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {

      registry.addResourceHandler("/load/noticeImage/**")
         .addResourceLocations("file:" + myFileUtil.getSummernotePath() + "/");
      registry.addResourceHandler("/load/tent/**")
         .addResourceLocations("file:" + myFileUtil.getTentPath() + "/");
      registry.addResourceHandler("/load/profileImagePreview/**")
      .addResourceLocations("file:" + myFileUtil.getPreviewPath() + "/");
      registry.addResourceHandler("/load/profileImage/**")
      .addResourceLocations("file:" + myFileUtil.getProfilePath() + "/");
   }
   
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(keepLoginInterceptor)
      .addPathPatterns("/**")
      .excludePathPatterns("/users/login/form")
      .excludePathPatterns("/users/login");
      
      registry.addInterceptor(sleepUserCheckingInterceptor)
      .addPathPatterns("/users/login");
      
      registry.addInterceptor(preventLoginInterceptor)
      .addPathPatterns("/users/agree")
      .addPathPatterns("/users/join/write")
      .addPathPatterns("/users/login/form")
      .addPathPatterns("/users/naver/login")
      .addPathPatterns("/users/sleep/display")
      .addPathPatterns("/users/findId/form")
      .addPathPatterns("/users/findPw/form");
      
   }
}