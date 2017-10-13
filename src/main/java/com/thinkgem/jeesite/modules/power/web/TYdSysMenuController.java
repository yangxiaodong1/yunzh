/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.power.web;

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
import com.thinkgem.jeesite.modules.power.entity.TYdSysMenu;
import com.thinkgem.jeesite.modules.power.service.TYdSysMenuService;

/**
 * 权限列表Controller
 * @author yang
 * @version 2016-01-30
 */
@Controller
@RequestMapping(value = "${adminPath}/power/tYdSysMenu")
public class TYdSysMenuController extends BaseController {

	@Autowired
	private TYdSysMenuService tYdSysMenuService;
	
	@ModelAttribute
	public TYdSysMenu get(@RequestParam(required=false) String id) {
		TYdSysMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tYdSysMenuService.get(id);
		}
		if (entity == null){
			entity = new TYdSysMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("power:tYdSysMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(TYdSysMenu tYdSysMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TYdSysMenu> page = tYdSysMenuService.findPage(new Page<TYdSysMenu>(request, response), tYdSysMenu); 
		model.addAttribute("page", page);
		return "modules/power/tYdSysMenuList";
	}

	@RequiresPermissions("power:tYdSysMenu:view")
	@RequestMapping(value = "form")
	public String form(TYdSysMenu tYdSysMenu, Model model) {
		model.addAttribute("tYdSysMenu", tYdSysMenu);
		return "modules/power/tYdSysMenuForm";
	}

	@RequiresPermissions("power:tYdSysMenu:edit")
	@RequestMapping(value = "save")
	public String save(TYdSysMenu tYdSysMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tYdSysMenu)){
			return form(tYdSysMenu, model);
		}
		tYdSysMenuService.save(tYdSysMenu);
		addMessage(redirectAttributes, "保存权限列表成功");
		return "redirect:"+Global.getAdminPath()+"/power/tYdSysMenu/?repage";
	}
	
	@RequiresPermissions("power:tYdSysMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(TYdSysMenu tYdSysMenu, RedirectAttributes redirectAttributes) {
		tYdSysMenuService.delete(tYdSysMenu);
		addMessage(redirectAttributes, "删除权限列表成功");
		return "redirect:"+Global.getAdminPath()+"/power/tYdSysMenu/?repage";
	}

}