/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.books.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 总账和科目Entity
 * @author zhangtong
 * @version 2015-12-04
 */
public class Accountbalance extends DataEntity<Accountbalance> {
	
	private static final long serialVersionUID = 1L;
	private String fdbid;		// fdbid
	private String accuntId;		// accunt_id
	private String accountName;		// account_name
	private String accLevel;		// acc_level
	private String detail;		// detail
	private Accountbalance parent;		// parent_id
	private String rootId;		// root_id
	private String groupId;		// group_id
	private String dc;		// dc
	private String accountPeriod;		// 账期
	private String beginbalance;		// 初期余额
	private String debittotalamount;		// 本期合计  借方金额
	private String credittotalamount;		// 本期合计  贷方金额
	private String ytddebittotalamount;		// 本年合计  借方金额
	private String ytdcredittotalamount;		// 本年合计  贷方金额
	private String endbalance;		// 期末余额
	private String count;
	private String periodEnd;  //账期后
	private String parentName;//科目父级 名称
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}
		
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Accountbalance() {
		super();
	}

	public Accountbalance(String id){
		super(id);
	}

	@Length(min=0, max=64, message="fdbid长度必须介于 0 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
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
	
	@Length(min=0, max=64, message="acc_level长度必须介于 0 和 64 之间")
	public String getAccLevel() {
		return accLevel;
	}

	public void setAccLevel(String accLevel) {
		this.accLevel = accLevel;
	}
	
	@Length(min=0, max=64, message="detail长度必须介于 0 和 64 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@JsonBackReference
	public Accountbalance getParent() {
		return parent;
	}

	public void setParent(Accountbalance parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=64, message="root_id长度必须介于 0 和 64 之间")
	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	
	@Length(min=0, max=64, message="group_id长度必须介于 0 和 64 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@Length(min=0, max=20, message="dc长度必须介于 0 和 20 之间")
	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}
	
	@Length(min=0, max=6, message="账期长度必须介于 0 和 6 之间")
	public String getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	
	public String getBeginbalance() {
		return beginbalance;
	}

	public void setBeginbalance(String beginbalance) {
		this.beginbalance = beginbalance;
	}
	
	public String getDebittotalamount() {
		return debittotalamount;
	}

	public void setDebittotalamount(String debittotalamount) {
		this.debittotalamount = debittotalamount;
	}
	
	public String getCredittotalamount() {
		return credittotalamount;
	}

	public void setCredittotalamount(String credittotalamount) {
		this.credittotalamount = credittotalamount;
	}
	
	public String getYtddebittotalamount() {
		return ytddebittotalamount;
	}

	public void setYtddebittotalamount(String ytddebittotalamount) {
		this.ytddebittotalamount = ytddebittotalamount;
	}
	
	public String getYtdcredittotalamount() {
		return ytdcredittotalamount;
	}

	public void setYtdcredittotalamount(String ytdcredittotalamount) {
		this.ytdcredittotalamount = ytdcredittotalamount;
	}
	
	public String getEndbalance() {
		return endbalance;
	}

	public void setEndbalance(String endbalance) {
		this.endbalance = endbalance;
	}
	
}