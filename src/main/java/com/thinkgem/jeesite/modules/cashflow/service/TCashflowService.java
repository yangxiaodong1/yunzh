/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cashflow.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cashflow.entity.TCashflow;
import com.thinkgem.jeesite.modules.cashflow.dao.TCashflowDao;

/**
 * 现金流量表Service
 * @author zhangtong
 * @version 2015-12-14
 */
@Service
@Transactional(readOnly = true)
public class TCashflowService extends CrudService<TCashflowDao, TCashflow> {

	public TCashflow get(String id) {
		return super.get(id);
	}
	
	public List<TCashflow> findList(TCashflow tCashflow) {
		return super.findList(tCashflow);
	}
	
	public Page<TCashflow> findPage(Page<TCashflow> page, TCashflow tCashflow) {
		return super.findPage(page, tCashflow);
	}
	
	@Transactional(readOnly = false)
	public void save(TCashflow tCashflow) {
		super.save(tCashflow);
	}
	
	@Transactional(readOnly = false)
	public void delete(TCashflow tCashflow) {
		super.delete(tCashflow);
	}
	/**
	 * 报表编号和账期查询	现金		流量附加表使用
	 * @param reportitem
	 * @param yearperiod
	 * @return
	 */
	public TCashflow findCashflowByRepAndPer(@Param("reportitem")String reportitem,@Param("yearperiod")String yearperiod,
			@Param("fdbid")String fdbid,@Param("periodtype")String periodtype){
		return dao.findCashflowByRepAndPer(reportitem, yearperiod,fdbid,periodtype);
	}
	/**
	 * 批量保存数据				流量附加表使用
	 * @param listTCashflow
	 */
	@Transactional(readOnly = false)
	public void savaListCashflow(@Param("list")List<TCashflow> listTCashflow){
		dao.savaListCashflow(listTCashflow);
	}
	/**
	 * 批量更新数据				流量附加表使用
	 * @param listTCashflow
	 */
	@Transactional(readOnly = false)
	public void updateListCashflow(@Param("list")List<TCashflow> listTCashflow){
		dao.updateListCashflow(listTCashflow);
	}
	
	/**
	  * 查询数据				流量附加表使用
	  * @param groupId		分组id
	  * @param yearPeriod	账期
	  */
	public List<TCashflow> findListCashflow(@Param("groupId")String groupId,@Param("yearPeriod")String yearPeriod,@Param("fdbid")String fdbid){
		return dao.findListCashflow(groupId,yearPeriod,fdbid);
	}
}