/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rpt.entity.Reportbalance;
import com.thinkgem.jeesite.modules.rpt.dao.ReportbalanceDao;

/**
 * 报表和金额合计Service
 * 
 * @author zhangtong
 * @version 2015-12-05
 */
@Service
@Transactional(readOnly = true)
public class ReportbalanceService extends
		CrudService<ReportbalanceDao, Reportbalance> {

	public Reportbalance get(String id) {
		return super.get(id);
	}

	public List<Reportbalance> findList(Reportbalance reportbalance) {
		return super.findList(reportbalance);
	}

	public Page<Reportbalance> findPage(Page<Reportbalance> page,
			Reportbalance reportbalance) {
		return super.findPage(page, reportbalance);
	}

	@Transactional(readOnly = false)
	public void save(Reportbalance reportbalance) {
		super.save(reportbalance);
	}

	@Transactional(readOnly = false)
	public void delete(Reportbalance reportbalance) {
		super.delete(reportbalance);
	}

	public List<Reportbalance> findListByReportItem(
			@Param("accountP") String accountP, @Param("list") List<String> list) {
		return dao.findListByReportItem(accountP, list);
	}

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
			@Param("fdbid") String fdbid, @Param("accId") String accId) {
		return dao.findListByReItem(accountPeriod, periodEnd, reportitem,
				fdbid, accId);
	}
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
			@Param("fdbid") String fdbid) {
		return dao.findListByReportId(accountPeriod, periodEnd, reportId,
				fdbid);
	}

	public List<Reportbalance> findModeByReItem(
			@Param("accountPeriod") String accountPeriod,
			@Param("accId") String accId, @Param("reportitem") String reportitem) {
		return dao.findModeByReItem(accountPeriod, accId, reportitem);
	}
	/**
	 * 根据公司客户id 账期 编码的集合查询
	 * @param fdbid
	 * @param accountPeriod
	 * @param listReItems
	 * @return
	 */
	public List<Reportbalance> findListByListReItemS(@Param("fdbid")String fdbid,
			@Param("accountPeriod")String accountPeriod,
			@Param("list")List<String> listReItems) {
		return dao.findListByListReItemS(fdbid,accountPeriod,listReItems);
		
	}
}