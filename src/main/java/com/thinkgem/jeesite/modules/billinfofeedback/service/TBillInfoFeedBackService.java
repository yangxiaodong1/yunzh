/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billinfofeedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.billinfofeedback.dao.TBillInfoFeedBackDao;
import com.thinkgem.jeesite.modules.billinfofeedback.entity.TBillInfoFeedBack;

/**
 * 发票反馈Service
 * @author 发票反馈
 * @version 2015-10-21
 */
@Service
@Transactional(readOnly = true)
public class TBillInfoFeedBackService extends CrudService<TBillInfoFeedBackDao, TBillInfoFeedBack> {
	
	@Autowired
	private TBillInfoFeedBackDao dao;

	
	@Transactional(readOnly = false)
	public void save(TBillInfoFeedBack tBillInfoFeedBack) {
		super.save(tBillInfoFeedBack);
	}
	
}