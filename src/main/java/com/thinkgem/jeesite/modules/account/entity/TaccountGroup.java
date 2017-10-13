/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 科目分组Entity
 * @author 陈明
 * @version 2015-12-02
 */
public class TaccountGroup extends DataEntity<TaccountGroup> {
	
	private static final long serialVersionUID = 1L;
	private String fdbid;		//公司id
	private String groupId;		//分组编号
	private String classId;		//
	private String name;		//分组名称
	private String fdc;		//科目方向
	private String cash;		// cash
	private String isEnable;		//是否显示
	
	public TaccountGroup() {
		super();
	}

	public TaccountGroup(String id){
		super(id);
	}

	@Length(min=0, max=64, message="fdbid长度必须介于 0 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	@Length(min=0, max=64, message="group_id长度必须介于 0 和 64 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@Length(min=0, max=64, message="class_id长度必须介于 0 和 64 之间")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	@Length(min=0, max=64, message="name长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="fdc长度必须介于 0 和 64 之间")
	public String getFdc() {
		return fdc;
	}

	public void setFdc(String fdc) {
		this.fdc = fdc;
	}
	
	@Length(min=0, max=64, message="cash长度必须介于 0 和 64 之间")
	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}
	
	@Length(min=0, max=1, message="is_enable长度必须介于 0 和 1 之间")
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	
}