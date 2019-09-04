package com.service.appCategory;

import java.util.List;

import com.pojo.App_category;

public interface App_categoryService {
	
	public List<App_category> getAppCategoryList1();	//获得所有一级标签
	
	public List<App_category> getAppCategoryListAll(Integer cate1);	//根据一级标签获得二级标签
	
}
