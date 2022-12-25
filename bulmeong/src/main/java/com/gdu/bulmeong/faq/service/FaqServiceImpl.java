package com.gdu.bulmeong.faq.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.bulmeong.faq.domain.FaqDTO;
import com.gdu.bulmeong.faq.mapper.FaqMapper;
import com.gdu.bulmeong.util.PageUtil;
import com.gdu.bulmeong.util.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FaqServiceImpl implements FaqService {
	
	@Autowired 
	private FaqMapper faqMapper;
	@Autowired
	private PageUtil pageUtil;
	@Autowired
	private SecurityUtil securityUtil;
	
	@Override
	public void loadFaqList(HttpServletRequest request, Model model) {
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		int totalRecord = faqMapper.selectFaqCount();

		int recordPerPage = 10;
		
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("end", pageUtil.getEnd());
		map.put("recordPerPage", pageUtil.getRecordPerPage());
		
		List<FaqDTO> faqList = faqMapper.selectFaqListByMap(map);
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("faqList", faqList);
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/faq/list"));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
	}
	
	@Override
	public int addFaq(HttpServletRequest request) {
		
		String faqTitle = securityUtil.preventXSS(request.getParameter("faqTitle"));
		String faqContent = securityUtil.preventXSS(request.getParameter("faqContent"));
		
		FaqDTO faq = new FaqDTO();
		faq.setFaqTitle(faqTitle);
		faq.setFaqContent(faqContent);
		
		return faqMapper.insertFaq(faq);
	}
	
}
