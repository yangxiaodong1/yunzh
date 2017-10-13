/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.feedback.entity.TFeedback;
import com.thinkgem.jeesite.modules.feedback.dao.TFeedbackDao;

/**
 * 意见反馈Service
 * @author rongjd
 * @version 2016-01-20
 */
@Service
@Transactional(readOnly = true)
public class TFeedbackService extends CrudService<TFeedbackDao, TFeedback> {

	public TFeedback get(String id) {
		return super.get(id);
	}
	
	public List<TFeedback> findList(TFeedback tFeedback) {
		return super.findList(tFeedback);
	}
	
	public Page<TFeedback> findPage(Page<TFeedback> page, TFeedback tFeedback) {
		return super.findPage(page, tFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(TFeedback tFeedback) {
		super.save(tFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(TFeedback tFeedback) {
		super.delete(tFeedback);
	}
	
}