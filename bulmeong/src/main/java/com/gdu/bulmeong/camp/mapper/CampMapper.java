package com.gdu.bulmeong.camp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.camp.domain.CampDTO;

@Mapper
public interface CampMapper {
	
	public int insertCampApi(CampDTO camp);
	public int deleteCampApi();
	public int selectCampCount();
	public List<CampDTO> selectAllCamp(Map<String, Object> map);
	public List<CampDTO> selectCampByOption(Map<String, Object> map);
	public CampDTO selectCampByNo(int campNo);
}
