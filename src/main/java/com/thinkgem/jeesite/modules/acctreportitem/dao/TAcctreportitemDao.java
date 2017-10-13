/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acctreportitem.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.acctreportitem.entity.TAcctreportitem;

/**
 * 公式表DAO接口
 * @author zhangtong
 * @version 2015-12-05
 */
@MyBatisDao
public interface TAcctreportitemDao extends CrudDao<TAcctreportitem> {
	/**
	 * 根据id 删除  公式
	 * @param tAcctreportitem
	 */
	public void deleteAcctreporttitemById(TAcctreportitem tAcctreportitem);
	/**
	 * 自己的保存 公式
	 * @param tAcctreportitem
	 */
	public void savaAcctreporttitem(TAcctreportitem tAcctreportitem);
}