/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billtype.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.billtype.entity.TBillType;

/**
 * 发票类型DAO接口
 * @author 发票类型
 * @version 2015-10-21
 */
@MyBatisDao
public interface TBillTypeDao extends CrudDao<TBillType> {
	
}