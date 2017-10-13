/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfo;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfoToWeb;
import com.thinkgem.jeesite.modules.billinfo.service.TBillInfoService;
import com.thinkgem.jeesite.modules.billtype.entity.TBillType;
import com.thinkgem.jeesite.modules.billtype.service.TBillTypeService;
import com.thinkgem.jeesite.modules.customer.entity.TAppendix;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.cususer.entity.TCusUser;
import com.thinkgem.jeesite.modules.cususer.service.TCusUserService;
import com.thinkgem.jeesite.modules.power.entity.TYdSysMenu;
import com.thinkgem.jeesite.modules.power.service.TYdSysMenuService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucher.service.TVoucherService;
import com.thinkgem.jeesite.modules.vouchertemplatetype.entity.TVoucherTemplateType;
import com.thinkgem.jeesite.modules.vouchertemplatetype.service.TVoucherTemplateTypeService;
import com.thinkgem.jeesite.modules.vouchertitle.entity.TVoucherTitle;
import com.thinkgem.jeesite.modules.vouchertitle.service.TVoucherTitleService;
import com.thinkgem.jeesite.modules.workterrace.entity.FollowUp;

/*import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
 import com.thinkgem.jeesite.modules.voucher.service.TVoucherService;
 */
/**
 * 用户Controller
 * 
 * @author ThinkGem
 * @version 2013-8-29
 */
//@SessionAttributes({"sessionCustomerId"})
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;

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
	
	@Autowired
	private TCusUserService tCusUserService;
	
	@Autowired
	private TVoucherService voucherService;
	@Autowired
	private TYdSysMenuService tYdSysMenuService;
	
	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "index" })
	public String index(User user, Model model) {
		return "modules/sys/userIFndex";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "list", "" })
	public String list(User user, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request,
				response), user);
		model.addAttribute("page", page);
		return "modules/sys/userList";
	}

	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "listData" })
	public Page<User> listData(User user, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request,
				response), user);
		return page;
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany() == null || user.getCompany().getId() == null) {
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice() == null || user.getOffice().getId() == null) {
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userForm";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user
					.getNewPassword()));
		}
		if (!beanValidator(model, user)) {
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(),
				user.getLoginName()))) {
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()) {
			if (roleIdList.contains(r.getId())) {
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
			UserUtils.clearCache();
			// UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	@RequestMapping(value = "savePower")//保存用户的权限yang
	public String savePower(User user, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		String userid=request.getParameter("id");
		System.out.println("获得用户id"+userid);
		List<String> poweridList=user.getPowerIdList();//获取id，在这里没有
		
		user.setId(userid);
		systemService.saveUserPower(user);//保存到中间表了
		User currentUser = UserUtils.getUser();
		List<TYdSysMenu> ty=tYdSysMenuService.findPower(new TYdSysMenu(currentUser));
//		for (TYdSysMenu tyt : ty) {
//			System.out.println(tyt.getName()+"哈哈哈得到了");
//		}
		System.out.println(currentUser.getPowerNames()+"角色名字");
		return "redirect:"+Global.getAdminPath()+"/sys/user/usermanage";
	}
	@RequiresPermissions("sys:user:edit")
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
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 导出用户数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(User user, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			Page<User> page = systemService.findUser(new Page<User>(request,
					response, -1), user);
			new ExportExcel("用户数据", User.class).setDataList(page.getList())
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 导入用户数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file,
			RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list) {
				try {
					if ("true".equals(checkLoginName("", user.getLoginName()))) {
						user.setPassword(SystemService
								.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					} else {
						failureMsg.append("<br/>登录名 " + user.getLoginName()
								+ " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>登录名 " + user.getLoginName()
							+ " 导入失败：");
					List<String> messageList = BeanValidators
							.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>登录名 " + user.getLoginName()
							+ " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户"
					+ failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 下载导入用户数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据导入模板.xlsx";
			List<User> list = Lists.newArrayList();
			list.add(UserUtils.getUser());
			new ExportExcel("用户数据", User.class, 2).setDataList(list)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
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

	/**
	 * 用户信息显示及保存
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			currentUser.setName(user.getName());
			currentUser.setQq(user.getQq());
			currentUser.setAddress(user.getAddress());
			currentUser.setSex(user.getSex());
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			//currentUser.setPhoto(user.getPhoto());//
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		User usernewUser=new User();
		usernewUser.setCompany(UserUtils.getUser().getCompany());
		//用户信息
		//model.addAttribute("listuser", systemService.allUserList());chang
		model.addAttribute("listusernew", systemService.findListNew(usernewUser));//yang
		//客户信息
		model.addAttribute("listcustomer", tCustomerService.ListBySupervisor(currentUser.getId()));
		//model.addAttribute("listcustomer", tCustomerService.findBySupervisor(currentUser.getId()));
		//System.out.println("客户信息的长度："+tCustomerService.ListBySupervisor(user.getId()).size());
		model.addAttribute("tCusUser", new TCusUser());
		
		showCusUser(model,currentUser);
		model.addAttribute("currentUser",currentUser);
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo2";
		//return "modules/sys/yunzh";
	}
	/**
	 * 转凭证下拉框中的数据，cy
	 * **/
	public void showCusUser(Model model,User currentUser){
		
		//以下都是转账记录的信息显示
		//用户信息（下拉框）
		model.addAttribute("listuser", systemService.allUserList());
		
		//客户信息（下拉框）
		model.addAttribute("listcustomer", tCustomerService.ListBySupervisor(currentUser.getId()));
		
		//页面首次加载的时候，要有的变量
		TCusUser tCusUser=new TCusUser();
		model.addAttribute("tCusUser", tCusUser);
		
		Noaccept(tCusUser,model);
	}
	/**
	 * 根据接受状态、转交用户来获取所有数据(分组显示)，cy
	 * **/
	public void  Noaccept(TCusUser tCusUser,Model model){
		tCusUser.setUserid(UserUtils.getUser().getId());
		tCusUser.setAcceptState("0");
		List<TCusUser> tcususerList=tCusUserService.Noaccept(tCusUser);
		System.out.println("tcususerList length:"+tcususerList.size());
		model.addAttribute("tcususerList", tcususerList);
	}
	
	/**
	 * 返回用户信息
	 * 
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}

	/**
	 * 修改个人用户密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		String result="0";
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword)
				&& StringUtils.isNotBlank(newPassword)) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())) {
				systemService.updatePasswordById(user.getId(),user.getLoginName(), newPassword);
				result="1";
				model.addAttribute("message", "修改密码成功");
			} else {
				result="2";
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}

		return result;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String officeId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i = 0; i < list.size(); i++) {
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_" + e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	// @InitBinder
	// public void initBinder(WebDataBinder b) {
	// b.registerCustomEditor(List.class, "roleList", new
	// PropertyEditorSupport(){
	// @Autowired
	// private SystemService systemService;
	// @Override
	// public void setAsText(String text) throws IllegalArgumentException {
	// String[] ids = StringUtils.split(text, ",");
	// List<Role> roles = new ArrayList<Role>();
	// for (String id : ids) {
	// Role role = systemService.getRole(Long.valueOf(id));
	// roles.add(role);
	// }
	// setValue(roles);
	// }
	// @Override
	// public String getAsText() {
	// return Collections3.extractToString((List) getValue(), "id", ",");
	// }
	// });
	// }

	@RequiresPermissions("user")
	@RequestMapping(value = "billInfos")
	public String billInfos(HttpServletRequest request,
			HttpServletResponse response, TBillInfo tBillInfo,String showType,String orderBy ,Model model) {

		// 作废状态 billStatus 3
		/*List<TBillInfo> billInfos = billInfoService.findListForImages(null,
				"id", "id", "", "");*/
		if(StringUtils.isBlank(showType)){
			showType = "1";
		}
		
		tBillInfo.setBillStatus("2");
		
		List<TBillInfo> billInfos = billInfoService.findListWithOrderBy(tBillInfo,orderBy);
		if ("2".equals(showType)) {
			List<String> typeId = new ArrayList<String>();
			Map<String, List<TBillInfo>> maps = new HashMap<String, List<TBillInfo>>();
			Map<String, Double> mapsAmountCount = new HashMap<String, Double>();
			Map<String, Double> mapsTaxCount = new HashMap<String, Double>();
			Map<String, Double> mapsTotalPriceCount = new HashMap<String, Double>();

			for (TBillInfo billInfo : billInfos) {
				String type = billInfo.getTBId();

				// 同一类型发票信息
				List<TBillInfo> mapsBill = maps.get(type);
				if (null == mapsBill) {
					mapsBill = new ArrayList<TBillInfo>();
				}
				mapsBill.add(billInfo);
				maps.put(type, mapsBill);

				// 同一类型金额
				Double amountCountInteger = mapsAmountCount.get(type);
				if (null == amountCountInteger) {
					amountCountInteger = new Double(0);
				}
				if (StringUtils.isNoneBlank(billInfo.getAmount())) {
					amountCountInteger = amountCountInteger
							+ Double.parseDouble(billInfo.getAmount());
				}
				mapsAmountCount.put(type, amountCountInteger);

				// 同一类型税额
				Double taxCountInteger = mapsTaxCount.get(type);
				if (null == taxCountInteger) {
					taxCountInteger = new Double(0);
				}
				if (StringUtils.isNoneBlank(billInfo.getTax())) {
					taxCountInteger = taxCountInteger
							+ Double.parseDouble(billInfo.getTax());
				}
				mapsTaxCount.put(type, taxCountInteger);

				// 同一类型价格合计
				Double totalPriceCountCountInteger = mapsTotalPriceCount
						.get(type);
				if (null == totalPriceCountCountInteger) {
					totalPriceCountCountInteger = new Double(0);
				}
				if (StringUtils.isNoneBlank(billInfo.getTotalPrice())) {
					totalPriceCountCountInteger = totalPriceCountCountInteger
							+ Double.parseDouble(billInfo.getTotalPrice());
				}
				mapsTotalPriceCount.put(type, totalPriceCountCountInteger);

				// 发票类型
				if (!typeId.contains(type)) {
					typeId.add(type);
				}
			}
			List<TBillInfoToWeb> tBillInfoToWebs = new ArrayList<TBillInfoToWeb>();
			// 查询发票分类 并保存数据结构 给前台
			if (CollectionUtils.isNotEmpty(typeId)) {

				int index = 0;
				
				for (String id : typeId) {
					TBillInfoToWeb billInfoToWeb = new TBillInfoToWeb();
					TBillType billType = billTypeService.get(id);
					billInfoToWeb.setBillType(billType);
					billInfoToWeb.setBillInfoList(maps.get(id));
					billInfoToWeb.setAmountCount(mapsAmountCount.get(id)
							.toString());
					billInfoToWeb.setTaxCount(mapsTaxCount.get(id).toString());
					billInfoToWeb.setTotalPriceCount(mapsTotalPriceCount
							.get(id).toString());
					tBillInfoToWebs.add(billInfoToWeb);
				}
			}
			model.addAttribute("result", tBillInfoToWebs);
		}
		model.addAttribute("showType", showType);
		if ("1".equals(showType)) {
			model.addAttribute("result", billInfos);
		}

		//List<TAccount> accounts = tAccountService.findList(null);
		
		//model.addAttribute("accounts", accounts);
		
		List<TVoucherTemplateType> tVoucherTemplateTypes = tVoucherTemplateTypeService.findList(null);
		
		model.addAttribute("tVoucherTemplateTypes", tVoucherTemplateTypes);
		
		List<TVoucherTitle> voucherTitles = 	tVoucherTitleService.findList(null);
		model.addAttribute("voucherTitles", voucherTitles);
		
		return "modules/voucher/voucherInput";
	}
	
	@RequestMapping(value = "billInfosAjax")
	@ResponseBody
	public Object billInfosAjax(HttpServletRequest request,
			HttpServletResponse response, TBillInfo tBillInfo,String showType,String orderBy,Model model) {
		if(StringUtils.isBlank(showType)){
			showType = "1";
		}
		List<TBillInfo> billInfos = billInfoService.findListWithOrderBy(tBillInfo,orderBy);
		if ("2".equals(showType)) {
			List<String> typeId = new ArrayList<String>();
			Map<String, List<TBillInfo>> maps = new HashMap<String, List<TBillInfo>>();
			Map<String, Double> mapsAmountCount = new HashMap<String, Double>();
			Map<String, Double> mapsTaxCount = new HashMap<String, Double>();
			Map<String, Double> mapsTotalPriceCount = new HashMap<String, Double>();

			for (TBillInfo billInfo : billInfos) {
				String type = billInfo.getTBId();
				//对发票状态的判断
				if("0".equals(billInfo.getBillStatus())){
					billInfo.setStatusName("待审验");
				}
				if("1".equals(billInfo.getBillStatus())){
					billInfo.setStatusName("退回");
				}
				if("2".equals(billInfo.getBillStatus())){
					billInfo.setStatusName("已审验");
				}
				if("3".equals(billInfo.getBillStatus())){
					billInfo.setStatusName("已做账 ");
				}
				if("4".equals(billInfo.getBillStatus())){
					billInfo.setScrap(false);
					billInfo.setStatusName("作废");
				}
				if("5".equals(billInfo.getBillStatus())){
					billInfo.setError(false);
					billInfo.setStatusName("错误");
				}
				
				// 同一类型发票信息
				List<TBillInfo> mapsBill = maps.get(type);
				if (null == mapsBill) {
					mapsBill = new ArrayList<TBillInfo>();
				}
				mapsBill.add(billInfo);
				maps.put(type, mapsBill);

				// 同一类型金额
				Double amountCountInteger = mapsAmountCount.get(type);
				if (null == amountCountInteger) {
					amountCountInteger = new Double(0);
				}
				if (StringUtils.isNoneBlank(billInfo.getAmount())) {
					amountCountInteger = amountCountInteger
							+ Double.parseDouble(billInfo.getAmount());
				}
				mapsAmountCount.put(type, amountCountInteger);

				// 同一类型税额
				Double taxCountInteger = mapsTaxCount.get(type);
				if (null == taxCountInteger) {
					taxCountInteger = new Double(0);
				}
				if (StringUtils.isNoneBlank(billInfo.getTax())) {
					taxCountInteger = taxCountInteger
							+ Double.parseDouble(billInfo.getTax());
				}
				mapsTaxCount.put(type, taxCountInteger);

				// 同一类型价格合计
				Double totalPriceCountCountInteger = mapsTotalPriceCount
						.get(type);
				if (null == totalPriceCountCountInteger) {
					totalPriceCountCountInteger = new Double(0);
				}
				if (StringUtils.isNoneBlank(billInfo.getTotalPrice())) {
					totalPriceCountCountInteger = totalPriceCountCountInteger
							+ Double.parseDouble(billInfo.getTotalPrice());
				}
				mapsTotalPriceCount.put(type, totalPriceCountCountInteger);

				// 发票类型
				if (!typeId.contains(type)) {
					typeId.add(type);
				}
			}
			List<TBillInfoToWeb> tBillInfoToWebs = new ArrayList<TBillInfoToWeb>();
			// 查询发票分类 并保存数据结构 给前台
			if (CollectionUtils.isNotEmpty(typeId)) {
				int index = 0;
				for (String id : typeId) {
					TBillInfoToWeb billInfoToWeb = new TBillInfoToWeb();
					TBillType billType = billTypeService.get(id);
					billInfoToWeb.setBillType(billType);
					billInfoToWeb.setBillInfoList(maps.get(id));
					billInfoToWeb.setAmountCount(mapsAmountCount.get(id)
							.toString());
					billInfoToWeb.setTaxCount(mapsTaxCount.get(id).toString());
					billInfoToWeb.setTotalPriceCount(mapsTotalPriceCount
							.get(id).toString());
					billInfoToWeb.setIndex(index);
					index ++;
					tBillInfoToWebs.add(billInfoToWeb);
				}
			}
			return ImmutableMap.of("showType",showType,"result",tBillInfoToWebs);
		}
		if ("1".equals(showType)) {
			for (TBillInfo billInfo : billInfos) {
				String type = billInfo.getTBId();
				//对发票状态的判断
				if("0".equals(billInfo.getBillStatus())){
					billInfo.setStatusName("待审验");
				}
				if("1".equals(billInfo.getBillStatus())){
					billInfo.setStatusName("退回");
				}
				if("2".equals(billInfo.getBillStatus())){
					billInfo.setStatusName("已审验");
				}
				if("3".equals(billInfo.getBillStatus())){
					billInfo.setStatusName("已做账 ");
				}
				if("4".equals(billInfo.getBillStatus())){
					billInfo.setScrap(false);
					billInfo.setStatusName("作废");
				}
				if("5".equals(billInfo.getBillStatus())){
					billInfo.setError(false);
					billInfo.setStatusName("错误");
				}
	
			}
			return ImmutableMap.of("showType",showType,"result",billInfos);
		}	
		return null;
	}
	
	
	@RequiresPermissions("user")
	@RequestMapping(value = "voucherIndex")
	public String voucherIndex(String compay,String updateDate,Model model,HttpServletRequest request,HttpSession session) throws ParseException {
		
		TCustomer customerObject=(TCustomer)session.getAttribute("sessionCustomer");
		
		//根据条件统计数据
		TBillInfo billInfo = new TBillInfo();
		//公司名 传递或者session中获取
		compay = customerObject.getCustomerName();
		billInfo.setPayName(compay);
		//账期(yyyyMM) 传递或者session中获取 
		updateDate = customerObject.getCurrentperiod();
		//SimpleDateFormat sdFormat = new  SimpleDateFormat("yyyyMM");
		//billInfo.setUploadPeriod(updateDate);
		
		request.getSession().setAttribute("user_period", updateDate);
		
		//获取客户信息
	/*	TCustomer	tCustomer = new TCustomer();
		tCustomer.setCustomerName(compay);
		tCustomer = tCustomerService.getId(tCustomer);*/
		
		model.addAttribute("tCustomer",customerObject);
		
		billInfo.setUploadPeriod(updateDate);
		model.addAttribute("uploadNotes", tBillInfoService.countBillInfo(billInfo,null));//上传票据
		String billInfoStatus = "(1,2,3,4,5)";
		model.addAttribute("processed", tBillInfoService.countBillInfo(billInfo,billInfoStatus));//已处理
		billInfoStatus = "(3)";
		model.addAttribute("accounted", tBillInfoService.countBillInfo(billInfo,billInfoStatus));//已记账
		billInfoStatus = "(1,2,4,5)";
		model.addAttribute("notAccount",tBillInfoService.countBillInfo(billInfo,billInfoStatus));//未记账
		//初次加载页面的新建客户的信息为空
		
		//是否是第一次登陆 add by wub 20160106
		;
		model.addAttribute("firstLoad",voucherService.firstLoad(customerObject.getId()));//是否第一次登陆
		return "modules/voucherIndex";
	}
	@RequiresPermissions("user")
	@RequestMapping(value = "voucherIndexx")
	//@Transactional 
	public String voucherIndexx(TCustomer tCustomer,Model model,HttpSession session,
			HttpServletRequest request,HttpServletRequest response) throws ParseException {
		User currentUser = UserUtils.getUser();
		//根据用户的id获取管理的公司
		List<TCustomer> listcustomer=tCustomerService.findBySupervisor(currentUser.getId());
		
		
		//获取页面中传递过来的id
		String customerid="";
		//获取页面的session中的sessionCustomer对象
		
		TCustomer customerObject=(TCustomer)session.getAttribute("sessionCustomer");
		//customerObject.getCurrentperiod();//获得当前账期
		
		//System.out.println(customerids+"杨晓东杨晓东");
		//String customerids="1";
		//陈明加的
		String result="0";
		try{
			String aa= request.getHeader("referer");
			if(aa!=null){
					result="1";
			}
						
		}catch(Exception e){
			System.out.println("输入的地址错误!");
		}finally{
			/*if("0".equals(result)){
				return "modules/voucherIndexx";
			}*/
		}
		
		TCustomer tCustomerr=null;
		if("1".equals(result)){	
			String customeridd=request.getParameter("hrefid");
			/**
			 * 如果没有切换公司前，获取的是浏览器上面有的这个客户sessionCustomer对象
			 * 如果获取的对象为空，也没有切换公司的时候，采用的是用户管理下的第一个客户
			 */
			if(customeridd==null||customeridd.equals("")){
				if(customerObject==null||customerObject.equals("")){
					customerid=listcustomer.get(0).getId();
					System.out.println(customerid+"yxdyxdyxd");
				}else{
					//判断当前账期是否为空或者长度<1,如果公司第一次进入在线会计当前账期为空
					
					String   currentperiod=customerObject.getCurrentperiod();//获得当前账期
					if(currentperiod==null||currentperiod==""||currentperiod.length()<1){
						String initperiod=customerObject.getInitperiod();
						String customerids=customerObject.getId();
						TCustomer tc=new TCustomer();
						tc.setId(customerids);
						tc.setCurrentperiod(initperiod);
						tCustomerService.updatecurrentPeriod(tc);//将当前账期赋值为起始账期
						   
						Map<String, Object> map = new HashMap<String, Object>();			
						map.put("fdbid",customerids); 
						map.put("systemNumber", customerObject.getSystem());
						map.put("rtn","");
						String basedata=systemService.inputbasedata(map);
						basedata=map.get("rtn").toString();
						customerid=customerids;
					}else{//不为null等
						String customerids=customerObject.getId();
						customerid=customerids;
					}
					}
				
			}else {
			  customerid=customeridd;
			}
			//通过客户id获得当前客户的名字
			TCustomer customer=new TCustomer();
			tCustomerr=tCustomerService.get(customerid);
		}else{
			tCustomerr=customerObject;
		}
		System.out.println(tCustomerr.getCurrentperiod()+"当前账期");
		String customername=tCustomerr.getCustomerName();
		String customerId=tCustomerr.getId();
		//改变传递页面的session的sessionCustomerId		
		session.setAttribute("sessionCustomer", tCustomerr);
		
		
		//获取本公司的记账人
		String rolesName=currentUser.getRoleNames();
		List<User> userlist=new ArrayList<User>();
		User userr;
		if(rolesName.contains("记账公司管理员")){
			 userlist=systemService.findUserByCompanyid(currentUser);
		}else if(rolesName.equals("记账员")){
			userlist.add(currentUser);
		}
		model.addAttribute("userlist", userlist);
		model.addAttribute("customername", customername);
		model.addAttribute("customerId", customerId);
		model.addAttribute("listcustomer", listcustomer);
		//model.addAttribute("tCustomer",customer);
		//用于弹出页面显示的
		//model.addAttribute("listcustomer", tCustomerService.findBySupervisor(currentUser.getId()));
		model.addAttribute("followUp",new FollowUp());
		model.addAttribute("tCustomer", tCustomer);
		model.addAttribute("tCustomerr", tCustomerr);
		model.addAttribute("currentUser",currentUser);
		
		return "modules/voucherIndexx";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = "againinputbasedata")
	public String againinputbasedata(Model model,HttpSession session){
			TCustomer customerObject=(TCustomer)session.getAttribute("sessionCustomer");
			String customerids=customerObject.getId();
			TCustomer tc=new TCustomer();
		
			tCustomerService.updatecurrentPeriod(tc);//将当前账期赋值为起始账期
			   
			Map<String, Object> map = new HashMap<String, Object>();			
			map.put("fdbid",customerids); 					      
			map.put("rtn","");
			String basedata=systemService.againinputbasedata(map);
			basedata=map.get("rtn").toString();
		return "redirect:"+Global.getAdminPath()+"/yunzhmainsypt";
		
	}
	
	
	@RequiresPermissions("user")
	@RequestMapping(value = "voucherIndexJson")
	@ResponseBody
	public Object voucherIndexJ(String compay,String time,Model model,HttpServletRequest request,HttpSession session) throws ParseException {
		TBillInfo billInfo = new TBillInfo();
		//公司名 传递或者session中获取
		TCustomer customerinfo=(TCustomer)session.getAttribute("sessionCustomer");
		String customerId = customerinfo.getId();
		//billInfo.setCustomerId(customerId);
		
		//compay = "北京西贝万家餐饮公司";
		compay = customerinfo.getCustomerName();
		billInfo.setPayName(compay);
		//账期(yyyyMM) 传递或者session中获取
	//	String updateDate = "2015" + time;
		/*SimpleDateFormat sdFormat = new  SimpleDateFormat("yyyyMM");
		billInfo.setUpdateDate(sdFormat.parse(updateDate));*/
		
		request.getSession().setAttribute("user_period", time);
		
		billInfo.setUploadPeriod(time);
		
		int uploadNotes = tBillInfoService.countBillInfo(billInfo,null);
		String billInfoStatus = "(1,2,3,4,5)";
		int processed = tBillInfoService.countBillInfo(billInfo,billInfoStatus);
		billInfoStatus = "(3)";
		int accounted = tBillInfoService.countBillInfo(billInfo,billInfoStatus);
		billInfoStatus = "(1,2,4,5)";
		int notAccount =tBillInfoService.countBillInfo(billInfo,billInfoStatus);
		return ImmutableMap.of("uploadNotes", uploadNotes,"processed", processed,"accounted", accounted,"notAccount", notAccount);
	}
	/**
	 * 保存总公司下面的管理员
	 * @param model
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveUserBykh")
	public String saveUserBykh(Model model,HttpSession session,
			HttpServletRequest request,HttpServletRequest response){
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			String companytname=request.getParameter("companytname");
			//companytname = new String(companytname.getBytes("ISO8859-1"),"UTF-8");//客户名称
			String username=request.getParameter("username");
			
			String mobile=request.getParameter("mobile");
			
			String password=request.getParameter("password");
			
			String confirm_password=request.getParameter("confirm_password");
			
			System.out.println(companytname+"用户名");
			User user=new User();
			user.setName(companytname);
			user.setLoginName(username);
			user.setNewPassword(password);
			user.setMobile(mobile);
			Office office=new Office();
			office.setId("1");
			user.setOffice(office);
			user.setCompany(office);
			user.setCreateBy(UserUtils.getUser());
			System.out.println(UserUtils.getUser().getName());
			//if这句话用于加密的
			if (StringUtils.isNotBlank(user.getNewPassword())) {
				user.setPassword(SystemService.entryptPassword(user
						.getNewPassword()));
			}
			
			String[] roleIdArray={"41cfc68a551f4732adc3e18ba6754842"};//角色是芸豆管理员
			// 角色数据有效性验证，过滤不在授权内的角色,注意在不是这样角色登陆下的用户是插入不进来的
			List<Role> roleList = Lists.newArrayList();
			List<String> roleIdList =  new ArrayList<String>();
			Collections.addAll(roleIdList, roleIdArray);//所有指定元素添加到指定集合
			/*for (Role r : systemService.findAllRole()) {
			//	if (roleIdList.contains(r.getId())) {
					roleList.add(r);
				//}
			}*/
			//Role role=systemService.getRole(roleIdArray[0]);
			Role role=new Role(roleIdArray[0]);
			roleList.add(role);
			user.setRoleList(roleList);
				// 保存用户信息
			System.out.println("---------------");
			
			systemService.saveUser(user);//调用许小龙的方法
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("来了吗");
		//return "modules/adminback/usermanage";
		//保存成功后天跳转到可以显示列表的地方
		return "redirect:"+Global.getAdminPath()+"/sys/user/usermanage";
		
	}
	//用户管理列表
	@RequiresPermissions("user")
	@RequestMapping(value = "usermanage")
	//@ResponseBody
	public String usermanage(Model model,HttpSession session,
			HttpServletRequest request,HttpServletRequest response){

		//List<User> userList=systemService.findByOfficeId("1");
		List<User> userList=systemService.findAll();
//		Date da=(Date)userList.get(1).getCreateDate();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String startTime = sdf.format(da); 
		//User userr=new User();
		for (User user : userList) {
			Date da=user.getCreateDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String startTime = sdf.format(da); 
			
			user.setCreateDateString(startTime);
			//userr=systemService.getUser(user.getCreateBy().getId());
			User use=systemService.getUser(user.getCreateBy().getId());
			if(use!=null){
				user.setCreateByString(use.getName());
			}
		}
		//model.addAttribute("userr", userr);
		//System.out.println(userList.get(1).getCreateDate()+"这就是时间");
		//System.out.println(startTime+"转化后的时间");
		model.addAttribute("userList", userList);
		return "modules/adminback/usermanage";
	}
	//更改用户的状态
	@RequiresPermissions("user")
	@RequestMapping(value = "updateStatus")
	//@ResponseBody
	public String updateStatus(Model model,HttpSession session,
			HttpServletRequest request,HttpServletRequest response){
        
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		System.out.println(id+"这就是id");
		System.out.println(status+"这就是状态");
		String  loginflagString="";
		if(status.equals("1")){
			loginflagString="0";
		}else {
			loginflagString="1";
		}
		User user=systemService.getUser(id);
		//User user=new User();
		//user.setId(id);
		user.setUseStatus(status);
		user.setLoginFlag(loginflagString);
		systemService.updateStatus(user);
		
		return "redirect:"+Global.getAdminPath()+"/sys/user/usermanage";
	
	}
	
	@RequestMapping(value = "forms")//给表单赋值
	@ResponseBody
	public User forms(Model model,HttpSession session,
			HttpServletRequest request,HttpServletRequest response){
		String id=request.getParameter("id");
		User tc=systemService.getUser(id);
		return tc;
	}
	@RequestMapping(value = "fillpower")//填充权限复选框,返回的是list
	@ResponseBody
	public List<TYdSysMenu> fillpower(Model model,HttpSession session,
			HttpServletRequest request,HttpServletRequest response){
		String id=request.getParameter("id");
		User tc=new User();
		tc.setId(id);
		List<TYdSysMenu> tyList=tYdSysMenuService.findPower(new TYdSysMenu(tc));//得到权限列表
		
		return tyList;
	}
	
	@RequestMapping(value = "editUser")//编辑管理员
	//@ResponseBody
	public String editUser(Model model,HttpSession session,
			HttpServletRequest request,HttpServletRequest response){
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			String id=request.getParameter("id");
			System.out.println(id+"得到id了哦");
			String companytname=request.getParameter("name");
			//companytname = new String(companytname.getBytes("ISO8859-1"),"UTF-8");//客户名称
			String username=request.getParameter("loginName");
			
			String mobile=request.getParameter("mobile");
			
			String password=request.getParameter("password");
			
			System.out.println(companytname+"用户名");
			//User user=new User();
			User user=systemService.getUser(id);
			//user.setId(id);
			user.setName(companytname);
			user.setLoginName(username);
			user.setNewPassword(password);
			user.setMobile(mobile);
			/*Office office=new Office();
			office.setId("1");
			user.setOffice(office);
			user.setCompany(office);
			user.setCreateBy(UserUtils.getUser());
			System.out.println(UserUtils.getUser().getName());
			//if这句话用于加密的
			//System.out.println(user.getNewPassword()+"为空吗");*/
			if (StringUtils.isNotBlank(user.getNewPassword())) {
				user.setPassword(SystemService.entryptPassword(user
						.getNewPassword()));
				//System.out.println("走这里吗为空的话");
			}
			//添角色
			/*String[] roleIdArray={"41cfc68a551f4732adc3e18ba6754842","94b5b44a17144149bf52f02159137e6c","6dc779e02cc347ef96d99955305e28bb"};
			// 角色数据有效性验证，过滤不在授权内的角色
			List<Role> roleList = Lists.newArrayList();
			List<String> roleIdList =  new ArrayList<String>();
			Collections.addAll(roleIdList, roleIdArray);//所有指定元素添加到指定集合
			for (Role r : systemService.findAllRole()) {
				if (roleIdList.contains(r.getId())) {
					roleList.add(r);
				}
			}
			user.setRoleList(roleList);
				// 保存用户信息
			System.out.println("---------------");
			*/
			/*String[] roleIdArray={"41cfc68a551f4732adc3e18ba6754842"};//角色是芸豆管理员
			// 角色数据有效性验证，过滤不在授权内的角色,注意在不是这样角色登陆下的用户是插入不进来的
			List<Role> roleList = Lists.newArrayList();
			List<String> roleIdList =  new ArrayList<String>();
			Collections.addAll(roleIdList, roleIdArray);//所有指定元素添加到指定集合
			
			Role role=systemService.getRole(roleIdArray[0]);
			roleList.add(role);
			user.setRoleList(roleList);*/
			systemService.saveUser(user);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("来了吗");
		
		return "redirect:"+Global.getAdminPath()+"/sys/user/usermanage";
	}
	
	
	/*@RequestMapping(value = "upHeadImg")
	@ResponseBody
	public String upHeadImg(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException{
		
		String fileName="";
		String result="1";
		try{
			org.springframework.web.multipart.MultipartHttpServletRequest  mRequest = (MultipartHttpServletRequest)(request);  
			//解析器解析request的上下文
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			
			//先判断request中是否包涵multipart类型的数据，
			 if(multipartResolver.isMultipart(request)){
				 
				//再将request中的数据转化成multipart类型的数据modifyPwd
				 MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				 //迭代器
				 Iterator iter = multiRequest.getFileNames();
				 
				 while(iter.hasNext()){
					    MultipartFile file = multiRequest.getFile((String)iter.next());
					    if(file != null){
					     fileName = file.getOriginalFilename();
					     //解决文件冲突
					     //  下面的加的日期是为了防止上传的名字一样
	     				//String path2= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;
					    // String path = Global.getpeopleheadimg()+File.separator+fileName;// "F:/" + fileName;
					    // File localFile = new File(path);
					     //写文件到本地
					    // file.transferTo(localFile);
					     Map<String,Object> fileMap=FileUtils.upload(file,Global.getpeopleheadimg(),mRequest);
					     fileName=fileMap.get("newName").toString();
					    }
				   }
			 }
		}catch(Exception e){	
			e.printStackTrace();
			result="0";
		}
		if("1".equals(result)){
			 User user = UserUtils.getUser();
			 user.setPhoto(fileName);
			 systemService.updateUserPhoto(user);
		}
//		PrintWriter out=response.getWriter();
	//String json="{_raw:{json:\"\1\"}}";
//		out.write(json);
		return "123";
}
	*/
	@RequestMapping(value = "comparepwd")//修改个人信息
	@ResponseBody
	public String comparepwd(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		User currentUser = UserUtils.getUser();
		String result="1";
		String passwordsql=currentUser.getPassword();
		String acceptpwdString=request.getParameter("pwd");
		if (StringUtils.isNotBlank(passwordsql)) {
			boolean flag = systemService.validatePassword(acceptpwdString,passwordsql);
			if(flag){
				result="0";
			}
		}

		return result;
	}
	@RequestMapping(value = "upHeadImg")
	@ResponseBody
	public String upHeadImg(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException{
		
		String fileName="";
		String result="1";
		try{
			org.springframework.web.multipart.MultipartHttpServletRequest  mRequest = (MultipartHttpServletRequest)(request);  
			//解析器解析request的上下文
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			
			//先判断request中是否包涵multipart类型的数据，
			 if(multipartResolver.isMultipart(request)){
				 
				//再将request中的数据转化成multipart类型的数据modifyPwd
				 MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				 //迭代器
				 Iterator iter = multiRequest.getFileNames();
				 
				 while(iter.hasNext()){
					    MultipartFile file = multiRequest.getFile((String)iter.next());
					    if(file != null){
					     fileName = file.getOriginalFilename();
					     //解决文件冲突
					     //  下面的加的日期是为了防止上传的名字一样
	     				//String path2= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;
					    // String path = Global.getpeopleheadimg()+File.separator+fileName;// "F:/" + fileName;
					    // File localFile = new File(path);
					     //写文件到本地
					    // file.transferTo(localFile);
					     Map<String,Object> fileMap=FileUtils.upload(file,Global.getpeopleheadimg(),mRequest);
					     fileName=fileMap.get("newName").toString();
					    }
				   }
			 }
		}catch(Exception e){	
			e.printStackTrace();
			result="0";
		}
		if("1".equals(result)){
			 User user = UserUtils.getUser();
			 user.setPhoto(fileName);
			 systemService.updateUserPhoto(user);
		}

		//return "123";
		return fileName;
}
	@RequestMapping(value = "imageGet")
	public void imageGet(String fileName,HttpServletResponse response){
        FileInputStream fis = null;
        response.setContentType("image/gif");
        try {
            OutputStream out = response.getOutputStream();
            File file = new File(Global.getpeopleheadimg()+File.separator+fileName);
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
