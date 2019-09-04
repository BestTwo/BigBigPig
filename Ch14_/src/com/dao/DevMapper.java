package com.dao;

import org.apache.ibatis.annotations.Param;

import com.pojo.Dev_user;

public interface DevMapper {
	/**
	 * 登录
	 * @param devCode
	 * @param devpassword
	 * @return
	 */
	public Dev_user getLogin(@Param("devCode")String devCode,@Param("devPassword")String devPassword);
	/**
	 * 查找用户名
	 * @param code
	 * @return
	 */
	public String findCode(String code); // 查找用户名
}
