package com.controller;



import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pojo.App_info;
import com.pojo.App_version;
import com.service.DevService;
import com.service.appCategory.App_categoryService;
import com.service.appinfo.App_infoService;
import com.service.appversion.App_versionService;

@Controller
@RequestMapping("/dev")
public class DevController { 
	 
	Logger logger = Logger.getLogger(DevController.class);
	@Autowired
	public DevService devService;
	
	@Autowired
	public App_infoService app_infoService;
	
	@Autowired
	public App_categoryService app_categoryService;
	
	@Autowired 
	public App_versionService app_versionService;
	
	/**
	 * 进入前台用户登录
	 * @return
	 */
	@RequestMapping(value="/login")
	public String Login(){
		return "devlogin"; 
	}
	
	
	/**
	 * 跳转到主页面
	 * @return
	 */
	@RequestMapping(value="/main")
	public String Main(){
		return "/developer/main";
	}
	
	/**
	 * 跳转到新增页面
	 * @return
	 */
	@RequestMapping(value="flatform/app/appinfoadd")
	public String toInfoAdd(){ 
		return "developer/appinfoadd";
	}
	
	/**
	 * 跳转到修改页面
	 * @return
	 */
	@RequestMapping(value="flatform/app/appinfomodify")
	public String toAppinfomodify(String id,Model model){
		App_info app = app_infoService.getAppById(Integer.parseInt(id));
		model.addAttribute("appInfo",app);
		return "developer/appinfomodify";
	}
	
	/**
	 * 跳转到新增版本信息页面
	 * @param appId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="flatform/app/appversionadd")
	public String toAddVersion(@RequestParam(value="id")String appId,Model model){
		List<App_version> versionList = app_versionService.getVersionByAppId(Integer.parseInt(appId));
		App_version ver = new App_version();
		ver.setAppId(Integer.parseInt(appId));
		model.addAttribute("appVersionList", versionList);
		model.addAttribute("appVersion", ver);
		return "developer/appversionadd";
	}
	
	/**
	 * 跳转到修改版本信息页面
	 * @param appId
	 * @param vid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="flatform/app/appversionmodify")
	public String toModifyVersion(@RequestParam(value="aid")String appId,
								@RequestParam(value="vid")String vid,Model model){
		List<App_version> versionList = app_versionService.getVersionByAppId(Integer.parseInt(appId));
		model.addAttribute("appVersionList", versionList);
		App_version appVersion = app_versionService.getVersionById(Integer.parseInt(vid));
		model.addAttribute("appVersion", appVersion);
		return "developer/appversionmodify";
	}
	
	@RequestMapping(value="flatform/app/appview/{appinfoid}")
	public String toAppView(@PathVariable String  appinfoid,Model model){
		App_info app_info = app_infoService.getAppById(Integer.parseInt(appinfoid));
		List<App_version> versionList = app_versionService.getVersionByAppId(Integer.parseInt(appinfoid));
		model.addAttribute("appInfo",app_info);
		model.addAttribute("appVersionList", versionList);
		return "developer/appinfoview";
	}
	
}
