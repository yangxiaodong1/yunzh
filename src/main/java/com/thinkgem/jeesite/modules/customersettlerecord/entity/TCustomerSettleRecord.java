/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customersettlerecord.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 结账记录表Entity
 * @author zhangtong
 * @version 2016-01-30
 */
public class TCustomerSettleRecord extends DataEntity<TCustomerSettleRecord> {
	
	private static final long serialVersionUID = 1L;
	private String fdbid;		// 结账客户id
	private Date settleDate;		// 结账时间
	private String settlePeriod;		// 结账账期
	private User user;		// 结账人
	
	public TCustomerSettleRecord() {
		super();
	}

	public TCustomerSettleRecord(String id){
		super(id);
	}

	public TCustomerSettleRecord(String fdbid, Date settleDate,
			String settlePeriod, User user) {
		super();
		this.fdbid = fdbid;
		this.settleDate = settleDate;
		this.settlePeriod = settlePeriod;
		this.user = user;
	}

	@Length(min=0, max=64, message="结账客户id长度必须介于 0 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	
	@Length(min=0, max=6, message="结账账期长度必须介于 0 和 6 之间")
	public String getSettlePeriod() {
		return settlePeriod;
	}

	public void setSettlePeriod(String settlePeriod) {
		this.settlePeriod = settlePeriod;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}