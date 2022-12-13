package com.gdu.bulmeong.reserve.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.camp.domain.CampDTO;
import com.gdu.bulmeong.reserve.domain.TentDTO;

@Mapper
public interface ReserveMapper {
	
	public List<TentDTO> selectTentByCampNo(int campNo);
	public List<CampDTO> selectCampByCampNo(int campNo);
	
}
