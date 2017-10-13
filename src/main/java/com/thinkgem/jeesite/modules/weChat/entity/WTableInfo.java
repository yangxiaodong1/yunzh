package com.thinkgem.jeesite.modules.weChat.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class WTableInfo extends DataEntity<WTableInfo> {
	private String accountPeriod;	//账期
	private String rptNameL;	//名称
	private String rptMoneyL;	//金额
	
	private String rptNameR;
	private String rptMoneyR;
	
	public WTableInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WTableInfo(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public WTableInfo(String rptNameL, String rptMoneyL) {
		super();
		this.rptNameL = rptNameL;
		this.rptMoneyL = rptMoneyL;
	}
	public WTableInfo(String rptNameL, String rptMoneyL, String rptNameR,
			String rptMoneyR) {
		super();
		this.rptNameL = rptNameL;
		this.rptMoneyL = rptMoneyL;
		this.rptNameR = rptNameR;
		this.rptMoneyR = rptMoneyR;
	}
	public String getAccountPeriod() {
		return accountPeriod;
	}
	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	public String getRptNameL() {
		return rptNameL;
	}
	public void setRptNameL(String rptNameL) {
		this.rptNameL = rptNameL;
	}
	public String getRptMoneyL() {
		return rptMoneyL;
	}
	public void setRptMoneyL(String rptMoneyL) {
		this.rptMoneyL = rptMoneyL;
	}
	public String getRptNameR() {
		return rptNameR;
	}
	public void setRptNameR(String rptNameR) {
		this.rptNameR = rptNameR;
	}
	public String getRptMoneyR() {
		return rptMoneyR;
	}
	public void setRptMoneyR(String rptMoneyR) {
		this.rptMoneyR = rptMoneyR;
	}
	
}
