/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acctreportitem.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 公式表Entity
 * @author zhangtong
 * @version 2015-12-05
 */
public class TAcctreportitem extends DataEntity<TAcctreportitem> {
	
	private static final long serialVersionUID = 1L;
	private String fdbid;		// 公司客户外键
	private String reportId;		// 报表分组的外键
	private String reportitem;		// 报表编码
	private String accountid;		// 科目编号
	private String ftype;		// 取数规则
	private String op;		// 加减运算
	private String acctyearperiod;		// 创建年表
	private String accountPeriod;
	private String periodEnd;
	private String reportitemId ;
	private String periodtype;
	
	public String getPeriodtype() {
		return periodtype;
	}

	public void setPeriodtype(String periodtype) {
		this.periodtype = periodtype;
	}

	public String getReportitemId() {
		return reportitemId;
	}

	public void setReportitemId(String reportitemId) {
		this.reportitemId = reportitemId;
	}

	public String getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}

	public String getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	public TAcctreportitem() {
		super();
	}

	public TAcctreportitem(String id){
		super(id);
	}

	@Length(min=0, max=64, message="公司客户外键长度必须介于 0 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	@Length(min=0, max=11, message="报表分组的外键长度必须介于 0 和 11 之间")
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	@Length(min=0, max=64, message="报表编码长度必须介于 0 和 64 之间")
	public String getReportitem() {
		return reportitem;
	}

	public void setReportitem(String reportitem) {
		this.reportitem = reportitem;
	}
	
	@Length(min=0, max=64, message="科目编号长度必须介于 0 和 64 之间")
	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	
	@Length(min=0, max=4, message="取数规则长度必须介于 0 和 4 之间")
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	
	@Length(min=0, max=2, message="加减运算长度必须介于 0 和 2 之间")
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
	
	@Length(min=0, max=4, message="创建年表长度必须介于 0 和 4 之间")
	public String getAcctyearperiod() {
		return acctyearperiod;
	}

	public void setAcctyearperiod(String acctyearperiod) {
		this.acctyearperiod = acctyearperiod;
	}
	
}