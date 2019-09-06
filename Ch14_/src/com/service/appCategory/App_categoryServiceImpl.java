package com.service.appCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.appCategory.App_categoryMapper;
import com.pojo.App_category;
@Service("App_categoryService")
public class App_categoryServiceImpl implements App_categoryService{
	@Autowired
	private App_categoryMapper app_categoryMapper;
	public App_categoryMapper getApp_categoryMapper() {
		return app_categoryMapper;
	}

	public void setApp_categoryMapper(App_categoryMapper app_categoryMapper) {
		this.app_categoryMapper = app_categoryMapper;
	}
  
	@Override
	public List<App_category> getAppCategoryList1() {
		return app_categoryMapper.getAppCategoryList1();
	}
 
	@Override
	public List<App_category> getAppCategoryListAll(Integer cate1) {
		return app_categoryMapper.getAppCategoryListAll(cate1); 
	}


}
