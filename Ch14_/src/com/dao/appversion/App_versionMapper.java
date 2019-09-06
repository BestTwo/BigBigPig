package com.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.*;
public interface App_versionMapper {
	//根据appid获得对应版本信息
	public List<App_version> getVersionByAppId(@Param("appId")Integer appId);
	
	//新增版本信息
	public int addVersion(App_version app_version);
	
	//根据版本id查询版本信息
	public App_version getVersionById(@Param("id")Integer id);
	
	//删除路径
	public int delVersionPath(@Param("id")Integer id);
	
	//更新版本信息
	public int modifyVersion(App_version app_version);
	
	//根据appid删除对应版本信息
	public int delVersion(@Param("appId")Integer appId);
}
