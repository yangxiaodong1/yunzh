/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.workterrace.entity.FollowUp;
import com.thinkgem.jeesite.modules.workterrace.service.FollowUpService;

/**
 * 跟进表Controller
 * @author xuxiaolong
 * @version 2016-01-13
 */
@Controller
@RequestMapping(value = "${adminPath}/workterrace/followUp")
public class FollowUpController extends BaseController {

	@Autowired
	private FollowUpService followUpService;
	
	@ModelAttribute
	public FollowUp get(@RequestParam(required=false) String id) {
		FollowUp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = followUpService.get(id);
		}
		if (entity == null){
			entity = new FollowUp();
		}
		return entity;
	}
	
	//@RequiresPermissions("workterrace:followUp:view")
	@RequestMapping(value = {"list", ""})
	public String list(FollowUp followUp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FollowUp> page = followUpService.findPage(new Page<FollowUp>(request, response), followUp); 
		model.addAttribute("page", page);
		
		
	
		return "modules/workterrace/followUpList";
	}
	@ResponseBody
	@RequestMapping(value = {"followUplist", ""})
	public Page<FollowUp> followUplist(FollowUp followUp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FollowUp> page = followUpService.findPage(new Page<FollowUp>(request, response), followUp); 
		return page;
	}
	@ResponseBody
	@RequestMapping(value = "findUpContent")
	public String findUpContent(FollowUp followUp) {
		String  upContent=followUpService.findUpContent(followUp);
		return upContent;
	}
	
	//@RequiresPermissions("workterrace:followUp:view")
	@RequestMapping(value = "form")
	@ResponseBody
	public FollowUp form(FollowUp followUp) {
		
		return followUpService.get(followUp);
	}

	//@RequiresPermissions("workterrace:followUp:edit")
	@ResponseBody
	@RequestMapping(value = "save")
	public Page<FollowUp> save(FollowUp followUp, HttpServletRequest request, HttpServletResponse response, Model model) {
		
	Page<FollowUp> page=null;
	if (followUp.getIsNewRecord()){
		followUp.setUpMan(UserUtils.getUser().getId());
	}
	try {
		followUpService.save(followUp);
		 page = followUpService.findPage(new Page<FollowUp>(request, response), followUp); 
	
	} catch (Exception e) {
		// TODO: handle exception
	}
		return page;
	}
	
	//@RequiresPermissions("workterrace:followUp:edit")
	@ResponseBody
	@RequestMapping(value = "delect")
	public Page<FollowUp> delect(FollowUp followUp, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<FollowUp> page=null;
		
		try {
			followUpService.delete(followUp);
			page = followUpService.findPage(new Page<FollowUp>(request, response), followUp); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return page;
	}

}