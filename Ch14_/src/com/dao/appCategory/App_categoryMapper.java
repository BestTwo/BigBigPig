package com.dao.appCategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.*;
public interface App_categoryMapper {
	public List<App_category> getAppCategoryList1();	//获得所有一级标签
	
	public List<App_category> getAppCategoryListAll(@Param("parentId")Integer parentId);	//根据一级标签获得二级标签
	
}
   