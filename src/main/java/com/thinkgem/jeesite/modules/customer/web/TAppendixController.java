/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.web;

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
import com.thinkgem.jeesite.modules.customer.entity.TAppendix;
import com.thinkgem.jeesite.modules.customer.service.TAppendixService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 附件添加Controller
 * @author cy
 * @version 2015-12-16
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/tAppendix")
public class TAppendixController extends BaseController {

	@Autowired
	private TAppendixService tAppendixService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public TAppendix get(@RequestParam(required=false) String id) {
		TAppendix entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tAppendixService.get(id);
		}
		if (entity == null){
			entity = new TAppendix();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:tAppendix:view")
	@RequestMapping(value = {"list", ""})
	public String list(TAppendix tAppendix, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TAppendix> page = tAppendixService.findPage(new Page<TAppendix>(request, response), tAppendix); 
		model.addAttribute("page", page);
		return "modules/customer/tAppendixList";
	}

	@RequiresPermissions("customer:tAppendix:view")
	@RequestMapping(value = "form")
	public String form(TAppendix tAppendix, Model model) {
		model.addAttribute("tAppendix", tAppendix);
		return "modules/customer/tAppendixForm";
	}

	@RequiresPermissions("customer:tAppendix:edit")
	@RequestMapping(value = "save")
	public String save(TAppendix tAppendix, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tAppendix)){
			return form(tAppendix, model);
		}
		tAppendixService.save(tAppendix);
		addMessage(redirectAttributes, "保存附件添加成功");
		return "redirect:"+Global.getAdminPath()+"/customer/tAppendix/?repage";
	}
	
	@RequiresPermissions("customer:tAppendix:edit")
	@RequestMapping(value = "delete")
	public String delete(TAppendix tAppendix, RedirectAttributes redirectAttributes) {
		tAppendixService.delete(tAppendix);
		addMessage(redirectAttributes, "删除附件添加成功");
		return "redirect:"+Global.getAdminPath()+"/customer/tAppendix/?repage";
	}
	
	
	@RequestMapping(value = "findByState")
	@ResponseBody
	public List<TAppendix> findByState(){
		List<TAppendix> listTAppendix=new ArrayList<TAppendix>();
		listTAppendix=tAppendixService.findByState("0");
		return listTAppendix;
	}
	
	@RequestMapping(value = "findBytcuid")
	@ResponseBody
	public List<TAppendix> findBytcuid(HttpServletRequest request, HttpServletResponse response){
		
		String cuid=request.getParameter("id");
		List<TAppendix> listTAppendix=tAppendixService.findBytcusid(cuid);
		for (TAppendix tAppendix : listTAppendix) {
			if(tAppendix!=null){
				if(tAppendix.getCreateBy()!=null){
					User user=systemService.getUser(tAppendix.getCreateBy().getId());
					if(user!=null){
						tAppendix.setUpPeople(user.getName());
					}
				}
			}
			
			
		}
		return listTAppendix;
	}
	

}