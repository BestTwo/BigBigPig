package com.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.DevService;
import com.service.appCategory.App_categoryService;
import com.service.appinfo.App_infoService;

@Controller
@RequestMapping("/dev")
public class DevController {
	
	
	@Autowired
	public DevService devService;
	
	@Autowired
	public App_infoService app_infoService;
	
	@Autowired
	public App_categoryService app_categoryService;
	
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
	
}
