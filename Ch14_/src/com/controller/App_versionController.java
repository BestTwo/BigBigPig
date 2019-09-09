package com.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pojo.App_version;
import com.pojo.Dev_user;
import com.service.appinfo.App_infoService;
import com.service.appversion.App_versionService;

@Controller
@RequestMapping("/dev")
public class App_versionController {

	Logger logger = Logger.getLogger(App_versionController.class);

	@Autowired
	public App_versionService app_versionService;
   
	@Autowired  
	public App_infoService App_infoService;
 
	@RequestMapping(value = "/flatform/app/addversionsave")
	public String addVersion(@RequestParam(value = "a_downloadLink") MultipartFile attach, App_version app_version,
			HttpSession session, HttpServletRequest request) {
		String apkLocPath = null; // apk文件的服务器存储路径
		String downloadLink = null; // 下载链接
		// 判断文件是否为空
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			logger.error("uploadFile path---------->" + path);
			String oldFileName = attach.getOriginalFilename();// 原文件名
			logger.error("原文件名---------->" + oldFileName);
			String prefix = FilenameUtils.getExtension(oldFileName); // 原文件后缀
			logger.error("原文件后缀名---------->" + prefix);
			int filesize = 5000000;
			logger.debug("上传文件大小---------->" + attach.getSize());
			if (attach.getSize() > filesize) {
				request.setAttribute("fileUploadError", "* 文件大小不得超过 500MB");
				return "developer/appversionadd";
			} else if (!prefix.equalsIgnoreCase("apk")) {
				request.setAttribute("fileUploadError", "* 上传的文件只能是apk格式");
				return "developer/appversionadd";
			}
			String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.apk";
			logger.debug("新的文件名--------->" + fileName);
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存
			try {
				attach.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("fileUploadError", "* 发生错误，上传失败！");
				return "developer/appversionadd";
			}
			downloadLink = request.getContextPath() + "/statics/uploadfiles/" + fileName;
			apkLocPath = path + File.separator + fileName;
			app_version.setDownloadLink(downloadLink);
			app_version.setApkLocPath(apkLocPath);
			Dev_user user = (Dev_user) session.getAttribute("devUserSession");
			app_version.setCreatedBy(user.getId());
			app_version.setCreationDate(new Date());
			app_version.setPublishStatus(3);
			app_version.setApkFileName("com." + fileName);
			if (app_versionService.addVersion(app_version) > 0) {
				App_infoService.modifyVersion(app_version.getAppId());
			}
			return "redirect:list";
		}
		request.setAttribute("fileUploadError", "* 请上传文件!");
		return "developer/appversionadd";
	}

	@RequestMapping(value = "/flatform/app/appversionmodifysave")
	public String ModifyVersion(@RequestParam(value = "attach") MultipartFile attach, HttpServletRequest request,
			HttpSession session, App_version app_version, @RequestParam(value = "downloadLink") String downloadLink) {
		logger.info("downloadLink:"+downloadLink+"=============================");
		if (downloadLink.equals("") || downloadLink==null) {
			String apkLocPath = null; // apk文件的服务器存储路径
			String downloadLink_ = null; // 下载链接
			// 判断文件是否为空
			if (!attach.isEmpty()) {
				String path = request.getSession().getServletContext()
						.getRealPath("statics" + File.separator + "uploadfiles");
				logger.error("uploadFile path---------->" + path);
				String oldFileName = attach.getOriginalFilename();// 原文件名
				logger.error("原文件名---------->" + oldFileName);
				String prefix = FilenameUtils.getExtension(oldFileName); // 原文件后缀
				logger.error("原文件后缀名---------->" + prefix);
				int filesize = 5000000;
				logger.debug("上传文件大小---------->" + attach.getSize());
				if (attach.getSize() > filesize) {
					request.setAttribute("fileUploadError", "* 文件大小不得超过 500MB");
					return "developer/appversionmodify";
				} else if (!prefix.equalsIgnoreCase("apk")) {
					request.setAttribute("fileUploadError", "* 上传的文件只能是apk格式");
					return "developer/appversionmodify";
				}
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.apk";
				logger.debug("新的文件名--------->" + fileName);
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 保存
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "* 发生错误，上传失败！");
					return "developer/appversionmodify";
				}
				downloadLink_ = request.getContextPath() + "/statics/uploadfiles/" + fileName;
				apkLocPath = path + File.separator + fileName;
				app_version.setDownloadLink(downloadLink_);
				app_version.setApkLocPath(apkLocPath);
				app_version.setApkFileName("com." + fileName);
			}else{
				request.setAttribute("fileUploadError", "* 请上传文件!");
				return "developer/appversionmodify";
			}
		}
		Dev_user user = (Dev_user) session.getAttribute("devUserSession");
		app_version.setModifyBy(user.getId());
		app_version.setModifyDate(new Date());
		app_versionService.modifyVersion(app_version);
		return "redirect:list";
	}
}
