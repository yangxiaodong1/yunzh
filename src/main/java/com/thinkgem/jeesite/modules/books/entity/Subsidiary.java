package com.thinkgem.jeesite.modules.books.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class Subsidiary extends DataEntity<Subsidiary> {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private String id; // id
	private String voucherTitleName;		// 凭证字名称
	private String voucherTitleNumber;		// 凭证字号
	private Date accountDate;		// 记账日期
	private String accountPeriod;		// 账期
	private String accountDateString; //记账日期时间累计
	
	private String exp;		// 摘要
	private String debit;		// 借方金额
	private String credit;		// 贷方金额
	private String dc;		// 借贷方向
	private String balance;
	private String accId;
	
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVoucherTitleName() {
		return voucherTitleName;
	}
	public void setVoucherTitleName(String voucherTitleName) {
		this.voucherTitleName = voucherTitleName;
	}
	public String getVoucherTitleNumber() {
		return voucherTitleNumber;
	}
	public void setVoucherTitleNumber(String voucherTitleNumber) {
		this.voucherTitleNumber = voucherTitleNumber;
	}
	public Date getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
		this.setAccountDateString(format.format(accountDate));
	}
	public String getAccountPeriod() {
		return accountPeriod;
	}
	public String getAccountDateString() {
		return accountDateString;
	}
	public void setAccountDateString(String accountDateString) {
		this.accountDateString = accountDateString;
	}
	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
