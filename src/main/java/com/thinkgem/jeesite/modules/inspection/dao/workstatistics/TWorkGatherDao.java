/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inspection.dao.workstatistics;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.inspection.entity.workstatistics.TWorkGather;

/**
 * 工作量汇总表DAO接口
 * @author xuxiaolong
 * @version 2015-11-27
 */
@MyBatisDao
public interface TWorkGatherDao extends CrudDao<TWorkGather> {
	public TWorkGather tWorkGather1(TWorkGather tWorkGather);
	public TWorkGather tWorkGather2(TWorkGather tWorkGather);
	public TWorkGather tWorkGather3(TWorkGather tWorkGather);
}