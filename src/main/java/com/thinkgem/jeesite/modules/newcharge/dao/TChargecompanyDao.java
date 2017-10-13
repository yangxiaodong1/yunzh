/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.newcharge.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.newcharge.entity.TChargecompany;
import com.thinkgem.jeesite.modules.newcharge.entity.TCountCompanynew;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 记账公司DAO接口
 * @author yang
 * @version 2016-01-15
 */
@MyBatisDao
public interface TChargecompanyDao extends CrudDao<TChargecompany> {
	public List<Office> selectByStatus(Office office);
	public List<Office> selectByStatus2(Office office);//新修改
	public List<Office> selectByStatusN(Office office);
	public List<Office> selectByStatusN2(Office office);//新修改
	public List<Office> selByStatus(Office office);//根据试一试用状态查询
	public List<Office> selByStatus2(Office office);//根据试一试用状态查询  新修改
	public List<Map<String, Object>> dataGeneralSituation(Map<String, Object> map);
	
	public List<TCountCompanynew> dataGeneralSituationNew(Map<String, Object> map);//yang
	//public List<TCountCompanynew> dataGeneralSituationNew();//yang
	
	public List<Office> allCity(Office office);//根据所有城市状态查询
	//public List<Map<String, Object>> indexData(Map<String, Object> map);//首页数据统计
	
	public List<Map<String, Object>> indexDatanew(Map<String, Object> map);//首页数据统计new
	public List<Map<String, Object>> indexDatanewaccount(Map<String, Object> map);//首页数据统计new
	public List<Map<String, Object>> indexDatanewcustomer(Map<String, Object> map);
	
	public String conutcharge();
	public String conutaccount();
	public String conutcustomer();
	
	//public Map<String, Object> indexDatasum();//首页数据统计
	public List<TChargecompany> findByTime(TChargecompany tChargecompany);//根据时间查询

	public List<Map<String, Object>> cityGeneralSituation(@Param(value="city")String city);//根据区域统计代帐客户
	
	
	public List<Map<String, Object>> companySituation(@Param(value="city")String city);//首页显示记账公司城市分布
	public List<Map<String, Object>> customerSituation(@Param(value="city")String city);//首页显示记账公司城市分布
	public List<Map<String, Object>> userSituation(@Param(value="city")String city);//首页显示记账公司城市分布
	
	public Office getbyid(String id);
}