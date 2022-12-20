package com.gdu.bulmeong.index.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.camp.domain.CampDTO;

@Mapper
public interface IndexMapper {
	
	public List<CampDTO> selectAllCampIndex();
}
