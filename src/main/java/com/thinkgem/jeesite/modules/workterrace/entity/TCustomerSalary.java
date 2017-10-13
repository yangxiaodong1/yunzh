/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 工资Entity
 * @author xuxiaolong
 * @version 2015-12-29
 */
public class TCustomerSalary extends DataEntity<TCustomerSalary> {
	
	private static final long serialVersionUID = 1L;
	private String customerId;		// 客户id
	private String salaryPeriod;		// 工资账期
	private Date salaryDate;		// 工资保存账期
	private String orderNumber;		// 序号
	private int ordernum;		// 展示序号
	
	private String employeeName;		// 姓名
	private String identityType;		// 身份证件类型
	private String identityTypeId;		// 身份证件类型
	private String idNumber;		// 身份证件号码
	private String incomeProject;		// 所得项目
	private String incomePeriod;		// 所得期间
	private String incomeMoney;		// 收入额
	private String taxFreeIncome;		// 免税所得
	private String endowmentInsurance;		// 基本养老保险费
	private String medicalInsurance;		// 基本医疗保险费
	private String unemploymentInsurance;		// 失业保险费
	private String houseFund;		// 住房公积金
	private String originalValue;		// 财产原值
	private String allowableDeductions;		// 允许扣除的税费
	private String otherDeduction;		// 其他
	private String totalDeduction;		// 合计
	private String deductionExpenses;		// 减除费用
	private String deductibleDonationAmount;		// 准予扣除的捐赠额
	private String shouldPayIncome;//应纳税所得额#
	private String taxRate;//税率%#
	private String quickDeduction;//速算扣除数#
	private String shouldPay;//应纳税额#
	private String taxRelief;//减免税额
	private String shouldBucklePay;//应扣缴税额#
	private String hasBeenWithholdingTax;//已扣缴税额
	private String shouldRepairPay;//应补（退）税额
	private String remarks1;		// 备注
	
	
	private String unitEndowmentInsurance;		// 单位养老保险
	private String unitMedicalInsurance;		// 单位医疗保险
	private String unitUnemploymentInsurance;		// 单位失业保险
	private String unitInjuryInsurance;		// 单位工伤保险
	private String unitBirthInsurance;		// 单位生育保险
	private String unitHouseFund;		// 单位住房公积金
	
	
	

	
	public String getIdentityTypeId() {
		return identityTypeId;
	}

	public void setIdentityTypeId(String identityTypeId) {
		this.identityTypeId = identityTypeId;
	}

	public TCustomerSalary() {
		super();
	}

	public TCustomerSalary(String id){
		super(id);
	}

	public String getShouldPayIncome() {
		return shouldPayIncome;
	}

	public void setShouldPayIncome(String shouldPayIncome) {
		if(!"".equals(shouldPayIncome)){
			this.shouldPayIncome = shouldPayIncome;
		}
	}

	public String getShouldPay() {
		return shouldPay;
	}

	public void setShouldPay(String shouldPay) {
		if(!"".equals(shouldPay)){
			this.shouldPay = shouldPay;
		}
	}

	public String getShouldBucklePay() {
		return shouldBucklePay;
	}

	public void setShouldBucklePay(String shouldBucklePay) {
		if(!"".equals(shouldBucklePay)){
			this.shouldBucklePay = shouldBucklePay;
		}
	}

	public String getShouldRepairPay() {
		return shouldRepairPay;
	}

	public void setShouldRepairPay(String shouldRepairPay) {
		if(!"".equals(shouldRepairPay)){
			this.shouldRepairPay = shouldRepairPay;
		}
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		if(!"".equals(taxRate)){
			this.taxRate = taxRate;
		}
	}

	public String getQuickDeduction() {
		return quickDeduction;
	}

	public void setQuickDeduction(String quickDeduction) {
		if(!"".equals(quickDeduction)){
			this.quickDeduction = quickDeduction;
		}
	}

	public String getTaxRelief() {
		return taxRelief;
	}

	public void setTaxRelief(String taxRelief) {
		if(!"".equals(taxRelief)){
			this.taxRelief = taxRelief;
		}
	}

	public String getHasBeenWithholdingTax() {
		return hasBeenWithholdingTax;
	}

	public void setHasBeenWithholdingTax(String hasBeenWithholdingTax) {
		if(!"".equals(hasBeenWithholdingTax)){
			this.hasBeenWithholdingTax = hasBeenWithholdingTax;
		}
	}

	public int getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(int ordernum) {
		if(!"".equals(ordernum)){
			this.ordernum = ordernum;
		}
	}

	@Length(min=0, max=64, message="客户id长度必须介于 0 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		if(!"".equals(customerId)){
			this.customerId = customerId;
		}
	}
	
	@Length(min=0, max=6, message="工资账期长度必须介于 0 和 6 之间")
	public String getSalaryPeriod() {
		return salaryPeriod;
	}

	public void setSalaryPeriod(String salaryPeriod) {
		if(!"".equals(salaryPeriod)){
			this.salaryPeriod = salaryPeriod;
		}
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSalaryDate() {
		return salaryDate;
	}

	public void setSalaryDate(Date salaryDate) {
		if(!"".equals(salaryDate)){
			this.salaryDate = salaryDate;
		}
	}
	
	@Length(min=0, max=4, message="序号长度必须介于 0 和 4 之间")
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		if(!"".equals(orderNumber)){
			this.orderNumber = orderNumber;
		}
	}
	
	@Length(min=0, max=50, message="姓名长度必须介于 0 和 50 之间")
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		if(!"".equals(employeeName)){
			this.employeeName = employeeName;
		}
	}
	
	@Length(min=0, max=50, message="身份证件类型长度必须介于 0 和 50 之间")
	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		if(!"".equals(identityType)){
			this.identityType = identityType;
		}
	}
	
	@Length(min=0, max=50, message="身份证件号码长度必须介于 0 和 50 之间")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		if(!"".equals(idNumber)){
			this.idNumber = idNumber;
		}
	}
	
	@Length(min=0, max=50, message="所得项目长度必须介于 0 和 50 之间")
	public String getIncomeProject() {
		return incomeProject;
	}

	public void setIncomeProject(String incomeProject) {
		if(!"".equals(incomeProject)){
			this.incomeProject = incomeProject;
		}
	}
	
	@Length(min=0, max=6, message="所得期间长度必须介于 0 和 6 之间")
	public String getIncomePeriod() {
		return incomePeriod;
	}

	public void setIncomePeriod(String incomePeriod) {
		if(!"".equals(incomePeriod)){
			this.incomePeriod = incomePeriod;
		}
	}
	
	public String getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(String incomeMoney) {
		if(!"".equals(incomeMoney)){
			this.incomeMoney = incomeMoney;
		}
	}
	
	public String getTaxFreeIncome() {
		return taxFreeIncome;
	}

	public void setTaxFreeIncome(String taxFreeIncome) {
		if(!"".equals(taxFreeIncome)){
			this.taxFreeIncome = taxFreeIncome;
		}
	}
	
	public String getEndowmentInsurance() {
		return endowmentInsurance;
	}

	public void setEndowmentInsurance(String endowmentInsurance) {
		if(!"".equals(endowmentInsurance)){
			this.endowmentInsurance = endowmentInsurance;
		}
	}
	
	public String getMedicalInsurance() {
		return medicalInsurance;
	}

	public void setMedicalInsurance(String medicalInsurance) {
		if(!"".equals(medicalInsurance)){
			this.medicalInsurance = medicalInsurance;
		}
	}
	
	@Length(min=0, max=2, message="失业保险费长度必须介于 0 和 2 之间")
	public String getUnemploymentInsurance() {
		return unemploymentInsurance;
	}

	public void setUnemploymentInsurance(String unemploymentInsurance) {
		if(!"".equals(unemploymentInsurance)){
			this.unemploymentInsurance = unemploymentInsurance;
		}
	}
	
	public String getHouseFund() {
		return houseFund;
	}

	public void setHouseFund(String houseFund) {
		if(!"".equals(houseFund)){
			this.houseFund = houseFund;
		}
	}
	
	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		if(!"".equals(originalValue)){
			this.originalValue = originalValue;
		}
	}
	
	public String getAllowableDeductions() {
		return allowableDeductions;
	}

	public void setAllowableDeductions(String allowableDeductions) {
		if(!"".equals(allowableDeductions)){
			this.allowableDeductions = allowableDeductions;
		}
	}
	
	public String getOtherDeduction() {
		return otherDeduction;
	}

	public void setOtherDeduction(String otherDeduction) {
		if(!"".equals(otherDeduction)){
			this.otherDeduction = otherDeduction;
		}
	}
	
	public String getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(String totalDeduction) {
		if(!"".equals(totalDeduction)){
			this.totalDeduction = totalDeduction;
		}
	}
	
	public String getDeductionExpenses() {
		return deductionExpenses;
	}

	public void setDeductionExpenses(String deductionExpenses) {
		if(!"".equals(deductionExpenses)){
			this.deductionExpenses = deductionExpenses;
		}
	}
	
	public String getDeductibleDonationAmount() {
		return deductibleDonationAmount;
	}

	public void setDeductibleDonationAmount(String deductibleDonationAmount) {
		if(!"".equals(deductibleDonationAmount)){
			this.deductibleDonationAmount = deductibleDonationAmount;
		}
	}
	
	public String getUnitEndowmentInsurance() {
		return unitEndowmentInsurance;
	}

	public void setUnitEndowmentInsurance(String unitEndowmentInsurance) {
		if(!"".equals(unitEndowmentInsurance)){
			this.unitEndowmentInsurance = unitEndowmentInsurance;
		}
	}
	
	public String getUnitMedicalInsurance() {
		return unitMedicalInsurance;
	}

	public void setUnitMedicalInsurance(String unitMedicalInsurance) {
		if(!"".equals(unitMedicalInsurance)){
			this.unitMedicalInsurance = unitMedicalInsurance;
		}
	}
	
	public String getUnitUnemploymentInsurance() {
		return unitUnemploymentInsurance;
	}

	public void setUnitUnemploymentInsurance(String unitUnemploymentInsurance) {
		if(!"".equals(unitUnemploymentInsurance)){
			this.unitUnemploymentInsurance = unitUnemploymentInsurance;
		}
	}
	
	public String getUnitInjuryInsurance() {
		return unitInjuryInsurance;
	}

	public void setUnitInjuryInsurance(String unitInjuryInsurance) {
		if(!"".equals(unitInjuryInsurance)){
			this.unitInjuryInsurance = unitInjuryInsurance;
		}
	}
	
	public String getUnitBirthInsurance() {
		return unitBirthInsurance;
	}

	public void setUnitBirthInsurance(String unitBirthInsurance) {
		if(!"".equals(unitBirthInsurance)){
			this.unitBirthInsurance = unitBirthInsurance;
		}
	}
	
	public String getUnitHouseFund() {
		return unitHouseFund;
	}

	public void setUnitHouseFund(String unitHouseFund) {
		if(!"".equals(unitHouseFund)){
			this.unitHouseFund = unitHouseFund;
		}
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemarks1() {
		return remarks1;
	}

	public void setRemarks1(String remarks1) {
		if(!"".equals(remarks1)){
			this.remarks1 = remarks1;
		}
	}
	
}