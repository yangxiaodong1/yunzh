/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.feedback.dao.TFeedbackFileDao;
import com.thinkgem.jeesite.modules.feedback.entity.TFeedbackFile;

/**
 * 意见反馈附件表Service
 * @author rongjd
 * @version 2016-01-20
 */
@Service
@Transactional(readOnly = true)
public class TFeedbackFileService extends CrudService<TFeedbackFileDao, TFeedbackFile> {

	public TFeedbackFile get(String id) {
		return super.get(id);
	}
	
	public List<TFeedbackFile> findList(TFeedbackFile tFeedbackFile) {
		return super.findList(tFeedbackFile);
	}
	
	@Transactional(readOnly = false)
	public void save(TFeedbackFile tFeedbackFile) {
		super.save(tFeedbackFile);
	}
	
	@Transactional(readOnly = false)
	public void delete(TFeedbackFile tFeedbackFile) {
		super.delete(tFeedbackFile);
	}
	
	
}