package com.thinkgem.jeesite.modules.rpt.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ECashFlow extends DataEntity<ECashFlow> {
	private String id;
	private String reportItem; // 报表编码
	private String projectName; // 项目名称
	private String lineNumber; // 行次
	private String yearBalance; // 本年累计
	private String periodBalance;// 本月金额
	private boolean titleIsOrNo; // 是否是标题
	private boolean asIsALabel; // 是否为a标签
	private String groupId; // 分组编号
	private String tcashFowId;

	private String suppId;
	private String suppReportItem;
	private String suppProjectName;
	private String suppLineNumber;
	private String suppYearBalance;
	private String suppPeriodBalance;
	private boolean suppTitleIsOrNo;
	private boolean suppAsIsALabel;
	private String suppGroupId; // 分组编号
	private String suppTcashFowId;

	public String getTcashFowId() {
		return tcashFowId;
	}

	public void setTcashFowId(String tcashFowId) {
		this.tcashFowId = tcashFowId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isAsIsALabel() {
		return asIsALabel;
	}

	public void setAsIsALabel(boolean asIsALabel) {
		this.asIsALabel = asIsALabel;
	}

	public boolean isTitleIsOrNo() {
		return titleIsOrNo;
	}

	public void setTitleIsOrNo(boolean titleIsOrNo) {
		this.titleIsOrNo = titleIsOrNo;
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

	public String getSuppReportItem() {
		return suppReportItem;
	}

	public void setSuppReportItem(String suppReportItem) {
		this.suppReportItem = suppReportItem;
	}

	public String getSuppProjectName() {
		return suppProjectName;
	}

	public void setSuppProjectName(String suppProjectName) {
		this.suppProjectName = suppProjectName;
	}

	public String getSuppLineNumber() {
		return suppLineNumber;
	}

	public void setSuppLineNumber(String suppLineNumber) {
		this.suppLineNumber = suppLineNumber;
	}

	public String getSuppYearBalance() {
		return suppYearBalance;
	}

	public void setSuppYearBalance(String suppYearBalance) {
		this.suppYearBalance = suppYearBalance;
	}

	public String getSuppPeriodBalance() {
		return suppPeriodBalance;
	}

	public void setSuppPeriodBalance(String suppPeriodBalance) {
		this.suppPeriodBalance = suppPeriodBalance;
	}

	public boolean isSuppTitleIsOrNo() {
		return suppTitleIsOrNo;
	}

	public void setSuppTitleIsOrNo(boolean suppTitleIsOrNo) {
		this.suppTitleIsOrNo = suppTitleIsOrNo;
	}

	public boolean isSuppAsIsALabel() {
		return suppAsIsALabel;
	}

	public void setSuppAsIsALabel(boolean suppAsIsALabel) {
		this.suppAsIsALabel = suppAsIsALabel;
	}

	public String getSuppId() {
		return suppId;
	}

	public void setSuppId(String suppId) {
		this.suppId = suppId;
	}

	public String getSuppGroupId() {
		return suppGroupId;
	}

	public void setSuppGroupId(String suppGroupId) {
		this.suppGroupId = suppGroupId;
	}

	public String getSuppTcashFowId() {
		return suppTcashFowId;
	}

	public void setSuppTcashFowId(String suppTcashFowId) {
		this.suppTcashFowId = suppTcashFowId;
	}

}
