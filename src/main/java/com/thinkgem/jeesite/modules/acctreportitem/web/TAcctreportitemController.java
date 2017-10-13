/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acctreportitem.web;

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
import com.thinkgem.jeesite.modules.acctreportitem.entity.TAcctreportitem;
import com.thinkgem.jeesite.modules.acctreportitem.service.TAcctreportitemService;

/**
 * 公式表Controller
 * @author zhangtong
 * @version 2015-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/acctreportitem/tAcctreportitem")
public class TAcctreportitemController extends BaseController {

	@Autowired
	private TAcctreportitemService tAcctreportitemService;
	
	@ModelAttribute
	public TAcctreportitem get(@RequestParam(required=false) String id) {
		TAcctreportitem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tAcctreportitemService.get(id);
		}
		if (entity == null){
			entity = new TAcctreportitem();
		}
		return entity;
	}
	
	@RequiresPermissions("acctreportitem:tAcctreportitem:view")
	@RequestMapping(value = {"list", ""})
	public String list(TAcctreportitem tAcctreportitem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TAcctreportitem> page = tAcctreportitemService.findPage(new Page<TAcctreportitem>(request, response), tAcctreportitem); 
		model.addAttribute("page", page);
		return "modules/acctreportitem/tAcctreportitemList";
	}

	@RequiresPermissions("acctreportitem:tAcctreportitem:view")
	@RequestMapping(value = "form")
	public String form(TAcctreportitem tAcctreportitem, Model model) {
		model.addAttribute("tAcctreportitem", tAcctreportitem);
		return "modules/acctreportitem/tAcctreportitemForm";
	}

	//@RequiresPermissions("acctreportitem:tAcctreportitem:edit")
	@RequestMapping(value = "save")
	public String save(TAcctreportitem tAcctreportitem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tAcctreportitem)){
			return form(tAcctreportitem, model);
		}
		tAcctreportitemService.save(tAcctreportitem);
		addMessage(redirectAttributes, "保存公式表成功");
		return "redirect:"+Global.getAdminPath()+"/acctreportitem/tAcctreportitem/?repage";
	}
	
	@RequiresPermissions("acctreportitem:tAcctreportitem:edit")
	@RequestMapping(value = "delete")
	public String delete(TAcctreportitem tAcctreportitem, RedirectAttributes redirectAttributes) {
		tAcctreportitemService.delete(tAcctreportitem);
		addMessage(redirectAttributes, "删除公式表成功");
		return "redirect:"+Global.getAdminPath()+"/acctreportitem/tAcctreportitem/?repage";
	}

}