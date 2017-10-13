package com.thinkgem.jeesite.modules.books.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class SubjectBalance extends DataEntity<SubjectBalance>{
	private String id;
	private String accuntid;
	private String accountName;
	private String accountPeriod;		// 账期
	private String beginDebit;		// 初期余额 借
	private String beginCredit; // 初期余额 贷
	private String debittotalamount;		// 本期合计  借方金额
	private String credittotalamount;		// 本期合计  贷方金额
	private String ytddebittotalamount;		// 本年合计  借方金额
	private String ytdcredittotalamount;		// 本年合计  贷方金额
	private String endDebit;		// 期末借方余额
	private String endCredit;		// 期末贷方余额
	private String accLevel; 	//几级科目
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getAccountPeriod() {
		return accountPeriod;
	}
	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	public String getBeginDebit() {
		return beginDebit;
	}
	public void setBeginDebit(String beginDebit) {
		this.beginDebit = beginDebit;
	}
	public String getBeginCredit() {
		return beginCredit;
	}
	public void setBeginCredit(String beginCredit) {
		this.beginCredit = beginCredit;
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
	public String getEndDebit() {
		return endDebit;
	}
	public void setEndDebit(String endDebit) {
		this.endDebit = endDebit;
	}
	public String getEndCredit() {
		return endCredit;
	}
	public void setEndCredit(String endCredit) {
		this.endCredit = endCredit;
	}
	public String getAccLevel() {
		return accLevel;
	}
	public void setAccLevel(String accLevel) {
		this.accLevel = accLevel;
	}
	
}
