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
import com.thinkgem.jeesite.modules.rpt.entity.Accountbalanceperiod;
import com.thinkgem.jeesite.modules.rpt.dao.AccountbalanceperiodDao;

/**
 * 科目金额联合账期查询Service
 * @author zhangtong
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class AccountbalanceperiodService extends CrudService<AccountbalanceperiodDao, Accountbalanceperiod> {

	public Accountbalanceperiod get(String id) {
		return super.get(id);
	}
	
	public List<Accountbalanceperiod> findList(Accountbalanceperiod accountbalanceperiod) {
		return super.findList(accountbalanceperiod);
	}
	
	public Page<Accountbalanceperiod> findPage(Page<Accountbalanceperiod> page, Accountbalanceperiod accountbalanceperiod) {
		return super.findPage(page, accountbalanceperiod);
	}
	
	@Transactional(readOnly = false)
	public void save(Accountbalanceperiod accountbalanceperiod) {
		super.save(accountbalanceperiod);
	}
	
	@Transactional(readOnly = false)
	public void delete(Accountbalanceperiod accountbalanceperiod) {
		super.delete(accountbalanceperiod);
	}
	/**
	 * 根据科目编号和账期查询金额及科目信息
	 * @param accId
	 * @param accPeriod
	 * @return
	 */
	public Accountbalanceperiod findAccBlanceByPeriod(@Param("accId")String accId,@Param("accPeriod")String accPeriod,@Param("fdbid")String fdbid){
		return dao.findAccBlanceByPeriod(accId, accPeriod,fdbid);
	}
	/**
	 * 根据科目编号和账期前后查询金额及科目信息
	 * @param accId
	 * @param accPeriod
	 * @return
	 */
	public List<Accountbalanceperiod> findAccBlanceByPeriodEnd(@Param("accId")String accId,@Param("accPeriod")String accPeriod,@Param("periodEnd")String periodEnd,@Param("fdbid")String fdbid){
		return dao.findAccBlanceByPeriodEnd(accId, accPeriod,periodEnd,fdbid);
	}
}