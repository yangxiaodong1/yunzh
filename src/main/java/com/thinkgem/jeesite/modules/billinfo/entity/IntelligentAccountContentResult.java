package com.thinkgem.jeesite.modules.billinfo.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.thinkgem.jeesite.modules.account.entity.TAccount;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;

public class IntelligentAccountContentResult implements Serializable{
	
	private int seq;
	
	private String subject;
	
	private BigDecimal debit;
	
	private BigDecimal credit;
	
	private String summary;
	
	private String showdebit;	//页面展示的借方金额
	
	private String showcredit;	//页面显示的贷方金额
	
	private TBalance balance;// 编辑时科目余额
	
	private String dc;// 编辑时科目方向
	
	private TAccount account;
	
	private boolean amortizeFlag;
	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getShowdebit() {
		return showdebit;
	}

	public void setShowdebit(String showdebit) {
		this.showdebit = showdebit;
	}

	public String getShowcredit() {
		return showcredit;
	}

	public void setShowcredit(String showcredit) {
		this.showcredit = showcredit;
	}

	public TBalance getBalance() {
		return balance;
	}

	public void setBalance(TBalance balance) {
		this.balance = balance;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public TAccount getAccount() {
		return account;
	}

	public void setAccount(TAccount account) {
		this.account = account;
	}

	public boolean isAmortizeFlag() {
		return amortizeFlag;
	}

	public void setAmortizeFlag(boolean amortizeFlag) {
		this.amortizeFlag = amortizeFlag;
	}
}
