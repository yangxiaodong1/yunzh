/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customersettlerecord.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.customersettlerecord.entity.TCustomerSettleRecord;

/**
 * 结账记录表DAO接口
 * @author zhangtong
 * @version 2016-01-30
 */
@MyBatisDao
public interface TCustomerSettleRecordDao extends CrudDao<TCustomerSettleRecord> {

}