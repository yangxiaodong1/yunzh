/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billtype.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.billtype.entity.TBillType;
import com.thinkgem.jeesite.modules.billtype.dao.TBillTypeDao;

/**
 * 发票类型Service
 * @author 发票类型
 * @version 2015-10-21
 */
@Service
@Transactional(readOnly = true)
public class TBillTypeService extends CrudService<TBillTypeDao, TBillType> {

	public TBillType get(String id) {
		return super.get(id);
	}
	
	public List<TBillType> findList(TBillType tBillType) {
		return super.findList(tBillType);
	}
	
	public Page<TBillType> findPage(Page<TBillType> page, TBillType tBillType) {
		return super.findPage(page, tBillType);
	}
	
	@Transactional(readOnly = false)
	public void save(TBillType tBillType) {
		super.save(tBillType);
	}
	
	@Transactional(readOnly = false)
	public void delete(TBillType tBillType) {
		super.delete(tBillType);
	}
	
}