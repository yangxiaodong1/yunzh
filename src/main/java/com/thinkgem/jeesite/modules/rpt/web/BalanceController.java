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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.fop.render.rtf.rtflib.rtfdoc.RtfAfter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.acctreportitem.entity.TAcctreportitem;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.download.DownloadController;
import com.thinkgem.jeesite.modules.rpt.entity.EBalance;
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
 * 资产负债报表Controller
 * 
 * @author zhangtong
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/balance")
public class BalanceController extends BaseController {

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
	 * 资产负债
	 * 
	 * @param eBalance
	 * @param tReportitem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "listBalance", "" })
	public String listBalance(Model model) {

		return "modules/rpt/vBalanceList";
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
		String reportId = "";
		if (companySystem == 1) {	//小企业
			reportId = "100008"; // 报表分组 100008 是资产负债表的
		} else if(companySystem == 2) {	//新规则
			reportId = "100005"; // 报表分组 100005 是资产负债表的
		}
		tReportitem.setReportId(reportId);
		tReportitem.setFdbid(companyId); // 公司客户id
		// 第一次加载时候 账期为当前账期
		if (StringUtils.isNoneEmpty(accountPeriod) && StringUtils.isNoneEmpty(periodEnd)) {
			tReportitem.setAccountPeriod(defaultPeriod);
			tReportitem.setPeriodEnd(defaultPeriod);
		}

		// 根据账期 select 报表信息
		List<TReportitem> listTReportitem = tReportitemService.findList(tReportitem); // 转为list集合
		Map<String, Object> map = Maps.newHashMap();

		List<TReportitem> listAs = Lists.newArrayList(); // 资产类list
		List<TReportitem> listLi = Lists.newArrayList(); // 负债类list

		Map<String, BigDecimal> mapAsP = Maps.newHashMap(); // 资产类期map
		Map<String, BigDecimal> mapLiP = Maps.newHashMap(); // 负债类期map

		Map<String, BigDecimal> mapAsY = Maps.newHashMap(); // 资产类年map
		Map<String, BigDecimal> mapLiY = Maps.newHashMap(); // 负债类年map
		Map<String, List<Reportbalance>> mapPeriod = this.getListReportbalancePeriod(accountPeriod, periodEnd, companyId, listTReportitem, reportId);
		Map<String, List<Reportbalance>> mapYear = this.getListReportbalanceYear(accountPeriod, periodEnd, companyId, listTReportitem, reportId);

		int n = 0;
		int numAs = 0; // 资产的行次
		int numLi = 0; // 负债的行次
		// 一个循环遍历分组存储到两个集合中
		int listTReportitemSize = listTReportitem.size();
		
		for (TReportitem tr : listTReportitem) {
			n++;
			if (n <= listTReportitemSize/2) {
				if(!tr.getDatasource().equals("0")){
					numLi ++;
				}
				listAs.add(tr);
			} else {
				listLi.add(tr);
			}
		}
		// 转换为迭代器
		Iterator<TReportitem> iteratorAs = listAs.iterator();
		Iterator<TReportitem> iteratorLi = listLi.iterator();
		// 声明一个新的list集合 保存数据用于显示于页面
		List<EBalance> listEBalance = new ArrayList<EBalance>();		
		while (iteratorAs.hasNext() && iteratorLi.hasNext()) {
			TReportitem t1 = iteratorAs.next();
			TReportitem t2 = iteratorLi.next();
			EBalance eB = new EBalance();
			// 以下是分别赋值
			eB.setAsId(t1.getId());
			eB.setAsReportItem(t1.getReportitem());
			// 取出特殊符号|
			String assets = t1.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;");
			eB.setAssets(assets.equals("null") ? null : assets);
			if (t1.getDatasource().equals("0")) {
				eB.setAsIsALabel(false);
			} else {
				numAs += 1; // 资产行次+1
				eB.setAsLineNumber(String.valueOf(numAs));
				if (t1.getDatasource().equals("1")) {
					eB.setAsIsALabel(false); // 是超链接

					// 获取金额合计
					BigDecimal asPeriodEnd = this.endPeriod(mapPeriod, t1.getReportitem()); // 获取金额合计
					if (asPeriodEnd.compareTo(BigDecimal.ZERO) != 0) {
						eB.setAsPeriodEnd(moneyFormat.format(asPeriodEnd)); // 期末金额
					} else {
						eB.setAsPeriodEnd(null); // 期末金额
					}

					mapAsP.put(t1.getReportitem(), asPeriodEnd); // 资产类期map添加键值

					BigDecimal asYearStart = this.startYear(mapYear, t1.getReportitem());
					if (asYearStart.compareTo(BigDecimal.ZERO) != 0) {
						eB.setAsYearStart(moneyFormat.format(asYearStart)); // 年初金额
					} else {
						eB.setAsYearStart(null); // 年初金额
					}
					mapAsY.put(t1.getReportitem(), asYearStart); // 资产类年map添加键值

				}
				if (t1.getDatasource().equals("2")) {
					eB.setAsIsALabel(false); // 不是超链接

					BigDecimal asPeriodEnd = this.totalTotal(t1.getFormula(), mapAsP); // 期末金额
																						// 合计
					if (asPeriodEnd.compareTo(BigDecimal.ZERO) != 0) {
						eB.setAsPeriodEnd(moneyFormat.format(asPeriodEnd));
					} else {
						eB.setAsPeriodEnd(null);
					}

					mapAsP.put(t1.getReportitem(), asPeriodEnd); // 资产类期map添加键值
																	// 合计

					BigDecimal asYearStart = this.totalTotal(t1.getFormula(), mapAsY); // 年初金额
																						// 合计
					if (asYearStart.compareTo(BigDecimal.ZERO) != 0) {
						eB.setAsYearStart(moneyFormat.format(asYearStart));
					} else {
						eB.setAsYearStart(null);
					}

					mapAsY.put(t1.getReportitem(), asYearStart); // 资产类年map添加键值
																	// 合计
				}
				if (t1.getDatasource().equals("3")) {
					eB.setAsIsALabel(false); // 不是超链接

					BigDecimal asPeriodEnd = this.totalTotal(t1.getFormula(), mapAsP); // 期末金额
																						// 合计合计
					if (asPeriodEnd.compareTo(BigDecimal.ZERO) != 0) {
						eB.setAsPeriodEnd(moneyFormat.format(asPeriodEnd));
					} else {
						eB.setAsPeriodEnd(null);
					}

					mapAsP.put(t1.getReportitem(), asPeriodEnd); // 资产类期map添加键值
																	// 合计

					BigDecimal asYearStart = this.totalTotal(t1.getFormula(), mapAsY); // 年初金额
																						// 合计合计
					if (asYearStart.compareTo(BigDecimal.ZERO) != 0) {
						eB.setAsYearStart(moneyFormat.format(asYearStart));
					} else {
						eB.setAsYearStart(null);
					}

					mapAsY.put(t1.getReportitem(), asYearStart); // 资产类年map添加键值
																	// 合计
				}
			}
			eB.setLiId(t2.getId());
			eB.setLiReportItem(t2.getReportitem());
			String liabilities = t2.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;");
			eB.setLiabilities(liabilities.equals("null") ? null : liabilities);
			if (t2.getDatasource().equals("0")) {
				eB.setLiIsALabel(false);
			} else {
				numLi += 1;
				eB.setLiLineNumber(String.valueOf(numLi)); // 行记
				if (t2.getDatasource().equals("1")) {
					eB.setLiIsALabel(false); // 是超链接

					// 期末金额合计
					BigDecimal liPeriodEnd = this.endPeriod(mapPeriod, t2.getReportitem()); // 期末金额合计
					if (liPeriodEnd.compareTo(BigDecimal.ZERO) != 0) {
						eB.setLiPeriodEnd(moneyFormat.format(liPeriodEnd)); // 期末金额
					} else {
						eB.setLiPeriodEnd(null); // 期末金额
					}

					mapLiP.put(t2.getReportitem(), liPeriodEnd); // 负债类期map添加键值

					BigDecimal liYearStart = this.startYear(mapYear, t2.getReportitem());
					if (liYearStart.compareTo(BigDecimal.ZERO) != 0) {
						eB.setLiYearStart(moneyFormat.format(liYearStart)); // 年初金额
					} else {
						eB.setLiYearStart(null); // 年初金额
					}

					mapLiY.put(t2.getReportitem(), liYearStart); // 负债类年map添加键值
				}
				if (t2.getDatasource().equals("2")) {
					eB.setLiIsALabel(false); // 不是超链接

					BigDecimal liPeriodEnd = this.totalTotal(t2.getFormula(), mapLiP); // 期末金额
																						// 合计
					if (liPeriodEnd.compareTo(BigDecimal.ZERO) != 0) {
						eB.setLiPeriodEnd(moneyFormat.format(liPeriodEnd));
					} else {
						eB.setLiPeriodEnd(null);
					}

					mapLiP.put(t2.getReportitem(), liPeriodEnd); // 负债类期map添加键值
																	// 合计

					BigDecimal LiYearStart = this.totalTotal(t2.getFormula(), mapLiY); // 年初金额合计
					if (LiYearStart.compareTo(BigDecimal.ZERO) != 0) {
						eB.setLiYearStart(moneyFormat.format(LiYearStart)); // 年初金额合计
					} else {
						eB.setLiYearStart(null); // 年初金额合计
					}

					mapLiY.put(t2.getReportitem(), LiYearStart); // 负债类年map添加键值
																	// 合计
				}
				if (t2.getDatasource().equals("3")) {
					eB.setLiIsALabel(false); // 不是超链接

					BigDecimal liPeriodEnd = this.totalTotal(t2.getFormula(), mapLiP); // 期末金额
																						// 合计合计
					if (liPeriodEnd.compareTo(BigDecimal.ZERO) != 0) {
						eB.setLiPeriodEnd(moneyFormat.format(liPeriodEnd));
					} else {
						eB.setLiPeriodEnd(null);
					}

					mapLiP.put(t2.getReportitem(), liPeriodEnd); // 负债类期map添加键值
																	// 合计合计

					BigDecimal liYearStart = this.totalTotal(t2.getFormula(), mapLiY); // 年初金额
																						// 合计合计
					if (liYearStart.compareTo(BigDecimal.ZERO) != 0) {
						eB.setLiYearStart(moneyFormat.format(liYearStart));
					} else {
						eB.setLiYearStart(null);
					}

					mapLiY.put(t2.getReportitem(), liYearStart); // 负债类年map添加键值
																	// 合计合计
				}
				if (t2.getDatasource().equals("4")) {
					eB.setLiIsALabel(false); // 不是超链接
					BigDecimal liPeriodEnd = this.totalTotal(t2.getFormula(), mapLiP); // 期末金额
																						// 合计合计or合计
					if (liPeriodEnd.compareTo(BigDecimal.ZERO) != 0) {
						eB.setLiPeriodEnd(moneyFormat.format(liPeriodEnd));
					} else {
						eB.setLiPeriodEnd(null);
					}

					mapLiP.put(t2.getReportitem(), liPeriodEnd); // 负债类期map添加键值
																	// 合计合计

					BigDecimal liYearStart = this.totalTotal(t2.getFormula(), mapLiY); // 年初金额
																						// 合计合计or合计
					if (liYearStart.compareTo(BigDecimal.ZERO) != 0) {
						eB.setLiYearStart(moneyFormat.format(liYearStart));
					} else {
						eB.setLiYearStart(null);
					}
					mapLiY.put(t2.getReportitem(), liYearStart); // 负债类年map添加键值
																	// 合计合计
				}
			}

			listEBalance.add(eB);
		}

		session.setAttribute("downloadBalance", listEBalance);
		boolean balanceInfo = this.compareMoneyBalance(mapAsP, mapLiP, mapAsY, mapLiY, model); // 判断资产负债表是否平衡
		mapAsP.clear();
		mapAsY.clear();
		mapLiP.clear();
		mapLiY.clear();
		mapPeriod.clear();
		mapYear.clear();
		map.put("companyName", companyName);
		map.put("listEBalance", listEBalance);
		map.put("balanceInfo", balanceInfo);
		return map;

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

	public Map<String, List<Reportbalance>> getListReportbalanceYear(String accountPeriod, String periodEnd, String fdbid, List<TReportitem> listTReportitem,String reportId) {
		Map<String, List<Reportbalance>> mapYear = new HashMap<String, List<Reportbalance>>();
		accountPeriod = accountPeriod.substring(0, 4) + "01";
		periodEnd = accountPeriod;
		List<Reportbalance> listReportbalance = reportbalanceService.findListByReportId(accountPeriod, periodEnd, reportId, fdbid);

		List<Reportbalance> listRep = new ArrayList<Reportbalance>();
		for (TReportitem rep : listTReportitem) {
			for (Reportbalance re : listReportbalance) {
				if (re.getReportitem().equals(rep.getReportitem())) {
					listRep.add(re);
				}
			}
			mapYear.put(rep.getReportitem(), listRep);
			listRep = new ArrayList<Reportbalance>();
		}

		return mapYear;
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 */
	@RequestMapping(value = { "downloadBalance", "" })
	public void downloadBalance(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		String docsPath = request.getSession().getServletContext().getRealPath("docs");
		String fileName = "资产负债表_" + System.currentTimeMillis() + ".xlsx";

		List<EBalance> listEBalance = (ArrayList<EBalance>) session.getAttribute("downloadBalance");
		String filePath = docsPath + FILE_SEPARATOR + fileName;
		try {
			// 输出流
			OutputStream os = new FileOutputStream(filePath);
			// 工作区
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("资产负债表");
			sheet.setVerticallyCenter(true);
			/**
			 * 资产负债的宽度
			 */
			downloadController.balanceStyleWidth(sheet);
			/*
			 * 标题
			 */
			XSSFRow rowTitle = sheet.createRow(0);
			XSSFCell cellTitle = rowTitle.createCell(0);
			cellTitle.setCellStyle(downloadController.balanceHeaderStyle(wb, sheet));
			cellTitle.setCellValue("资产负债表");
			/*
			 * 副标题
			 */
			XSSFRow rowSubTitle = sheet.createRow(1);

			TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
			XSSFCell cellSubTitle0 = rowSubTitle.createCell(0);
			cellSubTitle0.setCellStyle(downloadController.balanceSubHeaderStyle1(wb, sheet));
			cellSubTitle0.setCellValue(companyInfo.getCustomerName());

			String accountPeriod = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
			accountPeriod = accountPeriod.substring(0, 4) + "年" + accountPeriod.subSequence(4, 6) + "期";
			XSSFCell cellSubTitle3 = rowSubTitle.createCell(3);
			cellSubTitle3.setCellStyle(downloadController.balanceSubHeaderStyle2(wb, sheet));
			cellSubTitle3.setCellValue(accountPeriod);

			String fur = "单位：元"; // 获取货币种类
			XSSFCell cellSubTitle5 = rowSubTitle.createCell(5);
			cellSubTitle5.setCellStyle(downloadController.balanceSubHeaderStyle3(wb, sheet));
			cellSubTitle5.setCellValue(fur);

			/*
			 * 列名
			 */
			XSSFRow rowColumn = sheet.createRow(2);

			XSSFCell cellColumn0 = rowColumn.createCell(0);
			cellColumn0.setCellStyle(downloadController.columnStyle(wb));
			cellColumn0.setCellValue("资产");
			XSSFCell cellColumn1 = rowColumn.createCell(1);
			cellColumn1.setCellStyle(downloadController.columnStyle(wb));
			cellColumn1.setCellValue("行次");
			XSSFCell cellColumn2 = rowColumn.createCell(2);
			cellColumn2.setCellStyle(downloadController.columnStyle(wb));
			cellColumn2.setCellValue("期末金额");
			XSSFCell cellColumn3 = rowColumn.createCell(3);
			cellColumn3.setCellStyle(downloadController.columnStyle(wb));
			cellColumn3.setCellValue("年初金额");
			XSSFCell cellColumn4 = rowColumn.createCell(4);
			cellColumn4.setCellStyle(downloadController.columnStyle(wb));
			cellColumn4.setCellValue("负债和所有着权益");
			XSSFCell cellColumn5 = rowColumn.createCell(5);
			cellColumn5.setCellStyle(downloadController.columnStyle(wb));
			cellColumn5.setCellValue("行次");
			XSSFCell cellColumn6 = rowColumn.createCell(6);
			cellColumn6.setCellStyle(downloadController.columnStyle(wb));
			cellColumn6.setCellValue("期末金额");
			XSSFCell cellColumn7 = rowColumn.createCell(7);
			cellColumn7.setCellStyle(downloadController.columnStyle(wb));
			cellColumn7.setCellValue("年初金额");
			int n = 3;
			String left = "left";
			String center = "center";
			String right = "right";
			for (EBalance eb : listEBalance) {
				XSSFRow rowsInfo = sheet.createRow(n);
				// 给这一行的第一列赋值
				String assets = eb.getAssets() == null ? "" : eb.getAssets().replace("&nbsp;", " ");
				XSSFCell cellInfo0 = rowsInfo.createCell(0);
				cellInfo0.setCellStyle(downloadController.infoStyle(wb, left));
				cellInfo0.setCellValue(assets);

				String asNumber = eb.getAsLineNumber();
				XSSFCell cellInfo1 = rowsInfo.createCell(1);
				cellInfo1.setCellStyle(downloadController.infoStyle(wb, center));
				cellInfo1.setCellValue(asNumber);

				String asPE = eb.getAsPeriodEnd();// == null
				
				XSSFCell cellInfo2 = rowsInfo.createCell(2);
				cellInfo2.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo2.setCellValue(asPE);

				String asYS = eb.getAsYearStart();// == null
				
				XSSFCell cellInfo3 = rowsInfo.createCell(3);
				cellInfo3.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo3.setCellValue(asYS);

				String li = eb.getLiabilities() == null ? "" : eb.getLiabilities().replace("&nbsp;", " ");
				XSSFCell cellInfo4 = rowsInfo.createCell(4);
				cellInfo4.setCellStyle(downloadController.infoStyle(wb, left));
				cellInfo4.setCellValue(li);

				String liNumber = eb.getLiLineNumber();
				XSSFCell cellInfo5 = rowsInfo.createCell(5);
				cellInfo5.setCellStyle(downloadController.infoStyle(wb, center));
				cellInfo5.setCellValue(liNumber);

				String liPE = eb.getLiPeriodEnd();// == null
				
				XSSFCell cellInfo6 = rowsInfo.createCell(6);
				cellInfo6.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo6.setCellValue(liPE);

				String liYS = eb.getLiYearStart();// == null
				
				XSSFCell cellInfo7 = rowsInfo.createCell(7);
				cellInfo7.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo7.setCellValue(liYS);
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
	@RequestMapping(value = { "stampBalance", "" })
	public void stampBalance(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws IOException {
		List<PdfPTable> listPdfPTable = new ArrayList<PdfPTable>();
		String title = "资产负债表"; // 标题
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String sub1 = companyInfo.getCustomerName(); // 上左
		String accountPeriod = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		accountPeriod = accountPeriod.substring(0, 4) + "年" + accountPeriod.subSequence(4, 6) + "期";
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
			/**
			 * 2 有多少列
			 */
			table = tBuilder.createPdf(fontsPath, 8, 90, new float[] { 0.23f, 0.05f, 0.16f, 0.16f, 0.23f, 0.05f, 0.16f, 0.16f });
			tableSub = tBuilder.createSub(fontsPath, sub1, sub2, sub3);
			tableRemarks = tBuilder.createRemarks(fontsPath, remarks, stampTime);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<EBalance> listEBalance = (ArrayList<EBalance>) session.getAttribute("downloadBalance");
		int n = 2;
		String left = "left";
		String center = "center";
		String right = "right";
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "资产", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "行次", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "期末数", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "年初数", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "负债和所有者(或股东)权益", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "行次", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "期末数", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "年初数", 0, 0));
		for (EBalance eb : listEBalance) {
			// 1st Row
			String assets = eb.getAssets() == null ? "" :eb.getAssets().replace("&nbsp;", "");
			table.addCell(tBuilder.createValueCell(n, left, fontsPath, assets, 0, 0));
			String asNumber = eb.getAsLineNumber();
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, asNumber, 0, 0));
			String asPE = eb.getAsPeriodEnd();// == null
			
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, asPE, 0, 0));
			String asYS = eb.getAsYearStart();// == null
			
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, asYS, 0, 0));
			String li = eb.getLiabilities() == null ? "" : eb.getLiabilities().replace("&nbsp;", "");
			table.addCell(tBuilder.createValueCell(n, left, fontsPath, li, 0, 0));
			String liNumber = eb.getLiLineNumber();
			table.addCell(tBuilder.createValueCell(n, center, fontsPath, liNumber, 0, 0));
			String liPE = eb.getLiPeriodEnd();// == null
		
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, liPE, 0, 0));
			String liYS = eb.getLiYearStart();// == null
			
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, liYS, 0, 0));
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
	 * 判断资产负债表是否平衡
	 * 
	 * @param mapAsP资产类期map
	 * @param mapLiP负债类期map
	 * @param mapAsY资产类年map
	 * @param mapLiY负债类年map
	 */
	private boolean compareMoneyBalance(Map<String, BigDecimal> mapAsP, Map<String, BigDecimal> mapLiP, Map<String, BigDecimal> mapAsY, Map<String, BigDecimal> mapLiY, Model model) {

		BigDecimal asP = getMapInfo(mapAsP, "01*003"); // 资产 期
		BigDecimal liP = getMapInfo(mapLiP, "03*002"); // 负债 期
		BigDecimal asY = getMapInfo(mapAsY, "01*003"); // 资产 年
		BigDecimal liY = getMapInfo(mapLiY, "03*002"); // 负债 年
		// true 不平衡 false 平衡
		if (asP.compareTo(liP) == 0 && asY.compareTo(liY) == 0) { // 资产不平衡
//			model.addAttribute("balanceInfo", "false");
			return false;
		} else {
			return true;
//			model.addAttribute("balanceInfo", "true");
		}
	}

	/**
	 * 处理map集合
	 * 
	 * @param maps
	 * @param key
	 * @return
	 */
	private BigDecimal getMapInfo(Map<String, BigDecimal> maps, String key) {
		if (maps.size() != 0) {
			return maps.get(key);
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 公式计算金额
	 * 
	 * @param listSymbol
	 * @param liBalance
	 */
	private BigDecimal calculation(String formula) {
		BigDecimal money = BigDecimal.ZERO;
		try {
			// 计算金额
			money = new BigDecimal(jse.eval(formula).toString());
		} catch (Exception t) {
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
	public BigDecimal totalTotal(String formula, Map<String, BigDecimal> map) {
		Pattern p = Pattern.compile("\\[(.*?)\\](.*?)?(?=$|\\[)");
		Matcher m = p.matcher(formula);
		List<String> listVariable = Lists.newArrayList();
		while (m.find()) {
			listVariable.add(m.group(1));
		}
		// 把数字和符号分别取出来
		for (String str : listVariable) { 
			Object mapStr = map.get(str) == null ? BigDecimal.ZERO : map.get(str);
	        	formula=formula.replace("["+str+"]", mapStr.toString());
	    }
		formula = formula.replaceAll("\\[", "").replaceAll("\\]", "");
		// 调用处理公式
		BigDecimal money = calculation(formula);
		money = money.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return money;
	}

	/**
	 * 期末余额
	 * 
	 * @param reportItem
	 * @param accountPeriod
	 * @return
	 */
	public BigDecimal endPeriod(Map<String, List<Reportbalance>> mapPeriod, String reportItem) {
		BigDecimal money = BigDecimal.ZERO;
		List<Reportbalance> listReportbalance = mapPeriod.get(reportItem);
		String subReportItem = reportItem.substring(0, 2); // 截取报表编号判断是资产类还是负债类和所有者权益
		String endBalance = "endBalance";
		if (subReportItem.equals("01")) { // 资产类
			money = getAsMoney(listReportbalance, money, endBalance);
		}
		if (subReportItem.equals("02") // 负债类
				|| subReportItem.equals("03")) { // 所有者权益
			money = getLiMoney(listReportbalance, money, endBalance);
		}
		money = money.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return money;

	}

	/**
	 * 年初余额
	 * 
	 * @param reportItem
	 * @param accountPeriod
	 * @return
	 */
	public BigDecimal startYear(String accountPeriod, String periodEnd, String reportItem, String fdbid, String accId) {
		accountPeriod = accountPeriod.substring(0, 4) + "01";
		periodEnd = accountPeriod;
		List<Reportbalance> listReportbalance = reportbalanceService.findListByReItem(accountPeriod, periodEnd, reportItem, fdbid, accId);
		BigDecimal money = BigDecimal.ZERO;
		String subReportItem = reportItem.substring(0, 2); // 截取报表编号判断是资产类还是负债类和所有者权益
		String startBalance = "startBalance";
		if (subReportItem.equals("01")) { // 资产类
			money = getAsMoney(listReportbalance, money, startBalance);
		}
		if (subReportItem.equals("02") // 负债类
				|| subReportItem.equals("03")) { // 所有者权益
			money = getLiMoney(listReportbalance, money, startBalance);
		}
		money = money.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return money;
	}

	/**
	 * 年初余额
	 * 
	 * @param reportItem
	 * @param accountPeriod
	 * @return
	 */
	public BigDecimal startYear(Map<String, List<Reportbalance>> mapYear, String reportItem) {
		String startBalance = "startBalance";
		List<Reportbalance> listReportbalance = mapYear.get(reportItem);
		BigDecimal money = BigDecimal.ZERO;
		String subReportItem = reportItem.substring(0, 2); // 截取报表编号判断是资产类还是负债类和所有者权益
		if (subReportItem.equals("01")) { // 资产类
			money = getAsMoney(listReportbalance, money, startBalance);
		}
		if (subReportItem.equals("02") // 负债类
				|| subReportItem.equals("03")) { // 所有者权益
			money = getLiMoney(listReportbalance, money, startBalance);
		}
		money = money.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return money;
	}

	/**
	 * 根据取数规则进行判断取数 资产类和所有者权益
	 */
	private BigDecimal getAsMoney(List<Reportbalance> listReportbalance, BigDecimal money, String balanceType) {
		/**
		 * 借方金额 （余额保存在汇总表（初始余额表）中为数值） 贷方金额 （余额保存在汇总表（初始余额表）中为相反数）
		 * 
		 * 资产类 借方科目 余额就取值 借方余额取值 贷方余额为0 资产类 贷方科目 余额取值即可 借方余额为0 贷方余额为相反数
		 * 
		 */
		for (Reportbalance re : listReportbalance) {
			String getbalance = "";
			if (balanceType.equals("startBalance")) { // 期初余额
				getbalance = re.getBeginbalance() == null || re.getBeginbalance().equals("") ? "0" : re.getBeginbalance(); // 余额
			}
			if (balanceType.equals("endBalance")) { // 期末余额
				getbalance = re.getEndbalance() == null || re.getEndbalance().equals("") ? "0" : re.getEndbalance(); // 余额
			}
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
					getbalanceDouble = getbalanceDouble.negate(); // 资产类 贷方科 目
																	// 贷方余额为相反数
				}
				if (re.getFtype().equals("Y")) {
					getbalanceDouble = getbalanceDouble.negate(); // 资产类 贷方科 目
				}
			}

			if (re.getOp().equals("+")) {
				money = money.add(getbalanceDouble);
			} else {
				money = money.subtract(getbalanceDouble);
			}

		}
		return money;
	}

	/**
	 * 根据取数规则进行判断取数 负债类和所有者权益
	 */
	private BigDecimal getLiMoney(List<Reportbalance> listReportbalance, BigDecimal money, String balanceType) {
		/**
		 * 借方金额 （余额保存在汇总表（初始余额表）中为数值） 贷方金额 （余额保存在汇总表（初始余额表）中为相反数）
		 * 
		 * 负债类 借方科目 余额为相反数 借方余额取值 贷方余额为0 负债类 贷方科目 余额为相反数 借方余额为0 贷方余额为相反数
		 */
		for (Reportbalance re : listReportbalance) {
			String getbalance = "";
			if (balanceType.equals("startBalance")) { // 期初余额
				getbalance = re.getBeginbalance() == null || re.getBeginbalance().equals("") ? "0" : re.getBeginbalance(); // 余额
			}
			if (balanceType.equals("endBalance")) { // 期末余额
				getbalance = re.getEndbalance() == null || re.getEndbalance().equals("") ? "0" : re.getEndbalance(); // 余额
			}
			BigDecimal getbalanceDouble = new BigDecimal(getbalance);
			if (re.getDc().equals("-1")) { // 贷方科目
				if (re.getFtype().equals("D")) { // 借方余额
					getbalanceDouble = BigDecimal.ZERO; // 负债类 贷方科目 借方余额为0
				}
				if (re.getFtype().equals("C")) { // 贷方余额
					getbalanceDouble = getbalanceDouble.negate(); // 负债类 贷方科目
																	// 贷方余额为相反数
				}
				if (re.getFtype().equals("Y")) { // 余额
					getbalanceDouble = getbalanceDouble.negate(); // 负债类 贷方科目
																	// 余额为相反数
				}
			}
			if (re.getDc().equals("1")) { // 借方科目
				if (re.getFtype().equals("C")) { // 贷方余额
					getbalanceDouble = BigDecimal.ZERO; // 负债类 借方科目 贷方余额为0
				}
				if (re.getFtype().equals("Y")) { // 余额
					getbalanceDouble = getbalanceDouble.negate(); // 负债类 借方科目
																	// 余额为相反数
				}
			}
			if (re.getOp().equals("+")) {
				money = money.add(getbalanceDouble);
			} else {
				money = money.subtract(getbalanceDouble);
			}
		}
		return money;
	}

	/**
	 * 获取所有的账期
	 * 
	 * @param model
	 * @param companyId
	 */
	public void getAllAccountPeriod(Model model, String companyId, String defaultPeriod) {
		List<TVoucher> listTVoucher = tVoucherService.finperiods(companyId);
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
		String companyName = companyInfo.getCustomerName();
		model.addAttribute("companyName", companyName);
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