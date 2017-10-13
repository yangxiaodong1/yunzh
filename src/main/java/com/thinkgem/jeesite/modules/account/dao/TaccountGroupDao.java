/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.TaccountGroup;

/**
 * 科目分组DAO接口
 * @author 陈明
 * @version 2015-12-02
 */
@MyBatisDao
public interface TaccountGroupDao extends CrudDao<TaccountGroup> {
	public List<TaccountGroup> findListByGroupId(String groupId);
}