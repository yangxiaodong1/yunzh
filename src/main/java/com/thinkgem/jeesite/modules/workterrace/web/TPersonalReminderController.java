/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.web;

import java.util.Arrays;
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
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.workterrace.entity.TPersonalReminder;
import com.thinkgem.jeesite.modules.workterrace.service.TPersonalReminderService;

/**
 * 个人提醒Controller
 * @author xuxiaolong
 * @version 2016-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/workterrace/tPersonalReminder")
public class TPersonalReminderController extends BaseController {

	@Autowired
	private TPersonalReminderService tPersonalReminderService;
	
	@ModelAttribute
	public TPersonalReminder get(@RequestParam(required=false) String id) {
		TPersonalReminder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tPersonalReminderService.get(id);
		}
		if (entity == null){
			entity = new TPersonalReminder();
		}
		return entity;
	}
	
	//@RequiresPermissions("workterrace:tPersonalReminder:view")
	@RequestMapping(value = {"list", ""})
	public String list(TPersonalReminder tPersonalReminder, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		tPersonalReminder.setUserId(UserUtils.getUser().getId());
		//Page<TPersonalReminder> page = tPersonalReminderService.findPage(new Page<TPersonalReminder>(request, response), tPersonalReminder);
		List<TPersonalReminder> findListReminderService = tPersonalReminderService.findList(tPersonalReminder);
		
		model.addAttribute("tPersonalReminder", tPersonalReminder);
		model.addAttribute("findListReminderService", findListReminderService);
		return "modules/workterrace/tPersonalReminderList";
	}

	//@RequiresPermissions("workterrace:tPersonalReminder:view")
	@ResponseBody
	@RequestMapping(value = "form")
	public TPersonalReminder form(TPersonalReminder tPersonalReminder) {
		
		return tPersonalReminderService.get(tPersonalReminder);
	}

	//@RequiresPermissions("workterrace:tPersonalReminder:edit")
	@RequestMapping(value = "save")
	public String save(TPersonalReminder tPersonalReminder, Model model, RedirectAttributes redirectAttributes) {

		if (tPersonalReminder.getIsNewRecord()){
			tPersonalReminder.setUserId(UserUtils.getUser().getId());
			tPersonalReminder.setState("0");
		}
		tPersonalReminderService.save(tPersonalReminder);
		addMessage(redirectAttributes, "保存个人提醒成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/tPersonalReminder/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "updatastate")
	public int updatastate(TPersonalReminder tPersonalReminder, Model model, RedirectAttributes redirectAttributes) {

		int ss=0;
		tPersonalReminder.setState("1");
		try {
			tPersonalReminderService.save(tPersonalReminder);
			ss=1;
		} catch (Exception e) {
			ss=0;
		}
		
		return ss;
	}
	@ResponseBody
	@RequestMapping(value = "updatastates")
	public Page<TPersonalReminder> updatastates(TPersonalReminder tPersonalReminder, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		tPersonalReminder.setState("1");
		tPersonalReminderService.save(tPersonalReminder);
		
		TPersonalReminder tPersonalReminder1=new TPersonalReminder();
		tPersonalReminder1.setUserId(UserUtils.getUser().getId());
		tPersonalReminder1.setState("0");
		return  tPersonalReminderService.findPage(new Page<TPersonalReminder>(1, 4), tPersonalReminder1);
	
	}

	
	
	//@RequiresPermissions("workterrace:tPersonalReminder:edit")
	@RequestMapping(value = "delete")
	public String delete(TPersonalReminder tPersonalReminder, RedirectAttributes redirectAttributes) {
		tPersonalReminderService.delete(tPersonalReminder);
		addMessage(redirectAttributes, "删除个人提醒成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/tPersonalReminder/?repage";
	}
	@ResponseBody
	@RequestMapping(value = "deletes")
	public int deletes(String ids) {
		return tPersonalReminderService.deletes(Arrays.asList(ids.split(",")));
	
	}

}