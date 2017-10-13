/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.voucherexp.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.balance.entity.TBalance;

/**
 * 凭证摘要Entity
 * @author 凭证摘要
 * @version 2015-11-27
 */
public class TVoucherExp extends DataEntity<TVoucherExp> {
	
	private static final long serialVersionUID = 1L;
	private String tVId;		// 外键，凭证表的主键
	private String expRowNumber;		// 摘要行号
	private String exp;		// 摘要
	private String accountId;		// 外键，引用科目表的主键

	private String accountName;		// 科目名称

	private double debit;		// 借方金额
	private double credit;		// 贷方金额

	private boolean amortize = false;//是否设置自动摊销
	
	private String showdebit;	//页面展示的借方金额
	private String showcredit;	//页面显示的贷方金额
	
	private String fdbid;//外键，客户表外键
	
	private TBalance balance;//add by wub 20160118 编辑时科目余额
	
	private String dc;//add by wub 20160118 编辑时科目方向
	
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
	public TVoucherExp() {
		super();
	}
	//---------------------------------------------------------------
	public TVoucherExp(String id){
		super(id);
	}

	@Length(min=0, max=64, message="t_v_id长度必须介于 0 和 64 之间")
	public String getTVId() {
		return tVId;
	}

	public void setTVId(String tVId) {
		this.tVId = tVId;
	}
	
	@Length(min=0, max=64, message="摘要行号长度必须介于 0 和 64 之间")
	public String getExpRowNumber() {
		return expRowNumber;
	}

	public void setExpRowNumber(String expRowNumber) {
		this.expRowNumber = expRowNumber;
	}
	
	@Length(min=0, max=64, message="摘要长度必须介于 0 和 64 之间")
	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}
	
	@Length(min=0, max=64, message="外键，引用科目表的主键长度必须介于 0 和 64 之间")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=64, message="科目名称长度必须介于 0 和 64 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public boolean isAmortize() {
		return amortize;
	}

	public void setAmortize(boolean amortize) {
		this.amortize = amortize;
	}

	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}

	public String gettVId() {
		return tVId;
	}

	public void settVId(String tVId) {
		this.tVId = tVId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

}