/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.google.common.collect.Maps;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.cashflow.entity.TCashflow;
import com.thinkgem.jeesite.modules.cashflow.service.TCashflowService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.download.DownloadController;
import com.thinkgem.jeesite.modules.rpt.entity.EBalance;
import com.thinkgem.jeesite.modules.rpt.entity.ECashFlow;
import com.thinkgem.jeesite.modules.rpt.entity.Reportbalance;
import com.thinkgem.jeesite.modules.rpt.entity.TReportitem;
import com.thinkgem.jeesite.modules.rpt.service.ReportbalanceService;
import com.thinkgem.jeesite.modules.rpt.service.TReportitemService;
import com.thinkgem.jeesite.modules.stamp.StampPdf;
import com.thinkgem.jeesite.modules.stamp.TableBuilder;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucher.service.TVoucherService;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 现金流量表Controller
 * 
 * @author zhangtong
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/cashFlow")
public class CashFlowController extends BaseController {

	@Autowired
	private TReportitemService tReportitemService;
	@Autowired
	private ReportbalanceService reportbalanceService;
	@Autowired
	private TBalanceService tBalanceService;
	@Autowired
	private TVoucherService tVoucherService;
	@Autowired
	private TCashflowService tCashflowService;
	@Autowired
	private DownloadController downloadController;
	@Autowired
	private TableBuilder tBuilder;
	@Autowired
	private StampPdf stampPdf;
	static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
	public static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
	public static NumberFormat moneyFormat = new DecimalFormat("#,##0.00");

	/**
	 * 现金流量表
	 * 
	 * @param eBalance
	 * @param tReportitem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "listCashFlow", "" })
	public String listCashFlow(Model model) {

		return "modules/rpt/vCashFlow";
	}

	/**
	 * 现金流量调整表
	 * 
	 * @param eBalance
	 * @param tReportitem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "listCashFlowAdjustPage", "" })
	public String listCashFlowAdjustPage(String accountPeriod, String periodEnd, String periodtype, Model model) {
		model.addAttribute("accountPeriod", accountPeriod);
		model.addAttribute("periodEnd", periodEnd);
		model.addAttribute("periodtype", periodtype);
		return "modules/rpt/vCashFlowAdjust";
	}

	@RequestMapping(value = { "list", "" })
	@ResponseBody
	public Object list(String accountPeriod, String periodEnd, String periodtype, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		Map<String, Object> map = Maps.newHashMap();
		TReportitem tReportitem = new TReportitem();

		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象

		String companyId = companyInfo.getId(); // 当前公司客户id
		String companyName = companyInfo.getCustomerName(); // 公司名称
		String defaultPeriod = companyInfo.getCurrentperiod();
		int companySystem = companyInfo.getSystem(); // 1 小企业 2 新规则

		// //当前账期
		tReportitem.setFdbid(companyId); // 公司客户的id
		/**
		 * 页面第一次加载需要处理的
		 */
		if (StringUtils.isNoneEmpty(accountPeriod) && StringUtils.isNoneEmpty(periodEnd) && StringUtils.isNoneEmpty(periodtype)) {
			periodtype = "1"; // 默认的为1 月报
			tReportitem.setAccountPeriod(defaultPeriod);
			tReportitem.setPeriodEnd(defaultPeriod);
		}

		if (periodtype.equals("2")) { // 季报
			String quarter = ""; // 获取的当前季度
			// 把当前账期转换为 对应的季度
			if (accountPeriod.length() == 6) { // 切换季度和月时候判断
				int num = Integer.valueOf(defaultPeriod.substring(4, 6));
				num = getQuarter(num);
				quarter = defaultPeriod.substring(0, 4) + num + ""; // 获取的当前季度
			} else {
				quarter = accountPeriod;
			}
			String[] periodInfo = getPeriodTypeTrans(quarter); // 根据当前季度获取从第几个月到第几个月
			accountPeriod = periodInfo[0]; // 账期前
			periodEnd = periodInfo[1]; // 账期后
		}
		if (companySystem == 1) {
			tReportitem.setReportId("100020");
		} else if (companySystem == 2) {
			tReportitem.setReportId("100017");
		}
		List<TReportitem> listTReportitem = tReportitemService.findListByBalanceProfit(tReportitem); // 获取非现金流量报表信息
		List<TReportitem> listTReportitemECashFlow = tReportitemService.findList(tReportitem); // 现金流量报表信息
		List<ECashFlow> listECashFlow = listCashFlowTable(accountPeriod, periodEnd, listTReportitem, listTReportitemECashFlow, companyInfo, periodtype);// 根据账期查询返回list

		session.setAttribute("downloadCashFlow", listECashFlow);
		map.put("companyName", companyName);
		map.put("listECashFlow", listECashFlow);
		map.put("companySystem", companySystem);
		return map;

	}

	/**
	 * 打印前处理流量表
	 * 
	 * @param listECashFlow
	 */
	private List<ECashFlow> handleListECashFlow(List<ECashFlow> listECashFlow) {
		CopyOnWriteArrayList<ECashFlow> listECashFlowNew = new CopyOnWriteArrayList<ECashFlow>();
		// listECashFlowNew.addAll(listECashFlow);
		for (ECashFlow eCashFlow : listECashFlow) {

			ECashFlow ecf = new ECashFlow();
			ecf.setId(eCashFlow.getSuppId());
			ecf.setReportItem(eCashFlow.getSuppReportItem());
			ecf.setProjectName(eCashFlow.getSuppProjectName());
			ecf.setLineNumber(eCashFlow.getSuppLineNumber());
			ecf.setYearBalance(eCashFlow.getSuppYearBalance());
			ecf.setPeriodBalance(eCashFlow.getSuppPeriodBalance());
			ecf.setTitleIsOrNo(eCashFlow.isSuppTitleIsOrNo());
			ecf.setAsIsALabel(eCashFlow.isSuppAsIsALabel());
			ecf.setGroupId(eCashFlow.getSuppGroupId());
			ecf.setTcashFowId(eCashFlow.getSuppTcashFowId());
			listECashFlowNew.add(ecf);
		}
		return listECashFlowNew;
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 */
	@RequestMapping(value = { "downloadCashFlow", "" })
	public void downloadCashFlow(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		String docsPath = request.getSession().getServletContext().getRealPath("docs");
		String fileName = "现金流量表_" + System.currentTimeMillis() + ".xlsx";

		List<ECashFlow> listECashFlow = (ArrayList<ECashFlow>) session.getAttribute("downloadCashFlow");

		String filePath = docsPath + FILE_SEPARATOR + fileName;
		try {
			// 输出流
			OutputStream os = new FileOutputStream(filePath);
			// 工作区
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("现金流量表");
			sheet.setVerticallyCenter(true);
			/**
			 * 资产负债的宽度
			 */
			downloadController.cashFlowAndProfitStyleWidth(sheet);
			/*
			 * 标题
			 */
			XSSFRow rowTitle = sheet.createRow(0);
			XSSFCell cellTitle = rowTitle.createCell(0);
			cellTitle.setCellStyle(downloadController.cashFlowHeaderStyle(wb, sheet));
			cellTitle.setCellValue("现金流量表");
			/*
			 * 副标题
			 */
			XSSFRow rowSubTitle = sheet.createRow(1);

			TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
			int companySystem = companyInfo.getSystem(); // 1 小企业 2 新规则
			String companyName = companyInfo.getCustomerName();
			XSSFCell cellSubTitle0 = rowSubTitle.createCell(0);
			cellSubTitle0.setCellStyle(downloadController.cashFlowAndProfitSubHeaderStyle1(wb, sheet));
			cellSubTitle0.setCellValue(companyName);

			String accountPeriod = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
			String periodtype = new String(request.getParameter("periodtype").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
			if (periodtype.equals("1")) {
				accountPeriod = accountPeriod.substring(0, 4) + "年" + accountPeriod.subSequence(4, 6) + "期";
			} else {
				accountPeriod = accountPeriod.substring(0, 4) + "年" + accountPeriod.subSequence(4, 5) + "季度";
			}

			XSSFCell cellSubTitle3 = rowSubTitle.createCell(1);
			cellSubTitle3.setCellStyle(downloadController.cashFlowAndProfitSubHeaderStyle2(wb, sheet));
			cellSubTitle3.setCellValue(accountPeriod);

			String fur = "单位：元"; // 获取货币种类
			XSSFCell cellSubTitle5 = rowSubTitle.createCell(2);
			cellSubTitle5.setCellStyle(downloadController.cashFlowAnProfitSubHeaderStyle3(wb, sheet));
			cellSubTitle5.setCellValue(fur);

			if (companySystem == 1) {// 小企业
				tableDownloadInfo(listECashFlow, wb, sheet);
			} else if (companySystem == 2) { // 新准则
				List<ECashFlow> listECashFlowNew = handleListECashFlow(listECashFlow);
				tableDownloadInfo(listECashFlowNew, wb, sheet);
			}

			// 写文件
			wb.write(os);
			// 关闭输出流
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		downloadController.download(filePath, response);
	}

	private void tableDownloadInfo(List<ECashFlow> listECashFlow, XSSFWorkbook wb, XSSFSheet sheet) {
		/*
		 * 列名
		 */
		XSSFRow rowColumn = sheet.createRow(2);

		XSSFCell cellColumn0 = rowColumn.createCell(0);
		cellColumn0.setCellStyle(downloadController.columnStyle(wb));
		cellColumn0.setCellValue("项目");
		XSSFCell cellColumn1 = rowColumn.createCell(1);
		cellColumn1.setCellStyle(downloadController.columnStyle(wb));
		cellColumn1.setCellValue("行次");
		XSSFCell cellColumn2 = rowColumn.createCell(2);
		cellColumn2.setCellStyle(downloadController.columnStyle(wb));
		cellColumn2.setCellValue("本年累计金额");
		XSSFCell cellColumn3 = rowColumn.createCell(3);
		cellColumn3.setCellStyle(downloadController.columnStyle(wb));
		cellColumn3.setCellValue("本月金额");
		int n = 3;
		String left = "left";
		String center = "center";
		String right = "right";
		for (ECashFlow cf : listECashFlow) {
			XSSFRow rowsInfo = sheet.createRow(n);
			// 给这一行的第一列赋值
			String projectName = cf.getProjectName().replace("&nbsp;", "");
			XSSFCell cellInfo0 = rowsInfo.createCell(0);
			cellInfo0.setCellStyle(downloadController.infoStyle(wb, left));
			cellInfo0.setCellValue(projectName);

			String number = cf.getLineNumber();
			XSSFCell cellInfo1 = rowsInfo.createCell(1);
			cellInfo1.setCellStyle(downloadController.infoStyle(wb, center));
			cellInfo1.setCellValue(number);

			String yb = cf.getYearBalance();// == null

			XSSFCell cellInfo2 = rowsInfo.createCell(2);
			cellInfo2.setCellStyle(downloadController.infoStyle(wb, right));
			cellInfo2.setCellValue(yb);

			String pb = cf.getPeriodBalance();// == null

			XSSFCell cellInfo3 = rowsInfo.createCell(3);
			cellInfo3.setCellStyle(downloadController.infoStyle(wb, right));
			cellInfo3.setCellValue(pb);

			n++;
		}
	}

	/**
	 * 打印
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = { "stampCashFlow", "" })
	public void stampCashFlow(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws IOException {
		List<PdfPTable> listPdfPTable = new ArrayList<PdfPTable>();
		List<ECashFlow> listECashFlow = (ArrayList<ECashFlow>) session.getAttribute("downloadCashFlow");
		String title = "现金流量表 "; // 标题
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		int companySystem = companyInfo.getSystem(); // 1 小企业 2 新规则
		String sub1 = companyInfo.getCustomerName();// 上左
		String accountPeriod = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		String periodtype = new String(request.getParameter("periodtype").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		if (periodtype.equals("1")) {
			accountPeriod = accountPeriod.substring(0, 4) + "年" + accountPeriod.subSequence(4, 6) + "期";
		} else {
			accountPeriod = accountPeriod.substring(0, 4) + "年" + accountPeriod.subSequence(4, 5) + "季度";
		}
		String sub2 = accountPeriod; // 上中
		String fur = "单位：元"; // 获取货币种类
		String sub3 = fur; // 上右
		String remarks = ""; // 下作
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 打印时间
		String stampTime = "打印时间：" + sdf.format(new Date());

		String fontsPath = request.getSession().getServletContext().getRealPath("Fonts");
		PdfPTable tableInfo = new PdfPTable(1);
		tableInfo.setWidthPercentage(100);
		PdfPTable table = null;
		PdfPTable tableSupp = null;
		PdfPTable tableSub = null;
		PdfPTable tableRemarks = null;
		try {
			 if (companySystem==1) {//小企业
			table = tBuilder.createPdf(fontsPath, 4, 90, new float[] { 0.5f, 0.1f, 0.2f, 0.2f });
			 }else if (companySystem==2) { //新准则
				 table = tBuilder.createPdf(fontsPath, 4, 90, new float[] { 0.5f, 0.1f, 0.2f, 0.2f });
				 tableSupp = tBuilder.createPdf(fontsPath, 4, 90, new float[] { 0.5f, 0.1f, 0.2f, 0.2f });
//			 table = tBuilder.createPdf(fontsPath, 8, 90, new float[] { 0.25f,
//			 0.05f, 0.1f, 0.1f,0.25f, 0.05f, 0.1f, 0.1f });
			 }
			tableSub = tBuilder.createSub(fontsPath, sub1, sub2, sub3);
			tableRemarks = tBuilder.createRemarks(fontsPath, remarks, stampTime);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// -----------------Table Cells Label/Value------------------
		int n = 2;
		String left = "left";
		String center = "center";
		String right = "right";
		if (companySystem == 1) {// 小企业
			tableStampInfo(listECashFlow, fontsPath, table, n, left, center, right);
		} else if (companySystem == 2) { // 新准则
			tableStampInfo(listECashFlow, fontsPath, table, n, left, center, right);
			List<ECashFlow> listECashFlowNew = handleListECashFlow(listECashFlow);
			tableStampInfo(listECashFlowNew, fontsPath, tableSupp, n, left, center, right);
		}

		PdfPCell cellTableSub = new PdfPCell(tableSub);
		cellTableSub.setBorderWidth(0);
		tableInfo.addCell(cellTableSub);
		if (companySystem == 1) {// 小企业
			PdfPCell cellTable = new PdfPCell(table);
			cellTable.setBorderWidth(0);
			tableInfo.addCell(cellTable);	
		} else if (companySystem == 2) { // 新准则
			PdfPCell cellTable = new PdfPCell(table);
			cellTable.setBorderWidth(0);
			tableInfo.addCell(cellTable);
			PdfPCell cellTableSupp = new PdfPCell(tableSupp);
			cellTableSupp.setBorderWidth(0);
			tableInfo.addCell(cellTableSupp);
		}
		PdfPCell cellTableRemarks = new PdfPCell(tableRemarks);
		cellTableRemarks.setBorderWidth(0);
		tableInfo.addCell(cellTableRemarks);
		listPdfPTable.add(tableInfo);
		// 打印
		stampPdf.createPdf(request, response, listPdfPTable, title);
	}

	/**
	 * 打印普通的
	 * 
	 * @param listECashFlow
	 * @param fontsPath
	 * @param table
	 * @param n
	 * @param left
	 * @param center
	 * @param right
	 */
	private void tableStampInfo(List<ECashFlow> listECashFlow, String fontsPath, PdfPTable table, int n, String left, String center, String right) {
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "项目", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "行次", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "本年累计金额", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "本月金额", 0, 0));
		for (ECashFlow cf : listECashFlow) {
			String projectName = cf.getProjectName().replace("&nbsp;", "");
			table.addCell(tBuilder.createValueCell(n, left, fontsPath, projectName, 0, 0));

			String number = cf.getLineNumber();
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, number, 0, 0));

			String yb = cf.getYearBalance();// == null

			table.addCell(tBuilder.createValueCell(n, right, fontsPath, yb, 0, 0));

			String pb = cf.getPeriodBalance();// == null

			table.addCell(tBuilder.createValueCell(n, right, fontsPath, pb, 0, 0));
		}
	}

	/**
	 * 打印特殊补充的
	 * 
	 * @param listECashFlow
	 * @param fontsPath
	 * @param table
	 * @param n
	 * @param left
	 * @param center
	 * @param right
	 */
	private void tableStampInfoSupp(List<ECashFlow> listECashFlow, String fontsPath, PdfPTable table, int n, String left, String center, String right) {
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "项目", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "行次", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "本年累计金额", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "本月金额", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "项目", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "行次", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "本年累计金额", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "本月金额", 0, 0));
		for (ECashFlow cf : listECashFlow) {
			String projectName = cf.getProjectName().replace("&nbsp;", "");
			table.addCell(tBuilder.createValueCell(n, left, fontsPath, projectName, 0, 0));

			String number = cf.getLineNumber();
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, number, 0, 0));

			String yb = cf.getYearBalance();// == null

			table.addCell(tBuilder.createValueCell(n, right, fontsPath, yb, 0, 0));

			String pb = cf.getPeriodBalance();// == null

			table.addCell(tBuilder.createValueCell(n, right, fontsPath, pb, 0, 0));

			String projectNameSupp = cf.getSuppProjectName().replace("&nbsp;", "");
			table.addCell(tBuilder.createValueCell(n, left, fontsPath, projectNameSupp, 0, 0));

			String numberSupp = cf.getSuppLineNumber();
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, numberSupp, 0, 0));

			String ybSupp = cf.getSuppYearBalance();// == null

			table.addCell(tBuilder.createValueCell(n, right, fontsPath, ybSupp, 0, 0));

			String pbSupp = cf.getSuppPeriodBalance();// == null

			table.addCell(tBuilder.createValueCell(n, right, fontsPath, pbSupp, 0, 0));
		}
	}

	/**
	 * 显示编辑现金流量表页面
	 * 
	 * @param eCashFlow
	 * @param eBalance
	 * @param tReportitem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "listCashFlowAdjust", "" })
	@ResponseBody
	public Object listCashFlowAdjust(String accountPeriod, String periodEnd, String periodtype, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		Map<String, Object> map = Maps.newHashMap();
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();
		String companyName = companyInfo.getCustomerName(); // 公司名称
		int companySystem = companyInfo.getSystem(); // 1 小企业 2 新规则

		TReportitem tReportitem = new TReportitem();
		/**
		 * 页面第一次加载需要处理的
		 */

		// //当前账期
		if (StringUtils.isNoneEmpty(accountPeriod) && StringUtils.isNoneEmpty(periodEnd) && StringUtils.isNoneEmpty(periodtype)) {
			periodtype = "1"; // 默认的为1 月报
			tReportitem.setAccountPeriod(defaultPeriod);
			tReportitem.setPeriodEnd(defaultPeriod);
		}

		if (periodtype.equals("2")) { // 季报
			String[] periodInfo = getPeriodTypeTrans(accountPeriod); // 根据当前季度获取从第几个月到第几个月
			accountPeriod = periodInfo[0]; // 账期前
			periodEnd = periodInfo[1]; // 账期后
		}
		tReportitem.setFdbid(companyId);
		List<TReportitem> listTReportitem = tReportitemService.findList(tReportitem);
		if (companySystem == 1) {
			tReportitem.setReportId("100020");
		} else if (companySystem == 2) {
			tReportitem.setReportId("100017");
		}
		List<TReportitem> listTReportitemECashFlow = tReportitemService.findList(tReportitem);
		List<ECashFlow> listECashFlow = listCashFlowTable(accountPeriod, periodEnd, listTReportitem, listTReportitemECashFlow, companyInfo, periodtype); // 所有信息显示与页面
		map.put("companyName", companyName);
		map.put("listECashFlow", listECashFlow);
		map.put("companySystem", companySystem);
		return map;
	}

	/**
	 * 所有信息显示与页面
	 * 
	 * @param accountPeriod
	 * @param listTReportitem
	 * @return
	 */
	private List<ECashFlow> listCashFlowTable(String accountPeriod, String periodEnd, List<TReportitem> listTReportitem, List<TReportitem> listTReportitemECashFlow, TCustomer companyInfo, String periodtype) {
		Map<String, BigDecimal> mapY = Maps.newHashMap(); // 本年累计map
		Map<String, BigDecimal> mapP = Maps.newHashMap(); // 本月金额map
		Map<String, String> mapID = Maps.newHashMap(); // 流量表中的id
		List<ECashFlow> listECashFlow = Lists.newArrayList();
		int companySystem = companyInfo.getSystem(); // 1 小企业 2 新规则
		String reportIdBalance = ""; // 资产负债表分组id
		String reportIdCashFlow = ""; // 资金流量表的分组id
		if (companySystem == 1) {
			reportIdCashFlow = "100020";
			reportIdBalance = "100008";
		} else if (companySystem == 2) {
			reportIdCashFlow = "100017";
			reportIdBalance = "100005";
		}
		String groupId = "8";
		List<TCashflow> listTCashflow = tCashflowService.findListCashflow(groupId, accountPeriod, companyInfo.getId()); // select
		// 流量表数据
		if (CollectionUtils.isNotEmpty(listTCashflow)) { // 流量表中 有info
			addMapAddCashFlow(listTCashflow, mapY, mapP, mapID); // reader 流量表数据
			if (companySystem == 1) {
				/**
				 * 小企业
				 */
				int num = 0;
				for (TReportitem tr : listTReportitemECashFlow) {
					ECashFlow ec = new ECashFlow();
					ec.setReportItem(tr.getReportitem()); // 报表编码
					ec.setProjectName(tr.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;")); // 名称
					ec.setGroupId(tr.getGroupid()); // 分组编号
					if (tr.getDatasource().equals("0")) {
						ec.setTitleIsOrNo(true); // 是标题
					} else {
						ec.setTitleIsOrNo(false); // 是标题
						num += 1; // 行次
						ec.setLineNumber(String.valueOf(num));
					}
					ec.setTcashFowId(mapID.get(tr.getReportitem())); // 保存id
					haveCashFlowYes(mapY, mapP, tr, ec); // 现金流量表中有信息 处理数据
					listECashFlow.add(ec); // 集合中添加信息

				}
			} else if (companySystem == 2) {
				/**
				 * 新规则
				 */
				List<TReportitem> listTReportitemcf = Lists.newArrayList(); // list
				List<TReportitem> listECashFlowcfSupp = Lists.newArrayList(); // 补充list

				int n = 0;
				int num = 0; // 行次
				int numSupp = 0; // 补充的行次
				int listTReportitemTCahsFlowSize = listTReportitemECashFlow.size();

				for (TReportitem tr : listTReportitem) {
					n++;
					if (n <= listTReportitemTCahsFlowSize / 2) {
						if (!tr.getDatasource().equals("0")) {
							numSupp++;
						}
						listTReportitemcf.add(tr);
					} else {
						listECashFlowcfSupp.add(tr);
					}
				}
				// 转换为迭代器
				Iterator<TReportitem> iteratorcf = listTReportitemcf.iterator();
				Iterator<TReportitem> iteratorcfSupp = listECashFlowcfSupp.iterator();
				while (iteratorcf.hasNext() && iteratorcfSupp.hasNext()) {

					TReportitem tcf = iteratorcf.next();
					TReportitem tcfSupp = iteratorcfSupp.next();

					ECashFlow ec = new ECashFlow();

					ec.setReportItem(tcf.getReportitem()); // 报表编码
					ec.setProjectName(tcf.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;")); // 名称
					ec.setGroupId(tcf.getGroupid()); // 分组编号
					if (tcf.getDatasource().equals("0")) {
						ec.setTitleIsOrNo(true); // 是标题
					} else {
						ec.setTitleIsOrNo(false); // 是标题
						num += 1; // 行次
						ec.setLineNumber(String.valueOf(num));
					}
					ec.setTcashFowId(mapID.get(tcf.getReportitem())); // 保存id
					haveCashFlowYes(mapY, mapP, tcf, ec); // 现金流量表中有信息 处理数据
					// ==========================================================================
					ec.setSuppReportItem(tcfSupp.getReportitem()); // 报表编码
					ec.setSuppProjectName(tcfSupp.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;")); // 名称
					ec.setSuppGroupId(tcfSupp.getGroupid()); // 分组编号
					if (tcfSupp.getDatasource().equals("0")) {
						ec.setSuppTitleIsOrNo(true); // 是标题
					} else {
						ec.setSuppTitleIsOrNo(false); // 是标题
						numSupp += 1; // 行次
						ec.setSuppLineNumber(String.valueOf(numSupp));
					}
					ec.setSuppTcashFowId(mapID.get(tcfSupp.getReportitem())); // 保存id
					haveCashFlowYesSupp(mapY, mapP, tcfSupp, ec); // 现金流量表中有信息
																	// 处理数据
					listECashFlow.add(ec); // 集合中添加信息
				}

			}
		} else {
			Map<String, List<Reportbalance>> mapPeriod = this.getListReportbalancePeriod(accountPeriod, periodEnd, companyInfo.getId(), listTReportitem);
			Map<String, List<Reportbalance>> mapYear = this.getListReportbalanceYear(accountPeriod, periodEnd, companyInfo.getId(), listTReportitem, reportIdBalance);

			/**
			 * 读取非现金流量表信息
			 */
			for (TReportitem tr : listTReportitem) {
				addMapTotalAmount(mapPeriod, mapYear, mapY, mapP, tr, reportIdBalance); // reader资产负债表利润表的信息
				// if (!flag) {
				// flag = addMapAddCash(accountPeriod, companyInfo.getId(),
				// mapY, mapP); // 读取流量表中
				// }
			}
			String groupIdAdd = "7";
			List<TCashflow> listTCashflowAdd = tCashflowService.findListCashflow(groupIdAdd, accountPeriod, companyInfo.getId()); // select
			if (CollectionUtils.isNotEmpty(listTCashflowAdd)) {
				addMapAddCashFlow(listTCashflowAdd, mapY, mapP, mapID); // reader
																		// 流量表数据
			}
			if (companySystem == 1) {
				/**
				 * 小企业
				 */
				int num = 0;
				for (TReportitem tr : listTReportitemECashFlow) {
					ECashFlow ec = new ECashFlow();
					ec.setReportItem(tr.getReportitem()); // 报表编码
					ec.setProjectName(tr.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;")); // 名称
					ec.setGroupId(tr.getGroupid()); // 分组编号
					if (tr.getDatasource().equals("0")) {
						ec.setTitleIsOrNo(true); // 是标题
					} else {
						ec.setTitleIsOrNo(false); // 是标题
						num += 1; // 行次
						ec.setLineNumber(String.valueOf(num));
					}
					ec.setTcashFowId(mapID.get(tr.getReportitem())); // 保存id
					haveCashFlowNo(mapY, mapP, tr, ec); // 现金流量表中有没有信息 利润表
														// 资产负债表的合计
					listECashFlow.add(ec); // 集合中添加信息

				}
			} else if (companySystem == 2) {
				/**
				 * 新规则
				 */
				List<TReportitem> listTReportitemcf = Lists.newArrayList(); // list
				List<TReportitem> listECashFlowcfSupp = Lists.newArrayList(); // 补充list

				int n = 0;
				int num = 0; // 行次
				int numSupp = 0; // 补充的行次
				int listTReportitemTCahsFlowSize = listTReportitemECashFlow.size();

				for (TReportitem tr : listTReportitemECashFlow) {
					n++;
					if (n <= listTReportitemTCahsFlowSize / 2) {
						if (!tr.getDatasource().equals("0")) {
							numSupp++;
						}
						listTReportitemcf.add(tr);
					} else {
						listECashFlowcfSupp.add(tr);
					}
				}
				// 转换为迭代器
				Iterator<TReportitem> iteratorcf = listTReportitemcf.iterator();
				Iterator<TReportitem> iteratorcfSupp = listECashFlowcfSupp.iterator();
				while (iteratorcf.hasNext() && iteratorcfSupp.hasNext()) {

					TReportitem tcf = iteratorcf.next();
					TReportitem tcfSupp = iteratorcfSupp.next();
					ECashFlow ec = new ECashFlow();

					ec.setReportItem(tcf.getReportitem()); // 报表编码
					ec.setProjectName(tcf.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;")); // 名称
					ec.setGroupId(tcf.getGroupid()); // 分组编号
					if (tcf.getDatasource().equals("0")) {
						ec.setTitleIsOrNo(true); // 是标题
					} else {
						ec.setTitleIsOrNo(false); // 是标题
						num += 1; // 行次
						ec.setLineNumber(String.valueOf(num));
					}
					ec.setTcashFowId(mapID.get(tcf.getReportitem())); // 保存id
					haveCashFlowNo(mapY, mapP, tcf, ec); // 现金流量表中有信息 处理数据
					// ==========================================================================
					ec.setSuppReportItem(tcfSupp.getReportitem()); // 报表编码
					ec.setSuppProjectName(tcfSupp.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;")); // 名称
					ec.setSuppGroupId(tcfSupp.getGroupid()); // 分组编号
					if (tcfSupp.getDatasource().equals("0")) {
						ec.setSuppTitleIsOrNo(true); // 是标题
					} else {
						ec.setSuppTitleIsOrNo(false); // 是标题
						numSupp += 1; // 行次
						ec.setSuppLineNumber(String.valueOf(numSupp));
					}
					ec.setSuppTcashFowId(mapID.get(tcfSupp.getReportitem())); // 保存id
					haveCashFlowNoSupp(mapY, mapP, tcfSupp, ec); // 现金流量表中有没有信息
																	// 利润表
																	// 资产负债表的合计
					listECashFlow.add(ec); // 集合中添加信息
				}
			}
		}
		return listECashFlow;
	}

	/**
	 * 季报月报转换
	 * 
	 * @param accountPeriod
	 * @return
	 */
	private String[] getPeriodTypeTrans(String quarter) {
		String accountPeriod = "";
		String periodEnd = "";
		String year = quarter.substring(0, 4);
		String periodType = quarter.substring(4, 5); // 截取最后一个值 他记录的季
		if (periodType.equals("1")) { // 第一季
			accountPeriod = year + "01";
			periodEnd = year + "03";
		}
		if (periodType.equals("2")) { // 第二季
			accountPeriod = year + "04";
			periodEnd = year + "06";
		}
		if (periodType.equals("3")) { // 第三季
			accountPeriod = year + "07";
			periodEnd = year + "09";
		}
		if (periodType.equals("4")) { // 第四季s
			accountPeriod = year + "10";
			periodEnd = year + "12";
		}
		String[] periodInfo = { accountPeriod, periodEnd };

		return periodInfo;
	}

	/**
	 * 读取流量表中 流量附加表的信息
	 * 
	 * @param accountPeriod
	 * @param companyId
	 * @param mapY
	 * @param mapP
	 */
	private boolean addMapAddCash(String accountPeriod, String companyId, Map<String, BigDecimal> mapY, Map<String, BigDecimal> mapP) {
		boolean flag = false;
		String groupId = "7";
		List<TCashflow> listTCashflow = tCashflowService.findListCashflow(groupId, accountPeriod, companyId);
		if (CollectionUtils.isNotEmpty(listTCashflow)) {
			flag = true;
			for (TCashflow tc : listTCashflow) {
				String ytdendamount = tc.getYtdendamount() == null || tc.getYtdendamount().equals("") ? "0" : tc.getYtdendamount(); // 本月
																																	// 末
				String ytdbeginamount = tc.getYtdbeginamount() == null || tc.getYtdbeginamount().equals("") ? "0" : tc.getYtdbeginamount(); // 本月
																																			// 初
				String lytdendamount = tc.getLytdendamount() == null || tc.getLytdendamount().equals("") ? "0" : tc.getLytdendamount(); // 本年
																																		// 末
				String lytdbeginamount = tc.getLytdbeginamount() == null || tc.getLytdbeginamount().equals("") ? "0" : tc.getLytdbeginamount(); // 本年
																																				// 初
				BigDecimal ytAmount = new BigDecimal(ytdendamount).subtract(new BigDecimal(ytdbeginamount));
				BigDecimal lytAmount = new BigDecimal(lytdendamount).subtract(new BigDecimal(lytdbeginamount));

				mapP.put(tc.getReportitem(), ytAmount);
				mapY.put(tc.getReportitem(), lytAmount);
			}
		}
		return flag;

	}

	/**
	 * read 现金流量表
	 * 
	 * @param accountPeriod
	 * @param companyId
	 * @param mapY
	 * @param mapP
	 */
	private void addMapAddCashFlow(List<TCashflow> listTCashflow, Map<String, BigDecimal> mapY, Map<String, BigDecimal> mapP, Map<String, String> mapID) {
		for (TCashflow tc : listTCashflow) {
			String ytdendamount = tc.getYtdendamount() == null || tc.getYtdendamount().equals("") ? "0" : tc.getYtdendamount(); // 本月
																																// 末
			String ytdbeginamount = tc.getYtdbeginamount() == null || tc.getYtdbeginamount().equals("") ? "0" : tc.getYtdbeginamount(); // 本月
																																		// 初
			String lytdendamount = tc.getLytdendamount() == null || tc.getLytdendamount().equals("") ? "0" : tc.getLytdendamount(); // 本年
																																	// 末
			String lytdbeginamount = tc.getLytdbeginamount() == null || tc.getLytdbeginamount().equals("") ? "0" : tc.getLytdbeginamount(); // 本年
																																			// 初
			BigDecimal ytAmount = new BigDecimal(ytdendamount).subtract(new BigDecimal(ytdbeginamount));
			BigDecimal lytAmount = new BigDecimal(lytdendamount).subtract(new BigDecimal(lytdbeginamount));

			mapP.put(tc.getReportitem(), ytAmount);

			mapY.put(tc.getReportitem(), lytAmount);

			mapID.put(tc.getReportitem(), tc.getId());
		}

	}

	/**
	 * reader 资产负债 利润表
	 * 
	 * @param mapPeriod
	 * @param mapYear
	 * @param mapY
	 * @param mapP
	 * @param tr
	 */

	private void addMapTotalAmount(Map<String, List<Reportbalance>> mapPeriod, Map<String, List<Reportbalance>> mapYear, Map<String, BigDecimal> mapY, Map<String, BigDecimal> mapP, TReportitem tr, String reportId) {
		if (tr.getDatasource().equals("1")) {
			BigDecimal[] dous = this.findPeriodBalance(mapPeriod, mapYear, tr.getReportitem(), reportId);

			mapP.put(tr.getReportitem(), dous[0]); // 本月金额map
			mapY.put(tr.getReportitem(), dous[1]); // 本年金额map
		}
		if (tr.getDatasource().equals("2")) {

			BigDecimal[] dous = totalTotal(tr.getFormula(), mapP, mapY);

			mapP.put(tr.getReportitem(), dous[0]); // 本月金额map 合计
			mapY.put(tr.getReportitem(), dous[1]); // 本年金额map 合计

		}
		if (tr.getDatasource().equals("3")) {

			BigDecimal[] dous = this.totalTotal(tr.getFormula(), mapP, mapY);

			mapP.put(tr.getReportitem(), dous[0]); // 本月金额map 合计 合计
			mapY.put(tr.getReportitem(), dous[1]); // 本年金额map 合计 合计
		}
	}

	/**
	 * 读取现金流量表中有信息
	 * 
	 * @param tr
	 * @param ec
	 * @param tCashflow
	 */
	private void haveCashFlowYes(Map<String, BigDecimal> mapY, Map<String, BigDecimal> mapP, TReportitem tr, ECashFlow ec) {

		BigDecimal mP = mapP.get(tr.getReportitem());
		mP = mP == null ? BigDecimal.ZERO : mP;
		BigDecimal mY = mapY.get(tr.getReportitem());
		mY = mY == null ? BigDecimal.ZERO : mY;
		if (mP.compareTo(BigDecimal.ZERO) != 0) {
			ec.setPeriodBalance(moneyFormat.format(mP)); // 本月金额
		} else {
			ec.setPeriodBalance(null); // 本月金额
		}
		if (mY.compareTo(BigDecimal.ZERO) != 0) {
			ec.setYearBalance(moneyFormat.format(mY)); // 本年累计金额
		} else {
			ec.setYearBalance(null); // 本年累计金额
		}

		if (tr.getDatasource().equals("1")) {
			ec.setAsIsALabel(true); // 可读可写
		}
		if (tr.getDatasource().equals("2")) { // 合计

			ec.setAsIsALabel(true); // 可读可写
		}
		if (tr.getDatasource().equals("3")) { // 合计 合计
			ec.setAsIsALabel(false); // 可读不可写
		}
	}

	/**
	 * 读取现金流量表中有信息
	 * 
	 * @param tr
	 * @param ec
	 * @param tCashflow
	 */
	private void haveCashFlowYesSupp(Map<String, BigDecimal> mapY, Map<String, BigDecimal> mapP, TReportitem tr, ECashFlow ec) {

		BigDecimal mP = mapP.get(tr.getReportitem());
		mP = mP == null ? BigDecimal.ZERO : mP;
		BigDecimal mY = mapY.get(tr.getReportitem());
		mY = mY == null ? BigDecimal.ZERO : mY;
		if (mP.compareTo(BigDecimal.ZERO) != 0) {
			ec.setSuppPeriodBalance(moneyFormat.format(mP)); // 本月金额
		} else {
			ec.setSuppPeriodBalance(null); // 本月金额
		}
		if (mY.compareTo(BigDecimal.ZERO) != 0) {
			ec.setSuppYearBalance(moneyFormat.format(mY)); // 本年累计金额
		} else {
			ec.setSuppYearBalance(null); // 本年累计金额
		}

		if (tr.getDatasource().equals("1")) {
			ec.setSuppAsIsALabel(true); // 可读可写
		}
		if (tr.getDatasource().equals("2")) { // 合计

			ec.setSuppAsIsALabel(true); // 可读可写
		}
		if (tr.getDatasource().equals("3")) { // 合计 合计
			ec.setSuppAsIsALabel(false); // 可读不可写
		}
	}

	/**
	 * 现金流量表中有没有信息 从资产负债表和现金流量表读取信息
	 * 
	 * @param mapY
	 * @param mapP
	 * @param tr
	 * @param ec
	 */
	private void haveCashFlowNo(Map<String, BigDecimal> mapY, Map<String, BigDecimal> mapP, TReportitem tr, ECashFlow ec) {
		if (tr.getDatasource().equals("1")) {
			ec.setAsIsALabel(true); // 可读可写
		}
		if (tr.getDatasource().equals("2")) { // 合计

			ec.setAsIsALabel(true); // 可读可写

			BigDecimal[] dous;
			dous = totalTotal(tr.getFormula(), mapP, mapY);

			if (dous[1].compareTo(BigDecimal.ZERO) != 0) {
				ec.setYearBalance(moneyFormat.format(dous[1])); // 本年累计金额
			} else {
				ec.setYearBalance(null); // 本年累计金额
			}
			if (dous[0].compareTo(BigDecimal.ZERO) != 0) {
				ec.setPeriodBalance(moneyFormat.format(dous[0])); // 本月金额
			} else {
				ec.setPeriodBalance(null); // 本月金额
			}
			mapP.put(tr.getReportitem(), dous[0]); // 本月金额map 合计
			mapY.put(tr.getReportitem(), dous[1]); // 本年金额map 合计
		}
		if (tr.getDatasource().equals("3")) { // 合计 合计
			ec.setAsIsALabel(false); // 可读不可写
			BigDecimal[] dous = totalTotal(tr.getFormula(), mapP, mapY);
			if (dous[1].compareTo(BigDecimal.ZERO) != 0) {
				ec.setYearBalance(moneyFormat.format(dous[1])); // 本年累计
			} else {
				ec.setYearBalance(null); // 本年累计
			}
			if (dous[0].compareTo(BigDecimal.ZERO) != 0) {
				ec.setPeriodBalance(moneyFormat.format(dous[0])); // 本月金额
			} else {
				ec.setPeriodBalance(null); // 本月金额
			}
			mapP.put(tr.getReportitem(), dous[0]); // 本月金额map 合计
			mapY.put(tr.getReportitem(), dous[1]); // 本年金额map 合计

		}
	}

	/**
	 * 现金流量表中有没有信息 从资产负债表和现金流量表读取信息
	 * 
	 * @param mapY
	 * @param mapP
	 * @param tr
	 * @param ec
	 */
	private void haveCashFlowNoSupp(Map<String, BigDecimal> mapY, Map<String, BigDecimal> mapP, TReportitem tr, ECashFlow ec) {
		if (tr.getDatasource().equals("1")) {
			ec.setSuppAsIsALabel(true); // 可读可写
		}
		if (tr.getDatasource().equals("2")) { // 合计

			ec.setSuppAsIsALabel(true); // 可读可写

			BigDecimal[] dous;
			dous = totalTotal(tr.getFormula(), mapP, mapY);

			if (dous[1].compareTo(BigDecimal.ZERO) != 0) {
				ec.setSuppYearBalance(moneyFormat.format(dous[1])); // 本年累计金额
			} else {
				ec.setSuppYearBalance(null); // 本年累计金额
			}
			if (dous[0].compareTo(BigDecimal.ZERO) != 0) {
				ec.setSuppPeriodBalance(moneyFormat.format(dous[0])); // 本月金额
			} else {
				ec.setSuppPeriodBalance(null); // 本月金额
			}
			mapP.put(tr.getReportitem(), dous[0]); // 本月金额map 合计
			mapY.put(tr.getReportitem(), dous[1]); // 本年金额map 合计
		}
		if (tr.getDatasource().equals("3")) { // 合计 合计
			ec.setSuppAsIsALabel(false); // 可读不可写
			BigDecimal[] dous = totalTotal(tr.getFormula(), mapP, mapY);
			if (dous[1].compareTo(BigDecimal.ZERO) != 0) {
				ec.setSuppYearBalance(moneyFormat.format(dous[1])); // 本年累计
			} else {
				ec.setSuppYearBalance(null); // 本年累计
			}
			if (dous[0].compareTo(BigDecimal.ZERO) != 0) {
				ec.setSuppPeriodBalance(moneyFormat.format(dous[0])); // 本月金额
			} else {
				ec.setSuppPeriodBalance(null); // 本月金额
			}
			mapP.put(tr.getReportitem(), dous[0]); // 本月金额map 合计
			mapY.put(tr.getReportitem(), dous[1]); // 本年金额map 合计

		}
	}

	/**
	 * 获取同期同客户的所有的公式
	 * 
	 * @param accountPeriod
	 * @param periodEnd
	 * @param fdbid
	 * @return
	 */
	public Map<String, List<Reportbalance>> getListReportbalancePeriod(String accountPeriod, String periodEnd, String fdbid, List<TReportitem> listTReportitem) {
		Map<String, List<Reportbalance>> mapPeriod = Maps.newHashMap();
		List<Reportbalance> listReportbalance = reportbalanceService.findListByReportId(accountPeriod, periodEnd, null, fdbid);
		List<Reportbalance> listRep = Lists.newArrayList();

		for (TReportitem rep : listTReportitem) {
			if (listReportbalance.size() <= 0) {
				break;
			} else {
				for (Reportbalance re : listReportbalance) {
					if (re.getReportitem().equals(rep.getReportitem())) {
						listRep.add(re);
					}
				}
				mapPeriod.put(rep.getReportitem(), listRep);
				listRep = new ArrayList<Reportbalance>();
			}
		}

		return mapPeriod;

	}

	/**
	 * 获取同期同客户的所有的公式
	 * 
	 * @param accountPeriod
	 * @param periodEnd
	 * @param fdbid
	 * @return
	 */
	public Map<String, List<Reportbalance>> getListReportbalanceYear(String accountPeriod, String periodEnd, String fdbid, List<TReportitem> listTReportitem, String reportId) {
		Map<String, List<Reportbalance>> mapYear = Maps.newHashMap();
		accountPeriod = accountPeriod.substring(0, 4) + "01";
		periodEnd = accountPeriod;
		List<Reportbalance> listReportbalance = reportbalanceService.findListByReportId(accountPeriod, periodEnd, reportId, fdbid);
		List<Reportbalance> listRep = new ArrayList<Reportbalance>();
		for (TReportitem rep : listTReportitem) {
			if (listReportbalance.size() <= 0) {
				break;
			} else {
				for (Reportbalance re : listReportbalance) {
					if (re.getReportitem().equals(rep.getReportitem())) {
						listRep.add(re);
					}
				}
				mapYear.put(rep.getReportitem(), listRep);
				listRep = new ArrayList<Reportbalance>();
			}
		}

		return mapYear;
	}

	/**
	 * 本年累计 和本月合计
	 * 
	 * @param accountPeriod
	 * @param reportitem
	 * @return
	 */
	public BigDecimal[] findPeriodBalance(Map<String, List<Reportbalance>> mapPeriod, Map<String, List<Reportbalance>> mapYear, String reportitem, String reportId) {
		BigDecimal doup = BigDecimal.ZERO; // 本月合计
		BigDecimal douy = BigDecimal.ZERO; // 本年合计
		// List<Reportbalance> listReportbalance = reportbalanceService
		// .findListByReItem(accountPeriod, periodEnd, reportitem, fdbid,
		// null);
		List<Reportbalance> listReportbalance = mapPeriod.get(reportitem);
		BigDecimal n = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(listReportbalance)) {
			for (Reportbalance re : listReportbalance) {
				if (re.getReportId().equals(reportId)) { // 资产负债
					n = n.add(BigDecimal.ONE);

					if (re.getReportitem().equals("01*001*01")) {// 取值 期初余额
																	// 和年初余额
						if (re.getAccDc().equals("1")) { // 借方科目

							String getBeginbalance = re.getBeginbalance() == null || re.getBeginbalance().equals("") ? "0" : re.getBeginbalance();// 获取本期期初金额
																																					// //
																																					// 借方
							BigDecimal getBeginbalanceDouble = new BigDecimal(getBeginbalance); // 转为double计算

							doup = doup.add(getBeginbalanceDouble); // 计算合计金额 月
							BigDecimal asYearStart = this.startYear(mapYear, re.getReportitem());
							douy = asYearStart; // 计算合计金额 年 年初余额
						}
						if (re.getAccDc().equals("-1")) { // 贷方科目
							String getBeginbalance = re.getBeginbalance() == null || re.getBeginbalance().equals("") ? "0" : re.getBeginbalance();// 获取本期期初金额
																																					// //
																																					// 借方
							BigDecimal getBeginbalanceDouble = new BigDecimal(getBeginbalance).negate(); // 转为double计算

							doup = doup.add(getBeginbalanceDouble); // 计算合计金额 月
							BigDecimal asYearStart = this.startYear(mapYear, re.getReportitem());
							douy = asYearStart; // 计算合计金额 年 年初余额
						}
					} else { // 取值现金净增额
						if (re.getAccDc().equals("1")) { // 借方科目

							String getDebitTotal = re.getDebittotalamount() == null || re.getDebittotalamount().equals("") ? "0" : re.getDebittotalamount();
							BigDecimal getDebitTotalDouble = new BigDecimal(getDebitTotal);

							String getCreditTotal = re.getCredittotalamount() == null || re.getCredittotalamount().equals("") ? "0" : re.getCredittotalamount();
							BigDecimal getCreditTotalDouble = new BigDecimal(getCreditTotal);

							BigDecimal getAmountforDouble = getDebitTotalDouble.subtract(getCreditTotalDouble); // 本月额（实际损益发生额）

							String getYtdDebitTotal = re.getYtddebittotalamount() == null || re.getYtddebittotalamount().equals("") ? "0" : re.getYtddebittotalamount();
							BigDecimal getYtdDebitTotalDouble = new BigDecimal(getYtdDebitTotal);
							String getYtdCrediTotal = re.getYtdcredittotalamount() == null || re.getYtdcredittotalamount().equals("") ? "0" : re.getYtdcredittotalamount();
							BigDecimal getYtdCrediTotalDouble = new BigDecimal(getYtdCrediTotal);
							BigDecimal getYtdamountforDouble = getYtdDebitTotalDouble.subtract(getYtdCrediTotalDouble);

							if (re.getOp().equals("+")) {
								doup = doup.add(getAmountforDouble); // 计算合计金额 月
								douy = douy.add(getYtdamountforDouble); // 计算合计金额
							} else {
								doup = doup.subtract(getAmountforDouble); // 计算合计金额
																			// 月
								douy = douy.subtract(getYtdamountforDouble); // 计算合计金额
							}

							// 年
						}

						if (re.getAccDc().equals("-1")) { // 贷方科目

							String getDebitTotal = re.getDebittotalamount() == null || re.getDebittotalamount().equals("") ? "0" : re.getDebittotalamount();
							BigDecimal getDebitTotalDouble = new BigDecimal(getDebitTotal);

							String getCreditTotal = re.getCredittotalamount() == null || re.getCredittotalamount().equals("") ? "0" : re.getCredittotalamount();
							BigDecimal getCreditTotalDouble = new BigDecimal(getCreditTotal);

							BigDecimal getAmountforDouble = getCreditTotalDouble.subtract(getDebitTotalDouble); // 本月额（实际损益发生额）

							String getYtdDebitTotal = re.getYtddebittotalamount() == null || re.getYtddebittotalamount().equals("") ? "0" : re.getYtddebittotalamount();
							BigDecimal getYtdDebitTotalDouble = new BigDecimal(getYtdDebitTotal);
							String getYtdCrediTotal = re.getYtdcredittotalamount() == null || re.getYtdcredittotalamount().equals("") ? "0" : re.getYtdcredittotalamount();
							BigDecimal getYtdCrediTotalDouble = new BigDecimal(getYtdCrediTotal);
							BigDecimal getYtdamountforDouble = getYtdCrediTotalDouble.subtract(getYtdDebitTotalDouble);
							if (n.compareTo(BigDecimal.ONE) == 1) {
								if (re.getOp().equals("+")) {
									doup = doup.subtract(getAmountforDouble); // 计算合计金额
																				// 月
									douy = douy.subtract(getYtdamountforDouble); // 计算合计金额
								} else {
									doup = doup.add(getAmountforDouble); // 计算合计金额
																			// 月
									douy = douy.add(getYtdamountforDouble); // 计算合计金额
								}
							} else {
								if (re.getOp().equals("+")) {
									doup = doup.add(getAmountforDouble); // 计算合计金额
																			// 月
									douy = douy.add(getYtdamountforDouble); // 计算合计金额
								} else {
									doup = doup.subtract(getAmountforDouble); // 计算合计金额
																				// 月
									douy = douy.subtract(getYtdamountforDouble); // 计算合计金额
								}
							}
						}

					}

				} else { // 利润表
					if (re.getAccDc().equals("1")) { // 借方科目

						String getAmountfor = re.getAmountfor() == null || re.getAmountfor().equals("") ? // 获取本月金额（实际损益发生额）
						"0"
								: re.getAmountfor();
						BigDecimal getAmountforDouble = new BigDecimal(getAmountfor); // 转为double计算
						doup = doup.add(getAmountforDouble); // 计算合计金额 月

						String getYtdamountfor = re.getYtdamountfor() == null || re.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额）
						"0"
								: re.getYtdamountfor();
						BigDecimal getYtdamountforDouble = new BigDecimal(getYtdamountfor); // 转为double用于计算
						douy = douy.add(getYtdamountforDouble); // 计算合计金额 年
					}
					if (re.getAccDc().equals("-1")) { // 贷方科目
						String getAmountfor = re.getAmountfor() == null || re.getAmountfor().equals("") ? // 获取本月金额（实际损益发生额）
						"0"
								: re.getAmountfor();
						BigDecimal getAmountforDouble = new BigDecimal(getAmountfor).negate(); // 转为double计算
						doup = doup.add(getAmountforDouble); // 计算合计金额 月

						String getYtdamountfor = re.getYtdamountfor() == null || re.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额）
						"0"
								: re.getYtdamountfor();
						BigDecimal getYtdamountforDouble = new BigDecimal(getYtdamountfor).negate(); // 转为double计算
						douy = douy.add(getYtdamountforDouble); // 计算合计金额 年
					}
				}

			}
		}
		BigDecimal[] dous = { doup, douy };
		return dous;
	}

	/**
	 * 年初余额
	 * 
	 * @param reportItem
	 * @param accountPeriod
	 * @return
	 */
	public BigDecimal startYear(Map<String, List<Reportbalance>> mapYear, String reportItem) {
		List<Reportbalance> listReportbalance = mapYear.get(reportItem);

		BigDecimal money = BigDecimal.ZERO;
		String subReportItem = reportItem.substring(0, 2); // 截取报表编号判断是资产类还是负债类和所有者权益
		if (subReportItem.equals("01")) { // 资产类
			money = getAsMoney(listReportbalance, money);
		}
		if (subReportItem.equals("02") // 负债类
				|| subReportItem.equals("03")) { // 所有者权益
			money = getLiMoney(listReportbalance, money);
		}
		return money;
	}

	/**
	 * 根据取数规则进行判断取数 资产类和所有者权益
	 */
	private BigDecimal getAsMoney(List<Reportbalance> listReportbalance, BigDecimal money) {
		/**
		 * 借方金额 （余额保存在汇总表（初始余额表）中为数值） 贷方金额 （余额保存在汇总表（初始余额表）中为相反数）
		 * 
		 * 资产类 借方科目 余额就取值 借方余额取值 贷方余额为0 资产类 贷方科目 余额取值即可 借方余额为0 贷方余额为相反数
		 * 
		 */
		if (CollectionUtils.isNotEmpty(listReportbalance)) {
			for (Reportbalance re : listReportbalance) {
				// 余额
				String getbalance = re.getBeginbalance() == null || re.getBeginbalance().equals("") ? "0" : re.getBeginbalance(); // 余额
				BigDecimal getbalanceDouble = new BigDecimal(getbalance);

				if (re.getDc().equals("1")) { // 借方科目
					if (re.getFtype().equals("C")) { // 贷方余额
						getbalanceDouble = BigDecimal.ZERO; // 资产类 借方科目 贷方余额为0
					}
				}
				if (re.getDc().equals("-1")) { // 贷方科目
					if (re.getFtype().equals("D")) { // 借方余额
						getbalanceDouble = BigDecimal.ZERO; // 资产类 贷方科目 借方余额为0
					}
					if (re.getFtype().equals("C")) { // 贷方余额
						getbalanceDouble = getbalanceDouble.negate(); // 资产类 贷方科
																		// 目
																		// 贷方余额为相反数
					}
				}
				if (re.getOp().equals("+")) {
					money = money.add(getbalanceDouble);
				} else {
					money = money.subtract(getbalanceDouble);
				}
			}
		}

		return money;
	}

	/**
	 * 根据取数规则进行判断取数 负债类和所有者权益
	 */
	private BigDecimal getLiMoney(List<Reportbalance> listReportbalance, BigDecimal money) {
		/**
		 * 借方金额 （余额保存在汇总表（初始余额表）中为数值） 贷方金额 （余额保存在汇总表（初始余额表）中为相反数）
		 * 
		 * 负债类 借方科目 余额为相反数 借方余额取值 贷方余额为0 负债类 贷方科目 余额为相反数 借方余额为0 贷方余额为相反数
		 */
		if (CollectionUtils.isNotEmpty(listReportbalance)) {
			for (Reportbalance re : listReportbalance) {
				// String getEndbalance = re.getEndbalance() == null
				// || re.getEndbalance().equals("") ? "0" : re.getEndbalance();
				// //
				// 余额
				String getbalance = re.getBeginbalance() == null || re.getBeginbalance().equals("") ? "0" : re.getBeginbalance(); // 余额
				BigDecimal getbalanceDouble = new BigDecimal(getbalance);
				if (re.getDc().equals("-1")) { // 贷方科目
					if (re.getFtype().equals("D")) { // 借方余额
						getbalanceDouble = BigDecimal.ZERO; // 负债类 贷方科目 借方余额为0
					}
					if (re.getFtype().equals("C")) { // 贷方余额
						getbalanceDouble = getbalanceDouble.negate(); // 负债类
																		// 贷方科目
																		// 贷方余额为相反数
					}
					if (re.getFtype().equals("Y")) { // 余额
						getbalanceDouble = getbalanceDouble.negate(); // 负债类
																		// 贷方科目
																		// 余额为相反数
					}
				}
				if (re.getDc().equals("1")) { // 借方科目
					if (re.getFtype().equals("C")) { // 贷方余额
						getbalanceDouble = BigDecimal.ZERO; // 负债类 借方科目 贷方余额为0
					}
					if (re.getFtype().equals("Y")) { // 余额
						getbalanceDouble = getbalanceDouble.negate(); // 负债类
																		// 借方科目
																		// 余额为相反数
					}
				}
				if (re.getOp().equals("+")) {
					money = money.add(getbalanceDouble);
				} else {
					money = money.subtract(getbalanceDouble);
				}
			}
		}
		return money;
	}

	/**
	 * 合计合计余额Map
	 * 
	 * @param formula
	 * @param accountPeriod
	 * @return
	 */
	public BigDecimal[] totalTotal(String formula, Map<String, BigDecimal> mapP, Map<String, BigDecimal> mapY) {
		Pattern p = Pattern.compile("\\[(.*?)\\](.*?)?(?=$|\\[)");
		Matcher m = p.matcher(formula);
		List<String> listVariable = Lists.newArrayList();
		while (m.find()) {
			listVariable.add(m.group(1));
		}
		String listBalanceP = formula; // 存储金额 期
		String listBalanceY = formula; // 存储金额 年

		/**
		 * 处理本期的
		 */
		for (String str : listVariable) {
			Object mapStr = mapP.get(str) == null ? BigDecimal.ZERO : mapP.get(str);
			listBalanceP = listBalanceP.replace("["+str+"]", mapStr.toString());
		}
		/**
		 * 处理本年的
		 */
		for (String str : listVariable) {
			Object mapStr = mapY.get(str) == null ? BigDecimal.ZERO : mapY.get(str);
			listBalanceY = listBalanceY.replace("["+str+"]", mapStr.toString());
		}
		listBalanceP = listBalanceP.replaceAll("\\[", "").replaceAll("\\]", "");
		listBalanceY = listBalanceY.replaceAll("\\[", "").replaceAll("\\]", "");
		// 调用处理公式
		BigDecimal moneyY = calculation(listBalanceY);
		BigDecimal moneyP = calculation(listBalanceP);
		BigDecimal[] dous = { moneyP, moneyY };
		return dous;
	}

	/**
	 * 公式计算金额
	 * 
	 * @param listSymbol
	 * @param liBalance
	 */
	private BigDecimal calculation(String liBalance) {
		BigDecimal money = BigDecimal.ZERO;
		try {
			// 计算金额
			money = new BigDecimal(jse.eval(liBalance).toString());
		} catch (Exception t) {
		}
		return money;
	}

	// 获取所有的账期
	@RequestMapping(value = { "getAllAccountPeriod", "" })
	@ResponseBody
	public Object getAllAccountPeriod(Model model, HttpSession session, String periodtype) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();// //获取当前账期
		Map<String, Object> mapPeriod = Maps.newHashMap();
		mapPeriod.put("defaultPeriod", defaultPeriod);
		mapPeriod.put("periodtype", periodtype);
		List<String> listAccountperiod = tBalanceService.getAllAccountperiod(companyId);

		boolean flag = false;
		for (String tVoucher : listAccountperiod) {
			if (tVoucher.equals(defaultPeriod)) {
				flag = true; // 如果存在
			}
		}
		// 如果不存在
		if (!flag) {
			
			listAccountperiod.add(defaultPeriod);
		}
		if (periodtype.equals("2")) { // 季报
			List<TVoucher> listQuarter = new ArrayList<TVoucher>(); // 保存报表
			String startP = listAccountperiod.get(0); // 获取第一个账期
			String endP = listAccountperiod.get(listAccountperiod.size() - 1); // 获取最后一个账期
			int startY = Integer.valueOf(startP.substring(0, 4)); // 获取第一个账期的年份
			int startM = Integer.valueOf(startP.substring(4, 6)); // 获取第一个账期的月份
			int endY = Integer.valueOf(endP.substring(0, 4)); // 获取最后一个账期的年份
			int endM = Integer.valueOf(endP.substring(4, 6)); // 获取最后一个账期的月份

			int startQ = this.getQuarter(startM);
			int endQ = this.getQuarter(endM);
			int numQuarter = 4 * (endY - startY) + (endQ + 1) - startQ;// 总共几个季度
																		// 公式4*（结束年
																		// -
																		// 起始年）
																		// +
																		// （结束季度+1）-
																		// 开始季度
			for (int i = 0; i < numQuarter; i++) {
				TVoucher tV = new TVoucher();

				String quarter = startY + "" + startQ + "";
				if (startQ == 4) {
					startQ = 0; // 初始化
					startY++;// 等于4 了 一年过去了 +1
				}
				startQ++; // 添加一次 季度加1

				tV.setAccountPeriod(quarter);
				listQuarter.add(tV);
			}
			mapPeriod.put("listAccountperiod", listQuarter);
		} else {
			mapPeriod.put("listAccountperiod", listAccountperiod);
		}
		return mapPeriod;
	}

	/**
	 * 根据月份获取属于第几季度
	 * 
	 * @param period
	 * @return
	 */
	private int getQuarter(int month) {
		int quarter = 0;
		if (1 <= month && month <= 3) { // 第1季度
			quarter = 1;
		}
		if (4 <= month && month <= 6) { // 第2季度
			quarter = 2;
		}
		if (7 <= month && month <= 9) { // 第3季度
			quarter = 3;
		}
		if (10 <= month && month <= 12) { // 第4季度
			quarter = 4;
		}
		return quarter;
	}

	/**
	 * 获取公司客户信息
	 */
	public TCustomer getCompanyInfo(Model model, HttpSession session) {
		Object obj = session.getAttribute("sessionCustomer");
		TCustomer companyInfo = (TCustomer) obj; // 客户公司信息对象
		return companyInfo;

	}
}