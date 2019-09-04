package com.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.pojo.*;

public interface App_infoMapper {
	//���App��������
	public int getCount(@Param("querySoftwareName")String querySoftwareName,
						@Param("queryStatus")Integer queryStatus,
						@Param("queryFlatformId")Integer queryFlatformId,
						@Param("queryCategoryLevel1")Integer queryCategoryLevel1,
						@Param("queryCategoryLevel2")Integer queryCategoryLevel2,
						@Param("queryCategoryLevel3")Integer queryCategoryLevel3);	
	 
	//��ѯ����App��Ϣ����ҳ�Ͳ�ѯ
	public List<App_info> getAppinfo(@Param("currentPageNo")Integer pageNo,
									 @Param("pageSize")Integer pageSize,
									 @Param("querySoftwareName")String querySoftwareName,
									 @Param("queryStatus")Integer queryStatus,
									 @Param("queryFlatformId")Integer queryFlatformId,
									 @Param("queryCategoryLevel1")Integer queryCategoryLevel1,
									 @Param("queryCategoryLevel2")Integer queryCategoryLevel2,
									 @Param("queryCategoryLevel3")Integer queryCategoryLevel3);
	//��ѯ����״̬
	public List<Data_dictionary> getStatus();
	
	//��ѯ����ƽ̨
	public List<Data_dictionary> getFlat(); 
}
