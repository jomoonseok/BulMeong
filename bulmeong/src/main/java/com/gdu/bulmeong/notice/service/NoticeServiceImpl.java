package com.gdu.bulmeong.notice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.bulmeong.notice.domain.NoticeDTO;
import com.gdu.bulmeong.notice.mapper.NoticeMapper;
import com.gdu.bulmeong.util.MyFileUtil;
import com.gdu.bulmeong.util.PageUtil;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired 
	private NoticeMapper noticeMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Autowired
	private MyFileUtil myFileUtil;
	
	@Override
	public void getNoticeList(HttpServletRequest request, Model model) {
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		
		int page = Integer.parseInt(opt.orElse("1"));

		int totalRecord = noticeMapper.selectNoticeListCount();
		
		int recordPerPage = 10;

		pageUtil.setPageUtil(page, totalRecord, recordPerPage);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());

		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("noticeList", noticeMapper.selectNoticeListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/notice/list"));
		
	}
	 
	@Override
	public void saveNotice(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int increaseNoticeHit(int noticeNo) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public NoticeDTO getNoticeByNo(int noticeNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
