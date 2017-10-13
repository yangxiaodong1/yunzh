/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.billinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.billinfo.entity.TBillInfo;

/**
 * 发票信息DAO接口
 * @author 发票信息
 * @version 2015-10-21
 */
@MyBatisDao
public interface TBillInfoDao extends CrudDao<TBillInfo> {
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<TBillInfo> findListWithOrderBy(@Param("billInfo")TBillInfo billInfo,@Param("orderBy")String order);
	
	/**
	 * 根据条件统计数据
	 * @param billInfo
	 * @return
	 */
	public int countBillInfo(@Param("billInfo")TBillInfo billInfo,@Param("billInfoStatus")String billInfoStatus);
	
	
	public List<TBillInfo> queryUploadBillInfo(@Param("billInfo")TBillInfo billInfo);
	
	public List<TBillInfo> queryDealBillInfo(@Param("billInfo")TBillInfo billInfo,@Param("defaultFlag")int defaultFlag,@Param("cancelFlag")boolean cancelFlag);
	
	
	public int updateBill(@Param("id")String id ,@Param("field")String field ,@Param("value")String value);
}