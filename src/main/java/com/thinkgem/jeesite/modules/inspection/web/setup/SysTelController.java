/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.web.setup;

import java.util.List;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 
 * @author xuxialong
 * @version 2015-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/setup/sysTel")
public class SysTelController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request,HttpServletResponse response, Model model) {
		user.setCompany(UserUtils.getUser().getCompany());
		Office office=new Office();
		office.setpId(UserUtils.getUser().getCompany().getId());
	 	List<Office> sysOfficeList=officeService.findByParentIds(office);
	 
	 	User user1 = new User();
	 	user1.setCompany(UserUtils.getUser().getCompany());
	 	List<User> userList = systemService.findListNewLeave(user1);
	 	
	 	model.addAttribute("id",UserUtils.getUser().getCompany().getId());
	 	model.addAttribute("userList",userList);
	 	model.addAttribute("sysOfficeList", sysOfficeList);
	 	model.addAttribute("user", user);
		return "modules/inspection/setup/sysTelList";
	}
	
	@RequestMapping(value = "userlist")
	@ResponseBody
	public List<User> userlist(User user) {
		System.out.println(user.getOffice().getId());
		if(!user.getOffice().getId().equals(UserUtils.getUser().getCompany().getId())){
			user.setCompany(UserUtils.getUser().getCompany());
			List<User> userList = systemService.findListNew(user);
			return userList;
		}else{
			user.setCompany(UserUtils.getUser().getCompany());
			user.setOffice(null);
			List<User> userList = systemService.findListNew(user);
			return userList;
		}
	}
	@RequestMapping(value = "user")
	@ResponseBody
	public List<User> user(User user, Model model) {
		
		if(user==null){
			User user1 = new User();
		 	user1.setCompany(UserUtils.getUser().getCompany());
		 	List<User> userList = systemService.findListNewLeave(user1);
		 	return userList;
		}else{
			List<User> userList = systemService.findListNewLeave(user);
			return userList;
		}
		
		
	}
	/**
	 * 根据输入条件查询用户
	 * @param string
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("inspection:setup:sysUser:view")
//	@RequestMapping(value = "search")
//	@ResponseBody
//	public List<User> form(String search, Model model) {
//		System.out.println("search"+search);
//		String companyId = UserUtils.getUser().getCompany().getId();
//		List<User> list = systemService.findUserListBySearch(search,companyId);
//		int  i = 0;
//		List<User> list1 =  systemService.allUserList();
//		list1.clear();
//		list1.addAll(list);
//		for(User user : list){
//			Office office = officeService.get(user.getOfficeid());
//			if(office!=null){
//				user.setOffice(office);
//				user.setOfficeName(office.getName());
//				System.out.println(user.toString());
//				System.out.println("y"+i);
//			}
//			else{
//				System.out.println("n"+i);
//				list1.remove(i);
//				i--;
//			}
//			i++;
//		}
//		return list1;
//	}
	@RequestMapping(value = "search")
	@ResponseBody
	public List<User> search(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setCompany(UserUtils.getUser().getCompany());
		//Page<User> page = systemService.findListNew(new Page<User>(request,response), user);
		List<User> page=systemService.findListNew(user);
		return page;
	}
	
}