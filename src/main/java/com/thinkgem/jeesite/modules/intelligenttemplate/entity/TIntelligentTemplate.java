/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.intelligenttemplate.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * intelligenttemplateEntity
 * @author intelligenttemplate
 * @version 2015-12-07
 */
public class TIntelligentTemplate extends DataEntity<TIntelligentTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String company;		// company
	private String billType;		// bill_type
	private int count;		// count
	private String debitAccount;		// debit_account
	private String creditAccount;		// credit_account
	
	private String debitAccountName;
	
	private String creditAccountName;		
	
	public TIntelligentTemplate() {
		super();
	}

	public TIntelligentTemplate(String id){
		super(id);
	}

	@Length(min=0, max=255, message="company长度必须介于 0 和 255 之间")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@Length(min=0, max=255, message="bill_type长度必须介于 0 和 255 之间")
	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}
	
	@Length(min=0, max=11, message="count长度必须介于 0 和 11 之间")
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	@Length(min=0, max=255, message="debit_account长度必须介于 0 和 255 之间")
	public String getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}
	
	@Length(min=0, max=255, message="credit_account长度必须介于 0 和 255 之间")
	public String getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}

	public String getDebitAccountName() {
		return debitAccountName;
	}

	public void setDebitAccountName(String debitAccountName) {
		this.debitAccountName = debitAccountName;
	}

	public String getCreditAccountName() {
		return creditAccountName;
	}

	public void setCreditAccountName(String creditAccountName) {
		this.creditAccountName = creditAccountName;
	}
}