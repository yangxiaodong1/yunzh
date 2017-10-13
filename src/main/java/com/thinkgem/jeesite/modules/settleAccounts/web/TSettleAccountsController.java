package com.thinkgem.jeesite.modules.settleAccounts.web;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.account.service.TAccountService;
import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
import com.thinkgem.jeesite.modules.amortize.service.TAmortizeService;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;
import com.thinkgem.jeesite.modules.balance.service.TBalanceService;
import com.thinkgem.jeesite.modules.books.entity.Accountbalance;
import com.thinkgem.jeesite.modules.books.service.AccountbalanceService;
import com.thinkgem.jeesite.modules.customer.entity.TCustomer;
import com.thinkgem.jeesite.modules.customer.service.TCustomerService;
import com.thinkgem.jeesite.modules.settleAccounts.entity.TSettleAccounts;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.taxbase.entity.TTaxbase;
import com.thinkgem.jeesite.modules.taxbase.service.TTaxbaseService;

import com.thinkgem.jeesite.modules.taxbaseformula.entity.VTaxbaseformula;
import com.thinkgem.jeesite.modules.taxbaseformula.service.VTaxbaseformulaService;
import com.thinkgem.jeesite.modules.voucherexp.service.TVoucherExpService;
import com.thinkgem.jeesite.modules.workterrace.entity.TCustomerSalary;
import com.thinkgem.jeesite.modules.workterrace.service.TCustomerSalaryService;

/**
 * 结账Controller
 * 
 * @author
 * @version 2015-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/settleAccounts/settleAccounts")
public class TSettleAccountsController extends BaseController {

	@Autowired
	private TAmortizeService tAmortizeService;

	@Autowired
	private TBalanceService tBalanceService;

	@Autowired
	private TAccountService tAccountService;
	@Autowired
	private TCustomerSalaryService tCustomerSalaryService;
	@Autowired
	private VTaxbaseformulaService vTaxbaseformulaService;
	@Autowired
	private AccountbalanceService accountbalanceService;
	@Autowired
	private TCustomerService customerService;
	@Autowired
	private TVoucherExpService tVoucherExpService;
	private boolean flag;
	
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@RequestMapping(value = "settleAccounts")
	@ResponseBody
	public Object save(String fdbid, String accId, String accountPeriod, Model model, HttpSession session) {
		this.setFlag(false);
		TCustomer customerObject = (TCustomer) session.getAttribute("sessionCustomer");
		fdbid = customerObject.getId(); // 公司客户表
		int companySystem = customerObject.getSystem(); // 1 小企业 2 新规则
		accountPeriod = customerObject.getCurrentperiod(); // 客户的当前账期

		TAccount tAccount = null;

		Map map = new HashMap();

		DecimalFormat df = new DecimalFormat("#0.00");
		////保存损益凭证前的生成凭证中的损益类科目	key  编号
		Map<String,TSettleAccounts> tSettleAccountsMap = Maps.newHashMap();		
		// ======1.税金计提	=============begin====		ok
		//营业税、消费税、增值税、 基数 为科目 “5001--主营业务收入”当期累计值
//		TAccount mainOperatingIncomeAccount = tAccountService.getAccountsInfoByName("主营业务收入", fdbid,""); //主营业务收入 科目
//		//基数
//		BigDecimal mainOperatingIncomeVal = BigDecimal.ZERO;
//		if(null != mainOperatingIncomeAccount){
//			List<TSettleAccounts> mainOperatingIncome = tBalanceService.settleAccounts(mainOperatingIncomeAccount.getId(), fdbid, accountPeriod);// 主营业务收入
//			mainOperatingIncomeVal = addCreditTotal(mainOperatingIncome);
//			//mainOperatingIncomeVal.add(addDebitTotal(mainOperatingIncome));//add todo
//			mainOperatingIncomeVal.subtract(addDebitTotal(mainOperatingIncome));//zt 修改 取值为主营业务收入的本期累计金额
//		}
		// 222103应交税费-应交营业税-本期营业税
				
		List<TAccount> currentTaxAccounts = tAccountService.findAllListByfdbidLevelTwo(fdbid);	//查询启用的二级的税的科目，税种科目在凭证中已记账，税种科目余额值>0，这种情况此税种不适用公式，后面加
		TAccount tAccount2 = tAccountService.getAccountsInfoByName("未交增值税",fdbid,"222102");	//未交增值税
		List<String> accountIDTax = Lists.newArrayList();	//二级税科目编码
		for (TAccount tAccTax : currentTaxAccounts) {
			accountIDTax.add(tAccTax.getAccuntId());
		}
		//根据报表二级科目编码	账期	公司id
		List<Accountbalance> listTAccountBalances = accountbalanceService.findListByAccountId(accountPeriod,accountIDTax,fdbid);
		Map<String, Accountbalance> accBalanceTaxMap = Maps.newHashMap();//保存科目编号 	
		for (Accountbalance accBalance : listTAccountBalances) {
			accBalanceTaxMap.put(accBalance.getId(), accBalance);
		}
		
		Map<String, List<VTaxbaseformula>> mapVTaxBaseFormula = this.getTaxBaseInfo(fdbid,currentTaxAccounts);	//获取所有需要计算税金的科目的公式包含的基数科目
				
		Map<String, BigDecimal> mapBigDecimal = Maps.newHashMap();		//保存税金
		BigDecimal totalCurrentTaxVal = BigDecimal.ZERO;
		for (TAccount ta : currentTaxAccounts) {
				BigDecimal currentTaxVal = BigDecimal.ZERO;
				BigDecimal mainOperatingIncomeVal = this.getmainOperatingIncomeVal(fdbid,accountPeriod,tAccount2,
						mapVTaxBaseFormula.get(ta.getAccuntId()),accBalanceTaxMap,mapBigDecimal);
						
				if ("222102".equals(ta.getAccuntId())) {	//未交增值税特殊处理
					BigDecimal taxRate = new BigDecimal(ta.getRate()).divide(new BigDecimal(100));	//未交增值税的税率
					currentTaxVal = mainOperatingIncomeVal.multiply(taxRate).divide(BigDecimal.ONE.add(taxRate),2,BigDecimal.ROUND_HALF_EVEN);
					
				}else {
					currentTaxVal = mainOperatingIncomeVal.multiply(new BigDecimal(ta.getRate()).divide(new BigDecimal(100))).setScale(2,BigDecimal.ROUND_HALF_EVEN);		
				}
				if ("1".equals(ta.getIsrateenable())) {	//税启用
					totalCurrentTaxVal = totalCurrentTaxVal.add(currentTaxVal);		//保存合计
				}
				mapBigDecimal.put(ta.getAccuntId(), currentTaxVal);
			
		}
		TAccount businessTaxesAdditionalAccount = null;

		if(companySystem == 1){     //小企业
			businessTaxesAdditionalAccount = tAccountService.getAccountsInfoByName("营业税金及附加", fdbid,"5403"); //营业税金及附加科目
		}else if (companySystem == 2){      //新规则
			businessTaxesAdditionalAccount = tAccountService.getAccountsInfoByName("营业税金及附加", fdbid,"6403"); //营业税金及附加科目
		}		
		BigDecimal businessTaxesAdditionalVal = BigDecimal.ZERO;
		if(null != businessTaxesAdditionalAccount){
			businessTaxesAdditionalVal = totalCurrentTaxVal;
		}
		if(businessTaxesAdditionalVal.compareTo(BigDecimal.ZERO) == 1){		
			List creditList = Lists.newArrayList();
			for (TAccount ta : currentTaxAccounts) {
				if ("1".equals(ta.getIsrateenable())) {	//税启用
					BigDecimal currentTaxVal = BigDecimal.ZERO;
					currentTaxVal = mapBigDecimal.get(ta.getAccuntId());
					this.getAccParentName(ta);
					if (currentTaxVal.compareTo(BigDecimal.ZERO) != 0) {
						creditList.add(ImmutableMap.of("account",ta,"val",formatBigDecimal( currentTaxVal ).toString()));
					}	
				}
			}
			map.put("taxPlanning",ImmutableMap.of("businessTaxesAdditionalAccount",businessTaxesAdditionalAccount,"businessTaxesAdditionalVal",formatBigDecimal( businessTaxesAdditionalVal ).toString(),"credit",creditList,"exp","税金计提"));
			if(null != businessTaxesAdditionalAccount){
				if (tSettleAccountsMap.containsKey(businessTaxesAdditionalAccount.getAccuntId())) {
					BigDecimal bigDecimal = tSettleAccountsMap.get(businessTaxesAdditionalAccount.getAccuntId())
											.getDebittotalamount()
											.add(businessTaxesAdditionalVal);
					tSettleAccountsMap.get(businessTaxesAdditionalAccount.getAccuntId())
											.setDebittotalamount(bigDecimal);
				}else {
					TSettleAccounts tSettleAccounts1 = new TSettleAccounts();		
					tSettleAccounts1.setDebitAccount(businessTaxesAdditionalAccount);	//5403营业税金及附加	借
					tSettleAccounts1.setDebitAccountId(businessTaxesAdditionalAccount.getId());
					tSettleAccounts1.setDebittotalamount(businessTaxesAdditionalVal);
					tSettleAccountsMap.put(businessTaxesAdditionalAccount.getAccuntId(), tSettleAccounts1);
				}
			}
			
		}
		
		//查询凭证摘要表中是否存在	计提增值税 这种类的凭证
		TAccount primeOperatingRevenueAccount = null;
		if(companySystem == 1){     //小企业
			primeOperatingRevenueAccount = tAccountService.getAccountsInfoByName("主营业务收入", fdbid,"5001"); //主营业务收入
		}else if (companySystem == 2){      //新规则
			primeOperatingRevenueAccount = tAccountService.getAccountsInfoByName("主营业务收入", fdbid,"6001"); //主营业务收入
		}
		TAccount shouldPayTaxesAccount = tAccountService.getAccountsInfoByName("未交增值税", fdbid,"222102"); //应交税费
		Integer countexp = tVoucherExpService.getCountByAccIDAndAccID(accountPeriod, fdbid, primeOperatingRevenueAccount.getId(), shouldPayTaxesAccount.getId());
		if (countexp<=0) {
			if (!this.isFlag()) {	//出现 计提增值税。。
				List<VTaxbaseformula>  listvalueAddedTax= mapVTaxBaseFormula.get("222102"); //未交增值税的公式
				
				BigDecimal taxRate = new BigDecimal(tAccount2.getRate()).divide(new BigDecimal(100));	//未交增值税的税率
				BigDecimal valueAddedTax = this.getmainOperatingIncomeVal(fdbid,accountPeriod,tAccount2,
						listvalueAddedTax,accBalanceTaxMap,mapBigDecimal);
				
		
				BigDecimal primeOperatingRevenueVal = valueAddedTax.multiply(taxRate).divide(BigDecimal.ONE.add(taxRate),2,BigDecimal.ROUND_HALF_EVEN);
				
				this.getAccParentName(shouldPayTaxesAccount);
				if (primeOperatingRevenueVal.compareTo(BigDecimal.ZERO)==1) {
					map.put("increaseInValue",ImmutableMap.of("primeOperatingRevenueAccount",primeOperatingRevenueAccount,"shouldPayTaxesAccount",shouldPayTaxesAccount,"valDebit",df.format(primeOperatingRevenueVal.negate()).toString(),"valCredit",df.format(primeOperatingRevenueVal).toString(),"exp","计提增值税"));	
					if (primeOperatingRevenueAccount != null) {
						if (tSettleAccountsMap.containsKey(primeOperatingRevenueAccount.getAccuntId())) {
							BigDecimal bigDecimal = tSettleAccountsMap.get(primeOperatingRevenueAccount.getAccuntId())
												.getCredittotalamount()
												.add(primeOperatingRevenueVal);
							tSettleAccountsMap.get(primeOperatingRevenueAccount.getAccuntId())
												.setCredittotalamount(bigDecimal);
						}else {
							TSettleAccounts tSettleAccount11 = new TSettleAccounts();
							tSettleAccount11.setCreditAccount(primeOperatingRevenueAccount);	//	5001主营业务收入	贷
							tSettleAccount11.setCreditAccountId(primeOperatingRevenueAccount.getId());
							tSettleAccount11.setCredittotalamount(primeOperatingRevenueVal.negate());
							tSettleAccountsMap.put(primeOperatingRevenueAccount.getAccuntId(), tSettleAccount11);
						}	
					}				
				}
			}	
		}
				
		// ======1.计提税金=============end====
		
		// ======2.计提工资=============begin====
		//2.1 此处需要判断工资表中是否有当前账期的工资数据 ，首先获取工资表里面最大的账期的工资数据 max_period_salary。 如果max_period_salary为null，那么本期就不会有工资计提；
		//如果max_period_salary不为空，current_periold - max_period_salary >0，那么调用存储过程，复制上一个账期的工资数据到当前账期。
		TCustomerSalary tCustomerSalary = new TCustomerSalary();
		tCustomerSalary.setCustomerId(fdbid);
		tCustomerSalary = tCustomerSalaryService.findListBymax(tCustomerSalary);	//获取最大账期
		if (tCustomerSalary !=null) {
			BigDecimal maxPeriodSalary = tCustomerSalary.getSalaryPeriod() == null ||
					tCustomerSalary.getSalaryPeriod() == "" ? BigDecimal.ZERO : new BigDecimal(tCustomerSalary.getSalaryPeriod());
			BigDecimal currentPeriod =  accountPeriod == null ||
					accountPeriod =="" ? BigDecimal.ZERO : new BigDecimal(accountPeriod);
			if (currentPeriod.compareTo(maxPeriodSalary)==1) {
				
					Map<String, Object> mapSalary = new HashMap<String, Object>();			
					map.put("cid",fdbid);	//公司id 
					map.put("oldspd",tCustomerSalary.getSalaryPeriod());	//最大账期
					map.put("newspd",accountPeriod);	//当前账期	
					map.put("rtn","");
					String result=tCustomerSalaryService.copyCustomerSalary(map);
				
			}
		}
		
		//2.2--计提工资
		TCustomerSalary customerSalary = tCustomerSalaryService.sumSalary(fdbid, accountPeriod);
		if (null != customerSalary) {
			try {
				this.nullToZeroReflect(customerSalary);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		TAccount employeesWageAccount = tAccountService.getAccountsInfoByName("工资", fdbid,"221101");//获取科目：应付职工薪酬-工资
		this.getAccParentName(employeesWageAccount);
		if(null !=customerSalary && null != customerSalary.getIncomeMoney() && BigDecimal.ZERO.compareTo(new BigDecimal(customerSalary.getIncomeMoney())) == -1 )	{
			//计提工资 
			//借560209 管理费用-工资
			//贷221101 应付职工薪酬-工资
			TAccount manageWageAccount = null;
			if(companySystem == 1){     //小企业
				manageWageAccount = tAccountService.getAccountsInfoByName("工资", fdbid,"560101");// 获取科目：560209管理费用-工资
			}else if (companySystem == 2){      //新规则
				manageWageAccount = tAccountService.getAccountsInfoByName("工资", fdbid,"660201");// 获取科目：560209管理费用-工资
			}
			
			this.getAccParentName(manageWageAccount);
			if (manageWageAccount != null) {
				if (tSettleAccountsMap.containsKey(manageWageAccount.getAccuntId())) {
					BigDecimal bigDecimal = tSettleAccountsMap.get(manageWageAccount.getAccuntId())
							.getDebittotalamount()
							.add(new BigDecimal(customerSalary.getIncomeMoney()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
					 tSettleAccountsMap.get(manageWageAccount.getAccuntId())
					 		.setDebittotalamount(bigDecimal);
				} else {
					TSettleAccounts tSettleAccounts22 = new TSettleAccounts();
					tSettleAccounts22.setDebitAccount(manageWageAccount);	//560209管理费用-工资 借
					tSettleAccounts22.setDebitAccountId(manageWageAccount.getId());
					tSettleAccounts22.setDebittotalamount(new BigDecimal(customerSalary.getIncomeMoney()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
					tSettleAccountsMap.put(manageWageAccount.getAccuntId(), tSettleAccounts22);
				}	
			}		
			
			map.put("planRaiseWages",ImmutableMap.of("employeesWageAccount",employeesWageAccount,"manageWageAccount",manageWageAccount,"val",df.format(new BigDecimal(customerSalary.getIncomeMoney())).toString(),"exp","计提工资"));
		}
		
		//2.3--代扣五险一金
		if(null !=customerSalary )	{
			//代扣五险一金
			//借 221101 应付职工薪酬-工资
			//贷 22410101其他应付款-代扣代缴五险一金-养老保险
			//贷 22410102其他应付款-代扣代缴五险一金-医疗保险
			//贷 22410103其他应付款-代扣代缴五险一金-失业保险
			//贷 22410104其他应付款-代扣代缴五险一金-住房公积金
			
			//基本养老保险费 + 基本医疗保险费 + 失业保险费 + 住房公积金
			BigDecimal fiveSocialInsuranceHousingFund = new BigDecimal(customerSalary.getEndowmentInsurance()).add(new BigDecimal(customerSalary.getMedicalInsurance()))
					.add(new BigDecimal(customerSalary.getUnemploymentInsurance())).add(new BigDecimal(customerSalary.getHouseFund()));
			if(BigDecimal.ZERO.compareTo(fiveSocialInsuranceHousingFund) == -1){
				List<TSettleAccounts> creditUnitAccounts = new ArrayList<TSettleAccounts>();
				//养老保险
				if(null != customerSalary && null != customerSalary.getEndowmentInsurance() && new BigDecimal(customerSalary.getEndowmentInsurance()).compareTo(BigDecimal.ZERO) == 1 ){
					TAccount endowmentInsuranceAccount = null;
					if(companySystem == 1){     //小企业
						endowmentInsuranceAccount = tAccountService.getAccountsInfoByName("养老保险", fdbid,"22110401");//22410101其他应付款-代扣代缴五险一金-养老保险	
					}else if (companySystem == 2){      //新规则
						endowmentInsuranceAccount = tAccountService.getAccountsInfoByName("养老保险", fdbid,"22110301");//22410301其他应付款-代扣代缴五险一金-养老保险	
					}
					this.getAccParentName(endowmentInsuranceAccount);
					TSettleAccounts settleAccounts = new TSettleAccounts();
					settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getEndowmentInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN)).toString());
					settleAccounts.setCreditAccount(endowmentInsuranceAccount);
					creditUnitAccounts.add(settleAccounts);
				}
				//医疗保险
				if(null != customerSalary && null != customerSalary.getMedicalInsurance() && new BigDecimal(customerSalary.getMedicalInsurance()).compareTo(BigDecimal.ZERO) == 1 ){
					TAccount medicalInsuranceAccount = null;
					if(companySystem == 1){     //小企业
						medicalInsuranceAccount = tAccountService.getAccountsInfoByName("医疗保险", fdbid,"22110402");//22410102其他应付款-代扣代缴五险一金-医疗保险	
					}else if (companySystem == 2){      //新规则
						medicalInsuranceAccount = tAccountService.getAccountsInfoByName("医疗保险", fdbid,"22110302");//22410102其他应付款-代扣代缴五险一金-医疗保险
					}
					this.getAccParentName(medicalInsuranceAccount);
					TSettleAccounts settleAccounts = new TSettleAccounts();
					settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getMedicalInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN)).toString());
					settleAccounts.setCreditAccount(medicalInsuranceAccount);
					creditUnitAccounts.add(settleAccounts);
				}
				//失业保险
				if(null != customerSalary && null != customerSalary.getUnemploymentInsurance() && new BigDecimal(customerSalary.getUnemploymentInsurance()).compareTo(BigDecimal.ZERO) == 1 ){
					TAccount unemploymentInsuranceAccount = null;
					if(companySystem == 1){     //小企业
						unemploymentInsuranceAccount = tAccountService.getAccountsInfoByName("失业保险", fdbid,"22110403");//22410103其他应付款-代扣代缴五险一金-失业保险	
					}else if (companySystem == 2){      //新规则
						unemploymentInsuranceAccount = tAccountService.getAccountsInfoByName("失业保险", fdbid,"22110303");//22410103其他应付款-代扣代缴五险一金-失业保险
					}
					this.getAccParentName(unemploymentInsuranceAccount);
					TSettleAccounts settleAccounts = new TSettleAccounts();
					settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnemploymentInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN)).toString());
					settleAccounts.setCreditAccount(unemploymentInsuranceAccount);
					creditUnitAccounts.add(settleAccounts);
				}
				//住房公积金
				if(null != customerSalary && null != customerSalary.getHouseFund() && new BigDecimal(customerSalary.getHouseFund()).compareTo(BigDecimal.ZERO) == 1 ){
					TAccount houseFundAccount = null;
					if(companySystem == 1){     //小企业
						houseFundAccount = tAccountService.getAccountsInfoByName("住房公积金", fdbid,"22110406");//22410106其他应付款-代扣代缴五险一金-住房公积金
					}else if (companySystem == 2){      //新规则
						houseFundAccount = tAccountService.getAccountsInfoByName("住房公积金", fdbid,"221104");//22410106其他应付款-代扣代缴五险一金-住房公积金	
					}
					this.getAccParentName(houseFundAccount);
					TSettleAccounts settleAccounts = new TSettleAccounts();
					settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getHouseFund()).setScale(2,BigDecimal.ROUND_HALF_EVEN)).toString());
					settleAccounts.setCreditAccount(houseFundAccount);
					creditUnitAccounts.add(settleAccounts);
				}
				map.put("fiveSocialInsuranceHousingFund", ImmutableMap.of("employeesWageAccount",employeesWageAccount,"val",df.format(fiveSocialInsuranceHousingFund).toString(),"credits",creditUnitAccounts,"exp","代扣五险一金"));
			}
		}
		
		//2.4--代扣个税
		if(null != customerSalary && StringUtils.isNotBlank(customerSalary.getShouldPay())  &&  new BigDecimal(customerSalary.getShouldPay()).compareTo(BigDecimal.ZERO) == 1 ){
			//借221101 应付职工薪酬-工资
			//贷222112应交税费-个人所得税
			TAccount manageWageAccount = tAccountService.getAccountsInfoByName("工资", fdbid,"221101");// 应付职工薪酬-工资
			this.getAccParentName(manageWageAccount);
			TAccount individualIncomeTaxAccount = null;
			if(companySystem == 1){     //小企业
				individualIncomeTaxAccount = tAccountService.getAccountsInfoByName("应交个人所得税", fdbid,"222107");// 应交税费-个人所得税
			}else if (companySystem == 2){      //新规则
				individualIncomeTaxAccount = tAccountService.getAccountsInfoByName("应交个人所得税", fdbid,"222108");// 应交税费-个人所得税
			}
			
			
			this.getAccParentName(individualIncomeTaxAccount);
			map.put("withholdingTax", ImmutableMap.of("manageWageAccount",manageWageAccount,"individualIncomeTaxAccount",individualIncomeTaxAccount,"val",df.format( new BigDecimal(customerSalary.getShouldPay()).setScale(2,BigDecimal.ROUND_HALF_EVEN)).toString(),"exp","代扣个税"));
		}

		//2.5--计提单位承担五险一金
		BigDecimal unitFiveSocialInsuranceHousingFund = BigDecimal.ZERO;
		List<TSettleAccounts> creditUnitAccounts = new ArrayList<TSettleAccounts>();
		if(null != customerSalary && StringUtils.isNotBlank(customerSalary.getUnitEndowmentInsurance())  &&  new BigDecimal(customerSalary.getUnitEndowmentInsurance()).compareTo(BigDecimal.ZERO) == 1 ){
			unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitEndowmentInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
			TSettleAccounts settleAccounts = new TSettleAccounts();
			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitEndowmentInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN)));
			unitFiveSocialInsuranceHousingFund = unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitEndowmentInsurance()));
			TAccount endowmentInsuranceAccount = null;
			if(companySystem == 1){     //小企业
				endowmentInsuranceAccount = tAccountService.getAccountsInfoByName("养老保险", fdbid,"22110401");//22110401应付职工薪酬-单位承担五险一金-养老保险
			}else if (companySystem == 2){      //新规则
				endowmentInsuranceAccount = tAccountService.getAccountsInfoByName("养老保险", fdbid,"22110301");//22110401应付职工薪酬-单位承担五险一金-养老保险	
			}
			this.getAccParentName(endowmentInsuranceAccount);
			settleAccounts.setCreditAccount(endowmentInsuranceAccount);//应付职工薪酬-单位承担五险一金-养老保险
			creditUnitAccounts.add(settleAccounts);
		}
				
		//单位医疗保险
		if(null != customerSalary && StringUtils.isNotBlank(customerSalary.getUnitMedicalInsurance())  &&  new BigDecimal(customerSalary.getUnitMedicalInsurance()).compareTo(BigDecimal.ZERO) == 1 ){
			unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitMedicalInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
			TSettleAccounts settleAccounts = new TSettleAccounts();
//			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitEndowmentInsurance())));
			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitMedicalInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN)));	// zt 修改
			unitFiveSocialInsuranceHousingFund = unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitMedicalInsurance()));
			TAccount medicalInsuranceAccount = null;
			if(companySystem == 1){     //小企业
				medicalInsuranceAccount = tAccountService.getAccountsInfoByName("医疗保险", fdbid,"22110402"); //22110402应付职工薪酬-单位承担五险一金-医疗保险
			}else if (companySystem == 2){      //新规则
				medicalInsuranceAccount = tAccountService.getAccountsInfoByName("医疗保险", fdbid,"22110302"); //22110402应付职工薪酬-单位承担五险一金-医疗保险	
			}
			this.getAccParentName(medicalInsuranceAccount);
			settleAccounts.setCreditAccount(medicalInsuranceAccount);//应付职工薪酬-单位承担五险一金-医疗保险
			creditUnitAccounts.add(settleAccounts);
		}
				
		//单位失业保险
		if(null != customerSalary && StringUtils.isNotBlank(customerSalary.getUnitUnemploymentInsurance())  &&  new BigDecimal(customerSalary.getUnitUnemploymentInsurance()).compareTo(BigDecimal.ZERO) == 1 ){
			unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitUnemploymentInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
			TSettleAccounts settleAccounts = new TSettleAccounts();
//			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitEndowmentInsurance())));
			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitUnemploymentInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN)));	//zt 修改
			unitFiveSocialInsuranceHousingFund = unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitUnemploymentInsurance()));
			TAccount unemploymentInsuranceAccount = null;
			if(companySystem == 1){     //小企业
				unemploymentInsuranceAccount = tAccountService.getAccountsInfoByName("失业保险", fdbid,"22110403");//22110403应付职工薪酬-单位承担五险一金-失业保险
			}else if (companySystem == 2){      //新规则
				unemploymentInsuranceAccount = tAccountService.getAccountsInfoByName("失业保险", fdbid,"22110303");//22110403应付职工薪酬-单位承担五险一金-失业保险	
			}
			this.getAccParentName(unemploymentInsuranceAccount);
			settleAccounts.setCreditAccount(unemploymentInsuranceAccount);//应付职工薪酬-单位承担五险一金-失业保险
			creditUnitAccounts.add(settleAccounts);
		}
		
		//单位工伤保险
		if(null != customerSalary && StringUtils.isNotBlank(customerSalary.getUnitInjuryInsurance())  &&  new BigDecimal(customerSalary.getUnitInjuryInsurance()).compareTo(BigDecimal.ZERO) == 1 ){
			unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitInjuryInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
			TSettleAccounts settleAccounts = new TSettleAccounts();
//			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitEndowmentInsurance())));
			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitInjuryInsurance())));	//zt修改
			unitFiveSocialInsuranceHousingFund = unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitInjuryInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
			TAccount employmentInjuryInsuranceAccount = null;
			if(companySystem == 1){     //小企业
				employmentInjuryInsuranceAccount = tAccountService.getAccountsInfoByName("工伤保险", fdbid,"22110404");//22110404应付职工薪酬-单位承担五险一金-工伤保险
			}else if (companySystem == 2){      //新规则
				employmentInjuryInsuranceAccount = tAccountService.getAccountsInfoByName("工伤保险", fdbid,"22110304");//22110404应付职工薪酬-单位承担五险一金-工伤保险	
			}
			this.getAccParentName(employmentInjuryInsuranceAccount);
			settleAccounts.setCreditAccount(employmentInjuryInsuranceAccount);//应付职工薪酬-单位承担五险一金-工伤保险
			creditUnitAccounts.add(settleAccounts);
		}
				
		//单位生育保险
		if(null != customerSalary && StringUtils.isNotBlank(customerSalary.getUnitBirthInsurance())  &&  new BigDecimal(customerSalary.getUnitBirthInsurance()).compareTo(BigDecimal.ZERO) == 1 ){
			unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitBirthInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
			TSettleAccounts settleAccounts = new TSettleAccounts();
//			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitEndowmentInsurance())));
			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitBirthInsurance())));	//zt修改
			unitFiveSocialInsuranceHousingFund = unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitBirthInsurance()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
			TAccount maternityInsuranceAccount = null;
			if(companySystem == 1){     //小企业
				maternityInsuranceAccount = tAccountService.getAccountsInfoByName("生育保险", fdbid,"22110405");//22110405应付职工薪酬-单位承担五险一金-生育保险
			}else if (companySystem == 2){      //新规则
				maternityInsuranceAccount = tAccountService.getAccountsInfoByName("生育保险", fdbid,"22110305");//22110405应付职工薪酬-单位承担五险一金-生育保险	
			}
			this.getAccParentName(maternityInsuranceAccount);
			settleAccounts.setCreditAccount(maternityInsuranceAccount);//应付职工薪酬-单位承担五险一金-生育保险
			creditUnitAccounts.add(settleAccounts);
		}
		//住房公积金
		if(null != customerSalary && StringUtils.isNotBlank(customerSalary.getUnitHouseFund())  &&  new BigDecimal(customerSalary.getUnitHouseFund()).compareTo(BigDecimal.ZERO) == 1 ){
			unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitHouseFund()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
			TSettleAccounts settleAccounts = new TSettleAccounts();
//			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitEndowmentInsurance())));
			settleAccounts.setVal(df.format(new BigDecimal(customerSalary.getUnitHouseFund())));	//zt修改
			unitFiveSocialInsuranceHousingFund = unitFiveSocialInsuranceHousingFund.add(new BigDecimal(customerSalary.getUnitHouseFund()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
			TAccount housingProvidentFundAccount = null;
			if(companySystem == 1){     //小企业
				housingProvidentFundAccount = tAccountService.getAccountsInfoByName("住房公积金", fdbid,"22110406");//22110406应付职工薪酬-单位承担五险一金-住房公积金
			}else if (companySystem == 2){      //新规则
				housingProvidentFundAccount = tAccountService.getAccountsInfoByName("住房公积金", fdbid,"221104");//22110406应付职工薪酬-单位承担五险一金-住房公积金	
			}
			this.getAccParentName(housingProvidentFundAccount);
			settleAccounts.setCreditAccount(housingProvidentFundAccount);//应付职工薪酬-单位承担五险一金-住房公积金
			creditUnitAccounts.add(settleAccounts);
		}
			
		if(unitFiveSocialInsuranceHousingFund.compareTo(BigDecimal.ZERO) == 1){
			//借560210 管理费用-五险一金
			TAccount unitFiveSocialInsuranceHousingFundAccount = null;
			if(companySystem == 1){     //小企业
				unitFiveSocialInsuranceHousingFundAccount = tAccountService.getAccountsInfoByName("五险一金", fdbid,"560202");// 管理费用-五险一金
			}else if (companySystem == 2){      //新规则
				unitFiveSocialInsuranceHousingFundAccount = tAccountService.getAccountsInfoByName("五险一金", fdbid,"660202");// 管理费用-五险一金
			}
			this.getAccParentName(unitFiveSocialInsuranceHousingFundAccount);
			if (unitFiveSocialInsuranceHousingFundAccount != null) {
				if (tSettleAccountsMap.containsKey(unitFiveSocialInsuranceHousingFundAccount.getAccuntId())) {
					BigDecimal bigDecimal = tSettleAccountsMap.get(unitFiveSocialInsuranceHousingFundAccount.getAccuntId())
										.getDebittotalamount()
										.add(unitFiveSocialInsuranceHousingFund);
					tSettleAccountsMap.get(unitFiveSocialInsuranceHousingFundAccount.getAccuntId())
										.setDebittotalamount(bigDecimal);
				} else {
					TSettleAccounts tSettleAccounts25 = new TSettleAccounts();
					tSettleAccounts25.setDebitAccount(unitFiveSocialInsuranceHousingFundAccount);	//借560210 管理费用-五险一金
					tSettleAccounts25.setDebitAccountId(unitFiveSocialInsuranceHousingFundAccount.getId());
					tSettleAccounts25.setDebittotalamount(unitFiveSocialInsuranceHousingFund);
					tSettleAccountsMap.put(unitFiveSocialInsuranceHousingFundAccount.getAccuntId(), tSettleAccounts25);

				}	
			}			
			map.put("unitFiveSocialInsuranceHousingFundAccount",ImmutableMap.of("unitFiveSocialInsuranceHousingFundAccount",unitFiveSocialInsuranceHousingFundAccount,"val",df.format(unitFiveSocialInsuranceHousingFund),"credits",creditUnitAccounts,"exp","五险一金"));
		}

		// ======2.计提工资=============end====
		
		
		// ======3.结转销售成本:统计“金额平衡表”符合科目的值==涉及科目名称：本期主营业务收入、库存商品余额========begin=========
		String primeOperatingRevenueAccId = null;
		if(companySystem == 1){     //小企业
			primeOperatingRevenueAccId = getAccountsInfoByName("主营业务收入", fdbid ,"5001"); //主营业务收入
		}else if (companySystem == 2){      //新规则
			primeOperatingRevenueAccId = getAccountsInfoByName("主营业务收入", fdbid ,"6001"); //主营业务收入
		}
		List<TSettleAccounts> primeOperatingRevenue = tBalanceService.settleAccounts(primeOperatingRevenueAccId, fdbid, accountPeriod);// 主营业务收入
		
		String primeOperatingCostAccId = null; 
		if(companySystem == 1){     //小企业
			primeOperatingCostAccId = getAccountsInfoByName("主营业务成本", fdbid ,"5401"); //主营业务成本
		}else if (companySystem == 2){      //新规则
			primeOperatingCostAccId = getAccountsInfoByName("主营业务成本", fdbid ,"6401"); //主营业务成本
		}
		List<TSettleAccounts> primeOperatingCost = tBalanceService.settleAccounts(primeOperatingCostAccId, fdbid, accountPeriod);// 主营业务成本
		
		String inventoryBalanceAccId = getAccountsInfoByName("库存商品", fdbid,"1405"); // 库存商品
		List<TSettleAccounts> inventoryBalance = tBalanceService.settleAccounts(inventoryBalanceAccId, fdbid, accountPeriod);// 库存商品余额
		
		// 从“科目5001 主营业务收入”本期贷方发生累计额的值，根据结转百分比设置（默认值为0）进行相乘获得的值
		BigDecimal primeOperatingRevenueValue = BigDecimal.ZERO;// 本期贷方发生累计额的值
		BigDecimal mainBusinessIncome = BigDecimal.ZERO;
		String carryForwardPercentage = customerService.get(fdbid).getCarryForwardPercentage();
		carryForwardPercentage = carryForwardPercentage == null 
				|| carryForwardPercentage == "" 
				? "0" : carryForwardPercentage;
		double percentageJunction = Double.valueOf(carryForwardPercentage);// 结转百分比 TODO 从设置值获取
		BigDecimal primeOperatingRevenuePercentageValue = BigDecimal.ZERO;// 本期贷方发生累计额的值  * 结转百分比
		if (CollectionUtils.isNotEmpty(primeOperatingRevenue)) {
			for (TSettleAccounts settleAccounts : primeOperatingRevenue) {
				if(null != settleAccounts){
					BigDecimal balance = settleAccounts.getCredittotalamount().subtract(settleAccounts.getDebittotalamount()).setScale(2,BigDecimal.ROUND_HALF_EVEN);// zt 修改    科目为贷方科目 本期贷减本期借位本期发生累计金额
					primeOperatingRevenueValue = primeOperatingRevenueValue.add(balance);
					mainBusinessIncome = primeOperatingRevenueValue;
//					primeOperatingRevenueValue = primeOperatingRevenueValue.add(settleAccounts.getCredittotalamount().add(settleAccounts.getDebittotalamount()));
				}
			}
			if (primeOperatingRevenueValue.compareTo(BigDecimal.ZERO) == 1) {		
					primeOperatingRevenuePercentageValue = primeOperatingRevenueValue.multiply(BigDecimal.valueOf(percentageJunction).divide(new BigDecimal("100")));
			}
		}
		BigDecimal primeOperatingCostValue = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(primeOperatingCost)) {	//主营业务成本
			for (TSettleAccounts settleAccounts : primeOperatingCost) {
				if (null != settleAccounts) {
					primeOperatingCostValue = settleAccounts.getDebittotalamount().subtract(settleAccounts.getCredittotalamount()).setScale(2,BigDecimal.ROUND_HALF_EVEN);
				}
			}
		}
		
		
		// 库存商品余额
		BigDecimal inventoryBalanceValue = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(inventoryBalance)) {
			for (TSettleAccounts settleAccounts : inventoryBalance) {
				if(null != settleAccounts){
					inventoryBalanceValue = inventoryBalanceValue.add(settleAccounts.getBalance().setScale(2,BigDecimal.ROUND_HALF_EVEN));
					//inventoryBalanceValue = inventoryBalanceValue.add(settleAccounts.getCredittotalamount()).add(settleAccounts.getDebittotalamount());
				}
			}
		}	
		// 比较规则：库存商品余额 > 主营业务收入 * 结转百分比 TODO 那个大取那个么？//zt 修改   必须显示  如果 "库存商品余额 > 主营业务收入 * 结转百分比"规则 满足 则提示  不允许结账
		if(mainBusinessIncome.compareTo(BigDecimal.ZERO) == 1 && primeOperatingCostValue.compareTo(BigDecimal.ZERO) == 0){
//			boolean carryoverFlag = false;
//			if (inventoryBalanceValue.compareTo(primeOperatingRevenuePercentageValue) == -1
//					||primeOperatingRevenueValue.compareTo(BigDecimal.ZERO) == -1) {
//				carryoverFlag = true;	//不允许结转
//			} 			
			TAccount primeOperatingRevenueAcc = tAccountService.get(primeOperatingRevenueAccId); //主营业务收入
			TAccount primeOperatingCostAcc = tAccountService.get(primeOperatingCostAccId); //主营业务成本
			TAccount inventoryBalanceAcc = tAccountService.get(inventoryBalanceAccId);// 库存商品
	
			Map costSales = new HashMap();
			costSales.put("primeOperatingRevenueAcc", primeOperatingCostAcc);
			costSales.put("inventoryBalanceAcc", inventoryBalanceAcc);
			costSales.put("primeOperatingRevenuePercentageValue", df.format(primeOperatingRevenuePercentageValue));
			costSales.put("primeOperatingRevenueValue", df.format(primeOperatingRevenueValue));
			costSales.put("inventoryBalanceValue",  df.format(inventoryBalanceValue));
			costSales.put("carryForwardPercentage", percentageJunction);
		//	costSales.put("carryoverFlag", carryoverFlag);
			map.put("carryForwardCostSales", costSales);
//			if (tSettleAccountsMap != null) {
//				if (tSettleAccountsMap.containsKey(primeOperatingCostAcc.getAccuntId())) {
//					BigDecimal bigDecimal = tSettleAccountsMap.get(primeOperatingCostAcc.getAccuntId())
//										.getDebittotalamount()
//										.add(primeOperatingRevenuePercentageValue);
//					tSettleAccountsMap.get(primeOperatingCostAcc.getAccuntId())
//										.setDebittotalamount(bigDecimal);
//				} else {
//					TSettleAccounts tSettleAccounts3 = new TSettleAccounts();
//					tSettleAccounts3.setDebitAccount(primeOperatingCostAcc);	// 5401主营业务成本	借
//					tSettleAccounts3.setDebitAccountId(primeOperatingCostAcc.getId());
//					tSettleAccounts3.setDebittotalamount(primeOperatingRevenuePercentageValue);
//					tSettleAccountsMap.put(primeOperatingCostAcc.getAccuntId(), tSettleAccounts3);
//				}	
//			}			
		}

		
		
		// ======3.结转销售成本:统计“金额平衡表”符合科目的值==涉及科目名称：本期主营业务收入、库存商品余额========end=========
		
		//=======判断是否初期余额设置折旧。如果设置折旧减去累计折旧===========begin=========
		String periodInit = customerObject.getInitperiod();
		Map<String, BigDecimal> amortizeMap = Maps.newHashMap();
		//if (accountPeriod.equals(periodInit)) {	//如果当前账期等于初始账期
			List<String> accountIdStrings = tAmortizeService.selectAccountIdByPeriodAndFdbid(accountPeriod, fdbid);
			List<String> newAccountID = Lists.newArrayList();
			this.removeDuplicateWithOrder(accountIdStrings);//移除相同的科目编号
			for (Iterator<String> iterator = accountIdStrings.iterator(); iterator.hasNext();) {
				String accountID = iterator.next();
				String accAmortId = tAccountService.selectAccountByAccountIdAndFdbid(accountID, fdbid);
				amortizeMap.put(accAmortId, BigDecimal.ZERO);
				StringBuilder str = new StringBuilder(accountID);
				str.replace(3,4,"2");	//更换第四个字符为2
				newAccountID.add(str.toString());
			}
			List<TAccount> listTaAccounts = tAccountService.selectAccountAndBalanceByPeriodAndFdbid(accountPeriod, fdbid, newAccountID);	//查询对应的折旧科目
			for (Iterator<TAccount> iterator = listTaAccounts.iterator(); iterator.hasNext();) {
				TAccount ta = iterator.next();
				StringBuilder str = new StringBuilder(ta.getAccuntId());
				str.replace(3,4,"1");	//更换第四个字符为2
				ta.setAccuntId(str.toString());
				ta.setId(tAccountService.selectAccountByAccountIdAndFdbid(str.toString(), fdbid));
				amortizeMap.put(ta.getId(), new BigDecimal(ta.getLevel()).negate());
			}
			//tAmortizeService.updateAmortizeFromOriginalValue(listTaAccounts,accountPeriod,fdbid);
		//}	
		
		//=======判断是否初期余额设置折旧。如果设置折旧减去累计折旧===========end=========

		// ======4.计提折旧  固定资产=============begin====
		// TODO 摊销状态
		List<TAmortize> provisionDepreciation = tAmortizeService.settleAccountsProvisionDepreciation(accId, fdbid, accountPeriod, "计提折旧");// 计提折旧
		List<TSettleAccounts> provisionDepreciationSelect = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(provisionDepreciation)){
			for(TAmortize tAmortize : provisionDepreciation){
				TAccount debitAccount = tAccountService.get(tAmortize.getDebitAccountId());
				BigDecimal originalValue = new BigDecimal(tAmortize.getOriginalValue());
				BigDecimal scrapValue = new BigDecimal(tAmortize.getScrapValue());
				BigDecimal amortizeAgeLimit = new BigDecimal(tAmortize.getAmortizeAgeLimit());
				BigDecimal totalOldPosition = new BigDecimal(tAmortize.getTotalOldPosition());
				BigDecimal balance = BigDecimal.ZERO;
				if (amortizeAgeLimit.compareTo(totalOldPosition.add(BigDecimal.ONE)) == 0) {	//维护最后一次摊销
					if(tAmortize.getIfInit().equals("1")){	//初始余额时候
						BigDecimal amortize = amortizeMap.get(tAmortize.getAmortizeAccountId());
						balance = originalValue.subtract(
										((originalValue.subtract(scrapValue).subtract(amortize))
											.divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN))
												.multiply(totalOldPosition));
						
					}else {
						balance = originalValue.subtract(	
										(originalValue.subtract(scrapValue))
											.divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN)
												.multiply(totalOldPosition));
					}
				}else {
					if(tAmortize.getIfInit().equals("1")){	//初始余额时候
						BigDecimal amortize = amortizeMap.get(tAmortize.getAmortizeAccountId());
						balance = (originalValue.subtract(scrapValue).subtract(amortize)).divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN);
					}else {
						balance = (originalValue.subtract(scrapValue)).divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN);
					}
				}
				 
				
				if (debitAccount != null) {
					//updata by : zt getAccountId to getGroupId
					if (debitAccount.getGroupId().subSequence(0, 1).equals("5")) {	//有损益类科目	
						if (tSettleAccountsMap.containsKey(debitAccount.getAccuntId())) {
							BigDecimal bigDecimal = tSettleAccountsMap.get(debitAccount.getAccuntId())
												.getDebittotalamount()
												.add(balance);
							tSettleAccountsMap.get(debitAccount.getAccuntId())
												.setDebittotalamount(bigDecimal);
						} else {
							TSettleAccounts tSettleAccount4 = new TSettleAccounts();
							tSettleAccount4.setDebitAccount(debitAccount);	//借方
							tSettleAccount4.setDebitAccountId(debitAccount.getId());
							tSettleAccount4.setDebittotalamount(balance);	
							tSettleAccountsMap.put(debitAccount.getAccuntId(), tSettleAccount4);
						}
						
					}
					
					this.getAccParentName(debitAccount);	
				}
				TAccount creditAccount = tAccountService.get(tAmortize.getCreditAccountId());
				if (creditAccount != null) {
					//updata by : zt getAccountId to getGroupId
					if (creditAccount.getGroupId().subSequence(0, 1).equals("5")) {////有损益类科目
						if (tSettleAccountsMap.containsKey(creditAccount.getAccuntId())) {
							BigDecimal bigDecimal = tSettleAccountsMap.get(creditAccount.getAccuntId())
												.getCredittotalamount()
												.add(balance);
							tSettleAccountsMap.get(creditAccount.getAccuntId())
												.setCredittotalamount(bigDecimal);
						} else {
							TSettleAccounts tSettleAccount4 = new TSettleAccounts();
							tSettleAccount4.setCreditAccount(creditAccount);	//贷方
							tSettleAccount4.setCreditAccountId(creditAccount.getId());
							tSettleAccount4.setCredittotalamount(balance);	
							tSettleAccountsMap.put(creditAccount.getAccuntId(), tSettleAccount4);
						}					
					}
					this.getAccParentName(creditAccount);
				}
				TSettleAccounts settleAccounts = new TSettleAccounts ();
				settleAccounts.setDebitAccount(debitAccount);
				settleAccounts.setCreditAccount(creditAccount);
				settleAccounts.setBeginBalance(formatBigDecimal(balance));
				settleAccounts.setVal(df.format(settleAccounts.getBeginBalance()));
				provisionDepreciationSelect.add(settleAccounts);
			}
		}
		map.put("provisionDepreciation", ImmutableMap.of("name","计提折旧","data",provisionDepreciationSelect,"exp","计提折旧"));
	/*	BigDecimal provisionDepreciationValue = addBeginBalance(provisionDepreciation);
		if (provisionDepreciationValue.compareTo(BigDecimal.ZERO) == 1) {
			TAccount acc = tAccountService.get(accId);
			map.put("provisionDepreciation", ImmutableMap.of("acc", acc, "val", df.format(provisionDepreciationValue)));
		}*/

		// ======4.计提折旧  固定资产=============end====

		
		// ======5.无形资产=============begin====
		//List<TSettleAccounts> immaterialAssets = tAmortizeService.settleAccounts(accId, fdbid, accountPeriod,"固定资产");// 无形资产
		List<TAmortize> immaterialAssets = tAmortizeService.settleAccounts(accId, fdbid, accountPeriod,"无形资产摊销");// 无形资产  zt  修改
		List<TSettleAccounts> immaterialAssetsSelect = Lists.newArrayList();
		//BigDecimal immaterialAssetsValue = addBeginBalance(immaterialAssets);
		if(CollectionUtils.isNotEmpty(immaterialAssets)){
			for(TAmortize tAmortize : immaterialAssets){
				TAccount debitAccount = tAccountService.get(tAmortize.getDebitAccountId());
				BigDecimal originalValue = new BigDecimal(tAmortize.getOriginalValue());
				BigDecimal scrapValue = new BigDecimal(tAmortize.getScrapValue());
				BigDecimal amortizeAgeLimit = new BigDecimal(tAmortize.getAmortizeAgeLimit());
				BigDecimal totalOldPosition = new BigDecimal(tAmortize.getTotalOldPosition());
				BigDecimal balance = BigDecimal.ZERO;
				if (amortizeAgeLimit.compareTo(totalOldPosition.add(BigDecimal.ONE)) == 0) {	//维护摊销最后一期
					if (tAmortize.getIfInit().equals("1")) {	//初始余额时候
						BigDecimal amortize = amortizeMap.get(tAmortize.getAmortizeAccountId());
						balance = originalValue.subtract(
										(originalValue.subtract(scrapValue).subtract(amortize))
											.divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN)
												.multiply(totalOldPosition));
					} else {
						balance = originalValue.subtract(
										(originalValue.subtract(scrapValue))
											.divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN)
												.multiply(totalOldPosition));
					}
				}else {
					if (tAmortize.getIfInit().equals("1")) {	//初始余额时候
						BigDecimal amortize = amortizeMap.get(tAmortize.getAmortizeAccountId());
						balance = (originalValue.subtract(scrapValue).subtract(amortize)).divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN);
					} else {
						balance = (originalValue.subtract(scrapValue)).divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN);
					}
				}				
				if (debitAccount != null) {
					//updata by : zt getAccountId to getGroupId
					if (debitAccount.getGroupId().subSequence(0, 1).equals("5")) {	//有损益类科目	
						if (tSettleAccountsMap.containsKey(debitAccount.getAccuntId())) {
							BigDecimal bigDecimal = tSettleAccountsMap.get(debitAccount.getAccuntId())
												.getDebittotalamount()
												.add(balance);
							tSettleAccountsMap.get(debitAccount.getAccuntId())
												.setDebittotalamount(bigDecimal);
						} else {
							TSettleAccounts tSettleAccount4 = new TSettleAccounts();
							tSettleAccount4.setDebitAccount(debitAccount);	//借方
							tSettleAccount4.setDebitAccountId(debitAccount.getId());
							tSettleAccount4.setDebittotalamount(balance);	
							tSettleAccountsMap.put(debitAccount.getAccuntId(), tSettleAccount4);
						}
					}
					this.getAccParentName(debitAccount);
				}
				TAccount creditAccount = tAccountService.get(tAmortize.getCreditAccountId());
				if (creditAccount != null) {
					//updata by : zt getAccountId to getGroupId
					if (creditAccount.getGroupId().subSequence(0, 1).equals("5")) {////有损益类科目
						if (tSettleAccountsMap.containsKey(creditAccount.getAccuntId())) {
							BigDecimal bigDecimal = tSettleAccountsMap.get(creditAccount.getAccuntId())
												.getCredittotalamount()
												.add(balance);
							tSettleAccountsMap.get(creditAccount.getAccuntId())
												.setCredittotalamount(bigDecimal);
						} else {
							TSettleAccounts tSettleAccount4 = new TSettleAccounts();
							tSettleAccount4.setCreditAccount(creditAccount);	//贷方
							tSettleAccount4.setCreditAccountId(creditAccount.getId());
							tSettleAccount4.setCredittotalamount(balance);	
							tSettleAccountsMap.put(creditAccount.getAccuntId(), tSettleAccount4);
						}					
					}
					this.getAccParentName(creditAccount);
				}
				TSettleAccounts settleAccounts = new TSettleAccounts ();
				settleAccounts.setDebitAccount(debitAccount);
				settleAccounts.setCreditAccount(creditAccount);
				settleAccounts.setBeginBalance(formatBigDecimal(balance));
				settleAccounts.setVal(df.format(balance));
				immaterialAssetsSelect.add(settleAccounts);
			}
		}
		map.put("immaterialAssets", ImmutableMap.of("name","无形资产","data",immaterialAssetsSelect,"exp","无形资产摊销"));
		
		// ======5.无形资产=============end====
		
		
		
		
		// ======6.摊销待摊费用=============begin====
		// TODO 摊销状态
		List<TAmortize> amortizationExpense = tAmortizeService.settleAccounts(accId, fdbid, accountPeriod,"长期待摊费用摊销");// 摊销费用
		List<TSettleAccounts> amortizationExpenseSelect = Lists.newArrayList();
		//BigDecimal amortizationExpenseValue = addBeginBalance(amortizationExpense);
		if(CollectionUtils.isNotEmpty(amortizationExpense)){
			for(TAmortize tAmortize : amortizationExpense){
				TAccount debitAccount = tAccountService.get(tAmortize.getDebitAccountId());
				BigDecimal originalValue = new BigDecimal(tAmortize.getOriginalValue());
				BigDecimal scrapValue = new BigDecimal(tAmortize.getScrapValue());
				BigDecimal amortizeAgeLimit = new BigDecimal(tAmortize.getAmortizeAgeLimit());
				BigDecimal totalOldPosition = new BigDecimal(tAmortize.getTotalOldPosition());
				BigDecimal balance = BigDecimal.ZERO;
				if (amortizeAgeLimit.compareTo(totalOldPosition.add(BigDecimal.ONE)) == 0) {	//维护摊销最后一期
					if (tAmortize.getIfInit().equals("1")) {
						BigDecimal amortize = amortizeMap.get(tAmortize.getAmortizeAccountId());
						balance = originalValue.subtract(
									(originalValue.subtract(scrapValue).subtract(amortize))
										.divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN)
											.multiply(totalOldPosition));
					} else {
						balance = originalValue.subtract(
									(originalValue.subtract(scrapValue))
										.divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN)
											.multiply(totalOldPosition));
					}
				}else {
					if (tAmortize.getIfInit().equals("1")) {
						BigDecimal amortize = amortizeMap.get(tAmortize.getAmortizeAccountId());
						balance = (originalValue.subtract(scrapValue).subtract(amortize)).divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN);
					} else {
						balance = (originalValue.subtract(scrapValue)).divide(amortizeAgeLimit,2, BigDecimal.ROUND_HALF_EVEN);
					}	
				}
				if (debitAccount != null) {
					//updata by : zt getAccountId to getGroupId
					if (debitAccount.getGroupId().subSequence(0, 1).equals("5")) {	//有损益类科目	
						if (tSettleAccountsMap.containsKey(debitAccount.getAccuntId())) {
							BigDecimal bigDecimal = tSettleAccountsMap.get(debitAccount.getAccuntId())
												.getDebittotalamount()
												.add(balance);
							tSettleAccountsMap.get(debitAccount.getAccuntId())
												.setDebittotalamount(bigDecimal);
						} else {
							TSettleAccounts tSettleAccount4 = new TSettleAccounts();
							tSettleAccount4.setDebitAccount(debitAccount);	//借方
							tSettleAccount4.setDebitAccountId(debitAccount.getId());
							tSettleAccount4.setDebittotalamount(balance);	
							tSettleAccountsMap.put(debitAccount.getAccuntId(), tSettleAccount4);
						}
					}
					this.getAccParentName(debitAccount);
				}
				TAccount creditAccount = tAccountService.get(tAmortize.getCreditAccountId());
				if (creditAccount != null) {
					//updata by : zt getAccountId to getGroupId
					if (creditAccount.getGroupId().subSequence(0, 1).equals("5")) {////有损益类科目
						if (tSettleAccountsMap.containsKey(creditAccount.getAccuntId())) {
							BigDecimal bigDecimal = tSettleAccountsMap.get(creditAccount.getAccuntId())
												.getCredittotalamount()
												.add(balance);
							tSettleAccountsMap.get(creditAccount.getAccuntId()).setCredittotalamount(bigDecimal);
						} else {
							TSettleAccounts tSettleAccount4 = new TSettleAccounts();
							tSettleAccount4.setCreditAccount(creditAccount);	//贷方
							tSettleAccount4.setCreditAccountId(creditAccount.getId());
							tSettleAccount4.setCredittotalamount(balance);	
							tSettleAccountsMap.put(creditAccount.getAccuntId(), tSettleAccount4);
						}				
					}
					this.getAccParentName(creditAccount);
				}
				TSettleAccounts settleAccounts = new TSettleAccounts ();
				settleAccounts.setDebitAccount(debitAccount);
				settleAccounts.setCreditAccount(creditAccount);
				settleAccounts.setBeginBalance(formatBigDecimal(balance));
				settleAccounts.setVal(df.format(settleAccounts.getBeginBalance()));
				amortizationExpenseSelect.add(settleAccounts);
			}
		}
		map.put("amortizationExpense", ImmutableMap.of("name","摊销待摊费用","data",amortizationExpenseSelect,"exp","长期待摊费用摊销"));
		// ======6.摊销待摊费用=============end====

		

		// ======7.结转未交增值税=============begin====
		if("1".equals(customerObject.getValueAddedTax())){	//1 ValueAddedTax 小型纳税人
			TAccount outputAccount  = tAccountService.getAccountsInfoByName("销项税额", fdbid,"22210102");
			
			this.getAccParentName(outputAccount);
			TAccount inputAccount  = tAccountService.getAccountsInfoByName("进项税额", fdbid,"22210101");
			this.getAccParentName(inputAccount);
			List<TSettleAccounts> output = tBalanceService.settleAccounts(outputAccount.getId(), fdbid, accountPeriod);// 销项税额
			List<TSettleAccounts> input = tBalanceService.settleAccounts(inputAccount.getId(), fdbid, accountPeriod);// 进项税额
			BigDecimal outputValue = addBalance(output);// 销项税额值
			BigDecimal inputValue = addBalance(input);// 进项税额值
			// 情况1： 22210106应交税费-应交增值税-销项税额 》 2210101 应交税费-应交增值税-进项税额
			// 借：22210105应交税费-应交增值税-转出未交增值税
			// 贷:222102应交税费-未交增值税
			
			TAccount transferredOutVATAccount  = tAccountService.getAccountsInfoByName("转出未交增值税", fdbid,"22210106"); //转出未交增值税 科目
			this.getAccParentName(transferredOutVATAccount);
			TAccount unpaidVATValueAccount = tAccountService.getAccountsInfoByName("未交增值税", fdbid,"222102"); //未交增值税 科目
			this.getAccParentName(unpaidVATValueAccount);
			BigDecimal transferredOutVATValue = BigDecimal.ZERO;
			BigDecimal unpaidVATValue = BigDecimal.ZERO;
			List<TSettleAccounts> transferredOutVAT = tBalanceService.settleAccounts(transferredOutVATAccount.getId(), fdbid, accountPeriod);// 转出未交增值税
			List<TSettleAccounts> unpaidVAT = tBalanceService.settleAccounts(unpaidVATValueAccount.getId(), fdbid, accountPeriod);// 未交增值税
			transferredOutVATValue = addBalance(transferredOutVAT);// 转出未交增值税值
			unpaidVATValue = addBalance(unpaidVAT);// 未交增值税值
			transferredOutVATValue = formatBigDecimal(transferredOutVATValue);
			unpaidVATValue = formatBigDecimal(unpaidVATValue);
			if (inputValue.compareTo(BigDecimal.ZERO)!=0) {	//进项税额值 销项税额值 不为0
				if (outputValue.compareTo(inputValue) == 1) {
					map.put("carryForwardNotPayValueAddedTax", ImmutableMap.of("debitAccount", transferredOutVATAccount, "debitVal", formatBigDecimal(transferredOutVATValue).toString(),"creditAccount", unpaidVATValueAccount, "creditVal", formatBigDecimal(unpaidVATValue).toString()));
				} else if (outputValue.compareTo(inputValue) == -1) {
					map.put("carryForwardNotPayValueAddedTax", ImmutableMap.of("debitAccount", unpaidVATValueAccount, "debitVal", formatBigDecimal(unpaidVATValue).toString(),"creditAccount", transferredOutVATAccount, "creditVal",formatBigDecimal(transferredOutVATValue).toString()));
				}		
			}
		}
		// ======7.结转未交增值税=============end====

		String previousAnnualProfitAndLossAdjustmentAccountId = "";
		if (companySystem == 1) {
			previousAnnualProfitAndLossAdjustmentAccountId = "6000";
		}else {
			previousAnnualProfitAndLossAdjustmentAccountId="6901";
		}
		// ======8.结转损益=============begin====	
		//税金计提中的5403 营业税金及附加（借） 金额发生在借方   和  主营业务收入（贷方） 金额发生在贷方  
		//损益类科目  贷方  科目 发生额在贷方  金额增加，   发生在借方 金额减少
		List<TAccount> rofitLossAccountDebit  = tAccountService.findAllListByAccountIdBeginWith("5", fdbid,"1");//损益类科目  借方
		List<TSettleAccounts>  rofitLossBalancesDebit = new ArrayList<TSettleAccounts>();
		BigDecimal rofitLossAccountDebitVal = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(rofitLossAccountDebit)){
			for(TAccount account : rofitLossAccountDebit){
				if (!account.getAccuntId().equals(previousAnnualProfitAndLossAdjustmentAccountId)) {
					this.getAccParentName(account);
					TSettleAccounts settleAccounts = tBalanceService.settleAccounts(account.getId(), fdbid, accountPeriod).get(0);
					if(null != settleAccounts){
						// 余额为 借方   -  贷方
						BigDecimal balance = settleAccounts.getDebittotalamount().subtract(settleAccounts.getCredittotalamount());
						if(balance.compareTo( BigDecimal.ZERO) == 1){
							if (tSettleAccountsMap.containsKey(account.getAccuntId())) {
								TSettleAccounts settleAccountsForMap = tSettleAccountsMap.get(account.getAccuntId());
								balance = balance.add(settleAccountsForMap.getDebittotalamount());
								//rofitLossAccountDebitVal = rofitLossAccountDebitVal.add(settleAccountsForMap.getDebittotalamount());
							}
							rofitLossAccountDebitVal = rofitLossAccountDebitVal.add(balance);
							settleAccounts.setDebitAccount(account);
							settleAccounts.setVal(df.format(balance));
							rofitLossBalancesDebit.add(settleAccounts);
						}else {
							if (tSettleAccountsMap.containsKey(account.getAccuntId())){
								TSettleAccounts settleAccountsForMap = tSettleAccountsMap.get(account.getAccuntId());
								if (settleAccountsForMap.getDebittotalamount().compareTo(BigDecimal.ZERO) == 1) {
									settleAccountsForMap.setVal(df.format(settleAccountsForMap.getDebittotalamount()));
									rofitLossAccountDebitVal = rofitLossAccountDebitVal.add(settleAccountsForMap.getDebittotalamount());
									rofitLossBalancesDebit.add(settleAccountsForMap);	
								}
							}
						}
					}else {
						if (tSettleAccountsMap.containsKey(account.getAccuntId())) {
							TSettleAccounts settleAccountsForMap = tSettleAccountsMap.get(account.getAccuntId());
							if (settleAccountsForMap.getDebittotalamount().compareTo(BigDecimal.ZERO) == 1) {
								settleAccountsForMap.setVal(df.format(settleAccountsForMap.getDebittotalamount()));
								rofitLossAccountDebitVal = rofitLossAccountDebitVal.add(settleAccountsForMap.getDebittotalamount());
								rofitLossBalancesDebit.add(settleAccountsForMap);	
							}
						}	
					}	
				}				
				//根据科目id 去 tSettleAccountsMap 取值 如果存在 且为 成本 累的  则将值 累加到 rofitLossAccountDebitVal  和   settleAccounts.setVal(df.format(balance));630
			
			}
			
			//遍历 map 对于属于成本  且 上面循环没有用到的 科目 及金额值 处理后 存到	rofitLossBalancesDebit.add(settleAccounts);
		}
		
		List<TAccount> rofitLossAccountCredit  = tAccountService.findAllListByAccountIdBeginWith("5", fdbid,"-1");//损益类科目  贷方
		List<TSettleAccounts>  rofitLossBalancesCredit = new ArrayList<TSettleAccounts>();
		BigDecimal rofitLossAccountCreditVal = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(rofitLossAccountCredit)){
			for(TAccount account : rofitLossAccountCredit){
				if (!account.getAccuntId().equals(previousAnnualProfitAndLossAdjustmentAccountId)) {
					this.getAccParentName(account);
					TSettleAccounts settleAccounts = tBalanceService.settleAccounts(account.getId(), fdbid, accountPeriod).get(0);
					if(null != settleAccounts){
						// 余额为 贷方   -  借方
						BigDecimal balance = settleAccounts.getCredittotalamount().subtract(settleAccounts.getDebittotalamount());
						if (balance.compareTo(BigDecimal.ZERO) == 1) {
							if (tSettleAccountsMap.containsKey(account.getAccuntId())) {
								TSettleAccounts settleAccountsForMap = tSettleAccountsMap.get(account.getAccuntId());
								balance = balance.add(settleAccountsForMap.getCredittotalamount());
								//rofitLossAccountCreditVal = rofitLossAccountCreditVal.add(settleAccountsForMap.getCredittotalamount());
							}
							rofitLossAccountCreditVal = rofitLossAccountCreditVal.add(balance);
							settleAccounts.setDebitAccount(account);
							settleAccounts.setVal(df.format(balance));
							rofitLossBalancesCredit.add(settleAccounts);
						}else {
							if (tSettleAccountsMap.containsKey(account.getAccuntId())) {
								TSettleAccounts settleAccountsForMap = tSettleAccountsMap.get(account.getAccuntId());
								if (settleAccountsForMap.getCredittotalamount().compareTo(BigDecimal.ZERO) == 1) {
									settleAccountsForMap.setVal(df.format(settleAccountsForMap.getCredittotalamount()));
									rofitLossAccountCreditVal = rofitLossAccountCreditVal.add(settleAccountsForMap.getCredittotalamount());
									rofitLossBalancesCredit.add(settleAccountsForMap);	
								}							
							}	
						}
					}else {
						if (tSettleAccountsMap.containsKey(account.getAccuntId())) {
							TSettleAccounts settleAccountsForMap = tSettleAccountsMap.get(account.getAccuntId());
							if (settleAccountsForMap.getCredittotalamount().compareTo(BigDecimal.ZERO) == 1) {
								settleAccountsForMap.setVal(df.format(settleAccountsForMap.getCredittotalamount()));
								rofitLossAccountCreditVal = rofitLossAccountCreditVal.add(settleAccountsForMap.getCredittotalamount());
								rofitLossBalancesCredit.add(settleAccountsForMap);	
							}
						}	
					}	
				}
			}
		}
		TAccount currentProfitAccount = null; //本年利润 科目
		if(companySystem == 1){     //小企业
			currentProfitAccount = tAccountService.getAccountsInfoByName("本年利润", fdbid,"3103"); //本年利润 科目
		}else {
			currentProfitAccount = tAccountService.getAccountsInfoByName("本年利润", fdbid,"4103"); //本年利润 科目
		}
		map.put("lossGainBroughtForward", ImmutableMap.of("rofitLossAccountDebit", rofitLossBalancesDebit, "rofitLossAccountCredit", rofitLossBalancesCredit,"currentProfitAccount",currentProfitAccount,"rofitLossAccountDebitVal",formatBigDecimal(rofitLossAccountDebitVal).toString(),"rofitLossAccountCreditVal",formatBigDecimal(rofitLossAccountCreditVal).toString()));
		// ======8.结转损益=============end====	
		
		//========9.以前年度损益调整===============begin===========
		
		BigDecimal priorPeriodAdjustmentsMoney = BigDecimal.ZERO;
		BigDecimal priorPeriodAdjustmentsVal = BigDecimal.ZERO;
		TAccount priorPeriodAdjustmentsAccount = null; //以前年度损益调整 科目
		if(companySystem == 1){     //小企业
			priorPeriodAdjustmentsAccount = tAccountService.getAccountsInfoByName("以前年度损益调整", fdbid,"6000"); //以前年度损益调整 科目
		}else if (companySystem == 2){      //新规则
			priorPeriodAdjustmentsAccount = tAccountService.getAccountsInfoByName("以前年度损益调整", fdbid,"6901"); //以前年度损益调整 科目
		}
		List<TSettleAccounts> priorPeriodAdjustmentsVAT = tBalanceService.settleAccounts(priorPeriodAdjustmentsAccount.getId(), fdbid, accountPeriod);// 转出未交增值税
		TAccount notAssignedProfitAccount = null; //利润分配_未分配利润
		if(companySystem == 1){     //小企业
			notAssignedProfitAccount = tAccountService.getAccountsInfoByName("未分配利润", fdbid,"310403"); //利润分配_未分配利润
		}else if (companySystem == 2){      //新规则
			notAssignedProfitAccount = tAccountService.getAccountsInfoByName("未分配利润", fdbid,"410406"); //利润分配_未分配利润
		}
		this.getAccParentName(notAssignedProfitAccount);
		String creditTotal = "";
		boolean iscredit=false;	//判断是否同为贷方 true  是 ；false 否
		priorPeriodAdjustmentsMoney = addBalance(priorPeriodAdjustmentsVAT);// 未交增值税值
		if (priorPeriodAdjustmentsMoney.compareTo(BigDecimal.ZERO) < 0) {	//以前年度损益调整余额小于0 都显示于贷方
			iscredit = true;
			priorPeriodAdjustmentsAccount.setDebitTotal(BigDecimal.ZERO.toString());
			priorPeriodAdjustmentsAccount.setCreditTotal(priorPeriodAdjustmentsMoney.toString());
			creditTotal = priorPeriodAdjustmentsMoney.negate().toString();
		}else {
			iscredit = false;
			//以前年度损益调整余额大于0   以前年损益显示于贷方   未分配利润显示于借方
			priorPeriodAdjustmentsAccount.setDebitTotal(BigDecimal.ZERO.toString());
			priorPeriodAdjustmentsAccount.setCreditTotal(priorPeriodAdjustmentsMoney.toString());
			creditTotal = priorPeriodAdjustmentsMoney.toString();
		}
		
		priorPeriodAdjustmentsVal = formatBigDecimal(priorPeriodAdjustmentsMoney);
		if (priorPeriodAdjustmentsMoney.compareTo(BigDecimal.ZERO) != 0 ) {
			map.put("priorPeriod",ImmutableMap.of("debitAccount",notAssignedProfitAccount,"creditAccount",priorPeriodAdjustmentsAccount,"val",creditTotal,"exp","结转以前年度损益","iscredit",iscredit));		
		}
		//========9.以前年度损益调整===============end===========
		return map;
	}

	@RequestMapping(value = "gotocheckout")
	public String gotocheckout(HttpSession session,Model model) {
		
		TCustomer customerinfo = (TCustomer) session.getAttribute("sessionCustomer");// 获得当前的客户信息
		String updateDate = customerinfo.getCurrentperiod();
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, Integer.parseInt(updateDate.substring(0, 4)));
		// 设置月份
		cal.set(Calendar.MONTH, Integer.parseInt(updateDate.substring(4, 6)) - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		model.addAttribute("date", lastDayOfMonth);
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		
		return "modules/voucherexp/checkout";
	}
	/**
	 * 
	 * @param lists
	 * @return
	 */
	private BigDecimal addBeginBalance(List<TSettleAccounts> lists) {
		BigDecimal value = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(lists)) {
			for (TSettleAccounts settleAccounts : lists) {
				if (null != settleAccounts) {
					value = value.add(null == settleAccounts.getBeginBalance() ? BigDecimal.ZERO : settleAccounts.getBeginBalance());
				}
			}
		}
		return value;
	}

	private BigDecimal addDebitTotal(List<TSettleAccounts> lists) {
		BigDecimal value = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(lists)) {
			for (TSettleAccounts settleAccounts : lists) {
				if (null != settleAccounts) {
					value = value.add(null == settleAccounts.getDebittotalamount() ? BigDecimal.ZERO : settleAccounts.getDebittotalamount());
				}
			}
		}
		return value;
	}

	private BigDecimal addBalance(List<TSettleAccounts> lists) {
		BigDecimal value = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(lists)) {
			for (TSettleAccounts settleAccounts : lists) {
				if (null != settleAccounts) {
					value = value.add(null == settleAccounts.getBalance() ? BigDecimal.ZERO : settleAccounts.getBalance().setScale(2,BigDecimal.ROUND_HALF_EVEN));
				}
			}
		}
		return value;
	}


	private BigDecimal addCreditTotal(List<TSettleAccounts> lists) {
		BigDecimal value = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(lists)) {
			for (TSettleAccounts settleAccounts : lists) {
				if (null != settleAccounts) {
					value = value.add(null == settleAccounts.getCredittotalamount() ? BigDecimal.ZERO : settleAccounts.getCredittotalamount().setScale(2,BigDecimal.ROUND_HALF_EVEN));
				}
			}
		}
		return value;
	}
	
	
	/**
	 * 根据名称 获取科目 accountid
	 * 
	 * @param accountname
	 * @param fdbid
	 * @return
	 */
	private String getAccountsInfoByName(String accountname, String fdbid,String accId) {
		TAccount tAccount = tAccountService.getAccountsInfoByName(accountname, fdbid,accId);
		if (null != tAccount) {
			return tAccount.getId();
		}
		return null;
	}
	
	/**
	 * 格式化 0.00
	 * @param data
	 * @return
	 */
	private BigDecimal formatBigDecimal(BigDecimal data){
		DecimalFormat df = new DecimalFormat("#.00");
		return new BigDecimal(df.format(data));
	}
	 /**
	  * 获取所有税金科目的基数
	  * @param fdbid 公id
	  * @param listTAccount	所有税的二级科目
	  * @return
	  */
	private Map<String, List<VTaxbaseformula>> getTaxBaseInfo(String fdbid,List<TAccount> listTAccount){
		String accountid = "2221%";
		//获取所有的公式
		List<VTaxbaseformula> listVTaxbaseformulas = vTaxbaseformulaService.findTTaxbaseFormulaByfdbIdAndTaxCategory(fdbid, accountid);
		Map<String, List<VTaxbaseformula>> mapTaxBase = Maps.newHashMap();	//税编号 	和税所对应的公式
		for (TAccount tAccount : listTAccount) {
			
				List<VTaxbaseformula> addMapList =Lists.newArrayList();
				for (VTaxbaseformula vt : listVTaxbaseformulas) {
					if (tAccount.getAccuntId().equals(vt.getTaxcategory())) {
						addMapList.add(vt);
					}
				}
				mapTaxBase.put(tAccount.getAccuntId(), addMapList);	
			
		}
		
		return mapTaxBase;
	}
	/**
	 *  根据编号取余额
	 * @param fdbid
	 * @param accountPeriod
	 * @param accBalanceTaxMap 保存的税种在t_balance 中是否有值
	 * @param listVTaxbaseformulas 税公式
	 * @param mapBigDecimal	保存
	 * @return
	 */
	 
	private BigDecimal getmainOperatingIncomeVal(String fdbid,String accountPeriod,TAccount account,
			List<VTaxbaseformula> listVTaxbaseformulas,
			Map<String, Accountbalance> accBalanceTaxMap,
			Map<String, BigDecimal> mapBigDecimal){
		List<String> accountIds = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(listVTaxbaseformulas)) {
			for (VTaxbaseformula vt : listVTaxbaseformulas) {
				accountIds.add(vt.getAccId());
			}
			List<TBalance> listTBalances = tBalanceService.findListByAccountId(fdbid,accountPeriod, accountIds);
			Map<String, TBalance> maptBalance = Maps.newHashMap();
			if (accountIds.size() > 0) {
				for (TBalance tBalance : listTBalances) {
					maptBalance.put(tBalance.getAccountId(), tBalance);
				}
			}
			return this.getBaseNumber(listVTaxbaseformulas,account, maptBalance,accBalanceTaxMap,mapBigDecimal);
		}else {
			return BigDecimal.ZERO;
		}
	}
	/**
	 * 根据公式计算
	 * @param accBalanceTaxMap 保存的税种在t_balance 中是否有值
	 * @param listVTaxbaseformulas
	 * @param maptBalance
	 * @return
	 */
	private BigDecimal getBaseNumber (List<VTaxbaseformula> listVTaxbaseformulas, TAccount account,
			Map<String, TBalance> maptBalance, Map<String, Accountbalance> accBalanceTaxMap,
			Map<String, BigDecimal> mapBigDecimal){
		BigDecimal comeVal = BigDecimal.ZERO;
		for (VTaxbaseformula vt : listVTaxbaseformulas) {
			
			double debitDouble = 0;
			double creditDouble =0;
			TBalance tb = maptBalance.get(vt.getAccId());
			BigDecimal sl = BigDecimal.ZERO;
				
			if (null !=  tb) {
				String getDebit =  tb.getDebittotalamount() == null 
						|| tb.getDebittotalamount() == "" ? 
								"0" :tb.getDebittotalamount();
				debitDouble = Double.valueOf(getDebit);
				
				String getCredit = tb.getCredittotalamount() == null 
						|| tb.getCredittotalamount() == "" ? 
								"0" :tb.getCredittotalamount();
				creditDouble = Double.valueOf(getCredit);	
			}
			if (mapBigDecimal.containsKey(vt.getAccountid())){
				sl = mapBigDecimal.get(vt.getAccountid());
				
			} else {
				if ("1".equals(vt.getAccDc())) {	// 借	借方金额 - 贷方金额
					sl = BigDecimal.valueOf(debitDouble).subtract(BigDecimal.valueOf(creditDouble));
				} 
				if ("-1".equals(vt.getAccDc())) {		//贷    贷方金额  - 借方金额
					sl = BigDecimal.valueOf(creditDouble).subtract(BigDecimal.valueOf(debitDouble));
				}
			}	
			if ("+".equals(vt.getOp())) {
				comeVal = comeVal.add(sl);
			} 
			if ("-".equals(vt.getOp())) {
				comeVal = comeVal.subtract(sl);
			}	
			if (vt.getAccountid().equals("222102")) {	//如果基数是未交增值税
				Accountbalance accBalance = accBalanceTaxMap.get(account.getId());
				if (accBalance != null) {	//有值  
					String getDebit =  accBalance.getDebittotalamount() == null 
							|| accBalance.getDebittotalamount() == "" ? 
									"0" :accBalance.getDebittotalamount();
					debitDouble = Double.valueOf(getDebit);
					
					String getCredit = accBalance.getCredittotalamount() == null 
							|| accBalance.getCredittotalamount() == "" ? 
									"0" :accBalance.getCredittotalamount();
					creditDouble = Double.valueOf(getCredit);	
					if ( creditDouble != 0) {		//未交增值税 贷方发生变化 则显示 该凭证
						this.setFlag(true);
						comeVal = BigDecimal.ZERO;
						comeVal = BigDecimal.valueOf(creditDouble);//等于贷方本期发生额。
					}
				} 
			}
		}
		return comeVal;
	}
	
	/**
	 * 结转百分比
	 * @return
	 */
	@RequestMapping(value = "updateCustom")
	@ResponseBody
	public Object updateCustom(String carryForwardPercentage,HttpSession session){
		TCustomer customerObject = (TCustomer) session.getAttribute("sessionCustomer");
		customerObject.setCarryForwardPercentage(carryForwardPercentage);
		customerService.save(customerObject);
		return ImmutableMap.of("suc",true);
	}
	/**
	 * 通过子级科目获取父级科目名称
	 * @param acc
	 */
	public void getAccParentName(TAccount acc){
		if (acc != null) {
			List<TAccount> pareAccounts = tAccountService
					.getParentAccounts(acc.getParentId().getId());
		/*	Collections.sort(pareAccounts,new Comparator<TAccount>(){  
	            @Override  
	            public int compare(TAccount b1, TAccount b2) {  
	            	return new Integer( b1.getAccuntId()) - new Integer( b2.getAccuntId());
	            }  
	              
	        });*/
			StringBuffer parentNameString = new StringBuffer();
			if (CollectionUtils.isNotEmpty(pareAccounts)) {
				/*for (TAccount parenttAccount : pareAccounts) {
					parentNameString.append(parenttAccount.getAccountName()
							+ "-");
				}*/
				for(int i = pareAccounts.size() ; i > 0 ; i --){
					parentNameString.append(pareAccounts.get(i-1).getAccountName() + "-");
				}
			}
			//acc.setParentName(parentNameString.toString());
			acc.setAccountName(parentNameString.toString() + acc.getAccountName());	
		}		
	}
	/**
	 * 空值转换为0 防止转换错误
	 * @param model
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	public static void nullToZeroReflect(Object model) throws NoSuchMethodException,
	IllegalAccessException, IllegalArgumentException,
	InvocationTargetException, IntrospectionException {
		Field[] fields = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		// 写数据
		for (Field ff : fields) {
			String name = ff.getName(); // 获取属性的名字
			if (!name.equals("serialVersionUID")) {
				String type = ff.getGenericType().toString(); // 获取属性的类型
				ff.setAccessible(true);
				PropertyDescriptor pd = new PropertyDescriptor(ff.getName(),
						model.getClass());
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
	 * 移除list中相同的元素
	 * @param list
	 */
	public   static   void  removeDuplicateWithOrder(List list)   { 
	   Set set  =   new  HashSet(); 
	   List newList  =   new  ArrayList(); 
	   for  (Iterator iter  =  list.iterator(); iter.hasNext();)   { 
	         Object element  =  iter.next(); 
	         if  (set.add(element)) 
	            newList.add(element); 
	     } 
	     list.clear(); 
	     list.addAll(newList); 
	}
}
