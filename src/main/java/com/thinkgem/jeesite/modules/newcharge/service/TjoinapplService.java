/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.newcharge.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.newcharge.entity.Tjoinappl;
import com.thinkgem.jeesite.modules.newcharge.dao.TjoinapplDao;

/**
 * 加盟申请Service
 * @author yang
 * @version 2016-01-17
 */
@Service
@Transactional(readOnly = true)
public class TjoinapplService extends CrudService<TjoinapplDao, Tjoinappl> {

	public Tjoinappl get(String id) {
		return super.get(id);
	}
	
	public List<Tjoinappl> findList(Tjoinappl tjoinappl) {
		return super.findList(tjoinappl);
	}
	
	public Page<Tjoinappl> findPage(Page<Tjoinappl> page, Tjoinappl tjoinappl) {
		return super.findPage(page, tjoinappl);
	}
	
	@Transactional(readOnly = false)
	public void save(Tjoinappl tjoinappl) {
		super.save(tjoinappl);
	}
	
	@Transactional(readOnly = false)
	public void delete(Tjoinappl tjoinappl) {
		super.delete(tjoinappl);
	}
	
}