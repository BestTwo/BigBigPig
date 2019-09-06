package com.service.appversion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.appversion.App_versionMapper;
import com.pojo.App_version;
@Service("app_versionService")
public class App_versionServiceImpl implements App_versionService{
	@Autowired
	public App_versionMapper app_versionMapper;
	@Override
	public List<App_version> getVersionByAppId(Integer appId) {
		return app_versionMapper.getVersionByAppId(appId);
	}
	@Override
	public int addVersion(App_version app_version) {
		return app_versionMapper.addVersion(app_version);
	}
	@Override
	public App_version getVersionById(Integer id) {
		return app_versionMapper.getVersionById(id);
	}
	@Override
	public int delVersionPath(Integer id) {
		return app_versionMapper.delVersionPath(id);
	}
	@Override
	public int modifyVersion(App_version app_version) {
		return app_versionMapper.modifyVersion(app_version);
	}
	@Override
	public int delVersion(Integer appId) {
		return app_versionMapper.delVersion(appId);
	}

}
