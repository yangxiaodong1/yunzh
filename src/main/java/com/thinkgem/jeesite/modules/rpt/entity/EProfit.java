package com.thinkgem.jeesite.modules.rpt.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class EProfit extends DataEntity<EProfit>{
	private String id;
	private String reportItem; //报表编码
	private String projectName; //项目名称
	private String lineNumber; //行次
	private String yearBalance; // 本年累计
	private String periodBalance;//  本月金额
	private boolean asIsALabel; // 是否为a标签
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportItem() {
		return reportItem;
	}
	public void setReportItem(String reportItem) {
		this.reportItem = reportItem;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getYearBalance() {
		return yearBalance;
	}
	public void setYearBalance(String yearBalance) {
		this.yearBalance = yearBalance;
	}
	public String getPeriodBalance() {
		return periodBalance;
	}
	public void setPeriodBalance(String periodBalance) {
		this.periodBalance = periodBalance;
	}
	public boolean isAsIsALabel() {
		return asIsALabel;
	}
	public void setAsIsALabel(boolean asIsALabel) {
		this.asIsALabel = asIsALabel;
	}
	
}
