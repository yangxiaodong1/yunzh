/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.workterrace.entity.TPersonalReminder;

/**
 * 个人提醒DAO接口
 * @author xuxiaolong
 * @version 2016-01-15
 */
@MyBatisDao
public interface TPersonalReminderDao extends CrudDao<TPersonalReminder> {
	public int deletes(List<String> ids); 
}