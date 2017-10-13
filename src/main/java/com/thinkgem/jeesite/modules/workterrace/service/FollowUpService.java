/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.workterrace.entity.FollowUp;
import com.thinkgem.jeesite.modules.workterrace.dao.FollowUpDao;

/**
 * 跟进表Service
 * @author xuxiaolong
 * @version 2016-01-13
 */
@Service
@Transactional(readOnly = true)
public class FollowUpService extends CrudService<FollowUpDao, FollowUp> {

	public FollowUp get(String id) {
		return super.get(id);
	}
	
	public List<FollowUp> findList(FollowUp followUp) {
		return super.findList(followUp);
	}
	
	public Page<FollowUp> findPage(Page<FollowUp> page, FollowUp followUp) {
		page.setPageSize(3);
		return super.findPage(page, followUp);
	}
	public String findUpContent(FollowUp followUp){
		return dao.findUpContent(followUp);
	}

	
	
	@Transactional(readOnly = false)
	public void save(FollowUp followUp) {
		super.save(followUp);
	}
	
	@Transactional(readOnly = false)
	public void delete(FollowUp followUp) {
		super.delete(followUp);
	}
	
}