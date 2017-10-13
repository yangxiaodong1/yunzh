/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.taxbaseformula.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 税率公式和科目表Entity
 * @author zt
 * @version 2016-01-11
 */
public class VTaxbaseformula extends DataEntity<VTaxbaseformula> {
	
	private static final long serialVersionUID = 1L;
	private String fdbid;		// 公司客户外键
	private String taxcategory;		// 税科目的编号
	private String accountid;		// 科目编号
	private String ftype;		// 取数规则
	private String op;		// 运算符号
	private String accId;		// acc_id
	private String accuntId;		// accunt_id
	private String accountName;		// account_name
	private String accDc;		// acc_dc
	
	public VTaxbaseformula() {
		super();
	}

	public VTaxbaseformula(String id){
		super(id);
	}

	@Length(min=0, max=64, message="公司客户外键长度必须介于 0 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	@Length(min=0, max=11, message="税科目的编号长度必须介于 0 和 11 之间")
	public String getTaxcategory() {
		return taxcategory;
	}

	public void setTaxcategory(String taxcategory) {
		this.taxcategory = taxcategory;
	}
	
	@Length(min=0, max=64, message="科目编号长度必须介于 0 和 64 之间")
	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	
	@Length(min=0, max=4, message="取数规则长度必须介于 0 和 4 之间")
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	
	@Length(min=0, max=2, message="运算符号长度必须介于 0 和 2 之间")
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
	
	@Length(min=1, max=64, message="acc_id长度必须介于 1 和 64 之间")
	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
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
	
	@Length(min=0, max=20, message="acc_dc长度必须介于 0 和 20 之间")
	public String getAccDc() {
		return accDc;
	}

	public void setAccDc(String accDc) {
		this.accDc = accDc;
	}
	
}