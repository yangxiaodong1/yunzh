/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.clientfile.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.clientfile.entity.TClientFile;
import com.thinkgem.jeesite.modules.clientfile.service.TClientFileService;

/**
 * 添加图片Controller
 * @author yang
 * @version 2016-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/clientfile/tClientFile")
public class TClientFileController extends BaseController {

	@Autowired
	private TClientFileService tClientFileService;
	
	@ModelAttribute
	public TClientFile get(@RequestParam(required=false) String id) {
		TClientFile entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tClientFileService.get(id);
		}
		if (entity == null){
			entity = new TClientFile();
		}
		return entity;
	}
	
	@RequiresPermissions("clientfile:tClientFile:view")
	@RequestMapping(value = {"list", ""})
	public String list(TClientFile tClientFile, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TClientFile> page = tClientFileService.findPage(new Page<TClientFile>(request, response), tClientFile); 
		model.addAttribute("page", page);
		return "modules/clientfile/tClientFileList";
	}

	@RequiresPermissions("clientfile:tClientFile:view")
	@RequestMapping(value = "form")
	public String form(TClientFile tClientFile, Model model) {
		model.addAttribute("tClientFile", tClientFile);
		return "modules/clientfile/tClientFileForm";
	}

	@RequiresPermissions("clientfile:tClientFile:edit")
	@RequestMapping(value = "save")
	public String save(TClientFile tClientFile, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tClientFile)){
			return form(tClientFile, model);
		}
		tClientFileService.save(tClientFile);
		addMessage(redirectAttributes, "保存添加图片成功");
		return "redirect:"+Global.getAdminPath()+"/clientfile/tClientFile/?repage";
	}
	
	@RequiresPermissions("clientfile:tClientFile:edit")
	@RequestMapping(value = "delete")
	public String delete(TClientFile tClientFile, RedirectAttributes redirectAttributes) {
		tClientFileService.delete(tClientFile);
		addMessage(redirectAttributes, "删除添加图片成功");
		return "redirect:"+Global.getAdminPath()+"/clientfile/tClientFile/?repage";
	}

}