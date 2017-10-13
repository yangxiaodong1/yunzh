/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.feedback.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.feedback.entity.TFeedback;

/**
 * 意见反馈DAO接口
 * @author rongjd
 * @version 2016-01-20
 */
@MyBatisDao
public interface TFeedbackDao extends CrudDao<TFeedback> {
	
}