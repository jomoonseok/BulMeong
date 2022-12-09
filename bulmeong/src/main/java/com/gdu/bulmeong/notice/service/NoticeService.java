package com.gdu.bulmeong.notice.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.gdu.bulmeong.notice.domain.NoticeDTO;

public interface NoticeService {
	public void getNoticeList(HttpServletRequest request, Model model);
	public void saveNotice(HttpServletRequest request, HttpServletResponse response);
	public int increaseNoticeHit(int noticeNo);
	public NoticeDTO getNoticeByNo(int noticeNo);
}
