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
import com.thinkgem.jeesite.modules.rpt.entity.TReportitem;
import com.thinkgem.jeesite.modules.rpt.dao.TReportitemDao;

/**
 * 所有报表Service
 * @author zhangtong
 * @version 2015-12-03
 */
@Service
@Transactional(readOnly = true)
public class TReportitemService extends CrudService<TReportitemDao, TReportitem> {

	public TReportitem get(String id) {
		return super.get(id);
	}
	
	public List<TReportitem> findList(TReportitem tReportitem) {
		return super.findList(tReportitem);
	}
	
	public Page<TReportitem> findPage(Page<TReportitem> page, TReportitem tReportitem) {
		page.setPageSize(600);
		return super.findPage(page, tReportitem);
	}
	
	@Transactional(readOnly = false)
	public void save(TReportitem tReportitem) {
		super.save(tReportitem);
	}
	
	@Transactional(readOnly = false)
	public void delete(TReportitem tReportitem) {
		super.delete(tReportitem);
	}
	
	public TReportitem findModelByReportitem(@Param("id")String id){
		return dao.findModelByReportitem(id);
	}
	public TReportitem findForMulaByReportitem (@Param("reportitem") String reportitem ){
		return dao.findForMulaByReportitem(reportitem);
	}
	/**
	 * 
	 * @param fdbid 公司id
	 * @param listReItems 报表编码的集合
	 * @return
	 */
	public List<TReportitem> findTReportitemByReItems(@Param("fdbid")String fdbid,
			@Param("list")List<String> listReItems){
		return dao.findTReportitemByReItems(fdbid,listReItems);
		
	}
	/**
	 * 查找非现金流量表的报表信息
	 * @param tReportitem
	 * @return
	 */
	public List<TReportitem> findListByBalanceProfit(TReportitem tReportitem) {
		return dao.findListByBalanceProfit(tReportitem);
	}
}