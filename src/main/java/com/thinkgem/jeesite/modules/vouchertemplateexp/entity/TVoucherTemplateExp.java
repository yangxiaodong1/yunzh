/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vouchertemplateexp.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 凭证模板摘要Entity
 * @author 凭证模板摘要
 * @version 2015-10-30
 */
public class TVoucherTemplateExp extends DataEntity<TVoucherTemplateExp> {
	
	private static final long serialVersionUID = 1L;
	private String templateId;		// template_id
	private String expRowNumber;		// exp_row_number
	private String exp;		// exp
	private String accountId;		// 外键，引用科目表的主键
	private String accountName;		// account_name
	private BigDecimal debit;		// debit
	private BigDecimal credit;		// credit
	
	private String debitStr;
	
	private String creditStr;
	
	private String dc;//科目的 借贷
	
	private String fdbid;		//外键，客户表外键
	
	public TVoucherTemplateExp() {
		super();
	}

	public TVoucherTemplateExp(String id){
		super(id);
	}

	@Length(min=0, max=64, message="template_id长度必须介于 0 和 64 之间")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@Length(min=0, max=64, message="exp_row_number长度必须介于 0 和 64 之间")
	public String getExpRowNumber() {
		return expRowNumber;
	}

	public void setExpRowNumber(String expRowNumber) {
		this.expRowNumber = expRowNumber;
	}
	
	@Length(min=0, max=64, message="exp长度必须介于 0 和 64 之间")
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
	
	@Length(min=0, max=64, message="account_name长度必须介于 0 和 64 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getDebitStr() {
		return debitStr;
	}

	public void setDebitStr(String debitStr) {
		this.debitStr = debitStr;
	}

	public String getCreditStr() {
		return creditStr;
	}

	public void setCreditStr(String creditStr) {
		this.creditStr = creditStr;
	}

	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
}