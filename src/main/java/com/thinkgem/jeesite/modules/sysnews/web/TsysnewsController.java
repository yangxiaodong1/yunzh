/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sysnews.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sysnews.entity.Tsysnews;
import com.thinkgem.jeesite.modules.sysnews.service.TsysnewsService;

/**
 * 系统消息表Controller
 * @author yang
 * @version 2016-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/sysnews/tsysnews")
public class TsysnewsController extends BaseController {

	@Autowired
	private TsysnewsService tsysnewsService;
	
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Tsysnews get(@RequestParam(required=false) String id) {
		Tsysnews entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tsysnewsService.get(id);
		}
		if (entity == null){
			entity = new Tsysnews();
		}
		return entity;
	}
	
	@RequiresPermissions("sysnews:tsysnews:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tsysnews tsysnews, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Tsysnews> page = tsysnewsService.findPage(new Page<Tsysnews>(request, response), tsysnews); 
		model.addAttribute("page", page);
		return "modules/sysnews/tsysnewsList";
	}

	@RequiresPermissions("sysnews:tsysnews:view")
	@RequestMapping(value = "form")
	public String form(Tsysnews tsysnews, Model model) {
		model.addAttribute("tsysnews", tsysnews);
		return "modules/sysnews/tsysnewsForm";
	}

	@RequiresPermissions("sysnews:tsysnews:edit")
	@RequestMapping(value = "save")
	public String save(Tsysnews tsysnews, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsysnews)){
			return form(tsysnews, model);
		}
		tsysnewsService.save(tsysnews);
		addMessage(redirectAttributes, "保存系统消息成功");
		return "redirect:"+Global.getAdminPath()+"/sysnews/tsysnews/?repage";
	}
	
	@RequiresPermissions("sysnews:tsysnews:edit")
	@RequestMapping(value = "delete")
	public String delete(Tsysnews tsysnews, RedirectAttributes redirectAttributes) {
		tsysnewsService.delete(tsysnews);
		addMessage(redirectAttributes, "删除系统消息成功");
		return "redirect:"+Global.getAdminPath()+"/sysnews/tsysnews/?repage";
	}
	/*
	@RequestMapping(value = "newsMenue")//列表
	public String newsMenue(Tsysnews tsysnews, Model model) {
		List<Tsysnews> tslist=tsysnewsService.findList(tsysnews);
		 // User user=new User();
		for (Tsysnews ts : tslist) {
			 //Date gd=ts.getCreateDate();
			Date gd=ts.getSendtime();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String createDa = sdf.format(gd); 
				//ts.setCreateString(createDa);
			     ts.setSendtimeString(createDa);
			     User user=systemService.getUser(ts.getUpdateBy().getId());
			     if(user!=null){
						ts.setUpdateByStrinig(user.getName());
			}
		}
		//model.addAttribute("user", user);
		model.addAttribute("tslist", tslist);
		model.addAttribute("tsysnews", tsysnews);
		 return "modules/adminback/contentManage_sysNews";
	}*/
	@RequestMapping(value = "newsMenue")//列表
	public String newsMenue(Tsysnews tsysnews, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Tsysnews> page = tsysnewsService.findPage(new Page<Tsysnews>(request, response), tsysnews); 
		
		//page.getList();
		/*
		for (Tsysnews ts :page.getList()) {
			    // System.out.println(ts.getUpdateBy().getName()+"用户的名字有吗哈哈哈哈？");
			     User user=systemService.getUser(ts.getUpdateBy().getId());
			     if(user!=null){
						ts.setUpdateByStrinig(user.getName());
						//System.out.println(user.getName()+"用户的名字有吗？");
			}
		}
		*/
		model.addAttribute("page", page);
		 return "modules/adminback/contentManage_sysNews";
	}
	@RequestMapping(value = "addsysNews")//添加新闻页
	public String addsysNews(Tsysnews tsysnews, Model model) {
		model.addAttribute("tsysnews", tsysnews);
		 return "modules/adminback/contentManage_sysNews_add";
	}
	@RequestMapping(value = "savesysNews")//保存新闻
	public String savesysNews(Tsysnews tsysnews, Model model, RedirectAttributes redirectAttributes) {
		System.out.println(tsysnews.getContent()+"得到了内容哈哈哈");
		//将字符串类型的时间转化为时间类型
		String sendTimeString=tsysnews.getSendtimeString();
		System.out.println(sendTimeString);
		System.out.println(tsysnews.getTitle()+"得到标题了");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date datee=(Date)format.parse(sendTimeString);
			tsysnews.setSendtime(datee);//将string类型转化为日期后赋给日期字段
			tsysnewsService.save(tsysnews);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:"+Global.getAdminPath()+"/sysnews/tsysnews/newsMenue";
	}
	@RequestMapping(value = "forms")//系统新闻编辑
	public String forms(Tsysnews tsysnews, Model model) {
		//System.out.println(tsysnews.getSendtimeString()+"得到时间了");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String s=format.format(tsysnews.getSendtime());
		tsysnews.setSendtimeString(s);
		model.addAttribute("tsysnews", tsysnews);
		return "modules/adminback/contentManage_sysNews_add";
	}
	@RequestMapping(value = "sendstatus")//修改发送的状态
	public String sendstatus(Tsysnews tsysnews, Model model) {
		
		tsysnews.setSendstatus("1");
		tsysnewsService.save(tsysnews);
		model.addAttribute("tsysnews", tsysnews);
		return "redirect:"+Global.getAdminPath()+"/sysnews/tsysnews/newsMenue";
	}
	@RequestMapping(value = "check")//查看信息
	public String check(Tsysnews tsysnews, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String s=format.format(tsysnews.getSendtime());
		tsysnews.setSendtimeString(s);
	System.out.println(tsysnews.getSendtime());
		model.addAttribute("tsysnews", tsysnews);
		return "modules/adminback/contentManage_sysNews_check";
	}
	
}