/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.comquestion.web;

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
import com.thinkgem.jeesite.modules.comquestion.entity.Tcomquest;
import com.thinkgem.jeesite.modules.comquestion.service.TcomquestService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 常见问题表Controller
 * @author yang
 * @version 2016-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/comquestion/tcomquest")
public class TcomquestController extends BaseController {

	@Autowired
	private TcomquestService tcomquestService;
	@Autowired
	private SystemService systemService;
	@ModelAttribute
	public Tcomquest get(@RequestParam(required=false) String id) {
		Tcomquest entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tcomquestService.get(id);
		}
		if (entity == null){
			entity = new Tcomquest();
		}
		return entity;
	}
	
	@RequiresPermissions("comquestion:tcomquest:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tcomquest tcomquest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Tcomquest> page = tcomquestService.findPage(new Page<Tcomquest>(request, response), tcomquest); 
		model.addAttribute("page", page);
		return "modules/comquestion/tcomquestList";
	}

	@RequiresPermissions("comquestion:tcomquest:view")
	@RequestMapping(value = "form")
	public String form(Tcomquest tcomquest, Model model) {
		model.addAttribute("tcomquest", tcomquest);
		return "modules/comquestion/tcomquestForm";
	}

	@RequiresPermissions("comquestion:tcomquest:edit")
	@RequestMapping(value = "save")
	public String save(Tcomquest tcomquest, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tcomquest)){
			return form(tcomquest, model);
		}
		tcomquestService.save(tcomquest);
		addMessage(redirectAttributes, "保存常见问题表成功");
		return "redirect:"+Global.getAdminPath()+"/comquestion/tcomquest/?repage";
	}
	
	@RequiresPermissions("comquestion:tcomquest:edit")
	@RequestMapping(value = "delete")
	public String delete(Tcomquest tcomquest, RedirectAttributes redirectAttributes) {
		tcomquestService.delete(tcomquest);
		addMessage(redirectAttributes, "删除常见问题表成功");
		return "redirect:"+Global.getAdminPath()+"/comquestion/tcomquest/?repage";
	}
	@RequestMapping(value = "oftenMeetQue")//常见问题列表显示
	public String oftenMeetQue(Tcomquest tcomquest, Model model,RedirectAttributes redirectAttributes) {
		List<Tcomquest> tcList=tcomquestService.findList(tcomquest);

		 // User user=new User();
		for (Tcomquest tc : tcList) {
			 Date gd=tc.getCreateDate();
	    	   Date ud=tc.getUpdateDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String createDa = sdf.format(gd); 
				String update = sdf.format(ud); 
				tc.setCreateDateString(createDa);
				tc.setUpdateDateString(update);
				/*User user=systemService.getUser(tc.getUpdateBy().getId());
				if(user.getName()==null||"".equals(user.getName())){
					
				}else{
					tc.setUpdateByStrinig(user.getName());
				}*/
				User user=systemService.getUser(tc.getUpdateBy().getId());
				if(user!=null){
					tc.setUpdateByStrinig(user.getName());
				}
		}
		//model.addAttribute("user", user);
		model.addAttribute("tcList", tcList);
		model.addAttribute("tcomquest", tcomquest);
		return "modules/adminback/contentManage_oftenMeetQue";
	}
	@RequestMapping(value = "oftenMeetaddym")//常见问题列表添加页面
	public String oftenMeetaddym(Tcomquest tcomquest, Model model,RedirectAttributes redirectAttributes) {
		model.addAttribute("tcomquest", tcomquest);
		return "modules/adminback/contentManage_oftenMeetQue_add";
	}
	@RequestMapping(value = "savecomQuest")
	public String savecomQuest(Tcomquest tcomquest, Model model, RedirectAttributes redirectAttributes) {
		
		tcomquestService.save(tcomquest);
		
		return "redirect:"+Global.getAdminPath()+"/comquestion/tcomquest/oftenMeetQue";
	}
	@RequestMapping(value = "editcomQuest")//编辑常见问题
	public String editcomQuest(Tcomquest tcomquest, Model model, RedirectAttributes redirectAttributes) {
		
		
		model.addAttribute("tcomquest", tcomquest);
		return "modules/adminback/contentManage_oftenMeetQue_edit";
	}
	@RequestMapping(value = "deletecomquest")
	public String deletecomquest(Tcomquest tcomquest, RedirectAttributes redirectAttributes) {
		tcomquestService.delete(tcomquest);
		addMessage(redirectAttributes, "删除常见问题表成功");
		return "redirect:"+Global.getAdminPath()+"/comquestion/tcomquest/oftenMeetQue";
	}
}