/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.web.setup;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.billinfo.service.TBillInfoService;
import com.thinkgem.jeesite.modules.billtype.service.TBillTypeService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.vouchertemplatetype.service.TVoucherTemplateTypeService;
import com.thinkgem.jeesite.modules.vouchertitle.service.TVoucherTitleService;

/**
 * 用户表Controller
 * @author xuxialong
 * @version 2015-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/setup/sysUser")
public class SysUserController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private TBillInfoService billInfoService;
	
	@Autowired
	private TBillTypeService billTypeService;
	
	@Autowired
	private TAccountService tAccountService;
	
	@Autowired
	private TVoucherTemplateTypeService tVoucherTemplateTypeService;

	@Autowired
	private TVoucherTitleService tVoucherTitleService;
	
	@Autowired
	private TBillInfoService tBillInfoService;
	@Autowired
	private TCustomerService tCustomerService;
	
	
	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}
	
	//@RequiresPermissions("inspection:setup:sysUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request,HttpServletResponse response, Model model) {
		user.setCompany(UserUtils.getUser().getCompany());
		Office office=new Office();
		office.setpId(UserUtils.getUser().getCompany().getId());
	 	List<Office> sysOfficeList=officeService.findByParentIds(office);
	 	model.addAttribute("sysOfficeList", sysOfficeList);
	 	model.addAttribute("user", user);
	 	model.addAttribute("office", office);
	 	List<Role> listRoles=systemService.findAllListByJZ();
	 	model.addAttribute("allRoles", listRoles);
		return "modules/inspection/setup/sysUserList";
	}
	
	@RequestMapping(value = "userlist")
	@ResponseBody
	public List<User> userlist(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setCompany(UserUtils.getUser().getCompany());
//		Page<User> page = systemService.findListNew(new Page<User>(request,response), user);
//		return page;
		return systemService.findListNew(user);
		
		
	}
	
	
	//@RequiresPermissions("inspection:setup:sysUser:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany() == null || user.getCompany().getId() == null) {
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice() == null || user.getOffice().getId() == null) {
			user.setOffice(UserUtils.getUser().getOffice());
		}
		Office office=new Office();
		office.setParentIds(UserUtils.getUser().getOffice().getParentIds());
	 	List<Office> listSysOffice=officeService.findByParentIds(office);
	 	model.addAttribute("listSysOffice", listSysOffice);
	 	model.addAttribute("user", user);
	 	model.addAttribute("office", office);
		model.addAttribute("allRoles", systemService.findAllListByJZ());
		return "modules/inspection/setup/sysUserList";
	}
	
	//@RequiresPermissions("inspection:setup:sysUser:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		//user.setCompany(new Office(request.getParameter("company.id")));
		//user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isBlank(user.getId())){
			user.setNewPassword("666666");
			user.setMasteraccount("0");
		}
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user
					.getNewPassword()));
		}
		user.setCompany(UserUtils.getUser().getCompany());
//		if (!beanValidator(model, user)) {
//			return form(user, model);
//		}
//		if ((null==user.getId()||"".equals(user.getId())) &&!"true".equals(checkLoginName(user.getOldLoginName(),user.getLoginName()))) {
//			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
//			return form(user, model);
//		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList =  new ArrayList<String>();
		Collections.addAll(roleIdList, user.getRoleIdArray());
		for (Role r : systemService.findAllListByJZ()) {
			if (roleIdList.contains(r.getId())) {
				roleList.add(r);
			}
		}
		Office office=new Office(user.getOfficeid());
		user.setOffice(office);
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
			UserUtils.clearCache();
			// UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/setup/sysUser/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "updateDel")
	public String updateDel(User user){
		String iString="0";
		try {
			user.setLoginFlag("1");
			systemService.saveUser(user);
			iString="1";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return iString;
	}
	
	//@RequiresPermissions("inspection:setup:sysUser:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		} else if (User.isAdmin(user.getId())) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		} else {
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:"+Global.getAdminPath()+"/inspection/setup/sysUser/?repage";
	}
	//@RequiresPermissions("inspection:setup:sysUser:edit")
	@RequestMapping(value = "deletes")
	public String deletes(String ids, RedirectAttributes redirectAttributes) {
		User user=new User();
		String [] idsArr= ids.split(",");
		for(String id:idsArr){
			user.setLoginFlag("0");
			user.setId(id);
			if (id.equals(UserUtils.getPrincipal().getId())) {
				addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
				return "redirect:"+Global.getAdminPath()+"/inspection/setup/sysUser/?repage";
			} else if (User.isAdmin(user.getId())) {
				addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
				return "redirect:"+Global.getAdminPath()+"/inspection/setup/sysUser/?repage";
			} else {
				systemService.deleteUser(user);
			}
		}	
		addMessage(redirectAttributes,"删除批量用户成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/setup/sysUser/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "countCustmerbySupervisor")
	public String countCustmerbySupervisor(String ids) {
		String i="0";
		String j="0";
		String h="0";
		TCustomer tCustomer=new TCustomer();
		String [] idsArr= ids.split(",");
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		
		
		for(String id:idsArr){
			tCustomer.setSupervisor(id);
			if(tCustomerService.countTCustomerBySupervisor(tCustomer)>0){
				if(!"1".equals(i)){
					sb2.append("这些职员拥有服务的客户，不可以被移除");
					sb2.append("<br/>");
				}
				sb2.append(systemService.getUser(id).getName());
				sb2.append(" ");
				i="1";
			}
			if (id.equals(UserUtils.getPrincipal().getId())) {
				sb1.append("<br/>");
				sb1.append(UserUtils.getUser().getName()+"职员为当前用户，不可以被移除");
				j="1";
				
			} 
			User user=systemService.getUser(id);
			if (user.getMasteraccount().equals("1")) {
				sb3.append("<br/>");
				sb3.append(user.getName()+"职员为本公司主帐号，不可以被移除");
				h="1";
			} 
		}
		if (  ("1".equals(i))||("1".equals(j))||("1".equals(h)) ) {
			sb.append(sb2);
			sb.append(sb1);
			sb.append(sb3);
			sb.append("<br/>");
			sb.append("请重新选择...");
			return sb.toString();
		}else {
			return i.toString();
		}
		
	}
	
	
	/**
	 * 验证登录名是否有效
	 * 
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	
	//@RequiresPermissions("sys:user:edit")
	@ResponseBody
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName != null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName != null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	@ResponseBody
	@RequestMapping(value = "validatorLoginName")
	public String validatorLoginName(String id,String loginName) {
		User user =new User();
		user.setId(id);
		user.setLoginName(loginName);
		String validatorLoginName="true";
		if(systemService.validatorLoginName(user)>0)
			validatorLoginName="false";
		return validatorLoginName;
	}
	
	@RequestMapping(value = "getUserById", method = RequestMethod.POST)
	public void getUserById(PrintWriter printWriter, HttpServletRequest request) {
		String userid=request.getParameter("id");
		String jsonString="";
		JsonMapper json=new JsonMapper();
		if(userid!=null && !"".equals(userid)){
			User user=new User();
			user.setId(userid);
			List<User> info=systemService.findListNew(user);	
			if(info!=null && info.size()>0){
				jsonString= json.toJsonString(info.get(0));
			}
		}
		printWriter.write(jsonString);  
		printWriter.flush();  
		printWriter.close();
	}
	
	//@RequiresPermissions("inspection:setup:sysOffice:edit")
	@RequestMapping(value = "deleteOffice")
	public String deleteOffice(Office office, RedirectAttributes redirectAttributes) {
		User user=new User();
		user.setOffice(office);
	 	List<User> listUsers=systemService.findListNew(user);
		if(listUsers.size()<1||listUsers==null){
			office.setpId(office.getId());
		 	List<Office> listOffices=officeService.findByParentId(office);
		 	if(listOffices.size()<1||listOffices==null){
		 		officeService.deletes(office);
				addMessage(redirectAttributes, "删除机构表成功");
			}else {
				addMessage(redirectAttributes, "不能删除该机构，因为该机构下有其他机构存在！");
				}
		}else{
			addMessage(redirectAttributes, "不能删除该机构，因为该机构表下有职员存在！");
		}
		return "redirect:"+Global.getAdminPath()+"/inspection/setup/sysUser/?repage";
	}
	//@RequiresPermissions("inspection:setup:sysOffice:edit")
	@RequestMapping(value = "saveOffice")
	public String saveOffice(Office office, Model model, RedirectAttributes redirectAttributes) {
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
			return formOffice(office, model);
		}
		
		officeService.save(office);
		addMessage(redirectAttributes, "保存机构表成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/setup/sysUser/?repage";
	}
	//@RequiresPermissions("inspection:setup:sysOffice:view")
	@RequestMapping(value = "formOffice")
	public String formOffice(Office office, Model model) {
		
		model.addAttribute("office", office);
		return "modules/inspection/setup/sysUserList";
	}
	@ResponseBody
	@RequestMapping(value = "resetPassWord")
	public String resetPassWord(User user) {
		String mes="";
		try {
			user.setPassword(SystemService.entryptPassword("666666"));
			systemService.saveUser(user);
			mes="重置用户密码成功!";
		} catch (Exception e) {
			mes="";
		}
		return mes;
	}

}