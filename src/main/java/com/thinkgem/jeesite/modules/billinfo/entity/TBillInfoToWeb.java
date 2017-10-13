package com.thinkgem.jeesite.modules.billinfo.entity;

import java.util.List;

import com.thinkgem.jeesite.modules.billtype.entity.TBillType;

public class TBillInfoToWeb {

	private TBillType billType;

	private List<TBillInfo> billInfoList;

	private String amountCount;

	private String taxCount;

	private String totalPriceCount;

	private int index;
	
	public TBillType getBillType() {
		return billType;
	}

	public void setBillType(TBillType billType) {
		this.billType = billType;
	}

	public List<TBillInfo> getBillInfoList() {
		return billInfoList;
	}

	public void setBillInfoList(List<TBillInfo> billInfoList) {
		this.billInfoList = billInfoList;
	}

	public String getAmountCount() {
		return amountCount;
	}

	public void setAmountCount(String amountCount) {
		this.amountCount = amountCount;
	}

	public String getTaxCount() {
		return taxCount;
	}

	public void setTaxCount(String taxCount) {
		this.taxCount = taxCount;
	}

	public String getTotalPriceCount() {
		return totalPriceCount;
	}

	public void setTotalPriceCount(String totalPriceCount) {
		this.totalPriceCount = totalPriceCount;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
