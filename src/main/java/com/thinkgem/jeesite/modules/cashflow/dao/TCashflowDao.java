/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cashflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cashflow.entity.TCashflow;

/**
 * 现金流量表DAO接口
 * @author zhangtong
 * @version 2015-12-14
 */
@MyBatisDao
public interface TCashflowDao extends CrudDao<TCashflow> {
	
	/**
	 * 报表编号和账期查询	现金		流量附加表使用
	 * @param reportitem
	 * @param yearperiod
	 * @return
	 */
	public TCashflow findCashflowByRepAndPer(@Param("reportitem")String reportitem,@Param("yearperiod")String yearperiod,
			@Param("fdbid")String fdbid,@Param("periodtype")String periodtype);
	/**
	 * 批量保存数据				流量附加表使用
	 * @param listTCashflow
	 */
	public void savaListCashflow(@Param("list")List<TCashflow> listTCashflow);
	/**
	 * 批量更新数据				流量附加表使用
	 * @param listTCashflow
	 */
	public void updateListCashflow(@Param("list")List<TCashflow> listTCashflow);
	/**
	  * 查询数据				流量附加表使用
	  * @param groupId		分组id
	  * @param yearPeriod	账期
	  */
	public List<TCashflow> findListCashflow(@Param("groupId")String groupId,@Param("yearPeriod")String yearPeriod,@Param("fdbid")String fdbid);
}