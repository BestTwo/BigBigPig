package com.service.appCategory;

import java.util.List;

import com.pojo.App_category;

public interface App_categoryService {
	
	public List<App_category> getAppCategoryList1();	//�������һ����ǩ
	
	public List<App_category> getAppCategoryListAll(Integer cate1);	//����һ����ǩ��ö�����ǩ
	
}
