/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.web.setup;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构表Controller
 * @author xuxiaolong
 * @version 2015-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/setup/sysOffice")
public class SysOfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	
	//@RequiresPermissions("inspection:setup:sysOffice:view")
	@RequestMapping(value = {"list", ""})
	public String list(Office office, HttpServletRequest request,HttpServletResponse response, Model model) {
		office.setType("1");
		Page<Office> page = officeService.findByParentIds(new Page<Office>(request,response), office);
		
		model.addAttribute("page", page);
		return "modules/inspection/setup/sysOfficeList";
	}
	
	
	
	
	//@RequiresPermissions("inspection:setup:sysOffice:edit")
	@RequestMapping(value = "findCompanyAdmin")
	public String findCompanyAdmin(Office office, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		User user=new User();
		user.setCompany(office);
		List<User> listuser=systemService.findCompanyAdmin(user);
		model.addAttribute("listuser", listuser);
		office.setType("1");
		office.setGrade("1");
		Page<Office> page = officeService.findByParentIds(new Page<Office>(request,response), office);
		
		model.addAttribute("page", page);
		
		return "modules/inspection/setup/sysOfficeList";
	}
	//@RequiresPermissions("inspection:setup:sysOffice:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		
		model.addAttribute("office", office);
		return "modules/inspection/setup/sysOfficeForm";
	}

	//@RequiresPermissions("inspection:setup:sysOffice:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
		Office company =UserUtils.getUser().getCompany();
		if(!"".equals(company)&&null!=company){
			office.setParent(company);
			office.setParentIds(company.getParentIds()+office.getParentId()+",");
			office.setSort(20);
			Area area=new Area();
			area.setId("1");
			office.setArea(area);
			office.setType("2");
			office.setGrade("2");
		}
		if (!beanValidator(model, office)){
			return form(office, model);
		}
		
		officeService.save(office);
		
		addMessage(redirectAttributes, "保存机构表成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/setup/sysUser/?repage";
	}
	
	//@RequiresPermissions("inspection:setup:sysOffice:edit")
	@RequestMapping(value = "saveCompany")
	public String saveCompany(Office office, Model model, RedirectAttributes redirectAttributes) {
		Office company=new Office();
		company.setId("0");
		office.setParent(company);
		office.setParentIds("0,");
		office.setSort(20);
		Area area=new Area();
		area.setId("1");
		office.setArea(area);
		office.setType("1");
		office.setGrade("1");
		office.setCreateBy(UserUtils.getUser());
		office.setCreateDate(new Date());
		office.setUpdateBy(UserUtils.getUser());
		office.setUpdateDate(new Date());
		if (!beanValidator(model, office)){
			return form(office, model);
		}
		Boolean isInsert=false;
		if (office.getIsNewRecord()){
			isInsert=true;
		}
		System.out.println("          45456456464----"+office.getId());
		officeService.insertCompany(office);
		System.out.println("          45456456464----"+office.getId());
		
		if(isInsert){
		    User user=new User();
		    user.setNewPassword("666666");
		    user.setLoginName(office.getName()+"admin");
		    user.setName(office.getName()+"admin");
		    user.setCompany(office);
		    user.setOffice(office);
			// 如果新密码为空，则不更换密码
			if (StringUtils.isNotBlank(user.getNewPassword())) {
				user.setPassword(SystemService.entryptPassword(user
						.getNewPassword()));
			}
			String[] roleIdArray={"2","3","6"};
			// 角色数据有效性验证，过滤不在授权内的角色
			List<Role> roleList = Lists.newArrayList();
			List<String> roleIdList =  new ArrayList<String>();
			Collections.addAll(roleIdList, roleIdArray);
			for (Role r : systemService.findAllRole()) {
				if (roleIdList.contains(r.getId())) {
					roleList.add(r);
				}
			}
			user.setRoleList(roleList);
				// 保存用户信息
			systemService.saveUser(user);
		}
		
		
		
		
		
		addMessage(redirectAttributes, "保存机构表成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/setup/sysOffice/?repage";
	}
	/**
	 * 验证登录名是否有效
	 * 
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName != null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName != null
				&& systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	//@RequiresPermissions("inspection:setup:sysOffice:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		User user=new User();
		user.setOffice(office);
	 	List<User> listUsers=systemService.findListNew(user);
		if(listUsers.size()<1||listUsers==null){
			office.setpId(office.getId());
		 	List<Office> listOffices=officeService.findByParentIds(office);
		 	if(listOffices.size()<1||listOffices==null){
		 		officeService.deletes(office);
				addMessage(redirectAttributes, "删除机构表成功");
			}else {
				addMessage(redirectAttributes, "不能删除该机构，因为该机构下有其他机构存在！");
				}
		}else{
			addMessage(redirectAttributes, "不能删除该机构，因为该机构表下有职员存在！");
		}
		return "redirect:"+Global.getAdminPath()+"/inspection/setup/sys/?repage";
	}
	
}