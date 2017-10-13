/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.web.workstatistics;

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
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TCompanyYmc;
import com.thinkgem.jeesite.modules.inspection.service.workstatistics.TCompanyYmcService;

/**
 * 客户总数记录Controller
 * @author xuxiaolong
 * @version 2016-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/workstatistics/tCompanyYmc")
public class TCompanyYmcController extends BaseController {

	@Autowired
	private TCompanyYmcService tCompanyYmcService;
	
	@ModelAttribute
	public TCompanyYmc get(@RequestParam(required=false) String id) {
		TCompanyYmc entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCompanyYmcService.get(id);
		}
		if (entity == null){
			entity = new TCompanyYmc();
		}
		return entity;
	}
	
	@RequiresPermissions("inspection:workstatistics:tCompanyYmc:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCompanyYmc tCompanyYmc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TCompanyYmc> page = tCompanyYmcService.findPage(new Page<TCompanyYmc>(request, response), tCompanyYmc); 
		model.addAttribute("page", page);
		return "modules/inspection/workstatistics/tCompanyYmcList";
	}

	@RequiresPermissions("inspection:workstatistics:tCompanyYmc:view")
	@RequestMapping(value = "form")
	public String form(TCompanyYmc tCompanyYmc, Model model) {
		model.addAttribute("tCompanyYmc", tCompanyYmc);
		return "modules/inspection/workstatistics/tCompanyYmcForm";
	}

	@RequiresPermissions("inspection:workstatistics:tCompanyYmc:edit")
	@RequestMapping(value = "save")
	public String save(TCompanyYmc tCompanyYmc, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCompanyYmc)){
			return form(tCompanyYmc, model);
		}
		tCompanyYmcService.save(tCompanyYmc);
		addMessage(redirectAttributes, "保存客户总数记录成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tCompanyYmc/?repage";
	}
	
	@RequiresPermissions("inspection:workstatistics:tCompanyYmc:edit")
	@RequestMapping(value = "delete")
	public String delete(TCompanyYmc tCompanyYmc, RedirectAttributes redirectAttributes) {
		tCompanyYmcService.delete(tCompanyYmc);
		addMessage(redirectAttributes, "删除客户总数记录成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tCompanyYmc/?repage";
	}

}