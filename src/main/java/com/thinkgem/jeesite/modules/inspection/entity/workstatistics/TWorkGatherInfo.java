/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.entity.workstatistics;

import java.util.Calendar;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 工作量汇总明细Entity
 * @author xuxiaolong
 * @version 2015-11-26
 */
public class TWorkGatherInfo extends DataEntity<TWorkGatherInfo> {

	private static final long serialVersionUID = 1L;
	private String byYear=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));	// 年份
	private String byYearName=byYear+"年";		// 年份
	private String customerName;		// 客户名称
	private String billSum;		// 票据总数
	private String voucherSum;		// 凭证总数
	private String shuntSum;		// 分路总数
	private String workerName;		// 记账员
	private String companyId;		// 记账公司id
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public TWorkGatherInfo() {
		super();
	}

	public TWorkGatherInfo(String id){
		super(id);
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
	
	@Length(min=1, max=100, message="客户名称长度必须介于 1 和 100 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	
	@Length(min=0, max=11, message="分路总数长度必须介于 0 和 11 之间")
	public String getShuntSum() {
		return shuntSum;
	}

	public void setShuntSum(String shuntSum) {
		this.shuntSum = shuntSum;
	}
	
	@Length(min=1, max=100, message="记账员长度必须介于 1 和 100 之间")
	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	
}