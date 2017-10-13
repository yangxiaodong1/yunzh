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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

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

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.books.entity.Accountbalance;
import com.thinkgem.jeesite.modules.books.entity.SubjectBalance;
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
 * 科目余额表Controller
 * 
 * @author zhangtong
 * @version 2015-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/books/subjectBalance")
public class SubjectBalanceController extends BaseController {

	@Autowired
	private AccountbalanceService accountbalanceService;
	@Autowired
	private TVoucherService tVoucherService;
	@Autowired
	private TBalanceService tBalanceService;
	@Autowired
	private TAccountService tAccountService;
	@Autowired
	private TableBuilder tBuilder;
	@Autowired
	private StampPdf stampPdf;
	@Autowired
	private DownloadController downloadController;
	public static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
	public static NumberFormat moneyFormat = new DecimalFormat("#,##0.00");
	
	@RequestMapping(value = { "listSubjectBalance", "" })
	public String listSubjectBalance(Model model) {
		
		return "modules/books/vSubjectBalance";
	}
	
	@RequestMapping(value = { "list", "" })
	@ResponseBody
	public Object list(String accountPeriod, String periodEnd, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();
		
		TBalance tBalance = new TBalance(); 
		Accountbalance accountbalance = new Accountbalance();
		
		/**
		 * 第一次加载时处理的事件
		 */
		if (accountPeriod == null || accountPeriod.equals("")) {
			accountbalance.setAccountPeriod(defaultPeriod); // 设置账期前
			accountbalance.setPeriodEnd(defaultPeriod); // 设置账期后
		}

		accountbalance.setFdbid(companyId); // 赋值当前公司的id
		tBalance.setFdbid(companyId); // 赋值当前公司的id

		tBalance.setAccountPeriod(accountPeriod); // 赋值当前账期前
		tBalance.setPeriodEnd(periodEnd); // 赋值当前账期后
		String accLevel = null; // 控制显示几级科目
		List<Accountbalance> listHappenAcc = accountbalanceService.findHappenByAcc(companyId, tBalance.getAccountPeriod(), tBalance.getPeriodEnd(), null, accLevel); // 显示所有发生金额变化的科目
		List<TBalance> listTBalance = tBalanceService.findList(tBalance);

		List<SubjectBalance> listSubjectBalance = new ArrayList<SubjectBalance>(); // 声明一个list用于保存数据显示于界面

		BigDecimal startDebit = BigDecimal.ZERO; // 期初借 合计
		BigDecimal startCredit = BigDecimal.ZERO; // 期初贷 合计

		BigDecimal credittotalamountTotal = BigDecimal.ZERO; // 本期发生额 贷方 合计
		BigDecimal debittotalamountTotal = BigDecimal.ZERO; // 本期发生额 借方 合计
		BigDecimal ytdcredittotalamountTotal = BigDecimal.ZERO; // 本年累计发生额 贷方 合计
		BigDecimal ytddebittotalamountTotal = BigDecimal.ZERO; // 本年累计发生额 借方 合计

		BigDecimal endDebit = BigDecimal.ZERO; // 期末借 合计
		BigDecimal endCredit = BigDecimal.ZERO;// 期末贷 合计

		for (Accountbalance acc : listHappenAcc) {

			BigDecimal creditAmount = BigDecimal.ZERO; // 本期发生额 贷方
			BigDecimal debitAmount = BigDecimal.ZERO; // 本期发生额 借方
			BigDecimal ytdcreditAmount = BigDecimal.ZERO; // 本年累计发生额 贷方
			BigDecimal ytddebitAmount = BigDecimal.ZERO; // 本年累计发生额 借方
			int minPeriod = Integer.valueOf(accountPeriod); // 最小的账期
																				// 用于多期查询时候
			int maxPeriod = Integer.valueOf(periodEnd); // 最大的账期
																				// 用于多期查询时候
			SubjectBalance su = new SubjectBalance();
			//getAccParentName(acc); //添加父级科目
			for (TBalance tb : listTBalance) {

				if (acc.getId().equals(tb.getAccountId())) {

					String getCredit = tb.getCredittotalamount() == null || tb.getCredittotalamount().equals("") ? "0" : tb.getCredittotalamount();
					creditAmount = creditAmount.add(new BigDecimal(getCredit)); // 本期发生额 贷方
					
					String getDebit = tb.getDebittotalamount() == null || tb.getDebittotalamount().equals("") ? "0" : tb.getDebittotalamount();
					debitAmount =debitAmount.add(new BigDecimal(getDebit)); // 本期发生额 借方

					/**
					 * 当查询多期时候 这是 期数中 第一期显示的保存的期初余额
					 */
					int period = Integer.valueOf(tb.getAccountPeriod());
					if (minPeriod >= period) { // 等于最小账期
						minPeriod = period;
						String getBeginbalance = "";
						BigDecimal beginBalanceDouble = BigDecimal.ZERO;
						if (acc.getDc().equals("1")) { // 借方
							getBeginbalance = tb.getBeginbalance() == null || tb.getBeginbalance().equals("") ? "0" : tb.getBeginbalance();
							beginBalanceDouble =beginBalanceDouble.add(new BigDecimal(getBeginbalance));
							if(acc.getAccLevel().equals("1")){	//合计计算一级科目的金额
								startDebit = startDebit.add(beginBalanceDouble);
							}
							if (beginBalanceDouble.compareTo(BigDecimal.ZERO)!= 0) {
								su.setBeginDebit(moneyFormat.format(beginBalanceDouble)); // 期初余额
							} else {
								su.setBeginDebit(null);
							}

						} else {
							getBeginbalance = tb.getBeginbalance() == null || tb.getBeginbalance().equals("") ? "0" : tb.getBeginbalance();
							beginBalanceDouble = new BigDecimal(getBeginbalance).negate(); // 如果是贷方余额转为相反数
							if(acc.getAccLevel().equals("1")){	//合计计算一级科目的金额
								startCredit = startCredit.add(beginBalanceDouble);
							}
							if (beginBalanceDouble.compareTo(BigDecimal.ZERO) != 0) {
								su.setBeginCredit(moneyFormat.format(beginBalanceDouble)); // 期初余额
							} else {
								su.setBeginCredit(null); // 期初余额
							}

						}
					}
					// 期末余额
					if (maxPeriod <= period) {
						String getYtdcredit = tb.getYtdcredittotalamount() == null || tb.getYtdcredittotalamount().equals("") ? "0" : tb.getYtdcredittotalamount();
						ytdcreditAmount = new BigDecimal(getYtdcredit); // 本年累计发生额 贷方
													
						String getYtddebit = tb.getYtddebittotalamount() == null || tb.getYtddebittotalamount().equals("") ? "0" : tb.getYtddebittotalamount();
						ytddebitAmount = new BigDecimal(getYtddebit); // 本年累计发生额 借方

						maxPeriod = period;
						String getEndbalance = "";
						BigDecimal endBalanceDouble = BigDecimal.ZERO;
						if (acc.getDc().equals("1")) {
							getEndbalance = tb.getEndbalance() == null || tb.getEndbalance().equals("") ? "0" : tb.getEndbalance();
							endBalanceDouble = new BigDecimal(getEndbalance);
							if(acc.getAccLevel().equals("1")){	//合计计算一级科目的金额
								endDebit = endDebit.add(endBalanceDouble);
							}
							if (endBalanceDouble.compareTo(BigDecimal.ZERO) != 0) {
								su.setEndDebit(moneyFormat.format(endBalanceDouble)); // 期末余额
							} else {
								su.setEndDebit(null); // 期末余额
							}

						} else {
							getEndbalance = tb.getEndbalance() == null || tb.getEndbalance().equals("") ? "0" : tb.getEndbalance();
							endBalanceDouble = new BigDecimal(getEndbalance).negate(); // 如果是贷方余额转为相反数
							if(acc.getAccLevel().equals("1")){	//合计计算一级科目的金额
								endCredit = endCredit.add(endBalanceDouble);
							}
							if (endBalanceDouble.compareTo(BigDecimal.ZERO )!= 0) {
								su.setEndCredit(moneyFormat.format(endBalanceDouble)); // 期末余额
							} else {
								su.setEndCredit(null); // 期末余额
							}

						}
					}

				}
			}
			su.setAccLevel(acc.getAccLevel());	//几级科目
			su.setId(acc.getId()); // 保存科目id
			su.setAccuntid(acc.getAccuntId()); // 保存科目编号
			su.setAccountName(acc.getAccountName()); // 保存科目名称
			String creditAmountString = "";
			String debitAmountString = "";
			String ytdcreditAmountString = "";
			String ytddebitAmountString = "";
			if (creditAmount.compareTo(BigDecimal.ZERO) != 0) {
				creditAmountString = moneyFormat.format(creditAmount); // 本期发生额
			}
			if (debitAmount.compareTo(BigDecimal.ZERO) != 0) {
				debitAmountString = moneyFormat.format(debitAmount); // 本期发生额 借方
			}
			if (ytdcreditAmount.compareTo(BigDecimal.ZERO) != 0) {
				ytdcreditAmountString = moneyFormat.format(ytdcreditAmount); // 本年累计发生额
			}
			if (ytddebitAmount.compareTo(BigDecimal.ZERO) != 0) {
				ytddebitAmountString = moneyFormat.format(ytddebitAmount); // 本期发生额
			}

			su.setCredittotalamount(creditAmountString); // 本期发生额
			su.setDebittotalamount(debitAmountString); // 本期发生额 借方
			su.setYtdcredittotalamount(ytdcreditAmountString); // 本年累计发生额
			su.setYtddebittotalamount(ytddebitAmountString);

			if(acc.getAccLevel().equals("1")){	//合计计算一级科目的金额
				credittotalamountTotal =credittotalamountTotal.add(creditAmount); // 本期发生额 贷方 合计
				debittotalamountTotal = debittotalamountTotal.add(debitAmount); // 本期发生额 借方 合计
				ytdcredittotalamountTotal = ytdcredittotalamountTotal.add(ytdcreditAmount); // 本年累计发生额 贷方 合计
				ytddebittotalamountTotal = ytddebittotalamountTotal.add(ytddebitAmount); // 本年累计发生额 借方 合计	
			}
			
			listSubjectBalance.add(su); // 数据存储在显示在list集合中
		}
		listSubjectBalance.add(this.totalBalance(startDebit, startCredit, credittotalamountTotal, debittotalamountTotal, ytdcredittotalamountTotal, ytddebittotalamountTotal, endDebit, endCredit));
		session.setAttribute("downloadSubjectBalance", listSubjectBalance);
		return listSubjectBalance;
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 */
	@RequestMapping(value = { "downloadSubjectBalance", "" })
	public void downloadSubjectBalance(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		String docsPath = request.getSession().getServletContext().getRealPath("docs");
		String fileName = "科目余额表_" + System.currentTimeMillis() + ".xlsx";

		List<SubjectBalance> listSubjectBalance = (ArrayList<SubjectBalance>) session.getAttribute("downloadSubjectBalance");
		String filePath = docsPath + FILE_SEPARATOR + fileName;
		try {
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
			// 输出流
			OutputStream os = new FileOutputStream(filePath);
			// 工作区
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(accountPeriod + "科目余额表");
			sheet.setVerticallyCenter(true);
			/**
			 * 资产负债的宽度
			 */
			downloadController.subjectBalanceStyleWidth(sheet);

			/*
			 * 列名
			 */
			XSSFRow rowColumn = sheet.createRow(0);

			XSSFCell cellColumn0 = rowColumn.createCell(0);
			cellColumn0.setCellStyle(downloadController.columnSubjectBalanceStyle1(wb, sheet));
			cellColumn0.setCellValue("科目编码");
			XSSFCell cellColumn1 = rowColumn.createCell(1);
			cellColumn1.setCellStyle(downloadController.columnSubjectBalanceStyle2(wb, sheet));
			cellColumn1.setCellValue("科目名称");
			XSSFCell cellColumn2 = rowColumn.createCell(2);
			cellColumn2.setCellStyle(downloadController.columnSubjectBalanceStyle3(wb, sheet));
			cellColumn2.setCellValue("期初余额");
			XSSFCell cellColumn3 = rowColumn.createCell(4);
			cellColumn3.setCellStyle(downloadController.columnSubjectBalanceStyle4(wb, sheet));
			cellColumn3.setCellValue("本期发生额");
			XSSFCell cellColumn4 = rowColumn.createCell(6);
			cellColumn4.setCellStyle(downloadController.columnSubjectBalanceStyle5(wb, sheet));
			cellColumn4.setCellValue("本年累计发生额");
			XSSFCell cellColumn5 = rowColumn.createCell(8);
			cellColumn5.setCellStyle(downloadController.columnSubjectBalanceStyle6(wb, sheet));
			cellColumn5.setCellValue("期末余额");

			XSSFRow rowSubColumn = sheet.createRow(1);
			XSSFCell cellSubColumn2 = rowSubColumn.createCell(2);
			cellSubColumn2.setCellStyle(downloadController.columnStyle(wb));
			cellSubColumn2.setCellValue("借方");
			XSSFCell cellSubColumn3 = rowSubColumn.createCell(3);
			cellSubColumn3.setCellStyle(downloadController.columnStyle(wb));
			cellSubColumn3.setCellValue("贷方");
			XSSFCell cellSubColumn4 = rowSubColumn.createCell(4);
			cellSubColumn4.setCellStyle(downloadController.columnStyle(wb));
			cellSubColumn4.setCellValue("借方");
			XSSFCell cellSubColumn5 = rowSubColumn.createCell(5);
			cellSubColumn5.setCellStyle(downloadController.columnStyle(wb));
			cellSubColumn5.setCellValue("贷方");
			XSSFCell cellSubColumn6 = rowSubColumn.createCell(6);
			cellSubColumn6.setCellStyle(downloadController.columnStyle(wb));
			cellSubColumn6.setCellValue("借方");
			XSSFCell cellSubColumn7 = rowSubColumn.createCell(7);
			cellSubColumn7.setCellStyle(downloadController.columnStyle(wb));
			cellSubColumn7.setCellValue("贷方");
			XSSFCell cellSubColumn8 = rowSubColumn.createCell(8);
			cellSubColumn8.setCellStyle(downloadController.columnStyle(wb));
			cellSubColumn8.setCellValue("借方");
			XSSFCell cellSubColumn9 = rowSubColumn.createCell(9);
			cellSubColumn9.setCellStyle(downloadController.columnStyle(wb));
			cellSubColumn9.setCellValue("贷方");

			int n = 2;
			String left = "left";
			String center = "center";
			String right = "right";
			for (SubjectBalance sb : listSubjectBalance) {
				XSSFRow rowsInfo = sheet.createRow(n);
				// 给这一行的第一列赋值
				String accountId = sb.getAccuntid();
				XSSFCell cellInfo0 = rowsInfo.createCell(0);
				cellInfo0.setCellStyle(downloadController.infoStyle(wb, left));
				cellInfo0.setCellValue(accountId);

				String accountName = sb.getAccountName();
				XSSFCell cellInfo1 = rowsInfo.createCell(1);
				cellInfo1.setCellStyle(downloadController.infoStyle(wb, left));
				cellInfo1.setCellValue(accountName);

				String beginDebit = sb.getBeginDebit();// == null
				// || sb.getBeginDebit().equals("") ? "0" : sb
				// .getBeginDebit();
				// beginDebit = beginDebit.equals("0") ||
				// beginDebit.equals("0.0")
				// || beginDebit.equals("0.00") ? "" : beginDebit;
				// double beginDebitDouble = Double.valueOf(beginDebit);
				// if (beginDebitDouble != 0) {
				// beginDebit = moneyFormat.format(beginDebitDouble);
				// } else {
				// beginDebit = null;
				// }
				XSSFCell cellInfo2 = rowsInfo.createCell(2);
				cellInfo2.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo2.setCellValue(beginDebit);

				String beginCredit = sb.getBeginCredit();// == null
				// || sb.getBeginCredit().equals("") ? "0" : sb
				// .getBeginCredit();
				// beginCredit = beginCredit.equals("0") ||
				// beginCredit.equals("0.0")
				// || beginCredit.equals("0.00") ? "" : beginCredit;
				// double beginCreditDouble = Double.valueOf(beginCredit);
				// if (beginCreditDouble != 0) {
				// beginCredit = moneyFormat.format(beginCreditDouble);
				// } else {
				// beginCredit = null;
				// }
				XSSFCell cellInfo3 = rowsInfo.createCell(3);
				cellInfo3.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo3.setCellValue(beginCredit);

				String debit = sb.getDebittotalamount();// == null
				// || sb.getDebittotalamount().equals("") ? "0" : String
				// .valueOf(sb.getDebittotalamount());
				debit = debit.equals("0") || debit.equals("0.0") || debit.equals("0.00") ? "" : debit;
				// double debitDouble = Double.valueOf(debit);
				// if (debitDouble != 0) {
				// debit = moneyFormat.format(debitDouble);
				// } else {
				// debit = null;
				// }
				XSSFCell cellInfo4 = rowsInfo.createCell(4);
				cellInfo4.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo4.setCellValue(debit);

				String credit = sb.getCredittotalamount();// == null
				// || sb.getCredittotalamount().equals("") ? "0" : sb
				// .getCredittotalamount();
				// credit = credit.equals("0") || credit.equals("0.0")
				// || credit.equals("0.00") ? "" : credit;
				// double creditDouble = Double.valueOf(credit);
				// if (creditDouble != 0) {
				// credit = moneyFormat.format(creditDouble);
				// } else {
				// credit = null;
				// }
				XSSFCell cellInfo5 = rowsInfo.createCell(5);
				cellInfo5.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo5.setCellValue(credit);

				String ydebit = sb.getYtddebittotalamount();// == null
				// || sb.getYtddebittotalamount().equals("") ? "0" : sb
				// .getYtddebittotalamount();
				// ydebit = ydebit.equals("0") || ydebit.equals("0.0")
				// || ydebit.equals("0.00") ? "" : ydebit;
				// double ydebitDouble = Double.valueOf(ydebit);
				// if (ydebitDouble != 0) {
				// ydebit = moneyFormat.format(ydebitDouble);
				// } else {
				// ydebit = null;
				// }
				XSSFCell cellInfo6 = rowsInfo.createCell(6);
				cellInfo6.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo6.setCellValue(ydebit);

				String ycredit = sb.getYtdcredittotalamount();// == null
				// || sb.getYtdcredittotalamount().equals("") ? "0" : sb
				// .getYtdcredittotalamount();
				// ycredit = ycredit.equals("0") || ycredit.equals("0.0")
				// || ycredit.equals("0.00") ? "" : ycredit;
				// double ycreditDouble = Double.valueOf(ycredit);
				// if (ycreditDouble != 0) {
				// ycredit = moneyFormat.format(ycreditDouble);
				// } else {
				// ycredit = null;
				// }
				XSSFCell cellInfo7 = rowsInfo.createCell(7);
				cellInfo7.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo7.setCellValue(ycredit);

				String endDebit = sb.getEndDebit();// == null
				// || sb.getEndDebit().equals(""); ? "0" : sb.getEndDebit();
				// endDebit = endDebit.equals("0") || endDebit.equals("0.0")
				// || endDebit.equals("0.00") ? "" : endDebit;
				// double endDebitDouble = Double.valueOf(endDebit);
				// if (endDebitDouble != 0) {
				// endDebit = moneyFormat.format(endDebitDouble);
				// } else {
				// endDebit = null;
				// }
				XSSFCell cellInfo8 = rowsInfo.createCell(8);
				cellInfo8.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo8.setCellValue(endDebit);

				String endCredit = sb.getEndCredit();// == null
				// || sb.getEndCredit().equals("") ? "0" : sb
				// .getEndCredit();
				// endCredit = endCredit.equals("0") || endCredit.equals("0.0")
				// || endCredit.equals("0.00") ? "" : endCredit;
				// double endCreditDouble = Double.valueOf(endCredit);
				// if (endCreditDouble != 0) {
				// endCredit = moneyFormat.format(endCreditDouble);
				// } else {
				// endCredit = null;
				// }
				XSSFCell cellInfo9 = rowsInfo.createCell(9);
				cellInfo9.setCellStyle(downloadController.infoStyle(wb, right));
				cellInfo9.setCellValue(endCredit);

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
	@RequestMapping(value = { "stampSubjectBalance", "" })
	public void stampSubjectBalance(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws IOException {
		List<SubjectBalance> listSubjectBalance = (ArrayList<SubjectBalance>) session.getAttribute("downloadSubjectBalance");
		List<PdfPTable> listPdfPTable = new ArrayList<PdfPTable>();
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
		String title = "科目余额"; // 标题
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String sub1 = companyInfo.getCustomerName(); // 上左
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
			table = tBuilder.createPdf(fontsPath, 10, 90, new float[] { 0.11f, 0.11f, 0.09f, 0.09f, 0.09f, 0.09f, 0.09f, 0.09f, 0.09f, 0.09f });
			tableSub = tBuilder.createSub(fontsPath, sub1, sub2, sub3);
			tableRemarks = tBuilder.createRemarks(fontsPath, remarks, stampTime);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// -----------------Table Cells Label/Value------------------
		int n = 0;
		String left = "left";
		String center = "center";
		String right = "right";
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "科目编码", 2, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "科目名称", 2, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "期初余额", 0, 2));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "本期发生额", 0, 2));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "本年累计发生额", 0, 2));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "期末余额", 0, 2));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "借方1", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "贷方1", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "借方2", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "贷方2", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "借方3", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "贷方3", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "借方4", 0, 0));
		table.addCell(tBuilder.createValueCell(1, center, fontsPath, "贷方4", 0, 0));
		for (SubjectBalance sb : listSubjectBalance) {

			// 给这一行的第一列赋值
			String accountId = sb.getAccuntid();
			table.addCell(tBuilder.createValueCell(n, left, fontsPath, accountId, 0, 0));

			String accountName = sb.getAccountName();
			table.addCell(tBuilder.createValueCell(n, left, fontsPath, accountName, 0, 0));

			String beginDebit = sb.getBeginDebit();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, beginDebit, 0, 0));

			String beginCredit = sb.getBeginCredit();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, beginCredit, 0, 0));

			String debit = sb.getDebittotalamount();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, debit, 0, 0));

			String credit = sb.getCredittotalamount();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, credit, 0, 0));

			String ydebit = sb.getYtddebittotalamount();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, ydebit, 0, 0));

			String ycredit = sb.getYtdcredittotalamount();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, ycredit, 0, 0));

			String endDebit = sb.getEndDebit();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, endDebit, 0, 0));

			String endCredit = sb.getEndCredit();
			table.addCell(tBuilder.createValueCell(n, right, fontsPath, endCredit, 0, 0));
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
		listPdfPTable.add(tableInfo);
		stampPdf.createPdf(request, response, listPdfPTable, title);
	}

	/**
	 * 用来存储合计
	 * 
	 * @param cTotal
	 *            本期发生额 贷方 合计
	 * @param dTotal
	 *            本期发生额 借方 合计
	 * @param yCTotal
	 *            本年累计发生额 贷方 合计
	 * @param yDTotal
	 *            本年累计发生额 借方 合计
	 * @return
	 */
	public SubjectBalance totalBalance(BigDecimal sDebit, BigDecimal sCerdit, BigDecimal cTotal, BigDecimal dTotal, BigDecimal yCTotal, BigDecimal yDTotal, BigDecimal eDebit, BigDecimal eCredit) {
		SubjectBalance su = new SubjectBalance();
		String sDebitString = "";
		if (sDebit.compareTo(BigDecimal.ZERO) != 0) {
			sDebitString = moneyFormat.format(sDebit);
		}
		String sCerditString = "";
		if (sCerdit.compareTo(BigDecimal.ZERO) != 0) {
			sCerditString = moneyFormat.format(sCerdit);
		}
		String cTotalString = "";
		if (cTotal.compareTo(BigDecimal.ZERO) != 0) {
			cTotalString = moneyFormat.format(cTotal);
		}
		String dTotalString = "";
		if (dTotal.compareTo(BigDecimal.ZERO) != 0) {
			dTotalString = moneyFormat.format(dTotal);
		}
		String yCTotalString = "";
		if (yCTotal.compareTo(BigDecimal.ZERO) != 0) {
			yCTotalString = moneyFormat.format(yCTotal);
		}
		String yDTotalString = "";
		if (yDTotal.compareTo(BigDecimal.ZERO) != 0) {
			yDTotalString = moneyFormat.format(yDTotal);
		}
		String eDebitString = "";
		if (eDebit.compareTo(BigDecimal.ZERO) != 0) {
			eDebitString = moneyFormat.format(eDebit);
		}
		String eCreditString = "";
		if (eCredit.compareTo(BigDecimal.ZERO) != 0) {
			eCreditString = moneyFormat.format(eCredit);
		}
		su.setAccountName("合计");
		su.setBeginCredit(sCerditString); // 本期期初贷
		su.setBeginDebit(sDebitString); // 本期期初借
		su.setCredittotalamount(cTotalString); // 本期发生额 贷方
		su.setDebittotalamount(dTotalString); // 本期发生额 借方
		su.setYtdcredittotalamount(yCTotalString); // 本年累计发生额 贷方
		su.setYtddebittotalamount(yDTotalString); // 本期发生额 借方
		su.setEndCredit(eCreditString);
		su.setEndDebit(eDebitString);
		return su;
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
	 * 通过子级科目获取父级科目名称
	 * @param acc
	 */
	public void getAccParentName(Accountbalance accBalance){
		if (accBalance != null) {
			List<TAccount> pareAccounts = tAccountService.getParentAccounts(accBalance.getParent().getId());
			StringBuffer parentNameString = new StringBuffer();
			if (CollectionUtils.isNotEmpty(pareAccounts)) {
				
				for(int i = pareAccounts.size() ; i > 0 ; i --){
					parentNameString.append(pareAccounts.get(i-1).getAccountName() + "-");
				}
			}
			accBalance.setAccountName(parentNameString.toString() + accBalance.getAccountName());	
		}		
	}
}