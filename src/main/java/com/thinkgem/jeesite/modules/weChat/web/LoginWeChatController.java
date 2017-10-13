package com.thinkgem.jeesite.modules.weChat.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.weChat.entity.TWechatusers;
import com.thinkgem.jeesite.modules.weChat.entity.WTableInfo;
import com.thinkgem.jeesite.modules.weChat.service.TWechatusersService;



@Controller
@RequestMapping(value = "/weChat/WTableInfo")
public class LoginWeChatController extends BaseController {

	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private TWechatusersService tWechatusersService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private WTableInfoController wTableInfoController;
	/**
	 * 微信登陆
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "loginWeChat")
	public String loginWeChat( Model model,
			HttpSession session) {
		return "modules/weChat/loginInterface";
	}
	@RequestMapping(value = "indexWeChat")
	public Object  indexWeChat(TWechatusers tWechatusers,Model model,
			HttpSession session,HttpServletRequest request, HttpServletResponse response){
		String username = tWechatusers.getUserName();
		String password = tWechatusers.getPassword();
		String dbPassword = tWechatusersService.selectkWeChatUserPassword(username);
		if (StringUtils.isNotBlank(dbPassword)) {
			boolean flag = systemService.validatePassword(password,dbPassword);
			
			if (flag) {	//登陆成功 
				tWechatusers = tWechatusersService.selectWeChatUserInfo(username, dbPassword);
				TCustomer tCustomer = tCustomerService.get(tWechatusers.getFdbid());
				tCustomer.setOfficeCompanyName(tWechatusers.getOfficeCompanyName());
//				model.addAttribute("tCustomer", tCustomer);
				session.setAttribute("tCustomer", tCustomer);
				session.setAttribute("sessionWeChatCustomer", tCustomer);
//				return "modules/weChat/weChatIndex";		
				return new ModelAndView("redirect:/weChat/weChatIndex.jsp");
			} else {
				return new ModelAndView("redirect:/weChat/loginInterface.jsp");
				
			}	
		}else {//登陆错误 
			return new ModelAndView("redirect:/weChat/loginInterface.jsp");

		}
	}
	/*
	 *进入账簿
	 */
	@RequestMapping(value = "indexWeChatInfo")
	public Object indexWeChatInfo(Model model,
			HttpSession session){
		Object obj = session.getAttribute("sessionWeChatCustomer");
		if (obj == null) {//session 不存在
			return new ModelAndView("redirect:/weChat/loginInterface.jsp");
		} else {
			wTableInfoController.getAllAccountPeriod(model, session);
			return new ModelAndView("redirect:/weChat/capitalProfitList.jsp");
//			return "modules/weChat/capitalProfitList";
		}
	}

}
