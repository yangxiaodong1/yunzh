/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 跟进表Entity
 * @author xuxiaolong
 * @version 2016-01-13
 */
public class FollowUp extends DataEntity<FollowUp> {
	
	private static final long serialVersionUID = 1L;
	private String customerId;		// 客户id
	private String upContent;		// 跟进内容
	private Date upTime=new Date();		// 跟进时间
	private String linkman;		// 联系人
	private String phone;		// 联系号码
	private String remarks1;		// 备注
	private String upMan;		// 跟进人userName
	private String userName;
	private String byYear;
	
	
	

	public String getByYear() {
		return byYear;
	}

	public void setByYear(String byYear) {
		this.byYear = byYear;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public FollowUp() {
		super();
	}

	public FollowUp(String id){
		super(id);
	}

	@Length(min=0, max=64, message="客户id长度必须介于 0 和 64 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=64, message="跟进内容长度必须介于 0 和 64 之间")
	public String getUpContent() {
		return upContent;
	}

	public void setUpContent(String upContent) {
		if(!"".equals(upContent)&&null!=upContent)
		{
			this.upContent = upContent;
		}
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getUpTime() {
		return upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}
	
	@Length(min=0, max=50, message="联系人长度必须介于 0 和 50 之间")
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	@Length(min=0, max=50, message="联系号码长度必须介于 0 和 50 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=300, message="备注长度必须介于 0 和 300 之间")
	public String getRemarks1() {
		return remarks1;
	}

	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}
	
	@Length(min=0, max=50, message="跟进人长度必须介于 0 和 50 之间")
	public String getUpMan() {
		return upMan;
	}

	public void setUpMan(String upMan) {
		this.upMan = upMan;
	}
	
}