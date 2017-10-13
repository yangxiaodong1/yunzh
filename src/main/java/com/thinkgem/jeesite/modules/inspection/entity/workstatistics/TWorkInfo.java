/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.entity.workstatistics;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 工作量明细表Entity
 * @author xuxiaolong
 * @version 2015-11-26
 */
public class TWorkInfo extends DataEntity<TWorkInfo> {
	
	private static final long serialVersionUID = 1L;
	private String byYear=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));		// 年份// 年份
	private String byYearName=byYear+"年";		// 年份
	private String workerName;		// 记账员
	private String workerNames;		// 记账员
	private String billSum;		// 票据总数
	private String voucherSum;		// 凭证总数
	private String customerSum;		// 客户数
	private String companyId=UserUtils.getUser().getCompany().getId();		// 公司Id
	private String officeId;		// 部门id
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public TWorkInfo() {
		super();
	}

	public TWorkInfo(String id){
		super(id);
	}

	public String getWorkerNames() {
		return workerNames;
	}

	public void setWorkerNames(String workerNames) {
		this.workerNames = workerNames;
	}

	public String getByYearName() {
		return byYearName;
	}

	public void setByYearName(String byYearName) {
		this.byYearName = this.byYear+"年";
	}

	@Length(min=0, max=64, message="年份长度必须介于 0 和 64 之间")
	public String getByYear() {
		return byYear;
	}

	public void setByYear(String byYear) {
		this.byYear = byYear;
		this.byYearName=byYear+"年";
	}
	
	@Length(min=1, max=100, message="记账员长度必须介于 1 和 100 之间")
	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	
	@Length(min=0, max=11, message="票据总数长度必须介于 0 和 11 之间")
	public String getBillSum() {
		return billSum;
	}

	public void setBillSum(String billSum) {
		this.billSum = billSum;
	}
	
	@Length(min=0, max=11, message="凭证总数长度必须介于 0 和 11 之间")
	public String getVoucherSum() {
		return voucherSum;
	}

	public void setVoucherSum(String voucherSum) {
		this.voucherSum = voucherSum;
	}
	
	@Length(min=0, max=11, message="客户数长度必须介于 0 和 11 之间")
	public String getCustomerSum() {
		return customerSum;
	}

	public void setCustomerSum(String customerSum) {
		this.customerSum = customerSum;
	}
	
}