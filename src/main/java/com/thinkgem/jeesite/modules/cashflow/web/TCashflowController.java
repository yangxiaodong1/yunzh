/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cashflow.web;

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
import com.thinkgem.jeesite.modules.cashflow.entity.TCashflow;
import com.thinkgem.jeesite.modules.cashflow.service.TCashflowService;

/**
 * 现金流量表Controller
 * @author zhangtong
 * @version 2015-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cashflow/tCashflow")
public class TCashflowController extends BaseController {

	@Autowired
	private TCashflowService tCashflowService;
	
	@ModelAttribute
	public TCashflow get(@RequestParam(required=false) String id) {
		TCashflow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCashflowService.get(id);
		}
		if (entity == null){
			entity = new TCashflow();
		}
		return entity;
	}
	
	@RequiresPermissions("cashflow:tCashflow:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCashflow tCashflow, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TCashflow> page = tCashflowService.findPage(new Page<TCashflow>(request, response), tCashflow); 
		model.addAttribute("page", page);
		return "modules/cashflow/tCashflowList";
	}

	@RequiresPermissions("cashflow:tCashflow:view")
	@RequestMapping(value = "form")
	public String form(TCashflow tCashflow, Model model) {
		model.addAttribute("tCashflow", tCashflow);
		return "modules/cashflow/tCashflowForm";
	}

	@RequiresPermissions("cashflow:tCashflow:edit")
	@RequestMapping(value = "save")
	public String save(TCashflow tCashflow, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCashflow)){
			return form(tCashflow, model);
		}
		tCashflowService.save(tCashflow);
		addMessage(redirectAttributes, "保存现金流量表成功");
		return "redirect:"+Global.getAdminPath()+"/cashflow/tCashflow/?repage";
	}
	
	@RequiresPermissions("cashflow:tCashflow:edit")
	@RequestMapping(value = "delete")
	public String delete(TCashflow tCashflow, RedirectAttributes redirectAttributes) {
		tCashflowService.delete(tCashflow);
		addMessage(redirectAttributes, "删除现金流量表成功");
		return "redirect:"+Global.getAdminPath()+"/cashflow/tCashflow/?repage";
	}

}