/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.balance.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 金额平衡表Entity
 * @author zhangtong
 * @version 2015-12-04
 */
public class TBalance extends DataEntity<TBalance> {
	
	private static final long serialVersionUID = 1L;
	private String fdbid;		// 外键，引用公司客户表
	private String accountId;		// 外键，引用科目表的主键
	private String accountPeriod;		// 账期
	private String beginbalance;		// 初期余额
	private String debittotalamount;		// 本期合计  借方金额
	private String credittotalamount;		// 本期合计  贷方金额
	private String ytddebittotalamount;		// 本年合计  借方金额
	private String ytdcredittotalamount;		// 本年合计  贷方金额
	private String endbalance;		// 期末余额
	private String periodEnd;  //账期后
	private String fcur;//货币种类
	private String amountfor;//损益本期的累计
	private String ytdamountfor;//损益本年的累计
	
	public String getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}
	public TBalance() {
		super();
	}

	public TBalance(String id){
		super(id);
	}

	@Length(min=1, max=64, message="外键，引用公司客户表长度必须介于 1 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	@Length(min=0, max=64, message="外键，引用科目表的主键长度必须介于 0 和 64 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=6, message="账期长度必须介于 0 和 6 之间")
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
	
	public String getEndbalance() {
		return endbalance;
	}

	public void setEndbalance(String endbalance) {
		this.endbalance = endbalance;
	}
	public String getFcur() {
		return fcur;
	}
	public void setFcur(String fcur) {
		this.fcur = fcur;
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
	
}