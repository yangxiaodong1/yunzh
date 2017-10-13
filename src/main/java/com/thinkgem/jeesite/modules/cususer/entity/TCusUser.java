/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cususer.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 转交Entity
 * @author cy
 * @version 2015-11-24
 */
public class TCusUser extends DataEntity<TCusUser> {
	
	private static final long serialVersionUID = 1L;
	private String customerName;		// 客户名称
	private String sysuerNameBe;		// 被转交用户
	private String sysuerName;		// 转交用户
	private String acceptState;		// 接受状态
	
	/**
	 * 新添加的字段
	 * **/
	private String customerid;		//客户外键
	private String userid;			//用户外键
	private String userbeid;		//被转交用户外键
	private String count;			//每个人转交他几个公司

	public String getUserbeid() {
		return userbeid;
	}

	public void setUserbeid(String userbeid) {
		this.userbeid = userbeid;
	}
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public TCusUser() {
		super();
	}

	public TCusUser(String id){
		super(id);
	}

	@Length(min=1, max=64, message="客户名称长度必须介于 1 和 64 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=1, max=64, message="被转交用户长度必须介于 1 和 64 之间")
	public String getSysuerNameBe() {
		return sysuerNameBe;
	}

	public void setSysuerNameBe(String sysuerNameBe) {
		this.sysuerNameBe = sysuerNameBe;
	}
	
	@Length(min=1, max=64, message="转交用户长度必须介于 1 和 64 之间")
	public String getSysuerName() {
		return sysuerName;
	}

	public void setSysuerName(String sysuerName) {
		this.sysuerName = sysuerName;
	}
	
	@Length(min=1, max=11, message="接受状态长度必须介于 1 和 11 之间")
	public String getAcceptState() {
		return acceptState;
	}

	public void setAcceptState(String acceptState) {
		this.acceptState = acceptState;
	}
	
}