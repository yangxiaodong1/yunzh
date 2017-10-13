/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.taxbase.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 税基数表Entity
 * @author zt
 * @version 2016-01-11
 */
public class TTaxbase extends DataEntity<TTaxbase> {
	
	private static final long serialVersionUID = 1L;
	private String fdbid;		// 公司客户外键
	private String taxcategory;		// 税科目的编号
	private String accountid;		// 科目编号
	private String ftype;		// 取数规则
	private String op;		// 运算符号
	
	public TTaxbase() {
		super();
	}

	public TTaxbase(String id){
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
	
}