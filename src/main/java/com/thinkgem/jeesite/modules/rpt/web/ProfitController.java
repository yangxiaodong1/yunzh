/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.acctreportitem.entity.TAcctreportitem;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.download.DownloadController;
import com.thinkgem.jeesite.modules.rpt.entity.Accountbalanceperiod;
import com.thinkgem.jeesite.modules.rpt.entity.EBalance;
import com.thinkgem.jeesite.modules.rpt.entity.ECashFlow;
import com.thinkgem.jeesite.modules.rpt.entity.EProfit;
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
 * 利润表Controller
 * 
 * @author zhangtong
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/profit")
public class ProfitController extends BaseController {

	@Autowired
	private TReportitemService tReportitemService;
	@Autowired
	private ReportbalanceService reportbalanceService;
	@Autowired
	private TVoucherService tVoucherService;
	@Autowired
	private TAccountService tAccountService;
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
	 * 利润表
	 * 
	 * @param eBalance
	 * @param tReportitem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "listProfit", "" })
	public String listProfit(Model model) {

		return "modules/rpt/vProfitList";
	}

	@RequestMapping(value = { "list", "" })
	@ResponseBody
	public Object list(String accountPeriod, String periodEnd, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String companyName = companyInfo.getCustomerName(); // 公司名称
		String defaultPeriod = companyInfo.getCurrentperiod();
		int companySystem =companyInfo.getSystem();	//1 小企业  2  新规则
		
		TReportitem tReportitem = new TReportitem();
		List<EProfit> listEProfit = Lists.newArrayList(); // 创建一个新的集合添加数据 用于显示界面
		Map<String, Object> map = Maps.newHashMap();
		String reportId = "";
		if (companySystem == 1) {	//小企业
			reportId="100004"; // 利润表
		}else if (companySystem == 2) {	//新规则
			reportId="100001"; // 利润表
		}
		tReportitem.setReportId(reportId);
		tReportitem.setFdbid(companyId); // 公司客户的id
		if (StringUtils.isNoneEmpty(accountPeriod) && StringUtils.isNoneEmpty(periodEnd)) {
			tReportitem.setAccountPeriod(defaultPeriod);
			tReportitem.setPeriodEnd(defaultPeriod);
		}
//		this.listAccount(model, companyId); // 所有的科目
		List<TReportitem> listReportitem = tReportitemService.findList(tReportitem); // 转为list集合

		Map<String, BigDecimal> mapY = Maps.newHashMap(); // 本年累计map
		Map<String, BigDecimal> mapP = Maps.newHashMap(); // 本月金额map
		Map<String, List<Reportbalance>> mapPeriod = this.getListReportbalancePeriod(accountPeriod, periodEnd, companyId, listReportitem,reportId);

		int num = 0;
		for (TReportitem tr : listReportitem) {
			EProfit ep = new EProfit();
			ep.setId(tr.getId()); // 报表id
			ep.setReportItem(tr.getReportitem()); // 报表编码
			ep.setProjectName(tr.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;")); // 名称

			if (tr.getDatasource().equals("0")) {
				ep.setAsIsALabel(false); // 不是超链接的
			} else {
				num += 1; // 行次+1
				ep.setLineNumber(String.valueOf(num));
				if (tr.getDatasource().equals("1")) {
					ep.setAsIsALabel(false); // 是超链接的
					// double[] dous = findPeriodBalance(accountPeriod,
					// periodEnd,
					// tr.getReportitem(), companyId); // 获取本年累计和本月金额
					BigDecimal[] dous = findPeriodBalance(mapPeriod, tr.getReportitem()); // 获取本年累计和本月金额
					if (dous[0].compareTo(BigDecimal.ZERO) != 0) {
						ep.setPeriodBalance(moneyFormat.format(dous[0])); // 设置本月金额
					} else {
						ep.setPeriodBalance(null); // 设置本月金额
					}
					if (dous[1].compareTo(BigDecimal.ZERO) != 0) {
						ep.setYearBalance(moneyFormat.format(dous[1])); // 设置本年累计
					} else {
						ep.setYearBalance(null); // 设置本年累计
					}

					mapP.put(tr.getReportitem(), dous[0]); // 本月金额map
					mapY.put(tr.getReportitem(), dous[1]); // 本年金额map
				}
				if (tr.getDatasource().equals("2")) {
					ep.setAsIsALabel(false); // 不是超链接的
					BigDecimal[] dous = totalTotal(tr.getFormula(), mapP, mapY); // 获取本年累计和本月金额
																					// 合计
					if (dous[0].compareTo(BigDecimal.ZERO) != 0) {
						ep.setPeriodBalance(moneyFormat.format(dous[0])); // 设置本月金额
																			// 合计
					} else {
						ep.setPeriodBalance(null); // 设置本月金额
													// 合计
					}
					if (dous[1].compareTo(BigDecimal.ZERO) != 0) {
						ep.setYearBalance(moneyFormat.format(dous[1])); // 设置本年累计
																		// 合计
					} else {
						ep.setYearBalance(null); // 设置本年累计 合计
					}
					mapP.put(tr.getReportitem(), dous[0]); // 本月金额map
					mapY.put(tr.getReportitem(), dous[1]); // 本年金额map
				}
			}
			// 集合中添加信息
			listEProfit.add(ep);
		}
		session.setAttribute("downloadProfit", listEProfit);

		map.put("companyName", companyName);
		map.put("listEProfit", listEProfit);
		for (TReportitem tReportitem2 : listReportitem) {
			System.out.println(tReportitem2);
		}
		return map;
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 */
	@RequestMapping(value = { "downloadProfit", "" })
	public void downloadProfit(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		String docsPath = request.getSession().getServletContext().getRealPath("docs");
		String fileName = "利润表_" + System.currentTimeMillis() + ".xlsx";

		List<EProfit> listEProfit = (ArrayList<EProfit>) session.getAttribute("downloadProfit");
		String filePath = docsPath + FILE_SEPARATOR + fileName;
		try {
			// 输出流
			OutputStream os = new FileOutputStream(filePath);
			// 工作区
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("利润表");
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
			cellTitle.setCellValue("利润表");
			/*
			 * 副标题
			 */
			XSSFRow rowSubTitle = sheet.createRow(1);

			TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
			XSSFCell cellSubTitle0 = rowSubTitle.createCell(0);
			cellSubTitle0.setCellStyle(downloadController.cashFlowAndProfitSubHeaderStyle1(wb, sheet));
			cellSubTitle0.setCellValue(companyInfo.getCustomerName());

			String periodStart = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
			String periodEnd = new String(request.getParameter("periodEnd").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
			String year = periodStart.substring(0, 4);
			String month = periodStart.substring(4, 6);
			String accountPeriod = "";
			if (periodStart.equals(periodEnd)) {
				year = periodStart.substring(0, 4);
				month = periodStart.substring(4, 6);
				accountPeriod = year + "年" + month + "月";
			} else {
				String yearSt = periodStart.substring(0, 4);
				String monthSt = periodStart.substring(4, 6);

				String yearEn = periodEnd.substring(0, 4);
				String monthEn = periodEnd.substring(4, 6);
				if (yearSt.equals(yearEn)) {
					accountPeriod = yearSt + "年" + monthSt + "期 - " + monthEn + "期";
				} else {
					accountPeriod = yearSt + "年" + monthSt + "期 -" + yearEn + "年" + monthEn + "期";
				}

			}

			XSSFCell cellSubTitle3 = rowSubTitle.createCell(1);
			cellSubTitle3.setCellStyle(downloadController.cashFlowAndProfitSubHeaderStyle2(wb, sheet));
			cellSubTitle3.setCellValue(accountPeriod);

			String fur = "单位：元"; // 获取货币种类
			XSSFCell cellSubTitle5 = rowSubTitle.createCell(2);
			cellSubTitle5.setCellStyle(downloadController.cashFlowAnProfitSubHeaderStyle3(wb, sheet));
			cellSubTitle5.setCellValue(fur);

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
			for (EProfit pro : listEProfit) {
				XSSFRow rowsInfo = sheet.createRow(n);
				// 给这一行的第一列赋值
				String projectName = pro.getProjectName().replace("&nbsp;", "");
				XSSFCell cellInfo0 = rowsInfo.createCell(0);
				cellInfo0.setCellStyle(downloadController.infoStyle(wb, left));
				cellInfo0.setCellValue(projectName);

				String number = pro.getLineNumber();
				XSSFCell cellInfo1 = rowsInfo.createCell(1);
				cellInfo1.setCellStyle(downloadController.infoStyle(wb, center));
				cellInfo1.setCellValue(number);

				String yb = pro.getYearBalance();// == null
				// || pro.getYearBalance().equals("") ? "0" : pro
				// .getYearBalance();
				// double ybDouble = Double.valueOf(yb);
				// if (ybDouble != 0) {
				// yb = moneyFormat.format(ybDouble);
				// } else {
				// yb = null;
				// }
				XSSFCell cellInfo2 = rowsInfo.createCell(2);
				cellInfo2.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo2.setCellValue(yb);

				String pb = pro.getPeriodBalance();// == null
				// || pro.getPeriodBalance().equals("") ? "" : pro
				// .getPeriodBalance();
				// double pbDouble = Double.valueOf(pb);
				// if (pbDouble != 0) {
				// pb = moneyFormat.format(pbDouble);
				// } else {
				// pb = null;
				// }
				XSSFCell cellInfo3 = rowsInfo.createCell(3);
				cellInfo3.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo3.setCellValue(pb);

				n++;
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

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = { "stampProfit", "" })
	public void stampProfit(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws IOException {
		List<PdfPTable> listPdfPTable = new ArrayList<PdfPTable>();
		String title = "利润表 "; // 标题
		List<EProfit> listEProfit = (ArrayList<EProfit>) session.getAttribute("downloadProfit");
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String sub1 = companyInfo.getCustomerName(); // 上左
		String periodStart = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		String periodEnd = new String(request.getParameter("periodEnd").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		String year = periodStart.substring(0, 4);
		String month = periodStart.substring(4, 6);
		String accountPeriod = "";
		if (periodStart.equals(periodEnd)) {
			year = periodStart.substring(0, 4);
			month = periodStart.substring(4, 6);
			accountPeriod = year + "年" + month + "期";
		} else {
			String yearSt = periodStart.substring(0, 4);
			String monthSt = periodStart.substring(4, 6);

			String yearEn = periodEnd.substring(0, 4);
			String monthEn = periodEnd.substring(4, 6);
			if (yearSt.equals(yearEn)) {
				accountPeriod = yearSt + "年" + monthSt + "期 - " + monthEn + "期";
			} else {
				accountPeriod = yearSt + "年" + monthSt + "期 -" + yearEn + "年" + monthEn + "期";
			}

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
		PdfPTable tableSub = null;
		PdfPTable tableRemarks = null;
		try {
			table = tBuilder.createPdf(fontsPath, 4, 90, new float[] { 0.5f, 0.1f, 0.2f, 0.2f });
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
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "项目", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "行次", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "本年累计金额", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "本月金额", 0, 0));
		for (EProfit pro : listEProfit) {
			String projectName = pro.getProjectName().replace("&nbsp;", "");
			table.addCell(tBuilder.createValueCell(n, left, fontsPath, projectName, 0, 0));

			String number = pro.getLineNumber();
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, number, 0, 0));

			String yb = pro.getYearBalance();// == null
			// || pro.getYearBalance().equals("") ? "0" : pro
			// .getYearBalance();
			// double ybDouble = Double.valueOf(yb);
			// if (ybDouble != 0) {
			// yb = moneyFormat.format(ybDouble);
			// } else {
			// yb = null;
			// }
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, yb, 0, 0));

			String pb = pro.getPeriodBalance();// == null
			// || pro.getPeriodBalance().equals("") ? "" : pro
			// .getPeriodBalance();
			// double pbDouble = Double.valueOf(pb);
			// if (pbDouble != 0) {
			// pb = moneyFormat.format(pbDouble);
			// } else {
			// pb = null;
			// }
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, pb, 0, 0));
		}
		PdfPCell cellTableSub = new PdfPCell(tableSub);
		cellTableSub.setBorderWidth(0);
		tableInfo.addCell(cellTableSub);
		PdfPCell cellTable = new PdfPCell(table);
		cellTable.setBorderWidth(0);
		tableInfo.addCell(cellTable);
		PdfPCell cellTableRemarks = new PdfPCell(tableRemarks);
		cellTableRemarks.setBorderWidth(0);
		tableInfo.addCell(cellTableRemarks);
		listPdfPTable.add(tableInfo);
		// 打印
		stampPdf.createPdf(request, response, listPdfPTable, title);
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
		String listBalanceP = formula;
		String listBalanceY = formula;
		/**
		 * 处理本期的
		 */
		 for (String str : listVariable) {
			 Object mapStr = mapP.get(str) == null ? BigDecimal.ZERO : mapP.get(str);
			 listBalanceP=listBalanceP.replace("["+str+"]", mapStr.toString());
	    }
		 /**
		  * 处理本年的
		  */
		 for (String str : listVariable) { 
			 Object mapStr = mapY.get(str) == null ? BigDecimal.ZERO : mapY.get(str);
			 listBalanceY=listBalanceY.replace("["+str+"]", mapStr.toString());
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

	/**
	 * 获取同期同客户的所有的公式
	 * 
	 * @param accountPeriod
	 * @param periodEnd
	 * @param fdbid
	 * @return
	 */
	public Map<String, List<Reportbalance>> getListReportbalancePeriod(String accountPeriod, String periodEnd, String fdbid, List<TReportitem> listTReportitem,String reportId) {
		Map<String, List<Reportbalance>> mapPeriod = new HashMap<String, List<Reportbalance>>();
	
		List<Reportbalance> listReportbalance = reportbalanceService.findListByReportId(accountPeriod, periodEnd, reportId, fdbid);
		List<Reportbalance> listRep = new ArrayList<Reportbalance>();
		for (TReportitem rep : listTReportitem) {
			for (Reportbalance re : listReportbalance) {
				if (re.getReportitem().equals(rep.getReportitem())) {
					listRep.add(re);
				}
			}
			mapPeriod.put(rep.getReportitem(), listRep);
			listRep = new ArrayList<Reportbalance>();
		}
		return mapPeriod;
	}

	/**
	 * 本年累计 和本月合计
	 * 
	 * @param accountPeriod
	 * @param reportitem
	 * @return
	 */
	/*
	 * public double[] findPeriodBalance(String accountPeriod, String periodEnd,
	 * String reportItem, String fdbid) { double doup = 0; // 本月合计 double douy =
	 * 0; // 本年合计 List<Reportbalance> listReportbalance = reportbalanceService
	 * .findListByReItem(accountPeriod, periodEnd, reportItem, fdbid, null);
	 * listReportbalance = this.findPeriodBalanceHandle(listReportbalance); //
	 * 借来的钱 减去 贷出去钱就是利润 for (Reportbalance re : listReportbalance) { if
	 * (re.getAccDc().equals("1")) { // 借方科目
	 * 
	 * String getAmountfor = re.getAmountfor() == null ||
	 * re.getAmountfor().equals("") ? // 获取本月金额（实际损益发生额） "0" :
	 * re.getAmountfor(); double getAmountforDouble =
	 * Double.valueOf(getAmountfor); // 转为double计算 doup += getAmountforDouble;
	 * // 计算合计金额 月
	 * 
	 * String getYtdamountfor = re.getYtdamountfor() == null ||
	 * re.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额） "0" :
	 * re.getYtdamountfor(); double getYtdamountforDouble =
	 * Double.valueOf(getYtdamountfor); // 转为double用于计算 douy +=
	 * getYtdamountforDouble; // 计算合计金额 年 } if (re.getAccDc().equals("-1")) { //
	 * 贷方科目 String getAmountfor = re.getAmountfor() == null ||
	 * re.getAmountfor().equals("") ? // 获取本月金额（实际损益发生额） "0" :
	 * re.getAmountfor(); double getAmountforDouble = 0 -
	 * Double.valueOf(getAmountfor); // 转为double计算 doup += getAmountforDouble;
	 * // 计算合计金额 月
	 * 
	 * String getYtdamountfor = re.getYtdamountfor() == null ||
	 * re.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额） "0" :
	 * re.getYtdamountfor(); double getYtdamountforDouble = 0 - Double
	 * .valueOf(getYtdamountfor); // 转为double计算 douy += getYtdamountforDouble;
	 * // 计算合计金额 年 } }
	 * 
	 * double[] dous = { doup, douy }; return dous; }
	 */
	/**
	 * 本年累计 和本月合计
	 * 
	 * @param accountPeriod
	 * @param reportitem
	 * @return
	 */
	public BigDecimal[] findPeriodBalance(Map<String, List<Reportbalance>> mapPeriod, String reportItem) {
		BigDecimal doup = BigDecimal.ZERO; // 本月合计
		BigDecimal douy = BigDecimal.ZERO; // 本年合计
		List<Reportbalance> listReportbalance = mapPeriod.get(reportItem);
		listReportbalance = this.findPeriodBalanceHandle(listReportbalance);
		// 借来的钱 减去 贷出去钱就是利润
		for (Reportbalance re : listReportbalance) {
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

		BigDecimal[] dous = { doup, douy };
		return dous;
	}

	/**
	 * 处理同科目不同期的 便于多期查询
	 * 
	 * @param listReportbalance
	 * @return
	 */
	public List<Reportbalance> findPeriodBalanceHandle(List<Reportbalance> listReportbalance) {
		List<Reportbalance> listReportbalanceNew = new ArrayList<Reportbalance>();
		for (Reportbalance r : listReportbalance) {
			double getAmountforDouble = 0; // 本月合计
			double getYtdamountforDouble = 0; // 本年合计
			for (Reportbalance re : listReportbalance) {
				if (r.getAcctReportItemId().equals(re.getAcctReportItemId()) // 判断同公式
																				// 同科目
																				// 同期
						&& r.getAccId().equals(re.getAccId()) && r.getAccountPeriod().equals(re.getAccountPeriod())) {
					String getAmountfor = re.getAmountfor() == null || re.getAmountfor().equals("") ? // 获取本月金额（实际损益发生额）
					"0"
							: re.getAmountfor();
					getAmountforDouble = Double.valueOf(getAmountfor); // 转为double计算

					String getYtdamountfor = re.getYtdamountfor() == null || re.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额）
					"0"
							: re.getYtdamountfor();
					getYtdamountforDouble = Double.valueOf(getYtdamountfor); // 转为double用于计算
				}
				if (r.getAcctReportItemId().equals(re.getAcctReportItemId()) // 判断同公式
																				// 同科目
																				// 不
																				// 同期
						&& r.getAccId().equals(re.getAccId()) && !r.getAccountPeriod().equals(re.getAccountPeriod())) {
					String getAmountfor = re.getAmountfor() == null || re.getAmountfor().equals("") ? // 获取本月金额（实际损益发生额）
					"0"
							: re.getAmountfor();
					getAmountforDouble += Double.valueOf(getAmountfor); // 转为double计算

					int accountPeriodR = Integer.valueOf(r.getAccountPeriod());
					int accountPeriodRe = Integer.valueOf(re.getAccountPeriod());

					if (accountPeriodR < accountPeriodRe) { // 取最大期数的
						String getYtdamountfor = re.getYtdamountfor() == null || re.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额）
						"0"
								: re.getYtdamountfor();
						getYtdamountforDouble = Double.valueOf(getYtdamountfor); // 转为double用于计算
					} else {
						String getYtdamountfor = r.getYtdamountfor() == null || r.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额）
						"0"
								: r.getYtdamountfor();
						getYtdamountforDouble = Double.valueOf(getYtdamountfor); // 转为double用于计算
					}

				}

			}
			Reportbalance rep = r;
			if (getYtdamountforDouble != 0) {
				// rep.setYtdamountfor(moneyFormat.format(getYtdamountforDouble));
				// // 本年累计
				rep.setYtdamountfor(String.valueOf(getYtdamountforDouble)); // 本年累计
			} else {
				rep.setYtdamountfor(String.valueOf(getYtdamountforDouble)); // 本年累计
			}
			if (getAmountforDouble != 0) {
				// rep.setAmountfor(moneyFormat.format(getAmountforDouble));
				rep.setAmountfor(String.valueOf(getAmountforDouble));
			} else {
				rep.setAmountfor(String.valueOf(getAmountforDouble));
			}

			if (listReportbalance.size() == 0) { // 如果没有值 返回null
				rep = null;
			}// 本月合计
			listReportbalanceNew.add(rep);
		}
		return listReportbalanceNew;

	}

	/**
	 * 获取所有的账期
	 * 
	 * @param model
	 * @param companyId
	 */
	public void getAllAccountPeriod(Model model, String companyId, String defaultPeriod) {
		List<TVoucher> listTVoucher = tVoucherService.finperiods(companyId);
		// if (listTVoucher.size() <= 0) {
		// TVoucher voucher = new TVoucher();
		// voucher.setAccountPeriod(defaultPeriod);
		// listTVoucher.add(voucher);
		// }
		boolean flag = false;
		for (TVoucher tVoucher : listTVoucher) {
			if (tVoucher.getAccountPeriod().equals(defaultPeriod)) {
				flag = true; // 如果存在
			}
		}
		// 如果不存在
		if (!flag) {
			TVoucher voucher = new TVoucher();
			voucher.setAccountPeriod(defaultPeriod);
			listTVoucher.add(voucher);
		}
		model.addAttribute("listTVoucher", listTVoucher);
	}

	/**
	 * 获取公司客户信息
	 */
	public TCustomer getCompanyInfo(Model model, HttpSession session) {
		Object obj = session.getAttribute("sessionCustomer");
		TCustomer companyInfo = (TCustomer) obj; // 客户公司信息对象
		return companyInfo;

	}

	/**
	 * 显示所有发生金额变化的科目
	 * 
	 * @param model返回
	 *            第一个科目的id 用于显示
	 */
	public String listAccount(Model model, String companyId) {
		List<TAccount> listAccounts = tAccountService.findAllAccountByFdbid(companyId);
		model.addAttribute("listAcc", listAccounts);
		if (listAccounts.size() > 0) { // 判断有没有科目
			return listAccounts.get(0).getId();
		} else { // 如果没有科目
			return null;
		}
	}
}