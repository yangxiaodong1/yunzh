/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

import org.activiti.engine.impl.util.json.JSONArray;
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
import com.mysql.fabric.xmlrpc.base.Data;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.acctreportitem.entity.TAcctreportitem;
import com.thinkgem.jeesite.modules.cashflow.entity.TCashflow;
import com.thinkgem.jeesite.modules.cashflow.service.TCashflowService;
import com.thinkgem.jeesite.modules.cashflow.web.TCashflowController;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.rpt.entity.EBalance;
import com.thinkgem.jeesite.modules.rpt.entity.ECashFlow;
import com.thinkgem.jeesite.modules.rpt.entity.Reportbalance;
import com.thinkgem.jeesite.modules.rpt.entity.TReportitem;
import com.thinkgem.jeesite.modules.rpt.service.ReportbalanceService;
import com.thinkgem.jeesite.modules.rpt.service.TReportitemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 现金流量表附加表Controller
 * 
 * @author zhangtong
 * @version 2015-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rpt/cfadditional")
public class CfadditionalController extends BaseController {

	@Autowired
	private TReportitemService tReportitemService;
	@Autowired
	private TCashflowService tCashflowService;
	@Autowired
	private TAccountService tAccountService;
	@Autowired
	private ReportbalanceService reportbalanceService;
	public static NumberFormat moneyFormat = new DecimalFormat("#,##0.00");
	static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
	
	
	@RequestMapping(value = { "listCfadditional", "" })
	public String listCfadditional(String accountPeriod, String periodEnd, String periodtype,Model model) {
		model.addAttribute("accountPeriod", accountPeriod);
		model.addAttribute("periodEnd", periodEnd);
		model.addAttribute("periodtype", periodtype);
		return "modules/rpt/vCfadditional";
	}
	/**
	 * 现金流量附加表
	 * 
	 * @param eBalance
	 * @param tReportitem
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "list", "" })
	@ResponseBody
	public Object list(String accountPeriod, String periodEnd, String periodtype, Boolean emptyRecal, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		Map<String, Object> map = Maps.newHashMap();
		List<ECashFlow> listECashFlow = Lists.newArrayList(); // 定义一个list集合保存值用于界面显示
		TReportitem tReportitem = new TReportitem();

		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();
		int companySystem = companyInfo.getSystem(); // 1 小企业 2 新规则

		// //当前账期
		if (StringUtils.isNoneEmpty(accountPeriod) && StringUtils.isNoneEmpty(periodEnd) && StringUtils.isNoneEmpty(periodtype)) {
			periodtype = "1"; // 默认的为1 月报
			tReportitem.setAccountPeriod(defaultPeriod);
			tReportitem.setPeriodEnd(tReportitem.getAccountPeriod());
		}
		tReportitem.setFdbid(companyId);
		String reportId = "";
		if (companySystem == 1) {
			reportId = "100016";
		} else if (companySystem == 2) {
			reportId = "100013";
		}
		tReportitem.setReportId(reportId);
		List<TReportitem> listTReportitem = tReportitemService.findList(tReportitem); // 获取现金流量附加表的信息

		Map<String, BigDecimal> mapP = Maps.newHashMap(); // 本月数map
		Map<String, BigDecimal> mapY = Maps.newHashMap(); // 本年数map
		Map<String, String> mapID = Maps.newHashMap(); // 流量表中的id
		Map<String, List<Reportbalance>> mapPeriod = this.getListReportbalancePeriodAndYear(accountPeriod, periodEnd, companyId, listTReportitem,reportId);
		int num = 0; // 计算行次
		String groupId = "7";
		List<TCashflow> listTCashflow = tCashflowService.findListCashflow(groupId, accountPeriod, companyId);
		if (listTCashflow.size() > 0) {
			addMapAddCashFlow(listTCashflow, mapY, mapP, mapID); // 遍历流量表中有数据的信息
			for (TReportitem tr : listTReportitem) {
				ECashFlow ec = new ECashFlow();
				ec.setId(tr.getId()); // 报表id
				ec.setGroupId(tr.getGroupid());
				ec.setReportItem(tr.getReportitem()); // 报表编码
				ec.setProjectName(tr.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;")); // 名称
				if (!tr.getDatasource().equals("0")) { // 根据datasource 判断是不是超链接
					num += 1; // 行次
					ec.setLineNumber(String.valueOf(num));
					if (tr.getDatasource().equals("1")) {
						ec.setAsIsALabel(true); // 是超链接
					}
					if (tr.getDatasource().equals("3")) {
						ec.setAsIsALabel(false); // 不是超链接
					}
				}
				ec.setTcashFowId(mapID.get(tr.getReportitem())); // 保存id 判断是否更新
				if (emptyRecal) { // 是否清空重算
					mapP.clear();
					mapY.clear();
					getFormulaCalculation(mapPeriod, mapP, mapY, tr, ec); // 根据公式计算
				} else {

					getTCashFlowInfo(mapY, mapP, tr, ec);
				}

				// 集合中添加信息
				listECashFlow.add(ec);
			}
		} else {

			for (TReportitem tr : listTReportitem) {
				ECashFlow ec = new ECashFlow();
				ec.setId(tr.getId()); // 报表id
				ec.setGroupId(tr.getGroupid());
				ec.setReportItem(tr.getReportitem()); // 报表编码
				ec.setProjectName(tr.getFdesc().replaceAll("\\|", "").replace(" ", "&nbsp;")); // 名称
				if (!tr.getDatasource().equals("0")) { // 根据datasource 判断是不是超链接
					num += 1; // 行次
					ec.setLineNumber(String.valueOf(num));
					if (tr.getDatasource().equals("1")) {
						ec.setAsIsALabel(true); // 是超链接
					}
					if (tr.getDatasource().equals("3")) {
						ec.setAsIsALabel(false); // 不是超链接
					}
				}
				if (emptyRecal) { // 是否清空重算
					getFormulaCalculation(mapPeriod, mapP, mapY, tr, ec); // 根据公式计算
				} else {
					getFormulaCalculation(mapPeriod, mapP, mapY, tr, ec); // 根据公式计算
				}

				// 集合中添加信息
				listECashFlow.add(ec);
			}
		}

		map.put("listECashFlow", listECashFlow);
		
		return map;
	}

	/**
	 * 读取流量表中 流量的信息
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
	 * 根据公式计算
	 * 
	 * @param companyId
	 * @param accountPeriod
	 * @param periodEnd
	 * @param mapP
	 * @param mapY
	 * @param tr
	 * @param ec
	 */
	private void getFormulaCalculation(Map<String, List<Reportbalance>> mapPeriod, Map<String, BigDecimal> mapP, Map<String, BigDecimal> mapY, TReportitem tr, ECashFlow ec) {
		if (tr.getDatasource().equals("1")) {
			BigDecimal[] moneyDouble = this.getMoney(mapPeriod, tr.getReportitem()); // 获取本月和本年数
			BigDecimal moneyP = moneyDouble[0]; // 本月金额
			BigDecimal moneyY = moneyDouble[1]; // 本年金额
			if (moneyP.compareTo(BigDecimal.ZERO) != 0) {
				ec.setPeriodBalance(moneyFormat.format(moneyP)); // 本月金额
			} else {
				ec.setPeriodBalance(null);
			}
			if (moneyY.compareTo(BigDecimal.ZERO) != 0) {
				ec.setYearBalance(moneyFormat.format(moneyY)); // 本年累计金额
			} else {
				ec.setYearBalance(null);
			}

			mapP.put(tr.getReportitem(), moneyP); // 保存本月数 map 中 用于取值求和
			mapY.put(tr.getReportitem(), moneyY); // 保存本年数 map 中 用于取值求和
		}
		if (tr.getDatasource().equals("3")) {
			BigDecimal[] moneyDouble = totalTotal(tr.getFormula(), mapP, mapY);

			BigDecimal moneyP = moneyDouble[0]; // 本月金额
			BigDecimal moneyY = moneyDouble[1]; // 本年金额
			if (moneyP.compareTo(BigDecimal.ZERO) != 0) {
				ec.setPeriodBalance(moneyFormat.format(moneyP)); // 本月金额
			} else {
				ec.setPeriodBalance(null);
			}
			if (moneyY.compareTo(BigDecimal.ZERO) != 0) {
				ec.setYearBalance(moneyFormat.format(moneyY)); // 本年累计金额
			} else {
				ec.setYearBalance(null);
			}

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
	public Map<String, List<Reportbalance>> getListReportbalancePeriodAndYear(String accountPeriod, String periodEnd, String fdbid, List<TReportitem> listTReportitem,String reportId) {
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
	 * 读取本月数和本年数
	 * 
	 * @param reportItem
	 * @param accountPeriod
	 * @return
	 */
	public BigDecimal[] getMoney(Map<String, List<Reportbalance>> mapPeriod, String reportItem) {
		List<Reportbalance> listReportbalance = mapPeriod.get(reportItem);
		// 根据报表编号读取信息

		BigDecimal periodMoney = BigDecimal.ZERO; // 本期数
		BigDecimal yearMoney = BigDecimal.ZERO; // 本年数

		for (Reportbalance re : listReportbalance) {
			if (re.getFtype().equals("DF")) { // 借方发生额
				String getDebitTotal = re.getDebittotalamount() == null || re.getDebittotalamount().equals("") ? "0" : re.getDebittotalamount();
				BigDecimal getDebitTotalDouble = new BigDecimal(getDebitTotal); // 期
				// 借

				String getYtdDebitTotal = re.getYtddebittotalamount() == null || re.getYtddebittotalamount().equals("") ? "0" : re.getYtddebittotalamount();
				BigDecimal getYtdDebitTotalDouble = new BigDecimal(getYtdDebitTotal); // 年
																						// 借
				if (re.getOp().equals("+")) { // 进行逻辑运算
					periodMoney = periodMoney.add(getDebitTotalDouble);
					yearMoney = yearMoney.add(getYtdDebitTotalDouble);
				}
				if (re.getOp().equals("-")) {
					periodMoney = periodMoney.subtract(getDebitTotalDouble);
					yearMoney = yearMoney.subtract(getYtdDebitTotalDouble);
				}
			}
			if (re.getFtype().equals("CF")) { // 贷方发生额
				String getCreditTotal = re.getCredittotalamount() == null || re.getCredittotalamount().equals("") ? "0" : re.getCredittotalamount();
				BigDecimal getCreditTotalDouble = new BigDecimal(getCreditTotal); // 期
																					// 贷

				String getYtdCreditTotal = re.getYtdcredittotalamount() == null || re.getYtdcredittotalamount().equals("") ? "0" : re.getYtdcredittotalamount();
				BigDecimal getYtdCreditTotalDouble = new BigDecimal(getYtdCreditTotal); // 年
				// 贷
				if (re.getOp().equals("+")) { // 进行逻辑运算
					periodMoney = periodMoney.add(getCreditTotalDouble);
					yearMoney = yearMoney.add(getYtdCreditTotalDouble);
				}
				if (re.getOp().equals("-")) {
					periodMoney = periodMoney.subtract(getCreditTotalDouble);
					yearMoney = yearMoney.subtract(getYtdCreditTotalDouble);
				}
			}
			if (re.getFtype().equals("SL")) { // 损益发生额
				String getAmountfor = re.getAmountfor() == null || re.getAmountfor().equals("") ? "0" : re.getAmountfor();
				BigDecimal getAmountDouble = new BigDecimal(getAmountfor);

				String getYtdAmountfor = re.getYtdamountfor() == null || re.getYtdamountfor().equals("") ? "0" : re.getYtdamountfor();
				BigDecimal getYtdAmountDouble = new BigDecimal(getYtdAmountfor);
				if (re.getOp().equals("+")) { // 进行逻辑运算
					periodMoney = periodMoney.add(getAmountDouble);
					yearMoney = yearMoney.add(getYtdAmountDouble);
				}
				if (re.getOp().equals("-")) {
					periodMoney = periodMoney.subtract(getAmountDouble);
					yearMoney = yearMoney.subtract(getYtdAmountDouble);
				}
			}
		}

		BigDecimal[] money = { periodMoney, yearMoney };

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

	/**
	 * 查询现金流量表中是否有信息 如果有 显示信息
	 * 
	 * @param companyId
	 * @param accountPeriod
	 * @param tr
	 * @param ec
	 */
	private void getTCashFlowInfo(Map<String, BigDecimal> mapY, Map<String, BigDecimal> mapP, TReportitem tr, ECashFlow ec) {
		BigDecimal periodBalance = mapP.get(tr.getReportitem());
		BigDecimal yearBalance = mapY.get(tr.getReportitem());
		if (periodBalance.compareTo(BigDecimal.ZERO) != 0) {
			ec.setPeriodBalance(moneyFormat.format(periodBalance)); // 本月金额
		} else {
			ec.setPeriodBalance(null);
		}
		if (yearBalance.compareTo(BigDecimal.ZERO) != 0) {
			ec.setYearBalance(moneyFormat.format(yearBalance)); // 本年累计金额
		} else {
			ec.setYearBalance(null);
		}
	}

	/**
	 * 现金流量附加表保存
	 * 
	 * @param tCashflow
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "save")
	public String save(TCashflow tCashflow, HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String jsoninfo = new String(request.getParameter("info").getBytes("ISO-8859-1"), "utf-8");
		String accountPeriod = new String(request.getParameter("accountPeriod").getBytes("ISO-8859-1"), "utf-8"); // 当前账期
		String periodtype = new String(request.getParameter("periodtype").getBytes("ISO-8859-1"), "utf-8"); // 保存是否为季度或者月度
		JSONArray jsonArray = new JSONArray(jsoninfo); // 转成json数组
		String groupidString = "";
		Date date = new Date();
		List<TCashflow> listTCashflow = new ArrayList<TCashflow>();
		for (int i = 0; i < jsonArray.length(); i++) {
			TCashflow tc = new TCashflow();
			String id = this.undefinedToNull(jsonArray.getJSONObject(i).getString("tcashFowId")); // 报表编号
			String reportItem = this.undefinedToNull(jsonArray.getJSONObject(i).getString("reportItem")); // 报表编号
			String yearBalance = this.undefinedToNull(jsonArray.getJSONObject(i).getString("yearBalance")); // 年累计金额
			String periodBalance = this.undefinedToNull(jsonArray.getJSONObject(i).getString("periodBalance")); // 本期金额
			String groupId = this.undefinedToNull(jsonArray.getJSONObject(i).getString("groupId")); // 分组编号
			groupidString = groupId;
			reportItem = reportItem == null || reportItem.equals("") ? "0" : reportItem;
			yearBalance = yearBalance == null || yearBalance.equals("") ? "0" : yearBalance;
			periodBalance = periodBalance == null || periodBalance.equals("") ? "0" : periodBalance;
			tc.setId(id);
			tc.setFdbid(companyId); // 当前公司客户id
			tc.setFadddate(date); // 添加保存时候的时间
			tc.setFlowtype("2"); // 流的类型
			tc.setFcur("RMB");
			tc.setPeriodtype(periodtype); // 季度或者月度
			tc.setGroupid(groupId);
			tc.setReportitem(reportItem);
			tc.setYearperiod(accountPeriod);
			tc.setYtdendamount(periodBalance); // 本月金额 末
			tc.setYtdbeginamount("0"); // 本月金额 初
			tc.setLytdendamount(yearBalance); // 本月金额 末
			tc.setLytdbeginamount("0"); // 本年金额 初

			listTCashflow.add(tc);
		}
		if (jsonArray.length() > 0) {
			String id = listTCashflow.get(2).getId();
			if (id != null && !"".equals(id)) { // 如果为空则插入
				System.out.println(listTCashflow);
				tCashflowService.updateListCashflow(listTCashflow); // 执行跟新操作
			} else {
				tCashflowService.savaListCashflow(listTCashflow); // 执行插入操作
			}
		}
		if (groupidString.equals("7")) {
			return "redirect:" + Global.getAdminPath() + "/rpt/cfadditional/listCfadditional?accountPeriod=" + accountPeriod + "&&periodtype=" + periodtype;
		} else {
			return "redirect:" + Global.getAdminPath() + "/rpt/cashFlow/listCashFlow?accountPeriod=" + accountPeriod + "&&periodtype=" + periodtype;
		}

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
		List<TAccount> listAccounts = tAccountService.findAllAccountByFdbid(companyId);
		model.addAttribute("listAcc", listAccounts);
		if (listAccounts.size() > 0) { // 判断有没有科目
			return listAccounts.get(0).getId();
		} else { // 如果没有科目
			return null;
		}
	}
	
	public String undefinedToNull(String str) {
		return str.equals("undefined") ? null : str;
	}

}