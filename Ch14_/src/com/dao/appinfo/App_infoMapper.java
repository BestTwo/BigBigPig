package com.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	//����APK���Ʋ�ѯapp��Ϣ
	public App_info getAppByAPKName(@Param("APKName")String APKName);	
	
	//���App��Ϣ
	public int addApp(App_info app);
	
	//����id��ѯ����app��Ϣ
	public App_info getAppById(@Param("id")Integer id);
	
	//����id�޸�app��Ϣ
	public int modifyApp(App_info app);
	
	//����id���app logo
	public int delFile(@Param("id")Integer id);
	
	//����id�޸�status״̬
	public int modifyStatus(@Param("id")Integer id);
	
	//�޸�������°汾��
	public int modifyVersion(@Param("appId")Integer appId);
	
	//ɾ����Ӧidapp��Ϣ
	public int delApp(@Param("id")Integer Id);
	
	//����id�޸�״̬
	public int modifyStatus1(@Param("id")Integer id,@Param("status")Integer status);
}
