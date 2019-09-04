package com.service;

import com.pojo.Dev_user;

public interface DevService {
	/**
	 * 登录
	 * @param devCode
	 * @param devpassword
	 * @return
	 */
	public Dev_user getLogin(String devCode,String devPassword);
	
	/**
	 * 查找用户名
	 * @param code
	 * @return
	 */
	public String findCode(String code); // 查找用户名

}
