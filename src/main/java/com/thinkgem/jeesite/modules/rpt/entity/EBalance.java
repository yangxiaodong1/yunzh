package com.thinkgem.jeesite.modules.rpt.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class EBalance extends DataEntity<EBalance> {
	
	private String asId;
	private String asReportItem; //报表编码
	private String assets; //资产
	private String asLineNumber; //行次
	private String asPeriodEnd; // 期末
	private String asYearStart;// 年初
	private boolean asIsALabel; // 是否为a标签
	
	private String liId;
	private String liReportItem; //报表编码
	private String liabilities; //资产
	private String liLineNumber; //行次
	private String liPeriodEnd; // 期末
	private String liYearStart;// 年初
	private boolean liIsALabel; // 是否为a标签
	
	public EBalance() {
		super();
	}

	public EBalance(String id){
		super(id);
	}
	
	public String getAsId() {
		return asId;
	}

	public void setAsId(String asId) {
		this.asId = asId;
	}

	public String getLiId() {
		return liId;
	}

	public void setLiId(String liId) {
		this.liId = liId;
	}

	public String getAsReportItem() {
		return asReportItem;
	}

	public void setAsReportItem(String asReportItem) {
		this.asReportItem = asReportItem;
	}

	public String getAssets() {
		return assets;
	}

	public void setAssets(String assets) {
		this.assets = assets;
	}

	public String getAsLineNumber() {
		return asLineNumber;
	}

	public void setAsLineNumber(String asLineNumber) {
		this.asLineNumber = asLineNumber;
	}

	public String getAsPeriodEnd() {
		return asPeriodEnd;
	}

	public void setAsPeriodEnd(String asPeriodEnd) {
		this.asPeriodEnd = asPeriodEnd;
	}

	public String getAsYearStart() {
		return asYearStart;
	}

	public void setAsYearStart(String asYearStart) {
		this.asYearStart = asYearStart;
	}

	public String getLiReportItem() {
		return liReportItem;
	}

	public void setLiReportItem(String liReportItem) {
		this.liReportItem = liReportItem;
	}

	public String getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(String liabilities) {
		this.liabilities = liabilities;
	}

	public String getLiLineNumber() {
		return liLineNumber;
	}

	public void setLiLineNumber(String liLineNumber) {
		this.liLineNumber = liLineNumber;
	}

	public String getLiPeriodEnd() {
		return liPeriodEnd;
	}

	public void setLiPeriodEnd(String liPeriodEnd) {
		this.liPeriodEnd = liPeriodEnd;
	}

	public String getLiYearStart() {
		return liYearStart;
	}

	public void setLiYearStart(String liYearStart) {
		this.liYearStart = liYearStart;
	}

	public boolean isAsIsALabel() {
		return asIsALabel;
	}

	public void setAsIsALabel(boolean asIsALabel) {
		this.asIsALabel = asIsALabel;
	}

	public boolean isLiIsALabel() {
		return liIsALabel;
	}

	public void setLiIsALabel(boolean liIsALabel) {
		this.liIsALabel = liIsALabel;
	}

}
