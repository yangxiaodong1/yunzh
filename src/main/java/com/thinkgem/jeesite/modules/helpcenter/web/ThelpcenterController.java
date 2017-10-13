/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.helpcenter.web;

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
import com.thinkgem.jeesite.modules.helpcenter.entity.Thelpcenter;
import com.thinkgem.jeesite.modules.helpcenter.service.ThelpcenterService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 帮助中心Controller
 * @author yang
 * @version 2016-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/helpcenter/thelpcenter")
public class ThelpcenterController extends BaseController {

	@Autowired
	private ThelpcenterService thelpcenterService;
	@Autowired
	private SystemService systemService;
	@ModelAttribute
	public Thelpcenter get(@RequestParam(required=false) String id) {
		Thelpcenter entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = thelpcenterService.get(id);
		}
		if (entity == null){
			entity = new Thelpcenter();
		}
		return entity;
	}
	
	@RequiresPermissions("helpcenter:thelpcenter:view")
	@RequestMapping(value = {"list", ""})
	public String list(Thelpcenter thelpcenter, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Thelpcenter> page = thelpcenterService.findPage(new Page<Thelpcenter>(request, response), thelpcenter); 
		model.addAttribute("page", page);
		return "modules/helpcenter/thelpcenterList";
	}

	@RequiresPermissions("helpcenter:thelpcenter:view")
	@RequestMapping(value = "form")
	public String form(Thelpcenter thelpcenter, Model model) {
		model.addAttribute("thelpcenter", thelpcenter);
		return "modules/helpcenter/thelpcenterForm";
	}

	@RequiresPermissions("helpcenter:thelpcenter:edit")
	@RequestMapping(value = "save")
	public String save(Thelpcenter thelpcenter, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, thelpcenter)){
			return form(thelpcenter, model);
		}
		thelpcenterService.save(thelpcenter);
		addMessage(redirectAttributes, "保存帮助中心成功");
		return "redirect:"+Global.getAdminPath()+"/helpcenter/thelpcenter/?repage";
	}
	
	@RequiresPermissions("helpcenter:thelpcenter:edit")
	@RequestMapping(value = "delete")
	public String delete(Thelpcenter thelpcenter, RedirectAttributes redirectAttributes) {
		thelpcenterService.delete(thelpcenter);
		addMessage(redirectAttributes, "删除帮助中心成功");
		return "redirect:"+Global.getAdminPath()+"/helpcenter/thelpcenter/?repage";
	}
	
	@RequestMapping(value = {"helpMenue"})//帮助中心列表
	public String helpMenue(Thelpcenter thelpcenter, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Thelpcenter> thList=thelpcenterService.findList(thelpcenter);
		//User user=new User();
		for (Thelpcenter th : thList) {
			Date gd=th.getCreateDate();
	    	   Date ud=th.getUpdateDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String createDa = sdf.format(gd); 
				String update = sdf.format(ud); 
				th.setCreateDateString(createDa);
				th.setUpdateDateString(update);
				System.out.println(th.getUpdateBy().getId()+"vbvbvb");
				User user=systemService.getUser(th.getUpdateBy().getId());
				
				if(user!=null){
					th.setUpdateByStrinig(user.getName());
				}
		}
		//model.addAttribute("user", user);
		model.addAttribute("thList", thList);
		model.addAttribute("thelpcenter", thelpcenter);
		 return "modules/adminback/contentManage_helpCenter";
	}
	@RequestMapping(value = {"addHelp"})
	public String addHelp(Thelpcenter thelpcenter, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		model.addAttribute("thelpcenter", thelpcenter);
		 return "modules/adminback/contentManage_helpCenter_add";
	}
	@RequestMapping(value = {"addHelp2"})//编辑
	public String addHelp2(Thelpcenter thelpcenter, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		model.addAttribute("thelpcenter", thelpcenter);
		 return "modules/adminback/contentManage_helpCenter_edit";
	}
	@RequestMapping(value = "saveHelp")//保存帮助中心内容
	public String saveHelp(Thelpcenter thelpcenter, Model model, RedirectAttributes redirectAttributes) {
		
		thelpcenterService.save(thelpcenter);
		addMessage(redirectAttributes, "保存帮助中心成功");
		return "redirect:"+Global.getAdminPath()+"/helpcenter/thelpcenter/helpMenue";
	}
	@RequestMapping(value = "deleteHelp")
	public String deleteHelp(Thelpcenter thelpcenter, RedirectAttributes redirectAttributes) {
		thelpcenterService.delete(thelpcenter);
		addMessage(redirectAttributes, "删除帮助中心成功");
		return "redirect:"+Global.getAdminPath()+"/helpcenter/thelpcenter/helpMenue";
	}

}