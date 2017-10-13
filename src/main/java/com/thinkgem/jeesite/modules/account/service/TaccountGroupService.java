/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.entity.TaccountGroup;
import com.thinkgem.jeesite.modules.account.dao.TaccountGroupDao;

/**
 * 科目分组Service
 * @author 陈明
 * @version 2015-12-02
 */
@Service
@Transactional(readOnly = true)
public class TaccountGroupService extends CrudService<TaccountGroupDao, TaccountGroup> {

	@Autowired
	private TaccountGroupDao tAccountGroupDao;
	public TaccountGroup get(String id) {
		return super.get(id);
	}
	
	public List<TaccountGroup> findList(TaccountGroup taccountGroup) {
		return super.findList(taccountGroup);
	}
	
	public Page<TaccountGroup> findPage(Page<TaccountGroup> page, TaccountGroup taccountGroup) {
		return super.findPage(page, taccountGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(TaccountGroup taccountGroup) {
		super.save(taccountGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(TaccountGroup taccountGroup) {
		super.delete(taccountGroup);
	}
	public List<TaccountGroup> findListByGroupId(String groupId){
		return tAccountGroupDao.findListByGroupId(groupId);
	}
}