/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.intelligenttemplate.web;

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
import com.thinkgem.jeesite.modules.intelligenttemplate.entity.TIntelligentTemplate;
import com.thinkgem.jeesite.modules.intelligenttemplate.service.TIntelligentTemplateService;

/**
 * intelligenttemplateController
 * @author intelligenttemplate
 * @version 2015-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/intelligenttemplate/tIntelligentTemplate")
public class TIntelligentTemplateController extends BaseController {

	@Autowired
	private TIntelligentTemplateService tIntelligentTemplateService;
	
	@ModelAttribute
	public TIntelligentTemplate get(@RequestParam(required=false) String id) {
		TIntelligentTemplate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tIntelligentTemplateService.get(id);
		}
		if (entity == null){
			entity = new TIntelligentTemplate();
		}
		return entity;
	}
	
	@RequiresPermissions("intelligenttemplate:tIntelligentTemplate:view")
	@RequestMapping(value = {"list", ""})
	public String list(TIntelligentTemplate tIntelligentTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TIntelligentTemplate> page = tIntelligentTemplateService.findPage(new Page<TIntelligentTemplate>(request, response), tIntelligentTemplate); 
		model.addAttribute("page", page);
		return "modules/intelligenttemplate/tIntelligentTemplateList";
	}

	@RequiresPermissions("intelligenttemplate:tIntelligentTemplate:view")
	@RequestMapping(value = "form")
	public String form(TIntelligentTemplate tIntelligentTemplate, Model model) {
		model.addAttribute("tIntelligentTemplate", tIntelligentTemplate);
		return "modules/intelligenttemplate/tIntelligentTemplateForm";
	}

	@RequiresPermissions("intelligenttemplate:tIntelligentTemplate:edit")
	@RequestMapping(value = "save")
	public String save(TIntelligentTemplate tIntelligentTemplate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tIntelligentTemplate)){
			return form(tIntelligentTemplate, model);
		}
		tIntelligentTemplateService.save(tIntelligentTemplate);
		addMessage(redirectAttributes, "保存intelligenttemplate成功");
		return "redirect:"+Global.getAdminPath()+"/intelligenttemplate/tIntelligentTemplate/?repage";
	}
	
	@RequiresPermissions("intelligenttemplate:tIntelligentTemplate:edit")
	@RequestMapping(value = "delete")
	public String delete(TIntelligentTemplate tIntelligentTemplate, RedirectAttributes redirectAttributes) {
		tIntelligentTemplateService.delete(tIntelligentTemplate);
		addMessage(redirectAttributes, "删除intelligenttemplate成功");
		return "redirect:"+Global.getAdminPath()+"/intelligenttemplate/tIntelligentTemplate/?repage";
	}

}