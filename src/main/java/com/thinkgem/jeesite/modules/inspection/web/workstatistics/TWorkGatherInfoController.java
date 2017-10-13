/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.web.workstatistics;


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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TWorkGatherInfo;
import com.thinkgem.jeesite.modules.inspection.service.workstatistics.TWorkGatherInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 工作量汇总明细Controller
 * @author xuxiaolong
 * @version 2015-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/workstatistics/tWorkGatherInfo")
public class TWorkGatherInfoController extends BaseController {

	@Autowired
	private TWorkGatherInfoService tWorkGatherInfoService;
	
	@ModelAttribute
	public TWorkGatherInfo get(@RequestParam(required=false) String id) {
		TWorkGatherInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tWorkGatherInfoService.get(id);
		}
		if (entity == null){
			entity = new TWorkGatherInfo();
		}
		return entity;
	}
	
	//@RequiresPermissions("inspection:workstatistics:tWorkGatherInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(TWorkGatherInfo tWorkGatherInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		tWorkGatherInfo.setCompanyId(UserUtils.getUser().getCompany().getId());
//		Page<TWorkGatherInfo> page = tWorkGatherInfoService.findPage(new Page<TWorkGatherInfo>(request, response), tWorkGatherInfo); 
//		model.addAttribute("page", page);
		List<TWorkGatherInfo> tWorkGatherInfoList = tWorkGatherInfoService.findList(tWorkGatherInfo); 
		model.addAttribute("tWorkGatherInfoList", tWorkGatherInfoList);
		model.addAttribute("tWorkGatherInfo", tWorkGatherInfo);
		return "modules/inspection/workstatistics/tWorkGatherInfoList";
	}
	//@RequiresPermissions("inspection:workstatistics:tWorkGatherInfo:view")
	@RequestMapping(value = {"listByYera", ""})
	public String listByYera(String ByYera, HttpServletRequest request, HttpServletResponse response, Model model) {
		TWorkGatherInfo tWorkGatherInfo=new TWorkGatherInfo();
		tWorkGatherInfo.setByYear(ByYera);
		Page<TWorkGatherInfo> page = tWorkGatherInfoService.findPage(new Page<TWorkGatherInfo>(request, response), tWorkGatherInfo); 
		model.addAttribute("tWorkGatherInfo", tWorkGatherInfo);
		model.addAttribute("page", page);
		return "modules/inspection/workstatistics/tWorkGatherInfoList";
	}

	//@RequiresPermissions("inspection:workstatistics:tWorkGatherInfo:view")
	@RequestMapping(value = "form")
	public String form(TWorkGatherInfo tWorkGatherInfo, Model model) {
		model.addAttribute("tWorkGatherInfo", tWorkGatherInfo);
		return "modules/inspection/workstatistics/tWorkGatherInfoForm";
	}

	//@RequiresPermissions("inspection:workstatistics:tWorkGatherInfo:edit")
	@RequestMapping(value = "save")
	public String save(TWorkGatherInfo tWorkGatherInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tWorkGatherInfo)){
			return form(tWorkGatherInfo, model);
		}
		tWorkGatherInfoService.save(tWorkGatherInfo);
		addMessage(redirectAttributes, "保存工作量汇总明细成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tWorkGatherInfo/?repage";
	}
	
	//@RequiresPermissions("inspection:workstatistics:tWorkGatherInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(TWorkGatherInfo tWorkGatherInfo, RedirectAttributes redirectAttributes) {
		tWorkGatherInfoService.delete(tWorkGatherInfo);
		addMessage(redirectAttributes, "删除工作量汇总明细成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tWorkGatherInfo/?repage";
	}

}