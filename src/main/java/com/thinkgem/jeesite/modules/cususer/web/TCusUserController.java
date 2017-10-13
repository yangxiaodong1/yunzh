/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cususer.web;

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
import com.thinkgem.jeesite.modules.cususer.entity.TCusUser;
import com.thinkgem.jeesite.modules.cususer.service.TCusUserService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 转交Controller
 * @author cy
 * @version 2015-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/cususer/tCusUser")
public class TCusUserController extends BaseController {

	@Autowired
	private TCusUserService tCusUserService;
	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public TCusUser get(@RequestParam(required=false) String id) {
		TCusUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCusUserService.get(id);
		}
		if (entity == null){
			entity = new TCusUser();
		}
		return entity;
	}
	
	//@RequiresPermissions("cususer:tCusUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCusUser tCusUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TCusUser> page = tCusUserService.findPage(new Page<TCusUser>(request, response), tCusUser); 
		model.addAttribute("page", page);
		return "modules/cususer/tCusUserList";
	}

	//获取所有的用户信息
		public List<User> AllUsers(){
			List<User> userList=systemService.allUserList();
			return userList;
		}

	//根据用户ID来获取他所持有的客户信息
	public List<TCustomer> ListBySupervisor(String SupervisorId){
		 List<TCustomer> customerlist= tCustomerService.ListBySupervisor(SupervisorId);
		 for(int i=0;i<customerlist.size();i++){
			 System.out.println(customerlist.get(i).getCustomerName());
		 }
		 return customerlist;
	}
	
	@RequiresPermissions("cususer:tCusUser:view")
	//@RequiresPermissions("cususer:tCusUser:view"
	@RequestMapping(value = "form")
	public String form(TCusUser tCusUser, Model model) {
		
		model.addAttribute("user",UserUtils.getUser() );
		model.addAttribute("tCusUser", tCusUser);
		showCus(tCusUser,model);
		return "modules/sys/userInfo2";
		//return "modules/sys/userInfo2";
	}
	
	/**
	 * 显示所有客户转交凭证信息,cy
	 * **/
	public void showCus(TCusUser tCusUser,Model model){
		User user=UserUtils.getUser();
		List<User> listuser=AllUsers();
		List<TCustomer> customerlist=ListBySupervisor(user.getId());
		//用户信息
		model.addAttribute("listuser", listuser);
		//客户信息
		model.addAttribute("listcustomer", customerlist);
		Noaccept(tCusUser,model);
	}
	
	/**
	 * 添加转交记录
	 * **/
	//@RequiresPermissions("cususer:tCusUser:edit")
	@RequestMapping(value = "save")
	public @ResponseBody String save(TCusUser tCusUser, Model model, RedirectAttributes redirectAttributes) {
		String result="1";
		User user=UserUtils.getUser();
		if(tCusUser.getAcceptState()!=null){
			if("0".equals(tCusUser.getAcceptState()))
				tCusUser.setAcceptState("1");
		}
		else{
			//默认的转发记录为没有接受状态
			tCusUser.setAcceptState("0");
			if(tCusUser.getSysuerNameBe()==null){
				tCusUser.setSysuerNameBe(user.getName());
				tCusUser.setUserbeid(user.getId());
			}
			
			//通过客户外键、用户外键来获取 该 客户和用户外键
			TCustomer tCustomer=tCustomerService.get(tCusUser.getCustomerid());
			User system=systemService.findByid(tCusUser.getUserid());
			
			//给转交记录的企业名称、用户名称
			tCusUser.setCustomerName(tCustomer.getCustomerName());
			tCusUser.setSysuerName(system.getName());
		}
		
		//默认被转发人为当前登录用户
		if (!beanValidator(model, tCusUser)){
			return form(tCusUser, model);
		}
		try{
			tCusUserService.save(tCusUser);
		}catch(Exception e){
			result="0";
		}

		model.addAttribute("message", "转交成功");
		model.addAttribute("user", user);
		
		return result;
	}
	
	//@RequiresPermissions("cususer:tCusUser:edit")
	@RequestMapping(value = "delete")
	public String delete(TCusUser tCusUser, RedirectAttributes redirectAttributes) {
		tCusUserService.delete(tCusUser);
		addMessage(redirectAttributes, "删除转交成功");
		return "redirect:"+Global.getAdminPath()+"/cususer/tCusUser/?repage";
	}
	
	//根据接受状态、转交用户来获取所有数据
	public void findAccpetState(TCusUser tCusUser,Model model){
		tCusUser.setUserid(UserUtils.getUser().getId());
		//tCusUser.setAcceptState("0");
		
		List<TCusUser> tcususerList=tCusUserService.accpetSate(tCusUser);
		System.out.println("tcususerList length:"+tcususerList.size());
		model.addAttribute("tcususerList", tcususerList);
		//return "modules/cususer/tCusUserForm";
	}
	
	/**
	 * 根据接受状态、转交用户来获取所有数据(分组显示)
	 * **/
	public void  Noaccept(TCusUser tCusUser,Model model){
		tCusUser.setUserid(UserUtils.getUser().getId());
		tCusUser.setAcceptState("0");
		List<TCusUser> tcususerList=tCusUserService.Noaccept(tCusUser);
		System.out.println("tcususerList length:"+tcususerList.size());
		model.addAttribute("tcususerList", tcususerList);
	}
	/**
	 * 根据转凭证人、接受人、接受状态三个状态查询
	 * **/
	public List<TCusUser> accpetSate(TCusUser tCusUser){
		//tCusUser.setUserbeid(tCusUser.getUserbeid());
		tCusUser.setUserid(UserUtils.getUser().getId());
		tCusUser.setAcceptState("0");
		List<TCusUser> tcususer=tCusUserService.accpetSate(tCusUser);
		return tcususer;
	}
	
	/**
	 * 修改接收状态
	 * **/
	@RequestMapping(value = "updateAcceptState")
	@ResponseBody
	public String updateAcceptState(TCusUser tCusUser,Model model){
		
		String result="1";
		//先修改修改企业审核人（t_customer表），当点击接受的时候，企业的审核信息要修改。UserUtils.getUser().getId()当登录用户的id，
		for(TCusUser tu:accpetSate(tCusUser)){
			TCustomer tCustomer=new TCustomer();
			tCustomer.setSupervisor(UserUtils.getUser().getId());
			tCustomer.setId(tu.getCustomerid());
			tCustomerService.updateSupervisor(tCustomer);
					
		}
		
		//修改转凭证记录表中(根据)
		tCusUser.setAcceptState("1");
		tCusUser.setUserid(UserUtils.getUser().getId());
		try{
			tCusUserService.updateState(tCusUser);
		}catch(Exception ex)
		{
			result="0";
		}
		return result;
	}
}