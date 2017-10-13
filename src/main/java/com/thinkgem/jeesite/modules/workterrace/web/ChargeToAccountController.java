/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TServiceCharge;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.weChat.entity.TWechatusers;
import com.thinkgem.jeesite.modules.weChat.service.TWechatusersService;

/**
 * 记账Controller
 * @author xuxiaolong
 * @version 2015-12-21
 */
@Controller
@RequestMapping(value = "${adminPath}/workterrace/chargeToAccount")
public class ChargeToAccountController extends BaseController {

	@Autowired
	private TCustomerService tCustomerService;
	

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private TWechatusersService tWechatusersService;//微信
	
	
	
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
	//@RequiresPermissions("workterrace:chargeToAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCustomer tCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
//		int byYear=Integer.parseInt(tCustomer.getByYear()+"00");//条件年
		tCustomer.setSupervisor(UserUtils.getUser().getId());
		List<TCustomer> customerList= tCustomerService.ChargeToAccountBySupervisor(tCustomer); 
		String byYserString=tCustomer.getByYear();
		for(TCustomer t:customerList){
			String serviceexpirationdate=t.getServiceexpirationdate();
			try {
			if(serviceexpirationdate.substring(0,4).equals(byYserString)){
				int a = Integer.parseInt(serviceexpirationdate.substring(4,6));  
				switch (a) {  
				case 1: t.getWagesVo().setMontharray1("bg_sta4");  break;  
				case 2: t.getWagesVo().setMontharray2("bg_sta4");  break;  
				case 3: t.getWagesVo().setMontharray3("bg_sta4");  break;  
				case 4: t.getWagesVo().setMontharray4("bg_sta4");  break;  
				case 5: t.getWagesVo().setMontharray5("bg_sta4");  break;  
				case 6: t.getWagesVo().setMontharray6("bg_sta4");  break;  
				case 7: t.getWagesVo().setMontharray7("bg_sta4");  break;  
				case 8: t.getWagesVo().setMontharray8("bg_sta4");  break;  
				case 9: t.getWagesVo().setMontharray9("bg_sta4");  break;  
				case 10: t.getWagesVo().setMontharray10("bg_sta4");  break;  
				case 11: t.getWagesVo().setMontharray11("bg_sta4");  break;  
				case 12: t.getWagesVo().setMontharray12("bg_sta4");  break;  
				default: System.out.println("In default");  break; 
				}  
			}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
//		int initperiod;//起始账期年月
//		int currentperiod;//当前账期年月
//		int serviceexpirationdate;//结束账期年月
//		
//		for(TCustomer t:customerList){
//			String[] montharray=new String[]{" btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"," btn_sta01"};
//			initperiod=Integer.parseInt(t.getInitperiod());
//				
//				
//			if(!"".equals(t.getCurrentperiod())&&null!=t.getCurrentperiod()){
//				currentperiod=Integer.parseInt(t.getCurrentperiod());
//				if (initperiod-byYear<=12&&initperiod-byYear>=0) {
//					for(int i=initperiod-byYear-1;i<12;i++){
//						if(i>=initperiod-byYear-1){
//							montharray[i]=" btn_sta02";
//						}
//					}
//				}
//				if (initperiod-byYear<0&&currentperiod-byYear>=0&&currentperiod-byYear<=12) {
//					for(int i=0;i<12;i++){
//						if(i<currentperiod-byYear-1){
//							montharray[i]=" btn_sta02";
//						}else if(i==currentperiod-byYear-1){
//							montharray[i]=" going";
//						}
//						else {
//							montharray[i]=" ";
//						}
//					}
//				}
//				if (initperiod-byYear<0&&currentperiod-byYear>12) {
//					for(int i=0;i<12;i++){
//						montharray[i]=" btn_sta02";
//					}
//				}
//				if(currentperiod-byYear<=12&&currentperiod-byYear>=0){
//					for(int i=currentperiod-byYear-1;i<12;i++){
//						if(i==currentperiod-byYear-1)
//							montharray[i]=" going";
//						else 
//							montharray[i]="  ";
//					}
//				}			
//				if(!"".equals(t.getServiceexpirationdate())&&null!=t.getServiceexpirationdate()){
//					serviceexpirationdate=Integer.parseInt(t.getServiceexpirationdate());
//					if (currentperiod-byYear<0&&serviceexpirationdate-byYear>12) {
//						for(int i=0;i<12;i++){
//							montharray[i]="  ";
//						}
//					}
//				}
//				if (currentperiod-byYear<0) {
//					for(int i=0;i<12;i++){
//						montharray[i]="  ";
//					}
//				}
//				
//				if (initperiod-byYear<0) {
//					for(int i=0;i<12;i++){
//						montharray[i]="  ";
//					}
//				}
//				
//				
//			}
//			else {
//				if (initperiod-byYear<0) {
//					for(int i=0;i<12;i++){
//							montharray[i]=" ";
//					}
//				}
//				if (initperiod-byYear<=12&&initperiod-byYear>=0) {
//					for(int i=initperiod-byYear-1;i<12;i++){
//						if(i>=initperiod-byYear-1){
//							montharray[i]=" ";
//						}
//					}
//				}
//			}
//			if(!"".equals(t.getServiceexpirationdate())&&null!=t.getServiceexpirationdate()){
//				serviceexpirationdate=Integer.parseInt(t.getServiceexpirationdate());
//				
//			if(serviceexpirationdate-byYear<=12&&serviceexpirationdate-byYear>=0){
//				for(int i=serviceexpirationdate-byYear-1;i<12;i++){
//					if(i==serviceexpirationdate-byYear-1)
//						montharray[i]=" bg_sta4";
//				}
//			}
//			if (serviceexpirationdate-byYear<0) {
//				for(int i=0;i<12;i++){
//					montharray[i]="  ";
//				}
//			}
//		}
//						
//		
//			t.setMontharray(montharray);
//			
//		}
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

		//TCustomer tCustomerr=tCustomerService.get(customerid);
		
		model.addAttribute("username",currentUser.getName());
		model.addAttribute("userlist", userlist);
		model.addAttribute("userList",userlist);
		model.addAttribute("tCustomer", tCustomer);
//		page.setList(customerList);
//		model.addAttribute("page", page);
		model.addAttribute("customerList", customerList);
		TCustomer tc=tCustomerService.customercount(tCustomer);
		model.addAttribute("tc", tc);
		model.addAttribute("tServeInfoinsert",new TServiceCharge());
		model.addAttribute("tServiceCharge",new TServiceCharge());
		return "modules/workterrace/chargeToAccountList";
	}
	@ResponseBody
	@RequestMapping(value = "getCount")
	public TCustomer getCount(String byYearMonth, Model model) {
		TCustomer tCustomer=new TCustomer();
		tCustomer.setSupervisor(UserUtils.getUser().getId());
		tCustomer.setByYearMonth(byYearMonth);
		return tCustomerService.customercount(tCustomer);
	}
	
	//@RequiresPermissions("workterrace:chargeToAccount:view")
	@RequestMapping(value = "form")
	public String form(TCustomer tCustomer, Model model) {
		model.addAttribute("chargeToAccount", tCustomer);
		return "modules/workterrace/chargeToAccountForm";
	}

	//@RequiresPermissions("workterrace:chargeToAccount:edit")
	@RequestMapping(value = "save")
	public String save(TCustomer tCustomer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCustomer)){
			return form(tCustomer, model);
		}
		tCustomerService.save(tCustomer);
		addMessage(redirectAttributes, "保存记账成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/chargeToAccount/?repage";
	}
	
	//@RequiresPermissions("workterrace:chargeToAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(TCustomer tCustomer, RedirectAttributes redirectAttributes) {
		tCustomerService.delete(tCustomer);
		addMessage(redirectAttributes, "删除记账成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/chargeToAccount/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "fillcount")//填充企业账号
	public TWechatusers fillcount(String id) {
		String fdbid=id;
		TWechatusers wechet=tWechatusersService.selectByFdbid(fdbid);
		//System.out.println(wechet.getUserName()+"微信用户名");
		System.out.println(fdbid);
		return wechet;
	}
	@ResponseBody
	@RequestMapping(value = "updatepwd")//重置密码
	public String updatepwd(TWechatusers tWechatusers, Model model,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		
		try {
			String fdbidString=request.getParameter("fdbid");
			System.out.println("fdbid:"+fdbidString);
			String password=request.getParameter("password");
			System.out.println("password:"+password);
		    String username=request.getParameter("username");
		    System.out.println("username:"+username);
		    String dbPassword = tWechatusersService.selectkWeChatUserPassword(username);
			boolean flag = systemService.validatePassword(password,dbPassword);
			if(flag){//密码相同说明没用改变，不需要做更改操作
				
			}else{
				String newpasswordString=SystemService.entryptPassword(password);//新加密的密码插入到数据库
				tWechatusers.setPassword(newpasswordString);
				tWechatusers.setPlainTextPassword(password);
				tWechatusers.setFdbid(fdbidString);
				tWechatusersService.updatepwd(tWechatusers);
			}
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			return "0"; 
		}
		
	}

}