package com.gdu.bulmeong.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.admin.domain.AdminTentDTO;
import com.gdu.bulmeong.camp.domain.CampDTO;
import com.gdu.bulmeong.users.domain.MyPageReservedDTO;
import com.gdu.bulmeong.users.domain.UsersDTO;

@Mapper
public interface AdminMapper {
	
	public List<UsersDTO> selectAllUser(Map<String, Object> map);
	public int deleteUser(int userNo);
	public int selectAllUserCount();
	public List<CampDTO> selectAllCampAdmin(Map<String, Object> map);
	public int selectAllTentCount();
	public int selectAllReserveCount();
	public List<MyPageReservedDTO> selectReserveByMap(Map<String, Object> map);
	public List<AdminTentDTO> selectAllTentAdmin(Map<String, Object> map);
	public List<Map<String, Object>> selectCampcampNofacltNm();
	public int insertTent(AdminTentDTO tent);
	public AdminTentDTO selectTentByTentNo(int tentNo);
	public int updateTentByTentNo(AdminTentDTO tent);
	public int deleteTentByTentNo(int tentNo);
}
