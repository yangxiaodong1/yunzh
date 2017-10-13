package com.thinkgem.jeesite.modules.weChat.web;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.itextpdf.tool.xml.html.Break;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.rpt.entity.Reportbalance;
import com.thinkgem.jeesite.modules.rpt.entity.TReportitem;
import com.thinkgem.jeesite.modules.rpt.service.ReportbalanceService;
import com.thinkgem.jeesite.modules.rpt.service.TReportitemService;
import com.thinkgem.jeesite.modules.taxbaseformula.entity.VTaxbaseformula;
import com.thinkgem.jeesite.modules.taxbaseformula.service.VTaxbaseformulaService;
import com.thinkgem.jeesite.modules.voucher.entity.TVoucher;
import com.thinkgem.jeesite.modules.voucher.service.TVoucherService;
import com.thinkgem.jeesite.modules.weChat.entity.WTableInfo;
import com.thinkgem.jeesite.modules.workterrace.entity.TCustomerSalary;
import com.thinkgem.jeesite.modules.workterrace.service.TCustomerSalaryService;

@Controller
@RequestMapping(value = "/weChat/WTableInfo")
public class WTableInfoController extends BaseController {

	
	@Autowired
	private ReportbalanceService reportbalanceService;
	@Autowired
	private TReportitemService tReportitemService;
	@Autowired
	private TAccountService tAccountService;
	@Autowired
	private TBalanceService tBalanceService;
	@Autowired
	private TVoucherService tVoucherService;
	@Autowired
	private VTaxbaseformulaService vTaxbaseformulaService;
	@Autowired
	private TCustomerSalaryService tCustomerSalaryService;

	/**
	 * 资金利润
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "listRpt")
	@ResponseBody
	public Object listRpt(WTableInfo wTableInfo, Model model, HttpSession session) {
		Map<String, Object> map = Maps.newHashMap();
		Object obj = session.getAttribute("sessionWeChatCustomer");
		if (obj == null) {//session 不存在
			map.put("loginstate", false);
			return map;
		}
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();
		String accountPeriod = wTableInfo.getAccountPeriod();
		if (accountPeriod == null || accountPeriod == "") {
			accountPeriod = defaultPeriod;
		}
		// this.getAllAccountPeriod(model, companyId, defaultPeriod);
		// =============================资产负债=======================
		List<String> listItem = Lists.newArrayList();
		listItem.add("01*001*01"); // 货币资金
		listItem.add("01*001*12"); // 库存商品
		// ============应收===========
		listItem.add("01*001*03"); // 应收票据
		listItem.add("01*001*04"); // 应收账款
		listItem.add("01*001*08"); // 其他应收款
		// ============应付===========
		listItem.add("02*001*02"); // 应付票据
		listItem.add("02*001*03"); // 应付账款
		listItem.add("02*001*09"); // 其他应付款

		// =============================利润=======================
		// ============收入===========
		listItem.add("04*001"); // 营业收入
		// listItem.add("04*022"); // 营业外收入
		// ============成本===========
		listItem.add("04*002"); // 营业成本
		// ============费用===========
		listItem.add("04*011"); // 销售费用
		listItem.add("04*014"); // 管理费用
		listItem.add("04*018"); // 财务费用
		// ============利润===========净利润 = 利润总额 - 所得税费用 = （营业利润 + 营业外收入 - 营业外支出）
		// -所得税费用;
		listItem.add("04*021"); // 营业利润
		listItem.add("04*022"); // 营业外收入
		listItem.add("04*024"); // 营业外支出
		listItem.add("04*031"); // 所得税费用

		List<TReportitem> listTReportitem = tReportitemService.findTReportitemByReItems(companyId, listItem);
		List<Reportbalance> listReports = reportbalanceService.findListByListReItemS(companyId, accountPeriod, listItem);
		Map<String, List<Reportbalance>> mapPeriod = this.getListReportbalancePeriod(listTReportitem, listReports);

		BigDecimal hb = BigDecimal.ZERO; // 货币
		BigDecimal kc = BigDecimal.ZERO; // 库存
		BigDecimal ys = BigDecimal.ZERO; // 应收
		BigDecimal yf = BigDecimal.ZERO; // 应付

		BigDecimal sr = BigDecimal.ZERO; // 收入
		BigDecimal cb = BigDecimal.ZERO; // 成本
		BigDecimal fy = BigDecimal.ZERO; // 费用
		BigDecimal lr = BigDecimal.ZERO; // 利润
		for (TReportitem rep : listTReportitem) {
			String reportitem = rep.getReportitem();
			BigDecimal periodMoney = this.getPeriod(mapPeriod, reportitem);

			if ("01*001*01".equals(reportitem)) { // 货币资金
				hb = hb.add(periodMoney);
			}
			if ("01*001*12".equals(reportitem)) { // 库存商品
				kc = kc.add(periodMoney);
			}
			if ("01*001*03".equals(reportitem) // 应收 // 应收票据
					|| "01*001*04".equals(reportitem) // 应收账款
					|| "01*001*08".equals(reportitem)) { // 其他应收款
				ys = ys.add(periodMoney);
			}
			if ("02*001*02".equals(reportitem) // 应付 // 应付票据
					|| "02*001*03".equals(reportitem) // 应付账款
					|| "02*001*09".equals(reportitem)) { // 其他应付款
				yf = yf.add(periodMoney);
			}
			if ("04*001".equals(reportitem)) { // 收入 //营业收入
				sr = sr.add(periodMoney);
			}
			if ("04*002".equals(reportitem)) { // 成本 //营业成本
				cb = cb.add(periodMoney);
			}
			if ("04*011".equals(reportitem) // 费用 //销售费用
					|| "04*014".equals(reportitem) // 管理费用
					|| "04*018".equals(reportitem)) { // 财务费用
				fy = fy.add(periodMoney);
			}
			if ("04*021".equals(reportitem) // 利润 //营业利润
					|| "04*022".equals(reportitem)) { // 营业外收入
				lr = lr.add(periodMoney);
			}

			if ("04*024".equals(reportitem) // 利润 // 营业外支出
					|| "04*031".equals(reportitem)) { // 所得税费用
				lr = lr.subtract(periodMoney);
			}

			hb = this.zeroToNULL(hb);
			kc = this.zeroToNULL(kc);
			ys = this.zeroToNULL(ys);
			yf = this.zeroToNULL(yf);

			sr = this.zeroToNULL(sr);
			cb = this.zeroToNULL(cb);
			fy = this.zeroToNULL(fy);
			lr = this.zeroToNULL(lr);
		}
		List<WTableInfo> listWTableInfoCapital = Lists.newArrayList();
		listWTableInfoCapital.add(new WTableInfo("货币资金", hb + "", "库存商品", kc + ""));
		listWTableInfoCapital.add(new WTableInfo("应收", ys + "", "应付", yf + ""));

		List<WTableInfo> listWTableInfoProfit = Lists.newArrayList();
		listWTableInfoProfit.add(new WTableInfo("收入", sr + "", "成本", cb + ""));
		listWTableInfoProfit.add(new WTableInfo("费用", fy + "", "利润", lr + ""));

		map.put("rpt", listWTableInfoCapital);
		map.put("Profit", listWTableInfoProfit);
		map.put("pageName", "capitalProfitList");
		map.put("loginstate", true);
		return map;
	}

	/**
	 * 税费
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "listTaxation")
	@ResponseBody
	public Object listTaxation(WTableInfo wTableInfo, Model model, HttpSession session) {
		Map<String, Object> map = Maps.newHashMap();
		Object obj = session.getAttribute("sessionWeChatCustomer");
		if (obj == null) {//session 不存在
			map.put("loginstate", false);
			return map;
		}
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();
		String accountPeriod = wTableInfo.getAccountPeriod();
		if (accountPeriod == null || accountPeriod == "") {
			accountPeriod = defaultPeriod;
		}
		// this.getAllAccountPeriod(model, companyId, defaultPeriod);
		List<TAccount> currentTaxAccounts = tAccountService.findAllListByfdbidLevelTwo(companyId); // 查询启用的二级的税的科目，税种科目在凭证中已记账，税种科目余额值>0，这种情况此税种不适用公式，后面加
		Map<String, List<VTaxbaseformula>> mapVTaxBaseFormula = this.getTaxBaseInfo(companyId, currentTaxAccounts); // 获取所有需要计算税金的科目的公式包含的基数科目

		List<WTableInfo> listWTableInfoTaxation = Lists.newArrayList();
		Map<String, BigDecimal> mapBigDecimal = Maps.newHashMap(); // 保存税金
		BigDecimal totalCurrentTaxVal = BigDecimal.ZERO;
		for (TAccount ta : currentTaxAccounts) {
			BigDecimal currentTaxVal = BigDecimal.ZERO;
			BigDecimal mainOperatingIncomeVal = this.getmainOperatingIncomeVal(companyId, accountPeriod, mapVTaxBaseFormula.get(ta.getAccuntId()), mapBigDecimal);
			currentTaxVal = mainOperatingIncomeVal.multiply(new BigDecimal(ta.getRate()));
			totalCurrentTaxVal = totalCurrentTaxVal.add(currentTaxVal); // 保存合计
			mapBigDecimal.put(ta.getAccuntId(), currentTaxVal);
			currentTaxVal = this.zeroToNULL(currentTaxVal);
			listWTableInfoTaxation.add(new WTableInfo(ta.getAccountName(), currentTaxVal.toString()));
		}
		listWTableInfoTaxation.add(new WTableInfo("合计", totalCurrentTaxVal.toString()));
		
		map.put("Taxation", listWTableInfoTaxation);
		map.put("pageName", "payTaxesList");
		map.put("loginstate", true);
		return map;
	}

	/**
	 * 工资社保
	 * 
	 * @param model
	 * @param session
	 * @return
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@RequestMapping(value = "listWageSocialSecurity")
	@ResponseBody
	public Object listWageSocialSecurity(WTableInfo wTableInfo, Model model, HttpSession session) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		Map<String, Object> map = Maps.newHashMap();
		Object obj = session.getAttribute("sessionWeChatCustomer");
		if (obj == null) {//session 不存在
			map.put("loginstate", false);
			return map;
		}
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String companyId = companyInfo.getId(); // 当前公司客户id
		String defaultPeriod = companyInfo.getCurrentperiod();
		String accountPeriod = wTableInfo.getAccountPeriod();
		if (accountPeriod == null || accountPeriod == "") {
			accountPeriod = defaultPeriod;
		}
		// this.getAllAccountPeriod(model, companyId, defaultPeriod);

		List<WTableInfo> listWTableInfoSalary = Lists.newArrayList(); // 工资
		List<WTableInfo> listWTableInfoSocialSecurity = Lists.newArrayList(); // 社保
		BigDecimal yjgz = BigDecimal.ZERO; // 月计工资总额----收入额
		BigDecimal gzsj = BigDecimal.ZERO; // 工资税金----应扣缴税额
		BigDecimal whsgz = BigDecimal.ZERO; // 未含税平均工资----(当月计提工资总额-（工资税金）当月代扣的个人所得税)/当月人数----月代扣的个人
											// 已扣缴税额
		BigDecimal hsgz = BigDecimal.ZERO; // 含税平均工资----月计提工资总额/当月人数
		TCustomerSalary tCustomerSalary = tCustomerSalaryService.findSalaryOperation(companyId, accountPeriod);
		if (tCustomerSalary != null) {
			this.nullToZeroReflect(tCustomerSalary);
			yjgz = this.zeroToNULL(new BigDecimal(tCustomerSalary.getIncomeMoney()));
			gzsj = this.zeroToNULL(new BigDecimal(tCustomerSalary.getShouldBucklePay()));
			whsgz = this.zeroToNULL(new BigDecimal(tCustomerSalary.getHasBeenWithholdingTax()));
			hsgz = this.zeroToNULL(new BigDecimal(tCustomerSalary.getShouldPayIncome()));
		}

		BigDecimal yjsbTotal = BigDecimal.ZERO; // 月社保及公积金总额----当月计提的单位承担的五险一金总额
		BigDecimal gsjnTotal = BigDecimal.ZERO; // 公司缴纳总额
		
		BigDecimal sbjnProportion = BigDecimal.ZERO; // 社保缴纳比例----社保缴纳比例由会计根据当地规定自己填写，默认可以设置为单位承担的五险的比例之和
		sbjnProportion = new BigDecimal("12");
		BigDecimal gjjjnProportion = BigDecimal.ZERO; // 公积金缴纳比例
		gjjjnProportion = new BigDecimal("12");
		listWTableInfoSalary.add(new WTableInfo("月计工资总额", yjgz.toString(), "工资税金", gzsj.toString()));
		listWTableInfoSalary.add(new WTableInfo("未含税平均工资", whsgz.toString(), "含税平均工资", hsgz.toString()));

		listWTableInfoSocialSecurity.add(new WTableInfo("月社保及公积金总额", yjsbTotal.toString(), "公司缴纳总额", gsjnTotal.toString()));
		listWTableInfoSocialSecurity.add(new WTableInfo("社保缴纳比例", sbjnProportion.toString() + "%", "公积金缴纳比例", gjjjnProportion.toString() + "%"));
		map.put("Salary", listWTableInfoSalary);
		map.put("SocialSecurity", listWTableInfoSocialSecurity);
		map.put("pageName", "salarySocialSecurityList");
		map.put("loginstate", true);
		return map;
	}

	/**
	 * 获取公司客户信息
	 */
	public TCustomer getCompanyInfo(Model model, HttpSession session) {
		Object obj = session.getAttribute("sessionWeChatCustomer");
		TCustomer companyInfo = (TCustomer) obj; // 客户公司信息对象
		String companyName = companyInfo.getCustomerName();
		model.addAttribute("companyName", companyName);
		return companyInfo;

	}

	/**
	 * 获取所有的账期
	 * 
	 * @param model
	 * @param companyId
	 */
	public void getAllAccountPeriod(Model model, HttpSession session) {
		TCustomer companyInfo = this.getCompanyInfo(model, session); // 获取公司对象
		String defaultPeriod = companyInfo.getCurrentperiod();
		String initperiod = companyInfo.getInitperiod();
		if (defaultPeriod == null || defaultPeriod == "") {
			defaultPeriod = initperiod;
		}
		session.setAttribute("initperiod", initperiod);
		session.setAttribute("defaultPeriod", defaultPeriod);
//		model.addAttribute("initperiod", initperiod);
//		model.addAttribute("defaultPeriod", defaultPeriod);

	}

	// ===================================资产负债所需要的方法
	// begin==============================
	/**
	 * 获取同期同客户的所有的公式
	 * 
	 * @param accountPeriod
	 * @param periodEnd
	 * @param fdbid
	 * @return
	 */
	public Map<String, List<Reportbalance>> getListReportbalancePeriod(List<TReportitem> listTReportitem, List<Reportbalance> listReportbalance) {
		Map<String, List<Reportbalance>> mapPeriod = Maps.newHashMap();
		List<Reportbalance> listRep = Lists.newArrayList();
		for (TReportitem rep : listTReportitem) {
			for (Reportbalance re : listReportbalance) {
				if (re.getReportitem().equals(rep.getReportitem())) {
					listRep.add(re);
				}
			}
			mapPeriod.put(rep.getReportitem(), listRep);
			listRep = Lists.newArrayList();
		}

		return mapPeriod;

	}

	/**
	 * 期末余额
	 * 
	 * @param reportItem
	 * @param accountPeriod
	 * @return
	 */
	public BigDecimal getPeriod(Map<String, List<Reportbalance>> mapPeriod, String reportItem) {
		BigDecimal money = BigDecimal.ZERO;
		List<Reportbalance> listReportbalance = mapPeriod.get(reportItem);
		String subReportItem = reportItem.substring(0, 2); // 截取报表编号判断是资产类还是负债类和所有者权益
		String endBalance = "endBalance";
		if (subReportItem.equals("01")) { // 资产类
			money = this.getAsMoney(listReportbalance, endBalance);
		}
		if (subReportItem.equals("02") // 负债类
				|| subReportItem.equals("03")) { // 所有者权益
			money = this.getLiMoney(listReportbalance, endBalance);
		}
		if (subReportItem.equals("04")) {// 负债类
			money = this.findPeriodBalance(mapPeriod, reportItem)[0];
		}
		return money;

	}

	/**
	 * 根据取数规则进行判断取数 资产类和所有者权益
	 */
	private BigDecimal getAsMoney(List<Reportbalance> listReportbalance, String balanceType) {

		/**
		 * 借方金额 （余额保存在汇总表（初始余额表）中为数值） 贷方金额 （余额保存在汇总表（初始余额表）中为相反数）
		 * 
		 * 资产类 借方科目 余额就取值 借方余额取值 贷方余额为0 资产类 贷方科目 余额取值即可 借方余额为0 贷方余额为相反数
		 * 
		 */
		BigDecimal money = BigDecimal.ZERO;
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
	private BigDecimal getLiMoney(List<Reportbalance> listReportbalance, String balanceType) {
		/**
		 * 借方金额 （余额保存在汇总表（初始余额表）中为数值） 贷方金额 （余额保存在汇总表（初始余额表）中为相反数）
		 * 
		 * 负债类 借方科目 余额为相反数 借方余额取值 贷方余额为0 负债类 贷方科目 余额为相反数 借方余额为0 贷方余额为相反数
		 */
		BigDecimal money = BigDecimal.ZERO;
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
	 * 利润表 本年累计 和本月合计
	 * 
	 * @param accountPeriod
	 * @param reportitem
	 * @return
	 */
	public BigDecimal[] findPeriodBalance(Map<String, List<Reportbalance>> mapPeriod, String reportItem) {
		BigDecimal doup = BigDecimal.ZERO; // 本月合计
		BigDecimal douy = BigDecimal.ZERO; // 本年合计
		List<Reportbalance> listReportbalance = mapPeriod.get(reportItem);
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

	// ===================================资产负债所需要的方法
	// end==============================

	// ====================================缴税begin===========================================
	/**
	 * 获取所有税金科目的基数
	 * 
	 * @return
	 */
	private Map<String, List<VTaxbaseformula>> getTaxBaseInfo(String fdbid, List<TAccount> listTAccount) {
		String accountid = "2221%";
		List<VTaxbaseformula> listVTaxbaseformulas = vTaxbaseformulaService.findTTaxbaseFormulaByfdbIdAndTaxCategory(fdbid, accountid);
		Map<String, List<VTaxbaseformula>> mapTaxBase = Maps.newHashMap();

		for (TAccount tAccount : listTAccount) {
			if ("2".equals(tAccount.getLevel())) {
				List<VTaxbaseformula> addMapList = Lists.newArrayList();
				for (VTaxbaseformula vt : listVTaxbaseformulas) {
					if (tAccount.getAccuntId().equals(vt.getTaxcategory())) {
						addMapList.add(vt);
					}
				}
				mapTaxBase.put(tAccount.getAccuntId(), addMapList);
			}
		}

		return mapTaxBase;
	}

	/**
	 * 根据编号取余额
	 * 
	 * @param listTaxbase
	 * @return
	 */
	private BigDecimal getmainOperatingIncomeVal(String fdbid, String accountPeriod, List<VTaxbaseformula> listVTaxbaseformulas, Map<String, BigDecimal> mapBigDecimal) {
		List<String> accountIds = Lists.newArrayList();
		for (VTaxbaseformula vt : listVTaxbaseformulas) {
			accountIds.add(vt.getAccId());
		}
		List<TBalance> listTBalances = tBalanceService.findListByAccountId(fdbid, accountPeriod, accountIds);
		Map<String, TBalance> maptBalance = Maps.newHashMap();
		if (accountIds.size() > 0) {
			for (TBalance tBalance : listTBalances) {
				maptBalance.put(tBalance.getAccountId(), tBalance);
			}
		}
		return this.getBaseNumber(listVTaxbaseformulas, maptBalance, mapBigDecimal);
	}

	/**
	 * 根据公式计算
	 * 
	 * @param listVTaxbaseformulas
	 * @param maptBalance
	 * @return
	 */
	private BigDecimal getBaseNumber(List<VTaxbaseformula> listVTaxbaseformulas, Map<String, TBalance> maptBalance, Map<String, BigDecimal> mapBigDecimal) {
		BigDecimal comeVal = BigDecimal.ZERO;
		for (VTaxbaseformula vt : listVTaxbaseformulas) {
			TBalance tb = maptBalance.get(vt.getAccId());
			BigDecimal sl = BigDecimal.ZERO;
			double debitDouble = 0;
			double creditDouble = 0;
			if (null != tb) {
				String getDebit = tb.getDebittotalamount() == null || tb.getDebittotalamount() == "" ? "0" : tb.getDebittotalamount();
				debitDouble = Double.valueOf(getDebit);

				String getCredit = tb.getCredittotalamount() == null || tb.getCredittotalamount() == "" ? "0" : tb.getCredittotalamount();
				creditDouble = Double.valueOf(getCredit);
			}

			// 城市维护建设税、教育费附加、地方教育费附加计税基数为其他增值税的和
			// if ("222108".equals(vt.getAccountid()) ||
			// "222108".equals(vt.getAccountid())||"222114".equals(vt.getAccountid()))
			// {
			if (mapBigDecimal.containsKey(vt.getAccountid())) {
				sl = mapBigDecimal.get(vt.getAccountid());

			} else {
				if ("1".equals(vt.getAccDc())) { // 借 借方金额 - 贷方金额
					sl = BigDecimal.valueOf(debitDouble).subtract(BigDecimal.valueOf(creditDouble));
				}
				if ("-1".equals(vt.getAccDc())) { // 贷 贷方金额 - 借方金额
					sl = BigDecimal.valueOf(creditDouble).subtract(BigDecimal.valueOf(debitDouble));
				}
			}

			if ("+".equals(vt.getOp())) {
				comeVal = comeVal.add(sl);
			}
			if ("-".equals(vt.getOp())) {
				comeVal = comeVal.subtract(sl);
			}
		}
		return comeVal;
	}

	// ====================================缴税end===========================================

	/**
	 * 空值转换为0 防止转换错误
	 * 
	 * @param model
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	public static void nullToZeroReflect(Object model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		Field[] fields = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		// 写数据
		for (Field ff : fields) {
			String name = ff.getName(); // 获取属性的名字
			if (!name.equals("serialVersionUID")) {
				String type = ff.getGenericType().toString(); // 获取属性的类型
				ff.setAccessible(true);
				PropertyDescriptor pd = new PropertyDescriptor(ff.getName(), model.getClass());
				Method rM = pd.getReadMethod();// 获得读方法
				Object num = (Object) rM.invoke(model);
				if (num == null) {
					Method wM = pd.getWriteMethod();// 获得写方法
					if (type.equals("class java.lang.String")) {
						wM.invoke(model, "0");// 因为知道是int类型的属性，所以传个int过去就是了。。实际情况中需要判断下他的参数类型
					}
				}
			}

		}
	}

	/**
	 * 如果为0 则转为 null
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public BigDecimal zeroToNULL(BigDecimal bigDecimal) {
		if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO.setScale(2,BigDecimal.ROUND_HALF_EVEN);
		} else {
			return bigDecimal;
		}
	}
}
