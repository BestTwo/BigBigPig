package com.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
}
