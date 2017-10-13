/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.entity.workstatistics;


import java.util.Calendar;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 工作量汇总表Entity
 * @author xuxiaolong
 * @version 2015-11-27
 */
public class TWorkGather extends DataEntity<TWorkGather> {
	private static final long serialVersionUID = 1L;
//	SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
//	private String byYear=formatter.format(new Date());		// 年份
	private String byYear=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	private String customerSum;		// 客户总数
	private String billSum;		// 票据总数
	private String voucherSum;		// 凭证总数
	private String companyId;		// 记账公司id
	private String ymonths;		// 记账公司id
	private String notnull="0";		// 记账公司id
	public String getNotnull() {
		return notnull;
	}

	public void setNotnull(String notnull) {
		this.notnull = notnull;
	}

	public String getYmonths() {
		return ymonths;
	}

	public void setYmonths(String ymonths) {
		this.ymonths = ymonths;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public TWorkGather() {
		super();
	}

	public TWorkGather(String id){
		super(id);
	}

	@Length(min=0, max=64, message="年份长度必须介于 0 和 64 之间")
	public String getByYear() {
		return byYear;
	}

	public void setByYear(String byYear) {
		this.byYear = byYear;
	}
	
	@Length(min=0, max=255, message="客户总数长度必须介于 0 和 255 之间")
	public String getCustomerSum() {
		return customerSum;
	}

	public void setCustomerSum(String customerSum) {
		this.customerSum = customerSum;
	}
	
	@Length(min=0, max=255, message="票据总数长度必须介于 0 和 255 之间")
	public String getBillSum() {
		return billSum;
	}

	public void setBillSum(String billSum) {
		this.billSum = billSum;
	}
	
	@Length(min=0, max=255, message="凭证总数长度必须介于 0 和 255 之间")
	public String getVoucherSum() {
		return voucherSum;
	}

	public void setVoucherSum(String voucherSum) {
		this.voucherSum = voucherSum;
	}
	
}