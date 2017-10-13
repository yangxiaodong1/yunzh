package com.thinkgem.jeesite.modules.billinfo.entity;

import java.math.BigDecimal;

public class IntelligentAccountParam {

	private String invoiceID;// 票据唯一号
	private String invoiceType;//
	private String principle;// 会计准则 0，1，2，3
	private String taxNature;// 纳税性质，0，1
	private String companyID;// 公司ID
	private String companyName;// 公司名称
	private String summary;// 摘要
	private BigDecimal money;// 金额
	private BigDecimal tax;// 税金
	private BigDecimal total;// 总金额
	private String payer;// 付款方
	private String payee;// 收款方
	private String extra;// 额外数据

	private String invoiceCategory;
	private String invoiceInfo;
	public String getInvoiceID() {
		return invoiceID;
	}
	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getPrinciple() {
		return principle;
	}
	public void setPrinciple(String principle) {
		this.principle = principle;
	}
	public String getTaxNature() {
		return taxNature;
	}
	public void setTaxNature(String taxNature) {
		this.taxNature = taxNature;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getInvoiceCategory() {
		return invoiceCategory;
	}
	public void setInvoiceCategory(String invoiceCategory) {
		this.invoiceCategory = invoiceCategory;
	}
	public String getInvoiceInfo() {
		return invoiceInfo;
	}
	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
	public IntelligentAccountParam(String invoiceID, String invoiceType, String principle, String taxNature, String companyID, String companyName, String summary, BigDecimal money, BigDecimal tax,
			BigDecimal total, String payer, String payee, String extra, String invoiceCategory, String invoiceInfo) {
		super();
		this.invoiceID = invoiceID;
		this.invoiceType = invoiceType;
		this.principle = principle;
		this.taxNature = taxNature;
		this.companyID = companyID;
		this.companyName = companyName;
		this.summary = summary;
		this.money = money;
		this.tax = tax;
		this.total = total;
		this.payer = payer;
		this.payee = payee;
		this.extra = extra;
		this.invoiceCategory = invoiceCategory;
		this.invoiceInfo = invoiceInfo;
	}

	
}
