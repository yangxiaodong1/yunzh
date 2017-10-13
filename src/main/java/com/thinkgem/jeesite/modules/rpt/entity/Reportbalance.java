/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 报表和金额合计Entity
 * @author zhangtong
 * @version 2015-12-06
 */
public class Reportbalance extends DataEntity<Reportbalance> {
	
	private static final long serialVersionUID = 1L;
	private String reportitem;		// 报表编码
	private String fdesc;		// 名称
	private String accuntId;		// 科目编号
	private String accountName;		// 科目名称
	private String reportitems;		// 报表编码
	private String ftype;		// 取数规则
	private String beginbalance;		// 初期余额
	private String datasource;		// 数据源 表示本行处于那哪一级别
	private String endbalance;		// 期末余额
	private String op;		// 加减运算
	private String formula;		// 公式  用来计算合计的
	private String dc;		// 借贷方向
	private String accountPeriod;		// 账期
	private String accId;		// acc_id
	private String debittotalamount;		// 本期合计  借方金额
	private String credittotalamount;		// 本期合计  贷方金额
	private String ytddebittotalamount;		// 本年合计  借方金额
	private String ytdcredittotalamount;		// 本年合计  贷方金额
	private String reportAccountId;
	private String acctReportItemId; //公式主键
	private String reportId;	//分组外键
	private String amountfor; 
	private String ytdamountfor;
	private String periodEnd;
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
	public String getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}
	public String getAmountfor() {
		return amountfor;
	}
	public void setAmountfor(String amountfor) {
		this.amountfor = amountfor;
	}
	public String getYtdamountfor() {
		return ytdamountfor;
	}
	public void setYtdamountfor(String ytdamountfor) {
		this.ytdamountfor = ytdamountfor;
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

	private String accDc;
	
	private String acctYearPeriod;
	
	public String getAcctYearPeriod() {
		return acctYearPeriod;
	}

	public void setAcctYearPeriod(String acctYearPeriod) {
		this.acctYearPeriod = acctYearPeriod;
	}

	public Reportbalance() {
		super();
	}

	public Reportbalance(String id){
		super(id);
	}

	@Length(min=0, max=64, message="报表编码长度必须介于 0 和 64 之间")
	public String getReportitem() {
		return reportitem;
	}

	public void setReportitem(String reportitem) {
		this.reportitem = reportitem;
	}
	
	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	public String getFdesc() {
		return fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}
	
	@Length(min=0, max=64, message="科目编号长度必须介于 0 和 64 之间")
	public String getAccuntId() {
		return accuntId;
	}

	public void setAccuntId(String accuntId) {
		this.accuntId = accuntId;
	}
	
	@Length(min=0, max=64, message="科目名称长度必须介于 0 和 64 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	
	public String getBeginbalance() {
		return beginbalance;
	}

	public void setBeginbalance(String beginbalance) {
		this.beginbalance = beginbalance;
	}
	
	@Length(min=0, max=4, message="数据源 表示本行处于那哪一级别长度必须介于 0 和 4 之间")
	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	
	public String getEndbalance() {
		return endbalance;
	}

	public void setEndbalance(String endbalance) {
		this.endbalance = endbalance;
	}
	
	@Length(min=0, max=2, message="加减运算长度必须介于 0 和 2 之间")
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
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
	
	@Length(min=0, max=6, message="账期长度必须介于 0 和 6 之间")
	public String getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	
	@Length(min=1, max=11, message="acc_id长度必须介于 1 和 11 之间")
	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}
	
	public String getDebittotalamount() {
		return debittotalamount;
	}

	public void setDebittotalamount(String debittotalamount) {
		this.debittotalamount = debittotalamount;
	}
	
	public String getCredittotalamount() {
		return credittotalamount;
	}

	public void setCredittotalamount(String credittotalamount) {
		this.credittotalamount = credittotalamount;
	}
	
	public String getYtddebittotalamount() {
		return ytddebittotalamount;
	}

	public void setYtddebittotalamount(String ytddebittotalamount) {
		this.ytddebittotalamount = ytddebittotalamount;
	}
	
	public String getYtdcredittotalamount() {
		return ytdcredittotalamount;
	}

	public void setYtdcredittotalamount(String ytdcredittotalamount) {
		this.ytdcredittotalamount = ytdcredittotalamount;
	}
	
}