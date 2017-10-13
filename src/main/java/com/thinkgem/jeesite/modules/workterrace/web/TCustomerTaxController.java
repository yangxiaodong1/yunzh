/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.workterrace.entity.TCustomerSalary;
import com.thinkgem.jeesite.modules.workterrace.entity.TCustomerTax;
import com.thinkgem.jeesite.modules.workterrace.service.TCustomerTaxService;

/**
 * 报税表Controller
 * @author xuxiaolong
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/workterrace/tCustomerTax")
public class TCustomerTaxController extends BaseController {

	
	@Autowired
	private TCustomerService tCustomerService;
	
	@Autowired
	private TCustomerTaxService tCustomerTaxService;
	@Autowired
	private SystemService systemService;
	@ModelAttribute
	public TCustomerTax get(@RequestParam(required=false) String id) {
		TCustomerTax entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCustomerTaxService.get(id);
		}
		if (entity == null){
			entity = new TCustomerTax();
		}
		return entity;
	}
	
	//@RequiresPermissions("workterrace:tCustomerTax:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCustomerTax tCustomerTax, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TCustomerTax> page = tCustomerTaxService.findPage(new Page<TCustomerTax>(request, response), tCustomerTax); 
		
		List<TCustomerTax> tCustomerTaxList=tCustomerTaxService.tCustomerTaxList(tCustomerTax);
		User currentUser = UserUtils.getUser();
		//获取本公司的记账人
		String rolesName=currentUser.getRoleNames();
		List<User> userlist=new ArrayList<User>();
		User userr;
		if(rolesName.contains("记账公司管理员")){
			 userlist=systemService.findUserByCompanyid(currentUser);
		}else if(rolesName.equals("记账员")){
			userlist.add(currentUser);
		}
		
		model.addAttribute("userlist", userlist);
		model.addAttribute("page", page);
		return "modules/workterrace/tCustomerTaxList";
	}
	@ResponseBody
	@RequestMapping(value = {"tCustomerTaxList"})
	public List<TCustomerTax> tCustomerTaxList(TCustomerTax tCustomerTax, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<TCustomerTax> tCustomerTaxList=tCustomerTaxService.tCustomerTaxList(tCustomerTax);
		return tCustomerTaxList;
	}


	//@RequiresPermissions("workterrace:tCustomerTax:view")
	@RequestMapping(value = "form")
	public String form(TCustomerTax tCustomerTax, Model model) {
		model.addAttribute("tCustomerTax", tCustomerTax);
		return "modules/workterrace/tCustomerTaxForm";
	}
	@ResponseBody
	@RequestMapping(value = "savejson")
	public String savejson(@RequestBody List<TCustomerTax> CustomerTaxTist,TCustomerTax tCustomerTax) {
		String msg="0";
		String customerId=tCustomerTax.getCustomerId();
		String taxPeriod=tCustomerTax.getTaxPeriod();
		if(CustomerTaxTist.size()>0){
			for(TCustomerTax ct: CustomerTaxTist){
				ct.setCustomerId(customerId);
				ct.setTaxPeriod(taxPeriod);
				tCustomerTaxService.save(ct);
			}
		}else {
			tCustomerTaxService.save(tCustomerTax);
		}
		int currentperiod=Integer.parseInt(tCustomerService.get(customerId).getCurrentperiod());
		int taxPeriodint=Integer.parseInt(taxPeriod);
		if(taxPeriodint+1<currentperiod){
			msg="1";
		}
		return msg;
		
	}
	@ResponseBody
	@RequestMapping(value = {"CustomerTaxTist", ""})
	public List<TCustomerTax> CustomerTaxTist(TCustomerTax tCustomerTax, HttpServletRequest request, HttpServletResponse response, Model model) {
		return tCustomerTaxService.tCustomerTaxList(tCustomerTax);
	}
	//@RequiresPermissions("workterrace:tCustomerTax:edit")
	@RequestMapping(value = "save")
	public String save(TCustomerTax tCustomerTax, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCustomerTax)){
			return form(tCustomerTax, model);
		}
		tCustomerTaxService.save(tCustomerTax);
		addMessage(redirectAttributes, "保存报税表成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/tCustomerTax/?repage";
	}
	
	//@RequiresPermissions("workterrace:tCustomerTax:edit")
	@RequestMapping(value = "delete")
	public String delete(TCustomerTax tCustomerTax, RedirectAttributes redirectAttributes) {
		tCustomerTaxService.delete(tCustomerTax);
		addMessage(redirectAttributes, "删除报税表成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/tCustomerTax/?repage";
	}

}