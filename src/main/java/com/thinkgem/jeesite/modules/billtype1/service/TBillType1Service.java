/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billtype1.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.billtype1.dao.TBillType1Dao;
import com.thinkgem.jeesite.modules.billtype1.entity.TBillType1;

/**
 * 发票类型1Service
 * @author 发票类型1
 * @version 2015-10-21
 */
@Service
@Transactional(readOnly = true)
public class TBillType1Service extends CrudService<TBillType1Dao, TBillType1> {

	public TBillType1 get(String id) {
		return super.get(id);
	}
	
	
	
}