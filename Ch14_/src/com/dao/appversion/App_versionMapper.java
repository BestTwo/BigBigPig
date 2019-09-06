package com.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.*;
public interface App_versionMapper {
	//����appid��ö�Ӧ�汾��Ϣ
	public List<App_version> getVersionByAppId(@Param("appId")Integer appId);
	
	//�����汾��Ϣ
	public int addVersion(App_version app_version);
	
	//���ݰ汾id��ѯ�汾��Ϣ
	public App_version getVersionById(@Param("id")Integer id);
	
	//ɾ��·��
	public int delVersionPath(@Param("id")Integer id);
	
	//���°汾��Ϣ
	public int modifyVersion(App_version app_version);
	
	//����appidɾ����Ӧ�汾��Ϣ
	public int delVersion(@Param("appId")Integer appId);
}
