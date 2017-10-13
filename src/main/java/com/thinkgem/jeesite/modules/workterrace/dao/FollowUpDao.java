/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.workterrace.entity.FollowUp;

/**
 * 跟进表DAO接口
 * @author xuxiaolong
 * @version 2016-01-13
 */
@MyBatisDao
public interface FollowUpDao extends CrudDao<FollowUp> {
	public String findUpContent(FollowUp followUp);

}