/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.web.workstatistics;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TServeInfo;
import com.thinkgem.jeesite.modules.inspection.service.workstatistics.TServeInfoService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 服务收费报表Controller
 * @author xuxiaolong
 * @version 2015-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/workstatistics/tServeInfo")
public class TServeInfoController extends BaseController {

	@Autowired
	private TServeInfoService tServeInfoService;
	@Autowired
	private OfficeService officeService;
	@ModelAttribute
	public TServeInfo get(@RequestParam(required=false) String id) {
		TServeInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tServeInfoService.get(id);
		}
		if (entity == null){
			entity = new TServeInfo();
		}
		return entity;
	}
	
	//@RequiresPermissions("inspection:workstatistics:tServeInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(TServeInfo tServeInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		tServeInfo.setCompanyId(UserUtils.getUser().getCompany().getId());
//		Page<TServeInfo> page = null;
//		if(Integer.parseInt(tServeInfo.getByYear())<=Calendar.getInstance().get(Calendar.YEAR))
//		{
//			page =tServeInfoService.findPage(new Page<TServeInfo>(request, response), tServeInfo); 
//			List<TServeInfo> TServeInfolist=page.getList();
//			int TServeInfolistSize=TServeInfolist.size();
//			if(TServeInfolistSize>0&&TServeInfolistSize<9){
//				for(int i=page.getPageSize()-TServeInfolistSize;i>0;i--){
//					page.getList().add(new TServeInfo());
//				}
//			}
//		}
//		model.addAttribute("page", page);	
		List<TServeInfo> listTServeInfo = tServeInfoService.findList(tServeInfo);
		if(Integer.parseInt(tServeInfo.getByYear())>Calendar.getInstance().get(Calendar.YEAR)){
			 tServeInfo.setNotnull("1");
		}
		Office office=new Office();
		office.setpId(UserUtils.getUser().getCompany().getId());
	 	List<Office> listSysOffice=officeService.findByParentIds(office);
	 	
	 	float end=100;
		float listTServeInfoSize=listTServeInfo.size();
		if(listTServeInfoSize>9){
			float i=(float) Math.ceil(listTServeInfoSize/9);
			end= 100/i;
		}
		if(listTServeInfoSize>0&&listTServeInfoSize<9){
			for(int i=(int) (9-listTServeInfoSize);i>0;i--){
				listTServeInfo.add(new TServeInfo());
			}
		}
		model.addAttribute("end",end);
		model.addAttribute("findListMax",tServeInfoService.findListMax(tServeInfo));
		model.addAttribute("listSysOffice", listSysOffice);
		model.addAttribute("listTServeInfo", listTServeInfo);
		model.addAttribute("tServeInfo", tServeInfo);
		return "modules/inspection/workstatistics/tServeInfoList";
		
	}

	//@RequiresPermissions("inspection:workstatistics:tServeInfo:view")
	@RequestMapping(value = "form")
	public String form(TServeInfo tServeInfo, Model model) {
		model.addAttribute("tServeInfo", tServeInfo);
		return "modules/inspection/workstatistics/tServeInfoForm";
	}

	//@RequiresPermissions("inspection:workstatistics:tServeInfo:edit")
	@RequestMapping(value = "save")
	public String save(TServeInfo tServeInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tServeInfo)){
			return form(tServeInfo, model);
		}
		tServeInfoService.save(tServeInfo);
		addMessage(redirectAttributes, "保存收费报表成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tServeInfo/?repage";
	}
	
	//@RequiresPermissions("inspection:workstatistics:tServeInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(TServeInfo tServeInfo, RedirectAttributes redirectAttributes) {
		tServeInfoService.delete(tServeInfo);
		addMessage(redirectAttributes, "删除收费报表成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tServeInfo/?repage";
	}

}