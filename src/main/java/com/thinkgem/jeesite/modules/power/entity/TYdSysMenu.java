/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.power.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 权限列表Entity
 * @author yang
 * @version 2016-01-30
 */
public class TYdSysMenu extends DataEntity<TYdSysMenu> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 权限名称
	private String useable;		// 是否可用
	
	private User user;		// 根据用户ID查询权限列表
	
	public TYdSysMenu(User user) {
		super();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TYdSysMenu() {
		super();
	}

	public TYdSysMenu(String id){
		super(id);
	}

	@Length(min=1, max=100, message="权限名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="是否可用长度必须介于 0 和 64 之间")
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}
	
}