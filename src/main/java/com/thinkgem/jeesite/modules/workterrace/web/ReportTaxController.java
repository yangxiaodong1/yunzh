/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.web;

import java.util.ArrayList;
import java.util.Calendar;
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
 * 记账Controller
 * @author xuxiaolong
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/workterrace/reportTax")
public class ReportTaxController extends BaseController {

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
	//@RequiresPermissions("workterrace:reportTax:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCustomer tCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
//		int byYear=Integer.parseInt(tCustomer.getByYear()+"00");//条件年
//		Page<TCustomer> page=new Page<TCustomer>(request, response);
//		
//		tCustomer.setPage(page);
//		tCustomer.setSupervisor(UserUtils.getUser().getId());
//		List<TCustomer> customerList= tCustomerService.listTCustomerBySupervisor(tCustomer); 
//		int initperiod;//起始账期年月
//		int taxcurrentperiod;//当前账期年月
//		
//		for(TCustomer t:customerList){
//			String[] montharray=new String[]{" btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"};
//			initperiod=Integer.parseInt(t.getInitperiod());
//			if(!"".equals(t.getTaxcurrentperiod())&&null!=t.getTaxcurrentperiod()){
//			
//			taxcurrentperiod=Integer.parseInt(t.getTaxcurrentperiod());
//			if(initperiod-byYear<=12&&initperiod-byYear>=0){
//				for(int i=initperiod-byYear-1;i<12;i++){
//					if(i>=initperiod-byYear-1)
//						montharray[i]=" btn_sta02";
//				}
//			}
//
//			if (initperiod-byYear<0&&taxcurrentperiod-byYear>=0&&taxcurrentperiod-byYear<=12) {
//				for(int i=0;i<12;i++){
//					if(i<taxcurrentperiod-byYear-1){
//						montharray[i]=" btn_sta02";
//					}else if(i==taxcurrentperiod-byYear-1){
//						montharray[i]=" going";
//					}
//					else {
//						montharray[i]=" ";
//					}
//					
//				}
//			}
//			if (initperiod-byYear<0&&taxcurrentperiod-byYear>12) {
//				for(int i=0;i<12;i++){
//					montharray[i]=" btn_sta02";
//				}
//			}
//			if(taxcurrentperiod-byYear<=12&&taxcurrentperiod-byYear>=0){
//				for(int i=taxcurrentperiod-byYear-1;i<12;i++){
//					if(i==taxcurrentperiod-byYear-1)
//						montharray[i]=" going";
//					else 
//						montharray[i]="  ";
//				}
//			}
//
//			if (taxcurrentperiod-byYear<0) {
//				for(int i=0;i<12;i++){
//					montharray[i]="  ";
//				}
//			}
//			}else{
//				if(initperiod-byYear<=12&&initperiod-byYear>=0){
//					for(int i=initperiod-byYear-1;i<12;i++){
//						if(i>=initperiod-byYear-1)
//							montharray[i]=" ";
//					}
//				}
//				if(initperiod-byYear<0){
//					for(int i=0;i<12;i++){
//						montharray[i]=" ";
//					}
//				}
//				
//			}
//			t.setMontharray(montharray);
//			
//		}
//		page.setList(customerList);
//		model.addAttribute("page", page);
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
		tCustomer.setSupervisor(UserUtils.getUser().getId());
//		Page<TCustomer> page=tCustomerService.listTaxBySupervisor(new Page<TCustomer>(request, response),  tCustomer);
//		model.addAttribute("page", page);
		
		List<TCustomer> listTaxBySupervisor=tCustomerService.listTaxBySupervisor( tCustomer);
		model.addAttribute("listTaxBySupervisor", listTaxBySupervisor);
		
		model.addAttribute("tCustomer", tCustomer);
		
		TCustomer tc=tCustomerService.taxcount(tCustomer);
		model.addAttribute("tc", tc);
		model.addAttribute("tServeInfoinsert",new TServiceCharge());
		model.addAttribute("tServiceCharge",new TServiceCharge());
		return "modules/workterrace/reportTaxList";
	}
	
	@ResponseBody
	@RequestMapping(value = "taxcount")
	public TCustomer taxcount(String byYearMonth, Model model) {
		TCustomer tCustomer=new TCustomer();
		tCustomer.setSupervisor(UserUtils.getUser().getId());
		tCustomer.setByYearMonth(byYearMonth);
		return tCustomerService.taxcount(tCustomer);
	}
	
	//@RequiresPermissions("workterrace:reportTax:view")
	@RequestMapping(value = "form")
	public String form(TCustomer tCustomer, Model model) {
		model.addAttribute("chargeToAccount", tCustomer);
		return "modules/workterrace/reportTaxForm";
	}

	//@RequiresPermissions("workterrace:reportTax:edit")
	@RequestMapping(value = "save")
	public String save(TCustomer tCustomer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCustomer)){
			return form(tCustomer, model);
		}
		tCustomerService.save(tCustomer);
		addMessage(redirectAttributes, "保存报税成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/reportTax/?repage";
	}
	
	//@RequiresPermissions("workterrace:reportTax:edit")
	@RequestMapping(value = "delete")
	public String delete(TCustomer tCustomer, RedirectAttributes redirectAttributes) {
		tCustomerService.delete(tCustomer);
		addMessage(redirectAttributes, "删除报税成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/reportTax/?repage";
	}

}