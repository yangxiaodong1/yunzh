/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

//import java.util.Calendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.newcharge.entity.TChargecompany;
import com.thinkgem.jeesite.modules.power.entity.TYdSysMenu;
import com.thinkgem.jeesite.modules.power.service.TYdSysMenuService;
import com.thinkgem.jeesite.modules.quotation.entity.Tquotation;
import com.thinkgem.jeesite.modules.quotation.service.TquotationService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sysnews.entity.Tsysnews;
import com.thinkgem.jeesite.modules.sysnews.service.TsysnewsService;
import com.thinkgem.jeesite.modules.workterrace.entity.TPersonalReminder;
import com.thinkgem.jeesite.modules.workterrace.entity.WagesVo;
import com.thinkgem.jeesite.modules.workterrace.service.TPersonalReminderService;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private TPersonalReminderService tPersonalReminderService;
	//chenming加的
	@Autowired
	private SystemService systemService;
	@Autowired
	private TquotationService tquotationService;
	@Autowired
	private TYdSysMenuService tYdSysMenuService;
	@Autowired
	private TsysnewsService tsysnewsService;
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}
		
		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath;
		}
//		String view;
//		view = "/WEB-INF/views/modules/sys/sysLogin.jsp";
//		view = "classpath:";
//		view += "jar:file:/D:/GitHub/jeesite/src/main/webapp/WEB-INF/lib/jeesite.jar!";
//		view += "/"+getClass().getName().replaceAll("\\.", "/").replace(getClass().getSimpleName(), "")+"view/sysLogin";
//		view += ".jsp";
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		// 如果是手机登录，则返回JSON字符串
		if (mobile){
	        return renderString(response, model);
		}
		
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response,Model model,
			HttpSession session) {
		Principal principal = UserUtils.getPrincipal();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		// 如果是手机登录，则返回JSON字符串
		/*if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}*/
		
		User currentUser = UserUtils.getUser();
		
		
		
		
		//如果当前用户是微信
//		if("99".equals(currentUser.getUserType())){
//			String fdbid = currentUser.getCompany().getId();
//			return loginWeChatController.indexWeChat(model, session, fdbid);
//			
//		}
		System.out.println(currentUser.getName());
		//List<Role> userRole = loginUser.getRoleList();
		System.out.println("userRole"+currentUser.getRoleNames()+"BBB");
		//currentUser.getRoleNames().equals("记账管理员角色")
		if (currentUser.getRoleNames().contains("芸豆管理员")){
		//if (currentUser.getRoleNames().equals("芸豆管理员")){
			//if (currentUser.getRoleNames().contains("公司管理员123")){
			//if (currentUser.getName().equalsIgnoreCase("记账员")){
				return "redirect:"+Global.getAdminPath()+"/adminback";
				
			}
		// 如果是记账管理员登录，则返回审验管理员界面
		if (currentUser.getRoleNames().contains("记账公司管理员")){
		//if (currentUser.getRoleNames().equals("记账公司管理员")){
		//if (currentUser.getRoleNames().contains("系统管理员")){
		//if (currentUser.getName().equalsIgnoreCase("系统管理员")){
			return "redirect:"+Global.getAdminPath()+"/yunzhmainsy";
			
		}
		if (currentUser.getRoleNames().contains("记账员")){
		// 如果是记账员登录，则返回记账员界面
		//if (currentUser.getRoleNames().equals("记账员")){
		//if (currentUser.getRoleNames().contains("公司管理员123")){
		//if (currentUser.getName().equalsIgnoreCase("记账员")){
			return "redirect:"+Global.getAdminPath()+"/yunzhmainsy";
		}
		// 如果是记账员登录，则返回记账员界面
		
		//return "modules/sys/yunzhsy";//修改换头,成客户需要的样式
		//return "redirect:"+Global.getAdminPath()+"/yunzhmainsy";
		return "modules/sys/sysIndex";//原始有头的
		
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	
	/**
	 * 云智慧工作台首页子窗口
	 */
	@RequestMapping(value = "${adminPath}/yunzhmain")
	public String getYunzhMain( TCustomer tCustomer,HttpServletRequest request, HttpServletResponse response,Model model){
		User currentUser = UserUtils.getUser();//获取当前用户
		Calendar ca=Calendar.getInstance();
		int h=ca.get(Calendar.HOUR_OF_DAY);
		//根据时间的问候语
		String message="";
		if(h>=3&&h<6){
			message="凌晨好！";
		}else if(h>=6&&h<8){
			message="早上好！";
		}else if(h>=8&&h<11){
			message="上午好！";
		}else if(h>=11&&h<13){
			message="中午好！";
		}else if(h>=13&&h<17){
			message="下午好！";
		}else if(h>=17&&h<19){
			message="傍晚好！";
		}else if(h>=19&&h<23){
			message="晚上好！";
		}else if(h>=23&&h<3){
			message="请早点休息！";
		}
		
		//今日语录
		Random r=new Random();
		int i=r.nextInt(7)+1;
		String[] arr={"当困难来临时，用微笑去面对，用智慧去解决",
				"学会快乐，因为只有开心度过每一天，活得才精彩",
				"规划的根本目的是为了更快、更有效地达成目标",
				"不要惧怕学习，知识是没有重量的，你永远可以轻易的带着它与你同行",
				"欲穷千里目，更上一层楼",
				"纸上得来终觉浅，绝知此事要躬行",
				"言忠信，行笃敬",
				"言忠信，行笃敬"
		    };
		String dayRead=arr[i];//随机取出语录中的值
		
		//List<TCustomer> count = tCustomerService.ListBySupervisor(currentUser.getId());
		   // int countTcustomer =count.size();//获取集合中对象的个数
		    
		System.out.println();
		String rolesName=currentUser.getRoleNames();
		List<User> userlist=new ArrayList<User>();
		User userr;
		if(rolesName.contains("记账公司管理员")){
			 userlist=systemService.findUserByCompanyid(currentUser);
		}else if(rolesName.equals("记账员")){
			userlist.add(currentUser);
		}
		
		
		//获取本公司的记账人
		
		//个人提醒
		TPersonalReminder tPersonalReminder=new TPersonalReminder();
		tPersonalReminder.setState("0");
		tPersonalReminder.setUserId(UserUtils.getUser().getId());
		Page<TPersonalReminder> page=new Page<TPersonalReminder>(1,4);
		Page<TPersonalReminder> pageTPersonalReminder = tPersonalReminderService.findPage(page, tPersonalReminder); 
		//消息
		Page<Tsysnews> tsysnewspage=new Page<Tsysnews>(1,4);
		Page<Tsysnews> pageTsysnews = tsysnewsService.findListByUserId(tsysnewspage, new Tsysnews()); 
		
		
		//统计个数
		TCustomer tc=new TCustomer();
		tc.setSupervisor(UserUtils.getUser().getId());
		WagesVo vo=tCustomerService.homePageCount(tc);
		
		//随机从数据库中获取语录已经启用的语录，通过是否启用查询
		Tquotation t=new Tquotation();
		List<Tquotation> tqList=tquotationService.findByStartStatus(t);
		String title=null;
		int index=tqList.size();
		if(index==0){
			title="当困难来临时，用微笑去面对，用智慧去解决";
		}else{
			Random rr=new Random();
			int ii=rr.nextInt(index);
			System.out.println(ii+"得到ii");
			title=tqList.get(ii).getContent();
		}
		model.addAttribute("username",currentUser.getName());
		model.addAttribute("title",title);
		model.addAttribute("vo",vo);
		model.addAttribute("pageTPersonalReminder",pageTPersonalReminder);
		model.addAttribute("pageTsysnews",pageTsysnews);
		model.addAttribute("userlist",userlist);
		//model.addAttribute("countTcustomer",countTcustomer);
		model.addAttribute("currentUser",currentUser);
		model.addAttribute("message",message);
		model.addAttribute("dayRead",dayRead);
		//用于页面可以显示弹出框的
		model.addAttribute("tCustomer", tCustomer);
		return "modules/sys/yunzh";
	}	
	/**
	 * 云智慧工作台首页父窗口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "${adminPath}/yunzhmainsy")
	public String getYunzhMainsy(HttpServletRequest request, HttpServletResponse response,Model model){
		User currentUser = UserUtils.getUser();//获取当前用户
		 model.addAttribute("currentUser",currentUser);
       //return "modules/sys/sysIndex";
		 return "modules/sys/yunzhsy";
	}	
	@RequestMapping(value = "${adminPath}/yunzhmainsypt")//用于解决返回到工作平台的问题
	public String yunzhmainsypt(HttpServletRequest request, HttpServletResponse response,Model model){
		User currentUser = UserUtils.getUser();//获取当前用户
		String i="1";
		model.addAttribute("i",i);
		 model.addAttribute("currentUser",currentUser);
       //return "modules/sys/sysIndex";
		 return "modules/sys/yunzhsy";
	}	
	//后台管理父页面
	@RequestMapping(value = "${adminPath}/adminback")
	public String adminback(HttpServletRequest request, HttpServletResponse response,Model model){
		User currentUser = UserUtils.getUser();//获取当前用户
		List<TYdSysMenu> ty=tYdSysMenuService.findPower(new TYdSysMenu(currentUser));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ty.size(); i++) {
			if(i==ty.size()-1){
				sb.append(ty.get(i).getName());
			}else if(i<ty.size()-1){
				sb.append(ty.get(i).getName()).append(",");
			}
		}
		System.out.println(sb.toString()+"你你你你你你你你你");
		model.addAttribute("powerNames",sb.toString());
		 model.addAttribute("currentUser",currentUser);
		
		return "modules/adminback/frame";
	}
	
	//后台父页面，首页
		@RequestMapping(value = "${adminPath}/index")
		public String index(HttpServletRequest request, HttpServletResponse response,Model model){
			 return "modules/adminback/index";
		}
		
	//后台父页面，客户管理，加盟申请
	@RequestMapping(value = "${adminPath}/clientManage_joinAppl")
	public String clientManage_joinAppl(HttpServletRequest request, HttpServletResponse response,Model model){
		 return "modules/adminback/clientManage_joinAppl";
	}
	//后台父页面，客户管理，客户列表
	@RequestMapping(value = "${adminPath}/clientManage_menue")
	public String clientManage_menue(HttpServletRequest request, HttpServletResponse response,Model model){
		 return "modules/adminback/clientManage_menue";
	}
	//后台父页面，客户管理，客户列表停止
		@RequestMapping(value = "${adminPath}/clientManage_menue_stopServicemenue")
		public String clientManage_menue_stopServicemenue(HttpServletRequest request, HttpServletResponse response,Model model){
			 return "modules/adminback/clientManage_menue_stopServicemenue";
		}
		//后台父页面，客户管理，客户列表，添加客户
		@RequestMapping(value = "${adminPath}/clientManage_menue_add")
		public String clientManage_menue_add(TChargecompany tChargecompany,HttpServletRequest request, HttpServletResponse response,Model model){
			model.addAttribute("tChargecompany", tChargecompany);
			System.out.println("yxd杨晓东dddddddd");
			return "modules/adminback/clientManage_menue_add";
		}
	//后台父页面，内容管理，帮助中心
	@RequestMapping(value = "${adminPath}/contentManage_helpCenter")
	public String contentManage_helpCenter(HttpServletRequest request, HttpServletResponse response,Model model){
		 return "modules/adminback/contentManage_helpCenter";
	}
	//后台父页面，内容管理，帮助中心，编辑
		@RequestMapping(value = "${adminPath}/contentManage_helpCenter_edit")
		public String contentManage_helpCenter_edit(HttpServletRequest request, HttpServletResponse response,Model model){
			 return "modules/adminback/contentManage_helpCenter_edit";
		}
		//后台父页面，内容管理，帮助中心，常见问题
	@RequestMapping(value = "${adminPath}/contentManage_oftenMeetQue")
	public String contentManage_oftenMeetQue(HttpServletRequest request, HttpServletResponse response,Model model){
		 return "modules/adminback/contentManage_oftenMeetQue";
	}
	//后台父页面，内容管理，帮助中心，常见问题，编辑
		@RequestMapping(value = "${adminPath}/contentManage_oftenMeetQue_edit")
		public String contentManage_oftenMeetQue_edit(HttpServletRequest request, HttpServletResponse response,Model model){
			 return "modules/adminback/contentManage_oftenMeetQue_edit";
		}
		//后台父页面，内容管理，帮助中心，系统消息
		@RequestMapping(value = "${adminPath}/contentManage_sysNews")
		public String contentManage_sysNews(HttpServletRequest request, HttpServletResponse response,Model model){
			 return "modules/adminback/contentManage_sysNews";
		}
		//后台父页面，内容管理，帮助中心，系统消息，系统消息添加
		@RequestMapping(value = "${adminPath}/contentManage_sysNews_add")
		public String contentManage_sysNews_add(HttpServletRequest request, HttpServletResponse response,Model model){
			 return "modules/adminback/contentManage_sysNews_add";
		}
		//后台父页面，内容管理，帮助中心，语录管理
		@RequestMapping(value = "${adminPath}/contentManage_quotesManage")
		public String contentManage_quotesManage(HttpServletRequest request, HttpServletResponse response,Model model){
			 return "modules/adminback/contentManage_quotesManage";
		}
		
		//后台父页面，用户管理
		@RequestMapping(value = "${adminPath}/usermanage")
		public String usermanage(HttpServletRequest request, HttpServletResponse response,Model model){
			 return "modules/adminback/usermanage";
		}
}
