/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.power.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.power.entity.TYdSysMenu;

/**
 * 权限列表DAO接口
 * @author yang
 * @version 2016-01-30
 */
@MyBatisDao
public interface TYdSysMenuDao extends CrudDao<TYdSysMenu> {
	public List<TYdSysMenu> findPower(TYdSysMenu t);
}