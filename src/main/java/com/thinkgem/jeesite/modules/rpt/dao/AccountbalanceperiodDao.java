/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rpt.entity.Accountbalanceperiod;

/**
 * 科目金额联合账期查询DAO接口
 * @author zhangtong
 * @version 2015-12-09
 */
@MyBatisDao
public interface AccountbalanceperiodDao extends CrudDao<Accountbalanceperiod> {
	/**
	 * 根据科目编号和账期查询金额及科目信息
	 * @param accId
	 * @param accPeriod
	 * @return
	 */
	public Accountbalanceperiod findAccBlanceByPeriod(@Param("accId")String accId,@Param("accPeriod")String accPeriod,@Param("fdbid")String fdbid);
	
	/**
	 * 根据科目编号和账期前后查询金额及科目信息
	 * @param accId
	 * @param accPeriod
	 * @return
	 */
	public List<Accountbalanceperiod> findAccBlanceByPeriodEnd(@Param("accId")String accId,@Param("accPeriod")String accPeriod,@Param("periodEnd")String periodEnd,@Param("fdbid")String fdbid);

}