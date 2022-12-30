package com.gdu.bulmeong.users.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.camp.domain.CampDTO;
import com.gdu.bulmeong.reserve.domain.ReservedDTO;
import com.gdu.bulmeong.users.domain.MyPageReservedDTO;
import com.gdu.bulmeong.users.domain.RetireUsersDTO;
import com.gdu.bulmeong.users.domain.SleepUsersDTO;
import com.gdu.bulmeong.users.domain.UsersDTO;

@Mapper
public interface UsersMapper {
	public UsersDTO selectUserByMap(Map<String, Object> map);
	public SleepUsersDTO selectSleepUserByMap(Map<String, Object> map);
	public RetireUsersDTO selectRetireUserByMap(Map<String, Object> map);
	public int insertUser(UsersDTO user);
	public int insertAccessLog(String id);
	public int updateAccessLog(String id);
	public int deleteUserAccess(String id);
	public int deleteUser(String id);
	public int insertRetireUser(RetireUsersDTO retireUser);
	public int updateSessionInfo(UsersDTO user);
	public int updateUserPassword(UsersDTO user);
	public int updateUser(UsersDTO user);
	public List<UsersDTO> selectSleepExpectedUser();
	public int insertSleepUser();
	public int deleteUserForSleep();
	public SleepUsersDTO selectSleepUserById(String id);
	public int insertRestoreUser(String id);
	public int deleteSleepUser(String id);
	public int insertNaverUser(UsersDTO user);
	public int updateProfile(UsersDTO user);
	//public int insertProfileImage(ProfileImageDTO profileImage);
	
	// myPage
	public List<MyPageReservedDTO> selectReserveById(String id);
	public List<CampDTO> selectJjimList(String id);
	
}
