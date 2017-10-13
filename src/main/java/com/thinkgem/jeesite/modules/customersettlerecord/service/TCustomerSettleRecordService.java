/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customersettlerecord.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.customersettlerecord.entity.TCustomerSettleRecord;
import com.thinkgem.jeesite.modules.customersettlerecord.dao.TCustomerSettleRecordDao;

/**
 * 结账记录表Service
 * @author zhangtong
 * @version 2016-01-30
 */
@Service
@Transactional(readOnly = true)
public class TCustomerSettleRecordService extends CrudService<TCustomerSettleRecordDao, TCustomerSettleRecord> {

	public TCustomerSettleRecord get(String id) {
		return super.get(id);
	}
	
	public List<TCustomerSettleRecord> findList(TCustomerSettleRecord tCustomerSettleRecord) {
		return super.findList(tCustomerSettleRecord);
	}
	
	public Page<TCustomerSettleRecord> findPage(Page<TCustomerSettleRecord> page, TCustomerSettleRecord tCustomerSettleRecord) {
		return super.findPage(page, tCustomerSettleRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(TCustomerSettleRecord tCustomerSettleRecord) {
		super.save(tCustomerSettleRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCustomerSettleRecord tCustomerSettleRecord) {
		super.delete(tCustomerSettleRecord);
	}	
}