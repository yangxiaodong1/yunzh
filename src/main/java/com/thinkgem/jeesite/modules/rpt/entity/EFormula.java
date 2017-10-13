package com.thinkgem.jeesite.modules.rpt.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class EFormula extends DataEntity<EFormula>{
	private String reportitemid;
	private String reportitem; //报表编码
	private String accid; //科目id
	private String reportAccountId;
	private String accuntid;  // 科目编号
	private String accountName; // 科目名称
	private String ftype; //取数规则
	private String op; // 运算符号
	
	private String periodEnd; // 期末
	private String yearStart;// 年初
	private String acctReportItemId; //公式主键
	private String accountperiod; //账期
	
	
	public String getAccountperiod() {
		return accountperiod;
	}
	public void setAccountperiod(String accountperiod) {
		this.accountperiod = accountperiod;
	}
	public String getReportitemid() {
		return reportitemid;
	}
	public void setReportitemid(String reportitemid) {
		this.reportitemid = reportitemid;
	}
	public String getAcctReportItemId() {
		return acctReportItemId;
	}
	public void setAcctReportItemId(String acctReportItemId) {
		this.acctReportItemId = acctReportItemId;
	}
	public String getReportitem() {
		return reportitem;
	}
	public void setReportitem(String reportitem) {
		this.reportitem = reportitem;
	}
	public String getReportAccountId() {
		return reportAccountId;
	}
	public void setReportAccountId(String reportAccountId) {
		this.reportAccountId = reportAccountId;
	}
	public String getAccid() {
		return accid;
	}
	public void setAccid(String accid) {
		this.accid = accid;
	}
	public String getAccuntid() {
		return accuntid;
	}
	public void setAccuntid(String accuntid) {
		this.accuntid = accuntid;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}
	public String getYearStart() {
		return yearStart;
	}
	public void setYearStart(String yearStart) {
		this.yearStart = yearStart;
	}
	
	
}
