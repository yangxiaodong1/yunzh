/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.quotation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.quotation.entity.Tquotation;
import com.thinkgem.jeesite.modules.quotation.dao.TquotationDao;

/**
 * 语录Service
 * @author yang
 * @version 2016-01-14
 */
@Service
@Transactional(readOnly = true)
public class TquotationService extends CrudService<TquotationDao, Tquotation> {

	public Tquotation get(String id) {
		return super.get(id);
	}
	
	public List<Tquotation> findList(Tquotation tquotation) {
		return super.findList(tquotation);
	}
	//根据启用状态查询
	public List<Tquotation> findByStartStatus(Tquotation tquotation) {
		return dao.findByStartStatus(tquotation);
	}
	public Page<Tquotation> findPage(Page<Tquotation> page, Tquotation tquotation) {
		return super.findPage(page, tquotation);
	}
	
	@Transactional(readOnly = false)
	public void save(Tquotation tquotation) {
		super.save(tquotation);
	}
	
	@Transactional(readOnly = false)
	public void delete(Tquotation tquotation) {
		super.delete(tquotation);
	}
	
	@Transactional(readOnly = false)
	public void deleteById(String id) {
		dao.deleteById(id);
	}
	
}