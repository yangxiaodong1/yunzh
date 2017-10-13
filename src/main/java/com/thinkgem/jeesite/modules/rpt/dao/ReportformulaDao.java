/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rpt.entity.Reportformula;

/**
 * 报表公式科目DAO接口
 * @author zhangtong
 * @version 2015-12-07
 */
@MyBatisDao
public interface ReportformulaDao extends CrudDao<Reportformula> {
	
}