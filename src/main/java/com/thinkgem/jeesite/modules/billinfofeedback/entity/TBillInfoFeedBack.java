/**
 */
package com.thinkgem.jeesite.modules.billinfofeedback.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.account.entity.TAccount;

/**
 * 发票反馈Entity
 * 
 * @author 发票反馈
 * @version 2015-10-21
 */
public class TBillInfoFeedBack extends DataEntity<TBillInfoFeedBack> implements Cloneable {

	private int billId;
	
	private String feedReason;

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public String getFeedReason() {
		return feedReason;
	}

	public void setFeedReason(String feedReason) {
		this.feedReason = feedReason;
	}
	
	
	
}