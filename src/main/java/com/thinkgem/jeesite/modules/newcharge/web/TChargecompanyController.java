/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.newcharge.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.clientfile.entity.TClientFile;
import com.thinkgem.jeesite.modules.clientfile.service.TClientFileService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.feedback.entity.TFeedback;
import com.thinkgem.jeesite.modules.feedback.entity.TFeedbackFile;
import com.thinkgem.jeesite.modules.feedback.service.TFeedbackFileService;
import com.thinkgem.jeesite.modules.feedback.service.TFeedbackService;
import com.thinkgem.jeesite.modules.newcharge.entity.TChargecompany;
import com.thinkgem.jeesite.modules.newcharge.entity.TCountCompany;
import com.thinkgem.jeesite.modules.newcharge.entity.TCountCompanynew;
import com.thinkgem.jeesite.modules.newcharge.entity.Tjoinappl;
import com.thinkgem.jeesite.modules.newcharge.service.TChargecompanyService;
import com.thinkgem.jeesite.modules.newcharge.service.TjoinapplService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 记账公司Controller
 * @author yang
 * @version 2016-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/newcharge/tChargecompany")
public class TChargecompanyController extends BaseController {

	@Autowired
	private TChargecompanyService tChargecompanyService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;//用户的service
	@Autowired
	private TjoinapplService tjoinapplService;
	@Autowired
	private TFeedbackService tFeedbackService;//意见反馈
	
	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private TFeedbackFileService tFeedbackFileService;
	
	@Autowired
	private TClientFileService tClientFileService;
	@ModelAttribute
	public TChargecompany get(@RequestParam(required=false) String id) {
		TChargecompany entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tChargecompanyService.get(id);
		}
		if (entity == null){
			entity = new TChargecompany();
		}
		return entity;
	}
	
	@RequiresPermissions("newcharge:tChargecompany:view")
	@RequestMapping(value = {"list", ""})
	public String list(TChargecompany tChargecompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TChargecompany> page = tChargecompanyService.findPage(new Page<TChargecompany>(request, response), tChargecompany); 
		model.addAttribute("page", page);
		return "modules/newcharge/tChargecompanyList";
	}

	@RequiresPermissions("newcharge:tChargecompany:view")
	@RequestMapping(value = "form")
	public String form(TChargecompany tChargecompany, Model model) {
		model.addAttribute("tChargecompany", tChargecompany);
		return "modules/newcharge/tChargecompanyForm";
	}

	@RequiresPermissions("newcharge:tChargecompany:edit")
	@RequestMapping(value = "save")
	public String save(TChargecompany tChargecompany, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tChargecompany)){
			return form(tChargecompany, model);
		}
		tChargecompanyService.save(tChargecompany);
		addMessage(redirectAttributes, "保存记账公司成功");
		return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/?repage";
	}
	
	@RequiresPermissions("newcharge:tChargecompany:edit")
	@RequestMapping(value = "delete")
	public String delete(TChargecompany tChargecompany, RedirectAttributes redirectAttributes) {
		tChargecompanyService.delete(tChargecompany);
		addMessage(redirectAttributes, "删除记账公司成功");
		return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/?repage";
	}
	
	/**
	 * 保存记账公司，管理员等
	 * @param tChargecompany
	 * @param request
	 * @param response
	 * @return
	 */
	
	
	@RequestMapping(value = "update1")
	@ResponseBody
	public String update1(TChargecompany tChargecompany,HttpServletRequest request,Office office, HttpServletResponse response) throws IllegalStateException, IOException {
		String result="";
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			office.setParentId("0");
			office.setParentIds("0,");
			office.setName(tChargecompany.getChargecomname());
			office.setSort(20);
			Area area=new Area();
			area.setId("1");
			office.setArea(area);
			office.setType("1");
			office.setGrade("1");
			office.setUseable("1");
			office.setIsNewRecord(false);
			tChargecompany.setServicestatus("1");
			
			if(tChargecompany.getCity2()==null||"".equals(tChargecompany.getCity2())){
				tChargecompany.setCity(tChargecompany.getCity1());
			}else if(tChargecompany.getCity3()==null||"".equals(tChargecompany.getCity3())){
				tChargecompany.setCity(tChargecompany.getCity1()+"/"+tChargecompany.getCity2());
			}else{
				tChargecompany.setCity(tChargecompany.getCity1()+"/"+tChargecompany.getCity2()+"/"+tChargecompany.getCity3());
			}
			//office.setThargecompany(tChargecompany);
			office.setChargecomname(tChargecompany);
			office.setCity(tChargecompany);
			String ci=tChargecompany.getCity1();
			office.setProvince(ci);
			if (ci.equals("北京市")||ci.equals("天津市")||ci.equals("重庆市")||ci.equals("上海市")||ci.equals("台湾")||ci.equals("香港特别行政区")||ci.equals("澳门特别行政区")) {
				office.setMunicipality(ci);
				office.setDistrict(tChargecompany.getCity2());
			}else {
				office.setMunicipality(tChargecompany.getCity2());
				office.setDistrict(tChargecompany.getCity3());
			}
			
			//office.setMasteraccount("1");
			
			office.setContectperson(tChargecompany);
			office.setMobilenumber(tChargecompany);
			office.setCompanynumber(tChargecompany);
			office.setCompanyrunrange(tChargecompany);
			office.setServicestatus(tChargecompany);
			office.setUsestatus(tChargecompany);
			office.setLegalname(tChargecompany);
			office.setContectphone(tChargecompany);
			office.setAppendixtype(tChargecompany);
			office.setCardnumber(tChargecompany);
			office.setRunnumber(tChargecompany);
			office.setRunappendix(tChargecompany);
			office.setTaxrenum(tChargecompany);
			office.setTaxappendx(tChargecompany);
			office.setOrgancode(tChargecompany);
			office.setCodeappendx(tChargecompany);
			office.setAbutment(tChargecompany);
			office.setAuditbill(tChargecompany);
			office.setRapauditnum(tChargecompany);
			
			//String autoidString=officeService.getautoid();
			//System.out.println(officeService.getautoid()+"得到自增的id");
			officeService.insertChargeCompany(office);
			//System.out.println(office.getId()+"完全得到id");
			Office office2=officeService.get(office.getId());
			
			//tChargecompanyService.save(tChargecompany);//保存记账公司
			//System.out.println(tChargecompany.getCity()+"城市");
		   
			//System.out.println("------------------------");
			//下面做添加客户的信息
			User user=new User();
			String depName=request.getParameter("depNameed");
			depName = new String(depName.getBytes("ISO8859-1"),"UTF-8");
			System.out.println(depName+"我就不信了");
			String pwd=request.getParameter("pwd");
			pwd = new String(pwd.getBytes("ISO8859-1"),"UTF-8");
			//System.out.println(pwd+"这就是密码？");
			if(depName.length()!=0&&pwd.length()!=0){//如果没有添加用户信息则不进行插入，有用户信息才进行添加
				System.out.println("这个判断是正确的吗？？？？？？？？？？？");
			user.setLoginName(depName);
			//user.setPassword(pwd);
			user.setNewPassword(pwd);//用新密码
			user.setName(depName);
			user.setOffice(office2);
			user.setCompany(office2);
			//if这句话用于加密的
			if (StringUtils.isNotBlank(user.getNewPassword())) {
				user.setPassword(SystemService.entryptPassword(user
						.getNewPassword()));
			}
			//System.out.println("---------------记账公司管理员角色问题已经解决");
			String[] roleIdArray={"94b5b44a17144149bf52f02159137e6c"};
			// 角色数据有效性验证，过滤不在授权内的角色
			List<Role> roleList = Lists.newArrayList();
			List<String> roleIdList =  new ArrayList<String>();
			Collections.addAll(roleIdList, roleIdArray);//所有指定元素添加到指定集合
			/*for (Role r : systemService.findAllRole()) {
				//if (roleIdList.contains(r.getId())) {
					roleList.add(r);
				//}
			}*/
			//Role role=systemService.getRole(roleIdArray[0]);
			Role role=new Role(roleIdArray[0]);
			roleList.add(role);
			user.setRoleList(roleList);
				// 保存用户信息
			//System.out.println("---------------");
			user.setMasteraccount("1");
			systemService.saveUser(user);//调用许小龙的方法
			}
			//System.out.println(user.getId()+"得到添加用户的id");
			result="1";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			result="0";
			e.printStackTrace();
		}
		
		return result;
	}
	@RequestMapping(value = "update2")//修改记账公司
	@ResponseBody
	public String update2(TChargecompany tChargecompany,User user,HttpServletRequest request,Office office, HttpServletResponse response) throws IllegalStateException, IOException {
		String result="";
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			//Office office=new Office();
			String iddd=tChargecompany.getId();
			System.out.println(iddd);
				office.setId(iddd);
			office.setParentId("0");
			office.setParentIds("0,");
			office.setName(tChargecompany.getChargecomname());
			office.setSort(20);
			Area area=new Area();
			area.setId("1");
			office.setArea(area);
			office.setType("1");
			office.setGrade("1");
			office.setUseable("1");
			office.setIsNewRecord(true);
			tChargecompany.setServicestatus("1");
			if(tChargecompany.getCity2()==null||"".equals(tChargecompany.getCity2())){
				tChargecompany.setCity(tChargecompany.getCity1());
			}else if(tChargecompany.getCity3()==null||"".equals(tChargecompany.getCity3())){
				tChargecompany.setCity(tChargecompany.getCity1()+"/"+tChargecompany.getCity2());
			}else{
				tChargecompany.setCity(tChargecompany.getCity1()+"/"+tChargecompany.getCity2()+"/"+tChargecompany.getCity3());
			}
			//office.setThargecompany(tChargecompany);
			office.setChargecomname(tChargecompany);
			office.setCity(tChargecompany);
			String ci=tChargecompany.getCity1();
			office.setProvince(ci);
			if (ci.equals("北京市")||ci.equals("天津市")||ci.equals("重庆市")||ci.equals("上海市")||ci.equals("台湾")||ci.equals("香港特别行政区")||ci.equals("澳门特别行政区")) {
				office.setMunicipality(ci);
				office.setDistrict(tChargecompany.getCity2());
			}else {
				office.setMunicipality(tChargecompany.getCity2());
				office.setDistrict(tChargecompany.getCity3());
			}
			office.setContectperson(tChargecompany);
			office.setMobilenumber(tChargecompany);
			office.setCompanynumber(tChargecompany);
			office.setCompanyrunrange(tChargecompany);
			office.setServicestatus(tChargecompany);
			office.setUsestatus(tChargecompany);
			office.setLegalname(tChargecompany);
			office.setContectphone(tChargecompany);
			office.setAppendixtype(tChargecompany);
			office.setCardnumber(tChargecompany);
			office.setRunnumber(tChargecompany);
			office.setRunappendix(tChargecompany);
			office.setTaxrenum(tChargecompany);
			office.setTaxappendx(tChargecompany);
			office.setOrgancode(tChargecompany);
			office.setCodeappendx(tChargecompany);
			office.setAbutment(tChargecompany);
			office.setAuditbill(tChargecompany);
			office.setRapauditnum(tChargecompany);
			officeService.updatenew(office);
			
			user.setCompany(office);
			 user=systemService.findByidandaccount(user);
			String pwd=request.getParameter("pwd");
			pwd = new String(pwd.getBytes("ISO8859-1"),"UTF-8");
			if(pwd!=""){
			user.setNewPassword(pwd);//用新密码
			if (StringUtils.isNotBlank(user.getNewPassword())) {
				user.setPassword(SystemService.entryptPassword(user
						.getNewPassword()));
			}
			
			/*String[] roleIdArray={"94b5b44a17144149bf52f02159137e6c"};
			// 角色数据有效性验证，过滤不在授权内的角色
			List<Role> roleList = Lists.newArrayList();
			List<String> roleIdList =  new ArrayList<String>();
			Collections.addAll(roleIdList, roleIdArray);
			Role role=new Role(roleIdArray[0]);
			roleList.add(role);
			user.setRoleList(roleList);
			systemService.saveUser(user);*/
			systemService.updatePasswordByIdnew(user);
			}
			result="1";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			result="0";
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "menue")
	public String menue(TChargecompany tChargecompany,User user, Model model, Office office,RedirectAttributes redirectAttributes) {
		//List<TChargecompany> tcList=tChargecompanyService.findList(tChargecompany);
		List<Office> tcList=tChargecompanyService.selectByStatus(office);
		/*User currentUser = UserUtils.getUser();
		office.setCreateBy(currentUser);
		List<Office> tcList=tChargecompanyService.selectByStatus2(office);*/
		
		
		Set<String> citySetlist=new HashSet<String>();
		for (Office o : tcList) {
			//System.out.println(o.getChargecomname().getChargecomname()+"列表得到了名字对象");
			user.setCompany(o);
			List<User> userList=systemService.findsoncount(user);
			//List<User> userList=systemService.findUserByOfficeId(o.getId());
			List<User> userListcomp=systemService.findcompcount(user);
			int countCompany=0;
			for (User u : userListcomp) {//用于获取企业数
				List<TCustomer> tccList=tCustomerService.ListBySupervisor(u.getId());
				int counttc=tccList.size();
				countCompany=countCompany+counttc;
			}
			o.setCountCompany(countCompany);//用于显示企业数的
			//int count=userList.size()-1;
			int count=userList.size();
			o.setSonCount(count);//用于存放这个公司下面的子账号
			String city=o.getProvince();
			String citytrue="";
			if(city!=null){
			//cl.province=='北京市'||cl.province=='天津市'||cl.province=='重庆市'||cl.province=='上海市'||cl.province=='台湾'||cl.province=='香港特别行政区'||cl.province=='澳门特别行政区'}"
			if(city.equals("北京市")||city.equals("天津市")||city.equals("重庆市")||city.equals("上海市")||city.equals("台湾")||city.equals("香港特别行政区")||city.equals("澳门特别行政区")){
				citytrue=city;
			}else {
				citytrue=o.getMunicipality();
			}
			if(!"".equals(citytrue)){
				citySetlist.add(citytrue);
			}
			
			}
		}
		//System.out.println(tcList.size()+"我就这么多");
		
		//System.out.println("-----以下用于显示城市");
		//显示对接人
		model.addAttribute("citySetlist", citySetlist);
		model.addAttribute("tcList", tcList);
		
		//return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/?repage";
		return "modules/adminback/clientManage_menue";
	}
	@RequestMapping(value = "serviceStatus")//编辑状态
	public String serviceStatus(TChargecompany tChargecompany,Office office, User user,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		String delog=request.getParameter("delog");
		
		 office=tChargecompanyService.getbyid(id);
		 
		 user.setCompany(office);
		// user.setDelFlag(delog);
		// user.setLoginFlag(delog);
		//List<User> userlistList=systemService.findsoncount(user);
		 List<User> userlistList=systemService.findcompcount(user);
		for (User u : userlistList) {
			u.setLoginFlag(delog);
			systemService.updateuserStatus(u);//更新所有的登陆状态
		}
		
		UserUtils.clearCache();
		// systemService.updateStatus(user);
		 //System.out.println(office.getParentId()+"父节点id");
		String status=request.getParameter("serviceStatus");
		//tChargecompany.setId(id);
		tChargecompany.setServicestatus(status);
		office.setParentId("0");
		office.setServicestatus(tChargecompany);
		officeService.updatenew(office);
		//tChargecompanyService.save(tChargecompany);
		return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/menue";
	}
	@RequestMapping(value = "serviceStatusT")//恢复服务
	public String serviceStatusT(TChargecompany tChargecompany,User user, Model model,Office office, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		String delog=request.getParameter("delog");
		 office=tChargecompanyService.getbyid(id);
		 user.setCompany(office);
		// List<User> userlistList=systemService.findsoncount(user);
		 List<User> userlistList=systemService.findcompcount(user);//包含了主账号
			for (User u : userlistList) {
				u.setLoginFlag(delog);
				systemService.updateuserStatus(u);//更新所有的登陆状态
			}
		 UserUtils.clearCache();
		// systemService.updateStatus(user);
		String status=request.getParameter("serviceStatus");
		tChargecompany.setServicestatus(status);
		//tChargecompany.setId(id);
		office.setParentId("0");
		office.setServicestatus(tChargecompany);
		officeService.updatenew(office);
		
		return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/stopservemenue";
	}
	@RequestMapping(value = "addym")//添加页面
	public String addym(TChargecompany tChargecompany, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		User currentUser = UserUtils.getUser();//获取当前用户
		//List<User> userList=systemService.findByOfficeId("1");//修改了获取总部下人的方法
		//List<User> userList=systemService.findAll();
		List<User> userList=systemService.finddj();
		String[] s={"0","1","2","3","4","5","6","7","8","9","10"};
		List<String> numList=Arrays.asList(s);
		model.addAttribute("numList", numList);
		model.addAttribute("username",currentUser.getName());
		model.addAttribute("userList", userList);
		model.addAttribute("tChargecompany", tChargecompany);
		return "modules/adminback/clientManage_menue_add";
	}
	@RequestMapping(value = "edit")//编辑
	public String edit(TChargecompany tChargecompany, Office office,Model model,User user, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		//TChargecompany tC=tChargecompanyService.get(id);
		 //int idd=Integer.parseInt(id);
		//office.setId(idd);
		Office o=tChargecompanyService.getbyid(id);
		user.setCompany(o);
		user=systemService.findByidandaccount(user);
		String loginname="";
		if(user!=null){
			loginname=user.getLoginName();
		}
		//List<User> userList=systemService.findByOfficeId("1");
		 // List<User> userList=systemService.findAll();
		  List<User> userList=systemService.finddj();
		String city=o.getCity().getCity();//String city1="";
		String city2="";String city3="";String city1="";
		city1=o.getProvince();city2=o.getMunicipality();city3=o.getDistrict();
		if(city1.equals(city2)){
			city2=city3;
		}
		/*
		String city1=city.substring(0, city.indexOf("/")>-1?city.indexOf("/"):city.length());
		
		if((city.indexOf("/")>-1)&&(city.lastIndexOf("/")==city.indexOf("/"))){
			//city2=city.substring(city.indexOf("/")>-1?(city.indexOf("/")+1):city.length(), city.lastIndexOf("/")>-1?city.lastIndexOf("/"):city.length());
			city2=city.substring(city.indexOf("/")>-1?(city.indexOf("/")+1):city.length());
		}
		if(city.lastIndexOf("/")!=city.indexOf("/")){
			city2=city.substring(city.indexOf("/")+1, city.lastIndexOf("/"));
			city3=city.substring(city.lastIndexOf("/")+1);
		}*/
		System.out.println(city2+"城市2");
		String[] s={"0","1","2","3","4","5","6","7","8","9","10"};
		List<String> numList=Arrays.asList(s);
		//获取对应图片的名字
		String appnameString="";String runnameString="";String taxnameString="";String codnameString="";
		if(o.getAppendixtype().getAppendixtype()!=null&&o.getAppendixtype().getAppendixtype()!=""){
			TClientFile fA=tClientFileService.get(o.getAppendixtype().getAppendixtype());
			if(fA!=null){
				appnameString=fA.getFileName();
			}
			
		}
		if(o.getRunappendix().getRunappendix()!=null&&o.getRunappendix().getRunappendix()!=""){
			TClientFile fR=tClientFileService.get(o.getRunappendix().getRunappendix());
			if(fR!=null){
				runnameString=fR.getFileName();
			}
			 
		}
		if(o.getTaxappendx().getTaxappendx()!=null&&o.getTaxappendx().getTaxappendx()!=""){
			TClientFile fT=tClientFileService.get(o.getTaxappendx().getTaxappendx());
			if(fT!=null){
				taxnameString=fT.getFileName();
			}
			
		}
		if(o.getCodeappendx().getCodeappendx()!=null&&o.getCodeappendx().getCodeappendx()!=""){
			TClientFile fC=tClientFileService.get(o.getCodeappendx().getCodeappendx());
			if(fC!=null){
				codnameString=fC.getFileName();
			}
			
		}
		 model.addAttribute("appnameString",appnameString);
		 model.addAttribute("runnameString",runnameString);
		 model.addAttribute("taxnameString",taxnameString);
		 model.addAttribute("codnameString",codnameString);
      model.addAttribute("rnum",o.getRapauditnum().getRapauditnum());
      model.addAttribute("numList", numList);
		model.addAttribute("username",o.getAbutment().getAbutment());
		model.addAttribute("userList", userList);
		model.addAttribute("city1", city1);
		model.addAttribute("city2", city2);
		model.addAttribute("city3", city3);
		System.out.println(id+"scsdc");
		//System.out.println(tChargecompany.getId()+"获得到了id吗哈哈哈");
		model.addAttribute("loginname", loginname);//登录名
		model.addAttribute("o", o);
		tChargecompany.setId(id);
		officetchargecompany( o,tChargecompany);
		model.addAttribute("tChargecompany", tChargecompany);
		return "modules/adminback/clientManage_menue_edit";
	}
	public void officetchargecompany(Office o,TChargecompany tChargecompany){
		if (o.getChargecomname()!=null) {
			tChargecompany.setChargecomname(o.getChargecomname().getChargecomname());
		}
		if (o.getContectperson()!=null) {
			tChargecompany.setContectperson(o.getContectperson().getContectperson());
		}
		if (o.getMobilenumber()!=null) {
			tChargecompany.setMobilenumber(o.getMobilenumber().getMobilenumber());
		}
		if (o.getCompanynumber()!=null) {
			tChargecompany.setCompanynumber(o.getCompanynumber().getCompanynumber());
		}
		if (o.getCompanyrunrange()!=null) {
			tChargecompany.setCompanyrunrange(o.getCompanyrunrange().getCompanyrunrange());
		}
		if (o.getServicestatus()!=null) {
			tChargecompany.setServicestatus(o.getServicestatus().getServicestatus());
		}
		if (o.getUsestatus()!=null) {
			tChargecompany.setUsestatus(o.getUsestatus().getUsestatus());
		}
		if (o.getLegalname()!=null) {
			tChargecompany.setLegalname(o.getLegalname().getLegalname());
		}
		if (o.getContectphone()!=null) {
			tChargecompany.setContectphone(o.getContectphone().getContectphone());
		}
		if (o.getAppendixtype()!=null) {
			tChargecompany.setAppendixtype(o.getAppendixtype().getAppendixtype());
		}
		if (o.getRunnumber()!=null) {
			tChargecompany.setRunnumber(o.getRunnumber().getRunnumber());
		}
		if (o.getCardnumber()!=null) {
			tChargecompany.setCardnumber(o.getCardnumber().getCardnumber());
		}
		if (o.getRunappendix()!=null) {
			tChargecompany.setRunappendix(o.getRunappendix().getRunappendix());
		}
		if (o.getTaxappendx()!=null) {
			tChargecompany.setTaxappendx(o.getTaxappendx().getTaxappendx());
		}
		if (o.getTaxrenum()!=null) {
			tChargecompany.setTaxrenum(o.getTaxrenum().getTaxrenum());
		}
		if (o.getOrgancode()!=null) {
			tChargecompany.setOrgancode(o.getOrgancode().getOrgancode());
		}
		if (o.getCodeappendx()!=null) {
			tChargecompany.setCodeappendx(o.getCodeappendx().getCodeappendx());
		}
		if (o.getAbutment()!=null) {
			tChargecompany.setAbutment(o.getAbutment().getAbutment());
		}
		if (o.getAuditbill()!=null) {
			tChargecompany.setAuditbill(o.getAuditbill().getAuditbill());
		}
		if (o.getRapauditnum()!=null) {
			tChargecompany.setRapauditnum(o.getRapauditnum().getRapauditnum());
		}
		
	}
	@RequestMapping(value = "check")//查看
	public String check(TChargecompany tChargecompany, Model model, Office office,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		
		//System.out.println(id+"scsdc");
//		System.out.println(tChargecompany.getId()+"获得到了id吗哈哈哈");
//		TChargecompany tC=tChargecompanyService.get(id);
		Office o=tChargecompanyService.getbyid(id);
		String city=o.getCity().getCity();
		//获取对应图片的名字
				String appnameString="";String runnameString="";String taxnameString="";String codnameString="";
				if(o.getAppendixtype().getAppendixtype()!=null&&o.getAppendixtype().getAppendixtype()!=""){
					//TFeedbackFile fA=tFeedbackFileService.get(o.getAppendixtype().getAppendixtype());
					TClientFile fA=tClientFileService.get(o.getAppendixtype().getAppendixtype());
					if(fA!=null){
						appnameString=fA.getFileName();
					}
					
				}
				if(o.getRunappendix().getRunappendix()!=null&&o.getRunappendix().getRunappendix()!=""){
					TClientFile fR=tClientFileService.get(o.getRunappendix().getRunappendix());
					if(fR!=null){
						runnameString=fR.getFileName();
					}
					 
				}
				if(o.getTaxappendx().getTaxappendx()!=null&&o.getTaxappendx().getTaxappendx()!=""){
					TClientFile fT=tClientFileService.get(o.getTaxappendx().getTaxappendx());
					if(fT!=null){
						taxnameString=fT.getFileName();
					}
					
				}
				if(o.getCodeappendx().getCodeappendx()!=null&&o.getCodeappendx().getCodeappendx()!=""){
					TClientFile fC=tClientFileService.get(o.getCodeappendx().getCodeappendx());
					if(fC!=null){
						codnameString=fC.getFileName();
					}
					
				}
				
				 model.addAttribute("appnameString",appnameString);
				 model.addAttribute("runnameString",runnameString);
				 model.addAttribute("taxnameString",taxnameString);
				 model.addAttribute("codnameString",codnameString);
				 tChargecompany.setId(id);
					officetchargecompany( o,tChargecompany);
					TChargecompany tC=tChargecompany;
		model.addAttribute("city", city);
		model.addAttribute("tC", tC);
		model.addAttribute("tChargecompany", tChargecompany);
		return "modules/adminback/clientManage_menue_check";
	}
	@RequestMapping(value = "stopservemenue")//停止列表
	public String stopservemenue(TChargecompany tChargecompany,User user, Model model, Office office,RedirectAttributes redirectAttributes) {
	
		List<Office> tcList=tChargecompanyService.selectByStatusN(office);
		/*User currentUser = UserUtils.getUser();
		office.setCreateBy(currentUser);
		List<Office> tcList=tChargecompanyService.selectByStatusN2(office);*/
		
		for (Office o : tcList) {
			//System.out.println(o.getChargecomname().getChargecomname()+"列表得到了名字对象");
			//List<User> userList=systemService.findUserByOfficeId(o.getId());//3.29号改的
			user.setCompany(o);
			List<User> userList=systemService.findsoncount(user);
			List<User> userListcomp=systemService.findcompcount(user);
			int countCompany=0;
			for (User u : userListcomp) {//用于获取企业数
				List<TCustomer> tccList=tCustomerService.ListBySupervisor(u.getId());
				int counttc=tccList.size();
				countCompany=countCompany+counttc;
			}
			o.setCountCompany(countCompany);//用于显示企业数的
			int count=userList.size();
			o.setSonCount(count);//用于存放这个公司下面的子账号
			//citySetlist.add(o.getCity().getCity());
		}
		//System.out.println(tcList.size()+"我就这么多");
		
		//System.out.println("-----以下用于显示城市");
		//显示对接人
		//model.addAttribute("citySetlist", citySetlist);
		model.addAttribute("tcList", tcList);
		return "modules/adminback/clientManage_menue_stopServicemenue";
	}
	
	@RequestMapping(value = "selByStatus")//根据状态查找
	public String selByStatus(TChargecompany tChargecompany,User user,Office office,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String useStatus=request.getParameter("userStatus");
		tChargecompany.setUsestatus(useStatus);
		office.setUsestatus(tChargecompany);
		/*User currentUser = UserUtils.getUser();//用于当前的备份
		office.setCreateBy(currentUser);
		List<Office> tcList=tChargecompanyService.selByStatus2(office);*/
		List<Office> tcList=tChargecompanyService.selByStatus(office);
		Set<String> citySetlist=new HashSet<String>();
		for (Office o : tcList) {
			//System.out.println(o.getChargecomname().getChargecomname()+"列表得到了名字对象");
			//List<User> userList=systemService.findUserByOfficeId(o.getId());
			user.setCompany(o);
			List<User> userList=systemService.findsoncount(user);
			List<User> userListcomp=systemService.findcompcount(user);
			int countCompany=0;
			for (User u : userListcomp) {//用于获取企业数
				List<TCustomer> tccList=tCustomerService.ListBySupervisor(u.getId());
				int counttc=tccList.size();
				countCompany=countCompany+counttc;
			}
			o.setCountCompany(countCompany);//用于显示企业数的
			int count=userList.size();
			o.setSonCount(count);//用于存放这个公司下面的子账号
			citySetlist.add(o.getCity().getCity());
		}
		System.out.println(tcList.size()+"我就这么多");
		
		System.out.println("-----以下用于显示城市");
		//显示对接人
		model.addAttribute("citySetlist", citySetlist);
		model.addAttribute("tcList", tcList);
		//return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/?repage";
		return "modules/adminback/clientManage_menue";
	}
	@RequestMapping(value = "allCity")//根据所有城市查询
	public String allCity(TChargecompany tChargecompany,User user, Model model, Office office,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		try {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String allCity=request.getParameter("allCity");
		allCity = new String(allCity.getBytes("ISO8859-1"),"UTF-8");
		System.out.println(allCity+"fj");
		//tChargecompany.setCity(allCity);//设置要查询的城市
		tChargecompany.setServicestatus("1");
		//office.setCity(tChargecompany);
		if(allCity.equals("北京市")||allCity.equals("天津市")||allCity.equals("重庆市")||allCity.equals("上海市")||allCity.equals("台湾")||allCity.equals("香港特别行政区")||allCity.equals("澳门特别行政区")){
			office.setProvince(allCity);
		}else {
			office.setMunicipality(allCity);
		}
		
		office.setServicestatus(tChargecompany);
		List<Office> tcList=tChargecompanyService.allCity(office);
		
		Set<String> citySetlist=new HashSet<String>();
		for (Office o : tcList) {
			//System.out.println(o.getChargecomname().getChargecomname()+"列表得到了名字对象");
			//List<User> userList=systemService.findUserByOfficeId(o.getId());
			user.setCompany(o);
			List<User> userList=systemService.findsoncount(user);
			List<User> userListcomp=systemService.findcompcount(user);
			int countCompany=0;
			for (User u : userListcomp) {//用于获取企业数
				List<TCustomer> tccList=tCustomerService.ListBySupervisor(u.getId());
				int counttc=tccList.size();
				countCompany=countCompany+counttc;
			}
			o.setCountCompany(countCompany);//用于显示企业数的
			int count=userList.size();
			o.setSonCount(count);//用于存放这个公司下面的子账号
			String city=o.getProvince();
			String citytrue="";
			if(city!=null){
			//cl.province=='北京市'||cl.province=='天津市'||cl.province=='重庆市'||cl.province=='上海市'||cl.province=='台湾'||cl.province=='香港特别行政区'||cl.province=='澳门特别行政区'}"
			if(city.equals("北京市")||city.equals("天津市")||city.equals("重庆市")||city.equals("上海市")||city.equals("台湾")||city.equals("香港特别行政区")||city.equals("澳门特别行政区")){
				citytrue=city;
			}else {
				citytrue=o.getMunicipality();
			}
			if(!"".equals(citytrue)){
				citySetlist.add(citytrue);
			}
			
			}
		}
		model.addAttribute("citySetlist", citySetlist);
		
		model.addAttribute("tcList", tcList);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//model.addAttribute("count", count);
		
		
		//return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/?repage";
		return "modules/adminback/clientManage_menue";
	}
	@RequestMapping(value = "search")//根据收索城市查询
	public String search(TChargecompany tChargecompany,User user, Model model,Office office, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		try {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String search=request.getParameter("search");
		//search = new String(search.getBytes("ISO8859-1"),"UTF-8");
		if("".equals(search)){//如果为空就走列表
			return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/menue";
		}
		System.out.println(search+"fj");
		//tChargecompany.setCity(search);//设置要查询的城市
		tChargecompany.setServicestatus("1");
		
		if(search.equals("北京市")||search.equals("天津市")||search.equals("重庆市")||search.equals("上海市")||search.equals("台湾")||search.equals("香港特别行政区")||search.equals("澳门特别行政区")){
			office.setProvince(search);
		}else {
			office.setMunicipality(search);
		}
		
		office.setServicestatus(tChargecompany);
		List<Office> tcList=tChargecompanyService.allCity(office);
		
		Set<String> citySetlist=new HashSet<String>();
		for (Office o : tcList) {
			//System.out.println(o.getChargecomname().getChargecomname()+"列表得到了名字对象");
			//List<User> userList=systemService.findUserByOfficeId(o.getId());
			user.setCompany(o);
			List<User> userList=systemService.findsoncount(user);
			List<User> userListcomp=systemService.findcompcount(user);
			int countCompany=0;
			for (User u : userListcomp) {//用于获取企业数
				List<TCustomer> tccList=tCustomerService.ListBySupervisor(u.getId());
				int counttc=tccList.size();
				countCompany=countCompany+counttc;
			}
			o.setCountCompany(countCompany);//用于显示企业数的
			int count=userList.size();
			o.setSonCount(count);//用于存放这个公司下面的子账号
			String city=o.getProvince();
			String citytrue="";
			if(city!=null){
			//cl.province=='北京市'||cl.province=='天津市'||cl.province=='重庆市'||cl.province=='上海市'||cl.province=='台湾'||cl.province=='香港特别行政区'||cl.province=='澳门特别行政区'}"
			if(city.equals("北京市")||city.equals("天津市")||city.equals("重庆市")||city.equals("上海市")||city.equals("台湾")||city.equals("香港特别行政区")||city.equals("澳门特别行政区")){
				citytrue=city;
			}else {
				citytrue=o.getMunicipality();
			}
			if(!"".equals(citytrue)){
				citySetlist.add(citytrue);
			}
			
			}
		}
		model.addAttribute("citySetlist", citySetlist);
		
		model.addAttribute("tcList", tcList);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//model.addAttribute("count", count);
		
		
		//return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/?repage";
		return "modules/adminback/clientManage_menue";
	}
	
	@RequestMapping(value = "searchByStopService")//根据收索城市查询停止服务列表内的列表
	public String searchByStopService(TChargecompany tChargecompany,User user, Office office,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		try {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String search=request.getParameter("search");
		//search = new String(search.getBytes("ISO8859-1"),"UTF-8");
		if("".equals(search)){//如果为空就走列表
			return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/stopservemenue";
		}
		System.out.println(search+"fj");
		//tChargecompany.setCity(search);//设置要查询的城市
		tChargecompany.setServicestatus("0");
		if(search.equals("北京市")||search.equals("天津市")||search.equals("重庆市")||search.equals("上海市")||search.equals("台湾")||search.equals("香港特别行政区")||search.equals("澳门特别行政区")){
			office.setProvince(search);
		}else {
			office.setMunicipality(search);
		}
		
		office.setServicestatus(tChargecompany);
		List<Office> tcList=tChargecompanyService.allCity(office);
		
		Set<String> citySetlist=new HashSet<String>();
		for (Office o : tcList) {
			//System.out.println(o.getChargecomname().getChargecomname()+"列表得到了名字对象");
			//List<User> userList=systemService.findUserByOfficeId(o.getId());
			user.setCompany(o);
			List<User> userList=systemService.findsoncount(user);
			List<User> userListcomp=systemService.findcompcount(user);
			int countCompany=0;
			for (User u : userListcomp) {//用于获取企业数
				List<TCustomer> tccList=tCustomerService.ListBySupervisor(u.getId());
				int counttc=tccList.size();
				countCompany=countCompany+counttc;
			}
			o.setCountCompany(countCompany);//用于显示企业数的
			int count=userList.size();
			o.setSonCount(count);//用于存放这个公司下面的子账号
			String city=o.getProvince();
			String citytrue="";
			if(city!=null){
			//cl.province=='北京市'||cl.province=='天津市'||cl.province=='重庆市'||cl.province=='上海市'||cl.province=='台湾'||cl.province=='香港特别行政区'||cl.province=='澳门特别行政区'}"
			if(city.equals("北京市")||city.equals("天津市")||city.equals("重庆市")||city.equals("上海市")||city.equals("台湾")||city.equals("香港特别行政区")||city.equals("澳门特别行政区")){
				citytrue=city;
			}else {
				citytrue=o.getMunicipality();
			}
			if(!"".equals(citytrue)){
				citySetlist.add(citytrue);
			}
			
			}
		}
		model.addAttribute("citySetlist", citySetlist);
		
		model.addAttribute("tcList", tcList);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//model.addAttribute("count", count);
		
		
		//return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/?repage";
		return "modules/adminback/clientManage_menue_stopServicemenue";
	}
	/*@RequestMapping(value = "saveSetUser")//保存记账公司列表下的设置内的管理员密码等

	public String saveSetUser(TChargecompany tChargecompany,User user,HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			
			String id=request.getParameter("id");
			System.out.println(id+"设置的id有吗");
			Office office=new Office();
			office.setId(id);
			user.setCompany(office);
			 user=systemService.findByidandaccount(user);
			String pwd=request.getParameter("pwd");
			pwd = new String(pwd.getBytes("ISO8859-1"),"UTF-8");
			if(pwd!=""){
			user.setNewPassword(pwd);//用心密码
			if (StringUtils.isNotBlank(user.getNewPassword())) {
				user.setPassword(SystemService.entryptPassword(user
						.getNewPassword()));
			}
			}
			String[] roleIdArray={"94b5b44a17144149bf52f02159137e6c"};
			// 角色数据有效性验证，过滤不在授权内的角色
			List<Role> roleList = Lists.newArrayList();
			List<String> roleIdList =  new ArrayList<String>();
			Collections.addAll(roleIdList, roleIdArray);
			Role role=new Role(roleIdArray[0]);
			roleList.add(role);
			user.setRoleList(roleList);
			
			systemService.saveUser(user);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/menue";
	}
	
	*/
	@RequestMapping(value = "saveSetUser")//保存记账公司列表下的设置内的管理员密码等

	public String saveSetUser(TChargecompany tChargecompany,Office office,User user,HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			
			String id=request.getParameter("id");
			System.out.println(id+"设置的id有吗");
			
			office=officeService.get(id);
			user.setCompany(office);
			 user=systemService.findByidandaccount(user);
			String pwd=request.getParameter("pwd");
			pwd = new String(pwd.getBytes("ISO8859-1"),"UTF-8");
			if(pwd!=""){
			user.setNewPassword(pwd);//用心密码
			if (StringUtils.isNotBlank(user.getNewPassword())) {
				user.setPassword(SystemService.entryptPassword(user
						.getNewPassword()));
			}
			}
			systemService.updatePasswordByIdnew(user);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:"+Global.getAdminPath()+"/newcharge/tChargecompany/menue";
	}
	
	
	
	//后台父页面，数据统计，城市区域
	@RequestMapping(value = "cityGeneralSituation")
	public String dataCount_cityArea(HttpServletRequest request, HttpServletResponse response,Model model){
		String province = request.getParameter("province"); //省
		String city = request.getParameter("city"); //市
		if (city!=null) {
			if (province.equals("北京市")||province.equals("天津市")||province.equals("重庆市")||province.equals("上海市")||province.equals("台湾")||province.equals("香港特别行政区")||province.equals("澳门特别行政区")) {
				city=province;
			}
		}
		String county = request.getParameter("county"); //县
		StringBuffer sb = new StringBuffer();
		if(province != null && !"".equals(province))
			sb.append(province);
		if(city != null && !"".equals(city))
			sb.append("/").append(city);
		if(county != null && !"".equals(county))
			sb.append("/").append(county);
		//List<TCountCompany> list = tChargecompanyService.cityGeneralSituation(sb.toString()); 
		List<TCountCompany> list = tChargecompanyService.cityGeneralSituation(city); 
		model.addAttribute("list", list);
		model.addAttribute("province",province);
		model.addAttribute("city",city);
		model.addAttribute("county",county);
		model.addAttribute("curdate",DateUtils.formatDate(new Date()));
		return "modules/adminback/dataCount_cityArea";
	}
	//后台父页面，数据统计，数据概况
	@RequestMapping(value = "dataGeneralSituation")
	public String dataCount_dataGeneralSituation(HttpServletRequest request, HttpServletResponse response,Model model){
		
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		if(begin != null){
			model.addAttribute("begin",begin);
		}
		if(end != null){
			model.addAttribute("end",end);
		}
		List<TCountCompanynew> ttList=tChargecompanyService.dataGeneralSituationNew(request);
		model.addAttribute("ttList", ttList);
		return "modules/adminback/dataCount_dataGeneralSituation";
	}
	//上传
	@RequestMapping(value = "fileUpload2")
	@ResponseBody
	public String fileUpload2(HttpServletRequest request) throws IllegalStateException, IOException{
		String fiName=request.getParameter("file");
		System.out.println(fiName+"hahhahahahhahahahha哈哈哈");
		System.out.println("上传的上来吗？来来来");
		String fileName="";
		String id="";
		org.springframework.web.multipart.MultipartHttpServletRequest  mRequest = (MultipartHttpServletRequest)(request);  
		//解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		//先判断request中是否包涵multipart类型的数据，
		 if(multipartResolver.isMultipart(request)){
			 
			//再将request中的数据转化成multipart类型的数据
			 MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			 //迭代器
			 Iterator iter = multiRequest.getFileNames();
			 
			 while(iter.hasNext()){
				    MultipartFile file = multiRequest.getFile((String)iter.next());
				    if(file != null){
				     fileName = file.getOriginalFilename();
				     Map<String,Object> fileMap=FileUtils.upload(file,Global.getclientphoto(),mRequest);//对文件进行上传
				     if(!fileMap.isEmpty()){
				    	 /*
							TFeedbackFile f = new TFeedbackFile();
							f.setFileName(fileMap.get("newName").toString());
							f.setOldName(fileMap.get("oldName").toString());
							//id=officeService.getautoidfile().toString();
							tFeedbackFileService.save(f);
							id=f.getId().toString();*/
				    	 TClientFile t=new TClientFile();
				    	 t.setFileName(fileMap.get("newName").toString());
				    	 t.setOldName(fileMap.get("oldName").toString());
				    	 tClientFileService.save(t);
				    	 id=t.getId().toString();
						}
				    }
			   }
		 }
		 //添加附件表数据
		 ///TAppendix tAppendix=insertTAppendix(appendix,fileName);
		 //List<TAppendix> list=list();
		 System.out.println(id+"存入图片id");
		 return id;
	}
	
	//后台父页面，首页
			@RequestMapping(value = "index")//后台管理首页数据统计
			public String index( TChargecompany tChargecompany,HttpServletRequest request, HttpServletResponse response,Model model){
				
				 Date date=new Date();
	        	 System.out.println(date);
	        	   Calendar calendar = Calendar.getInstance();  
	               calendar.setTime(date);  
	              calendar.add(Calendar.DAY_OF_MONTH, -1);  
	               date = calendar.getTime();  
	              System.out.println(date);
	               Date dateNow=new Date();
	               SimpleDateFormat fm=new SimpleDateFormat("yyyy-MM-dd");
	               String d=fm.format(date);//前一天的时间
	               System.out.println(d+"这是昨天的时间吗");
	               String dNow=fm.format(dateNow);
	               System.out.println(dNow+"今天的日期");
				
				TCountCompany tconww=new TCountCompany();
				TCountCompany tconoldww=new TCountCompany();
				List<TCountCompany> listnew = tChargecompanyService.indexDatanew(d, dNow);
				for (TCountCompany t1 : listnew) {
					
					if (t1.getDate().equals(dNow)) {
						tconww.setCompanyNumber(t1.getCompanyNumber());
					}
				if (t1.getDate().equals(d)) {
					tconoldww.setCompanyNumber(t1.getCompanyNumber());
				}
					
				}
				List<TCountCompany> listnewaccount = tChargecompanyService.indexDatanewaccount(d, dNow);
				for (TCountCompany t2 : listnewaccount) {
					
					if (t2.getDate().equals(dNow)) {
						tconww.setCompanyUsers(t2.getCompanyUsers());
					}
					if (t2.getDate().equals(d)) {
						tconoldww.setCompanyUsers(t2.getCompanyUsers());
					}
				}
				List<TCountCompany> listnewcustomer = tChargecompanyService.indexDatanewcustomer(d, dNow);
			     for (TCountCompany t3 : listnewcustomer) {
			    	
			    	 if (t3.getDate().equals(dNow)) {
			    		 tconww.setCustomer(t3.getCustomer());
						}
						if (t3.getDate().equals(d)) {
							tconoldww.setCustomer(t3.getCustomer());
						}
				}
			    
			     
			     
				
				
				Tjoinappl tjoinappl=new Tjoinappl();
				List<Tjoinappl> tjList=tjoinapplService.findList(tjoinappl);
				int joinCount=tjList.size();
				TFeedback tFeedback=new TFeedback ();
				List<TFeedback> tfList=tFeedbackService.findList(tFeedback);
				int tfCount=tfList.size();
				
				
				String province = request.getParameter("province"); //省
				String city = request.getParameter("city"); //市
				String county = request.getParameter("county"); //县
				StringBuffer sb = new StringBuffer();
				if(province != null && !"".equals(province))
					sb.append(province);
				if(city != null && !"".equals(city))
					sb.append("/").append(city);
				if(county != null && !"".equals(county))
					sb.append("/").append(county);
				List<TCountCompany> listcomp = tChargecompanyService.companySituation(sb.toString()); //记账公司
				int j=0;
				for (TCountCompany tC : listcomp) {
					j++;tC.setTop(j);
				}
				List<TCountCompany> listcustomer = tChargecompanyService.customerSituation(sb.toString());//服务企业
				int jj=0;
				for (TCountCompany tC : listcustomer) {
					jj++;tC.setTop(jj);
				}
				List<TCountCompany> listusers = tChargecompanyService.userSituation(sb.toString());//财务人员
				int jjj=0;
				for (TCountCompany tC : listusers) {
					jjj++;tC.setTop(jjj);
				}
				
				String accountChargeString=tChargecompanyService.conutcharge();
				model.addAttribute("conutaccount", tChargecompanyService.conutaccount());
				model.addAttribute("conutcustomer", tChargecompanyService.conutcustomer());
				model.addAttribute("accountChargeString", accountChargeString);
				 model.addAttribute("tcon", tconww);
					model.addAttribute("tconold", tconoldww);
				
				
				model.addAttribute("listusers", listusers);
				model.addAttribute("listcustomer", listcustomer);
				model.addAttribute("listcomp", listcomp);
				model.addAttribute("dNow", dNow);//今日日期
				model.addAttribute("tfCount", tfCount);//反馈意见的数量
				model.addAttribute("joinCount", joinCount);//加盟申请的数量
				
				return "modules/adminback/index";
			}
			
			@RequestMapping(value = "fillusername")//填充客户列表设置用户的用户名
			@ResponseBody
			public String fillusername(TChargecompany tChargecompany,HttpServletRequest request,User user,Office office, HttpServletResponse response) throws IllegalStateException, IOException {
				String result="";
				
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("UTF-8");
				String id=request.getParameter("id");
				//office.setId(id);
				office=officeService.get(id);
				user.setCompany(office);
				 user=systemService.findByidandaccount(user);
				 if(user!=null){
					 result=user.getLoginName();
				 }
				return result;
			}
			
			@RequestMapping(value = "/getFile")
			public void imageGet(String fileName,HttpServletResponse response){
		        FileInputStream fis = null;
		        response.setContentType("image/gif");
		        try {
		            OutputStream out = response.getOutputStream();
		            File file = new File(Global.getclientphoto()+File.separator+fileName);
		            fis = new FileInputStream(file);
		            byte[] b = new byte[fis.available()];
		            fis.read(b);
		            out.write(b);
		            out.flush();
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            if (fis != null) {
		                try {
		                    fis.close();
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
		            }
		        }
		    }
	
}