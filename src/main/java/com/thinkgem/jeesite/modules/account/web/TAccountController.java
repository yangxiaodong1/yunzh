/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.web;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.common.collect.Lists;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.entity.AccountAndamortize;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.entity.TaccountNext;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.account.service.TaccountGroupService;
import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
import com.thinkgem.jeesite.modules.amortize.service.TAmortizeService;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.chineseCharToEn.ChineseCharToEnService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.download.DownloadBalance;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucherexp.entity.TVoucherExp;
import com.thinkgem.jeesite.modules.voucherexp.service.TVoucherExpService;

/**
 * 科目信息操作Controller
 * 
 * @author 陈明
 * @version 2015-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/account/tAccount")
public class TAccountController extends BaseController {

	// 初始余额的services
	@Autowired
	private TBalanceService tbalanceService;
	// 科目的services
	@Autowired
	private TAccountService tAccountService;
	// 科目分组的servics
	@Autowired
	private TaccountGroupService tAccountGroupService;
	// 客户的Service
	@Autowired
	private TCustomerService customerService;
	// 到处初始余额的样式
	@Autowired
	private DownloadBalance downloadBalance;
	// 摊销
	@Autowired
	private TAmortizeService tAmortizeService;
	// 凭证
	@Autowired
	private TVoucherExpService tVoucherExpService;
	@Autowired
	private ChineseCharToEnService chineseCharToEnService;

	public static final String FILE_SEPARATOR = System.getProperties()
			.getProperty("file.separator");

	@ModelAttribute
	public TAccount get(@RequestParam(required = false) String id) {
		TAccount entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = tAccountService.get(id);
		}
		if (entity == null) {
			entity = new TAccount();
		}
		return entity;
	}

	public TAccount getAccountid(String id, String fdbid) {
		TAccount parentInfoByIdlist = new TAccount();
		if (id != null && !"".equals(id)) {
			parentInfoByIdlist = tAccountService.get(id);
			List<String> list = tAccountService.countNumber(
					parentInfoByIdlist.getId(), fdbid);
			if (list != null && list.size() > 0) {
				System.out.println(list.size());
				String maxNumber = Collections.max(list);
				maxNumber = maxNumber.substring(maxNumber.length() - 2,
						maxNumber.length());
				int num = Integer.parseInt(maxNumber);
				String j = "0";
				for (int n = 1; n <= num; n++) {
					int m = 1;
					for (int i = 1; i <= list.size(); i++) {
						String st = list.get(i - 1);
						String str = st.substring(st.length() - 2, st.length());
						if (Integer.parseInt(str) < 10) {
							str = str.substring(str.length() - 1, str.length());
							if (str.equals("" + n + "")) {
								break;
							}
						} else {
							if (str.equals("" + n + "")) {
								break;
							}
						}
						m++;
					}
					System.out.println("m" + m);
					if (m - 1 == list.size()) {
						if (n < 10) {
							parentInfoByIdlist
									.setAccuntIdfor(parentInfoByIdlist
											.getAccuntId() + "0" + n);
							j = "1";
							break;
						} else {
							parentInfoByIdlist
									.setAccuntIdfor(parentInfoByIdlist
											.getAccuntId() + n);
							j = "1";
							break;
						}
					}
				}
				if (j.equals("0")) {
					int len = num + 1;
					if (len < 10) {
						parentInfoByIdlist.setAccuntIdfor(parentInfoByIdlist
								.getAccuntId() + "0" + len);
					} else {
						parentInfoByIdlist.setAccuntIdfor(parentInfoByIdlist
								.getAccuntId() + len);
					}
				}
			} else {
				parentInfoByIdlist.setAccuntIdfor(parentInfoByIdlist
						.getAccuntId() + "01");
			}
		}
		return parentInfoByIdlist;
	}

	/**
	 * 显示科目信息
	 */
	// @RequiresPermissions("account:tAccount:view")
	@RequestMapping(value = { "list", "" })
	public String list(TAccount tAccount, HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			Model model) {
		/*
		 * Page<TAccount> page = tAccountService.findPage(new
		 * Page<TAccount>(request, response), tAccount);
		 * model.addAttribute("page", page);
		 */
		response.setHeader("Pragma", "No-Cache");
		response.setHeader("Cache-Control", "No-Cache");
		response.setDateHeader("Expires", 0);
		String nTop = request.getParameter("nTop");
		String nTop1 = request.getParameter("nTop1");
		String count = request.getParameter("count");// 判断科目是否导入成功
		String accountid = null;
		if (!"".equals(count) && count != null) {
			if ("1".equals(count)) {
				model.addAttribute("kemu", "导入成功");
			} else if ("3".equals(count)) {
				model.addAttribute("kemu", "余额表已有值不可以导入其他公司的科目!");
			} else if ("4".equals(count)) {
				model.addAttribute("kemu", "亲!有自定义科目,导入失败!");
			} else {
				model.addAttribute("kemu", "导入失败!");
			}
		}
		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息
		String fdbid = customerinfo.getId();
		System.out.println(fdbid);
		// 得到科目类别的编号
		String id = request.getParameter("id");
		TAccount parentInfoByIdlist = getAccountid(id, fdbid);
		List<TAccount> list = Lists.newArrayList();
		List<TAccount> sourcelist = tAccountService.findListByGroup(fdbid,
				accountid);
		TAccount.sortList(list, sourcelist, TAccount.roodid(), true);

		User currentUser = UserUtils.getUser();
		// 根据用户的id获取管理的公司
		List<TCustomer> listcustomer = customerService
				.findBySupervisor(currentUser.getId());
		// 提取客户的公司名称(在导入的下拉框中显示)
		model.addAttribute("nTop1", nTop1);
		model.addAttribute("nTop", nTop);
		model.addAttribute("customerInfo", listcustomer);
		model.addAttribute("getparent", parentInfoByIdlist);
		model.addAttribute("list", list);
		return "modules/account/tAccountList";

	}

	/**
	 * 获取上一级的信息再添加科目的时候判断是否有下级科目的ajax
	 */
	@RequestMapping(value = "parentInfo", method = RequestMethod.GET)
	public void parentInfo(PrintWriter printWriter,
			HttpServletResponse response, HttpSession session,
			HttpServletRequest request) {
		response.setHeader("Pragma", "No-Cache");
		response.setHeader("Cache-Control", "No-Cache");
		response.setDateHeader("Expires", 0);
		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息
		String fdbid = customerinfo.getId();
		System.out.println(fdbid);
		String id = request.getParameter("id");
		TAccount parentInfoById = getAccountid(id, fdbid);
		String jsonString = "";
		JsonMapper json = new JsonMapper();
		if (parentInfoById != null) {
			jsonString = json.toJsonString(parentInfoById);
		}
		printWriter.write(jsonString);
		printWriter.flush();
		printWriter.close();
	}

	/**
	 * 添加科目之前显示的一些信息
	 */
	// @RequiresPermissions("account:tAccount:view")
	@RequestMapping(value = "form")
	public String form(TAccount tAccount, Model model,
			HttpServletRequest request) {
		return "modules/account/tAccountForm";
	}

	/**
	 * 编辑科目
	 * 
	 * @throws UnsupportedEncodingException
	 */
	// @RequiresPermissions("account:tAccount:edit")
	@RequestMapping(value = "save")
	public String save(TAccount tAccount, Model model, HttpSession session,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		String accountName = java.net.URLDecoder.decode(
				request.getParameter("accountName"), "UTF-8");
		String initName = chineseCharToEnService.getFirstSpell(accountName);
		String id = tAccount.getId();
		String nTop = request.getParameter("nTop");
		String nTop1 = request.getParameter("nTop1");
		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息
		String fdbid = customerinfo.getId();
		String addid = "";
		String accuntId = request.getParameter("faccuntId");
		
		if ("".equals(accountName) || accountName == null) {
			addMessage(redirectAttributes, "科目名称不可为空!");
		} else {
			TAccount parentinfo = tAccountService.get(id);
			// 现在是第几集
			System.out.println(String.valueOf(Integer.parseInt(parentinfo
					.getLevel()) + 1));
			parentinfo.setLevel(String.valueOf(Integer.parseInt(parentinfo
					.getLevel()) + 1));
			// 给父级赋id
			TAccount info = new TAccount();
			info.setId(parentinfo.getId());
			parentinfo.setParentId(info);

			int n = 0;
			// 判断上级是否有子集
			TAccount isDetail = tAccountService.findListByAccuntId(
					parentinfo.getId(), fdbid);
			/* String accountxl = tAccountService.accountidxl(); */
			if (isDetail != null) {
				if (isDetail.getDetail().equals("1")) {					
					isDetail.setDetail("0");
					tAccountService.save(isDetail);
					n = 1;
				}
			}
			/* parentinfo.setId(accountxl); */
			parentinfo.setAccuntId(accuntId);
			parentinfo.setAccountName(accountName);
			parentinfo.setDetail("1");
			parentinfo.setIsEnable("0");
			parentinfo.setIsrateenable("1");
			parentinfo.setFdbid(fdbid);
			parentinfo.setInitName(initName);
			tAccountService.insert(parentinfo);
			addid = id;// 按回车添加用到了
			if (n == 1) {
				// 获得所有账期
				List<String> list = tbalanceService.findAllperiod(fdbid);
				/*TAccount a = tAccountService.findListByParent(accuntId, fdbid);*/
				String aid = parentinfo.getId();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("faccountid", aid);
				map.put("otherid", id);
				tAccountService.updateOtherTableAaccountid(map);
				for (int i = 0; i < list.size(); i++) {
					TBalance t = tbalanceService.cjsdxxBy(fdbid, parentinfo
							.getParentId().getId(), list.get(i));
					if (t != null) {
						t.setAccountPeriod(list.get(i));
						t.setId(null);
						/* t.setAccountId(accountxl); */
						t.setAccountId(aid);
						tbalanceService.save(t);
					}
				}				
			}
			addMessage(redirectAttributes, "保存科目信息成功");
		}
		/* } */
		return "redirect:" + Global.getAdminPath()
				+ "/account/tAccount/list?id=" + addid + "&nTop=" + nTop
				+ "&nTop1=" + nTop1;

	}

	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(TAccount tAccount, Model model, HttpSession session,
			HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		
		String fresult="0";
		try{
			String aa= request.getHeader("referer");
			if(aa!=null){
				fresult="1";
			}
						
		}catch(Exception e){
			System.out.println("输入的地址错误!");
		}finally{
			
		}
		if("1".equals(fresult)){
		
			String accountName = java.net.URLDecoder.decode(
					request.getParameter("accountName"), "UTF-8");
			String initName = chineseCharToEnService.getFirstSpell(accountName);
			String id = tAccount.getId();
			TCustomer customerinfo = (TCustomer) session
					.getAttribute("sessionCustomer");// 获得当前的客户信息
			String fdbid = customerinfo.getId();
			int result = 1;
			TAccount infoid = tAccountService.get(id);
			if (!"".equals(accountName) && accountName != null) {
				infoid.setAccountName(accountName);
				infoid.setInitName(initName);
				tAccountService.save(infoid);			
			} else {
				/* addMessage(redirectAttributes, "科目名称不可为空!"); */
				result = 0;
			}
	
			return result;
		}else{
			return "";
		}
		
		// return
		// tAccountService.findAllListByAccountIdOrName(param,customerinfo.getId());

	}

	@RequestMapping(value = "add")
	public void add(PrintWriter printWriter, HttpSession session,
			HttpServletRequest request, RedirectAttributes redirectAttribute)
			throws UnsupportedEncodingException {

		String accountName = java.net.URLDecoder.decode(
				request.getParameter("accountName"), "UTF-8");
		String id = request.getParameter("id");
		String accuntId = request.getParameter("accountid");
		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息
		String fdbid = customerinfo.getId();
		TAccount parentinfo = tAccountService.get(id);
		// 现在是第几集
		System.out.println(String.valueOf(Integer.parseInt(parentinfo
				.getLevel()) + 1));
		parentinfo.setLevel(String.valueOf(Integer.parseInt(parentinfo
				.getLevel()) + 1));
		// 给父级赋id
		TAccount info = new TAccount();
		info.setId(parentinfo.getId());
		parentinfo.setParentId(info);

		int n = 0;
		// 判断上级是否有子集
		TAccount isDetail = tAccountService.findListByAccuntId(
				parentinfo.getId(), fdbid);
		/* String accountxl = tAccountService.accountidxl(); */
		if (isDetail != null) {
			if (isDetail.getDetail().equals("1")) {				
				isDetail.setDetail("0");
				tAccountService.save(isDetail);				
				n = 1;
			}
		}
		/* parentinfo.setId(accountxl); */
		parentinfo.setAccuntId(accuntId);
		parentinfo.setAccountName(accountName);
		parentinfo.setDetail("1");
		parentinfo.setIsEnable("0");
		parentinfo.setFdbid(fdbid);
		parentinfo.setIsrateenable("1");
		String initName = chineseCharToEnService.getFirstSpell(accountName);
		parentinfo.setInitName(initName);
		int count = tAccountService.insert(parentinfo);
		/*TAccount a = tAccountService.findListByParent(accuntId, fdbid);*/
		String aid = parentinfo.getId();
		if (n == 1) {
			// 获得所有账期
			List<String> list = tbalanceService.findAllperiod(fdbid);
			/*
			 * List<TAccount>
			 * a=tAccountService.findListByParent(accuntId,fdbid);
			 */

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("faccountid", aid);
			map.put("otherid", id);
			tAccountService.updateOtherTableAaccountid(map);
			for (int i = 0; i < list.size(); i++) {
				TBalance t = tbalanceService.cjsdxxBy(fdbid, parentinfo
						.getParentId().getId(), list.get(i));
				if (t != null) {
					t.setAccountPeriod(list.get(i));
					t.setId(null);
					t.setAccountId(aid);
					tbalanceService.save(t);
				}
			}
			String cjddiq = customerinfo.getCurrentperiod();// 起始账期本年的一月份的账期
			cjddiq = cjddiq.substring(0, cjddiq.length()-2) + "01";
			TBalance tBalance = tbalanceService.cjsdxxBy(fdbid, aid, cjddiq);
			if (tBalance != null) {
				parentinfo.setIdFor(tBalance.getId());
			}
		}
		String jsonString = "";
		JsonMapper json = new JsonMapper();
		parentinfo.setId(aid);
		jsonString = json.toJsonString(parentinfo);
		System.out.println(jsonString);

		printWriter.write(jsonString);
		printWriter.flush();
		printWriter.close();

	}

	/**
	 * 删除科目
	 */
	// @RequiresPermissions("account:tAccount:edit")
	@RequestMapping(value = "delete")
	public void delete(PrintWriter printWriter, TAccount tAccount,
			HttpSession session, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		
		String fresult="0";
		try{
			String aa= request.getHeader("referer");
			if(aa!=null){
				fresult="1";
			}
						
		}catch(Exception e){
			System.out.println("输入的地址错误!");
		}finally{
			
		}
		if("1".equals(fresult)){
			
		
			/*
			 * tAccountService.delete(tAccount); addMessage(redirectAttributes,
			 * "删除科目信息成功");
			 */
			TCustomer customerinfo = (TCustomer) session
					.getAttribute("sessionCustomer");// 获得当前的客户信息
			String fdbid = customerinfo.getId();
			String nTop = request.getParameter("nTop");
			String nTop1 = request.getParameter("nTop1");
			System.out.println(fdbid);
			int result = -1;
			TAccount deleteid = new TAccount();
			TAccount isUpdat = tAccountService.get(tAccount.getId());
			if (isUpdat.getDetail().equals("0")) {
				result = 1;// 有子集不能删除
			} else {
				// 判断初始余表是否在使用
				TBalance tBalance = tbalanceService.cjsdxxBy(fdbid,
						tAccount.getId(), customerinfo.getCurrentperiod());
				if (customerinfo.getCurrentperiod().equals(
						customerinfo.getInitperiod())) {
					if (tBalance != null) {
						if (!"0.00".equals(tBalance.getBeginbalance())
								|| !"0.00".equals(tBalance.getYtdamountfor())
								|| !"0.00".equals(tBalance
										.getYtdcredittotalamount())
								|| !"0.00"
										.equals(tBalance.getYtddebittotalamount())
								|| !"0.00".equals(tBalance.getEndbalance())) {
							result = 2;// 初始余额表正在使用不能删除
						} else {
							deleteid.setId(tAccount.getId());
							tAccountService.delete(deleteid);
							int Pcound = tAccountService.parentCount(isUpdat
									.getParentId().getId(), fdbid);
							if (Pcound > 0) {
								System.out.println("不用修改");
							} else {
								TAccount isUpdatagin = tAccountService
										.findListByAccuntId(isUpdat.getParentId()
												.getId(), fdbid);
								if (isUpdatagin != null) {
									isUpdatagin.setDetail("1");
									tAccountService.save(isUpdatagin);
								}
							}
							result = 0;// "删除科目信息成功"
						}
					} else {
						deleteid.setId(tAccount.getId());
						tAccountService.delete(deleteid);
						int Pcound = tAccountService.parentCount(isUpdat
								.getParentId().getId(), fdbid);
						if (Pcound > 0) {
							System.out.println("不用修改");
						} else {
							TAccount isUpdatagin = tAccountService
									.findListByAccuntId(isUpdat.getParentId()
											.getId(), fdbid);
							if (isUpdatagin != null) {
								isUpdatagin.setDetail("1");
								tAccountService.save(isUpdatagin);
							}
						}
						result = 0;// "删除科目信息成功"
					}
	
				} else {
					if (tBalance != null) {
						result = 2;// 初始余额表正在使用不能删除
					} else {
						deleteid.setId(tAccount.getId());
						tAccountService.delete(deleteid);
						int Pcound = tAccountService.parentCount(isUpdat
								.getParentId().getId(), fdbid);
						if (Pcound > 0) {
							System.out.println("不用修改");
						} else {
							TAccount isUpdatagin = tAccountService
									.findListByAccuntId(isUpdat.getParentId()
											.getId(), fdbid);
							if (isUpdatagin != null) {
								isUpdatagin.setDetail("1");
								tAccountService.save(isUpdatagin);
							}
						}
						result = 0;// "删除科目信息成功"
					}
				}
			}
			String jsonString = "";
			JsonMapper json = new JsonMapper();
	
			jsonString = json.toJsonString(result);
			System.out.println(jsonString);
	
			printWriter.write(jsonString);
			printWriter.flush();
			printWriter.close();
		}
	}

	/**
	 * 显示初始余额的信息
	 * 
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	// @RequiresPermissions("account:tAccount:view")
	@RequestMapping(value = ("balan"))
	public String balan(TaccountNext taccountNext, HttpSession session,
			HttpServletRequest request, Model model)
			throws NumberFormatException, ParseException {
		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息
		String fdbid = customerinfo.getId();
		String nTop = request.getParameter("nTop");
		String nTop1 = request.getParameter("nTop1");
		String selectnum = request.getParameter("selectnum");

		String initperiod = customerinfo.getInitperiod();// 本公司起始账期
		String contactPerson = customerinfo.getCurrentperiod();// 当前的账期
		String cjddiq = initperiod.substring(0, initperiod.length()-2);// 起始账期本年的一月份的账期
		List<TaccountNext> list = Lists.newArrayList();
		List<TaccountNext> sourcelist = tAccountService.balanceInfo(fdbid,
				cjddiq);
		TaccountNext.sortList(list, sourcelist, TaccountNext.rootid(), true);
		// 计算平衡
		/*
		 * Map<String, Object> map = new HashMap<String, Object>();
		 * map.put("ffdbid",fdbid); map.put("period",cjddiq);
		 * map.put("sumd",""); map.put("sumj",""); Map<String, Object>
		 * m=tAccountService.blanceBlance(map); String
		 * d=map.get("sumd")==null?null:map.get("sumd").toString(); String
		 * j=map.get("sumj")==null?null:map.get("sumj").toString(); double
		 * sumd=0; double sumj=0; double ds=0; double js=0; if(d!=null &&
		 * !"".equals(d)){ ds=-Double.parseDouble(d); } if(j!=null &&
		 * !"".equals(j)){ js=Double.parseDouble(j); } if((js+ds)!=0){
		 * sumj=js/(js+ds); sumd=1-sumj; }
		 */

		model.addAttribute("nTop", nTop == null ? "0" : nTop);
		model.addAttribute("nTop1", nTop1 == null ? "0" : nTop1);
		model.addAttribute("selectnum", selectnum == null ? "0" : selectnum);
		/*
		 * model.addAttribute("sumd",sumd); model.addAttribute("sumj",sumj);
		 */
		session.setAttribute("listBalan", list);
		model.addAttribute("applyPattern", initperiod);
		model.addAttribute("dq", contactPerson);
		model.addAttribute("cjddiq", cjddiq);
		return "modules/account/tbalanceList";
	}

	/*
	 * 导出期初余额信息
	 */
	@RequestMapping(value = ("downloadBalance"))
	public void downloadBalance(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {
		String docsPath = request.getSession().getServletContext()
				.getRealPath("docs");
		String fileName = "财务初始余额表_" + System.currentTimeMillis() + ".xlsx";

		List<TaccountNext> listBalace = (ArrayList<TaccountNext>) session
				.getAttribute("listBalan");
		String filePath = docsPath + FILE_SEPARATOR + fileName;
		try {
			// 输出流
			OutputStream os = new FileOutputStream(filePath);
			// 工作区
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("财务初始余额表");
			sheet.setVerticallyCenter(true);
			/**
			 * 资产负债的宽度
			 */
			downloadBalance.cashFlowAndProfitStyleWidth(sheet);

			/*
			 * 列名
			 */
			XSSFRow rowColumn = sheet.createRow(0);

			XSSFCell cellColumn0 = rowColumn.createCell(0);
			cellColumn0.setCellStyle(downloadBalance.columnStyle(wb));
			cellColumn0.setCellValue("科目编码");
			XSSFCell cellColumn1 = rowColumn.createCell(1);
			cellColumn1.setCellStyle(downloadBalance.columnStyle(wb));
			cellColumn1.setCellValue("科目名称");
			XSSFCell cellColumn2 = rowColumn.createCell(2);
			cellColumn2.setCellStyle(downloadBalance.columnStyle(wb));
			cellColumn2.setCellValue("方向");
			XSSFCell cellColumn3 = rowColumn.createCell(3);
			cellColumn3.setCellStyle(downloadBalance.columnStyle(wb));
			cellColumn3.setCellValue("期初余额");
			int n = 1;
			for (TaccountNext pro : listBalace) {
				XSSFRow rowsInfo = sheet.createRow(n);
				// 给这一行的第一列赋值
				String projectAccountId = pro.getAccuntId();
				XSSFCell cellInfo0 = rowsInfo.createCell(0);
				cellInfo0.setCellStyle(downloadBalance.infoNameStyle(wb));
				cellInfo0.setCellValue(projectAccountId);

				String AccountName = pro.getAccountName();
				XSSFCell cellInfo1 = rowsInfo.createCell(1);
				cellInfo1.setCellStyle(downloadBalance.infoNumberStyle(wb));
				cellInfo1.setCellValue(AccountName);

				String dc = pro.getDc();
				XSSFCell cellInfo2 = rowsInfo.createCell(2);
				cellInfo2.setCellStyle(downloadBalance.infoMoneyStyle(wb));
				String fdc = "借";
				if ("-1".equals(dc)) {
					fdc = "贷";
				}
				cellInfo2.setCellValue(fdc);

				String Endbalance = pro.getBeginbalance();
				XSSFCell cellInfo3 = rowsInfo.createCell(3);
				cellInfo3.setCellStyle(downloadBalance.infoMoneyStyle(wb));
				cellInfo3.setCellValue(Endbalance);
				n++;
			}

			// 写文件
			wb.write(os);
			// 关闭输出流
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		downloadBalance.download(filePath, response);
	}

	/**
	 * 导入科目
	 */
	// @RequiresPermissions("account:tAccount:view")
	@RequestMapping(value = ("leadAccount"))
	public String leadAccount(TaccountNext taccountNext, HttpSession session,
			HttpServletRequest request, Model model) {
		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息
		String did = customerinfo.getId();// 当前公司的id
		String cid = request.getParameter("cid");// 导入其他公司的id
		String count = "";
		int isuse = tbalanceService.isAllUse(did);
		int accountOwn = tAccountService.isHaveOwnAccount(did);
		if (isuse > 0) {
			count = "3";
		} else if (accountOwn > 0) {
			count = "4";
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cid", cid);
			map.put("did", did);
			map.put("rtn", "");
			String result = tAccountService.draccount(map);
			count = (String) map.get("rtn");
		}
		return "redirect:" + Global.getAdminPath()
				+ "/account/tAccount/list?count=" + count;
	}

	@RequestMapping(value = "getAccountByCodeOrName")
	@ResponseBody
	public Object getAccountByCodeOrName(String param, HttpSession session) {

		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息
		List<TAccount> accounts = tAccountService.findAllListByAccountIdOrName(
				param, customerinfo.getId());
		if (CollectionUtils.isNotEmpty(accounts)) {
			for (TAccount tAccount : accounts) {
				List<TAccount> pareAccounts = tAccountService
						.getParentAccounts(tAccount.getParentId().getId());
				/*
				 * Collections.sort(pareAccounts,new Comparator<TAccount>(){
				 * 
				 * @Override public int compare(TAccount b1, TAccount b2) {
				 * return new Integer( b1.getAccuntId()) - new Integer(
				 * b2.getAccuntId()); }
				 * 
				 * });
				 */

				StringBuffer parentNameString = new StringBuffer();
				StringBuffer parentInitNameString = new StringBuffer();
				if (CollectionUtils.isNotEmpty(pareAccounts)) {
					/*
					 * for (TAccount parenttAccount : pareAccounts) {
					 * parentNameString.append(parenttAccount.getAccountName() +
					 * "-"); }
					 */
					for (int i = pareAccounts.size(); i > 0; i--) {
						parentNameString.append(pareAccounts.get(i - 1)
								.getAccountName() + "-");
						parentInitNameString.append(pareAccounts.get(i - 1).getInitName());
					}
				}
				tAccount.setParentName(parentNameString.toString());
				parentInitNameString.append(tAccount.getInitName());
				tAccount.setInitNameParent(parentInitNameString.toString());
			}
		}

		return accounts;
		// return
		// tAccountService.findAllListByAccountIdOrName(param,customerinfo.getId());

	}

	/**
	 * 选择科目下拉添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAccountToSelect")
	@ResponseBody
	public Object getAccountToSelect(HttpSession session) {
		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息
		List<TAccount> accounts = tAccountService.findListByGroupWithLevel(
				customerinfo.getId(), null,3);
		if (CollectionUtils.isNotEmpty(accounts)) {
			for (TAccount tAccount : accounts) {
				List<TAccount> pareAccounts = tAccountService
						.getParentAccounts(tAccount.getParentId().getId());
				/*
				 * Collections.sort(pareAccounts,new Comparator<TAccount>(){
				 * 
				 * @Override public int compare(TAccount b1, TAccount b2) {
				 * return new Integer( b1.getAccuntId()) - new Integer(
				 * b2.getAccuntId()); }
				 * 
				 * });
				 */
				StringBuffer parentNameString = new StringBuffer();
				if (CollectionUtils.isNotEmpty(pareAccounts)) {
					/*
					 * for (TAccount parenttAccount : pareAccounts) {
					 * parentNameString.append(parenttAccount.getAccountName() +
					 * "-"); }
					 */
					for (int i = pareAccounts.size(); i > 0; i--) {
						parentNameString.append(pareAccounts.get(i - 1)
								.getAccountName() + "-");						
					}
				}
				tAccount.setParentName(parentNameString.toString());				
			}
		}
		return accounts;
	}

	/**
	 * 录入凭证 保存科目
	 * 
	 * @return
	 */
	@RequestMapping(value = "saveAccountInVoucher")
	@ResponseBody
	public Object saveAccountInVoucher(HttpSession session, String accountName,
			String id) {
		TCustomer customerinfo = (TCustomer) session
				.getAttribute("sessionCustomer");// 获得当前的客户信息
		String fdbid = customerinfo.getId();

		TAccount parentinfo = tAccountService.get(id);
		// 现在是第几集
		System.out.println(String.valueOf(Integer.parseInt(parentinfo
				.getLevel()) + 1));

		parentinfo.setLevel(String.valueOf(Integer.parseInt(parentinfo
				.getLevel()) + 1));
		// 给父级赋id
		TAccount info = new TAccount();
		info.setId(parentinfo.getId());
		parentinfo.setParentId(info);

		String accuntId = "";
		List<String> listAccountId = tAccountService.countNumber(id, fdbid);
		// 科目编号自动增加
		if (listAccountId != null && listAccountId.size() > 0) {
			String maxNumber = Collections.max(listAccountId);
			maxNumber = maxNumber.substring(maxNumber.length() - 2,
					maxNumber.length());
			int num = Integer.parseInt(maxNumber);
			String j = "0";
			for (int n = 1; n <= num; n++) {
				int m = 1;
				for (int i = 1; i <= listAccountId.size(); i++) {
					String st = listAccountId.get(i - 1);
					String str = st.substring(st.length() - 2, st.length());
					if (Integer.parseInt(str) < 10) {
						str = str.substring(str.length() - 1, str.length());
						if (str.equals("" + n + "")) {
							break;
						}
					} else {
						if (str.equals("" + n + "")) {
							break;
						}
					}
					m++;
				}
				System.out.println("m" + m);
				if (m - 1 == listAccountId.size()) {
					if (n < 10) {
						accuntId = parentinfo.getAccuntId() + "0" + n;
						j = "1";
						break;
					} else {
						accuntId = parentinfo.getAccuntId() + n;
						j = "1";
						break;
					}
				}
			}
			if (j.equals("0")) {
				int len = num + 1;
				if (len < 10) {
					accuntId = parentinfo.getAccuntId() + "0" + len;
				} else {
					accuntId = parentinfo.getAccuntId() + len;
				}
			}
		} else {
			accuntId = parentinfo.getAccuntId() + "01";
		}

		int n = 0;
		// 判断上级是否有子集
		TAccount isDetail = tAccountService.findListByAccuntId(
				parentinfo.getId(), fdbid);
		/*String accountxl = tAccountService.accountidxl();*/
		if (isDetail != null) {
			if (isDetail.getDetail().equals("1")) {
				/*
				 * Map<String, Object> map = new HashMap<String, Object>();
				 * map.put("faccountid",accountxl); map.put("otherid", id);
				 * tAccountService.updateOtherTableAaccountid(map);
				 */
				isDetail.setDetail("0");
				tAccountService.save(isDetail);
				n = 1;
			}
		}
		/*parentinfo.setId(accountxl);*/
		parentinfo.setAccuntId(accuntId);
		parentinfo.setAccountName(accountName);
		parentinfo.setDetail("1");
		parentinfo.setIsEnable("0");
		parentinfo.setFdbid(fdbid);
		String initName = chineseCharToEnService.getFirstSpell(accountName);
		parentinfo.setInitName(initName);
		int count = tAccountService.insert(parentinfo);
		
		/*// 获取刚刚插入的科目id
		TAccount a = tAccountService.findListByParent(accuntId, fdbid);
		String aid = a.getId();*/
		String aid=parentinfo.getId();
		if (n == 1) {
			// 获得所有账期
			List<String> list = tbalanceService.findAllperiod(fdbid);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("faccountid", aid);
			map.put("otherid", id);
			tAccountService.updateOtherTableAaccountid(map);
			for (int i = 0; i < list.size(); i++) {
				TBalance t = tbalanceService.cjsdxxBy(fdbid, parentinfo
						.getParentId().getId(), list.get(i));
				if (t != null) {
					t.setAccountPeriod(list.get(i));
					t.setId(null);
					t.setAccountId(aid);
					tbalanceService.save(t);
				}
			}
		}

		List<TAccount> pareAccounts = tAccountService
				.getParentAccounts(parentinfo.getParentId().getId());
		Collections.sort(pareAccounts, new Comparator<TAccount>() {
			@Override
			public int compare(TAccount b1, TAccount b2) {
				return new Integer(b1.getAccuntId())
						- new Integer(b2.getAccuntId());
			}

		});
		StringBuffer parentNameString = new StringBuffer();
		if (CollectionUtils.isNotEmpty(pareAccounts)) {
			for (TAccount parenttAccount : pareAccounts) {
				parentNameString.append(parenttAccount.getAccountName() + "-");
			}
		}
		parentinfo.setParentName(parentNameString.toString());
		Map map = new HashMap();
		map.put("suc", true);
		map.put("id", aid);
		map.put("accountid", accuntId);
		map.put("dc", parentinfo.getDc());
		map.put("ifAmortize", parentinfo.getIfAmortize());
		map.put("name", accountName);
		map.put("text", accuntId + "&nbsp;" + parentinfo.getParentName()
				+ accountName);
		// return ImmutableMap.of("suc",true);
		return map;
	}
}