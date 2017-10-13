/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.acctreportitem.entity.TAcctreportitem;
import com.thinkgem.jeesite.modules.acctreportitem.service.TAcctreportitemService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.rpt.entity.Accountbalanceperiod;
import com.thinkgem.jeesite.modules.rpt.entity.EBalance;
import com.thinkgem.jeesite.modules.rpt.entity.EFormula;
import com.thinkgem.jeesite.modules.rpt.entity.Reportbalance;
import com.thinkgem.jeesite.modules.rpt.entity.Reportformula;
import com.thinkgem.jeesite.modules.rpt.entity.TReportitem;
import com.thinkgem.jeesite.modules.rpt.service.AccountbalanceperiodService;
import com.thinkgem.jeesite.modules.rpt.service.ReportbalanceService;
import com.thinkgem.jeesite.modules.rpt.service.ReportformulaService;
import com.thinkgem.jeesite.modules.rpt.service.TReportitemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucher.service.TVoucherService;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 公式表Controller
 * 
 * @author zhangtong
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/formula")
public class FormulaController extends BaseController {

	@Autowired
	private ReportbalanceService reportbalanceService;
	@Autowired
	private ReportformulaService reportformulaService;
	@Autowired
	private BalanceController balanceController;
	@Autowired
	private ProfitController profitController;
	@Autowired
	private TAcctreportitemService tAcctreportitemService;
	@Autowired
	private TReportitemService tReportitemService;
	@Autowired
	private AccountbalanceperiodService accountbalanceperiodService;
	@Autowired
	private TAccountService tAccountService;
	static ScriptEngine jse = new ScriptEngineManager()
			.getEngineByName("JavaScript");

	/**
	 * 资产负债公式表
	 * 
	 * @param eBalance
	 * @param tReportitem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("rpt:tReportitem:view")
	@ResponseBody
	@RequestMapping(value = { "listBalance", "" })
	public List<EFormula> listBalance(TAcctreportitem tAcctreportitem,
			Accountbalanceperiod accountbalanceperiod,
			Reportformula reportformula, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();
		// //当前账期
		if (accountbalanceperiod.getAccountPeriod() == null
				|| accountbalanceperiod.getAccountPeriod().equals("")) {
			accountbalanceperiod.setAccountPeriod(defaultPeriod);
			accountbalanceperiod.setPeriodEnd(defaultPeriod);
		}
		String accountPeriod = accountbalanceperiod.getAccountPeriod();
		String periodEnd = accountbalanceperiod.getAccountPeriod();
		List<EFormula> listEFormula = new ArrayList<EFormula>(); // 定义list集合用于显示于也米娜
		double yearMoney = 0; // 年累计 合计
		double monthMoney = 0; // 月累计 合计
		String reportitem = "";
		if (tReportitemService.get(reportformula.getId()) == null) {
			reportitem = null;
		} else {
			reportitem = tReportitemService.get(reportformula.getId())
					.getReportitem();
		}
		// 报表id
		tAcctreportitem.setReportitem(reportitem);
		List<Reportformula> listReportForMula = reportformulaService
				.findList(reportformula);
		for (Reportformula re : listReportForMula) {
			EFormula ef = new EFormula();
			ef.setAccountperiod(accountPeriod); // 账期
			ef.setReportitemid(reportformula.getId()); // 报表id
			ef.setReportAccountId(re.getReportAccountId()); // 报表中科目编号
			ef.setReportitem(re.getReportitem()); // 报表编码
			ef.setAcctReportItemId(re.getAcctReportItemId()); // 公式id
			ef.setAccid(re.getAccId()); // 科目的id
			ef.setAccountName(re.getAccountName()); // 科目名称
			ef.setAccuntid(re.getAccuntId()); // 科目编号
			if (re.getFtype().equals("Y")) {
				ef.setFtype("余额");
			}
			if (re.getFtype().equals("C")) {
				ef.setFtype("贷方余额");
			}
			if (re.getFtype().equals("D")) {
				ef.setFtype("借方余额");
			}

			ef.setOp(re.getOp());
			// 根据科目id和账期在初始余额表和科目表的联合视图中 查询只有一个值
			String accId = re.getAccId();
			String accountId = re.getAccuntId();
			Accountbalanceperiod acc = accountbalanceperiodService
					.findAccBlanceByPeriod(accId, accountPeriod, companyId);
			if (acc != null) {
				// 年初余额没有实现
				double ym = balanceController.startYear(accountPeriod,
						periodEnd, re.getReportitem(), companyId, accountId).doubleValue();
				yearMoney += ym;
				String getEndbalance = acc.getEndbalance() == null
						|| acc.getEndbalance().equals("") ? "0" : acc
						.getEndbalance(); // 余额
				double getEndbalanceDouble = Double.valueOf(getEndbalance);
				String subReportItem = re.getReportitem().substring(0, 2); // 截取报表编号判断是资产类还是负债类和所有者权益
				/**
				 * 借方金额 （余额保存在汇总表（初始余额表）中为数值） 贷方金额 （余额保存在汇总表（初始余额表）中为相反数）
				 * 
				 * 资产类 借方科目 余额就取值 借方余额取值 贷方余额为0 资产类 贷方科目 余额取值即可 借方余额为0 贷方余额为相反数
				 * 
				 * 负债类 借方科目 余额为相反数 借方余额取值 贷方余额为0 负债类 贷方科目 余额为相反数 借方余额为0 贷方余额为相反数
				 */
				if (subReportItem.equals("01")) { // 资产类
					if (re.getDc().equals("1")) { // 借方科目
						if (re.getFtype().equals("C")) { // 贷方余额
							getEndbalanceDouble = 0; // 资产类 借方科目 贷方余额为0
						}
					}
					if (re.getDc().equals("-1")) { // 贷方科目
						if (re.getFtype().equals("D")) { // 借方余额
							getEndbalanceDouble = 0; // 资产类 贷方科目 借方余额为0
						}
						if (re.getFtype().equals("C")) { // 贷方余额
							getEndbalanceDouble = -getEndbalanceDouble; // 资产类
																		// 贷方科目
																		// 贷方余额为相反数
						}
					}
				}
				if (subReportItem.equals("02") // 负债类
						|| subReportItem.equals("03")) { // 所有者权益
					if (re.getDc().equals("-1")) { // 贷方科目
						if (re.getFtype().equals("D")) { // 借方余额
							getEndbalanceDouble = 0; // 负债类 贷方科目 借方余额为0
						}
						if (re.getFtype().equals("C")) { // 贷方余额
							getEndbalanceDouble = -getEndbalanceDouble; // 负债类
																		// 贷方科目
																		// 贷方余额为相反数
						}
						if (re.getFtype().equals("Y")) { // 余额
							getEndbalanceDouble = -getEndbalanceDouble; // 负债类
																		// 贷方科目
																		// 余额为相反数
						}
					}
					if (re.getDc().equals("1")) { // 借方科目
						if (re.getFtype().equals("C")) { // 贷方余额
							getEndbalanceDouble = 0; // 负债类 借方科目 贷方余额为0
						}
						if (re.getFtype().equals("Y")) { // 余额
							getEndbalanceDouble = -getEndbalanceDouble; // 负债类
																		// 借方科目
																		// 余额为相反数
						}
					}

				}
				monthMoney += getEndbalanceDouble;

				ef.setYearStart(String.valueOf(ym));
				ef.setPeriodEnd(String.valueOf(getEndbalanceDouble));
			} else {
				ef.setYearStart("0");
				ef.setPeriodEnd("0");
			}
			listEFormula.add(ef); // ef 添加到集合中
		}

		listEFormula.add(this.total(String.valueOf(yearMoney),
				String.valueOf(monthMoney)));

		// model.addAttribute("tAcctreportitem", tAcctreportitem);
		// model.addAttribute("accountPeriod",accountPeriod);
		// return"modules/rpt/vBalanceFormulaList";
		return listEFormula;

	}

	/**
	 * 利润表公式
	 * 
	 * @param reportformula
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("rpt:tReportitem:view")
	@ResponseBody
	@RequestMapping(value = { "listProfit", "" })
	public List<EFormula> listProfit(TAcctreportitem tAcctreportitem,
			Accountbalanceperiod accountbalanceperiod,
			Reportformula reportformula, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();
		// //当前账期
		String accountPeriod = accountbalanceperiod.getAccountPeriod(); // 当前账期
		String periodEnd = accountbalanceperiod.getPeriodEnd();
		List<EFormula> listEFormula = new ArrayList<EFormula>();
		double yearMoney = 0;
		double monthMoney = 0;
		// 报表id
		String reportitem = "";
		if (tReportitemService.get(reportformula.getId()) == null) {
			reportitem = null;
		} else {
			reportitem = tReportitemService.get(reportformula.getId())
					.getReportitem();
		}
		tAcctreportitem.setReportitem(reportitem);
		List<Reportformula> listReportformula = reportformulaService
				.findList(reportformula);
		for (Reportformula re : listReportformula) {
			EFormula ef = new EFormula();
			ef.setAccountperiod(accountPeriod); // 账期
			ef.setReportitemid(reportformula.getId()); // 报表id
			ef.setReportAccountId(re.getReportAccountId());// 报表中科目编号
			ef.setReportitem(re.getReportitem()); // 报表编码
			ef.setAcctReportItemId(re.getAcctReportItemId()); // 公式id
			ef.setAccid(re.getAccId());
			ef.setAccountName(re.getAccountName());
			ef.setAccuntid(re.getAccuntId());
			ef.setOp(re.getOp());
			// 根据科目id和账期在初始余额表和科目表的联合视图中 查询只有一个值
			List<Accountbalanceperiod> listAccountbalanceperiod = accountbalanceperiodService
					.findAccBlanceByPeriodEnd(re.getAccId(), accountPeriod,
							periodEnd, companyId);
			Accountbalanceperiod acc = findPeriodBalanceHandle(listAccountbalanceperiod);
			if (acc != null) {
				if (acc.getAccDc().equals("1")) { // 借方科目

					String getAmountfor = acc.getAmountfor() == null
							|| acc.getAmountfor().equals("") ? // 获取本月金额（实际损益发生额）
					"0"
							: acc.getAmountfor();
					double getAmountforDouble = Double.valueOf(getAmountfor); // 转为double计算
					monthMoney += getAmountforDouble; // 计算合计金额 月

					String getYtdamountfor = acc.getYtdamountfor() == null
							|| acc.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额）
					"0"
							: acc.getYtdamountfor();
					double getYtdamountforDouble = Double
							.valueOf(getYtdamountfor); // 转为double用于计算
					yearMoney += getYtdamountforDouble; // 计算合计金额 年

					ef.setYearStart(getYtdamountfor); // 本年金额
					ef.setPeriodEnd(getAmountfor); // 本期金额
				}
				if (acc.getAccDc().equals("-1")) { // 贷方科目
					String getAmountfor = acc.getAmountfor() == null
							|| acc.getAmountfor().equals("") ? // 获取本月金额（实际损益发生额）
					"0"
							: acc.getAmountfor();
					double getAmountforDouble = 0 - Double
							.valueOf(getAmountfor); // 转为double计算
					monthMoney += getAmountforDouble; // 计算合计金额 月

					String getYtdamountfor = acc.getYtdamountfor() == null
							|| acc.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额）
					"0"
							: acc.getYtdamountfor();
					double getYtdamountforDouble = 0 - Double
							.valueOf(getYtdamountfor); // 转为double计算
					yearMoney += getYtdamountforDouble; // 计算合计金额 年

					ef.setYearStart(String.valueOf(getYtdamountforDouble)); // 本年金额
					ef.setPeriodEnd(String.valueOf(getAmountforDouble)); // 本期金额
				}

			} else {

				ef.setYearStart("0");
				ef.setPeriodEnd("0");
			}
			listEFormula.add(ef);
		}

		listEFormula.add(this.total(String.valueOf(yearMoney),
				String.valueOf(monthMoney)));
		return listEFormula;

	}

	/**
	 * 处理同科目不同期的 利润表的 便于多期查询
	 * 
	 * @param listReportbalance
	 * @return
	 */
	public Accountbalanceperiod findPeriodBalanceHandle(
			List<Accountbalanceperiod> listAccountbalanceperiod) {

		Accountbalanceperiod rep = new Accountbalanceperiod();
		double getAmountforDouble = 0; // 本月合计（实际损益发生额）
		String getYtdamountforString = ""; // 本年合计（实际损益发生额）
		int accountPeriodMax = 0;
		for (Accountbalanceperiod r : listAccountbalanceperiod) {
			String getAmountfor = r.getAmountfor() == null
					|| r.getAmountfor().equals("") ? // 获取本月金额（实际损益发生额）
			"0"
					: r.getAmountfor();
			getAmountforDouble += Double.valueOf(getAmountfor); // 转为double计算
			int accountPeriodR = Integer.valueOf(r.getAccountPeriod());

			if (accountPeriodMax < accountPeriodR) { // 获取最大账期的年累计金额
				accountPeriodMax = accountPeriodR;
				String getYtdamountfor = r.getYtdamountfor() == null
						|| r.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额）
				"0"
						: r.getYtdamountfor();
				getYtdamountforString = getYtdamountfor; // 转为double用于计算
			}
			rep = r;
		}
		rep.setYtdamountfor(String.valueOf(getYtdamountforString)); // 本年累计
		rep.setAmountfor(String.valueOf(getAmountforDouble)); // 本月合计
		if (listAccountbalanceperiod.size() == 0) { // 如果没有值 返回null
			rep = null;
		}
		return rep;

	}

	/**
	 * 现金流量附加公式表
	 * 
	 * @param eBalance
	 * @param tReportitem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("rpt:tReportitem:view")
	@ResponseBody
	@RequestMapping(value = { "listCfadditional", "" })
	public List<EFormula> listCfadditional(TAcctreportitem tAcctreportitem,
			Accountbalanceperiod accountbalanceperiod,
			Reportformula reportformula, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();
		// //当前账期
		String periodtype = accountbalanceperiod.getPeriodtype(); // 周期类型 月报1
																	// 或者季报2
		if (accountbalanceperiod.getAccountPeriod() == null
				|| accountbalanceperiod.getAccountPeriod().equals("")) {
			accountbalanceperiod.setAccountPeriod(defaultPeriod);
			accountbalanceperiod.setPeriodEnd(defaultPeriod);
		}
		this.listAccount(model, companyId); // 所有的科目
		String accountPeriod = accountbalanceperiod.getAccountPeriod(); // 当前账期
		String periodEnd = accountbalanceperiod.getAccountPeriod(); // 账期后
		if (periodtype.equals("2")) { // 季报
			String[] periodInfo = getPeriodTypeTrans(accountPeriod); // 根据当前季度获取从第几个月到第几个月
			accountPeriod = periodInfo[0]; // 账期前
			periodEnd = periodInfo[1]; // 账期后
		}
		List<EFormula> listEFormula = new ArrayList<EFormula>(); // 定义list集合用于显示于页面
		double yearMoney = 0; // 年累计 合计
		double monthMoney = 0; // 月累计 合计
		tAcctreportitem.setReportitem(reportformula.getReportitem());
		// 报表id
		String reportitem = "";
		if (tReportitemService.get(reportformula.getId()) == null) {
			reportitem = null;
		} else {
			reportitem = tReportitemService.get(reportformula.getId())
					.getReportitem();
		}
		tAcctreportitem.setReportitem(reportitem);
		List<Reportformula> listReportForMula = reportformulaService
				.findList(reportformula);
		for (Reportformula re : listReportForMula) {
			EFormula ef = new EFormula();
			ef.setAccountperiod(accountPeriod); // 账期
			ef.setReportitemid(reportformula.getId()); // 报表id
			ef.setReportAccountId(re.getReportAccountId()); // 报表中科目编号
			ef.setReportitem(re.getReportitem()); // 报表编码
			ef.setAcctReportItemId(re.getAcctReportItemId()); // 公式id
			ef.setAccid(re.getAccId()); // 科目的id
			ef.setAccountName(re.getAccountName()); // 科目名称
			ef.setAccuntid(re.getAccuntId()); // 科目编号
			if (re.getFtype().equals("SL")) {
				ef.setFtype("损益发生额");
			}
			if (re.getFtype().equals("CF")) {
				ef.setFtype("贷方发生额");
			}
			if (re.getFtype().equals("DF")) {
				ef.setFtype("借方发生额");
			}

			ef.setOp(re.getOp());
			// 根据科目id和账期在初始余额表和科目表的联合视图中 查询只有一个值
			List<Accountbalanceperiod> listAccountbalanceperiod = accountbalanceperiodService
					.findAccBlanceByPeriodEnd(re.getAccId(), accountPeriod,
							periodEnd, companyId);
			Accountbalanceperiod acc = findCfadditionalBalanceHandle(listAccountbalanceperiod);
			if (acc != null) {
				if (re.getFtype().equals("DF")) { // 借方发生额
					String getDebitTotal = acc.getDebittotalamount() == null
							|| acc.getDebittotalamount().equals("") ? "0" : acc
							.getDebittotalamount();
					double getDebitTotalDouble = Double.valueOf(getDebitTotal); // 期
																				// 借
					ef.setPeriodEnd(getDebitTotal); // 期

					String getYtdDebitTotal = acc.getYtddebittotalamount() == null
							|| acc.getYtddebittotalamount().equals("") ? "0"
							: acc.getYtddebittotalamount();
					double getYtdDebitTotalDouble = Double
							.valueOf(getYtdDebitTotal); // 年 借
					ef.setYearStart(getYtdDebitTotal); // 年

					if (re.getOp().equals("+")) { // 进行逻辑运算
						monthMoney += getDebitTotalDouble;
						yearMoney += getYtdDebitTotalDouble;
					}
					if (re.getOp().equals("-")) {
						monthMoney -= getDebitTotalDouble;
						yearMoney -= getYtdDebitTotalDouble;
					}
				}
				if (re.getFtype().equals("CF")) { // 贷方发生额
					String getCreditTotal = acc.getCredittotalamount() == null
							|| acc.getCredittotalamount().equals("") ? "0"
							: acc.getCredittotalamount();
					double getCreditTotalDouble = Double
							.valueOf(getCreditTotal); // 期 贷
					ef.setPeriodEnd(getCreditTotal); // 期

					String getYtdCreditTotal = acc.getYtdcredittotalamount() == null
							|| acc.getYtdcredittotalamount().equals("") ? "0"
							: acc.getYtdcredittotalamount();
					double getYtdCreditTotalDouble = Double
							.valueOf(getYtdCreditTotal); // 年 贷
					ef.setYearStart(getYtdCreditTotal); // 年

					if (re.getOp().equals("+")) { // 进行逻辑运算
						monthMoney += getCreditTotalDouble;
						yearMoney += getYtdCreditTotalDouble;
					}
					if (re.getOp().equals("-")) {
						monthMoney -= getCreditTotalDouble;
						yearMoney -= getYtdCreditTotalDouble;
					}
				}
				if (re.getFtype().equals("SL")) { // 损益发生额
					String getAmountfor = acc.getAmountfor() == null
							|| acc.getAmountfor().equals("") ? "0" : acc
							.getAmountfor();
					double getAmountDouble = Double.valueOf(getAmountfor);
					ef.setPeriodEnd(getAmountfor); // 期

					String getYtdAmountfor = acc.getYtdamountfor() == null
							|| acc.getYtdamountfor().equals("") ? "0" : acc
							.getYtdamountfor();
					double getYtdAmountDouble = Double.valueOf(getYtdAmountfor);
					ef.setYearStart(getYtdAmountfor); // 年

					if (re.getOp().equals("+")) { // 进行逻辑运算
						monthMoney += getAmountDouble;
						yearMoney += getYtdAmountDouble;
					}
					if (re.getOp().equals("-")) {
						monthMoney -= getAmountDouble;
						yearMoney -= getYtdAmountDouble;
					}
				}

			} else {

				ef.setYearStart("0");
				ef.setPeriodEnd("0");
			}
			listEFormula.add(ef); // ef 添加到集合中
		}

		listEFormula.add(this.total(String.valueOf(yearMoney),
				String.valueOf(monthMoney)));
		model.addAttribute("listEFormula", listEFormula);
		model.addAttribute("tAcctreportitem", tAcctreportitem);
		model.addAttribute("accountPeriod", accountPeriod);
		return listEFormula;
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
	 * 处理同科目不同期的 利润表的 便于多期查询
	 * 
	 * @param listReportbalance
	 * @return
	 */
	public Accountbalanceperiod findCfadditionalBalanceHandle(
			List<Accountbalanceperiod> listAccountbalanceperiod) {

		Accountbalanceperiod rep = new Accountbalanceperiod();
		double getDebitTotalDouble = 0; // 本月 借方余额
		double getCreditTotalDouble = 0; // 本月 贷方余额
		double getAmountforDouble = 0; // 本月 实际损益发生额

		String getYtdDebitTotalString = ""; // 本年 借方余额
		String getYtdCreditTotalString = ""; // 本年 贷方余额
		String getYtdamountforString = ""; // 本年 实际损益发生额
		int accountPeriodMax = 0;
		for (Accountbalanceperiod r : listAccountbalanceperiod) {

			int accountPeriodR = Integer.valueOf(r.getAccountPeriod()); // 保存账期

			String getDebitTotal = r.getDebittotalamount() == null
					|| r.getDebittotalamount().equals("") ? // 获取本月金额（借方余额）
			"0"
					: r.getDebittotalamount();
			getDebitTotalDouble += Double.valueOf(getDebitTotal);

			String getCreditTotal = r.getCredittotalamount() == null
					|| r.getCredittotalamount().equals("") ? // 获取本月金额（贷方余额）
			"0"
					: r.getCredittotalamount();
			getCreditTotalDouble += Double.valueOf(getCreditTotal);

			String getAmountfor = r.getAmountfor() == null
					|| r.getAmountfor().equals("") ? // 获取本月金额（实际损益发生额）
			"0"
					: r.getAmountfor();
			getAmountforDouble += Double.valueOf(getAmountfor); // 转为double计算

			if (accountPeriodMax < accountPeriodR) { // 获取最大账期的年累计金额
				accountPeriodMax = accountPeriodR;
				String getYtdDebitTotal = r.getYtddebittotalamount() == null
						|| r.getYtddebittotalamount().equals("") ? // 本年累计金额（借方余额）
				"0"
						: r.getYtddebittotalamount();
				getYtdDebitTotalString = getYtdDebitTotal; // 转为double用于计算

				String getYtdCreditTotal = r.getYtdcredittotalamount() == null
						|| r.getYtdcredittotalamount().equals("") ? // 本年累计金额（贷方余额）
				"0"
						: r.getYtdcredittotalamount();
				getYtdCreditTotalString = getYtdCreditTotal; // 转为double用于计算

				String getYtdamountfor = r.getYtdamountfor() == null
						|| r.getYtdamountfor().equals("") ? // 本年累计金额（实际损益发生额）
				"0"
						: r.getYtdamountfor();
				getYtdamountforString = getYtdamountfor; // 转为double用于计算
			}
			rep = r;
		}
		rep.setDebittotalamount(String.valueOf(getDebitTotalDouble)); // 本月借方金额
		rep.setCredittotalamount(String.valueOf(getCreditTotalDouble)); // 本月贷方金额
		rep.setAmountfor(String.valueOf(getAmountforDouble)); // 本月合计
		rep.setYtddebittotalamount(getYtdDebitTotalString); // 本年累计（借方金额）
		rep.setYtdcredittotalamount(getYtdCreditTotalString); // 本年累计（贷方金额）
		rep.setYtdamountfor(getYtdamountforString); // 本年累计（实际损益发生额）

		if (listAccountbalanceperiod.size() == 0) { // 如果没有值 返回null
			rep = null;
		}
		return rep;

	}

	/**
	 * 合计计算
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public EFormula total(String year, String month) {
		EFormula efT = new EFormula();
		efT.setAccountName("合计");
		efT.setYearStart(year);
		efT.setPeriodEnd(month);
		return efT;
	}

	/**
	 * 保存资产负债报表公式
	 * 
	 * @param tAcctreportitem
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "saveBalance" })
	public String saveBalance(TAcctreportitem tAcctreportitem,
			HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		tAcctreportitem.setFdbid(companyId); // 赋值当前用户
		tAcctreportitem.setReportId("100008"); // 资产负债表id编号
		tAcctreportitem.setAcctyearperiod("1"); // 保存年报表1

		tAcctreportitemService.savaAcctreporttitem(tAcctreportitem);

		tAcctreportitem.setAcctyearperiod("2"); // 保存年报表2
		tAcctreportitemService.savaAcctreporttitem(tAcctreportitem);
		String id = tAcctreportitem.getId();
		return id;

	}

	/**
	 * 保存利润表报表公式
	 * 
	 * @param tAcctreportitem
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "saveProfit" })
	public String saveProfit(TAcctreportitem tAcctreportitem,
			HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		tAcctreportitem.setFdbid(companyId);
		tAcctreportitem.setReportId("100004");
		tAcctreportitem.setOp("+");
		tAcctreportitem.setFtype("SL");

		tAcctreportitem.setAcctyearperiod("1");
		tAcctreportitemService.savaAcctreporttitem(tAcctreportitem);

		tAcctreportitem.setAcctyearperiod("2");
		tAcctreportitemService.savaAcctreporttitem(tAcctreportitem);
		String id = tAcctreportitem.getId();
		return id;
	}

	/**
	 * 保存流量附加表报表公式
	 * 
	 * @param tAcctreportitem
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "saveCfadditional" })
	public String saveCfadditional(TAcctreportitem tAcctreportitem,
			HttpSession session, Model model) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		tAcctreportitem.setFdbid(companyId);
		tAcctreportitem.setReportId("100016");

		tAcctreportitem.setAcctyearperiod("1");
		tAcctreportitemService.savaAcctreporttitem(tAcctreportitem);

		tAcctreportitem.setAcctyearperiod("2");
		tAcctreportitemService.savaAcctreporttitem(tAcctreportitem);

		String id = tAcctreportitem.getId();
		return id;
	}

	/**
	 * 资产负债表中删除公式
	 * 
	 * @param reportformula
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteBalance")
	public String deleteBalance(TAcctreportitem tAcctreportitem) {
		tAcctreportitemService.deleteAcctreporttitemById(tAcctreportitem);
		return "1";
	}

	/**
	 * 利润表中删除公式
	 * 
	 * @param reportformula
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteProfit")
	public String deleteProfit(TAcctreportitem tAcctreportitem) {
		tAcctreportitemService.deleteAcctreporttitemById(tAcctreportitem);
		String accountPeriod = tAcctreportitem.getAccountPeriod();
		String periodEnd = tAcctreportitem.getPeriodEnd();
		String id = tAcctreportitem.getReportitemId();
		return "1";
	}

	/**
	 * 现金流量附加删除公式
	 * 
	 * @param reportformula
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteCfadditional")
	public String deleteCfadditional(TAcctreportitem tAcctreportitem) {
		tAcctreportitemService.deleteAcctreporttitemById(tAcctreportitem);
		return "1";
	}

	/**
	 * 获取公司客户信息
	 */
	public TCustomer getCompanyInfo(Model model, HttpSession session) {
		Object obj = session.getAttribute("sessionCustomer");
		TCustomer companyInfo = (TCustomer) obj; // 客户公司信息对象
		String companyName = "小米科技"; // companyInfo.getCustomerName();
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
		List<TAccount> listAccounts = tAccountService
				.findAllAccountByFdbid(companyId);
		model.addAttribute("listAcc", listAccounts);
		if (listAccounts.size() > 0) { // 判断有没有科目
			return listAccounts.get(0).getId();
		} else { // 如果没有科目
			return null;
		}
	}
}