package com.gdu.bulmeong.pay.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bulmeong.pay.domain.PayDTO;
import com.gdu.bulmeong.pay.domain.TentDTO;

@Mapper
public interface PayMapper {
	public TentDTO selectTentByNo(int tentNo);
	public int insertPay(PayDTO pay);
}
