/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.books.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mysql.fabric.xmlrpc.base.Data;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.books.entity.Accountbalance;
import com.thinkgem.jeesite.modules.books.entity.GeneralLedger;
import com.thinkgem.jeesite.modules.books.entity.SubjectBalance;
import com.thinkgem.jeesite.modules.books.entity.Subsidiary;
import com.thinkgem.jeesite.modules.books.entity.Subsidiaryledge;
import com.thinkgem.jeesite.modules.books.service.AccountbalanceService;
import com.thinkgem.jeesite.modules.books.service.SubsidiaryledgeService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.download.DownloadController;
import com.thinkgem.jeesite.modules.stamp.StampPdf;
import com.thinkgem.jeesite.modules.stamp.TableBuilder;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucher.service.TVoucherService;

/**
 * 总账Controller
 * 
 * @author zhangtong
 * @version 2015-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/books/generalLedger")
public class GeneralLedgerController extends BaseController {

	@Autowired
	private TBalanceService tBalanceService;
	@Autowired
	private TVoucherService tVoucherService;
	@Autowired
	private AccountbalanceService accountbalanceService;
	@Autowired
	private TAccountService tAccountService;
	@Autowired
	private DownloadController downloadController;
	@Autowired
	private TableBuilder tBuilder;
	private List<Accountbalance> listAccs;

	public List<Accountbalance> getListAccs() {
		return listAccs;
	}

	public void setListAccs(List<Accountbalance> listAccs) {
		this.listAccs = listAccs;
	}

	@Autowired
	private StampPdf stampPdf;
	public static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
	public static NumberFormat moneyFormat = new DecimalFormat("#,##0.00");

	@RequestMapping(value = { "listGeneralLedger", "" })
	public String listGeneralLedger(Model model) {
		return "modules/books/vGeneralLedger";
	}

	// @RequiresPermissions("books:accountbalance:view")
	@RequestMapping(value = { "list", "" })
	@ResponseBody
	public Object list(String period, String accId, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		Map<String, Object> map = Maps.newHashMap();
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();
		String startPeriod = companyInfo.getInitperiod();

		List<Accountbalance> listAccountbalance = accountbalanceService.findListByAccIdLikeYear(period + "%", accId, companyId, null); // 根据年份和科目查询所有的信息
		List<GeneralLedger> listGeneralLedger = new ArrayList<GeneralLedger>(); // 保存信息
																				// 显示与页面
		for (Accountbalance acc : listAccountbalance) {

			if (Integer.valueOf(acc.getAccountPeriod()) >= Integer.valueOf(startPeriod)) {
				listGeneralLedger.add(fingPeriodStart(acc)); // 期初余额
				listGeneralLedger.add(fingThisPeriod(acc)); // 本期合计
				listGeneralLedger.add(fingYearTotal(acc)); // 本年累计
			}
		}
		session.setAttribute("downloadGeneral", listGeneralLedger);
		map.put("listGeneralLedger", listGeneralLedger);
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
	@RequestMapping(value = { "downloadGeneral", "" })
	public void downloadGeneral(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		String docsPath = request.getSession().getServletContext().getRealPath("docs");
		String fileName = "总账_" + System.currentTimeMillis() + ".xlsx";

		List<GeneralLedger> listGeneralLedger = (ArrayList<GeneralLedger>) session.getAttribute("downloadGeneral");
		String filePath = docsPath + FILE_SEPARATOR + fileName;
		try {

			String accountPeriod = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期

			String periodStart = accountPeriod + "01";
			String periodEnd = accountPeriod + "12";
			String accountName = new String(request.getParameter("accountName").getBytes("ISO-8859-1"), "utf-8"); // 科目名称
			String year = periodStart.substring(0, 4);
			String month = periodStart.substring(4, 6);
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
			// 输出流
			OutputStream os = new FileOutputStream(filePath);
			// 工作区
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(accountPeriod + "利润表");
			sheet.setVerticallyCenter(true);
			/**
			 * 资产负债的宽度
			 */
			downloadController.generalLedgerStyleWidth(sheet);

			/*
			 * 列名
			 */
			XSSFRow rowColumn = sheet.createRow(0);

			XSSFCell cellColumn0 = rowColumn.createCell(0);
			cellColumn0.setCellStyle(downloadController.columnStyle(wb));
			cellColumn0.setCellValue("期间");
			XSSFCell cellColumn1 = rowColumn.createCell(1);
			cellColumn1.setCellStyle(downloadController.columnStyle(wb));
			cellColumn1.setCellValue("摘要");
			XSSFCell cellColumn2 = rowColumn.createCell(2);
			cellColumn2.setCellStyle(downloadController.columnStyle(wb));
			cellColumn2.setCellValue("借方");
			XSSFCell cellColumn3 = rowColumn.createCell(3);
			cellColumn3.setCellStyle(downloadController.columnStyle(wb));
			cellColumn3.setCellValue("借方");
			XSSFCell cellColumn4 = rowColumn.createCell(4);
			cellColumn4.setCellStyle(downloadController.columnStyle(wb));
			cellColumn4.setCellValue("方向");
			XSSFCell cellColumn5 = rowColumn.createCell(5);
			cellColumn5.setCellStyle(downloadController.columnStyle(wb));
			cellColumn5.setCellValue("余额");
			int n = 1;
			String left = "left";
			String center = "center";
			String right = "right";
			for (GeneralLedger gl : listGeneralLedger) {
				XSSFRow rowsInfo = sheet.createRow(n);
				// 给这一行的第一列赋值
				String accountPeriodI = gl.getAccountPeriod();
				XSSFCell cellInfo0 = rowsInfo.createCell(0);
				cellInfo0.setCellStyle(downloadController.infoStyle(wb, left));
				cellInfo0.setCellValue(accountPeriodI);

				String exp = gl.getExp();
				XSSFCell cellInfo1 = rowsInfo.createCell(1);
				cellInfo1.setCellStyle(downloadController.infoStyle(wb, left));
				cellInfo1.setCellValue(exp);

				String debit = gl.getDebit();// == null
				// || gl.getDebit().equals("") ? "0" : gl.getDebit();
				// double debitDouble = Double.valueOf(debit);
				// if(debitDouble!=0){
				// debit= moneyFormat.format(debitDouble);
				// }else {
				// debit= null;
				// }
				XSSFCell cellInfo2 = rowsInfo.createCell(2);
				cellInfo2.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo2.setCellValue(debit);

				String credit = gl.getCredit();// == null
				// || gl.getCredit().equals("")
				// ? "0" : gl.getCredit();
				// double creditDouble = Double.valueOf(credit);
				// if (creditDouble!=0) {
				// credit = moneyFormat.format(creditDouble);
				// } else {
				// credit = null;
				// }
				XSSFCell cellInfo3 = rowsInfo.createCell(3);
				cellInfo3.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo3.setCellValue(credit);

				String dc = "";
				if (gl.getDc().equals("1")) {
					dc = "借";
				} else {
					dc = "贷";
				}
				XSSFCell cellInfo4 = rowsInfo.createCell(5);
				cellInfo4.setCellStyle(downloadController.infoStyle(wb, center));
				cellInfo4.setCellValue(dc);

				String endbalance = gl.getEndbalance();// == null
				// || gl.getEndbalance().equals("") ? "0" : gl
				// .getEndbalance();
				// double endbalanceDouble =Double.valueOf(endbalance);
				// if (endbalanceDouble!=0) {
				// endbalance=moneyFormat.format(endbalanceDouble);
				// } else {
				// endbalance=null;
				// }
				XSSFCell cellInfo5 = rowsInfo.createCell(5);
				cellInfo5.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo5.setCellValue(endbalance);

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
	 * 打印
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = { "stampGeneral", "" })
	public void stampGeneral(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws IOException {
		List<GeneralLedger> listGeneralLedger = (ArrayList<GeneralLedger>) session.getAttribute("downloadGeneral");
		String accountName = new String(request.getParameter("accountName").getBytes("ISO-8859-1"), "utf-8"); // 科目名称
		String accountPeriod = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		String title = "总账 "; // 标题
		List<PdfPTable> listPdfPTable = new ArrayList<PdfPTable>();
		PdfPTable tableInfo = stampPdfTable(request, response, session, model, listGeneralLedger, accountName, accountPeriod);
		listPdfPTable.add(tableInfo);
		stampPdf.createPdf(request, response, listPdfPTable, title);
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
	@RequestMapping(value = { "stampYearGeneral", "" })
	public void stampYearGeneral(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws IOException {
		// 显示与页面
		List<Accountbalance> listAcc = this.getListAccs(); // 获取所有的科目对象
		List<String> accIdS = new ArrayList<String>(); // 保存所有的科目id
		String accountPeriod = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String startPeriod = companyInfo.getInitperiod();
		for (Accountbalance acc : listAcc) {
			accIdS.add(acc.getId());
		}
		String title = "总账 "; // 标题
		List<Accountbalance> listAccountbalance = accountbalanceService.findListByAccIdLikeYear(accountPeriod + "%", null, companyId, accIdS); // 根据年份和科目查询所有的信息
		List<PdfPTable> listPdfPTable = new ArrayList<PdfPTable>();
		for (Accountbalance accInfo : listAcc) {
			List<GeneralLedger> listGeneralLedger = new ArrayList<GeneralLedger>(); // 保存信息
			String accountName = accInfo.getAccuntId() + "-" + accInfo.getAccountName();
			for (Accountbalance acc : listAccountbalance) {
				if (accInfo.getId().equals(acc.getId())) {
					if (Integer.valueOf(acc.getAccountPeriod()) >= Integer.valueOf(startPeriod)) {
						listGeneralLedger.add(fingPeriodStart(acc)); // 期初余额
						listGeneralLedger.add(fingThisPeriod(acc)); // 本期合计
						listGeneralLedger.add(fingYearTotal(acc)); // 本年累计
					}
				}
			}
			PdfPTable tableInfo = stampPdfTable(request, response, session, model, listGeneralLedger, accountName, accountPeriod);
			listPdfPTable.add(tableInfo);
		}

		stampPdf.createPdf(request, response, listPdfPTable, title);

	}

	private PdfPTable stampPdfTable(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, List<GeneralLedger> listGeneralLedger, String accountName, String accountPeriod) throws IOException {
		String periodStart = accountPeriod + "01";
		String periodEnd = accountPeriod + "12";
		String year = periodStart.substring(0, 4);
		String month = periodStart.substring(4, 6);
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
		String sub1 = "科目：" + accountName; // 上左
		String sub2 = accountPeriod; // 上中
		String fur = "单位：元"; // 获取货币种类
		String sub3 = fur; // 上右
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String remarks = companyInfo.getCustomerName(); // 下作
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 打印时间
		String stampTime = "打印时间：" + sdf.format(new Date());

		String fontsPath = request.getSession().getServletContext().getRealPath("Fonts");
		PdfPTable tableInfo = new PdfPTable(1);
		tableInfo.setWidthPercentage(100);
		PdfPTable table = null;
		PdfPTable tableSub = null;
		PdfPTable tableRemarks = null;
		try {
			table = tBuilder.createPdf(fontsPath, 6, 90, new float[] { 0.06f, 0.12f, 0.06f, 0.06f, 0.03f, 0.06f });
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
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "期间", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "摘要", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "借方", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "贷方", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "方向", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "余额", 0, 0));

		for (GeneralLedger gl : listGeneralLedger) {
			String accountPeriodI = gl.getAccountPeriod();
			table.addCell(tBuilder.createValueCell(n, left, fontsPath, accountPeriodI, 0, 0));

			String exp = gl.getExp();
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, exp, 0, 0));

			String debit = gl.getDebit();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, debit, 0, 0));

			String credit = gl.getCredit();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, credit, 0, 0));

			String dc = "";
			if (gl.getDc().equals("1")) {
				dc = "借";
			} else {
				dc = "贷";
			}
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, dc, 0, 0));

			String endbalance = gl.getEndbalance();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, endbalance, 0, 0));
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

		return tableInfo;
		// 打印
		// stampPdf.createPdf(request, response, tableInfo,
		// title);
	}

	/**
	 * 根据父级的id 查询子级的父级id
	 * 
	 * @return
	 */
	public Accountbalance findSublevelGeneral(Accountbalance acc) {
		String fdbid = acc.getFdbid();
		String accountPeriod = acc.getAccountPeriod();
		String periodEnd = acc.getPeriodEnd();
		String parentId = acc.getParent().getId();
		List<Accountbalance> accList = accountbalanceService.findSublevelAcc(fdbid, accountPeriod, periodEnd, parentId);
		return this.flagSublevel(accList);

	}

	/**
	 * 判断有没有子级
	 * 
	 * @param acc
	 * @return
	 */
	public Accountbalance flagSublevel(List<Accountbalance> accList) {
		Accountbalance accountbalance = null;
		for (Accountbalance a : accList) {
			if (a.getDetail().equals("0")) { // 有子级
				accountbalance = this.findSublevelGeneral(a);
			}
			if (a.getDetail().equals("0")) { // 无子级
				accountbalance = a;
			}
		}
		return accountbalance;
	}

	/**
	 * 期初金额变化
	 * 
	 * @param acc
	 * @return
	 */
	public GeneralLedger fingPeriodStart(Accountbalance acc) {
		GeneralLedger gen = new GeneralLedger();
		gen.setId(acc.getId()); // 科目id
		gen.setAccountPeriod(acc.getAccountPeriod()); // 账期
		gen.setExp("期初余额");
		String dc = "";
		String stringBeginbalance = acc.getBeginbalance() == null || acc.getBeginbalance().equals("") ? "0" : acc.getBeginbalance(); // 为空
																																		// 转为0
																																		// 防止
																																		// 转换异常
		double doubleBeginbalance = 0;
		if (acc.getDc().equals("1")) {
			dc = "借";
			doubleBeginbalance = Double.valueOf(stringBeginbalance);

		} else {
			dc = "贷";
			doubleBeginbalance = 0 - Double.valueOf(stringBeginbalance);

		}
		if (doubleBeginbalance != 0) { // 如果不等于0
			gen.setEndbalance(moneyFormat.format(doubleBeginbalance));
		} else {
			gen.setEndbalance(null);
		}

		gen.setDc(dc);
		return gen;
	}

	/**
	 * 本期合计金额变化
	 * 
	 * @param acc
	 * @return
	 */
	public GeneralLedger fingThisPeriod(Accountbalance acc) {
		GeneralLedger gen = new GeneralLedger();
		gen.setId(acc.getId()); // 科目id
		gen.setAccountPeriod(acc.getAccountPeriod()); // 账期
		gen.setExp("本期合计");
		String debitTotalString = acc.getDebittotalamount();
		double debitTotalDouble = Double.valueOf(debitTotalString);
		gen.setDebit(moneyFormat.format(debitTotalDouble)); // 借
		String creditTotalString = acc.getCredittotalamount();
		double creditTotalDouble = Double.valueOf(creditTotalString);
		gen.setCredit(moneyFormat.format(creditTotalDouble)); // 贷
		String dc = "";
		String endBalanceString = acc.getEndbalance() == null || acc.getEndbalance().equals("") ? "0" : acc.getEndbalance();
		double endBalanceDouble = 0;
		if (acc.getDc().equals("1")) {
			dc = "借";
			endBalanceDouble = Double.valueOf(endBalanceString);
		} else {
			dc = "贷";
			endBalanceDouble = 0 - Double.valueOf(endBalanceString);
		}
		gen.setDc(dc); // 借贷方向
		String pdTotalString = acc.getDebittotalamount() == null || acc.getDebittotalamount().equals("") ? "0" : acc.getDebittotalamount(); // debit
		String pcTotalString = acc.getCredittotalamount() == null || acc.getCredittotalamount().equals("") ? "0" : acc.getCredittotalamount(); // credit

		double pdTotalDouble = Double.valueOf(pdTotalString);
		double pcTotalDouble = Double.valueOf(pcTotalString);

		if (endBalanceDouble != 0) {
			gen.setEndbalance(moneyFormat.format(endBalanceDouble));
		} else {
			gen.setEndbalance(null);
		}
		if (pcTotalDouble != 0) {
			gen.setCredit(moneyFormat.format(pcTotalDouble));
		} else {
			gen.setCredit(null);
		}
		if (pdTotalDouble != 0) {
			gen.setDebit(moneyFormat.format(pdTotalDouble));
		} else {
			gen.setDebit(null);
		}

		return gen;
	}

	/**
	 * 本年累计金额变化
	 * 
	 * @param acc
	 * @return
	 */
	public GeneralLedger fingYearTotal(Accountbalance acc) {
		GeneralLedger gen = new GeneralLedger();
		gen.setId(acc.getId()); // 科目id
		gen.setAccountPeriod(acc.getAccountPeriod()); // 账期
		gen.setExp("本年累计");
		gen.setDebit(acc.getYtddebittotalamount()); // 借
		gen.setCredit(acc.getYtdcredittotalamount()); // 贷
		String dc = "";
		String endbalanceString = acc.getEndbalance() == null || acc.getEndbalance().equals("") ? "0" : acc.getEndbalance();
		double endBalanceDouble = 0;
		if (acc.getDc().equals("1")) {
			dc = "借";
			endBalanceDouble = Double.valueOf(endbalanceString);
		} else {
			dc = "贷";
			endBalanceDouble = 0 - Double.valueOf(endbalanceString);
		}
		gen.setDc(dc); // 借贷方向

		String ydTotalString = acc.getYtddebittotalamount() == null || acc.getYtddebittotalamount().equals("") ? "0" : acc.getYtddebittotalamount();
		String ycTotalString = acc.getYtdcredittotalamount() == null || acc.getYtdcredittotalamount().equals("") ? "0" : acc.getYtdcredittotalamount();
		double ydTotalDouble = Double.valueOf(ydTotalString);
		double ycTotalDouble = Double.valueOf(ycTotalString);
		if (endBalanceDouble != 0) {
			gen.setEndbalance(moneyFormat.format(endBalanceDouble));
		} else {
			gen.setEndbalance(null);
		}
		if (ycTotalDouble != 0) {
			gen.setCredit(moneyFormat.format(ycTotalDouble));
		} else {
			gen.setCredit(null);
		}
		if (ydTotalDouble != 0) {
			gen.setDebit(moneyFormat.format(ydTotalDouble));
		} else {
			gen.setDebit(null);
		}
		return gen;
	}

	/**
	 * 显示所有发生金额变化的科目
	 * 
	 * @param model返回
	 *            第一个科目的id 用于显示
	 */
	@RequestMapping(value = { "listHappenAccount", "" })
	@ResponseBody
	public Object listHappenAccount(Model model, String period, HttpSession session) {
		Map<String, Object> map = Maps.newHashMap();
		Accountbalance defaultAccount = null;
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String accountPeriod = period + "01";
		String periodEnd = period + "12";
		String accLevel = "1"; // 一级科目
		List<Accountbalance> listAccounts = accountbalanceService.findHappenByAcc(companyId, accountPeriod, periodEnd, null, accLevel);
		this.setListAccs(listAccounts);
		if (CollectionUtils.isNotEmpty(listAccounts)) { // 判断有没有科目
			defaultAccount = listAccounts.get(0);
		}
		;
		map.put("listAccounts", listAccounts);
		map.put("defaultAccount", defaultAccount);
		return map;
	}

	/**
	 * 根据科目id查询 它发生的所有的账期取年份 总账页面调用
	 * 
	 * @param accId
	 * @return
	 */
	@RequestMapping(value = { "listHappenYear", "" })
	@ResponseBody
	public Object listHappenYear( HttpSession session, Model model) {
		Map<String, Object> map = Maps.newHashMap();
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		List<String> listAccountperiod = tBalanceService.getAllAccountperiod(companyId);
		Calendar calendar = Calendar.getInstance();
		List<String> listRepeatYear = new ArrayList<String>(); // 保存年份重复的
		if (CollectionUtils.isNotEmpty(listAccountperiod)) {
			for (String tv : listAccountperiod) {
				String year = tv.substring(0, 4); // 截取年份保存
				listRepeatYear.add(year);
			}
		} else {
			listRepeatYear.add(String.valueOf(calendar.get(Calendar.YEAR)));
		}

		List<String> listYear = this.removeRepeat(listRepeatYear); // 保存年份
		String defaultYear = null;
		if (listYear.size() > 0) {
			defaultYear = listYear.get(0);
		} else {
			defaultYear = String.valueOf(calendar.get(Calendar.YEAR));
		}
		map.put("listYear", listYear);
		map.put("defaultYear", defaultYear);
		return map;
	}

	/**
	 * 取出重复的年份
	 * 
	 * @param list
	 * @return
	 */
	public List<String> removeRepeat(List<String> list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	/**
	 * 获取公司客户信息
	 */
	public TCustomer getCompanyInfo(Model model, HttpSession session) {
		Object obj = session.getAttribute("sessionCustomer");
		TCustomer companyInfo = (TCustomer) obj; // 客户公司信息对象
		String companyName = companyInfo.getCustomerName();
		model.addAttribute("companyName", companyName);
		return companyInfo;

	}

}