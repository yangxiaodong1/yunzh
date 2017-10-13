/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.amortize.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 摊销设置Entity
 * @author yang
 * @version 2015-12-09
 */
public class TAmortize extends DataEntity<TAmortize> {
	
	private static final long serialVersionUID = 1L;
	private String expName;		// 名字
	private String enterAccountDate;		// 入账日期
	private String originalValue;		// 原值
	private String scrapValueRate;		// 残值率
	private String scrapValue;		// 残值
	private String amortizeAgeLimit;		// 摊销年限
	private String monthDiscountOldPosition;		// 月折旧额
	private String totalOldPosition;		// 累计折旧额
	private String netValue;		// 净值
	private String amortizeStatus;		// 摊销状态'0'代表停止摊销，&lsquo;1&rsquo;代表开始摊销

	private String tVoucherExpId;//关联凭证摘要id
	
	private String accountId;//科目id
	
	private String dc;//借贷方向
	
	private String fdbid;//公司id add by wub
	
	private String currentPeriod;//账期 add by wub
	
	private String amortizeAccountId;//摊销科目id add by wub
	
	private String ifInit;//是否是初始余额时设置的。1 初始余额时候设置的摊销，2插入凭证设置的摊销 add by zt
	
	private String debitAccountId; 
	private String creditAccountId;

	public TAmortize() {
		super();
	}

	public TAmortize(String id){
		super(id);
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

	public String getIfInit() {
		return ifInit;
	}

	public void setIfInit(String ifInit) {
		this.ifInit = ifInit;
	}

	@Length(min=0, max=64, message="名字长度必须介于 0 和 64 之间")
	public String getExpName() {
		return expName;
	}

	public void setExpName(String expName) {
		this.expName = expName;
	}
	
	@Length(min=0, max=64, message="入账日期长度必须介于 0 和 64 之间")
	public String getEnterAccountDate() {
		return enterAccountDate;
	}

	public void setEnterAccountDate(String enterAccountDate) {
		this.enterAccountDate = enterAccountDate;
	}
	
	@Length(min=0, max=64, message="原值长度必须介于 0 和 64 之间")
	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}
	
	@Length(min=0, max=10, message="残值率长度必须介于 0 和 10 之间")
	public String getScrapValueRate() {
		return scrapValueRate;
	}

	public void setScrapValueRate(String scrapValueRate) {
		this.scrapValueRate = scrapValueRate;
	}
	
	@Length(min=0, max=64, message="残值长度必须介于 0 和 64 之间")
	public String getScrapValue() {
		return scrapValue;
	}

	public void setScrapValue(String scrapValue) {
		this.scrapValue = scrapValue;
	}
	
	@Length(min=0, max=64, message="摊销年限长度必须介于 0 和 64 之间")
	public String getAmortizeAgeLimit() {
		return amortizeAgeLimit;
	}

	public void setAmortizeAgeLimit(String amortizeAgeLimit) {
		this.amortizeAgeLimit = amortizeAgeLimit;
	}
	
	@Length(min=0, max=64, message="月折旧额长度必须介于 0 和 64 之间")
	public String getMonthDiscountOldPosition() {
		return monthDiscountOldPosition;
	}

	public void setMonthDiscountOldPosition(String monthDiscountOldPosition) {
		this.monthDiscountOldPosition = monthDiscountOldPosition;
	}
	
	@Length(min=0, max=64, message="累计折旧额长度必须介于 0 和 64 之间")
	public String getTotalOldPosition() {
		return totalOldPosition;
	}

	public void setTotalOldPosition(String totalOldPosition) {
		this.totalOldPosition = totalOldPosition;
	}
	
	@Length(min=0, max=64, message="净值长度必须介于 0 和 64 之间")
	public String getNetValue() {
		return netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}
	
	@Length(min=0, max=64, message="摊销状态'0'代表停止摊销，&lsquo;1&rsquo;代表开始摊销长度必须介于 0 和 64 之间")
	public String getAmortizeStatus() {
		return amortizeStatus;
	}

	public void setAmortizeStatus(String amortizeStatus) {
		this.amortizeStatus = amortizeStatus;
	}

	public String gettVoucherExpId() {
		return tVoucherExpId;
	}

	public void settVoucherExpId(String tVoucherExpId) {
		this.tVoucherExpId = tVoucherExpId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}

	public String getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(String currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public String getAmortizeAccountId() {
		return amortizeAccountId;
	}

	public void setAmortizeAccountId(String amortizeAccountId) {
		this.amortizeAccountId = amortizeAccountId;
	}

}