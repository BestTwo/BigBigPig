package com.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.pojo.*;

public interface App_infoMapper {
	//获得App数据总数
	public int getCount(@Param("querySoftwareName")String querySoftwareName,
						@Param("queryStatus")Integer queryStatus,
						@Param("queryFlatformId")Integer queryFlatformId,
						@Param("queryCategoryLevel1")Integer queryCategoryLevel1,
						@Param("queryCategoryLevel2")Integer queryCategoryLevel2,
						@Param("queryCategoryLevel3")Integer queryCategoryLevel3);	
	 
	//查询所有App信息并分页和查询
	public List<App_info> getAppinfo(@Param("currentPageNo")Integer pageNo,
									 @Param("pageSize")Integer pageSize,
									 @Param("querySoftwareName")String querySoftwareName,
									 @Param("queryStatus")Integer queryStatus,
									 @Param("queryFlatformId")Integer queryFlatformId,
									 @Param("queryCategoryLevel1")Integer queryCategoryLevel1,
									 @Param("queryCategoryLevel2")Integer queryCategoryLevel2,
									 @Param("queryCategoryLevel3")Integer queryCategoryLevel3);
	//查询所有状态
	public List<Data_dictionary> getStatus();
	
	//查询所有平台
	public List<Data_dictionary> getFlat(); 
	
	//根据APK名称查询app信息
	public App_info getAppByAPKName(@Param("APKName")String APKName);	
	
	//添加App信息
	public int addApp(App_info app);
	
	//根据id查询所有app信息
	public App_info getAppById(@Param("id")Integer id);
	
	//根据id修改app信息
	public int modifyApp(App_info app);
	
	//根据id清除app logo
	public int delFile(@Param("id")Integer id);
	
	//根据id修改status状态
	public int modifyStatus(@Param("id")Integer id);
	
	//修改软件最新版本号
	public int modifyVersion(@Param("appId")Integer appId);
	
	//删除对应idapp信息
	public int delApp(@Param("id")Integer Id);
	
	//根据id修改状态
	public int modifyStatus1(@Param("id")Integer id,@Param("status")Integer status);
}
