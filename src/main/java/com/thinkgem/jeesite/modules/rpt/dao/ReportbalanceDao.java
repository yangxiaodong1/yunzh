/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rpt.entity.Reportbalance;

/**
 * 报表和金额合计DAO接口
 * 
 * @author zhangtong
 * @version 2015-12-05
 */
@MyBatisDao
public interface ReportbalanceDao extends CrudDao<Reportbalance> {
	public List<Reportbalance> findListByReportItem(
			@Param("accountP") String accountP, @Param("list") List<String> list);

	/**
	 * 根据账期前后以及公司客户id 查询 资产负债表使用
	 * 
	 * @param accountPeriod
	 * @param periodEnd
	 * @param reportitem
	 * @param fdbid
	 * @return
	 */
	public List<Reportbalance> findListByReItem(
			@Param("accountPeriod") String accountPeriod,
			@Param("periodEnd") String periodEnd,
			@Param("reportitem") String reportitem,
			@Param("fdbid") String fdbid, @Param("accId") String accId);
	/**
	 * 根据账期前后以及公司客户id 查询 资产负债表使用
	 * 
	 * @param accountPeriod
	 * @param periodEnd
	 * @param reportitem
	 * @param fdbid
	 * @return
	 */
	public List<Reportbalance> findListByReportId(
			@Param("accountPeriod") String accountPeriod,
			@Param("periodEnd") String periodEnd,
			@Param("reportId") String reportId,
			@Param("fdbid") String fdbid);
	public List<Reportbalance> findModeByReItem(
			@Param("accountPeriod") String accountPeriod,
			@Param("accId") String accId, @Param("reportitem") String reportitem);
	/**
	 * 根据公司客户id 账期 编码的集合查询
	 * @param fdbid
	 * @param accountPeriod
	 * @param listReItems
	 * @return
	 */
	public List<Reportbalance> findListByListReItemS(@Param("fdbid")String fdbid,
			@Param("accountPeriod")String accountPeriod,
			@Param("list")List<String> listReItems);
}