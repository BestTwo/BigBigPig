package com.dao.appCategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.*;
public interface App_categoryMapper {
	public List<App_category> getAppCategoryList1();	//�������һ����ǩ
	
	public List<App_category> getAppCategoryListAll(@Param("parentId")Integer parentId);	//����һ����ǩ��ö�����ǩ
	
}
   