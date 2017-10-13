/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.books.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.books.entity.Accountbalance;
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
import com.thinkgem.jeesite.modules.voucherexp.service.TVoucherExpService;
import com.thoughtworks.xstream.mapper.Mapper.Null;

/**
 * 明细账Controller
 * 
 * @author zhangtong
 * @version 2015-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/books/Subsidiary")
public class SubsidiaryController extends BaseController {

	@Autowired
	private SubsidiaryledgeService subsidiaryledgeService;
	@Autowired
	private TBalanceService tBalanceService;
	@Autowired
	private TVoucherService tVoucherService;
	@Autowired
	private TVoucherExpService tVoucherExpService;
	@Autowired
	private AccountbalanceService accountbalanceService;
	@Autowired
	private TableBuilder tBuilder;
	@Autowired
	private StampPdf stampPdf;
	@Autowired
	private TAccountService tAccountService;
	@Autowired
	private DownloadController downloadController;
	public static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
	public static NumberFormat moneyFormat = new DecimalFormat("#,##0.00");
	private BigDecimal beginMoney = BigDecimal.ZERO;

	public BigDecimal getBeginMoney() {
		return beginMoney;
	}

	public void setBeginMoney(BigDecimal beginMoney) {
		this.beginMoney = beginMoney;
	}

	private List<Accountbalance> listAccs;

	public List<Accountbalance> getListAccs() {
		return listAccs;
	}

	public void setListAccs(List<Accountbalance> listAccs) {
		this.listAccs = listAccs;
	}

	private Subsidiaryledge subsidiary;

	public Subsidiaryledge getSubsidiary() {
		return subsidiary;
	}

	public void setSubsidiary(Subsidiaryledge subsidiary) {
		this.subsidiary = subsidiary;
	}

	@RequestMapping(value = { "listSubsidiaryledge", "" })
	public String listSubsidiaryledge(String accId, String accountPeriod, String periodEnd, String accName, Model model) {
		if (accId != null && accountPeriod != null && periodEnd != null) {
			model.addAttribute("accountPeriod", accountPeriod);
			model.addAttribute("periodEnd", periodEnd);
			TAccount account = tAccountService.get(accId);
			model.addAttribute("accountInfo", account);
		}
		return "modules/books/vSubsidiaryList";
	}

	private List mySortByVoucherTitleNumber(List arr) { // 交换排序->冒泡排序
		Subsidiaryledge temp = null;
		boolean exchange = false;
		for (int i = 0; i < arr.size(); i++) {
			exchange = false;
			for (int j = arr.size() - 2; j >= i; j--) {
				if ((((Subsidiaryledge) arr.get(j + 1)).getVoucherTitleNumber().compareTo(((Subsidiaryledge) arr.get(j)).getVoucherTitleNumber()) <= 0)&&(((Subsidiaryledge) arr.get(j + 1)).getAccountPeriod().compareTo(((Subsidiaryledge) arr.get(j)).getAccountPeriod()) <= 0)) {
					temp = (Subsidiaryledge) arr.get(j + 1);
					arr.set(j + 1, (Subsidiaryledge) arr.get(j));
					arr.set(j, temp);
					exchange = true;
				}
			}
			if (!exchange)
				break;
		}
		return arr;
	}

	private List mySortByAccountPeriod(List arr) { // 交换排序->冒泡排序
		TBalance temp = null;
		boolean exchange = false;
		for (int i = 0; i < arr.size(); i++) {
			exchange = false;
			for (int j = arr.size() - 2; j >= i; j--) {
				if (((TBalance) arr.get(j + 1)).getAccountPeriod().compareTo(((TBalance) arr.get(j)).getAccountPeriod()) <= 0) {
					temp = (TBalance) arr.get(j + 1);
					arr.set(j + 1, (TBalance) arr.get(j));
					arr.set(j, temp);
					exchange = true;
				}
			}
			if (!exchange)
				break;
		}

		return arr;
	}

	@RequestMapping(value = { "list", "" })
	@ResponseBody
	public Object list(String accId, String accountPeriod, String periodEnd, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		// 当前公司客户
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		Subsidiaryledge subsidiaryledge = new Subsidiaryledge();
		subsidiaryledge.setAccId(accId);
		subsidiaryledge.setAccountPeriod(accountPeriod);
		subsidiaryledge.setPeriodEnd(periodEnd);
		TAccount tAccountInfo = null;
		List<Subsidiaryledge> listSubsidiaryledge = Lists.newArrayList();
		List<Subsidiary> listSubsidiary = Lists.newArrayList(); // 用来存储信息显示与界面
		if (accId != null && !accId.equals("")) {
			tAccountInfo = tAccountService.getAccountsInfo(accId, companyId); // 根据科目id
			List<TBalance> listTBalance = new ArrayList<TBalance>();
			listSubsidiaryledge = getSubAccount(companyId, accountPeriod, periodEnd, tAccountInfo, listSubsidiaryledge); // 读取相关科目相关账期的凭证摘要信息
			if (tAccountInfo.getDetail().equals("1")) { // 无子级
				// 根据科目id 客户id 和账期前后查询
				listSubsidiaryledge = subsidiaryledgeService.findSubsidiaryledgeList(accId, companyId, accountPeriod, periodEnd, null, null);
				// listTBalance = tBalanceService.findListTBalanceByInfo(accId,
				// companyId, accountPeriod, periodEnd, null);
			}

			listTBalance = tBalanceService.findListTBalanceByInfo(accId, companyId, accountPeriod, periodEnd, null); // 读取余额表相关科目公司的金额信息
			listSubsidiaryledge = this.mySortByVoucherTitleNumber(listSubsidiaryledge);
			// listSubsidiaryledge =
			// this.mySortByAccountPeriod(listSubsidiaryledge, new
			// Subsidiaryledge());
			listTBalance = this.mySortByAccountPeriod(listTBalance);
			this.setSubsidiary(subsidiaryledge);
			// 遍历集合添加到集合显示页面
			addTableSubSidiary(subsidiaryledge, accountPeriod, periodEnd, companyId, listSubsidiaryledge, listSubsidiary, listTBalance);
		}
		// 循环遍历填充到新的list中

		session.setAttribute("downloadSubsidiary", listSubsidiary);

		return listSubsidiary;
	}

	private void addTableSubSidiary(Subsidiaryledge subsidiaryledge, String accountPeriod, String periodEnd, String companyId, List<Subsidiaryledge> listSubsidiaryledge, List<Subsidiary> listSubsidiary, List<TBalance> listTBalance) {

		if (listSubsidiaryledge.size() > 0) {
			for (TBalance tb : listTBalance) {
				BigDecimal begMoney = BigDecimal.ZERO;
				int n = 0;

				for (Subsidiaryledge suy : listSubsidiaryledge) {
					subsidiaryledge = null;
					subsidiaryledge = suy;
					n++;
					if (tb.getAccountPeriod().equals(suy.getAccountPeriod()) && (tb.getAccountId().equals(suy.getAccId()) || tb.getAccountId().equals(suy.getParent().getId()))) { // 判断账期是否一样

						if (n == 1) {

							listSubsidiary.add(this.totalStart(tb, suy)); // 期初余额
							// 把开始余额传过去是用来保存期初余额的
							begMoney = this.getBeginMoney();
						}
						Subsidiary su = new Subsidiary();
						su.setAccId(suy.getAccId());
						su.setExp(suy.getExp()); // 摘要
						su.setAccountDate(suy.getAccountDate()); // 记账时间
						su.setVoucherTitleName(suy.getVoucherTitleName()); // 摘要名称
						su.setVoucherTitleNumber(suy.getVoucherTitleNumber()); // 摘要号
						su.setId(suy.getId()); // 凭证表id
						String debitString = suy.getDebit() == null || suy.getDebit().equals("") ? "0" : suy.getDebit();
						BigDecimal debitDouble = new BigDecimal(debitString);
						if (debitDouble.compareTo(BigDecimal.ZERO) != 0) {

							debitString = moneyFormat.format(debitDouble);
						} else {
							debitString = null;
						}
						su.setDebit(debitString); // 借方金额
						String creditString = suy.getCredit() == null || suy.getCredit().equals("") ? "0" : suy.getCredit();
						BigDecimal creditDouble = new BigDecimal(creditString);
						if (creditDouble.compareTo(BigDecimal.ZERO) != 0) {
							creditString = moneyFormat.format(creditDouble);
						} else {
							creditString = null;
						}
						su.setCredit(creditString); // 贷方金额
						if (suy.getDc().equals("1")) {
							su.setDc("借"); // 借贷方向
						} else {
							su.setDc("贷"); // 借贷方向
						}

						subsidiaryledge.setDc(suy.getDc()); // 防止报空指针异常
						String stringDebit = suy.getDebit().equals("") ? "0" : suy.getDebit();
						BigDecimal dDebit = new BigDecimal(stringDebit);
						if (dDebit.compareTo(BigDecimal.ZERO) != 0) { // 判断该科目该期存在数值
																		// 借方金额
							if (suy.getDc().equals("1")) { // 判断该科目的借贷方向
															// 借方金额相加
															// 贷方金额相减
								String getDebit = suy.getDebit() == null || suy.getDebit().equals("") ? "0" : suy.getDebit();
								begMoney = begMoney.add(new BigDecimal(getDebit));
							} else {
								String getDebit = suy.getDebit() == null || suy.getDebit().equals("") ? "0" : suy.getDebit();
								begMoney = begMoney.subtract(new BigDecimal(getDebit));
							}
						}
						String stringCredit = suy.getCredit().equals("") ? "0" : suy.getCredit();
						BigDecimal dCredit = new BigDecimal(stringCredit);
						if (dCredit.compareTo(BigDecimal.ZERO) != 0) { // 判断该科目该期存在数值
																		// 贷方金额
							if (suy.getDc().equals("1")) { // 判断该科目的借贷方向
															// 借方金额相减
															// 贷方金额相加
								String getCredit = suy.getCredit() == null || suy.getCredit().equals("") ? "0" : suy.getCredit();
								begMoney = begMoney.subtract(new BigDecimal(getCredit));
							} else {
								String getCredit = suy.getCredit() == null || suy.getCredit().equals("") ? "0" : suy.getCredit();
								begMoney = begMoney.add(new BigDecimal(getCredit));
							}
						}
						if (begMoney.compareTo(BigDecimal.ZERO) != 0) {
							su.setBalance(moneyFormat.format(begMoney)); // 设置余额
						} else {
							su.setBalance(null); // 设置余额
						}

						listSubsidiary.add(su); // 存储每次的凭证记录
					}
				}
				if (n >= listSubsidiaryledge.size()) {
					listSubsidiary.add(this.totalPeriod(tb, subsidiaryledge)); // 存储本期合计
					listSubsidiary.add(this.totalYear(tb, subsidiaryledge)); // 本年累计
				}
			}
		} else {
			List<Accountbalance> listHappenAcc = accountbalanceService.findHappenByAcc(companyId, accountPeriod, periodEnd, subsidiaryledge.getAccId(), null); // 显示所有发生金额变化的科目
			for (Accountbalance accb : listHappenAcc) {
				listSubsidiary.add(this.totalStart(null, accb)); // 期初余额
				listSubsidiary.add(this.totalPeriod(null, accb)); // 存储本期合计
				listSubsidiary.add(this.totalYear(null, accb)); // 本年累计
			}
		}
	}

	/**
	 * 遍历集合添加到集合显示页面
	 * 
	 * @param subsidiaryledge
	 * @param tBalance
	 * @param companyId
	 * @param listSubsidiaryledge
	 * @param listSubsidiary
	 * @param listTBalance
	 * @param begMoney
	 */
	private void addTableSubSidiaryStamp(Subsidiaryledge subsidiaryledge, String accountPeriod, String periodEnd, String companyId, List<Subsidiaryledge> listSubsidiaryledge, List<Subsidiary> listSubsidiary, List<TBalance> listTBalance) {

		if (listSubsidiaryledge.size() > 0) {
			for (TBalance tb : listTBalance) {
				BigDecimal begMoney = BigDecimal.ZERO;
				int n = 0;
				int m = 0;
				for (Subsidiaryledge suy : listSubsidiaryledge) {
					subsidiaryledge = suy;
					n++;
					if (tb.getAccountPeriod().equals(suy.getAccountPeriod()) && (tb.getAccountId().equals(suy.getAccId()))) { // 判断账期是否一样
						// || tb
						// .getAccountId().equals(
						// suy.getParent().getId())
						m++;
						if (m == 1) {

							listSubsidiary.add(this.totalStart(tb, suy)); // 期初余额
							// 把开始余额传过去是用来保存期初余额的
							begMoney = this.getBeginMoney();
						}
						Subsidiary su = new Subsidiary();
						su.setAccId(suy.getAccId());
						su.setExp(suy.getExp()); // 摘要
						su.setAccountDate(suy.getAccountDate()); // 记账时间
						su.setVoucherTitleName(suy.getVoucherTitleName()); // 摘要名称
						su.setVoucherTitleNumber(suy.getVoucherTitleNumber()); // 摘要号
						su.setId(suy.getId()); // 凭证表id
						String debitString = suy.getDebit() == null || suy.getDebit().equals("") ? "0" : suy.getDebit();
						BigDecimal debitDouble = new BigDecimal(debitString);
						if (debitDouble.compareTo(BigDecimal.ZERO) != 0) {

							debitString = moneyFormat.format(debitDouble);
						} else {
							debitString = null;
						}
						su.setDebit(debitString); // 借方金额
						String creditString = suy.getCredit() == null || suy.getCredit().equals("") ? "0" : suy.getCredit();
						BigDecimal creditDouble = new BigDecimal(creditString);
						if (creditDouble.compareTo(BigDecimal.ZERO) != 0) {
							creditString = moneyFormat.format(creditDouble);
						} else {
							creditString = null;
						}
						su.setCredit(creditString); // 贷方金额
						if (suy.getDc().equals("1")) {
							su.setDc("借"); // 借贷方向
						} else {
							su.setDc("贷"); // 借贷方向
						}

						subsidiaryledge.setDc(suy.getDc()); // 防止报空指针异常
						String stringDebit = suy.getDebit().equals("") ? "0" : suy.getDebit();
						BigDecimal dDebit = new BigDecimal(stringDebit);
						if (dDebit.compareTo(BigDecimal.ZERO) != 0) { // 判断该科目该期存在数值
																		// 借方金额
							if (suy.getDc().equals("1")) { // 判断该科目的借贷方向
															// 借方金额相加
															// 贷方金额相减
								String getDebit = suy.getDebit() == null || suy.getDebit().equals("") ? "0" : suy.getDebit();
								begMoney = begMoney.add(new BigDecimal(getDebit));
							} else {
								String getDebit = suy.getDebit() == null || suy.getDebit().equals("") ? "0" : suy.getDebit();
								begMoney = begMoney.subtract(new BigDecimal(getDebit));
							}
						}
						String stringCredit = suy.getCredit().equals("") ? "0" : suy.getCredit();
						BigDecimal dCredit = new BigDecimal(stringCredit);
						if (dCredit.compareTo(BigDecimal.ZERO) != 0) { // 判断该科目该期存在数值
																		// 贷方金额
							if (suy.getDc().equals("1")) { // 判断该科目的借贷方向
															// 借方金额相减
															// 贷方金额相加
								String getCredit = suy.getCredit() == null || suy.getCredit().equals("") ? "0" : suy.getCredit();
								begMoney = begMoney.subtract(new BigDecimal(getCredit));
							} else {
								String getCredit = suy.getCredit() == null || suy.getCredit().equals("") ? "0" : suy.getCredit();
								begMoney = begMoney.add(new BigDecimal(getCredit));
							}
						}
						if (begMoney.compareTo(BigDecimal.ZERO) != 0) {
							su.setBalance(moneyFormat.format(begMoney)); // 设置余额
						} else {
							su.setBalance(null); // 设置余额
						}

						listSubsidiary.add(su); // 存储每次的凭证记录
					}
				}
				if (n >= listSubsidiaryledge.size()) {
					listSubsidiary.add(this.totalPeriod(tb, subsidiaryledge)); // 存储本期合计
					listSubsidiary.add(this.totalYear(tb, subsidiaryledge)); // 本年累计
				}
			}
		} else {
			List<Accountbalance> listHappenAcc = accountbalanceService.findHappenByAcc(companyId, accountPeriod, periodEnd, subsidiaryledge.getAccId(), null); // 显示所有发生金额变化的科目
			for (Accountbalance accb : listHappenAcc) {
				listSubsidiary.add(this.totalStart(null, accb)); // 期初余额
				listSubsidiary.add(this.totalPeriod(null, accb)); // 存储本期合计
				listSubsidiary.add(this.totalYear(null, accb)); // 本年累计
			}
		}
	}

	/**
	 * 获取子级科目信息
	 * 
	 * @param companyId
	 * @param accountPeriod
	 * @param periodEnd
	 * @param tAccountInfo
	 * @param listSubsidiaryledge
	 * @return
	 */
	private List<Subsidiaryledge> getSubAccount(String companyId, String accountPeriod, String periodEnd, TAccount tAccountInfo, List<Subsidiaryledge> listSubsidiaryledge) {
		if (tAccountInfo.getDetail().equals("0")) { // 有子级
			List<TAccount> listAccounts = tAccountService.getSubAccountsInfo(tAccountInfo.getId(), companyId); // 根据父级的id查询子级

			List<String> accIdS = new ArrayList<String>();
			for (TAccount tacc : listAccounts) { // 遍历子级的id
				if (tacc.getDetail().equals("0")) { // 有子级
					List<TAccount> listAccount3 = tAccountService.getSubAccountsInfo(tacc.getId(), companyId); // 根据父级的id
					for (TAccount tacc3 : listAccount3) {
						if (tacc3.getDetail().equals("0")) {
							List<TAccount> listAccount4 = tAccountService.getSubAccountsInfo(tacc3.getId(), companyId); // 根据父级的id
							for (TAccount tacc4 : listAccount4) {
								if (tacc4.getDetail().equals("0")) {

								} else {
									accIdS.add(tacc4.getId());
								}
							}

						} else {
							accIdS.add(tacc3.getId());
						}
					}
				} else {
					accIdS.add(tacc.getId());
				}

			}

			listSubsidiaryledge = subsidiaryledgeService.findSubsidiaryledgeList(null, companyId, accountPeriod, periodEnd, null, accIdS);
		}
		return listSubsidiaryledge;
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 */
	@RequestMapping(value = { "downloadSubsidiary", "" })
	public void downloadSubsidiary(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		String docsPath = request.getSession().getServletContext().getRealPath("docs");
		String fileName = "明细账_" + System.currentTimeMillis() + ".xlsx";

		List<Subsidiary> listSubsidiary = (ArrayList<Subsidiary>) session.getAttribute("downloadSubsidiary");
		String filePath = docsPath + FILE_SEPARATOR + fileName;
		try {

			String periodStart = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
			String periodEnd = new String(request.getParameter("periodEnd").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
			String accountName = new String(request.getParameter("accountName").getBytes("ISO-8859-1"), "utf-8"); // 科目名称
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

			// 输出流
			OutputStream os = new FileOutputStream(filePath);
			// 工作区
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(accountPeriod + "明细账");
			sheet.setVerticallyCenter(true);
			/**
			 * 资产负债的宽度
			 */
			downloadController.subsidiaryStyleWidth(sheet);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 打印时间
			/*
			 * 列名
			 */
			XSSFRow rowColumn = sheet.createRow(0);

			XSSFCell cellColumn0 = rowColumn.createCell(0);
			cellColumn0.setCellStyle(downloadController.columnStyle(wb));
			cellColumn0.setCellValue("日期");
			XSSFCell cellColumn1 = rowColumn.createCell(1);
			cellColumn1.setCellStyle(downloadController.columnStyle(wb));
			cellColumn1.setCellValue("凭证字号");
			XSSFCell cellColumn2 = rowColumn.createCell(2);
			cellColumn2.setCellStyle(downloadController.columnStyle(wb));
			cellColumn2.setCellValue("摘要");
			XSSFCell cellColumn3 = rowColumn.createCell(3);
			cellColumn3.setCellStyle(downloadController.columnStyle(wb));
			cellColumn3.setCellValue("借方");
			XSSFCell cellColumn4 = rowColumn.createCell(4);
			cellColumn4.setCellStyle(downloadController.columnStyle(wb));
			cellColumn4.setCellValue("贷方");
			XSSFCell cellColumn5 = rowColumn.createCell(5);
			cellColumn5.setCellStyle(downloadController.columnStyle(wb));
			cellColumn5.setCellValue("方向");
			XSSFCell cellColumn6 = rowColumn.createCell(6);
			cellColumn6.setCellStyle(downloadController.columnStyle(wb));
			cellColumn6.setCellValue("余额");
			int n = 1;
			String left = "left";
			String center = "center";
			String right = "right";
			for (Subsidiary su : listSubsidiary) {
				XSSFRow rowsInfo = sheet.createRow(n); // 给这一行的第一列赋值 String
				Date accountDate = su.getAccountDate();
				String accountDateString = null;
				if (accountDate != null) {
					accountDateString = sdf.format(accountDate).toString();
				}
				XSSFCell cellInfo1 = rowsInfo.createCell(0);
				cellInfo1.setCellStyle(downloadController.infoStyle(wb, left));
				cellInfo1.setCellValue(accountDateString);

				String getVoucherTitleName = su.getVoucherTitleName() == null ? "" : su.getVoucherTitleName();

				String getVoucherTitleNumber = su.getVoucherTitleNumber() == null ? "" : su.getVoucherTitleNumber();
				String voucherTitle = getVoucherTitleName + getVoucherTitleNumber;

				XSSFCell cellInfo2 = rowsInfo.createCell(1);
				cellInfo2.setCellStyle(downloadController.infoStyle(wb, center));
				cellInfo2.setCellValue(voucherTitle);

				String exp = su.getExp();
				XSSFCell cellInfo3 = rowsInfo.createCell(2);
				cellInfo3.setCellStyle(downloadController.infoStyle(wb, left));
				cellInfo3.setCellValue(exp);

				String debit = su.getDebit();// == null

				XSSFCell cellInfo4 = rowsInfo.createCell(3);
				cellInfo4.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo4.setCellValue(debit);

				String credit = su.getCredit();// == null

				XSSFCell cellInfo5 = rowsInfo.createCell(4);
				cellInfo5.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo5.setCellValue(credit);

				String dc = "";
				if (su.getDc().equals("1")) {
					dc = "借";
				} else {
					dc = "贷"; // 借贷方向
				}
				XSSFCell cellInfo6 = rowsInfo.createCell(5);
				cellInfo6.setCellStyle(downloadController.infoStyle(wb, center));
				cellInfo6.setCellValue(dc);

				String balance = su.getBalance();// == null

				XSSFCell cellInfo7 = rowsInfo.createCell(6);
				cellInfo7.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo7.setCellValue(balance);
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
	@RequestMapping(value = { "stampSubsidiary", "" })
	public void stampSubsidiary(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws IOException {

		String periodStart = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		String periodEnd = new String(request.getParameter("periodEnd").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		String accountName = new String(request.getParameter("accountName").getBytes("ISO-8859-1"), "utf-8"); // 科目名称
		String title = "明细账"; // 标题
		List<PdfPTable> listPdfPTable = new ArrayList<PdfPTable>();
		List<Subsidiary> listSubsidiary = (ArrayList<Subsidiary>) session.getAttribute("downloadSubsidiary");
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String fontsPath = request.getSession().getServletContext().getRealPath("Fonts");
		PdfPTable tableInfo = stampPdfTable(fontsPath, periodStart, periodEnd, accountName, listSubsidiary, companyInfo);
		listPdfPTable.add(tableInfo);
		stampPdf.createPdf(request, response, listPdfPTable, title);
	}

	/**
	 * 打印年度账
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = { "stampYearSubsidiary", "" })
	public void stampYearSubsidiary(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws IOException {
		String periodStart = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		String periodEnd = new String(request.getParameter("periodEnd").getBytes("ISO-8859-1"), "utf-8"); // 获取账期
		String title = "明细账"; // 标题
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String fontsPath = request.getSession().getServletContext().getRealPath("Fonts");
		List<PdfPTable> listPdfPTable = new ArrayList<PdfPTable>();
		List<Accountbalance> listAcc = this.getListAccs(); // 获取所有的科目对象
		List<String> accIdS = new ArrayList<String>(); // 保存所有的科目id

		// for (Accountbalance acc : listAcc) {
		// accIdS.add(acc.getId());
		// }
		// List<Subsidiaryledge> listSubsidiaryledge =
		// subsidiaryledgeService.findSubsidiaryledgeList(
		// null, companyId, periodStart, periodEnd, null, accIdS);
		// List<TBalance> listTBalance =
		// tBalanceService.findListTBalanceByInfo(null, companyId,
		// periodStart, periodEnd, accIdS);

		// List<Subsidiary> listSubsidiary = new ArrayList<Subsidiary>(); //
		// 存储的所有的信息

		// addTableSubSidiaryStamp(this.getSubsidiary(), periodStart, periodEnd,
		// companyId, listSubsidiaryledge, listSubsidiary, listTBalance);
		// for (Accountbalance acc : listAcc) {
		// List<Subsidiary> listSubsidiarytable = new ArrayList<Subsidiary>();
		// // 存储的所有的信息
		// String accountName =acc.getAccuntId() +"-"+ acc.getAccountName();
		// for (Subsidiary su : listSubsidiary) {
		// if (acc.getId().equals(su.getAccId())) {
		// listSubsidiarytable.add(su);
		// }
		// }
		//
		// PdfPTable tableInfo = stampPdfTable(fontsPath, periodStart,
		// periodEnd, accountName, listSubsidiarytable, companyInfo);
		// listPdfPTable.add(tableInfo);
		// }

		// -- 第二种方法

		for (Accountbalance acc : listAcc) { // 遍历发生金额变化的所有的科目
			String accId = acc.getId();
			List<TAccount> pareAccounts = tAccountService.getParentAccounts(acc.getParent().getId());
			/*
			 * Collections.sort(pareAccounts,new Comparator<TAccount>(){
			 * 
			 * @Override public int compare(TAccount b1, TAccount b2) { return
			 * new Integer( b1.getAccuntId()) - new Integer( b2.getAccuntId());
			 * }
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
					parentNameString.append(pareAccounts.get(i - 1).getAccountName() + "-");
				}
			}
			acc.setParentName(parentNameString.toString());

			TAccount tAccountInfo = null;

			List<Subsidiary> listSubsidiary = new ArrayList<Subsidiary>(); // 用来保存信息显示界面

			tAccountInfo = tAccountService.getAccountsInfo(accId, companyId); // 根据科目id

			List<Subsidiaryledge> listSubsidiaryledge = new ArrayList<Subsidiaryledge>();
			List<TBalance> listTBalance = new ArrayList<TBalance>();
			listSubsidiaryledge = getSubAccount(companyId, periodStart, periodEnd, tAccountInfo, listSubsidiaryledge);
			if (tAccountInfo.getDetail().equals("1")) { // 无子级

				listSubsidiaryledge = subsidiaryledgeService.findSubsidiaryledgeList(accId, companyId, periodStart, periodEnd, null, null);
			}
			listTBalance = tBalanceService.findListTBalanceByInfo(accId, companyId, periodStart, periodEnd, null);
			this.getSubsidiary().setAccId(accId);
			addTableSubSidiary(this.getSubsidiary(), periodStart, periodEnd, companyId, listSubsidiaryledge, listSubsidiary, listTBalance);
			String accountName = acc.getAccuntId() + "-" + acc.getParentName() + acc.getAccountName();
			PdfPTable tableInfo = stampPdfTable(fontsPath, periodStart, periodEnd, accountName, listSubsidiary, companyInfo);
			listPdfPTable.add(tableInfo);
		}

		stampPdf.createPdf(request, response, listPdfPTable, title);

	}

	private PdfPTable stampPdfTable(String fontsPath, String periodStart, String periodEnd, String accountName, List<Subsidiary> listSubsidiary, TCustomer companyInfo) throws IOException {
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
		String sub1 = "科目：" + accountName; // 上左
		String sub2 = accountPeriod; // 上中
		String fur = "单位：元"; // 获取货币种类
		String sub3 = fur; // 上右
		String remarks = companyInfo.getCustomerName(); // 下作
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 打印时间
		String stampTime = "打印时间：" + sdf.format(new Date());

		PdfPTable tableInfo = new PdfPTable(1);
		tableInfo.setWidthPercentage(100);
		PdfPTable table = null;
		PdfPTable tableSub = null;
		PdfPTable tableRemarks = null;
		try {
			table = tBuilder.createPdf(fontsPath, 7, 90, new float[] { 0.11f, 0.11f, 0.30f, 0.14f, 0.14f, 0.06f, 0.14f });
			tableSub = tBuilder.createSub(fontsPath, sub1, sub2, sub3);
			tableRemarks = tBuilder.createRemarks(fontsPath, remarks, stampTime);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// -----------------Table Cells Label/Value------------------
		int n = 1;
		String left = "left";
		String center = "center";
		String right = "right";
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "日期", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "凭证字号", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "摘要", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "借方", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "贷方", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "方向", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "余额", 0, 0));
		for (Subsidiary su : listSubsidiary) {
			Date accountDate = su.getAccountDate();
			String accountDateString = null;
			if (accountDate != null) {
				accountDateString = sdf.format(accountDate).toString();
			}
			table.addCell(tBuilder.createValueCell(0, center, fontsPath, accountDateString, 0, 0));

			String getVoucherTitleName = su.getVoucherTitleName() == null ? "" : su.getVoucherTitleName();

			String getVoucherTitleNumber = su.getVoucherTitleNumber() == null ? "" : su.getVoucherTitleNumber();
			String voucherTitle = getVoucherTitleName + getVoucherTitleNumber;
			table.addCell(tBuilder.createValueCell(0, center, fontsPath, voucherTitle, 0, 0));

			String exp = su.getExp();
			table.addCell(tBuilder.createValueCell(0, left, fontsPath, exp, 0, 0));

			String debit = su.getDebit();
			table.addCell(tBuilder.createValueCell(0, right, fontsPath, debit, 0, 0));

			String credit = su.getCredit();
			table.addCell(tBuilder.createValueCell(0, right, fontsPath, credit, 0, 0));

			String dc = "";
			if (su.getDc().equals("1")) {
				dc = "借";
			} else {
				dc = "贷";
			}
			table.addCell(tBuilder.createValueCell(0, center, fontsPath, dc, 0, 0));

			String balance = su.getBalance();
			table.addCell(tBuilder.createValueCell(0, right, fontsPath, balance, 0, 0));
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

		// 打印
		// stampPdf.createPdf(request, response, listPdfPTable,
		// title);
		return tableInfo;
	}

	/**
	 * 期初余额
	 * 
	 * @param listTBalance
	 * @param tb
	 * @param s
	 * @param beginMoney
	 * @return
	 */
	public Subsidiary totalStart(TBalance tb, Object obj) {
		Subsidiary start = new Subsidiary();
		String getBeginbalance = "";
		BigDecimal beginMoney = BigDecimal.ZERO;
		if (tb != null) { // 如果不为空
			getBeginbalance = tb.getBeginbalance() == null || tb.getBeginbalance().equals("") ? "0" : tb.getBeginbalance();
		}
		if (obj instanceof Subsidiaryledge) {
			Subsidiaryledge s = (Subsidiaryledge) obj;
			String dc = "";
			if (s.getDc().equals("1")) {
				dc = "借";
				beginMoney = new BigDecimal(getBeginbalance);
			} else {
				dc = "贷";
				beginMoney = new BigDecimal(getBeginbalance).negate();
			}
			start.setAccId(s.getAccId());
			start.setDc(dc);
			start.setAccountDate(s.getAccountDate()); // 获取记账日期
		}
		if (obj instanceof Accountbalance) {
			Accountbalance a = (Accountbalance) obj;
			StringBuffer accountPeriod = new StringBuffer( a.getAccountPeriod());
			Date accountDate =start.getAccountDate() ;
			String accDateString = "";
			if (accountDate == null) {
				accDateString = accountPeriod.insert(4,'-').append("-01").toString();
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				accountDate= formatter.parse(accDateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			start.setAccountDate(getLastDayOfMonth(accountDate));
			String dc = "";
			if (a.getDc().equals("1")) {
				dc = "借";
				beginMoney = new BigDecimal(a.getBeginbalance());
			} else {
				dc = "贷";
				beginMoney = new BigDecimal(a.getBeginbalance()).negate();
			}
			start.setAccId(a.getId());
			start.setDc(dc);
		}
		start.setExp("期初余额");
		this.setBeginMoney(beginMoney);
		start.setBalance(moneyFormat.format(beginMoney)); // 期初金额
		return start;
	}

	/**
	 * 本期合计
	 * 
	 * @param tb
	 * @param s
	 * @return
	 */
	public Subsidiary totalPeriod(TBalance tb, Object obj) {
		Subsidiary su1 = new Subsidiary();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String banlanceString = "";
		BigDecimal banlanceDouble = BigDecimal.ZERO;
		Date tbAccDate = new Date();
		if (tb != null) {
			String tbAccP = new StringBuffer(tb.getAccountPeriod()).insert(4,'-').append("-01").toString();
			try {
				tbAccDate= formatter.parse(tbAccP);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String debitTotalString = tb.getDebittotalamount() == null || tb.getDebittotalamount().equals("") ? "0" : tb.getDebittotalamount();
			BigDecimal debitTotalDouble = new BigDecimal(debitTotalString);
			if (debitTotalDouble.compareTo(BigDecimal.ZERO) != 0) {
				su1.setDebit(moneyFormat.format(debitTotalDouble)); // 将本期合计借设置借
			} else {
				su1.setDebit(null); // 将本期合计借设置借
			}
			su1.setAccId(tb.getAccountId());
			String creditTotalString = tb.getCredittotalamount() == null || tb.getCredittotalamount().equals("") ? "0" : tb.getCredittotalamount();
			BigDecimal creditTotalDouble = new BigDecimal(creditTotalString);
			if (creditTotalDouble.compareTo(BigDecimal.ZERO) != 0) {
				su1.setCredit(moneyFormat.format(creditTotalDouble)); // 将本期合计贷设置到贷
			} else {
				su1.setCredit(null); // 将本期合计贷设置到贷
			}

			banlanceString = tb.getEndbalance() == null || tb.getEndbalance().equals("") ? "0" : tb.getEndbalance();

		}
		String dc = "";
		if (obj instanceof Subsidiaryledge) {
			Subsidiaryledge s = (Subsidiaryledge) obj;
			if (tbAccDate.getYear() == s.getAccountDate().getYear() && tbAccDate.getMonth() == s.getAccountDate().getMonth()) {	//判断两个日期是否处于同一个月
				su1.setAccountDate(s.getAccountDate()); // 获取记账日期	
			}else {
				su1.setAccountDate(getLastDayOfMonth(tbAccDate)); // 获取记账日期
			}
			if (s.getDc().equals("1")) {
				dc = "借";
				banlanceDouble = new BigDecimal(banlanceString);
			} else {
				dc = "贷";
				banlanceDouble = new BigDecimal(banlanceString).negate();
			}
			su1.setDc(dc);
		}
		if (obj instanceof Accountbalance) {
			Accountbalance a = (Accountbalance) obj;
			StringBuffer accountPeriod = new StringBuffer( a.getAccountPeriod());
			Date accountDate =su1.getAccountDate() ;
			String accDateString = "";
			if (accountDate == null) {
				accDateString = accountPeriod.insert(4,'-').append("-01").toString();
			}
			
			try {
				accountDate= formatter.parse(accDateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			su1.setAccountDate(getLastDayOfMonth(accountDate));
			su1.setBalance(a.getEndbalance());
			if (a.getDc().equals("1")) {
				dc = "借";
				banlanceDouble = new BigDecimal(a.getEndbalance());
			} else {
				dc = "贷";
				banlanceDouble = new BigDecimal(a.getEndbalance()).negate();
			}
			su1.setDc(dc);
		}
		if (banlanceDouble.compareTo(BigDecimal.ZERO) != 0) {
			su1.setBalance(moneyFormat.format(banlanceDouble));
		} else {
			su1.setBalance(null);
		}

		su1.setExp("本期合计");
		return su1;
	}

	/**
	 * 本年累计
	 * 
	 * @param tb
	 * @param s
	 * @return
	 */
	public Subsidiary totalYear(TBalance tb, Object obj) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Subsidiary su2 = new Subsidiary();
		String banlanceString = "";
		BigDecimal banlanceDouble = BigDecimal.ZERO;
		Date tbAccDate = new Date();
		if (tb != null) {
			String tbAccP = new StringBuffer(tb.getAccountPeriod()).insert(4,'-').append("-01").toString();
			try {
				tbAccDate= formatter.parse(tbAccP);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String ydebitTotalString = tb.getYtddebittotalamount() == null || tb.getYtddebittotalamount().equals("") ? "0" : tb.getYtddebittotalamount();
			BigDecimal ydebitTotalDouble = new BigDecimal(ydebitTotalString);
			if (ydebitTotalDouble.compareTo(BigDecimal.ZERO) != 0) {
				su2.setDebit(moneyFormat.format(ydebitTotalDouble)); // 将本期合计借设置借
			} else {
				su2.setDebit(null); // 将本期合计借设置借
			}
			su2.setAccId(tb.getAccountId());
			String ycreditTotalString = tb.getYtdcredittotalamount() == null || tb.getYtdcredittotalamount().equals("") ? "0" : tb.getYtdcredittotalamount();
			BigDecimal ycreditTotalDouble = new BigDecimal(ycreditTotalString);
			if (ycreditTotalDouble.compareTo(BigDecimal.ZERO) != 0) {
				su2.setCredit(moneyFormat.format(ycreditTotalDouble)); // 将本期合计贷设置到贷
			} else {
				su2.setCredit(null); // 将本期合计贷设置到贷
			}

			banlanceString = tb.getEndbalance() == null || tb.getEndbalance().equals("") ? "0" : tb.getEndbalance();
		}
		String dc = "";
		if (obj instanceof Subsidiaryledge) {
			Subsidiaryledge s = (Subsidiaryledge) obj;
			if (tbAccDate.getYear() == s.getAccountDate().getYear() && tbAccDate.getMonth() == s.getAccountDate().getMonth()) {	//判断两个日期是否处于同一个月
				su2.setAccountDate(s.getAccountDate()); // 获取记账日期	
			}else {
				su2.setAccountDate(getLastDayOfMonth(tbAccDate)); // 获取记账日期
			}

			if (s.getDc().equals("1")) {
				dc = "借";
				banlanceDouble = new BigDecimal(banlanceString);
			} else {
				dc = "贷";
				banlanceDouble = new BigDecimal(banlanceString).negate();
			}
			su2.setDc(dc);
		}
		if (obj instanceof Accountbalance) {
			Accountbalance a = (Accountbalance) obj;
			StringBuffer accountPeriod = new StringBuffer( a.getAccountPeriod());
			Date accountDate =su2.getAccountDate() ;
			String accDateString = "";
			if (accountDate == null) {
				accDateString = accountPeriod.insert(4,'-').append("-01").toString();
			}
			
			try {
				accountDate= formatter.parse(accDateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			su2.setAccountDate(getLastDayOfMonth(accountDate));
			su2.setBalance(a.getEndbalance());
			if (a.getDc().equals("1")) {
				dc = "借";
				banlanceDouble = new BigDecimal(a.getEndbalance());
			} else {
				dc = "贷";
				banlanceDouble = new BigDecimal(a.getEndbalance()).negate();
			}
			su2.setDc(dc);
		}
		if (banlanceDouble.compareTo(BigDecimal.ZERO) != 0) {
			su2.setBalance(moneyFormat.format(banlanceDouble));
		} else {
			su2.setBalance(null);
		}

		su2.setExp("本年累计");
		return su2;
	}

	/**
	 * 获取所有的账期
	 * 
	 * @param model
	 * @param companyId
	 */
	@RequestMapping(value = { "getAllAccountPeriod", "" })
	@ResponseBody
	public Object getAllAccountPeriod(Model model, HttpSession session) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();// //获取当前账期
		List<String> listAccountperiod = tBalanceService.getAllAccountperiod(companyId);
		boolean flag = false;
		for (String str : listAccountperiod) {
			if (str.equals(defaultPeriod)) {
				flag = true; // 如果存在
			}
		}
		// 如果不存在
		if (!flag) {
			listAccountperiod.add(defaultPeriod);
		}
		Map<String, Object> mapPeriod = Maps.newHashMap();
		mapPeriod.put("listAccountperiod", listAccountperiod);
		mapPeriod.put("defaultPeriod", defaultPeriod);
		return mapPeriod;
	}

	/**
	 * 显示所有发生金额变化的科目
	 * 
	 * @param model返回
	 * 
	 */
	@RequestMapping(value = { "listAccount", "" })
	@ResponseBody
	public Object listAccount(Model model, HttpSession session, String accountPeriod, String periodEnd) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		List<Accountbalance> listAccounts = accountbalanceService.findHappenByAcc(companyId, accountPeriod, periodEnd, null, null);
		if (CollectionUtils.isNotEmpty(listAccounts)) {
			for (Accountbalance acc : listAccounts) {
				List<TAccount> pareAccounts = tAccountService.getParentAccounts(acc.getParent().getId());
				StringBuffer parentNameString = new StringBuffer();
				if (CollectionUtils.isNotEmpty(pareAccounts)) {
					for (int i = pareAccounts.size(); i > 0; i--) {
						parentNameString.append(pareAccounts.get(i - 1).getAccountName());
					}
				}
				acc.setParentName(parentNameString.toString());
			}
		}
		this.setListAccs(listAccounts);
		return listAccounts;
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
	
	public static Date getLastDayOfMonth(Date sDate1) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(sDate1);
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		return lastDate;
	}
}