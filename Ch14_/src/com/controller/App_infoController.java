package com.controller;

import java.io.File;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.Util;
import com.pojo.App_category;
import com.pojo.App_info;
import com.pojo.App_version;
import com.pojo.Data_dictionary;
import com.pojo.Dev_user;
import com.pojo.PageSupport;
import com.service.DevService;
import com.service.appCategory.App_categoryService;
import com.service.appinfo.App_infoService;
import com.service.appversion.App_versionService;

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

	@Autowired
	public App_versionService app_versionService;

	/**
	 * ��¼�Ƿ�ɹ�
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dologin.html", method = RequestMethod.POST)
	public String doLogin(String devCode, String devPassword, HttpSession session, Model model) {
		Dev_user dev_user = null;
		dev_user = devService.getLogin(devCode, devPassword);
		if (dev_user != null) {
			session.setAttribute("devUserSession", dev_user);
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
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String LogOut(HttpSession session) {
		session.removeAttribute("devUserSession");
		return "devlogin";
	}

	/**
	 * app�б��ѯ����ת
	 * 
	 * @param model
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/flatform/app/list")
	public String findApp(Model model, @RequestParam(value = "pageIndex", required = false) String pageIndex,
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "queryStatus", required = false) String queryStatus,
			@RequestParam(value = "queryFlatformId", required = false) String queryFlatformId,
			@RequestParam(value = "queryCategoryLevel1", required = false) String queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String queryCategoryLevel3) {
		List<App_category> cateList = app_categoryService.getAppCategoryList1();
		logger.error(queryFlatformId + "===========================");
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
		if (queryStatus != null && queryStatus != "") {
			queryStatus1 = Integer.parseInt(queryStatus);
		}
		if (queryFlatformId != null && queryFlatformId != "") {
			queryFlatformId1 = Integer.parseInt(queryFlatformId);
		}
		if (queryCategoryLevel1 != null && queryCategoryLevel1 != "") {
			queryCategoryLevel1_ = Integer.parseInt(queryCategoryLevel1);
		}
		if (queryCategoryLevel2 != null && queryCategoryLevel2 != "") {
			queryCategoryLevel2_ = Integer.parseInt(queryCategoryLevel2);
		}
		if (queryCategoryLevel3 != null && queryCategoryLevel3 != "") {
			queryCategoryLevel3_ = Integer.parseInt(queryCategoryLevel3);
		}
		// ������
		int totalCount = app_infoService.getCount(querySoftwareName, queryStatus1, queryFlatformId1,
				queryCategoryLevel1_, queryCategoryLevel2_, queryCategoryLevel3_);
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
		applist = app_infoService.getAppinfo((currentpageNo - 1) * pageSize, pageSize, querySoftwareName, queryStatus1,
				queryFlatformId1, queryCategoryLevel1_, queryCategoryLevel2_, queryCategoryLevel3_);
		model.addAttribute("appInfoList", applist);
		List<Data_dictionary> statusList = app_infoService.getStatus();
		List<Data_dictionary> flatList = app_infoService.getFlat();
		// ��ֵһ��һ���Ĵ���ҳ��
		model.addAttribute("flatFormList", flatList);
		model.addAttribute("statusList", statusList);
		model.addAttribute("pages", page);
		model.addAttribute("categoryLevel1List", cateList);
		return "developer/appinfolist";
	}

	/**
	 * ajax����һ�������ѯ��������
	 * 
	 * @return
	 */
	@RequestMapping(value = "/flatform/app/categorylevellist", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object categorylevellist1(@RequestParam(value = "pid") String pid) {
		logger.info(pid + "=========================");
		List<App_category> cate2 = app_categoryService
				.getAppCategoryListAll(pid == null || pid == "" ? null : Integer.parseInt(pid));
		return JSONArray.toJSONString(cate2);
	}

	/**
	 * �������ƽ̨��Ϣ
	 * 
	 * @return
	 */
	@RequestMapping(value = "/flatform/app/datadictionarylist", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object datadictionarylist() {
		List<Data_dictionary> flatList = app_infoService.getFlat();
		return JSONArray.toJSONString(flatList);
	}

	/**
	 * �ж�������Ƿ��ظ�
	 * 
	 * @return
	 */
	@RequestMapping(value = "/flatform/app/apkexist")
	@ResponseBody
	public Object apkexist(@RequestParam(value = "APKName") String APKName) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (APKName.trim() == "") {
			resultMap.put("APKName", "empty");
		} else {
			App_info app = app_infoService.getAppByAPKName(APKName);
			if (null != app)
				resultMap.put("APKName", "exist");
			else
				resultMap.put("APKName", "noexist");
		}
		return JSONArray.toJSONString(resultMap);
	}

	/**
	 * ����app
	 * 
	 * @return
	 */
	@RequestMapping(value = "/flatform/app/appinfoaddsave")
	public String addSaveApp(@RequestParam(value = "a_logoPicPath", required = false) MultipartFile attach,
			App_info app, HttpServletRequest request, HttpSession session) {
		String logoPicPath = null; // LOGOͼƬurl·��
		String logoLocPath = null; // LOGOͼƬ�ķ������洢·��
		// �ж��ļ��Ƿ�Ϊ��
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			logger.error("uploadFile path---------->" + path);
			String oldFileName = attach.getOriginalFilename();// ԭ�ļ���
			logger.error("ԭ�ļ���---------->" + oldFileName);
			String prefix = FilenameUtils.getExtension(oldFileName); // ԭ�ļ���׺
			logger.error("ԭ�ļ���׺��---------->" + prefix);
			int filesize = 500000;
			logger.debug("�ϴ��ļ���С---------->" + attach.getSize());
			if (attach.getSize() > filesize) {
				request.setAttribute("fileUploadError", "* �ļ���С���ó��� 50KB");
				return "developer/appinfoadd";
			} else if (!prefix.equalsIgnoreCase("jpg") && !prefix.equalsIgnoreCase("jpeg")
					&& !prefix.equalsIgnoreCase("png")) {
				request.setAttribute("fileUploadError", "* �ϴ����ļ�ֻ����jpg��jpeg��png��ʽ");
				return "developer/appinfoadd";
			}
			String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.jpg";
			logger.debug("�µ��ļ���--------->" + fileName);
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// ����
			try {
				attach.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("fileUploadError", "* ���������ϴ�ʧ�ܣ�");
				return "developer/appinfoadd";
			}
			logoPicPath = path + File.separator + fileName;
			logoLocPath = request.getContextPath() + "/statics/uploadfiles/" + fileName;
			app.setLogoPicPath(logoPicPath);
			app.setLogoLocPath(logoLocPath);
		}
		Dev_user user = (Dev_user) session.getAttribute("devUserSession");
		app.setCreatedBy(user.getId());
		app.setCreationDate(new Date());
		app_infoService.addApp(app);
		return "redirect:list";
	}

	/**
	 * 
	 */
	@RequestMapping("/flatform/app/delfile")
	@ResponseBody
	public Object delFile(String id, @RequestParam(value = "flag") String flag) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		int result = 0;
		if (flag.equals("logo"))
			result = app_infoService.delFile(Integer.parseInt(id));
		else if (flag.equals("apk"))
			result = app_versionService.delVersionPath(Integer.parseInt(id));
		if (result > 0) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "failed");
		}
		return JSONArray.toJSONString(resultMap);
	}

	/**
	 * �޸�app��Ϣ
	 * 
	 * @return
	 */
	@RequestMapping("/flatform/app/appinfomodifysave")
	public String modifysave(@RequestParam(value = "attach", required = false) MultipartFile attach, App_info app,
			HttpServletRequest request, HttpSession session,
			@RequestParam(value = "status", required = false) String status) {
		String logoPicPath = null; // LOGOͼƬurl·��
		String logoLocPath = null; // LOGOͼƬ�ķ������洢·��
		int status_ = 0;
		if (null != status) {
			status_ = Integer.parseInt(status);
		}
		// �ж��ļ��Ƿ�Ϊ��
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			logger.error("uploadFile path---------->" + path);
			String oldFileName = attach.getOriginalFilename();// ԭ�ļ���
			logger.error("ԭ�ļ���---------->" + oldFileName);
			String prefix = FilenameUtils.getExtension(oldFileName); // ԭ�ļ���׺
			logger.error("ԭ�ļ���׺��---------->" + prefix);
			int filesize = 500000;
			logger.debug("�ϴ��ļ���С---------->" + attach.getSize());
			if (attach.getSize() > filesize) {
				request.setAttribute("fileUploadError", "* �ļ���С���ó��� 50KB");
				return "developer/appinfoadd";
			} else if (!prefix.equalsIgnoreCase("jpg") && !prefix.equalsIgnoreCase("jpeg")
					&& !prefix.equalsIgnoreCase("png")) {
				request.setAttribute("fileUploadError", "* �ϴ����ļ�ֻ����jpg��jpeg��png��ʽ");
				return "developer/appinfoadd";
			}
			String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.jpg";
			logger.debug("�µ��ļ���--------->" + fileName);
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// ����
			try {
				attach.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("fileUploadError", "* ���������ϴ�ʧ�ܣ�");
				return "developer/appinfoadd";
			}
			logoPicPath = request.getContextPath() + "/statics/uploadfiles/" + fileName;
			logoLocPath = path + File.separator + fileName;
			app.setLogoPicPath(logoPicPath);
			app.setLogoLocPath(logoLocPath);
		}
		Dev_user user = (Dev_user) session.getAttribute("devUserSession");
		app.setModifyBy(user.getId());
		app.setModifyDate(new Date());
		app_infoService.modifyApp(app);
		if (status_ == 1) {
			app_infoService.modifyStatus(app.getId());
		}
		return "redirect:list";
	}

	@RequestMapping(value = "/flatform/app/delapp")
	@ResponseBody
	public Object delApp(@RequestParam(value = "id") String id) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isNullOrEmpty(id)) {
			resultMap.put("delResult", "notexist");
		} else {
			int id_ = Integer.parseInt(id);
			app_versionService.delVersion(id_);
			if (app_infoService.delApp(id_) > 0) {
				resultMap.put("delResult", "true");
			}else{
				resultMap.put("delResult", "false");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	@RequestMapping(value="/flatform/app/sale")
	@ResponseBody
	public Object OpenOrClose(@RequestParam("id") String appId){
		App_info app = app_infoService.getAppById(Integer.parseInt(appId));
		HashMap<String, String> resultMap = new HashMap<String,String>();
		if(app.getStatus()==4){
			app_infoService.modifyStatus1(Integer.parseInt(appId), 5);
			resultMap.put("resultMsg", "success");
			resultMap.put("errorCode", "0");
		}
		else if(app.getStatus()==5){
			app_infoService.modifyStatus1(Integer.parseInt(appId), 4);
			resultMap.put("resultMsg", "success");
		}else{
			resultMap.put("errorCode", "exception000001");
		}
		return JSONArray.toJSONString(resultMap);
	}
}
