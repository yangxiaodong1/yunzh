/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.web.workstatistics;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.download.DownloadController;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.NumberToCN;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TServiceCharge;
import com.thinkgem.jeesite.modules.inspection.service.workstatistics.TServiceChargeService;
import com.thinkgem.jeesite.modules.stamp.StampPdf;
import com.thinkgem.jeesite.modules.stamp.TableBuilder;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 收费审核Controller
 * @author xuxiaoong
 * @version 2015-11-25
 */
@Controller
@RequestMapping(value = "${adminPath}/inspection/workstatistics/tServiceCharge")
public class TServiceChargeController extends BaseController {

	@Autowired
	private TServiceChargeService tServiceChargeService;
	@Autowired
	private TCustomerService tCustomerService;
	@Autowired
	private DownloadController downloadController;
	@Autowired
	private TableBuilder tBuilder;
	@Autowired
	private StampPdf stampPdf;
	static ScriptEngine jse = new ScriptEngineManager()
			.getEngineByName("JavaScript");
	public static final String FILE_SEPARATOR = System.getProperties()
			.getProperty("file.separator");
	public static NumberFormat moneyFormat = new DecimalFormat("#,##0.00");
	
	@ModelAttribute
	public TServiceCharge get(@RequestParam(required=false) String id) {
		TServiceCharge entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tServiceChargeService.get(id);
		}
		if (entity == null){
			entity = new TServiceCharge();
		}
		return entity;
	}
	
	//@RequiresPermissions("inspection:workstatistics:tServiceCharge:view")
	@RequestMapping(value = {"list", ""})
	public String list(TServiceCharge tServiceCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(!tServiceCharge.getStates()){
			tServiceCharge.setState("0");
		}
		List<TServiceCharge> tServiceChargeList = tServiceChargeService.findList(tServiceCharge); 
		model.addAttribute("tServiceCharge", tServiceCharge);
		model.addAttribute("tServeInfoinsert", new TServiceCharge());
		model.addAttribute("tServiceChargeList", tServiceChargeList);
		return "modules/inspection/workstatistics/tServiceChargeList";
	}
	
	//@RequiresPermissions("inspection:workstatistics:tServiceCharge:view")
	@RequestMapping(value = {"lists"})
	public String lists(TServiceCharge tServiceCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<TServiceCharge> page = tServiceChargeService.findPage(new Page<TServiceCharge>(request, response), tServiceCharge); 
		TServiceCharge tServeInfoinsert=new TServiceCharge();
		model.addAttribute("tServiceCharge", tServiceCharge);
		model.addAttribute("tServeInfoinsert", tServeInfoinsert);
		model.addAttribute("page", page);
		return "modules/workterrace/tServiceChargeList";
	}
	
	//@RequiresPermissions("inspection:workstatistics:tServiceCharge:view")
	@ResponseBody
	@RequestMapping(value = {"listpage"})
	public Page<TServiceCharge> listpage(TServiceCharge tServiceCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TServiceCharge> page = tServiceChargeService.findListBycustomerIdAndPayeeMan(new Page<TServiceCharge>(request, response), tServiceCharge); 

		return page;
	}

	//@RequiresPermissions("inspection:workstatistics:tServiceCharge:view")
	@RequestMapping(value = "form")
	public String form(TServiceCharge tServiceCharge, Model model) {
		model.addAttribute("tServiceCharge", tServiceCharge);
		return "modules/inspection/workstatistics/tServiceChargeForm";
	}

	//@RequiresPermissions("inspection:workstatistics:tServiceCharge:edit")
	@RequestMapping(value = "save")
	public String save(TServiceCharge tServiceCharge, Model model, RedirectAttributes redirectAttributes) {
	
		
		tServiceCharge.setCompanyId(UserUtils.getUser().getCompany().getId());
		String serviceChargeNo=tServiceChargeService.getMaxServiceChargeNo(tServiceCharge);
		if(serviceChargeNo==null||"".equals(serviceChargeNo)){
			serviceChargeNo=(new SimpleDateFormat("yyyyMM")).format(tServiceCharge.getSignDate());
		}
		tServiceCharge.setServiceChargeNo(serviceChargeNo);
		tServiceChargeService.save(tServiceCharge);
		addMessage(redirectAttributes, "保存收费审核成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tServiceCharge/?repage";
	}
	@ResponseBody
	@RequestMapping(value = "saves")
	public Page<TServiceCharge> saves(TServiceCharge tServiceCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		tServiceCharge.setServiceDate(tServiceCharge.getServiceDate1()+tServiceCharge.getServiceDate2()+"~"+tServiceCharge.getServiceDate3()+tServiceCharge.getServiceDate4());
		if (tServiceCharge.getIsNewRecord()){
			tServiceCharge.setPayeeMan(UserUtils.getPrincipal().getId());
			tServiceCharge.setState("0");
			tServiceCharge.setCompanyId(UserUtils.getUser().getCompany().getId());
			String serviceChargeNo=tServiceChargeService.getMaxServiceChargeNo(tServiceCharge);
			if(serviceChargeNo==null||"".equals(serviceChargeNo)){
				serviceChargeNo=new SimpleDateFormat("yyyyMM").format(tServiceCharge.getSignDate())+"0001";
			}
			tServiceCharge.setServiceChargeNo(serviceChargeNo);
		}
		try {
			tServiceChargeService.save(tServiceCharge);
			
			tServiceCharge.setPayeeMan(null);
			return tServiceChargeService.findListBycustomerIdAndPayeeMan(new Page<TServiceCharge>(request, response), tServiceCharge); 
		} catch (Exception e) {
			
		}
		return null;
		
		
	}
	
	@ResponseBody
	@RequestMapping(value = "delects")
	public Page<TServiceCharge> delects(TServiceCharge tServiceCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		tServiceCharge.setPayeeMan(UserUtils.getPrincipal().getId());
		if (UserUtils.getUser().getRoleNames().contains("记账公司管理员")){
			tServiceCharge.setPayeeMan(null);
		}
		Page<TServiceCharge> page;
		try {
			tServiceChargeService.delete(tServiceCharge);
			TCustomer tCustomer=new TCustomer();
			tCustomer.setId(tServiceCharge.getCustomerId());
			tCustomer.setServiceexpirationdate(tServiceChargeService.getMaxServicedate(tServiceCharge));
			tCustomerService.updateServiceexpirationdate(tCustomer);
			page=tServiceChargeService.findListBycustomerIdAndPayeeMan(new Page<TServiceCharge>(request, response), tServiceCharge); 
			 
		} catch (Exception e) {
			page= null;
		}
		return page;
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "forms")
	public TServiceCharge forms(TServiceCharge tServiceCharge) {
		TServiceCharge ts= tServiceChargeService.get(tServiceCharge);
		return ts;
	}
	
	
	//@RequiresPermissions("inspection:workstatistics:tServiceCharge:edit")
	@RequestMapping(value = "delete")
	public String delete(TServiceCharge tServiceCharge, RedirectAttributes redirectAttributes) {
		tServiceChargeService.delete(tServiceCharge);
		addMessage(redirectAttributes, "删除收费审核成功");
		return "redirect:"+Global.getAdminPath()+"/inspection/workstatistics/tServiceCharge/?repage";
	}
	
	//@RequiresPermissions("inspection:workstatistics:tServiceCharge:edit")
	@ResponseBody
	@RequestMapping(value = "auditing")
	public void auditing(TServiceCharge tServiceCharge, RedirectAttributes redirectAttributes) {
		tServiceCharge.setState("1");
		TCustomer tCustomer=new TCustomer();
		tCustomer.setId(tServiceCharge.getCustomerId());
		tCustomer.setServiceexpirationdate(tServiceCharge.getServiceDate3()+tServiceCharge.getServiceDate4());
		tCustomerService.updateServiceexpirationdate(tCustomer);
		
		tServiceChargeService.save(tServiceCharge);
	}
	
	@RequestMapping(value = { "stampShoujv" })
	public void stampBalance(TServiceCharge tServiceCharge,HttpServletRequest request,HttpServletResponse response, HttpSession session, Model model)	throws IOException {
		List<PdfPTable> listPdfPTable = new ArrayList<PdfPTable>();
		String title = UserUtils.getUser().getCompany().getName()+" 收据"; // 标题
		//TServiceCharge TServiceCharge=tServiceChargeService.get(id);
		String sub1 = "付款单位:"+tServiceCharge.getCustomerName(); // 上左
		String sub3 = (new SimpleDateFormat("yyyy年MM月dd日")).format(new Date()); // 上右
		
		
		String remarks = "收款人:"+tServiceCharge.getLoginName(); // 下作
		String fontsPath = request.getSession().getServletContext().getRealPath("Fonts");
		PdfPTable tableInfo = new PdfPTable(1);
		tableInfo.setWidthPercentage(100);
		PdfPTable table = null;
		PdfPTable tableSub = null;
		PdfPTable tableSub1 = null;
		PdfPTable tableRemarks = null;
		try {
			/**
			 * 2 有多少列
			 */
			table = tBuilder.createPdf(fontsPath, 4, 90, new float[] { 0.23f, 0.16f,0.05f, 0.16f});
			tableSub = tBuilder.createSub(fontsPath, sub1,"", sub3);
			tableSub1 = tBuilder.createSub(fontsPath, "","", "NO:"+tServiceCharge.getServiceChargeNo());
			tableRemarks = tBuilder	.createRemarks(fontsPath, remarks, "");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int n = 2;
		String left = "left";
		String center = "center";
		String right = "right";
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "收费项目", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "收费标准", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "金额", 0,0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "备注", 0,0));
			// 1st Row
			String assets = tServiceCharge.getServiceDate1()+"年"+tServiceCharge.getServiceDate2()+"月到"+tServiceCharge.getServiceDate3()+"年"+tServiceCharge.getServiceDate4()+"月的服务费";
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, assets,0, 0));
			String asNumber = tServiceCharge.getLoanMoney().toString();
			table.addCell(tBuilder.createValueCell(n, center, fontsPath,asNumber, 0, 0));
			String asPE = tServiceCharge.getRealityMoney().toString();
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, asPE,0, 0));
			String asYS=tServiceCharge.getRemark();
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, asYS,5, 0));
		for (n=n+1;  n<6; n++) {
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, "",0, 0));
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, "",0, 0));
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, "",0, 0));
		}
		table.addCell(tBuilder.createValueCell(n, left, fontsPath, "合计："+NumberToCN.number2CNMontrayUnit(new BigDecimal(tServiceCharge.getRealityMoney())),0, 2));
		table.addCell(tBuilder.createValueCell(n, center, fontsPath, asPE,0, 0));
		table.addCell(tBuilder.createValueCell(n, center, fontsPath, "",0, 0));
		PdfPCell cellTableSub1 = new PdfPCell (tableSub1);
		cellTableSub1.setBorderWidth(0);
		tableInfo.addCell(cellTableSub1);
		PdfPCell cellTableSub = new PdfPCell (tableSub);
		cellTableSub.setBorderWidth(0);
		tableInfo.addCell(cellTableSub);
		PdfPCell cellTable = new PdfPCell (table);
		cellTable.setBorderWidth(0);
		tableInfo.addCell(cellTable);
		PdfPCell cellTableRemarks = new PdfPCell (tableRemarks);
		cellTableRemarks.setBorderWidth(0);
		tableInfo.addCell(cellTableRemarks);
		listPdfPTable.add(tableInfo);
		// 打印
		stampPdf.createPdf(request, response, listPdfPTable, 
				title);
	}

}