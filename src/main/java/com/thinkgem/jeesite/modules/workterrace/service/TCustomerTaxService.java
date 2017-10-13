/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.workterrace.entity.TCustomerTax;
import com.thinkgem.jeesite.modules.workterrace.dao.TCustomerTaxDao;

/**
 * 报税表Service
 * @author xuxiaolong
 * @version 2015-12-29
 */
@Service
@Transactional(readOnly = true)
public class TCustomerTaxService extends CrudService<TCustomerTaxDao, TCustomerTax> {

	public TCustomerTax get(String id) {
		return super.get(id);
	}
	
	public List<TCustomerTax> findList(TCustomerTax tCustomerTax) {
		return super.findList(tCustomerTax);
	}
	public List<TCustomerTax> tCustomerTaxList(TCustomerTax tCustomerTax){
		return dao.tCustomerTaxList(tCustomerTax);
	}
	public Page<TCustomerTax> findPage(Page<TCustomerTax> page, TCustomerTax tCustomerTax) {
		return super.findPage(page, tCustomerTax);
	}
	
	@Transactional(readOnly = false)
	public void save(TCustomerTax tCustomerTax) {
		super.save(tCustomerTax);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCustomerTax tCustomerTax) {
		super.delete(tCustomerTax);
	}
	
}