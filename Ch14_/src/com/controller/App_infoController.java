package com.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.pojo.App_category;
import com.pojo.App_info;
import com.pojo.Data_dictionary;
import com.pojo.Dev_user;
import com.pojo.PageSupport;
import com.service.DevService;
import com.service.appCategory.App_categoryService;
import com.service.appinfo.App_infoService;
@Controller
@RequestMapping("/dev")
public class App_infoController {
	
	private Logger logger = Logger.getLogger(DevController.class);
	
	@Autowired
	public DevService devService;
	
	@Autowired
	public App_infoService app_infoService;
	
	@Autowired
	public App_categoryService app_categoryService;
	
	/**
	 * ��¼�Ƿ�ɹ�
	 * @return
	 */
	@RequestMapping(value="/dologin.html",method = RequestMethod.POST)
	public String doLogin(String devCode,String devPassword,HttpSession session,Model model){
		Dev_user dev_user = null;
		dev_user = devService.getLogin(devCode, devPassword);
		if(dev_user != null){
			session.setAttribute("devUserSession",dev_user);
			return "redirect:/dev/main";
		}
		String code = devService.findCode(devCode);
		String error = "";
		if (code == null) {
			error = "�û���������!";
		} else {
			error = "�����������";
		}
		model.addAttribute("error", error);
		return "devlogin";
	}
	
	/**
	 * ע��
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String LogOut(HttpSession session){
		session.removeAttribute("devUserSession");
		return "devlogin";
	}
	/**
	 * app�б��ѯ����ת
	 * @param model
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value="/flatform/app/list")
	public String findApp(Model model,@RequestParam(value="pageIndex",required=false)String pageIndex,
							@RequestParam(value="querySoftwareName",required=false)String querySoftwareName,
							@RequestParam(value="queryStatus",required=false)String queryStatus,
							@RequestParam(value="queryFlatformId",required=false)String queryFlatformId,
							@RequestParam(value="queryCategoryLevel1",required=false)String queryCategoryLevel1,
							@RequestParam(value="queryCategoryLevel2",required=false)String queryCategoryLevel2,
							@RequestParam(value="queryCategoryLevel3",required=false)String queryCategoryLevel3){
		List<App_category> cateList = app_categoryService.getAppCategoryList1();
		logger.error(queryFlatformId+"===========================");
		List<App_info> applist = null;
		int queryStatus1 = 0;
		int queryFlatformId1 = 0;
		int queryCategoryLevel1_ = 0;
		int queryCategoryLevel2_ = 0;
		int queryCategoryLevel3_ = 0;
		// ����ҳ������
		int pageSize = 5;
		// ��ǰҳ��
		int currentpageNo = 1;
		// ����Ҫȥ��ѯ���ݣ���ô��Ҫȷ������
		if (pageIndex != null) {
			currentpageNo = Integer.parseInt(pageIndex);  
		}
		if(queryStatus!=null&&queryStatus!=""){
			queryStatus1 = Integer.parseInt(queryStatus);
		}
		if(queryFlatformId != null&&queryFlatformId!=""){
			queryFlatformId1 = Integer.parseInt(queryFlatformId);
		}
		if(queryCategoryLevel1!= null&& queryCategoryLevel1!=""){
			queryCategoryLevel1_ = Integer.parseInt(queryCategoryLevel1);
		}
		if(queryCategoryLevel2 != null && queryCategoryLevel2!=""){
			queryCategoryLevel2_ = Integer.parseInt(queryCategoryLevel2);
		}
		if(queryCategoryLevel3 != null && queryCategoryLevel3!=""){
			queryCategoryLevel3_ = Integer.parseInt(queryCategoryLevel3);
		}
		// ������
		int totalCount = app_infoService.getCount(querySoftwareName,queryStatus1,queryFlatformId1,queryCategoryLevel1_,
												  queryCategoryLevel2_,queryCategoryLevel3_);
		// ��ҳ��
		PageSupport page = new PageSupport(); 
		page.setCurrentPageNo(currentpageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(totalCount);
		int totalPageCount = page.getTotalPageCount();
		// ������ҳ��βҳ
		if (currentpageNo < 1) {
			currentpageNo = 1;
		} else if (currentpageNo > totalPageCount) {
			currentpageNo = totalPageCount;
		}
		// ��ѯ��ҳ����
		applist = app_infoService.getAppinfo((currentpageNo-1)*pageSize, pageSize,querySoftwareName,queryStatus1,queryFlatformId1,queryCategoryLevel1_,
				  queryCategoryLevel2_,queryCategoryLevel3_);
		model.addAttribute("appInfoList", applist);
		List<Data_dictionary> statusList = app_infoService.getStatus();
		List<Data_dictionary> flatList = app_infoService.getFlat();
		// ��ֵһ��һ���Ĵ���ҳ��
		model.addAttribute("flatFormList",flatList);
		model.addAttribute("statusList",statusList);
		model.addAttribute("pages", page);
		model.addAttribute("categoryLevel1List",cateList);
		return "developer/appinfolist";
	}
	/**
	 * ajax����һ�������ѯ��������
	 * @return 
	 */
	@RequestMapping(value="/flatform/app/categorylevellist", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object categorylevellist1(@RequestParam(value="pid")String pid){
		logger.info(pid+"=========================");
		List<App_category> cate2 = app_categoryService.getAppCategoryListAll(pid==null||pid==""?null:Integer.parseInt(pid));
		return JSONArray.toJSONString(cate2);
	}
	
	
	/**
	 * �������ƽ̨��Ϣ
	 * @return
	 */
	@RequestMapping(value="/flatform/app/datadictionarylist",produces = {"application/json;charset=UTF-8" })
	@ResponseBody
	public Object datadictionarylist(){
		List<Data_dictionary> flatList = app_infoService.getFlat();
		return JSONArray.toJSONString(flatList);  
	}
}
