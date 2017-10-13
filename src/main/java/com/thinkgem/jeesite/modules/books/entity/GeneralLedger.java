package com.thinkgem.jeesite.modules.books.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class GeneralLedger extends DataEntity<GeneralLedger>{
	
	private String id;
	private String exp;
	private String accuntId;		// accunt_id
	private String accountName;		// account_name
	private String dc;		// dc
	private String accountPeriod;		// 账期
	private String beginbalance;		// 初期余额
	private String debit;		//  借方金额
	private String credit;		//  贷方金额
	private String endbalance;	// 期末余额
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getAccuntId() {
		return accuntId;
	}
	public void setAccuntId(String accuntId) {
		this.accuntId = accuntId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	public String getAccountPeriod() {
		return accountPeriod;
	}
	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	public String getBeginbalance() {
		return beginbalance;
	}
	public void setBeginbalance(String beginbalance) {
		this.beginbalance = beginbalance;
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
	public String getEndbalance() {
		return endbalance;
	}
	public void setEndbalance(String endbalance) {
		this.endbalance = endbalance;
	}
	
	
}
