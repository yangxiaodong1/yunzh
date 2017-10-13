package com.thinkgem.jeesite.modules.billinfo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.amortize.entity.TAmortize;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.NumberToCN;

public class IntelligentAccountResult implements Serializable{
	
	private TBillInfo billInfo;
	
	private String summary;

	private List<IntelligentAccountContentResult> intelligentAccountContentResults;

	private Date accountDate;
	
	private BigDecimal totalAmount;
	
	private String totalAmountZN;
	
	private int title;
	
	private String accountDateString;
	
	private TAmortize amortize;
	
	private String debitAccountId;
	
	private String creditAccountId;
	
	private boolean amortizeFlag;
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<IntelligentAccountContentResult> getIntelligentAccountContentResults() {
		return intelligentAccountContentResults;
	}

	public void setIntelligentAccountContentResults(
			List<IntelligentAccountContentResult> intelligentAccountContentResults) {
		this.intelligentAccountContentResults = intelligentAccountContentResults;
	}

	public TBillInfo getBillInfo() {
		return billInfo;
	}

	public void setBillInfo(TBillInfo billInfo) {
		this.billInfo = billInfo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalAmountZN() {
		return NumberToCN.number2CNMontrayUnit(totalAmount);
	}

	public void setTotalAmountZN(String totalAmountZN) {
		this.totalAmountZN = totalAmountZN;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public String getAccountDateString() {
		return accountDateString;
	}

	public void setAccountDateString(String accountDateString) {
		this.accountDateString = accountDateString;
	}

	public TAmortize getAmortize() {
		return amortize;
	}

	public void setAmortize(TAmortize amortize) {
		this.amortize = amortize;
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

	public void setCreditAccountId(String creditAccountId) {
		this.creditAccountId = creditAccountId;
	}

	public boolean isAmortizeFlag() {
		return amortizeFlag;
	}

	public void setAmortizeFlag(boolean amortizeFlag) {
		this.amortizeFlag = amortizeFlag;
	}
	
}
