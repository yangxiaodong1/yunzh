/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertitle.web;

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
import com.thinkgem.jeesite.modules.vouchertitle.entity.TVoucherTitle;
import com.thinkgem.jeesite.modules.vouchertitle.service.TVoucherTitleService;

/**
 * 凭证标题Controller
 * @author 凭证标题
 * @version 2015-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/vouchertitle/tVoucherTitle")
public class TVoucherTitleController extends BaseController {

	@Autowired
	private TVoucherTitleService tVoucherTitleService;
	
	@ModelAttribute
	public TVoucherTitle get(@RequestParam(required=false) String id) {
		TVoucherTitle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tVoucherTitleService.get(id);
		}
		if (entity == null){
			entity = new TVoucherTitle();
		}
		return entity;
	}
	
	@RequiresPermissions("vouchertitle:tVoucherTitle:view")
	@RequestMapping(value = {"list", ""})
	public String list(TVoucherTitle tVoucherTitle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TVoucherTitle> page = tVoucherTitleService.findPage(new Page<TVoucherTitle>(request, response), tVoucherTitle); 
		model.addAttribute("page", page);
		return "modules/vouchertitle/tVoucherTitleList";
	}

	@RequiresPermissions("vouchertitle:tVoucherTitle:view")
	@RequestMapping(value = "form")
	public String form(TVoucherTitle tVoucherTitle, Model model) {
		model.addAttribute("tVoucherTitle", tVoucherTitle);
		return "modules/vouchertitle/tVoucherTitleForm";
	}

	@RequiresPermissions("vouchertitle:tVoucherTitle:edit")
	@RequestMapping(value = "save")
	public String save(TVoucherTitle tVoucherTitle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tVoucherTitle)){
			return form(tVoucherTitle, model);
		}
		tVoucherTitleService.save(tVoucherTitle);
		addMessage(redirectAttributes, "保存凭证标题成功");
		return "redirect:"+Global.getAdminPath()+"/vouchertitle/tVoucherTitle/?repage";
	}
	
	@RequiresPermissions("vouchertitle:tVoucherTitle:edit")
	@RequestMapping(value = "delete")
	public String delete(TVoucherTitle tVoucherTitle, RedirectAttributes redirectAttributes) {
		tVoucherTitleService.delete(tVoucherTitle);
		addMessage(redirectAttributes, "删除凭证标题成功");
		return "redirect:"+Global.getAdminPath()+"/vouchertitle/tVoucherTitle/?repage";
	}

}