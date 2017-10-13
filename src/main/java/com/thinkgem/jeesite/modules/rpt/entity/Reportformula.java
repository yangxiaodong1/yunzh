/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 报表公式科目Entity
 * @author zhangtong
 * @version 2015-12-07
 */
public class Reportformula extends DataEntity<Reportformula> {
	
	private static final long serialVersionUID = 1L;
	private String fdesc;		// 名称
	private String reportitem;		// 报表编码
	private String datasource;		// 数据源 表示本行处于那哪一级别
	private String formula;		// 公式  用来计算合计的
	private String dc;		// 借贷方向
	private String reportitems;		// 报表编码
	private String ftype;		// 取数规则
	private String op;		// 加减运算
	private String accId;		// acc_id
	private String accuntId;		// accunt_id
	private String accountName;		// account_name
	private String acctYearPeriod;
	private String reportAccountId;
	private String accDc;
	private String acctReportItemId; //公式主键
	private String reportId;		//报表分组的外键
	private String reportitemId;
	private String fdbid;
	
	public String getFdbid() {
		return fdbid;
	}
	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	public String getReportitemId() {
		return reportitemId;
	}
	public void setReportitemId(String reportitemId) {
		this.reportitemId = reportitemId;
	}
	public String getAcctReportItemId() {
		return acctReportItemId;
	}
	public void setAcctReportItemId(String acctReportItemId) {
		this.acctReportItemId = acctReportItemId;
	}
	
	public String getReportAccountId() {
		return reportAccountId;
	}

	public void setReportAccountId(String reportAccountId) {
		this.reportAccountId = reportAccountId;
	}

	public String getAccDc() {
		return accDc;
	}

	public void setAccDc(String accDc) {
		this.accDc = accDc;
	}

	public String getAcctYearPeriod() {
		return acctYearPeriod;
	}

	public void setAcctYearPeriod(String acctYearPeriod) {
		this.acctYearPeriod = acctYearPeriod;
	}

	public Reportformula() {
		super();
	}

	public Reportformula(String id){
		super(id);
	}

	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	public String getFdesc() {
		return fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}
	
	@Length(min=0, max=64, message="报表编码长度必须介于 0 和 64 之间")
	public String getReportitem() {
		return reportitem;
	}

	public void setReportitem(String reportitem) {
		this.reportitem = reportitem;
	}
	
	@Length(min=0, max=4, message="数据源 表示本行处于那哪一级别长度必须介于 0 和 4 之间")
	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	
	@Length(min=0, max=500, message="公式  用来计算合计的长度必须介于 0 和 500 之间")
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	@Length(min=0, max=2, message="借贷方向长度必须介于 0 和 2 之间")
	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}
	
	@Length(min=0, max=64, message="报表编码长度必须介于 0 和 64 之间")
	public String getReportitems() {
		return reportitems;
	}

	public void setReportitems(String reportitems) {
		this.reportitems = reportitems;
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
	
	@Length(min=1, max=11, message="acc_id长度必须介于 1 和 11 之间")
	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}
	
	@Length(min=0, max=64, message="accunt_id长度必须介于 0 和 64 之间")
	public String getAccuntId() {
		return accuntId;
	}

	public void setAccuntId(String accuntId) {
		this.accuntId = accuntId;
	}
	
	@Length(min=0, max=64, message="account_name长度必须介于 0 和 64 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
}