/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billtype.web;

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
import com.thinkgem.jeesite.modules.billtype.entity.TBillType;
import com.thinkgem.jeesite.modules.billtype.service.TBillTypeService;

/**
 * 发票类型Controller
 * @author 发票类型
 * @version 2015-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/billtype/tBillType")
public class TBillTypeController extends BaseController {

	@Autowired
	private TBillTypeService tBillTypeService;
	
	@ModelAttribute
	public TBillType get(@RequestParam(required=false) String id) {
		TBillType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tBillTypeService.get(id);
		}
		if (entity == null){
			entity = new TBillType();
		}
		return entity;
	}
	
	@RequiresPermissions("billtype:tBillType:view")
	@RequestMapping(value = {"list", ""})
	public String list(TBillType tBillType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TBillType> page = tBillTypeService.findPage(new Page<TBillType>(request, response), tBillType); 
		model.addAttribute("page", page);
		return "modules/billtype/tBillTypeList";
	}

	@RequiresPermissions("billtype:tBillType:view")
	@RequestMapping(value = "form")
	public String form(TBillType tBillType, Model model) {
		model.addAttribute("tBillType", tBillType);
		return "modules/billtype/tBillTypeForm";
	}

	@RequiresPermissions("billtype:tBillType:edit")
	@RequestMapping(value = "save")
	public String save(TBillType tBillType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tBillType)){
			return form(tBillType, model);
		}
		tBillTypeService.save(tBillType);
		addMessage(redirectAttributes, "保存发票类型成功");
		return "redirect:"+Global.getAdminPath()+"/billtype/tBillType/?repage";
	}
	
	@RequiresPermissions("billtype:tBillType:edit")
	@RequestMapping(value = "delete")
	public String delete(TBillType tBillType, RedirectAttributes redirectAttributes) {
		tBillTypeService.delete(tBillType);
		addMessage(redirectAttributes, "删除发票类型成功");
		return "redirect:"+Global.getAdminPath()+"/billtype/tBillType/?repage";
	}

}