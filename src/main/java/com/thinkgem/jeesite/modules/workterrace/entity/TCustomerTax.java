/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 报税表Entity
 * @author xuxiaolong
 * @version 2015-12-29
 */
public class TCustomerTax extends DataEntity<TCustomerTax> {
	
	private static final long serialVersionUID = 1L;
	private String customerId;		// 客户id
	private String orderNumber;		// 序号
	private String accountId;		// 税种科目id
	private String accountName;		// 税种科目名称
	private String taxBase;		// 计税基数
	private String taxRate;		// 税率
	private String taxMoney;		// 应纳税额
	private String taxPeriod;		// 报税账期
	private Date taxDate=new Date();;		// 报税日期
	private String remarks1;		// 备注
	
	public TCustomerTax() {
		super();
	}

	public TCustomerTax(String id){
		super(id);
	}

	@Length(min=0, max=64, message="客户id长度必须介于 0 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=4, message="序号长度必须介于 0 和 4 之间")
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	@Length(min=0, max=100, message="税种科目id长度必须介于 0 和 100 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=50, message="税种科目名称长度必须介于 0 和 50 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getTaxBase() {
		return taxBase;
	}

	public void setTaxBase(String taxBase) {
		this.taxBase = taxBase;
	}
	
	@Length(min=0, max=2, message="税率长度必须介于 0 和 2 之间")
	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	
	public String getTaxMoney() {
		return taxMoney;
	}

	public void setTaxMoney(String taxMoney) {
		this.taxMoney = taxMoney;
	}
	
	@Length(min=0, max=6, message="报税账期长度必须介于 0 和 6 之间")
	public String getTaxPeriod() {
		return taxPeriod;
	}

	public void setTaxPeriod(String taxPeriod) {
		this.taxPeriod = taxPeriod;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaxDate() {
		return taxDate;
	}

	public void setTaxDate(Date taxDate) {
		this.taxDate = taxDate;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemarks1() {
		return remarks1;
	}

	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}
	
}