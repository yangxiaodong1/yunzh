/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.web;

import java.util.List;

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
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TCompanyYmc;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TServiceCharge;
import com.thinkgem.jeesite.modules.inspection.service.workstatistics.TCompanyYmcService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 回收站Controller
 * @author xuxiaolong
 * @version 2016-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/workterrace/recyclebin")
public class RecyclebinController extends BaseController {

	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TCompanyYmcService tCompanyYmcService;
	@ModelAttribute
	public TCustomer get(@RequestParam(required=false) String id) {
		TCustomer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCustomerService.get(id);
		}
		if (entity == null){
			entity = new TCustomer();
		}
		return entity;
	}
	
	//@RequiresPermissions("workterrace:recyclebin:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCustomer tCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
		tCustomer.setDelFlag("1");
		tCustomer.setSupervisor(UserUtils.getUser().getId());
		Page<TCustomer> page= tCustomerService.listDelectBySupervisor(new Page<TCustomer>(request, response),tCustomer); 
	
		
		model.addAttribute("page", page);
		return "modules/workterrace/recyclebinList";
	}

	//@RequiresPermissions("workterrace:recyclebin:view")
	@RequestMapping(value = "form")
	public String form(TCustomer tCustomer, Model model) {
		model.addAttribute("chargeToAccount", tCustomer);
		return "modules/workterrace/recyclebinForm";
	}

	//@RequiresPermissions("workterrace:recyclebin:edit")
	@RequestMapping(value = "save")
	public String save(TCustomer tCustomer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCustomer)){
			return form(tCustomer, model);
		}
		tCustomerService.save(tCustomer);
		addMessage(redirectAttributes, "保存记账成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/recyclebin/?repage";
	}
	
	//@RequiresPermissions("workterrace:recyclebin:edit")
	@ResponseBody
	@RequestMapping(value = "delete")
	public void delete(String id, RedirectAttributes redirectAttributes) {
		
		
		try {
			tCustomerService.restore(id);
			TCompanyYmc tCompanyYmc=new TCompanyYmc();
			tCompanyYmc.setCompanyId(UserUtils.getUser().getCompany().getId());
			tCompanyYmcService.updatePlus(tCompanyYmc);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}