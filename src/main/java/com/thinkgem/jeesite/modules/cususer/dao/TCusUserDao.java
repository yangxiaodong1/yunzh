/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cususer.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cususer.entity.TCusUser;

/**
 * 转交DAO接口
 * @author cy
 * @version 2015-11-24
 */
@MyBatisDao
public interface TCusUserDao extends CrudDao<TCusUser> {
	/**
	 * 根据接受状态、转交用户来获取所有数据
	 * **/
	public List<TCusUser> accpetSate(TCusUser tCusUser);
	
	/**
	 * 改变接受状态
	 * **/
	public void updateState(TCusUser tCusUser);
	
	/**
	 * 根据接受状态、转交用户来获取所有数据(分组显示)
	 * **/
	public List<TCusUser> Noaccept(TCusUser tCusUser);
}