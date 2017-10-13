/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.workterrace.entity.TPersonalReminder;
import com.thinkgem.jeesite.modules.workterrace.dao.TPersonalReminderDao;

/**
 * 个人提醒Service
 * @author xuxiaolong
 * @version 2016-01-15
 */
@Service
@Transactional(readOnly = true)
public class TPersonalReminderService extends CrudService<TPersonalReminderDao, TPersonalReminder> {

	public TPersonalReminder get(String id) {
		return super.get(id);
	}
	
	public List<TPersonalReminder> findList(TPersonalReminder tPersonalReminder) {
		return super.findList(tPersonalReminder);
	}
	
	public Page<TPersonalReminder> findPage(Page<TPersonalReminder> page, TPersonalReminder tPersonalReminder) {
		return super.findPage(page, tPersonalReminder);
	}
	
	@Transactional(readOnly = false)
	public void save(TPersonalReminder tPersonalReminder) {
		super.save(tPersonalReminder);
	}
	
	@Transactional(readOnly = false)
	public void delete(TPersonalReminder tPersonalReminder) {
		super.delete(tPersonalReminder);
	}
	@Transactional(readOnly = false)
	public int deletes(List<String> ids){
		return dao.deletes(ids);
	}
	
}