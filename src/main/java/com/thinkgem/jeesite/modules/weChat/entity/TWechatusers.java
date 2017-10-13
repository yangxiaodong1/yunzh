/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weChat.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微信用户操作Entity
 * @author zhangtong
 * @version 2016-01-26
 */
public class TWechatusers extends DataEntity<TWechatusers> {
	
	private static final long serialVersionUID = 1L;
	private String fdbid;		// 公司客户外键
	private String officeCompanyName;	//记账人所在公司名
	private String userName;		// 用户名
	private String password;		// 密码
	private String plainTextPassword; //明文密码
	public TWechatusers() {
		super();
	}

	public TWechatusers(String id){
		super(id);
	}

	@Length(min=0, max=64, message="公司客户外键长度必须介于 0 和 64 之间")
	public String getFdbid() {
		return fdbid;
	}

	public void setFdbid(String fdbid) {
		this.fdbid = fdbid;
	}
	
	public String getOfficeCompanyName() {
		return officeCompanyName;
	}

	public void setOfficeCompanyName(String officeCompanyName) {
		this.officeCompanyName = officeCompanyName;
	}

	@Length(min=0, max=64, message="用户名长度必须介于 0 和 64 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Length(min=0, max=64, message="用户名长度必须介于 0 和 64 之间")
	public String getPlainTextPassword() {
		return plainTextPassword;
	}

	public void setPlainTextPassword(String plainTextPassword) {
		this.plainTextPassword = plainTextPassword;
	}

	@Length(min=0, max=64, message="密码长度必须介于 0 和 64 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}