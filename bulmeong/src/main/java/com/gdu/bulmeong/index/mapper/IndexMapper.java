package com.gdu.bulmeong.index.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.camp.domain.CampDTO;
import com.gdu.bulmeong.index.domain.IndexFreeBoardDTO;

@Mapper
public interface IndexMapper {
	
	public List<CampDTO> selectAllCampIndex();
	public List<CampDTO> selectAllCampByJjim();
	public List<IndexFreeBoardDTO> selectFreeBrdListTopFiveLike();
	
}
