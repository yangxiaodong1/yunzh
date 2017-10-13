/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.taxbaseformula.web;

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
import com.thinkgem.jeesite.modules.taxbaseformula.entity.VTaxbaseformula;
import com.thinkgem.jeesite.modules.taxbaseformula.service.VTaxbaseformulaService;

/**
 * 税率公式和科目表Controller
 * @author zt
 * @version 2016-01-11
 */
@Controller
@RequestMapping(value = "${adminPath}/taxbaseformula/vTaxbaseformula")
public class VTaxbaseformulaController extends BaseController {

	@Autowired
	private VTaxbaseformulaService vTaxbaseformulaService;
	
	@ModelAttribute
	public VTaxbaseformula get(@RequestParam(required=false) String id) {
		VTaxbaseformula entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vTaxbaseformulaService.get(id);
		}
		if (entity == null){
			entity = new VTaxbaseformula();
		}
		return entity;
	}
	
	@RequiresPermissions("taxbaseformula:vTaxbaseformula:view")
	@RequestMapping(value = {"list", ""})
	public String list(VTaxbaseformula vTaxbaseformula, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<VTaxbaseformula> page = vTaxbaseformulaService.findPage(new Page<VTaxbaseformula>(request, response), vTaxbaseformula); 
		model.addAttribute("page", page);
		return "modules/taxbaseformula/vTaxbaseformulaList";
	}

	@RequiresPermissions("taxbaseformula:vTaxbaseformula:view")
	@RequestMapping(value = "form")
	public String form(VTaxbaseformula vTaxbaseformula, Model model) {
		model.addAttribute("vTaxbaseformula", vTaxbaseformula);
		return "modules/taxbaseformula/vTaxbaseformulaForm";
	}

	@RequiresPermissions("taxbaseformula:vTaxbaseformula:edit")
	@RequestMapping(value = "save")
	public String save(VTaxbaseformula vTaxbaseformula, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vTaxbaseformula)){
			return form(vTaxbaseformula, model);
		}
		vTaxbaseformulaService.save(vTaxbaseformula);
		addMessage(redirectAttributes, "保存税率公式和科目表成功");
		return "redirect:"+Global.getAdminPath()+"/taxbaseformula/vTaxbaseformula/?repage";
	}
	
	@RequiresPermissions("taxbaseformula:vTaxbaseformula:edit")
	@RequestMapping(value = "delete")
	public String delete(VTaxbaseformula vTaxbaseformula, RedirectAttributes redirectAttributes) {
		vTaxbaseformulaService.delete(vTaxbaseformula);
		addMessage(redirectAttributes, "删除税率公式和科目表成功");
		return "redirect:"+Global.getAdminPath()+"/taxbaseformula/vTaxbaseformula/?repage";
	}

}