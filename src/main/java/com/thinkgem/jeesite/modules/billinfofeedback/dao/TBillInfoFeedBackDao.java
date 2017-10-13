package com.thinkgem.jeesite.modules.billinfofeedback.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.billinfofeedback.entity.TBillInfoFeedBack;

/**
 * 发票反馈DAO接口
 * 
 * @author 发票信息反馈
 * @version 2015-10-21
 */
@MyBatisDao
public interface TBillInfoFeedBackDao extends CrudDao<TBillInfoFeedBack> {
	
}