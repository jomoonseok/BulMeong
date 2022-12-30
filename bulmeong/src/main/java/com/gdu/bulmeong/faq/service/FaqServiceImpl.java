package com.gdu.bulmeong.faq.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
	
	private FaqMapper faqMapper;
	private PageUtil pageUtil;
	private SecurityUtil securityUtil;
	
	@Override
	public void loadFaqList(HttpServletRequest request, Model model) {
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		int totalRecord = faqMapper.selectFaqCount();

		int recordPerPage = 7;
		
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin() - 1);
		map.put("recordPerPage", recordPerPage);
		
		List<FaqDTO> faqList = faqMapper.selectFaqListByMap(map);
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("faqList", faqList);
		model.addAttribute("paging", pageUtil.getPaging("/faq/list"));
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
	
	@Override
	public int removeFaq(int faqNo) {
		return faqMapper.deleteFaq(faqNo);
	}
	
}
