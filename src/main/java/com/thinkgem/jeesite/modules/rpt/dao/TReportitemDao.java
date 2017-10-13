/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rpt.entity.TReportitem;

/**
 * 所有报表DAO接口
 * @author zhangtong
 * @version 2015-12-03
 */
@MyBatisDao
public interface TReportitemDao extends CrudDao<TReportitem> {
	public TReportitem findModelByReportitem(@Param("id")String id);
	public TReportitem findForMulaByReportitem (@Param("reportitem") String reportitem );
	/**
	 * 
	 * @param fdbid 公司id
	 * @param listReItems 报表编码的集合
	 * @return
	 */
	public List<TReportitem> findTReportitemByReItems(@Param("fdbid")String fdbid,
			@Param("list")List<String> listReItems);
	/**
	 * 查找非现金流量表的报表信息
	 * @param tReportitem
	 * @return
	 */
	public List<TReportitem> findListByBalanceProfit(TReportitem tReportitem);
}