package com.gdu.bulmeong.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.users.domain.UsersDTO;

@Mapper
public interface AdminMapper {
	
	public List<UsersDTO> selectAllUser();
	
}
