package com.service.appinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.appinfo.App_infoMapper;
import com.pojo.App_info;
import com.pojo.Data_dictionary;
@Service("App_infoService")
public class App_infoServiceImpl implements App_infoService{
	@Autowired
	private App_infoMapper app_infoMapper;

	public App_infoMapper getApp_infoMapper() {
		return app_infoMapper;
	}

	public void setApp_infoMapper(App_infoMapper app_infoMapper) {
		this.app_infoMapper = app_infoMapper;
	}

	@Override
	public int getCount(String querySoftwareName, Integer queryStatus, Integer queryFlatformId,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3) {
		return app_infoMapper.getCount(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
	}

	@Override
	public List<App_info> getAppinfo(Integer pageNo, Integer pageSize, String querySoftwareName, Integer queryStatus,
			Integer queryFlatformId, Integer queryCategoryLevel1, Integer queryCategoryLevel2,
			Integer queryCategoryLevel3) {
		return app_infoMapper.getAppinfo(pageNo, pageSize, querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
	}

	@Override
	public List<Data_dictionary> getStatus() {
		return app_infoMapper.getStatus();
	}

	@Override
	public List<Data_dictionary> getFlat() {
		return app_infoMapper.getFlat(); 
	}
	

}
