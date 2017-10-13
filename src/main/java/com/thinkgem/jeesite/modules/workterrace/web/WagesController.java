/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.web;

import java.util.ArrayList;
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
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TServiceCharge;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 工资展示Controller
 * @author xuxiaolong
 * @version 2015-12-30
 */
@Controller
@RequestMapping(value = "${adminPath}/workterrace/wages")
public class WagesController extends BaseController {

	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private SystemService systemService;
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
	
	//@RequiresPermissions("workterrace:wages:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCustomer tCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
	
		tCustomer.setSupervisor(UserUtils.getUser().getId());
//		Page<TCustomer> page=tCustomerService.listWagesBySupervisor(new Page<TCustomer>(request, response),  tCustomer);
		List<TCustomer> listWagesBySupervisor=tCustomerService.listWagesBySupervisor(tCustomer);
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
		model.addAttribute("username",currentUser.getName());
		model.addAttribute("userlist", userlist);
		model.addAttribute("userList",userlist);
		model.addAttribute("tCustomer", tCustomer);
//		model.addAttribute("page", page);
		model.addAttribute("listWagesBySupervisor", listWagesBySupervisor);
		TCustomer tc=tCustomerService.wagesCount(tCustomer);
		model.addAttribute("tc", tc);
		model.addAttribute("tServeInfoinsert",new TServiceCharge());
		model.addAttribute("tServiceCharge",new TServiceCharge());
		return "modules/workterrace/wagesList";
	}
	@ResponseBody
	@RequestMapping(value = "wagesCount")
	public TCustomer wagesCount(String byYearMonth, Model model) {
		TCustomer tCustomer=new TCustomer();
		tCustomer.setSupervisor(UserUtils.getUser().getId());
		tCustomer.setByYearMonth(byYearMonth);
		return tCustomerService.wagesCount(tCustomer);
	}
	//@RequiresPermissions("workterrace:wages:view")
	@RequestMapping(value = "form")
	public String form(TCustomer tCustomer, Model model) {
		model.addAttribute("chargeToAccount", tCustomer);
		return "modules/workterrace/wagesForm";
	}

	//@RequiresPermissions("workterrace:wages:edit")
	@RequestMapping(value = "save")
	public String save(TCustomer tCustomer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCustomer)){
			return form(tCustomer, model);
		}
		tCustomerService.save(tCustomer);
		addMessage(redirectAttributes, "保存工资展示成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/wages/?repage";
	}
	
	//@RequiresPermissions("workterrace:wages:edit")
	@RequestMapping(value = "delete")
	public String delete(TCustomer tCustomer, RedirectAttributes redirectAttributes) {
		tCustomerService.delete(tCustomer);
		addMessage(redirectAttributes, "删除工资展示成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/wages/?repage";
	}

}