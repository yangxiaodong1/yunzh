/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.dao.workstatistics;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TCompanyYmc;

/**
 * 客户总数记录DAO接口
 * @author xuxiaolong
 * @version 2016-02-24
 */
@MyBatisDao
public interface TCompanyYmcDao extends CrudDao<TCompanyYmc> {
	public TCompanyYmc findYMCountBycompanyId(TCompanyYmc tCompanyYmc);
	public int updatePlus(TCompanyYmc tCompanyYmc);
	public int updateSubtract(TCompanyYmc tCompanyYmc);
	public int updateSubtracts(TCompanyYmc tCompanyYmc);
	
	public TCompanyYmc insertTerm(TCompanyYmc tCompanyYmc);
	public int updateymonthcount(TCompanyYmc tCompanyYmc);
	public TCompanyYmc selectzjymc(TCompanyYmc tCompanyYmc);
	public int insert(TCompanyYmc tCompanyYmc);
	public TCompanyYmc MaxTCompanyYmcByCompanyId(String companyId);
	public int insertlist(List<TCompanyYmc> listtCompanyYmc);
	

}