/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 附件添加Entity
 * @author cy
 * @version 2015-12-16
 */
public class TAppendix extends DataEntity<TAppendix> {
	
	private static final long serialVersionUID = 1L;
	private String appendixName;		// 附件名称
	private String tCusId;		// 客户外键
	private String appendixTypeName;		// 附加类型及名称
	private String upPeople;		// 上传人
	private String upPeopleId;		// 上传人外键（用户外键）
	private String duiyingState;		// 企业对应状态
	
	public TAppendix() {
		super();
	}

	public TAppendix(String id){
		super(id);
	}

	@Length(min=0, max=100, message="附件名称长度必须介于 0 和 100 之间")
	public String getAppendixName() {
		return appendixName;
	}

	public void setAppendixName(String appendixName) {
		this.appendixName = appendixName;
	}
	
	@Length(min=0, max=64, message="客户外键长度必须介于 0 和 64 之间")
	public String getTCusId() {
		return tCusId;
	}

	public void setTCusId(String tCusId) {
		this.tCusId = tCusId;
	}
	
	@Length(min=0, max=64, message="附加类型及名称长度必须介于 0 和 64 之间")
	public String getAppendixTypeName() {
		return appendixTypeName;
	}

	public void setAppendixTypeName(String appendixTypeName) {
		this.appendixTypeName = appendixTypeName;
	}
	
	@Length(min=0, max=100, message="上传人长度必须介于 0 和 100 之间")
	public String getUpPeople() {
		return upPeople;
	}

	public void setUpPeople(String upPeople) {
		this.upPeople = upPeople;
	}
	
	@Length(min=0, max=64, message="上传人外键（用户外键）长度必须介于 0 和 64 之间")
	public String getUpPeopleId() {
		return upPeopleId;
	}

	public void setUpPeopleId(String upPeopleId) {
		this.upPeopleId = upPeopleId;
	}
	
	@Length(min=0, max=2, message="企业对应状态长度必须介于 0 和 2 之间")
	public String getDuiyingState() {
		return duiyingState;
	}

	public void setDuiyingState(String duiyingState) {
		this.duiyingState = duiyingState;
	}
	
}