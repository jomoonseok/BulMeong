package com.gdu.bulmeong.notice.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.bulmeong.notice.domain.NoticeDTO;

public interface NoticeService {
	public void getNoticeList(HttpServletRequest request, Model model);
	public Map<String, Object> saveSummernoteImage(MultipartHttpServletRequest multipartRequest);
	public void saveNotice(HttpServletRequest request, HttpServletResponse response);
	public int increaseNoticeHit(int noticeNo);
	public NoticeDTO getNoticeByNo(int noticeNo);
	public void modifyNotice(HttpServletRequest request, HttpServletResponse response);
	public void removeNotice(HttpServletRequest request, HttpServletResponse response);
}
