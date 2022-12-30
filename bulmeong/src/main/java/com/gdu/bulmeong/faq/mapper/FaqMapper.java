package com.gdu.bulmeong.faq.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.faq.domain.FaqDTO;

@Mapper
public interface FaqMapper {
	public int selectFaqCount();
	public List<FaqDTO> selectFaqListByMap(Map<String, Object> map);
	public int insertFaq(FaqDTO faq);
	public int deleteFaq(int faqNo);
}
