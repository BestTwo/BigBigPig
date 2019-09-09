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
		String apkLocPath = null; // apk�ļ��ķ������洢·��
		String downloadLink = null; // ��������
		// �ж��ļ��Ƿ�Ϊ��
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			logger.error("uploadFile path---------->" + path);
			String oldFileName = attach.getOriginalFilename();// ԭ�ļ���
			logger.error("ԭ�ļ���---------->" + oldFileName);
			String prefix = FilenameUtils.getExtension(oldFileName); // ԭ�ļ���׺
			logger.error("ԭ�ļ���׺��---------->" + prefix);
			int filesize = 5000000;
			logger.debug("�ϴ��ļ���С---------->" + attach.getSize());
			if (attach.getSize() > filesize) {
				request.setAttribute("fileUploadError", "* �ļ���С���ó��� 500MB");
				return "developer/appversionadd";
			} else if (!prefix.equalsIgnoreCase("apk")) {
				request.setAttribute("fileUploadError", "* �ϴ����ļ�ֻ����apk��ʽ");
				return "developer/appversionadd";
			}
			String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.apk";
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
		request.setAttribute("fileUploadError", "* ���ϴ��ļ�!");
		return "developer/appversionadd";
	}

	@RequestMapping(value = "/flatform/app/appversionmodifysave")
	public String ModifyVersion(@RequestParam(value = "attach") MultipartFile attach, HttpServletRequest request,
			HttpSession session, App_version app_version, @RequestParam(value = "downloadLink") String downloadLink) {
		logger.info("downloadLink:"+downloadLink+"=============================");
		if (downloadLink.equals("") || downloadLink==null) {
			String apkLocPath = null; // apk�ļ��ķ������洢·��
			String downloadLink_ = null; // ��������
			// �ж��ļ��Ƿ�Ϊ��
			if (!attach.isEmpty()) {
				String path = request.getSession().getServletContext()
						.getRealPath("statics" + File.separator + "uploadfiles");
				logger.error("uploadFile path---------->" + path);
				String oldFileName = attach.getOriginalFilename();// ԭ�ļ���
				logger.error("ԭ�ļ���---------->" + oldFileName);
				String prefix = FilenameUtils.getExtension(oldFileName); // ԭ�ļ���׺
				logger.error("ԭ�ļ���׺��---------->" + prefix);
				int filesize = 5000000;
				logger.debug("�ϴ��ļ���С---------->" + attach.getSize());
				if (attach.getSize() > filesize) {
					request.setAttribute("fileUploadError", "* �ļ���С���ó��� 500MB");
					return "developer/appversionmodify";
				} else if (!prefix.equalsIgnoreCase("apk")) {
					request.setAttribute("fileUploadError", "* �ϴ����ļ�ֻ����apk��ʽ");
					return "developer/appversionmodify";
				}
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.apk";
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
					return "developer/appversionmodify";
				}
				downloadLink_ = request.getContextPath() + "/statics/uploadfiles/" + fileName;
				apkLocPath = path + File.separator + fileName;
				app_version.setDownloadLink(downloadLink_);
				app_version.setApkLocPath(apkLocPath);
				app_version.setApkFileName("com." + fileName);
			}else{
				request.setAttribute("fileUploadError", "* ���ϴ��ļ�!");
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
