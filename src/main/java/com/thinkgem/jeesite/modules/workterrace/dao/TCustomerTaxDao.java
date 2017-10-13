/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.workterrace.entity.TCustomerTax;

/**
 * 报税表DAO接口
 * @author xuxiaolong
 * @version 2015-12-29
 */
@MyBatisDao
public interface TCustomerTaxDao extends CrudDao<TCustomerTax> {
	public List<TCustomerTax> tCustomerTaxList(TCustomerTax tCustomerTax);
}