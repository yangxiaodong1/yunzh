/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.dao.workstatistics;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TWorkGatherInfo;

/**
 * 工作量汇总明细DAO接口
 * @author xuxiaolong
 * @version 2015-11-26
 */
@MyBatisDao
public interface TWorkGatherInfoDao extends CrudDao<TWorkGatherInfo> {
	
}