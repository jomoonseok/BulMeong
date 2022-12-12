package com.gdu.bulmeong.users.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.users.domain.RetireUsersDTO;
import com.gdu.bulmeong.users.domain.SleepUsersDTO;
import com.gdu.bulmeong.users.domain.UsersDTO;

@Mapper
public interface UsersMapper {
	public UsersDTO selectUserByMap(Map<String, Object> map);
	public SleepUsersDTO selectSleepUserByMap(Map<String, Object> map);
	public RetireUsersDTO selectRetireUserById(String id);
	public int insertUser(UsersDTO user);
	public int insertAccessLog(String id);
	public int updateAccessLog(String id);
}
