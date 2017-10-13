package com.thinkgem.jeesite.modules.account.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class BalanceSum extends DataEntity<BalanceSum>{
	private String 	fben;//年初余额的汇总
	private String fen;//期初余额的的汇总
	private String bnj;//本年借的汇总
	private String bnd;//本年代的汇总
	private String bnsy;//本年损益的汇总
	private String bqj;//本期借的汇总
	private String bqd;//本期贷的汇总
	private String bqsy;//本期损益的汇总
	
	public String getFben() {
		return fben;
	}
	public void setFben(String fben) {
		this.fben = fben;
	}
	public String getFen() {
		return fen;
	}
	public void setFen(String fen) {
		this.fen = fen;
	}
	public String getBnj() {
		return bnj;
	}
	public void setBnj(String bnj) {
		this.bnj = bnj;
	}
	public String getBnd() {
		return bnd;
	}
	public void setBnd(String bnd) {
		this.bnd = bnd;
	}
	public String getBnsy() {
		return bnsy;
	}
	public void setBnsy(String bnsy) {
		this.bnsy = bnsy;
	}
	public String getBqj() {
		return bqj;
	}
	public void setBqj(String bqj) {
		this.bqj = bqj;
	}
	public String getBqd() {
		return bqd;
	}
	public void setBqd(String bqd) {
		this.bqd = bqd;
	}
	public String getBqsy() {
		return bqsy;
	}
	public void setBqsy(String bqsy) {
		this.bqsy = bqsy;
	}
	
}
