/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 科目金额联合账期查询Entity
 * @author zhangtong
 * @version 2015-12-09
 */
public class Accountbalanceperiod extends DataEntity<Accountbalanceperiod> {
	
	private static final long serialVersionUID = 1L;
	private String accuntId;		// accunt_id
	private String accountName;		// account_name
	private Accountbalanceperiod parent;		// parent_id
	private String accountGroup;		// account_group
	private String accDc;		// acc_dc
	private String isEnable;		// is_enable
	private String balanceid;		// id
	private String balancefdbid;		// 外键，引用公司客户表
	private String accountPeriod;		// 账期
	private String beginbalance;		// 初期余额
	private String debittotalamount;		// 本期合计  借方金额
	private String credittotalamount;		// 本期合计  贷方金额
	private String ytddebittotalamount;		// 本年合计  借方金额
	private String ytdcredittotalamount;		// 本年合计  贷方金额
	private String endbalance;		// 期末余额
	private String amountfor; 
	private String ytdamountfor;
	private String periodEnd;
	private String periodtype;		//月报1 季报2
	
	public String getPeriodtype() {
		return periodtype;
	}
	public void setPeriodtype(String periodtype) {
		this.periodtype = periodtype;
	}
	public String getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
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
	public Accountbalanceperiod() {
		super();
	}

	public Accountbalanceperiod(String id){
		super(id);
	}

	@Length(min=0, max=64, message="accunt_id长度必须介于 0 和 64 之间")
	public String getAccuntId() {
		return accuntId;
	}

	public void setAccuntId(String accuntId) {
		this.accuntId = accuntId;
	}
	
	@Length(min=0, max=64, message="account_name长度必须介于 0 和 64 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@JsonBackReference
	public Accountbalanceperiod getParent() {
		return parent;
	}

	public void setParent(Accountbalanceperiod parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=64, message="account_group长度必须介于 0 和 64 之间")
	public String getAccountGroup() {
		return accountGroup;
	}

	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}
		
	@Length(min=0, max=20, message="acc_dc长度必须介于 0 和 20 之间")
	public String getAccDc() {
		return accDc;
	}

	public void setAccDc(String accDc) {
		this.accDc = accDc;
	}
	
	@Length(min=0, max=1, message="is_enable长度必须介于 0 和 1 之间")
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	
	@Length(min=1, max=64, message="id长度必须介于 1 和 64 之间")
	public String getBalanceid() {
		return balanceid;
	}

	public void setBalanceid(String balanceid) {
		this.balanceid = balanceid;
	}
	
	@Length(min=1, max=64, message="外键，引用公司客户表长度必须介于 1 和 64 之间")
	public String getBalancefdbid() {
		return balancefdbid;
	}

	public void setBalancefdbid(String balancefdbid) {
		this.balancefdbid = balancefdbid;
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
	
}