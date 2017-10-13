/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.web;

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
import com.thinkgem.jeesite.modules.account.entity.TaccountGroup;
import com.thinkgem.jeesite.modules.account.service.TaccountGroupService;

/**
 * 科目分组Controller
 * @author 陈明
 * @version 2015-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/account/taccountGroup")
public class TaccountGroupController extends BaseController {

	@Autowired
	private TaccountGroupService taccountGroupService;
	
	@ModelAttribute
	public TaccountGroup get(@RequestParam(required=false) String id) {
		TaccountGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = taccountGroupService.get(id);
		}
		if (entity == null){
			entity = new TaccountGroup();
		}
		return entity;
	}
	
	@RequiresPermissions("account:taccountGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaccountGroup taccountGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TaccountGroup> page = taccountGroupService.findPage(new Page<TaccountGroup>(request, response), taccountGroup); 
		model.addAttribute("page", page);
		return "modules/account/taccountGroupList";
	}

	@RequiresPermissions("account:taccountGroup:view")
	@RequestMapping(value = "form")
	public String form(TaccountGroup taccountGroup, Model model) {
		model.addAttribute("taccountGroup", taccountGroup);
		return "modules/account/taccountGroupForm";
	}

	@RequiresPermissions("account:taccountGroup:edit")
	@RequestMapping(value = "save")
	public String save(TaccountGroup taccountGroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, taccountGroup)){
			return form(taccountGroup, model);
		}
		taccountGroupService.save(taccountGroup);
		addMessage(redirectAttributes, "保存科目分组成功");
		return "redirect:"+Global.getAdminPath()+"/account/taccountGroup/?repage";
	}
	
	@RequiresPermissions("account:taccountGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(TaccountGroup taccountGroup, RedirectAttributes redirectAttributes) {
		taccountGroupService.delete(taccountGroup);
		addMessage(redirectAttributes, "删除科目分组成功");
		return "redirect:"+Global.getAdminPath()+"/account/taccountGroup/?repage";
	}

}