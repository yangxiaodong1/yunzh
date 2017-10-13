/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.download.DownloadController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.workterrace.entity.ExcelTemplate;
import com.thinkgem.jeesite.modules.workterrace.entity.TCustomerSalary;
import com.thinkgem.jeesite.modules.workterrace.service.TCustomerSalaryService;

/**
 * 工资Controller
 * @author xuxiaolong
 * @version 2015-12-29
 */
@Controller
@RequestMapping(value = "${adminPath}/workterrace/tCustomerSalary")
public class TCustomerSalaryController extends BaseController {
	@Autowired
	private DownloadController downloadController;
	@Autowired
	private TCustomerSalaryService tCustomerSalaryService;
	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private SystemService systemService;
	@ModelAttribute
	public TCustomerSalary get(@RequestParam(required=false) String id) {
		TCustomerSalary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCustomerSalaryService.get(id);
		}
		if (entity == null){
			entity = new TCustomerSalary();
		}
		return entity;
	}
	
	//@RequiresPermissions("workterrace:tCustomerSalary:view")
	@RequestMapping(value = {"list"})
	public String list(TCustomerSalary tCustomerSalary,RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) {
		String isok=request.getParameter("isok");
		System.out.println(isok);
		User currentUser = UserUtils.getUser();
		//获取本公司的记账人
		String rolesName=currentUser.getRoleNames();
		List<User> userlist=new ArrayList<User>();
		if(rolesName.contains("记账公司管理员")){
			 userlist=systemService.findUserByCompanyid(currentUser);
		}else if(rolesName.equals("记账员")){
			userlist.add(currentUser);
		}
		
		model.addAttribute("userlist", userlist);
		model.addAttribute("tCustomerSalary", tCustomerSalary);
		model.addAttribute("isok", isok);
		return "modules/workterrace/tCustomerSalaryList";
	}
	
	public static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
	@RequestMapping(value = {"GetExcelByTemplate"})
	public void GetExcelByTemplate(TCustomerSalary tCustomerSalary,HttpServletRequest request, HttpServletResponse response) {
		String docsPath = request.getSession().getServletContext()
				.getRealPath("docs");
		String templateName = "CustomerSalarytemplate.xls";// Excel模版
		String templatePath = docsPath + FILE_SEPARATOR + templateName;
		
		
		ExcelTemplate excel = ExcelTemplate.getInstance().readTemplatePath(templatePath);
		List<TCustomerSalary> tCustomerSalaryList = tCustomerSalaryService.findList(tCustomerSalary); 
		for(TCustomerSalary tcs:tCustomerSalaryList){
			excel.creatNewRow();
			
			// EmployeeName;		// 姓名
			excel.createNewCol(tcs.getEmployeeName());
			// IdentityType;		// 身份证件类型
			excel.createNewCol(tcs.getIdentityType());
			//  IdNumber;		// 身份证件号码
			excel.createNewCol(tcs.getIdNumber());
			// IncomeProject;		// 所得项目
			excel.createNewCol(tcs.getIncomeProject());
			//  IncomePeriod;		// 所得期间
			excel.createNewCol(tcs.getIncomePeriod());
			//  IncomeMoney;		// 收入额
			excel.createNewCol(tcs.getIncomeMoney());
			// TaxFreeIncome;		// 免税所得
			excel.createNewCol(tcs.getTaxFreeIncome());
			//  EndowmentInsurance;		// 基本养老保险费
			excel.createNewCol(tcs.getEndowmentInsurance());
			//  MedicalInsurance;		// 基本医疗保险费
			excel.createNewCol(tcs.getMedicalInsurance());
			//  UnemploymentInsurance;		// 失业保险费
			excel.createNewCol(tcs.getUnemploymentInsurance());
			//  HouseFund;		// 住房公积金
			excel.createNewCol(tcs.getHouseFund());
			//  OriginalValue;		// 财产原值
			excel.createNewCol(tcs.getOriginalValue());
			//  AllowableDeductions;		// 允许扣除的税费
			excel.createNewCol(tcs.getAllowableDeductions());
			//  OtherDeduction;		// 其他
			excel.createNewCol(tcs.getOtherDeduction());
			//  TotalDeduction;		// 合计
			excel.createNewCol(tcs.getTotalDeduction());
			//  DeductionExpenses;		// 减除费用
			excel.createNewCol(tcs.getDeductionExpenses());
			//  DeductibleDonationAmount;		// 准予扣除的捐赠额
			excel.createNewCol(tcs.getDeductibleDonationAmount());
			//  ShouldPayIncome;//应纳税所得额#
			excel.createNewCol(tcs.getShouldPayIncome());
			//  TaxRate;//税率%#
			excel.createNewCol(tcs.getTaxRate());
			// QuickDeduction;//速算扣除数#
			excel.createNewCol(tcs.getQuickDeduction());
			// ShouldPay;//应纳税额#
			excel.createNewCol(tcs.getShouldPay());
			// TaxRelief;//减免税额
			excel.createNewCol(tcs.getTaxRelief());
			// ShouldBucklePay;//应扣缴税额#
			excel.createNewCol(tcs.getShouldBucklePay());
			// HasBeenWithholdingTax;//已扣缴税额
			excel.createNewCol(tcs.getHasBeenWithholdingTax());
			// ShouldRepairPay;//应补（退）税额
			excel.createNewCol(tcs.getShouldRepairPay());
			//  Remarks1;		// 备注
			excel.createNewCol(tcs.getRemarks1());
		}
		
		TCustomer customer= tCustomerService.get(tCustomerSalary.getCustomerId());
		String customerName=customer.getCustomerName();
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMM");
		String salaryPeriod=tCustomerSalary.getSalaryPeriod();
		try {
			rightNow.setTime(simpleDate.parse(salaryPeriod));
		} catch (ParseException e) {
			e.printStackTrace();
		} //要计算你想要的月份，改变这里即可
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		salaryPeriod=salaryPeriod.substring(0,4)+"年"+salaryPeriod.substring(4,6)+"月";
		String beginDate=salaryPeriod+"01日";
		String endDate=salaryPeriod+days+"日";
		String fileName = customerName+beginDate+"到"+endDate+"工资信息.xls";// 导出Excel文件名
		String filePath = docsPath + FILE_SEPARATOR + fileName;
		
		Map<String, String> datas = new HashMap<String, String>();
		datas.put("customerName", customerName);
		datas.put("stateTaxRegistrationNumber", customer.getStateTaxRegistrationNumber());
		datas.put("beginDate", beginDate);
		datas.put("endDate", endDate);
		excel.replaceFind(datas);
		excel.insertSer();
		excel.writeToFile(filePath);
		downloadController.download(filePath, response);
	
	}
	
	@ResponseBody
	@RequestMapping(value = {"tCustomerSalaryList"})
	public List<TCustomerSalary> tCustomerSalaryList(TCustomerSalary tCustomerSalary,RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<TCustomerSalary> tCustomerSalaryList = tCustomerSalaryService.findList(tCustomerSalary); 
		if(tCustomerSalaryList.size()<1){
			String customeridString=tCustomerSalary.getCustomerId();
			String newspd=tCustomerSalary.getSalaryPeriod();
			TCustomerSalary tCustomerSalary2=tCustomerSalaryService.findListBymax(tCustomerSalary);
		    try {
		    	tCustomerSalary2.getSalaryPeriod();
		    	Map<String, Object> map = new HashMap<String, Object>();			
				map.put("cid",customeridString); 
				map.put("oldspd",tCustomerSalary2.getSalaryPeriod());	
				map.put("newspd",newspd);	
				map.put("rtn","");
				String result=tCustomerSalaryService.copyCustomerSalary(map);
		    	tCustomerSalaryList = tCustomerSalaryService.findList(tCustomerSalary);
			} catch (Exception e) {
			}
		}
		int tCustomerSalaryListSize=tCustomerSalaryList.size();
		if(tCustomerSalaryListSize>0){
			int i=1;
			for(TCustomerSalary tcs:tCustomerSalaryList){
				tcs.setOrdernum(i);
				i++;
			}
			
		}
		tCustomerSalary.setOrdernum(tCustomerSalaryListSize+1);
		tCustomerSalaryList.add(tCustomerSalary);
		return tCustomerSalaryList;
	}
	

	//@RequiresPermissions("workterrace:tCustomerSalary:view")
	@RequestMapping(value = "form")
	public String form(TCustomerSalary tCustomerSalary, Model model) {
		model.addAttribute("tCustomerSalary", tCustomerSalary);
		return "modules/workterrace/tCustomerSalaryForm";
	}

	//@RequiresPermissions("workterrace:tCustomerSalary:edit")
	@RequestMapping(value = "save")
	public String save(TCustomerSalary tCustomerSalary, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCustomerSalary)){
			return form(tCustomerSalary, model);
		}
		tCustomerSalaryService.save(tCustomerSalary);
		addMessage(redirectAttributes, "保存工资成功");
		return "redirect:"+Global.getAdminPath()+"/workterrace/tCustomerSalary/?repage";
	}
	@ResponseBody
	@RequestMapping(value = "savejson")
	public String savejson(@RequestBody List<TCustomerSalary> tCustomerSalarys) {
		String res="0";
		int tCustomerSalarysSize= tCustomerSalarys.size();
		if(tCustomerSalarysSize>2){
			TCustomerSalary ts= tCustomerSalarys.get(tCustomerSalarysSize-1);
			tCustomerSalarys.remove(tCustomerSalarysSize-1);
			tCustomerSalarys.add(0, ts);
			
			
		}
			for(TCustomerSalary tCustomerSalary: tCustomerSalarys){
				if (!"".equals(tCustomerSalary.getEmployeeName())&&null!=tCustomerSalary.getEmployeeName()) {
					if(null==tCustomerSalary.getId()||"".equals(tCustomerSalary.getId())){
						tCustomerSalary.setId(IdGen.uuid());
						tCustomerSalary.setSalaryDate(new Date());
					}
					tCustomerSalaryService.save(tCustomerSalary);
				}
				res="1";
			}
	
		return res;
	}
	
	//@RequiresPermissions("workterrace:tCustomerSalary:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TCustomerSalary tCustomerSalary) {
		String i="0";
		try {
			tCustomerSalaryService.delete(tCustomerSalary);
			i="1";
		} catch (Exception e) {
			// TODO: handle exception
			i="0";
		}
		return i;
		
	}

}