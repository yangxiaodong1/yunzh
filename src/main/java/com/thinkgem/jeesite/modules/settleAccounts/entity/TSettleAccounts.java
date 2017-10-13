package com.thinkgem.jeesite.modules.settleAccounts.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.account.entity.TAccount;

public class TSettleAccounts extends DataEntity<TSettleAccounts> {

	private BigDecimal beginBalance;
	
	private BigDecimal debittotalamount;
	
	private BigDecimal credittotalamount;
	
	private BigDecimal balance;

	private String debitAccountId;
	
	private String creditAccountId;
	
	private TAccount debitAccount;
	
	private TAccount creditAccount;
	
	private String val;
	
	
	public BigDecimal getBeginBalance() {
		return beginBalance;
	}

	public void setBeginBalance(BigDecimal beginBalance) {
		this.beginBalance = beginBalance;
	}

	public BigDecimal getDebittotalamount() {
		return debittotalamount;
	}

	public void setDebittotalamount(BigDecimal debittotalamount) {
		this.debittotalamount = debittotalamount;
	}

	public BigDecimal getCredittotalamount() {
		return credittotalamount;
	}

	public void setCredittotalamount(BigDecimal credittotalamount) {
		this.credittotalamount = credittotalamount;
	}

	public TAccount getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(TAccount debitAccount) {
		this.debitAccount = debitAccount;
	}

	public TAccount getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(TAccount creditAccount) {
		this.creditAccount = creditAccount;
	}

	public String getDebitAccountId() {
		return debitAccountId;
	}

	public void setDebitAccountId(String debitAccountId) {
		this.debitAccountId = debitAccountId;
	}

	public String getCreditAccountId() {
		return creditAccountId;
	}

	public void setCreditAccountIdString(String creditAccountId) {
		this.creditAccountId = creditAccountId;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public void setCreditAccountId(String creditAccountId) {
		this.creditAccountId = creditAccountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
