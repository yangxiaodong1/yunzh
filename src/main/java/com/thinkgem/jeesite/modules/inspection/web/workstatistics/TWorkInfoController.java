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
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TWorkInfo;
import com.thinkgem.jeesite.modules.inspection.service.workstatistics.TWorkInfoService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 工作量明细表Controller
 * @author xuxiaolong
 * @version 2015-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/workstatistics/tWorkInfo")
public class TWorkInfoController extends BaseController {

	@Autowired
	private TWorkInfoService tWorkInfoService;
	@Autowired
	private OfficeService officeService;
	@ModelAttribute
	public TWorkInfo get(@RequestParam(required=false) String id) {
		TWorkInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tWorkInfoService.get(id);
		}
		if (entity == null){
			entity = new TWorkInfo();
		}
		return entity;
	}
	
	//@RequiresPermissions("inspection:workstatistics:tWorkInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(TWorkInfo tWorkInfo, HttpServletRequest request, HttpServletResponse response, Model model) {

		Office office=new Office();
		//获取当前用户公司id
		office.setpId(UserUtils.getUser().getCompany().getId());
		//根据上一级是当前用户公司id查询公司下的部门
	 	List<Office> listSysOffice=officeService.findByParentIds(office);
		model.addAttribute("listSysOffice", listSysOffice);
		tWorkInfo.setCompanyId(UserUtils.getUser().getCompany().getId());
		List<TWorkInfo> tWorkInfoList = tWorkInfoService.findList(tWorkInfo); 
		model.addAttribute("tWorkInfo", tWorkInfo);
		model.addAttribute("tWorkInfoList", tWorkInfoList);
		return "modules/inspection/workstatistics/tWorkInfoList";
	}

	//@RequiresPermissions("inspection:workstatistics:tWorkInfo:view")
	@RequestMapping(value = "form")
	public String form(TWorkInfo tWorkInfo, Model model) {
		model.addAttribute("tWorkInfo", tWorkInfo);
		return "modules/inspection/workstatistics/tWorkInfoForm";
	}

	//@RequiresPermissions("inspection:workstatistics:tWorkInfo:edit")
	@RequestMapping(value = "save")
	public String save(TWorkInfo tWorkInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tWorkInfo)){
			return form(tWorkInfo, model);
		}
		tWorkInfoService.save(tWorkInfo);
		addMessage(redirectAttributes, "保存工作量明细成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tWorkInfo/?repage";
	}
	
	//@RequiresPermissions("inspection:workstatistics:tWorkInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(TWorkInfo tWorkInfo, RedirectAttributes redirectAttributes) {
		tWorkInfoService.delete(tWorkInfo);
		addMessage(redirectAttributes, "删除工作量明细成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tWorkInfo/?repage";
	}

}