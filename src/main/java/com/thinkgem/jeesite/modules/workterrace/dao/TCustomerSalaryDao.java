/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.workterrace.dao;

import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.workterrace.entity.TCustomerSalary;

/**
 * 工资DAO接口
 * @author xuxiaolong
 * @version 2015-12-29
 */
@MyBatisDao
public interface TCustomerSalaryDao extends CrudDao<TCustomerSalary> {
	public TCustomerSalary findListBymax(TCustomerSalary tCustomerSalary);
	public String copyCustomerSalary( Map<String, Object> map);
	/**
	 * 统计客户账期
	 * @param fdbid
	 * @param period
	 * @return
	 */
	public TCustomerSalary sumSalary(@Param("fdbid")String fdbid,@Param("period")String period);
	/**
	 * 对工资操作处理
	 * @param fdbid
	 * @param period
	 * @return
	 */
	public TCustomerSalary findSalaryOperation(@Param("fdbid")String fdbid,@Param("period")String period);

}