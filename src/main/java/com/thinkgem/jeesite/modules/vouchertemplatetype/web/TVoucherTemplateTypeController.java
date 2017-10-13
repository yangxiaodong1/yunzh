/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplatetype.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.vouchertemplatetype.entity.TVoucherTemplateType;
import com.thinkgem.jeesite.modules.vouchertemplatetype.service.TVoucherTemplateTypeService;

/**
 * 凭证模板类别Controller
 * @author 凭证模板类别
 * @version 2015-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/vouchertemplatetype/tVoucherTemplateType")
public class TVoucherTemplateTypeController extends BaseController {

	@Autowired
	private TVoucherTemplateTypeService tVoucherTemplateTypeService;
	
	@ModelAttribute
	public TVoucherTemplateType get(@RequestParam(required=false) String id) {
		TVoucherTemplateType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tVoucherTemplateTypeService.get(id);
		}
		if (entity == null){
			entity = new TVoucherTemplateType();
		}
		return entity;
	}
	
	@RequiresPermissions("vouchertemplatetype:tVoucherTemplateType:view")
	@RequestMapping(value = {"list", ""})
	public String list(TVoucherTemplateType tVoucherTemplateType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TVoucherTemplateType> page = tVoucherTemplateTypeService.findPage(new Page<TVoucherTemplateType>(request, response), tVoucherTemplateType); 
		model.addAttribute("page", page);
		return "modules/vouchertemplatetype/tVoucherTemplateTypeList";
	}

	@RequiresPermissions("vouchertemplatetype:tVoucherTemplateType:view")
	@RequestMapping(value = "form")
	public String form(TVoucherTemplateType tVoucherTemplateType, Model model) {
		model.addAttribute("tVoucherTemplateType", tVoucherTemplateType);
		return "modules/vouchertemplatetype/tVoucherTemplateTypeForm";
	}

	@RequiresPermissions("vouchertemplatetype:tVoucherTemplateType:edit")
	@RequestMapping(value = "save")
	public String save(TVoucherTemplateType tVoucherTemplateType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tVoucherTemplateType)){
			return form(tVoucherTemplateType, model);
		}
		tVoucherTemplateTypeService.save(tVoucherTemplateType);
		addMessage(redirectAttributes, "保存凭证模板类别成功");
		return "redirect:"+Global.getAdminPath()+"/vouchertemplatetype/tVoucherTemplateType/?repage";
	}
	
	@RequiresPermissions("vouchertemplatetype:tVoucherTemplateType:edit")
	@RequestMapping(value = "delete")
	public String delete(TVoucherTemplateType tVoucherTemplateType, RedirectAttributes redirectAttributes) {
		tVoucherTemplateTypeService.delete(tVoucherTemplateType);
		addMessage(redirectAttributes, "删除凭证模板类别成功");
		return "redirect:"+Global.getAdminPath()+"/vouchertemplatetype/tVoucherTemplateType/?repage";
	}

	@RequestMapping(value = "getVoucherTemplateType")
	@ResponseBody
	public Object getVoucherTemplateType(){
		return tVoucherTemplateTypeService.findList(null);
	}
}